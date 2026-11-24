package com.example.data

import com.example.geomancy.AlToukhiGeomancyEngine
import com.example.geomancy.ArchiveRecurrenceReport
import com.example.geomancy.CalibrationReport
import com.example.geomancy.FigureWeightTable
import com.example.geomancy.MatchCalibrationSample
import com.example.geomancy.MatchOutcome
import com.example.geomancy.MatchOutcomeParser
import com.example.geomancy.Planet
import com.example.geomancy.PredictiveCalibrationEngine
import com.example.geomancy.QuestionDomain
import com.example.geomancy.TakhtReading
import com.example.geomancy.ThemeArchiveInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class SavedThemeRepository(
  private val dao: SavedThemeDao,
  private val calibrationDao: CalibrationDao? = null,
) {

  val savedThemes: Flow<List<SavedThemeEntity>> = dao.observeAll()

  val calibratedWeightsConfig: Flow<CalibratedWeightsEntity?> =
    calibrationDao?.observeActiveWeights() ?: flowOf(null)

  companion object {
    fun categoryFor(entity: SavedThemeEntity): String {
      val explicit = entity.leagueCategory.trim()
      if (explicit.isNotEmpty()) return explicit
      val domain =
        runCatching { QuestionDomain.valueOf(entity.domainName) }.getOrDefault(QuestionDomain.GENERAL)
      return if (domain == QuestionDomain.CONTEST) {
        MatchOutcomeParser.inferLeague("", entity.title, entity.contextOutcomeNotes)
      } else {
        domain.title
      }
    }

    fun groupByCategory(entities: List<SavedThemeEntity>): Map<String, List<SavedThemeEntity>> =
      entities
        .groupBy { categoryFor(it) }
        .entries
        .sortedWith(
          compareByDescending<Map.Entry<String, List<SavedThemeEntity>>> { it.value.size }
            .thenBy { it.key },
        )
        .associate { it.key to it.value }
  }

  fun categoryFor(entity: SavedThemeEntity): String = Companion.categoryFor(entity)

  fun groupByCategory(entities: List<SavedThemeEntity>): Map<String, List<SavedThemeEntity>> =
    Companion.groupByCategory(entities)

  suspend fun saveReading(
    reading: TakhtReading,
    title: String,
    contextOutcomeNotes: String,
    existingId: Long? = null,
    actualOutcome: MatchOutcome? = null,
    actualScoreText: String = "",
    leagueCategory: String = "",
    actualYellowCardsText: String = "",
    actualRedCardsText: String = "",
    includeExactScoreOption: Boolean = true,
    includeYellowCardsOption: Boolean = true,
    includeRedCardsOption: Boolean = true,
  ): Long {
    val existing = if (existingId != null && existingId > 0L) dao.getById(existingId) else null
    val safeTitle =
      title.trim().ifEmpty {
        reading.questionText.ifEmpty {
          "${reading.domain.title} · Juge ${reading.house(15).figure.westAfricanName}"
        }
      }
    val (inferredOutcome, parsedScore) =
      MatchOutcomeParser.parse(
        explicitOutcomeName = actualOutcome?.name,
        scoreText = actualScoreText,
        notesText = contextOutcomeNotes,
      )
    val resolvedCategory =
      if (reading.domain == QuestionDomain.CONTEST) {
        MatchOutcomeParser.inferLeague(
          explicitLeague = leagueCategory,
          title = safeTitle,
          notes = contextOutcomeNotes,
        )
      } else {
        leagueCategory.trim().ifEmpty { reading.domain.title }
      }
    val resolvedOutcome = (actualOutcome ?: inferredOutcome)?.name
      ?: existing?.actualOutcomeName
    val resolvedScore =
      actualScoreText.trim().ifEmpty { parsedScore }.ifEmpty { existing?.actualScoreText.orEmpty() }
    val resolvedYellow =
      actualYellowCardsText.trim().ifEmpty { existing?.actualYellowCardsText.orEmpty() }
    val resolvedRed =
      actualRedCardsText.trim().ifEmpty { existing?.actualRedCardsText.orEmpty() }
    val takhtHouseIdsCsv = (1..16).joinToString(",") { reading.house(it).figure.id.toString() }
    val entity =
      SavedThemeEntity(
        id = existingId ?: 0L,
        title = safeTitle,
        domainName = reading.domain.name,
        questionText = reading.questionText,
        planetHourName = reading.activePlanetHour.name,
        motherDotCountsCsv = reading.rawMotherDotCounts.joinToString(","),
        judgeFigureId = reading.house(15).figure.id,
        outcomeFigureId = reading.house(16).figure.id,
        overallPolarityName = reading.analysis.overallPolarity.name,
        contextOutcomeNotes = contextOutcomeNotes.trim(),
        createdAtMillis = existing?.createdAtMillis ?: System.currentTimeMillis(),
        actualOutcomeName = resolvedOutcome,
        actualScoreText = resolvedScore,
        leagueCategory = resolvedCategory,
        actualYellowCardsText = resolvedYellow,
        actualRedCardsText = resolvedRed,
        includeExactScoreOption = includeExactScoreOption,
        includeYellowCardsOption = includeYellowCardsOption,
        includeRedCardsOption = includeRedCardsOption,
        takhtHouseIdsCsv = takhtHouseIdsCsv,
      )
    return dao.insert(entity)
  }

  suspend fun updateContext(
    id: Long,
    title: String,
    notes: String,
    actualOutcome: MatchOutcome? = null,
    actualScoreText: String? = null,
    leagueCategory: String? = null,
    actualYellowCardsText: String? = null,
    actualRedCardsText: String? = null,
    includeExactScoreOption: Boolean? = null,
    includeYellowCardsOption: Boolean? = null,
    includeRedCardsOption: Boolean? = null,
  ) {
    val existing = dao.getById(id)
    val cleanTitle = title.trim()
    val cleanNotes = notes.trim()
    val isContest = existing?.domainName == QuestionDomain.CONTEST.name ||
      actualOutcome != null ||
      !actualScoreText.isNullOrBlank()
    val hasExtendedUpdate =
      actualOutcome != null ||
        actualScoreText != null ||
        leagueCategory != null ||
        actualYellowCardsText != null ||
        actualRedCardsText != null ||
        includeExactScoreOption != null ||
        includeYellowCardsOption != null ||
        includeRedCardsOption != null

    val domainTitle =
      existing?.domainName?.let { name ->
        runCatching { QuestionDomain.valueOf(name).title }.getOrNull()
      } ?: QuestionDomain.GENERAL.title

    if (hasExtendedUpdate) {
      val (inferredOutcome, parsedScore) =
        MatchOutcomeParser.parse(
          explicitOutcomeName = actualOutcome?.name,
          scoreText = actualScoreText.orEmpty(),
          notesText = cleanNotes,
        )
      val resolvedCategory =
        if (isContest) {
          MatchOutcomeParser.inferLeague(
            explicitLeague = leagueCategory ?: existing?.leagueCategory.orEmpty(),
            title = cleanTitle,
            notes = cleanNotes,
          )
        } else {
          leagueCategory?.trim()?.takeIf { it.isNotEmpty() }
            ?: existing?.leagueCategory?.takeIf { it.isNotBlank() }
            ?: domainTitle
        }
      dao.updateContextAndMatchDetails(
        id = id,
        title = cleanTitle,
        notes = cleanNotes,
        actualOutcomeName = (actualOutcome ?: inferredOutcome)?.name,
        actualScoreText = (actualScoreText?.trim().orEmpty()).ifEmpty { parsedScore },
        leagueCategory = resolvedCategory,
        actualYellowCardsText = (actualYellowCardsText ?: existing?.actualYellowCardsText.orEmpty()).trim(),
        actualRedCardsText = (actualRedCardsText ?: existing?.actualRedCardsText.orEmpty()).trim(),
        includeExactScoreOption = includeExactScoreOption ?: (existing?.includeExactScoreOption ?: true),
        includeYellowCardsOption = includeYellowCardsOption ?: (existing?.includeYellowCardsOption ?: true),
        includeRedCardsOption = includeRedCardsOption ?: (existing?.includeRedCardsOption ?: true),
      )
    } else {
      val (inferredOutcome, parsedScore) =
        MatchOutcomeParser.parse(
          explicitOutcomeName = null,
          scoreText = "",
          notesText = cleanNotes,
        )
      if (inferredOutcome != null) {
        val resolvedCategory =
          if (existing?.domainName == QuestionDomain.CONTEST.name) {
            MatchOutcomeParser.inferLeague(existing.leagueCategory, cleanTitle, cleanNotes)
          } else {
            existing?.leagueCategory?.takeIf { it.isNotBlank() } ?: domainTitle
          }
        dao.updateContextAndOutcome(
          id = id,
          title = cleanTitle,
          notes = cleanNotes,
          actualOutcomeName = inferredOutcome.name,
          actualScoreText = parsedScore,
          leagueCategory = resolvedCategory,
        )
      } else {
        dao.updateContext(id = id, title = cleanTitle, notes = cleanNotes)
      }
    }
  }

  suspend fun updatePrecisionOptions(
    id: Long,
    includeExactScore: Boolean,
    includeYellowCards: Boolean,
    includeRedCards: Boolean,
  ) {
    dao.updatePrecisionOptions(
      id = id,
      includeExactScore = includeExactScore,
      includeYellowCards = includeYellowCards,
      includeRedCards = includeRedCards,
    )
  }

  suspend fun deleteTheme(id: Long) {
    dao.deleteById(id)
  }

  suspend fun persistCalibrationReport(
    report: CalibrationReport,
    enabled: Boolean = true,
  ) {
    val cDao = calibrationDao ?: return
    val csv = serializeWeights(report.calibratedWeights)
    cDao.upsert(
      CalibratedWeightsEntity(
        id = 1,
        weightsCsv = csv,
        isEnabled = enabled,
        sampleCount = report.totalSamples,
        baselineAccuracyPercent = report.baselineAccuracyPercent,
        calibratedAccuracyPercent = report.calibratedAccuracyPercent,
      ),
    )
  }

  suspend fun setCalibrationEnabled(enabled: Boolean) {
    calibrationDao?.setEnabled(enabled)
  }

  suspend fun clearCalibratedWeights() {
    calibrationDao?.clear()
  }

  suspend fun importNov17RealMatchesIfMissing(): Int {
    val existing = dao.observeAll().first()
    val existingTitles = existing.map { it.title.trim().lowercase() }.toSet()
    var added = 0
    PredictiveCalibrationEngine.nov17RealMatchSamples().forEach { sample ->
      if (sample.title.lowercase() !in existingTitles) {
        val reading =
          AlToukhiGeomancyEngine.castFromDotCounts(
            sixteenCounts = sample.motherDotCounts,
            domain = QuestionDomain.CONTEST,
            questionText = sample.title,
            activePlanetHour = Planet.MARS,
          )
        saveReading(
          reading = reading,
          title = sample.title,
          contextOutcomeNotes = "17-11-2026 · ${sample.leagueOrType} · Score officiel : ${sample.scoreLabel} (${sample.actualOutcome.label})",
          actualOutcome = sample.actualOutcome,
          actualScoreText = sample.scoreLabel,
          leagueCategory = sample.leagueOrType,
        )
        added++
      }
    }
    return added
  }

  suspend fun seedNov18ConsultationDrawsIfMissing(
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): Int {
    val existing = dao.observeAll().first()
    val existingTitles = existing.map { it.title.trim().lowercase() }.toSet()
    var added = 0
    PredictiveCalibrationEngine.nov18ConsultationDraws().forEach { seed ->
      if (seed.title.lowercase() !in existingTitles) {
        val reading =
          AlToukhiGeomancyEngine.castFromDotCounts(
            sixteenCounts = seed.motherDotCounts,
            domain = QuestionDomain.CONTEST,
            questionText = seed.title,
            activePlanetHour = Planet.MERCURY,
            weightTable = weightTable,
          )
        val prediction = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable)
        val scoreA = FigureWeightTable.formatWeight(prediction.sideAScore)
        val scoreB = FigureWeightTable.formatWeight(prediction.sideBScore)
        val note =
          "Tirage du 18-11-2026 à ${seed.drawTime} · Juge ${reading.house(15).figure.westAfricanName} · " +
            "Prédiction : ${prediction.predictedOutcome.label} ($scoreA vs $scoreB). Score réel à compléter."
        saveReading(
          reading = reading,
          title = seed.title,
          contextOutcomeNotes = note,
          actualOutcome = null,
          actualScoreText = "",
          leagueCategory = seed.league,
        )
        added++
      }
    }
    return added
  }

  fun extractCalibrationSamples(entities: List<SavedThemeEntity>): List<MatchCalibrationSample> =
    entities.mapNotNull { entity ->
      if (entity.domainName != QuestionDomain.CONTEST.name) return@mapNotNull null
      val (outcome, parsedScore) =
        MatchOutcomeParser.parse(
          explicitOutcomeName = entity.actualOutcomeName,
          scoreText = entity.actualScoreText,
          notesText = entity.contextOutcomeNotes,
        )
      if (outcome == null) return@mapNotNull null
      val counts = parseMotherCounts(entity.motherDotCountsCsv)
      val league =
        MatchOutcomeParser.inferLeague(
          explicitLeague = entity.leagueCategory,
          title = entity.title,
          notes = entity.contextOutcomeNotes,
        )
      MatchCalibrationSample(
        id = entity.id,
        title = entity.title,
        motherDotCounts = counts,
        actualOutcome = outcome,
        scoreLabel = parsedScore.ifEmpty { outcome.shortLabel },
        leagueOrType = league,
      )
    }

  fun decodeWeightTable(entity: CalibratedWeightsEntity?): FigureWeightTable {
    if (entity == null || !entity.isEnabled) return FigureWeightTable.default()
    val parsed =
      entity.weightsCsv
        .split(",")
        .mapNotNull { token ->
          val parts = token.split(":")
          if (parts.size == 2) {
            val id = parts[0].trim().toIntOrNull()
            val w = parts[1].trim().toDoubleOrNull()
            if (id != null && w != null) id to w else null
          } else {
            null
          }
        }
        .toMap()
    if (parsed.isEmpty()) return FigureWeightTable.default()
    val merged = FigureWeightTable.default().weightsByFigureId.toMutableMap()
    merged.putAll(parsed)
    return FigureWeightTable(
      weightsByFigureId = merged,
      drawThreshold = 3.0,
      isCalibrated = true,
    )
  }

  private fun serializeWeights(table: FigureWeightTable): String =
    table.weightsByFigureId.entries
      .sortedBy { it.key }
      .joinToString(",") { (id, w) -> "$id:${FigureWeightTable.formatWeight(w)}" }

  private fun parseMotherCounts(csv: String): List<Int> =
    csv
      .split(",")
      .mapNotNull { it.trim().toIntOrNull() }
      .let { parsed -> if (parsed.size == 16) parsed else List(16) { 2 } }

  fun reconstructReading(
    entity: SavedThemeEntity,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): TakhtReading {
    val domain =
      runCatching { QuestionDomain.valueOf(entity.domainName) }
        .getOrDefault(QuestionDomain.GENERAL)
    val planetHour =
      runCatching { Planet.valueOf(entity.planetHourName) }
        .getOrDefault(Planet.JUPITER)
    val dotCounts = parseMotherCounts(entity.motherDotCountsCsv)

    return AlToukhiGeomancyEngine.castFromDotCounts(
      sixteenCounts = dotCounts,
      domain = domain,
      questionText = entity.questionText,
      activePlanetHour = planetHour,
      weightTable = weightTable,
    )
  }

  fun exportSingleArchive(
    entity: SavedThemeEntity,
    format: ArchiveExportFormat,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): String {
    val reading = reconstructReading(entity, weightTable)
    return ArchiveReportExporter.formatSingleArchive(
      entity = entity,
      reading = reading,
      format = format,
      weightTable = weightTable,
    )
  }

  fun exportAllArchives(
    entities: List<SavedThemeEntity>,
    calibrationReport: CalibrationReport?,
    format: ArchiveExportFormat,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): String =
    ArchiveReportExporter.formatAllArchives(
      entities = entities,
      reconstructReading = { entity -> reconstructReading(entity, weightTable) },
      calibrationReport = calibrationReport,
      weightTable = weightTable,
      format = format,
    )

  fun exportCategoryArchives(
    categoryName: String,
    entities: List<SavedThemeEntity>,
    format: ArchiveExportFormat,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): String {
    val categoryEntities = entities.filter { categoryFor(it).equals(categoryName, ignoreCase = true) }
    val samples = extractCalibrationSamples(categoryEntities)
    val categoryCalibration =
      if (samples.isNotEmpty()) PredictiveCalibrationEngine.evaluateAndCalibrate(samples) else null
    return ArchiveReportExporter.formatCategoryArchives(
      categoryName = categoryName,
      entities = categoryEntities,
      reconstructReading = { entity -> reconstructReading(entity, weightTable) },
      calibrationReport = categoryCalibration,
      weightTable = weightTable,
      format = format,
    )
  }

  fun signatureForEntity(entity: SavedThemeEntity): String =
    PredictiveCalibrationEngine.canonicalSignatureFromMotherDotCounts(parseMotherCounts(entity.motherDotCountsCsv))

  fun computeArchiveRecurrences(entities: List<SavedThemeEntity>): ArchiveRecurrenceReport {
    val inputs =
      entities.map { entity ->
        val domain =
          runCatching { QuestionDomain.valueOf(entity.domainName) }.getOrDefault(QuestionDomain.GENERAL)
        val outcomeLabel =
          entity.actualOutcomeName?.let { name ->
            runCatching { MatchOutcome.valueOf(name).label }.getOrNull()
          }
        ThemeArchiveInput(
          id = entity.id,
          title = entity.title,
          domain = domain,
          motherDotCounts = parseMotherCounts(entity.motherDotCountsCsv),
          createdAtMillis = entity.createdAtMillis,
          actualOutcomeLabel = outcomeLabel,
        )
      }
    return PredictiveCalibrationEngine.computeThemeRecurrences(inputs)
  }

  suspend fun applyNov18VerifiedResults(): Int {
    val existing = dao.observeAll().first()
    val verifiedList = PredictiveCalibrationEngine.nov18VerifiedMatchResults()
    var updated = 0
    existing.forEach { entity ->
      val isPendingNov18 =
        entity.actualOutcomeName == null ||
          (entity.actualScoreText.isBlank() &&
            entity.contextOutcomeNotes.contains("à compléter", ignoreCase = true))
      if (!isPendingNov18) return@forEach
      val match =
        verifiedList.firstOrNull { verified ->
          entity.title.contains(verified.titlePrefix, ignoreCase = true)
        } ?: return@forEach
      val mergedNotes =
        if (entity.contextOutcomeNotes.contains("Résultat vérifié 18-11-2026")) {
          entity.contextOutcomeNotes
        } else {
          listOf(entity.contextOutcomeNotes.trim(), match.summaryNote)
            .filter { it.isNotEmpty() }
            .joinToString(" · ")
        }
      dao.updateContextAndOutcome(
        id = entity.id,
        title = entity.title,
        notes = mergedNotes,
        actualOutcomeName = match.outcome90Min.name,
        actualScoreText = match.scoreDisplay,
        leagueCategory = entity.leagueCategory.ifEmpty { "Copa del Rey" },
      )
      updated++
    }
    return updated
  }
}
