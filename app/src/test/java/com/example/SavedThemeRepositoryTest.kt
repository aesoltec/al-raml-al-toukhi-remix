package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AlRamlDatabase
import com.example.data.ArchiveExportFormat
import com.example.data.ArchiveReportExporter
import com.example.data.SavedThemeRepository
import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.AlToukhiGeomancyEngine
import com.example.geomancy.Planet
import com.example.geomancy.PointParity
import com.example.geomancy.PredictiveCalibrationEngine
import com.example.geomancy.QuestionDomain
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SavedThemeRepositoryTest {

  private lateinit var db: AlRamlDatabase
  private lateinit var repository: SavedThemeRepository

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db = Room.inMemoryDatabaseBuilder(context, AlRamlDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    repository = SavedThemeRepository(db.savedThemeDao(), db.calibrationDao())
  }

  @After
  fun tearDown() {
    db.close()
  }

  @Test
  fun saveUpdateAndReconstructTheme() = runBlocking {
    val m1 = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.ODD, PointParity.ODD, PointParity.ODD)
    val m2 = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.ODD, PointParity.ODD, PointParity.EVEN)
    val m3 = AlToukhiFigures.findByRanks(PointParity.EVEN, PointParity.ODD, PointParity.ODD, PointParity.ODD)
    val m4 = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.EVEN, PointParity.ODD, PointParity.EVEN)

    val reading =
      AlToukhiGeomancyEngine.castFromMothers(
        mothers = listOf(m1, m2, m3, m4),
        domain = QuestionDomain.CONTEST,
        questionText = "Mauritanie vs Burkina Faso",
        activePlanetHour = Planet.MARS,
      )

    val id =
      repository.saveReading(
        reading = reading,
        title = "Mauritanie vs Burkina Faso",
        contextOutcomeNotes = "17-11-2026: 1-3",
      )

    var saved = repository.savedThemes.first()
    assertEquals(1, saved.size)
    assertEquals("17-11-2026: 1-3", saved.first().contextOutcomeNotes)

    repository.updateContext(id, "Mauritanie vs Burkina Faso (CAN 2027)", "Final score: 1-3 Burkina Faso")
    saved = repository.savedThemes.first()
    assertEquals("Final score: 1-3 Burkina Faso", saved.first().contextOutcomeNotes)

    val reconstructed = repository.reconstructReading(saved.first())
    assertEquals(reading.house(15).figure.code, reconstructed.house(15).figure.code)
    assertEquals(reading.house(16).figure.code, reconstructed.house(16).figure.code)
    assertEquals(QuestionDomain.CONTEST, reconstructed.domain)
    assertEquals(Planet.MARS, reconstructed.activePlanetHour)
  }

  @Test
  fun importMatchesExtractSamplesAndPersistCalibratedWeights() = runBlocking {
    val added = repository.importNov17RealMatchesIfMissing()
    assertEquals(10, added)
    assertEquals(0, repository.importNov17RealMatchesIfMissing())

    val themes = repository.savedThemes.first()
    val samples = repository.extractCalibrationSamples(themes)
    assertEquals(10, samples.size)

    val report = com.example.geomancy.PredictiveCalibrationEngine.evaluateAndCalibrate(samples)
    repository.persistCalibrationReport(report, enabled = true)

    val storedConfig = repository.calibratedWeightsConfig.first()
    val decoded = repository.decodeWeightTable(storedConfig)
    assertEquals(report.calibratedWeights.weightsByFigureId.size, decoded.weightsByFigureId.size)
    org.junit.Assert.assertTrue(decoded.isCalibrated == report.calibratedWeights.isCalibrated || decoded.isCalibrated)

    repository.setCalibrationEnabled(false)
    val disabledTable = repository.decodeWeightTable(repository.calibratedWeightsConfig.first())
    org.junit.Assert.assertFalse(disabledTable.isCalibrated)
  }

  @Test
  fun seedNov18ConsultationDrawsIntoArchivesIdempotently() = runBlocking {
    val addedFirst = repository.seedNov18ConsultationDrawsIfMissing()
    assertEquals(6, addedFirst)
    val addedSecond = repository.seedNov18ConsultationDrawsIfMissing()
    assertEquals(0, addedSecond)

    val themes = repository.savedThemes.first()
    assertEquals(6, themes.size)
    org.junit.Assert.assertTrue(themes.all { it.leagueCategory == "Copa del Rey" })
    org.junit.Assert.assertTrue(themes.any { it.title.contains("Club Portugalete vs Real Unión") })
    org.junit.Assert.assertTrue(themes.any { it.title.contains("Reus Reddis vs Barakaldo") })
  }

  @Test
  fun exportSingleAndAllArchivesAsTextAndMarkdownReport() = runBlocking {
    repository.importNov17RealMatchesIfMissing()
    val themes = repository.savedThemes.first()
    val samples = repository.extractCalibrationSamples(themes)
    val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)
    val firstTheme = themes.first()

    val singleTxt = repository.exportSingleArchive(firstTheme, ArchiveExportFormat.PLAIN_TEXT, report.calibratedWeights)
    val singleMd = repository.exportSingleArchive(firstTheme, ArchiveExportFormat.MARKDOWN_REPORT, report.calibratedWeights)
    val allTxt = repository.exportAllArchives(themes, report, ArchiveExportFormat.PLAIN_TEXT, report.calibratedWeights)
    val allMd = repository.exportAllArchives(themes, report, ArchiveExportFormat.MARKDOWN_REPORT, report.calibratedWeights)

    org.junit.Assert.assertTrue(singleTxt.contains("LES 16 MAISONS DU TAKHT"))
    org.junit.Assert.assertTrue(singleTxt.contains(firstTheme.title))
    org.junit.Assert.assertTrue(singleMd.contains("# Rapport Géomantique : ${firstTheme.title}"))
    org.junit.Assert.assertTrue(singleMd.contains("| Maison | Nom traditionnel |"))

    org.junit.Assert.assertTrue(allTxt.contains("EXPORT COMPLET DES ARCHIVES (10 THÈMES)"))
    org.junit.Assert.assertTrue(allTxt.contains("BILAN PRÉDICTIF DES RENCONTRES ÉVALUÉES"))
    org.junit.Assert.assertTrue(allMd.contains("# Archives Complètes — Al-Raml Al-Toukhi"))
    org.junit.Assert.assertTrue(allMd.contains("## Synthèse de Précision Prédictive"))

    val singleFile = ArchiveReportExporter.suggestedFileName(firstTheme, ArchiveExportFormat.MARKDOWN_REPORT)
    val allFile = ArchiveReportExporter.suggestedFileName(null, ArchiveExportFormat.PLAIN_TEXT)
    org.junit.Assert.assertTrue(singleFile.startsWith("al_raml_") && singleFile.endsWith(".md"))
    org.junit.Assert.assertTrue(allFile.startsWith("al_raml_archives_complet_") && allFile.endsWith(".txt"))
  }

  @Test
  fun archiveRecurrenceCountsDuplicatesAndAppliesVerifiedNov18Results() = runBlocking {
    repository.seedNov18ConsultationDrawsIfMissing()
    val updated = repository.applyNov18VerifiedResults()
    assertEquals(6, updated)
    assertEquals(0, repository.applyNov18VerifiedResults())

    val themes = repository.savedThemes.first()
    val samples = repository.extractCalibrationSamples(themes)
    assertEquals(6, samples.size)

    // Sauvegarder un thème ayant exactement la même parité M1–M4 que Portugalete vs Real Unión
    val duplicateReading =
      AlToukhiGeomancyEngine.castFromDotCounts(
        sixteenCounts = listOf(4, 5, 6, 8, 6, 4, 3, 5, 7, 5, 8, 3, 4, 3, 6, 4), // 2122 / 2211 / 1121 / 2122
        domain = QuestionDomain.CONTEST,
        questionText = "Répétition du thème",
      )
    repository.saveReading(duplicateReading, "Thème répété", "")

    val recurrenceReport = repository.computeArchiveRecurrences(repository.savedThemes.first())
    assertEquals(7, recurrenceReport.totalThemes)
    assertEquals(6, recurrenceReport.uniqueSignatures)
    assertEquals(1, recurrenceReport.recurringSignatureCount)
    assertEquals(2, recurrenceReport.bySignature["2122/2211/1121/2122"]?.occurrenceCount)
  }

  @Test
  fun categoryGroupingCardsTrackingAndCategoryExportWorkTogether() = runBlocking {
    repository.importNov17RealMatchesIfMissing()
    val first = repository.savedThemes.first().first()

    val originalCreatedAt = first.createdAtMillis
    repository.updateContext(
      id = first.id,
      title = first.title,
      notes = first.contextOutcomeNotes,
      actualOutcome = com.example.geomancy.MatchOutcome.CAMP_B_WIN,
      actualScoreText = "1-3",
      leagueCategory = "Qualifications CAN Test",
      actualYellowCardsText = "4",
      actualRedCardsText = "0",
    )

    val themesAfterEdit = repository.savedThemes.first()
    val updated = themesAfterEdit.first { it.id == first.id }
    assertEquals(originalCreatedAt, updated.createdAtMillis)
    assertEquals("4", updated.actualYellowCardsText)
    assertEquals("0", updated.actualRedCardsText)
    assertEquals("Qualifications CAN Test", updated.leagueCategory)

    val grouped = repository.groupByCategory(themesAfterEdit)
    org.junit.Assert.assertTrue(grouped.containsKey("Qualifications CAN Test"))
    org.junit.Assert.assertEquals(1, grouped["Qualifications CAN Test"]?.size)

    val catMd =
      repository.exportCategoryArchives(
        categoryName = "Qualifications CAN Test",
        entities = themesAfterEdit,
        format = ArchiveExportFormat.MARKDOWN_REPORT,
      )
    val catTxt =
      repository.exportCategoryArchives(
        categoryName = "Qualifications CAN Test",
        entities = themesAfterEdit,
        format = ArchiveExportFormat.PLAIN_TEXT,
      )
    org.junit.Assert.assertTrue(catMd.contains("# Export par Catégorie : Qualifications CAN Test"))
    org.junit.Assert.assertTrue(catMd.contains("Cartons jaunes réels"))
    org.junit.Assert.assertTrue(catTxt.contains("EXPORT CATÉGORIE : Qualifications CAN Test"))

    val catFile = ArchiveReportExporter.suggestedFileName(null, "Qualifications CAN Test", ArchiveExportFormat.MARKDOWN_REPORT)
    org.junit.Assert.assertTrue(catFile.startsWith("al_raml_cat_qualifications_can_test_"))
    org.junit.Assert.assertTrue(catFile.endsWith(".md"))
  }

  private fun fixedCalendar(
    dayOfWeek: Int = java.util.Calendar.THURSDAY,
    hour: Int = 9,
    minute: Int = 30,
  ): java.util.Calendar =
    java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.DAY_OF_WEEK, dayOfWeek)
      set(java.util.Calendar.HOUR_OF_DAY, hour)
      set(java.util.Calendar.MINUTE, minute)
    }

  @Test
  fun viewModelAutoSavesCastWithoutDuplicatingOnSubsequentEdit() = runBlocking {
    val vm =
      com.example.ui.GeomancyViewModel(
        repository = repository,
        calendarProvider = { fixedCalendar(java.util.Calendar.THURSDAY, 9, 30) },
        lunarDayProvider = { 10 },
      )
    vm.updateDomain(QuestionDomain.CONTEST)
    vm.updateTeamAName("Sénégal")
    vm.updateTeamBName("Maroc")
    vm.updateSelectedCategoryInput("CAN 2027")

    val reading = vm.castSpontaneous()
    org.junit.Assert.assertNotNull(reading)
    // Attendre que la coroutine d'auto-enregistrement termine
    var attempts = 0
    while (repository.savedThemes.first().isEmpty() && attempts < 40) {
      org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()
      kotlinx.coroutines.delay(25)
      attempts++
    }
    org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()

    val afterAutoSave = repository.savedThemes.first()
    assertEquals(1, afterAutoSave.size)
    val savedId = afterAutoSave.first().id
    assertEquals("CAN 2027", afterAutoSave.first().leagueCategory)
    org.junit.Assert.assertTrue(afterAutoSave.first().title.contains("Sénégal vs Maroc"))

    // Sauvegarder des résultats réels après le tirage auto-enregistré ne doit PAS créer de doublon
    vm.saveCurrentReading(
      title = "Sénégal vs Maroc (Finale)",
      contextNotes = "Score observé 2-1",
      actualOutcome = com.example.geomancy.MatchOutcome.CAMP_A_WIN,
      actualScoreText = "2-1",
      leagueCategory = "CAN 2027",
      actualYellowCardsText = "5",
      actualRedCardsText = "1",
    )
    attempts = 0
    while (repository.savedThemes.first().first().actualScoreText != "2-1" && attempts < 40) {
      org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()
      kotlinx.coroutines.delay(25)
      attempts++
    }
    org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()
    val afterEdit = repository.savedThemes.first()
    assertEquals(1, afterEdit.size)
    assertEquals(savedId, afterEdit.first().id)
    assertEquals("5", afterEdit.first().actualYellowCardsText)
    assertEquals("1", afterEdit.first().actualRedCardsText)
  }

  @Test
  fun viewModelBlocksSandTappingDuringForbiddenTimeByDefaultAndSupportsToggle() = runBlocking {
    var currentCal = fixedCalendar(java.util.Calendar.THURSDAY, 12, 5) // Zawāl solaire (interdit)
    var currentLunarDay = 10

    val vm =
      com.example.ui.GeomancyViewModel(
        repository = repository,
        calendarProvider = { currentCal },
        lunarDayProvider = { currentLunarDay },
      )

    // Par défaut, disableRamlAtForbiddenTimes = true et disableRamlAtDiscouragedTimes = false
    org.junit.Assert.assertTrue(vm.uiState.value.disableRamlAtForbiddenTimes)
    org.junit.Assert.assertFalse(vm.uiState.value.disableRamlAtDiscouragedTimes)
    org.junit.Assert.assertTrue(vm.uiState.value.ramlTimeAssessment.isForbidden)
    org.junit.Assert.assertTrue(vm.uiState.value.isSandTappingBlocked)

    // Les actions de tapé et de tirage doivent être bloquées
    vm.strikeSandAt(0.4f, 0.5f)
    assertEquals(0, vm.uiState.value.sandLineCounts[0])
    vm.autoCompleteRemainingSandLines()
    assertEquals(0, vm.uiState.value.completedSandLines)
    org.junit.Assert.assertNull(vm.castSpontaneous())
    org.junit.Assert.assertNull(vm.erectTakhtFromCurrentMode())

    // L'utilisateur peut désactiver le blocage des temps interdits
    vm.updateDisableRamlAtForbiddenTimes(false)
    org.junit.Assert.assertFalse(vm.uiState.value.isSandTappingBlocked)
    vm.strikeSandAt(0.4f, 0.5f)
    assertEquals(1, vm.uiState.value.sandLineCounts[0])
    org.junit.Assert.assertNotNull(vm.castSpontaneous())

    // Réactiver pour les temps interdits, puis basculer sur un créneau déconseillé (Mars un Jeudi)
    vm.updateDisableRamlAtForbiddenTimes(true)
    currentCal = fixedCalendar(java.util.Calendar.THURSDAY, 9, 30)
    currentLunarDay = 10
    vm.updatePlanetHour(Planet.MARS)
    org.junit.Assert.assertTrue(vm.uiState.value.ramlTimeAssessment.isDiscouraged)
    org.junit.Assert.assertFalse(vm.uiState.value.isSandTappingBlocked)

    // Activer le blocage pour les temps et heures déconseillés
    vm.updateDisableRamlAtDiscouragedTimes(true)
    org.junit.Assert.assertTrue(vm.uiState.value.isSandTappingBlocked)
    val countBeforeBlockedTap = vm.uiState.value.sandLineCounts[0]
    vm.strikeSandAt(0.5f, 0.5f)
    assertEquals(countBeforeBlockedTap, vm.uiState.value.sandLineCounts[0])
    org.junit.Assert.assertNull(vm.castSpontaneous())

    // Désactiver à nouveau débloque immédiatement
    vm.updateDisableRamlAtDiscouragedTimes(false)
    org.junit.Assert.assertFalse(vm.uiState.value.isSandTappingBlocked)
  }

  @Test
  fun updatePlanetHourPreservesAnchorAndNonContestEditPreservesDomainCategory() = runBlocking {
    val vm =
      com.example.ui.GeomancyViewModel(
        repository = repository,
        calendarProvider = { fixedCalendar(java.util.Calendar.THURSDAY, 9, 30) },
        lunarDayProvider = { 10 },
      )
    vm.updateDomain(QuestionDomain.HEALTH)
    vm.updateConsultantFirstName("Moussa")
    vm.updateMotherFirstName("Aminata")
    vm.updateQuestionText("Rétablissement rapide")
    vm.updateSelectedCategoryInput("Santé Familiale")

    val reading = vm.castSpontaneous()!!
    org.junit.Assert.assertTrue(reading.questionText.contains("Moussa"))
    org.junit.Assert.assertTrue(reading.questionText.contains("Aminata"))

    // Changer d'heure planétaire ne doit pas perdre le nom du consultant et de sa mère
    vm.updatePlanetHour(Planet.SUN)
    val refreshed = vm.uiState.value.currentReading!!
    org.junit.Assert.assertTrue(refreshed.questionText.contains("Moussa"))
    org.junit.Assert.assertTrue(refreshed.questionText.contains("Aminata"))

    var attempts = 0
    while (repository.savedThemes.first().isEmpty() && attempts < 40) {
      org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()
      kotlinx.coroutines.delay(25)
      attempts++
    }
    val saved = repository.savedThemes.first().first()
    assertEquals("Santé Familiale", saved.leagueCategory)

    // Éditer le thème Santé sans changer de catégorie ne doit pas basculer sur "Duel Libre"
    vm.updateSavedThemeContext(
      id = saved.id,
      title = "Suivi Moussa",
      notes = "Amélioration constatée",
      leagueCategory = "",
    )
    attempts = 0
    while (repository.savedThemes.first().first().title != "Suivi Moussa" && attempts < 40) {
      org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()
      kotlinx.coroutines.delay(25)
      attempts++
    }
    org.robolectric.Shadows.shadowOf(android.os.Looper.getMainLooper()).idle()
    val updated = repository.savedThemes.first().first()
    assertEquals("Santé Familiale", updated.leagueCategory)

    // Recharger le thème doit restaurer selectedCategoryInput et vider les grains visuels
    vm.updateSelectedCategoryInput("Autre brouillon")
    vm.loadSavedTheme(updated)
    assertEquals("Santé Familiale", vm.uiState.value.selectedCategoryInput)
    org.junit.Assert.assertTrue(vm.uiState.value.sandGrains.isEmpty())
  }
}
