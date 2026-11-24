package com.example

import android.content.Context
import android.os.Looper
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.AlRamlDatabase
import com.example.data.FigureEncyclopediaDatabase
import com.example.data.FigureEncyclopediaRepository
import com.example.data.SavedThemeRepository
import com.example.geomancy.Element
import com.example.geomancy.QuestionDomain
import com.example.ui.GeomancyViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class FigureEncyclopediaRepositoryTest {

  private lateinit var archivesDb: AlRamlDatabase
  private lateinit var encyclopediaDb: FigureEncyclopediaDatabase
  private lateinit var themeRepository: SavedThemeRepository
  private lateinit var encyclopediaRepository: FigureEncyclopediaRepository

  @Before
  fun setUp() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    archivesDb =
      Room.inMemoryDatabaseBuilder(context, AlRamlDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    encyclopediaDb =
      Room.inMemoryDatabaseBuilder(context, FigureEncyclopediaDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    themeRepository = SavedThemeRepository(archivesDb.savedThemeDao(), archivesDb.calibrationDao())
    encyclopediaRepository = FigureEncyclopediaRepository(encyclopediaDb.geomanticFigureDao())
  }

  @After
  fun tearDown() {
    encyclopediaDb.close()
    archivesDb.close()
  }

  @Test
  fun encyclopediaSeedsAll16FiguresWithMeaningsAndAssociationsIdempotently() = runBlocking {
    encyclopediaRepository.ensureSeeded()
    val all = encyclopediaRepository.allFigures.first()
    assertEquals(16, all.size)

    // Ordre ouest-africain #1 = Djanfa Almami
    val first = all.first()
    assertEquals("Djanfa Almami", first.westAfricanName)
    assertTrue(first.arabicName.isNotBlank())
    assertTrue(first.traditionalMeaning.isNotBlank())
    assertTrue(first.associationsSummary.contains("Planète"))
    assertTrue(first.associationsSummary.contains("Zodiaque"))

    // Marquer une figure favorite et vérifier l'idempotence du seed
    encyclopediaRepository.toggleFavorite(first.id, true)
    encyclopediaRepository.ensureSeeded()
    val reloaded = encyclopediaRepository.getById(first.id)!!
    assertTrue(reloaded.isFavorite)
  }

  @Test
  fun encyclopediaSqlQueryFiltersByNameMeaningAssociationElementAndFavorite() = runBlocking {
    encyclopediaRepository.ensureSeeded()

    val byPlanet = encyclopediaRepository.observeFigures(query = "Jupiter").first()
    assertTrue(byPlanet.any { it.westAfricanName == "Adama" })
    assertTrue(byPlanet.any { it.westAfricanName == "Ousmana" })

    val byFire = encyclopediaRepository.observeFigures(query = "", element = Element.FIRE).first()
    assertEquals(4, byFire.size)
    assertTrue(byFire.all { it.dominantElementName == Element.FIRE.name })

    val adamaId = byPlanet.first { it.westAfricanName == "Adama" }.id
    encyclopediaRepository.toggleFavorite(adamaId, true)

    val favs = encyclopediaRepository.observeFigures(query = "", favoritesOnly = true).first()
    assertEquals(1, favs.size)
    assertEquals("Adama", favs.first().westAfricanName)
  }

  @Test
  fun viewModelPersistsFull16HouseTakhtAndExposesRoomEncyclopedia() = runBlocking {
    val favorableCalendar =
      java.util.Calendar.getInstance().apply {
        set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.THURSDAY)
        set(java.util.Calendar.HOUR_OF_DAY, 9)
        set(java.util.Calendar.MINUTE, 30)
      }
    val vm =
      GeomancyViewModel(
        repository = themeRepository,
        figureEncyclopediaRepository = encyclopediaRepository,
        calendarProvider = { favorableCalendar },
        lunarDayProvider = { 10 },
      )
    vm.updateDomain(QuestionDomain.TRAVEL)
    vm.updateQuestionText("Voyage d'affaires à Dakar")
    assertTrue(vm.castSpontaneous() != null)

    var attempts = 0
    while (themeRepository.savedThemes.first().isEmpty() && attempts < 40) {
      Shadows.shadowOf(Looper.getMainLooper()).idle()
      kotlinx.coroutines.delay(25)
      attempts++
    }
    Shadows.shadowOf(Looper.getMainLooper()).idle()

    val saved = themeRepository.savedThemes.first()
    assertEquals(1, saved.size)
    val stored = saved.first()
    assertTrue(stored.title.contains("Voyage"))
    assertTrue(stored.createdAtMillis > 0L)
    val houseIds = stored.takhtHouseIdsCsv.split(",").mapNotNull { it.trim().toIntOrNull() }
    assertEquals(16, houseIds.size)

    // Vérifier que l'encyclopédie Room a été peuplée par le ViewModel
    var encAttempts = 0
    while (encyclopediaRepository.allFigures.first().size < 16 && encAttempts < 40) {
      Shadows.shadowOf(Looper.getMainLooper()).idle()
      kotlinx.coroutines.delay(25)
      encAttempts++
    }
    Shadows.shadowOf(Looper.getMainLooper()).idle()
    val encAll = encyclopediaRepository.allFigures.first()
    assertEquals(16, encAll.size)
    assertFalse(encAll.any { it.traditionalMeaning.isBlank() })
  }
}
