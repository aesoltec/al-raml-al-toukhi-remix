package com.example.geomancy

import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign

/**
 * Module d'analyse prédictive et de calibrage automatique des poids des 16 figures.
 * Compare chaque thème géomantique saisi avec son résultat sportif réel stocké, puis ajuste
 * les poids des figures dans l'algorithme d'Al-Ghālib wa-l-Maghlūb sans jamais dégrader
 * la précision observée par rapport à la table de base d'Al-Toukhi.
 */
object PredictiveCalibrationEngine {

  fun evaluateAndCalibrate(samples: List<MatchCalibrationSample>): CalibrationReport {
    val baseline = FigureWeightTable.default()
    if (samples.isEmpty()) {
      return CalibrationReport(
        totalSamples = 0,
        baselineCorrectCount = 0,
        calibratedCorrectCount = 0,
        baselineAccuracyPercent = 0,
        calibratedAccuracyPercent = 0,
        calibratedWeights = baseline,
        evaluationsBefore = emptyList(),
        evaluationsAfter = emptyList(),
        accuracyByLeague = emptyList(),
        accuracyByMatchType = emptyList(),
        figureDeltas =
          AlToukhiFigures.allInWestAfricanOrder.map { fig ->
            val w = baseline.weightFor(fig)
            FigureWeightDelta(
              figure = fig,
              baselineWeight = w,
              calibratedWeight = w,
              appearancesInSamples = 0,
            )
          },
      )
    }

    val readings =
      samples.map { sample ->
        sample to
          AlToukhiGeomancyEngine.castFromDotCounts(
            sixteenCounts = sample.motherDotCounts,
            domain = QuestionDomain.CONTEST,
            questionText = sample.title,
            weightTable = baseline,
          )
      }

    val evaluationsBefore =
      readings.map { (sample, reading) ->
        evaluateSingle(sample = sample, houses = reading.houses, weightTable = baseline)
      }
    val baselineCorrect = evaluationsBefore.count { it.isCorrect }

    val currentWeights = baseline.weightsByFigureId.toMutableMap()
    var bestWeights = currentWeights.toMap()
    var bestCorrect = baselineCorrect
    var bestLoss = computeMarginLoss(readings, baseline)

    val appearances = mutableMapOf<Int, Int>()
    readings.forEach { (_, reading) ->
      val uniqueDuelFigures =
        listOf(1, 2, 4, 7, 8, 9, 10, 11, 12, 13, 14)
          .map { reading.house(it).figure.id }
          .toSet()
      uniqueDuelFigures.forEach { id -> appearances[id] = (appearances[id] ?: 0) + 1 }
    }

    val learningRate = 0.25
    val anchorDecay = 0.015
    val epochs = 45

    for (epoch in 0 until epochs) {
      val decay = 1.0 / (1.0 + epoch * 0.04)
      for ((sample, reading) in readings) {
        val candidateTable = FigureWeightTable(currentWeights.toMap(), baseline.drawThreshold, isCalibrated = true)
        val pred = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, candidateTable)
        val diff = pred.sideAScore - pred.sideBScore

        val targetError =
          when (sample.actualOutcome) {
            MatchOutcome.CAMP_A_WIN ->
              if (pred.predictedOutcome != MatchOutcome.CAMP_A_WIN || diff < 1.8) 1.0 else 0.0
            MatchOutcome.CAMP_B_WIN ->
              if (pred.predictedOutcome != MatchOutcome.CAMP_B_WIN || diff > -1.8) -1.0 else 0.0
            MatchOutcome.DRAW -> {
              if (pred.predictedOutcome != MatchOutcome.DRAW || abs(diff) > 1.2) {
                if (abs(diff) < 0.05) 0.0 else -diff.sign
              } else {
                0.0
              }
            }
          }

        if (targetError != 0.0) {
          val netCoeffs = computeNetFigureCoefficients(reading.houses)
          val normSq = netCoeffs.values.sumOf { it * it }.coerceAtLeast(1.0)
          netCoeffs.forEach { (figId, coeff) ->
            val baseW = baseline.weightsByFigureId[figId] ?: 0.0
            val curW = currentWeights[figId] ?: baseW
            val step = (learningRate * decay * targetError * coeff) / kotlin.math.sqrt(normSq)
            val reg = anchorDecay * (baseW - curW)
            currentWeights[figId] =
              (curW + step + reg).coerceIn(FigureWeightTable.MIN_WEIGHT, FigureWeightTable.MAX_WEIGHT)
          }
        }
      }

      val roundedMap =
        currentWeights.mapValues { (_, v) -> ((v * 10.0).roundToInt() / 10.0) }
      val candidateTable = FigureWeightTable(roundedMap, baseline.drawThreshold, isCalibrated = true)
      val candidateCorrect =
        readings.count { (sample, reading) ->
          AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, candidateTable).predictedOutcome ==
            sample.actualOutcome
        }
      val candidateLoss = computeMarginLoss(readings, candidateTable)

      if (candidateCorrect > bestCorrect || (candidateCorrect == bestCorrect && candidateLoss < bestLoss - 1e-4)) {
        bestCorrect = candidateCorrect
        bestLoss = candidateLoss
        bestWeights = roundedMap
      }
    }

    val hasAnyWeightChange =
      bestWeights.any { (id, w) -> abs(w - (baseline.weightsByFigureId[id] ?: 0.0)) >= 0.05 }
    val finalTable =
      FigureWeightTable(
        weightsByFigureId = bestWeights,
        drawThreshold = baseline.drawThreshold,
        isCalibrated = hasAnyWeightChange,
      )

    val evaluationsAfter =
      readings.map { (sample, reading) ->
        evaluateSingle(sample = sample, houses = reading.houses, weightTable = finalTable)
      }
    val finalCorrect = evaluationsAfter.count { it.isCorrect }

    val accuracyByLeague =
      aggregateByKey(evaluationsBefore, evaluationsAfter) { detail ->
        detail.sample.leagueOrType.ifBlank { "Duel Libre" }
      }

    val accuracyByMatchType =
      aggregateByKey(evaluationsBefore, evaluationsAfter) { detail ->
        detail.sample.actualOutcome.label
      }

    val figureDeltas =
      AlToukhiFigures.allInWestAfricanOrder.map { fig ->
        val baseW = baseline.weightFor(fig)
        val calW = finalTable.weightFor(fig)
        FigureWeightDelta(
          figure = fig,
          baselineWeight = baseW,
          calibratedWeight = calW,
          appearancesInSamples = appearances[fig.id] ?: 0,
        )
      }

    val total = samples.size
    return CalibrationReport(
      totalSamples = total,
      baselineCorrectCount = baselineCorrect,
      calibratedCorrectCount = finalCorrect,
      baselineAccuracyPercent = ((baselineCorrect * 100.0) / total).roundToInt(),
      calibratedAccuracyPercent = ((finalCorrect * 100.0) / total).roundToInt(),
      calibratedWeights = finalTable,
      evaluationsBefore = evaluationsBefore,
      evaluationsAfter = evaluationsAfter,
      accuracyByLeague = accuracyByLeague,
      accuracyByMatchType = accuracyByMatchType,
      figureDeltas = figureDeltas,
    )
  }

  private fun aggregateByKey(
    beforeList: List<MatchEvaluationDetail>,
    afterList: List<MatchEvaluationDetail>,
    keySelector: (MatchEvaluationDetail) -> String,
  ): List<AccuracyCategoryStat> {
    val beforeById = beforeList.associateBy { it.sample.id }
    return afterList
      .groupBy(keySelector)
      .map { (category, afterGroup) ->
        val baselineSuccess =
          afterGroup.count { detail -> beforeById[detail.sample.id]?.isCorrect == true }
        val calibratedSuccess = afterGroup.count { it.isCorrect }
        AccuracyCategoryStat(
          categoryName = category,
          totalMatches = afterGroup.size,
          baselineSuccessCount = baselineSuccess,
          calibratedSuccessCount = calibratedSuccess,
        )
      }
      .sortedByDescending { it.totalMatches }
  }

  private fun evaluateSingle(
    sample: MatchCalibrationSample,
    houses: List<ChartHouse>,
    weightTable: FigureWeightTable,
  ): MatchEvaluationDetail {
    val prediction = AlToukhiGeomancyEngine.predictContestOutcome(houses, weightTable)
    return MatchEvaluationDetail(
      sample = sample,
      predictedOutcome = prediction.predictedOutcome,
      sideAScore = prediction.sideAScore,
      sideBScore = prediction.sideBScore,
      isCorrect = prediction.predictedOutcome == sample.actualOutcome,
      judgeFigure = houses[14].figure,
      outcome16Figure = houses[15].figure,
    )
  }

  private fun computeNetFigureCoefficients(houses: List<ChartHouse>): Map<Int, Double> {
    val coeffs = mutableMapOf<Int, Double>()
    fun add(houseNum: Int, delta: Double) {
      val id = houses[houseNum - 1].figure.id
      coeffs[id] = (coeffs[id] ?: 0.0) + delta
    }
    // Camp A (+)
    add(1, 3.0)
    add(13, 3.0)
    add(10, 3.0)
    add(2, 2.0)
    add(9, 1.0)
    // Camp B (-)
    add(7, -3.0)
    add(14, -3.0)
    add(4, -2.0)
    add(8, -2.0)
    add(11, -1.0)
    add(12, -1.0)
    return coeffs
  }

  private fun computeMarginLoss(
    readings: List<Pair<MatchCalibrationSample, TakhtReading>>,
    weightTable: FigureWeightTable,
  ): Double =
    readings.sumOf { (sample, reading) ->
      val pred = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable)
      val diff = pred.sideAScore - pred.sideBScore
      when (sample.actualOutcome) {
        MatchOutcome.CAMP_A_WIN -> (1.5 - diff).coerceAtLeast(0.0)
        MatchOutcome.CAMP_B_WIN -> (diff + 1.5).coerceAtLeast(0.0)
        MatchOutcome.DRAW -> abs(diff)
      }
    }

  /**
   * Calcule la fréquence d'apparition de chacune des 16 figures à travers les thèmes archivés
   * (M1–M4 Mères, M5–M14 dérivées, M15–M16 Juge & Issue) pour dégager les tendances dominantes.
   */
  fun computeArchiveFrequencies(readings: List<TakhtReading>): ArchiveFrequencyReport {
    val totalThemes = readings.size
    val totalHouseSlots = totalThemes * 16
    val expectedShare = 100.0 / 16.0

    val totalCounts = mutableMapOf<Int, Int>()
    val motherCounts = mutableMapOf<Int, Int>()
    val derivedCounts = mutableMapOf<Int, Int>()
    val verdictCounts = mutableMapOf<Int, Int>()
    val elementCounts = Element.entries.associateWith { 0 }.toMutableMap()
    val polarityCounts = FortunePolarity.entries.associateWith { 0 }.toMutableMap()

    readings.forEach { reading ->
      reading.houses.forEach { house ->
        val fig = house.figure
        val num = house.info.number
        totalCounts[fig.id] = (totalCounts[fig.id] ?: 0) + 1
        when (num) {
          in 1..4 -> motherCounts[fig.id] = (motherCounts[fig.id] ?: 0) + 1
          in 5..14 -> derivedCounts[fig.id] = (derivedCounts[fig.id] ?: 0) + 1
          else -> verdictCounts[fig.id] = (verdictCounts[fig.id] ?: 0) + 1
        }
        elementCounts[fig.dominantElement] = (elementCounts[fig.dominantElement] ?: 0) + 1
        polarityCounts[fig.polarity] = (polarityCounts[fig.polarity] ?: 0) + 1
      }
    }

    val stats =
      AlToukhiFigures.allInWestAfricanOrder.map { fig ->
        val total = totalCounts[fig.id] ?: 0
        val mothers = motherCounts[fig.id] ?: 0
        val derived = derivedCounts[fig.id] ?: 0
        val verdict = verdictCounts[fig.id] ?: 0
        val share = if (totalHouseSlots > 0) (total * 100.0) / totalHouseSlots else 0.0
        val ratio = if (totalHouseSlots > 0) share / expectedShare else 0.0
        val trend =
          when {
            total == 0 -> FigureTrendStatus.ABSENT
            ratio >= 1.45 -> FigureTrendStatus.DOMINANT
            ratio >= 1.12 -> FigureTrendStatus.ABOVE_AVERAGE
            ratio >= 0.72 -> FigureTrendStatus.BALANCED
            else -> FigureTrendStatus.RARE
          }
        FigureFrequencyStat(
          figure = fig,
          totalAppearances = total,
          motherAppearances = mothers,
          derivedAppearances = derived,
          judgeAndOutcomeAppearances = verdict,
          sharePercent = share,
          expectedSharePercent = expectedShare,
          trendStatus = trend,
        )
      }

    val topFigures =
      stats
        .filter { it.totalAppearances > 0 }
        .sortedWith(
          compareByDescending<FigureFrequencyStat> { it.totalAppearances }
            .thenByDescending { it.judgeAndOutcomeAppearances }
            .thenBy { it.figure.westAfricanOrder },
        )
        .take(3)

    val topVerdicts =
      stats
        .filter { it.judgeAndOutcomeAppearances > 0 }
        .sortedWith(
          compareByDescending<FigureFrequencyStat> { it.judgeAndOutcomeAppearances }
            .thenByDescending { it.totalAppearances }
            .thenBy { it.figure.westAfricanOrder },
        )
        .take(3)

    val dominantElement =
      if (totalHouseSlots > 0) elementCounts.maxByOrNull { it.value }?.key else null
    val dominantPolarity =
      if (totalHouseSlots > 0) polarityCounts.maxByOrNull { it.value }?.key else null

    val summary =
      if (totalThemes == 0) {
        "Aucun tirage archivé : enregistre des thèmes pour observer les figures récurrentes."
      } else {
        val leaders =
          topFigures.joinToString(", ") { stat ->
            "${stat.figure.westAfricanName} (${stat.totalAppearances}×)"
          }
        val judges =
          topVerdicts.joinToString(", ") { stat ->
            "${stat.figure.westAfricanName} (${stat.judgeAndOutcomeAppearances}×)"
          }
        buildString {
          append("Sur $totalThemes tirage(s) ($totalHouseSlots maisons), ")
          if (leaders.isNotEmpty()) append("les figures dominantes sont $leaders. ")
          if (judges.isNotEmpty()) append("En Juge/Issue (M15–M16) : $judges. ")
          if (dominantElement != null) {
            append("L'élément ${dominantElement.englishName} marque la tendance de fond.")
          }
        }.trim()
      }

    return ArchiveFrequencyReport(
      totalThemes = totalThemes,
      totalHouseSlots = totalHouseSlots,
      statsByFigure = stats,
      elementDistribution = elementCounts,
      polarityDistribution = polarityCounts,
      dominantElement = dominantElement,
      dominantPolarity = dominantPolarity,
      topFigures = topFigures,
      topVerdictFigures = topVerdicts,
      trendSummary = summary,
    )
  }

  /**
   * Calcule la signature canonique des 4 Mères (ex. "2122/2211/1121/2122") à partir des 16 lignes
   * de points bruts. Deux tirages aux nombres bruts différents mais de même parité produisent
   * exactement le même Takht à 16 maisons et partagent donc la même signature.
   */
  fun canonicalSignatureFromMotherDotCounts(counts: List<Int>): String {
    val safe = if (counts.size == 16) counts else List(16) { 2 }
    return (0 until 4).joinToString("/") { motherIdx ->
      val start = motherIdx * 4
      (0 until 4).joinToString("") { rankIdx ->
        if (safe[start + rankIdx] % 2 == 1) "1" else "2"
      }
    }
  }

  fun canonicalSignature(reading: TakhtReading): String =
    (1..4).joinToString("/") { houseNum -> reading.house(houseNum).figure.code }

  /**
   * Répertorie et compte combien de fois chaque thème géomantique complet (signature M1/M2/M3/M4)
   * est déjà apparu dans les archives locales.
   */
  fun computeThemeRecurrences(entries: List<ThemeArchiveInput>): ArchiveRecurrenceReport {
    if (entries.isEmpty()) {
      return ArchiveRecurrenceReport(
        totalThemes = 0,
        uniqueSignatures = 0,
        recurringSignatureCount = 0,
        recurringThemes = emptyList(),
        allSignaturesSorted = emptyList(),
        bySignature = emptyMap(),
      )
    }

    val grouped = entries.groupBy { entry -> canonicalSignatureFromMotherDotCounts(entry.motherDotCounts) }
    val summaries =
      grouped
        .map { (signature, groupEntries) ->
          val sampleCounts = groupEntries.first().motherDotCounts
          val sampleReading =
            AlToukhiGeomancyEngine.castFromDotCounts(
              sixteenCounts = sampleCounts,
              domain = groupEntries.first().domain,
            )
          val mothersLabel =
            (1..4).joinToString(" · ") { num -> sampleReading.house(num).figure.westAfricanName }
          val sortedOccurrences =
            groupEntries
              .sortedByDescending { it.createdAtMillis }
              .map { item ->
                ThemeOccurrenceSummary(
                  archiveId = item.id,
                  title = item.title,
                  domain = item.domain,
                  createdAtMillis = item.createdAtMillis,
                  actualOutcomeLabel = item.actualOutcomeLabel,
                )
              }
          ThemeSignatureRecurrence(
            signatureCode = signature,
            mothersLabel = mothersLabel,
            judgeFigure = sampleReading.house(15).figure,
            outcomeFigure = sampleReading.house(16).figure,
            occurrenceCount = sortedOccurrences.size,
            occurrences = sortedOccurrences,
          )
        }
        .sortedWith(
          compareByDescending<ThemeSignatureRecurrence> { it.occurrenceCount }
            .thenByDescending { it.occurrences.firstOrNull()?.createdAtMillis ?: 0L },
        )

    val recurringOnly = summaries.filter { it.occurrenceCount >= 2 }
    return ArchiveRecurrenceReport(
      totalThemes = entries.size,
      uniqueSignatures = summaries.size,
      recurringSignatureCount = recurringOnly.size,
      recurringThemes = recurringOnly,
      allSignaturesSorted = summaries,
      bySignature = summaries.associateBy { it.signatureCode },
    )
  }

  /**
   * Jeu de données vérifié du 17-11-2026 (10 rencontres avec ligues et scores officiels),
   * prêt à être importé dans les archives locales pour alimenter ou tester le calibrage.
   */
  fun nov17RealMatchSamples(): List<MatchCalibrationSample> =
    listOf(
      sampleOf(1L, "Danemark vs Norvège", "1221", "2121", "2121", "2222", MatchOutcome.CAMP_B_WIN, "2-3", "Ligue des Nations UEFA"),
      sampleOf(2L, "Pays de Galles vs Portugal", "2221", "2211", "1222", "1112", MatchOutcome.CAMP_B_WIN, "2-3", "Ligue des Nations UEFA"),
      sampleOf(3L, "Hongrie vs Irlande du Nord", "2212", "2112", "2122", "2222", MatchOutcome.DRAW, "1-1", "Ligue des Nations UEFA"),
      sampleOf(4L, "Ukraine vs Géorgie", "2211", "1111", "1121", "1121", MatchOutcome.CAMP_A_WIN, "1-0", "Ligue des Nations UEFA"),
      sampleOf(5L, "Israël vs Autriche", "2111", "2121", "1121", "1111", MatchOutcome.DRAW, "1-1", "Ligue des Nations UEFA"),
      sampleOf(6L, "Irlande vs Kosovo", "1211", "2222", "2121", "1222", MatchOutcome.CAMP_A_WIN, "3-0", "Ligue des Nations UEFA"),
      sampleOf(7L, "Bosnie-Herzégovine vs Roumanie", "2112", "2212", "2121", "2211", MatchOutcome.CAMP_A_WIN, "2-1", "Ligue des Nations UEFA"),
      sampleOf(8L, "Pologne vs Suède", "2211", "2121", "2121", "2221", MatchOutcome.DRAW, "1-1", "Ligue des Nations UEFA"),
      sampleOf(9L, "Botswana vs Ouganda", "1121", "1222", "2112", "1221", MatchOutcome.DRAW, "1-1", "Qualifications CAN 2027"),
      sampleOf(10L, "Mauritanie vs Burkina Faso", "1111", "1112", "2111", "1212", MatchOutcome.CAMP_B_WIN, "1-3", "Qualifications CAN 2027"),
    )

  fun nov18ConsultationDraws(): List<ConsultationArchiveSeed> =
    listOf(
      seedOf("Club Portugalete vs Real Unión (18h00)", "13h18", "2122", "2211", "1121", "2122", "Copa del Rey"),
      seedOf("Utebo vs Sabadell (18h00)", "13h23", "1111", "1222", "1112", "2121", "Copa del Rey"),
      seedOf("Alcoyano vs Majorque (19h00)", "13h37", "2112", "1112", "2121", "1221", "Copa del Rey"),
      seedOf("Badajoz vs Tenerife (19h00)", "12h54", "2221", "1122", "1111", "1111", "Copa del Rey"),
      seedOf("Club Deportiva Minera vs Cartagena (19h30)", "13h05", "2211", "2222", "2212", "1111", "Copa del Rey"),
      seedOf("Reus Reddis vs Barakaldo (19h30)", "13h11", "2122", "2122", "2121", "2211", "Copa del Rey"),
    )

  /** Résultats officiels vérifiés de la Copa del Rey du 18-11-2026 (90 min réglementaires / issue finale). */
  fun nov18VerifiedMatchResults(): List<Nov18VerifiedResult> =
    listOf(
      Nov18VerifiedResult(
        titlePrefix = "Club Portugalete vs Real Unión",
        outcome90Min = MatchOutcome.DRAW,
        scoreDisplay = "0-0 (4-1 TAB)",
        summaryNote = "Résultat vérifié 18-11-2026 : 0-0 après prolongation, Portugalete qualifié 4-1 aux TAB.",
      ),
      Nov18VerifiedResult(
        titlePrefix = "Utebo vs Sabadell",
        outcome90Min = MatchOutcome.CAMP_B_WIN,
        scoreDisplay = "0-3",
        summaryNote = "Résultat vérifié 18-11-2026 : Utebo 0-3 Sabadell (Obolskii, Ribeiro, Rahmani).",
      ),
      Nov18VerifiedResult(
        titlePrefix = "Alcoyano vs Majorque",
        outcome90Min = MatchOutcome.DRAW,
        scoreDisplay = "1-1 (1-3 a.p.)",
        summaryNote = "Résultat vérifié 18-11-2026 : 1-1 à la 90e min, RCD Mallorca s’impose 1-3 après prolongation.",
      ),
      Nov18VerifiedResult(
        titlePrefix = "Badajoz vs Tenerife",
        outcome90Min = MatchOutcome.CAMP_B_WIN,
        scoreDisplay = "2-5",
        summaryNote = "Résultat vérifié 18-11-2026 : CD Badajoz 2-5 CD Tenerife.",
      ),
      Nov18VerifiedResult(
        titlePrefix = "Club Deportiva Minera vs Cartagena",
        outcome90Min = MatchOutcome.DRAW,
        scoreDisplay = "1-1 (1-2 a.p.)",
        summaryNote = "Résultat vérifié 18-11-2026 : 1-1 à la 90e min, FC Cartagena s’impose 1-2 après prolongation (Dacosta 116').",
      ),
      Nov18VerifiedResult(
        titlePrefix = "Reus Reddis vs Barakaldo",
        outcome90Min = MatchOutcome.DRAW,
        scoreDisplay = "1-1 (4-3 TAB)",
        summaryNote = "Résultat vérifié 18-11-2026 : 1-1 après prolongation, Reus Reddis qualifié 4-3 aux TAB.",
      ),
    )

  private fun seedOf(
    title: String,
    drawTime: String,
    c1: String,
    c2: String,
    c3: String,
    c4: String,
    league: String,
  ): ConsultationArchiveSeed =
    ConsultationArchiveSeed(
      title = title,
      drawTime = drawTime,
      motherDotCounts =
        listOf(c1, c2, c3, c4).flatMap { code ->
          code.map { ch -> if (ch == '1') 1 else 2 }
        },
      league = league,
    )

  private fun sampleOf(
    id: Long,
    title: String,
    c1: String,
    c2: String,
    c3: String,
    c4: String,
    outcome: MatchOutcome,
    score: String,
    league: String,
  ): MatchCalibrationSample {
    val dots =
      listOf(c1, c2, c3, c4).flatMap { code ->
        code.map { ch -> if (ch == '1') 1 else 2 }
      }
    return MatchCalibrationSample(
      id = id,
      title = title,
      motherDotCounts = dots,
      actualOutcome = outcome,
      scoreLabel = score,
      leagueOrType = league,
    )
  }
}

data class Nov18VerifiedResult(
  val titlePrefix: String,
  val outcome90Min: MatchOutcome,
  val scoreDisplay: String,
  val summaryNote: String,
)

data class ConsultationArchiveSeed(
  val title: String,
  val drawTime: String,
  val motherDotCounts: List<Int>,
  val league: String,
)

/**
 * Extrait automatiquement un résultat sportif réel ([MatchOutcome] + score) et la ligue/catégorie
 * à partir d'un texte libre (ex. "17-11-2026: 1-3", "Score final 2–1", "Ligue des Nations").
 */
object MatchOutcomeParser {
  private val scoreRegex = Regex("""(?<!\d)(\d{1,2})\s*[-–:]\s*(\d{1,2})(?!\d)""")
  private val datePrefixRegex = Regex("""\b\d{1,2}[-/]\d{1,2}[-/]\d{2,4}\b""")

  fun parse(explicitOutcomeName: String?, scoreText: String, notesText: String): Pair<MatchOutcome?, String> {
    val explicit =
      explicitOutcomeName
        ?.takeIf { it.isNotBlank() }
        ?.let { runCatching { MatchOutcome.valueOf(it) }.getOrNull() }

    val cleanedScore = scoreText.trim()
    val candidateText = if (cleanedScore.isNotEmpty()) cleanedScore else notesText
    val withoutDates = datePrefixRegex.replace(candidateText, " ")
    val match = scoreRegex.find(withoutDates)

    if (match != null) {
      val a = match.groupValues[1].toIntOrNull()
      val b = match.groupValues[2].toIntOrNull()
      if (a != null && b != null) {
        val parsedOutcome =
          when {
            a > b -> MatchOutcome.CAMP_A_WIN
            b > a -> MatchOutcome.CAMP_B_WIN
            else -> MatchOutcome.DRAW
          }
        return (explicit ?: parsedOutcome) to "$a-$b"
      }
    }

    if (explicit != null) {
      return explicit to cleanedScore
    }

    val lower = notesText.lowercase()
    if ("à compléter" in lower || "en attente" in lower) {
      return null to cleanedScore
    }
    val inferred =
      when {
        "match nul" in lower || "égalité" in lower || "nul" in lower -> MatchOutcome.DRAW
        "camp a" in lower -> MatchOutcome.CAMP_A_WIN
        "camp b" in lower -> MatchOutcome.CAMP_B_WIN
        else -> null
      }
    return inferred to cleanedScore
  }

  fun inferLeague(explicitLeague: String, title: String, notes: String): String {
    val clean = explicitLeague.trim()
    if (clean.isNotEmpty()) return clean
    val combined = "$title $notes".lowercase()
    return when {
      "copa del rey" in combined -> "Copa del Rey"
      "nations" in combined || "uefa" in combined -> "Ligue des Nations UEFA"
      "can" in combined || "caf" in combined -> "Qualifications CAN 2027"
      "coupe du monde" in combined || "mondial" in combined -> "Éliminatoires Mondial"
      "ligue" in combined || "championnat" in combined -> "Championnat / Ligue"
      "coupe" in combined -> "Coupe"
      "amical" in combined -> "Match Amical"
      else -> "Duel Libre"
    }
  }
}
