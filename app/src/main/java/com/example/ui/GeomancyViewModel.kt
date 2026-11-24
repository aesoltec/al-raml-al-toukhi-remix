package com.example.ui

import android.icu.util.IslamicCalendar
import android.icu.util.ULocale
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.ArchiveExportFormat
import com.example.data.ArchiveReportExporter
import com.example.data.CalibratedWeightsEntity
import com.example.data.FigureEncyclopediaRepository
import com.example.data.GeomanticFigureEntity
import com.example.data.SavedThemeEntity
import com.example.data.SavedThemeRepository
import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.AlToukhiGeomancyEngine
import com.example.geomancy.ArchiveFrequencyReport
import com.example.geomancy.ArchiveRecurrenceReport
import com.example.geomancy.CalibrationReport
import com.example.geomancy.CourseTakhtExample
import com.example.geomancy.Element
import com.example.geomancy.FigureWeightTable
import com.example.geomancy.FortunePolarity
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.MatchOutcome
import com.example.geomancy.MatchOutcomeParser
import com.example.geomancy.Planet
import com.example.geomancy.PointParity
import com.example.geomancy.PredictiveCalibrationEngine
import com.example.geomancy.QuestionDomain
import com.example.geomancy.RamlTimeAssessment
import com.example.geomancy.RamlTimingAdvisor
import com.example.geomancy.TakhtReading
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Calendar
import kotlin.random.Random

enum class CastingMode {
  STRIKE_SAND,
  DIRECT_MOTHERS,
}

enum class IntentionAnchorType(
  val label: String,
  val shortLabel: String,
  val description: String,
) {
  TEAMS_DUEL(
    label = "Deux Équipes (Duel)",
    shortLabel = "Équipes",
    description = "Saisis les noms des deux équipes (Camp A vs Camp B) avant de frapper le sable.",
  ),
  PERSON_AND_MOTHER(
    label = "Consultant & sa Mère",
    shortLabel = "Prénom + Mère",
    description = "Ancrage traditionnel (Fulān ibn Fulāna) : prénom du consultant et de sa mère.",
  ),
  ORGANIZATION(
    label = "Institut / Entreprise / Parti",
    shortLabel = "Organisation",
    description = "Nom de l’institut, entreprise, organisation ou parti politique visé.",
  ),
  ANONYMOUS(
    label = "Anonyme (Silencieux)",
    shortLabel = "Anonyme",
    description = "Consultation anonyme centrée uniquement sur l’objet ou le but.",
  ),
}

enum class OrganizationKind(val label: String) {
  INSTITUTE("Institut"),
  COMPANY("Entreprise"),
  ORGANIZATION("Organisation"),
  POLITICAL_PARTY("Parti politique"),
  OTHER("Autre structure"),
}

@Immutable
data class SandGrain(
  val xFraction: Float,
  val yFraction: Float,
  val lineIndex: Int,
)

@Immutable
data class GeomancyUiState(
  val selectedDomain: QuestionDomain = QuestionDomain.GENERAL,
  val intentionAnchorType: IntentionAnchorType = IntentionAnchorType.PERSON_AND_MOTHER,
  val hasManualAnchorSelection: Boolean = false,
  val teamAName: String = "",
  val teamBName: String = "",
  val consultantFirstName: String = "",
  val motherFirstName: String = "",
  val organizationName: String = "",
  val organizationKind: OrganizationKind = OrganizationKind.COMPANY,
  val questionText: String = "",
  val activePlanetHour: Planet = Planet.JUPITER,
  val castingMode: CastingMode = CastingMode.STRIKE_SAND,
  val sandLineCounts: List<Int> = List(16) { 0 },
  val activeSandLineIndex: Int = 0,
  val sandGrains: List<SandGrain> = emptyList(),
  val selectedMothers: List<GeomanticFigure> =
    listOf(
      AlToukhiFigures.findById(1),
      AlToukhiFigures.findById(2),
      AlToukhiFigures.findById(11),
      AlToukhiFigures.findById(16),
    ),
  val currentReading: TakhtReading? = null,
  val selectedCategoryInput: String = "",
  val optionExactScore: Boolean = true,
  val optionYellowCards: Boolean = true,
  val optionRedCards: Boolean = true,
  val autoSaveEnabled: Boolean = true,
  val activeSavedThemeId: Long? = null,
  val activeSavedTitle: String = "",
  val activeContextNotes: String = "",
  val activeActualOutcome: MatchOutcome? = null,
  val activeActualScore: String = "",
  val activeActualYellowCards: String = "",
  val activeActualRedCards: String = "",
  val activeLeagueCategory: String = "",
  val inspectedHouseNumber: Int = 15,
  val figureSearchQuery: String = "",
  val figureElementFilter: Element? = null,
  val figurePolarityFilter: FortunePolarity? = null,
  val figureFavoritesOnlyFilter: Boolean = false,
  val disableRamlAtForbiddenTimes: Boolean = true,
  val disableRamlAtDiscouragedTimes: Boolean = false,
  val ramlTimeAssessment: RamlTimeAssessment = RamlTimeAssessment.favorableDefault(),
  val selectedCourseBookId: String = GeomancyCoursesCatalog.allBooks.first().id,
  val completedCourseModuleIds: Set<String> = emptySet(),
  val selectedGuideTab: Int = 0,
) {
  val isSandTappingBlocked: Boolean
    get() =
      (ramlTimeAssessment.isForbidden && disableRamlAtForbiddenTimes) ||
        (ramlTimeAssessment.isDiscouraged && disableRamlAtDiscouragedTimes)

  val completedSandLines: Int
    get() = sandLineCounts.count { it > 0 }

  val isSandCastingComplete: Boolean
    get() = sandLineCounts.size == 16 && sandLineCounts.all { it > 0 }

  fun revealedMotherFigures(): List<GeomanticFigure>? {
    if (!isSandCastingComplete) return null
    return (0 until 4).map { motherIdx ->
      val base = motherIdx * 4
      AlToukhiFigures.findByRanks(
        fire = if (sandLineCounts[base] % 2 == 1) PointParity.ODD else PointParity.EVEN,
        air = if (sandLineCounts[base + 1] % 2 == 1) PointParity.ODD else PointParity.EVEN,
        water = if (sandLineCounts[base + 2] % 2 == 1) PointParity.ODD else PointParity.EVEN,
        earth = if (sandLineCounts[base + 3] % 2 == 1) PointParity.ODD else PointParity.EVEN,
      )
    }
  }

  val anchoredSubjectSummary: String
    get() =
      when (intentionAnchorType) {
        IntentionAnchorType.TEAMS_DUEL -> {
          val a = teamAName.trim()
          val b = teamBName.trim()
          when {
            a.isNotEmpty() && b.isNotEmpty() -> "$a vs $b"
            a.isNotEmpty() -> "$a vs Camp B"
            b.isNotEmpty() -> "Camp A vs $b"
            else -> ""
          }
        }
        IntentionAnchorType.PERSON_AND_MOTHER -> {
          val p = consultantFirstName.trim()
          val m = motherFirstName.trim()
          when {
            p.isNotEmpty() && m.isNotEmpty() -> "$p (fils/fille de $m)"
            p.isNotEmpty() -> p
            m.isNotEmpty() -> "Mère : $m"
            else -> ""
          }
        }
        IntentionAnchorType.ORGANIZATION -> {
          val org = organizationName.trim()
          if (org.isNotEmpty()) "${organizationKind.label} : $org" else ""
        }
        IntentionAnchorType.ANONYMOUS -> "Consultation anonyme"
      }

  val isIntentionAnchored: Boolean
    get() =
      when (intentionAnchorType) {
        IntentionAnchorType.TEAMS_DUEL -> teamAName.isNotBlank() && teamBName.isNotBlank()
        IntentionAnchorType.PERSON_AND_MOTHER ->
          consultantFirstName.isNotBlank() || motherFirstName.isNotBlank()
        IntentionAnchorType.ORGANIZATION -> organizationName.isNotBlank()
        IntentionAnchorType.ANONYMOUS -> true
      }

  fun composedIntentionText(): String {
    val subject = anchoredSubjectSummary.trim()
    val note = questionText.trim()
    return when {
      subject.isNotEmpty() && note.isNotEmpty() ->
        if (note.contains(subject, ignoreCase = true)) note else "$subject — $note"
      subject.isNotEmpty() -> subject
      else -> note
    }
  }
}

private fun defaultHijriLunarDay(): Int =
  runCatching {
    IslamicCalendar(ULocale("@calendar=islamic-umalqura")).get(IslamicCalendar.DAY_OF_MONTH)
  }.getOrDefault(10)

class GeomancyViewModel(
  private val repository: SavedThemeRepository,
  private val figureEncyclopediaRepository: FigureEncyclopediaRepository? = null,
  private val calendarProvider: () -> Calendar = { Calendar.getInstance() },
  private val lunarDayProvider: () -> Int = { defaultHijriLunarDay() },
) : ViewModel() {

  private val _uiState = MutableStateFlow(GeomancyUiState())
  val uiState: StateFlow<GeomancyUiState> = _uiState.asStateFlow()

  private val saveMutex = Mutex()
  private var castSequence: Long = 0L

  val savedThemes: StateFlow<List<SavedThemeEntity>> =
    repository.savedThemes.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = emptyList(),
    )

  val calibratedWeightsConfig: StateFlow<CalibratedWeightsEntity?> =
    repository.calibratedWeightsConfig.stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5000),
      initialValue = null,
    )

  val isCalibrationEnabled: StateFlow<Boolean> =
    calibratedWeightsConfig
      .map { it?.isEnabled == true }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false,
      )

  val activeWeightTable: StateFlow<FigureWeightTable> =
    calibratedWeightsConfig
      .map { config -> repository.decodeWeightTable(config) }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FigureWeightTable.default(),
      )

  val calibrationReport: StateFlow<CalibrationReport> =
    savedThemes
      .map { entities ->
        PredictiveCalibrationEngine.evaluateAndCalibrate(
          repository.extractCalibrationSamples(entities),
        )
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PredictiveCalibrationEngine.evaluateAndCalibrate(emptyList()),
      )

  val archiveFrequencyReport: StateFlow<ArchiveFrequencyReport> =
    savedThemes
      .map { entities ->
        val readings = entities.map { entity -> repository.reconstructReading(entity) }
        PredictiveCalibrationEngine.computeArchiveFrequencies(readings)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PredictiveCalibrationEngine.computeArchiveFrequencies(emptyList()),
      )

  val archiveRecurrenceReport: StateFlow<ArchiveRecurrenceReport> =
    savedThemes
      .map { entities -> repository.computeArchiveRecurrences(entities) }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PredictiveCalibrationEngine.computeThemeRecurrences(emptyList()),
      )

  private data class EncyclopediaQueryParams(
    val query: String,
    val element: Element?,
    val polarity: FortunePolarity?,
    val favoritesOnly: Boolean,
  )

  @OptIn(ExperimentalCoroutinesApi::class)
  val encyclopediaFigures: StateFlow<List<GeomanticFigureEntity>> =
    _uiState
      .map { state ->
        EncyclopediaQueryParams(
          query = state.figureSearchQuery,
          element = state.figureElementFilter,
          polarity = state.figurePolarityFilter,
          favoritesOnly = state.figureFavoritesOnlyFilter,
        )
      }
      .distinctUntilChanged()
      .flatMapLatest { params ->
        if (figureEncyclopediaRepository != null) {
          figureEncyclopediaRepository.observeFigures(
            query = params.query,
            element = params.element,
            polarity = params.polarity,
            favoritesOnly = params.favoritesOnly,
          )
        } else {
          val q = params.query.trim().lowercase()
          val filtered =
            FigureEncyclopediaRepository.buildSeedEntities().filter { entity ->
              val matchesQuery =
                q.isEmpty() ||
                  entity.westAfricanName.lowercase().contains(q) ||
                  entity.arabicName.contains(q) ||
                  entity.transliteration.lowercase().contains(q) ||
                  entity.traditionalTitle.lowercase().contains(q) ||
                  entity.traditionalMeaning.lowercase().contains(q) ||
                  entity.associationsSummary.lowercase().contains(q) ||
                  entity.code.contains(q)
              val matchesElement = params.element == null || entity.dominantElementName == params.element.name
              val matchesPolarity = params.polarity == null || entity.polarityName == params.polarity.name
              matchesQuery && matchesElement && matchesPolarity && !params.favoritesOnly
            }
          flowOf(filtered)
        }
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList(),
      )

  init {
    refreshRamlTiming()
    viewModelScope.launch {
      figureEncyclopediaRepository?.ensureSeeded()
    }
    viewModelScope.launch {
      activeWeightTable.collect { table ->
        _uiState.update { state ->
          val existing = state.currentReading ?: return@update state
          val refreshed =
            AlToukhiGeomancyEngine.castFromDotCounts(
              sixteenCounts = existing.rawMotherDotCounts,
              domain = existing.domain,
              questionText = existing.questionText,
              activePlanetHour = existing.activePlanetHour,
              weightTable = table,
            )
          state.copy(currentReading = refreshed)
        }
      }
    }
  }

  fun updateDomain(domain: QuestionDomain) {
    val weightTable = activeWeightTable.value
    _uiState.update { state ->
      val suggestedAnchor =
        if (state.hasManualAnchorSelection) {
          state.intentionAnchorType
        } else {
          when (domain) {
            QuestionDomain.CONTEST -> IntentionAnchorType.TEAMS_DUEL
            QuestionDomain.AUTHORITY -> IntentionAnchorType.ORGANIZATION
            else -> IntentionAnchorType.PERSON_AND_MOTHER
          }
        }
      val nextState = state.copy(selectedDomain = domain, intentionAnchorType = suggestedAnchor)
      val composedQuestion = nextState.composedIntentionText()
      val refreshedReading =
        state.currentReading?.let { existing ->
          AlToukhiGeomancyEngine.castFromDotCounts(
            sixteenCounts = existing.rawMotherDotCounts,
            domain = domain,
            questionText = composedQuestion,
            activePlanetHour = state.activePlanetHour,
            weightTable = weightTable,
          )
        }
      nextState.copy(currentReading = refreshedReading)
    }
  }

  fun updateIntentionAnchorType(type: IntentionAnchorType) {
    _uiState.update { it.copy(intentionAnchorType = type, hasManualAnchorSelection = true) }
  }

  fun updateTeamAName(name: String) {
    _uiState.update { it.copy(teamAName = name) }
  }

  fun updateTeamBName(name: String) {
    _uiState.update { it.copy(teamBName = name) }
  }

  fun updateConsultantFirstName(name: String) {
    _uiState.update { it.copy(consultantFirstName = name) }
  }

  fun updateMotherFirstName(name: String) {
    _uiState.update { it.copy(motherFirstName = name) }
  }

  fun updateOrganizationName(name: String) {
    _uiState.update { it.copy(organizationName = name) }
  }

  fun updateOrganizationKind(kind: OrganizationKind) {
    _uiState.update { it.copy(organizationKind = kind) }
  }

  fun updateQuestionText(text: String) {
    _uiState.update { it.copy(questionText = text) }
  }

  fun updateSelectedCategoryInput(category: String) {
    _uiState.update { it.copy(selectedCategoryInput = category) }
  }

  fun updateAutoSaveEnabled(enabled: Boolean) {
    _uiState.update { it.copy(autoSaveEnabled = enabled) }
  }

  fun toggleOptionExactScore() {
    var nextFlag = true
    var activeId: Long? = null
    var yellow = true
    var red = true
    _uiState.update { state ->
      nextFlag = !state.optionExactScore
      activeId = state.activeSavedThemeId
      yellow = state.optionYellowCards
      red = state.optionRedCards
      state.copy(optionExactScore = nextFlag)
    }
    activeId?.let { id ->
      viewModelScope.launch {
        saveMutex.withLock {
          repository.updatePrecisionOptions(id, nextFlag, yellow, red)
        }
      }
    }
  }

  fun toggleOptionYellowCards() {
    var nextFlag = true
    var activeId: Long? = null
    var exact = true
    var red = true
    _uiState.update { state ->
      nextFlag = !state.optionYellowCards
      activeId = state.activeSavedThemeId
      exact = state.optionExactScore
      red = state.optionRedCards
      state.copy(optionYellowCards = nextFlag)
    }
    activeId?.let { id ->
      viewModelScope.launch {
        saveMutex.withLock {
          repository.updatePrecisionOptions(id, exact, nextFlag, red)
        }
      }
    }
  }

  fun toggleOptionRedCards() {
    var nextFlag = true
    var activeId: Long? = null
    var exact = true
    var yellow = true
    _uiState.update { state ->
      nextFlag = !state.optionRedCards
      activeId = state.activeSavedThemeId
      exact = state.optionExactScore
      yellow = state.optionYellowCards
      state.copy(optionRedCards = nextFlag)
    }
    activeId?.let { id ->
      viewModelScope.launch {
        saveMutex.withLock {
          repository.updatePrecisionOptions(id, exact, yellow, nextFlag)
        }
      }
    }
  }

  fun refreshRamlTiming() {
    val cal = calendarProvider()
    val lunarDay = lunarDayProvider()
    _uiState.update { state ->
      val assessment =
        RamlTimingAdvisor.evaluateFromCalendar(
          activePlanetHour = state.activePlanetHour,
          calendar = cal,
          lunarDay = lunarDay,
        )
      state.copy(ramlTimeAssessment = assessment)
    }
  }

  fun updateDisableRamlAtForbiddenTimes(enabled: Boolean) {
    _uiState.update { it.copy(disableRamlAtForbiddenTimes = enabled) }
  }

  fun updateDisableRamlAtDiscouragedTimes(enabled: Boolean) {
    _uiState.update { it.copy(disableRamlAtDiscouragedTimes = enabled) }
  }

  fun updatePlanetHour(planet: Planet) {
    val weightTable = activeWeightTable.value
    val cal = calendarProvider()
    val lunarDay = lunarDayProvider()
    _uiState.update { state ->
      val refreshedReading =
        state.currentReading?.let { existing ->
          AlToukhiGeomancyEngine.castFromDotCounts(
            sixteenCounts = existing.rawMotherDotCounts,
            domain = state.selectedDomain,
            questionText = state.composedIntentionText(),
            activePlanetHour = planet,
            weightTable = weightTable,
          )
        }
      val assessment =
        RamlTimingAdvisor.evaluateFromCalendar(
          activePlanetHour = planet,
          calendar = cal,
          lunarDay = lunarDay,
        )
      state.copy(
        activePlanetHour = planet,
        currentReading = refreshedReading,
        ramlTimeAssessment = assessment,
      )
    }
  }

  fun updateCastingMode(mode: CastingMode) {
    _uiState.update { it.copy(castingMode = mode) }
  }

  fun strikeSandAt(xFraction: Float, yFraction: Float) {
    _uiState.update { state ->
      if (state.isSandTappingBlocked) return@update state
      val lineIdx = state.activeSandLineIndex.coerceIn(0, 15)
      val updatedCounts = state.sandLineCounts.toMutableList()
      updatedCounts[lineIdx] = updatedCounts[lineIdx] + 1
      val updatedGrains =
        (state.sandGrains + SandGrain(xFraction, yFraction, lineIdx)).takeLast(140)
      state.copy(sandLineCounts = updatedCounts, sandGrains = updatedGrains)
    }
  }

  fun advanceSandLine() {
    _uiState.update { state ->
      val current = state.activeSandLineIndex.coerceIn(0, 15)
      if (state.sandLineCounts[current] <= 0) return@update state
      val nextEmpty =
        ((current + 1)..15).firstOrNull { state.sandLineCounts[it] == 0 }
          ?: (0 until current).firstOrNull { state.sandLineCounts[it] == 0 }
      val nextIndex = nextEmpty ?: if (current < 15) current + 1 else 15
      state.copy(activeSandLineIndex = nextIndex)
    }
  }

  fun selectSandLine(index: Int) {
    _uiState.update { it.copy(activeSandLineIndex = index.coerceIn(0, 15)) }
  }

  fun autoCompleteRemainingSandLines() {
    _uiState.update { state ->
      if (state.isSandTappingBlocked) return@update state
      val updatedCounts =
        state.sandLineCounts.map { count -> if (count > 0) count else Random.nextInt(5, 19) }
      val newGrains =
        if (state.sandGrains.isEmpty()) {
          List(32) { idx ->
            SandGrain(
              xFraction = Random.nextFloat().coerceIn(0.08f, 0.92f),
              yFraction = Random.nextFloat().coerceIn(0.12f, 0.88f),
              lineIndex = idx % 16,
            )
          }
        } else {
          state.sandGrains
        }
      state.copy(
        sandLineCounts = updatedCounts,
        activeSandLineIndex = 15,
        sandGrains = newGrains,
      )
    }
  }

  fun resetSand() {
    _uiState.update {
      it.copy(
        sandLineCounts = List(16) { 0 },
        activeSandLineIndex = 0,
        sandGrains = emptyList(),
      )
    }
  }

  fun toggleMotherRank(motherIndex: Int, rankIndex: Int) {
    _uiState.update { state ->
      val updated = state.selectedMothers.toMutableList()
      val fig = updated[motherIndex]
      val newRanks =
        fig.ranks.mapIndexed { idx, parity ->
          if (idx == rankIndex) {
            if (parity == PointParity.ODD) PointParity.EVEN else PointParity.ODD
          } else {
            parity
          }
        }
      updated[motherIndex] =
        AlToukhiFigures.findByRanks(newRanks[0], newRanks[1], newRanks[2], newRanks[3])
      state.copy(selectedMothers = updated)
    }
  }

  fun setMotherFigure(motherIndex: Int, figure: GeomanticFigure) {
    _uiState.update { state ->
      val updated = state.selectedMothers.toMutableList()
      updated[motherIndex] = figure
      state.copy(selectedMothers = updated)
    }
  }

  private fun resolveCastAutoTitle(reading: TakhtReading, composedQuestion: String): String =
    composedQuestion.trim().ifEmpty {
      "${reading.domain.title} · Juge ${reading.house(15).figure.westAfricanName}"
    }

  private fun resolveCastAutoCategory(
    state: GeomancyUiState,
    autoTitle: String,
    composedQuestion: String,
  ): String =
    if (state.selectedDomain == QuestionDomain.CONTEST) {
      MatchOutcomeParser.inferLeague(
        explicitLeague = state.selectedCategoryInput,
        title = autoTitle,
        notes = composedQuestion,
      )
    } else {
      state.selectedCategoryInput.trim().ifEmpty { state.selectedDomain.title }
    }

  private fun scheduleAutoSaveForCast(
    reading: TakhtReading,
    autoTitle: String,
    autoCategory: String,
    stateSnapshot: GeomancyUiState,
    sequenceId: Long,
  ) {
    if (!stateSnapshot.autoSaveEnabled) return
    viewModelScope.launch {
      saveMutex.withLock {
        if (castSequence != sequenceId) return@withLock
        val currentId = _uiState.value.activeSavedThemeId
        val insertedId =
          repository.saveReading(
            reading = reading,
            title = autoTitle,
            contextOutcomeNotes = _uiState.value.activeContextNotes,
            existingId = currentId,
            actualOutcome = _uiState.value.activeActualOutcome,
            actualScoreText = _uiState.value.activeActualScore,
            leagueCategory = autoCategory,
            actualYellowCardsText = _uiState.value.activeActualYellowCards,
            actualRedCardsText = _uiState.value.activeActualRedCards,
            includeExactScoreOption = _uiState.value.optionExactScore,
            includeYellowCardsOption = _uiState.value.optionYellowCards,
            includeRedCardsOption = _uiState.value.optionRedCards,
          )
        if (castSequence == sequenceId) {
          _uiState.update { latest ->
            latest.copy(
              activeSavedThemeId = insertedId,
              activeSavedTitle = latest.activeSavedTitle.ifBlank { autoTitle },
              activeLeagueCategory = latest.activeLeagueCategory.ifBlank { autoCategory },
            )
          }
        }
      }
    }
  }

  fun erectTakhtFromCurrentMode(): TakhtReading? {
    val state = _uiState.value
    if (state.isSandTappingBlocked) {
      return null
    }
    if (state.castingMode == CastingMode.STRIKE_SAND && !state.isSandCastingComplete) {
      return null
    }
    val weightTable = activeWeightTable.value
    val composedQuestion = state.composedIntentionText()
    val reading =
      when (state.castingMode) {
        CastingMode.STRIKE_SAND -> {
          AlToukhiGeomancyEngine.castFromDotCounts(
            sixteenCounts = state.sandLineCounts,
            domain = state.selectedDomain,
            questionText = composedQuestion,
            activePlanetHour = state.activePlanetHour,
            weightTable = weightTable,
          )
        }
        CastingMode.DIRECT_MOTHERS -> {
          AlToukhiGeomancyEngine.castFromMothers(
            mothers = state.selectedMothers,
            domain = state.selectedDomain,
            questionText = composedQuestion,
            activePlanetHour = state.activePlanetHour,
            weightTable = weightTable,
          )
        }
      }
    val autoTitle = resolveCastAutoTitle(reading, composedQuestion)
    val autoCategory = resolveCastAutoCategory(state, autoTitle, composedQuestion)
    val seq = ++castSequence
    _uiState.update {
      it.copy(
        currentReading = reading,
        activeSavedThemeId = null,
        activeSavedTitle = autoTitle,
        activeContextNotes = "",
        activeActualOutcome = null,
        activeActualScore = "",
        activeActualYellowCards = "",
        activeActualRedCards = "",
        activeLeagueCategory = autoCategory,
        inspectedHouseNumber = 15,
        sandLineCounts = reading.rawMotherDotCounts,
      )
    }
    scheduleAutoSaveForCast(
      reading = reading,
      autoTitle = autoTitle,
      autoCategory = autoCategory,
      stateSnapshot = state,
      sequenceId = seq,
    )
    return reading
  }

  fun castSpontaneous(): TakhtReading? {
    val state = _uiState.value
    if (state.isSandTappingBlocked) {
      return null
    }
    val weightTable = activeWeightTable.value
    val composedQuestion = state.composedIntentionText()
    val reading =
      AlToukhiGeomancyEngine.castSpontaneous(
        domain = state.selectedDomain,
        questionText = composedQuestion,
        activePlanetHour = state.activePlanetHour,
        weightTable = weightTable,
      )
    val autoTitle = resolveCastAutoTitle(reading, composedQuestion)
    val autoCategory = resolveCastAutoCategory(state, autoTitle, composedQuestion)
    val seq = ++castSequence
    _uiState.update {
      it.copy(
        currentReading = reading,
        activeSavedThemeId = null,
        activeSavedTitle = autoTitle,
        activeContextNotes = "",
        activeActualOutcome = null,
        activeActualScore = "",
        activeActualYellowCards = "",
        activeActualRedCards = "",
        activeLeagueCategory = autoCategory,
        inspectedHouseNumber = 15,
        sandLineCounts = reading.rawMotherDotCounts,
        selectedMothers =
          listOf(
            reading.house(1).figure,
            reading.house(2).figure,
            reading.house(3).figure,
            reading.house(4).figure,
          ),
      )
    }
    scheduleAutoSaveForCast(
      reading = reading,
      autoTitle = autoTitle,
      autoCategory = autoCategory,
      stateSnapshot = state,
      sequenceId = seq,
    )
    return reading
  }

  fun saveCurrentReading(
    title: String,
    contextNotes: String,
    actualOutcome: MatchOutcome? = null,
    actualScoreText: String = "",
    leagueCategory: String = "",
    actualYellowCardsText: String = "",
    actualRedCardsText: String = "",
  ) {
    val initialSnapshot = _uiState.value
    val reading = initialSnapshot.currentReading ?: return
    viewModelScope.launch {
      saveMutex.withLock {
        val latest = _uiState.value
        val insertedId =
          repository.saveReading(
            reading = reading,
            title = title,
            contextOutcomeNotes = contextNotes,
            existingId = latest.activeSavedThemeId,
            actualOutcome = actualOutcome,
            actualScoreText = actualScoreText,
            leagueCategory = leagueCategory,
            actualYellowCardsText = actualYellowCardsText,
            actualRedCardsText = actualRedCardsText,
            includeExactScoreOption = latest.optionExactScore,
            includeYellowCardsOption = latest.optionYellowCards,
            includeRedCardsOption = latest.optionRedCards,
          )
        val (resolvedOutcome, resolvedScore) =
          MatchOutcomeParser.parse(actualOutcome?.name, actualScoreText, contextNotes)
        val resolvedCategory =
          if (reading.domain == QuestionDomain.CONTEST) {
            MatchOutcomeParser.inferLeague(leagueCategory, title, contextNotes)
          } else {
            leagueCategory.trim().ifEmpty { reading.domain.title }
          }
        _uiState.update {
          it.copy(
            activeSavedThemeId = insertedId,
            activeSavedTitle = title.trim(),
            activeContextNotes = contextNotes.trim(),
            activeActualOutcome = resolvedOutcome,
            activeActualScore = resolvedScore,
            activeActualYellowCards = actualYellowCardsText.trim(),
            activeActualRedCards = actualRedCardsText.trim(),
            activeLeagueCategory = resolvedCategory,
          )
        }
        if (reading.domain == QuestionDomain.CONTEST && resolvedOutcome != null) {
          recalibrateFromStoredThemesIfAuto()
        }
      }
    }
  }

  fun updateSavedThemeContext(
    id: Long,
    title: String,
    notes: String,
    actualOutcome: MatchOutcome? = null,
    actualScoreText: String? = null,
    leagueCategory: String? = null,
    actualYellowCardsText: String? = null,
    actualRedCardsText: String? = null,
  ) {
    viewModelScope.launch {
      saveMutex.withLock {
        val existingEntity = savedThemes.value.firstOrNull { it.id == id }
        repository.updateContext(
          id = id,
          title = title,
          notes = notes,
          actualOutcome = actualOutcome,
          actualScoreText = actualScoreText,
          leagueCategory = leagueCategory,
          actualYellowCardsText = actualYellowCardsText,
          actualRedCardsText = actualRedCardsText,
        )
        val (resolvedOutcome, resolvedScore) =
          MatchOutcomeParser.parse(actualOutcome?.name, actualScoreText.orEmpty(), notes)
        val resolvedDomain =
          existingEntity?.domainName?.let { name ->
            runCatching { QuestionDomain.valueOf(name) }.getOrNull()
          } ?: _uiState.value.currentReading?.takeIf { _uiState.value.activeSavedThemeId == id }?.domain
        val isContest =
          resolvedDomain == QuestionDomain.CONTEST ||
            actualOutcome != null ||
            !actualScoreText.isNullOrBlank()
        val fallbackCategory =
          existingEntity?.leagueCategory?.ifBlank { resolvedDomain?.title ?: QuestionDomain.GENERAL.title }
            ?: resolvedDomain?.title
            ?: QuestionDomain.GENERAL.title
        val resolvedLeague =
          if (isContest) {
            MatchOutcomeParser.inferLeague(
              leagueCategory ?: existingEntity?.leagueCategory.orEmpty(),
              title,
              notes,
            )
          } else {
            leagueCategory?.trim()?.ifEmpty { fallbackCategory } ?: fallbackCategory
          }
        _uiState.update { state ->
          if (state.activeSavedThemeId == id) {
            state.copy(
              activeSavedTitle = title.trim(),
              activeContextNotes = notes.trim(),
              activeActualOutcome = resolvedOutcome,
              activeActualScore = resolvedScore,
              activeActualYellowCards = actualYellowCardsText?.trim() ?: state.activeActualYellowCards,
              activeActualRedCards = actualRedCardsText?.trim() ?: state.activeActualRedCards,
              activeLeagueCategory = resolvedLeague,
            )
          } else {
            state
          }
        }
        recalibrateFromStoredThemesIfAuto()
      }
    }
  }

  private suspend fun recalibrateFromStoredThemesIfAuto() {
    val existingConfig = repository.calibratedWeightsConfig.first()
    if (existingConfig != null && !existingConfig.isEnabled) return
    val allThemes = repository.savedThemes.first()
    val samples = repository.extractCalibrationSamples(allThemes)
    if (samples.isNotEmpty()) {
      val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)
      repository.persistCalibrationReport(report, enabled = true)
    }
  }

  fun applyAndPersistCalibration() {
    viewModelScope.launch {
      val allThemes = repository.savedThemes.first()
      val samples = repository.extractCalibrationSamples(allThemes)
      val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)
      repository.persistCalibrationReport(report, enabled = true)
    }
  }

  fun toggleCalibrationEnabled(enabled: Boolean) {
    viewModelScope.launch {
      val existingConfig = repository.calibratedWeightsConfig.first()
      if (existingConfig == null && enabled) {
        val allThemes = repository.savedThemes.first()
        val samples = repository.extractCalibrationSamples(allThemes)
        val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)
        repository.persistCalibrationReport(report, enabled = true)
      } else {
        repository.setCalibrationEnabled(enabled)
      }
    }
  }

  fun resetCalibrationToBaseline() {
    viewModelScope.launch {
      repository.clearCalibratedWeights()
    }
  }

  fun importNov17Matches() {
    viewModelScope.launch {
      repository.importNov17RealMatchesIfMissing()
      val allThemes = repository.savedThemes.first()
      val samples = repository.extractCalibrationSamples(allThemes)
      val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)
      repository.persistCalibrationReport(report, enabled = true)
    }
  }

  fun seedNov18ConsultationArchives() {
    viewModelScope.launch {
      repository.seedNov18ConsultationDrawsIfMissing(activeWeightTable.value)
    }
  }

  fun applyNov18VerifiedResults() {
    viewModelScope.launch {
      val updated = repository.applyNov18VerifiedResults()
      if (updated > 0) {
        recalibrateFromStoredThemesIfAuto()
      }
    }
  }

  fun buildArchiveExportText(
    targetEntity: SavedThemeEntity?,
    targetCategory: String? = null,
    format: ArchiveExportFormat,
  ): String {
    val weightTable = activeWeightTable.value
    return when {
      targetEntity != null ->
        repository.exportSingleArchive(
          entity = targetEntity,
          format = format,
          weightTable = weightTable,
        )
      !targetCategory.isNullOrBlank() ->
        repository.exportCategoryArchives(
          categoryName = targetCategory,
          entities = savedThemes.value,
          format = format,
          weightTable = weightTable,
        )
      else ->
        repository.exportAllArchives(
          entities = savedThemes.value,
          calibrationReport = calibrationReport.value,
          format = format,
          weightTable = weightTable,
        )
    }
  }

  fun suggestedArchiveExportFileName(
    targetEntity: SavedThemeEntity?,
    targetCategory: String? = null,
    format: ArchiveExportFormat,
  ): String = ArchiveReportExporter.suggestedFileName(targetEntity, targetCategory, format)

  fun loadSavedTheme(entity: SavedThemeEntity) {
    castSequence++
    val reconstructed = repository.reconstructReading(entity, activeWeightTable.value)
    val (outcome, score) =
      MatchOutcomeParser.parse(
        explicitOutcomeName = entity.actualOutcomeName,
        scoreText = entity.actualScoreText,
        notesText = entity.contextOutcomeNotes,
      )
    val category = repository.categoryFor(entity)
    _uiState.update {
      it.copy(
        selectedDomain = reconstructed.domain,
        questionText = reconstructed.questionText,
        selectedCategoryInput = category,
        activePlanetHour = reconstructed.activePlanetHour,
        sandLineCounts = reconstructed.rawMotherDotCounts,
        sandGrains = emptyList(),
        selectedMothers =
          listOf(
            reconstructed.house(1).figure,
            reconstructed.house(2).figure,
            reconstructed.house(3).figure,
            reconstructed.house(4).figure,
          ),
        currentReading = reconstructed,
        optionExactScore = entity.includeExactScoreOption,
        optionYellowCards = entity.includeYellowCardsOption,
        optionRedCards = entity.includeRedCardsOption,
        activeSavedThemeId = entity.id,
        activeSavedTitle = entity.title,
        activeContextNotes = entity.contextOutcomeNotes,
        activeActualOutcome = outcome,
        activeActualScore = score,
        activeActualYellowCards = entity.actualYellowCardsText,
        activeActualRedCards = entity.actualRedCardsText,
        activeLeagueCategory = category,
        inspectedHouseNumber = 15,
      )
    }
  }

  fun deleteSavedTheme(id: Long) {
    viewModelScope.launch {
      saveMutex.withLock {
        repository.deleteTheme(id)
        _uiState.update { state ->
          if (state.activeSavedThemeId == id) {
            state.copy(
              activeSavedThemeId = null,
              activeContextNotes = "",
              activeActualOutcome = null,
              activeActualScore = "",
              activeActualYellowCards = "",
              activeActualRedCards = "",
              activeLeagueCategory = "",
            )
          } else {
            state
          }
        }
        recalibrateFromStoredThemesIfAuto()
      }
    }
  }

  fun inspectHouse(houseNumber: Int) {
    _uiState.update { it.copy(inspectedHouseNumber = houseNumber.coerceIn(1, 16)) }
  }

  fun updateFigureSearch(query: String) {
    _uiState.update { it.copy(figureSearchQuery = query) }
  }

  fun toggleElementFilter(element: Element?) {
    _uiState.update { state ->
      state.copy(figureElementFilter = if (state.figureElementFilter == element) null else element)
    }
  }

  fun togglePolarityFilter(polarity: FortunePolarity?) {
    _uiState.update { state ->
      state.copy(
        figurePolarityFilter = if (state.figurePolarityFilter == polarity) null else polarity,
      )
    }
  }

  fun toggleFavoritesOnlyFilter() {
    _uiState.update { state ->
      state.copy(figureFavoritesOnlyFilter = !state.figureFavoritesOnlyFilter)
    }
  }

  fun toggleFigureFavorite(figureId: Int, isFavorite: Boolean) {
    viewModelScope.launch {
      figureEncyclopediaRepository?.toggleFavorite(figureId, isFavorite)
    }
  }

  fun selectCourseBook(bookId: String) {
    if (GeomancyCoursesCatalog.findBook(bookId) != null) {
      _uiState.update { it.copy(selectedCourseBookId = bookId) }
    }
  }

  fun selectGuideTab(tabIndex: Int) {
    _uiState.update { it.copy(selectedGuideTab = tabIndex.coerceIn(0, 2)) }
  }

  fun setCompletedCourseModules(moduleIds: Set<String>) {
    _uiState.update { it.copy(completedCourseModuleIds = moduleIds) }
  }

  fun toggleCourseModuleCompleted(moduleId: String): Set<String> {
    var updated = emptySet<String>()
    _uiState.update { state ->
      updated =
        if (moduleId in state.completedCourseModuleIds) {
          state.completedCourseModuleIds - moduleId
        } else {
          state.completedCourseModuleIds + moduleId
        }
      state.copy(completedCourseModuleIds = updated)
    }
    return updated
  }

  /**
   * Charge un thème Takht pédagogique issu d'un livre de cours directement sur l'écran Takht,
   * sans déclencher d'auto-enregistrement dans les archives personnelles de l'utilisateur.
   */
  fun loadCourseTakhtExample(example: CourseTakhtExample): TakhtReading {
    castSequence++
    val reading = example.computeReading(activeWeightTable.value)
    _uiState.update {
      it.copy(
        selectedDomain = reading.domain,
        questionText = reading.questionText,
        activePlanetHour = reading.activePlanetHour,
        sandLineCounts = reading.rawMotherDotCounts,
        selectedMothers =
          listOf(
            reading.house(1).figure,
            reading.house(2).figure,
            reading.house(3).figure,
            reading.house(4).figure,
          ),
        currentReading = reading,
        activeSavedThemeId = null,
        activeSavedTitle = example.title,
        activeContextNotes = "Exemple de cours (${example.sourceRef})",
        activeActualOutcome = null,
        activeActualScore = "",
        activeActualYellowCards = "",
        activeActualRedCards = "",
        activeLeagueCategory = "",
        inspectedHouseNumber = 15,
      )
    }
    return reading
  }
}

class GeomancyViewModelFactory(
  private val repository: SavedThemeRepository,
  private val figureEncyclopediaRepository: FigureEncyclopediaRepository? = null,
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(GeomancyViewModel::class.java)) {
      return GeomancyViewModel(repository, figureEncyclopediaRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
  }
}
