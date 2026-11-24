package com.example.geomancy

import kotlin.math.abs
import kotlin.math.roundToInt

/** Résultat sportif réel ou prédit d'un duel entre Camp A (domicile/consultant) et Camp B (adversaire). */
enum class MatchOutcome(val label: String, val shortLabel: String) {
  CAMP_A_WIN("Victoire Camp A", "Camp A"),
  DRAW("Match Nul", "Nul"),
  CAMP_B_WIN("Victoire Camp B", "Camp B"),
}

/**
 * Table des poids attribués aux 16 figures géomantiques pour le calcul d'Al-Ghālib wa-l-Maghlūb.
 * Les clés correspondent aux identifiants Taskīn (1..16) de [AlToukhiFigures].
 */
data class FigureWeightTable(
  val weightsByFigureId: Map<Int, Double>,
  val drawThreshold: Double = 3.0,
  val isCalibrated: Boolean = false,
) {
  fun weightFor(figure: GeomanticFigure): Double =
    weightsByFigureId[figure.id] ?: defaultWeightFor(figure)

  companion object {
    const val MIN_WEIGHT = -5.0
    const val MAX_WEIGHT = 5.0

    fun defaultWeightFor(figure: GeomanticFigure): Double =
      when (figure.polarity) {
        FortunePolarity.FORTUNATE ->
          when (figure.motion) {
            FigureMotion.ENTERING, FigureMotion.FIXED -> 3.0
            FigureMotion.MOBILE, FigureMotion.EXITING -> 2.0
          }
        FortunePolarity.MIXED ->
          when (figure.motion) {
            FigureMotion.ENTERING -> 1.0
            FigureMotion.FIXED, FigureMotion.MOBILE -> 0.0
            FigureMotion.EXITING -> -1.0
          }
        FortunePolarity.UNFORTUNATE ->
          when (figure.motion) {
            FigureMotion.FIXED -> -3.0
            FigureMotion.ENTERING, FigureMotion.MOBILE, FigureMotion.EXITING -> -2.0
          }
      }

    fun default(): FigureWeightTable =
      FigureWeightTable(
        weightsByFigureId = AlToukhiFigures.all.associate { it.id to defaultWeightFor(it) },
        drawThreshold = 3.0,
        isCalibrated = false,
      )

    fun formatWeight(value: Double): String {
      val rounded = (value * 10.0).roundToInt() / 10.0
      return if (abs(rounded - rounded.toInt()) < 0.05) {
        rounded.toInt().toString()
      } else {
        String.format(java.util.Locale.US, "%.1f", rounded)
      }
    }
  }
}

/** Indicateurs doctrinaux issus du traité Bulūgh al-Amal (Bāb 1, 3, 4, 14, 26, 29 & p. 97). */
data class BulughAlAmalDuelInsights(
  val totalChartDots: Int,
  val isOpenThemeAbove90: Boolean,
  val judgeParityValid: Boolean,
  val rightSideOpenPoints: Int,
  val leftSideOpenPoints: Int,
  val mawazinFortunateCount: Int,
  val mawazinUnfortunateCount: Int,
  val m11HopePolarity: FortunePolarity,
  val awtadCampABalance: Int,
  val campAReachesCrownM10: Boolean,
  val campBReachesCrownM10: Boolean,
  val doctrinalDeltaA: Double,
  val doctrinalDeltaB: Double,
  val activeRulesNotes: List<String>,
)

/** Indicateurs doctrinaux issus du traité « Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn » (Al-Baḥrī). */
data class AlFulkDuelInsights(
  val campAMod9Remainder: Int,
  val campBMod9Remainder: Int,
  val mod9Verdict: MatchOutcome,
  val mod9RuleNote: String,
  val oddHousesFortunateCount: Int,
  val evenHousesFortunateCount: Int,
  val oddVsEvenVerdict: MatchOutcome,
  val awtadSubJudge: GeomanticFigure,
  val awtadSubOutcome: GeomanticFigure,
  val awtadVerdict: MatchOutcome,
  val mod4TotalOddPoints: Int,
  val mod4Verdict: MatchOutcome,
  val campAFireComposite: GeomanticFigure,
  val campBFireComposite: GeomanticFigure,
  val consensusVerdict: MatchOutcome?,
  val convergenceLabel: String,
  val doctrinalDeltaA: Double,
  val doctrinalDeltaB: Double,
  val activeNotes: List<String>,
  val crossedAwtadA: GeomanticFigure? = null,
  val crossedAwtadB: GeomanticFigure? = null,
  val crossedAwtadVerdict: MatchOutcome? = null,
  val dignityBalanceNote: String = "",
)

/** Indicateurs doctrinaux issus de « Ḥāwī al-Akhbār wa Kanz al-Asrār » (Shaykh Ḥāzim al-Maqdisī). */
data class HawiAlAkhbarDuelInsights(
  val campAPairComposite: GeomanticFigure,
  val campBPairComposite: GeomanticFigure,
  val pairBalanceVerdict: MatchOutcome,
  val easternSultaniAdvantage: Int,
  val truceJamaaPresent: Boolean,
  val dignityBalanceA: Int,
  val dignityBalanceB: Int,
  val hawiVerdict: MatchOutcome,
  val summaryNote: String,
)

/** Niveau d'intensité disciplinaire estimé pour un match. */
enum class CardIntensityLevel(val label: String) {
  LOW("Calme"),
  MODERATE("Modérée"),
  HIGH("Élevée"),
  CRITICAL("Critique"),
}

/**
 * Précisions détaillées de match dérivées du thème géomantique à 16 maisons :
 * score exact probable, BTTS (les deux équipes marquent), cartons jaunes et risque de carton rouge.
 */
data class ContestMatchPrecisionForecast(
  val exactScoreCampA: Int,
  val exactScoreCampB: Int,
  val alternateScoreLabel: String,
  val totalGoalsRange: String,
  val bothTeamsToScore: Boolean,
  val bttsConfidencePercent: Int,
  val bttsReason: String,
  val yellowCardsCampA: IntRange,
  val yellowCardsCampB: IntRange,
  val yellowCardsTotalRange: String,
  val cardIntensity: CardIntensityLevel,
  val redCardLikely: Boolean,
  val redCardRiskPercent: Int,
  val redCardReason: String,
  val scoreReasoning: String,
) {
  val exactScoreLabel: String
    get() = "$exactScoreCampA – $exactScoreCampB"
}

/** Indicateurs doctrinaux issus d'« Al-Qawl al-Jāmiʿ » & Manuscrits de La Mecque (MS 2380 / 2405). */
data class AlQawlDuelInsights(
  val mothersOpenPoints: Int,
  val daughtersOpenPoints: Int,
  val m1M10Composite: GeomanticFigure,
  val m7M4Composite: GeomanticFigure,
  val aspectAdvantage: MatchOutcome,
  val alQawlVerdict: MatchOutcome,
  val summaryNote: String,
)

/** Résultat détaillé d'une prédiction de duel sur un thème à 16 maisons. */
data class ContestPrediction(
  val predictedOutcome: MatchOutcome,
  val sideAScore: Double,
  val sideBScore: Double,
  val campBCapturesOutcome: Boolean,
  val hasDrawSignal: Boolean,
  val summaryText: String,
  val bulughInsights: BulughAlAmalDuelInsights? = null,
  val alFulkInsights: AlFulkDuelInsights? = null,
  val hawiInsights: HawiAlAkhbarDuelInsights? = null,
  val alQawlInsights: AlQawlDuelInsights? = null,
  val matchPrecision: ContestMatchPrecisionForecast? = null,
)

/** Échantillon historique comparant un thème saisi avec son résultat sportif réel stocké. */
data class MatchCalibrationSample(
  val id: Long,
  val title: String,
  val motherDotCounts: List<Int>,
  val actualOutcome: MatchOutcome,
  val scoreLabel: String = "",
  val leagueOrType: String = "Duel Libre",
)

/** Résultat d'évaluation d'un match individuel avec une table de poids donnée. */
data class MatchEvaluationDetail(
  val sample: MatchCalibrationSample,
  val predictedOutcome: MatchOutcome,
  val sideAScore: Double,
  val sideBScore: Double,
  val isCorrect: Boolean,
  val judgeFigure: GeomanticFigure,
  val outcome16Figure: GeomanticFigure,
)

/** Statistique de précision (réussites vs échecs) agrégée par ligue ou par type de match. */
data class AccuracyCategoryStat(
  val categoryName: String,
  val totalMatches: Int,
  val baselineSuccessCount: Int,
  val calibratedSuccessCount: Int,
) {
  val baselineFailCount: Int
    get() = totalMatches - baselineSuccessCount

  val calibratedFailCount: Int
    get() = totalMatches - calibratedSuccessCount

  val baselineAccuracyPercent: Int
    get() = if (totalMatches == 0) 0 else ((baselineSuccessCount * 100.0) / totalMatches).roundToInt()

  val calibratedAccuracyPercent: Int
    get() = if (totalMatches == 0) 0 else ((calibratedSuccessCount * 100.0) / totalMatches).roundToInt()
}

/** Variation de poids observée pour une figure après apprentissage sur les résultats réels. */
data class FigureWeightDelta(
  val figure: GeomanticFigure,
  val baselineWeight: Double,
  val calibratedWeight: Double,
  val appearancesInSamples: Int,
) {
  val delta: Double
    get() = calibratedWeight - baselineWeight
}

/** Rapport complet produit par le module d'analyse prédictive et d'ajustement des poids. */
data class CalibrationReport(
  val totalSamples: Int,
  val baselineCorrectCount: Int,
  val calibratedCorrectCount: Int,
  val baselineAccuracyPercent: Int,
  val calibratedAccuracyPercent: Int,
  val calibratedWeights: FigureWeightTable,
  val evaluationsBefore: List<MatchEvaluationDetail>,
  val evaluationsAfter: List<MatchEvaluationDetail>,
  val accuracyByLeague: List<AccuracyCategoryStat>,
  val accuracyByMatchType: List<AccuracyCategoryStat>,
  val figureDeltas: List<FigureWeightDelta>,
)

/** Périmètre d'analyse de fréquence dans le Takht à 16 maisons. */
enum class FigureFrequencyScope(val label: String) {
  ALL_HOUSES("Toutes (M1–M16)"),
  MOTHERS_ONLY("Mères (M1–M4)"),
  JUDGES_AND_OUTCOMES("Juge & Issue (M15–M16)"),
}

/** Tendance statistique d'apparition d'une figure par rapport à l'équilibre théorique (1/16 = 6,25 %). */
enum class FigureTrendStatus(val label: String) {
  DOMINANT("Tendance forte"),
  ABOVE_AVERAGE("En hausse"),
  BALANCED("Équilibrée"),
  RARE("Sous-représentée"),
  ABSENT("Absente"),
}

/** Fréquence détaillée d'une figure géomantique à travers l'ensemble des archives locales. */
data class FigureFrequencyStat(
  val figure: GeomanticFigure,
  val totalAppearances: Int,
  val motherAppearances: Int,
  val derivedAppearances: Int,
  val judgeAndOutcomeAppearances: Int,
  val sharePercent: Double,
  val expectedSharePercent: Double,
  val trendStatus: FigureTrendStatus,
) {
  fun countForScope(scope: FigureFrequencyScope): Int =
    when (scope) {
      FigureFrequencyScope.ALL_HOUSES -> totalAppearances
      FigureFrequencyScope.MOTHERS_ONLY -> motherAppearances
      FigureFrequencyScope.JUDGES_AND_OUTCOMES -> judgeAndOutcomeAppearances
    }
}

/** Synthèse globale de fréquence et de tendance des 16 figures dans les archives. */
data class ArchiveFrequencyReport(
  val totalThemes: Int,
  val totalHouseSlots: Int,
  val statsByFigure: List<FigureFrequencyStat>,
  val elementDistribution: Map<Element, Int>,
  val polarityDistribution: Map<FortunePolarity, Int>,
  val dominantElement: Element?,
  val dominantPolarity: FortunePolarity?,
  val topFigures: List<FigureFrequencyStat>,
  val topVerdictFigures: List<FigureFrequencyStat>,
  val trendSummary: String,
)

/** Entrée minimale d'archive utilisée pour répertorier et compter les thèmes géomantiques identiques. */
data class ThemeArchiveInput(
  val id: Long,
  val title: String,
  val domain: QuestionDomain,
  val motherDotCounts: List<Int>,
  val createdAtMillis: Long,
  val actualOutcomeLabel: String? = null,
)

/** Occurence archivée correspondant à une signature géomantique donnée. */
data class ThemeOccurrenceSummary(
  val archiveId: Long,
  val title: String,
  val domain: QuestionDomain,
  val createdAtMillis: Long,
  val actualOutcomeLabel: String? = null,
)

/** Récurrence d'un thème géomantique complet identifié par sa signature des 4 Mères (M1/M2/M3/M4). */
data class ThemeSignatureRecurrence(
  val signatureCode: String,
  val mothersLabel: String,
  val judgeFigure: GeomanticFigure,
  val outcomeFigure: GeomanticFigure,
  val occurrenceCount: Int,
  val occurrences: List<ThemeOccurrenceSummary>,
)

/** Synthèse globale des thèmes déjà apparus dans les archives locales. */
data class ArchiveRecurrenceReport(
  val totalThemes: Int,
  val uniqueSignatures: Int,
  val recurringSignatureCount: Int,
  val recurringThemes: List<ThemeSignatureRecurrence>,
  val allSignaturesSorted: List<ThemeSignatureRecurrence>,
  val bySignature: Map<String, ThemeSignatureRecurrence>,
)

