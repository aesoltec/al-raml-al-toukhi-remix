package com.example.data

import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.AlToukhiGeomancyEngine
import com.example.geomancy.CalibrationReport
import com.example.geomancy.FigureWeightTable
import com.example.geomancy.MatchOutcome
import com.example.geomancy.MatchOutcomeParser
import com.example.geomancy.QuestionDomain
import com.example.geomancy.TakhtReading
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class ArchiveExportFormat(
  val label: String,
  val extension: String,
  val mimeType: String,
) {
  PLAIN_TEXT(
    label = "Texte (.txt)",
    extension = "txt",
    mimeType = "text/plain",
  ),
  MARKDOWN_REPORT(
    label = "Rapport (.md)",
    extension = "md",
    mimeType = "text/markdown",
  ),
}

object ArchiveReportExporter {

  private val dateTimeFormatter =
    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH)

  private val fileDateFormatter =
    SimpleDateFormat("yyyy-MM-dd", Locale.US)

  fun formatSingleArchive(
    entity: SavedThemeEntity,
    reading: TakhtReading,
    format: ArchiveExportFormat,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
    generatedAtMillis: Long = System.currentTimeMillis(),
  ): String {
    val dateLabel = dateTimeFormatter.format(Date(entity.createdAtMillis))
    val exportedLabel = dateTimeFormatter.format(Date(generatedAtMillis))
    val judge = reading.house(15).figure
    val outcome16 = reading.house(16).figure
    val contestPrediction =
      if (reading.domain == QuestionDomain.CONTEST) {
        AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable)
      } else {
        null
      }
    val (storedOutcome, storedScore) =
      MatchOutcomeParser.parse(
        explicitOutcomeName = entity.actualOutcomeName,
        scoreText = entity.actualScoreText,
        notesText = entity.contextOutcomeNotes,
      )
    val league = entity.leagueCategory.ifBlank {
      if (reading.domain == QuestionDomain.CONTEST) {
        MatchOutcomeParser.inferLeague("", entity.title, entity.contextOutcomeNotes)
      } else {
        ""
      }
    }

    return when (format) {
      ArchiveExportFormat.PLAIN_TEXT ->
        buildSinglePlainText(
          entity = entity,
          reading = reading,
          dateLabel = dateLabel,
          exportedLabel = exportedLabel,
          judgeName = "${judge.westAfricanName} (${judge.arabicName} · ${judge.code})",
          outcomeName = "${outcome16.westAfricanName} (${outcome16.arabicName} · ${outcome16.code})",
          contestPrediction = contestPrediction,
          storedOutcome = storedOutcome,
          storedScore = storedScore,
          league = league,
          weightTable = weightTable,
        )
      ArchiveExportFormat.MARKDOWN_REPORT ->
        buildSingleMarkdown(
          entity = entity,
          reading = reading,
          dateLabel = dateLabel,
          exportedLabel = exportedLabel,
          contestPrediction = contestPrediction,
          storedOutcome = storedOutcome,
          storedScore = storedScore,
          league = league,
          weightTable = weightTable,
        )
    }
  }

  private data class CategoryPrecisionStats(
    val outcomeEvaluated: Int = 0,
    val outcomeCorrect: Int = 0,
    val exactScoreEvaluated: Int = 0,
    val exactScoreHits: Int = 0,
    val bttsEvaluated: Int = 0,
    val bttsHits: Int = 0,
    val yellowEvaluated: Int = 0,
    val yellowInRangeHits: Int = 0,
    val redEvaluated: Int = 0,
    val redHits: Int = 0,
  )

  private fun parseScoreGoals(scoreText: String): Pair<Int, Int>? {
    val match = Regex("""(\d+)\s*[-–:]\s*(\d+)""").find(scoreText.trim()) ?: return null
    val a = match.groupValues[1].toIntOrNull() ?: return null
    val b = match.groupValues[2].toIntOrNull() ?: return null
    return a to b
  }

  private fun parseTotalYellowCards(text: String): Int? {
    val trimmed = text.trim()
    if (trimmed.isEmpty()) return null
    val splitPair = Regex("""(\d+)\s*[-–+/]\s*(\d+)""").find(trimmed)
    if (splitPair != null) {
      val a = splitPair.groupValues[1].toIntOrNull()
      val b = splitPair.groupValues[2].toIntOrNull()
      if (a != null && b != null) return a + b
    }
    return Regex("""\d+""").find(trimmed)?.value?.toIntOrNull()
  }

  private fun parseYellowRange(rangeText: String): IntRange? {
    val nums = Regex("""\d+""").findAll(rangeText).mapNotNull { it.value.toIntOrNull() }.toList()
    return when {
      nums.size >= 2 -> nums[0]..nums[1]
      nums.size == 1 -> nums[0]..nums[0]
      else -> null
    }
  }

  private fun parseRedCardOccurred(text: String): Boolean? {
    val clean = text.trim().lowercase(Locale.FRENCH)
    if (clean.isEmpty()) return null
    if (clean.startsWith("oui") || clean.startsWith("yes")) return true
    if (clean.startsWith("non") || clean.startsWith("no") || clean == "aucun") return false
    val firstNum = Regex("""\d+""").find(clean)?.value?.toIntOrNull() ?: return null
    return firstNum > 0
  }

  private fun computeCategoryPrecisionStats(
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    weightTable: FigureWeightTable,
  ): CategoryPrecisionStats {
    var outcomeEvaluated = 0
    var outcomeCorrect = 0
    var exactScoreEvaluated = 0
    var exactScoreHits = 0
    var bttsEvaluated = 0
    var bttsHits = 0
    var yellowEvaluated = 0
    var yellowInRangeHits = 0
    var redEvaluated = 0
    var redHits = 0

    entities.forEach { entity ->
      val reading = reconstructReading(entity)
      if (reading.domain != QuestionDomain.CONTEST) return@forEach
      val pred = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable)
      val mp = pred.matchPrecision
      val (storedOutcome, storedScore) =
        MatchOutcomeParser.parse(entity.actualOutcomeName, entity.actualScoreText, entity.contextOutcomeNotes)

      if (storedOutcome != null) {
        outcomeEvaluated++
        if (pred.predictedOutcome == storedOutcome) outcomeCorrect++
      }
      if (mp != null) {
        val goals = parseScoreGoals(storedScore)
        if (goals != null) {
          if (entity.includeExactScoreOption) {
            exactScoreEvaluated++
            if (goals.first == mp.exactScoreCampA && goals.second == mp.exactScoreCampB) {
              exactScoreHits++
            }
            bttsEvaluated++
            val actualBtts = goals.first > 0 && goals.second > 0
            if (actualBtts == mp.bothTeamsToScore) {
              bttsHits++
            }
          }
        }
        val actualYellow = parseTotalYellowCards(entity.actualYellowCardsText)
        val yellowRange = parseYellowRange(mp.yellowCardsTotalRange)
        if (entity.includeYellowCardsOption && actualYellow != null && yellowRange != null) {
          yellowEvaluated++
          if (actualYellow in yellowRange) yellowInRangeHits++
        }
        val actualRed = parseRedCardOccurred(entity.actualRedCardsText)
        if (entity.includeRedCardsOption && actualRed != null) {
          redEvaluated++
          if (actualRed == mp.redCardLikely) redHits++
        }
      }
    }

    return CategoryPrecisionStats(
      outcomeEvaluated = outcomeEvaluated,
      outcomeCorrect = outcomeCorrect,
      exactScoreEvaluated = exactScoreEvaluated,
      exactScoreHits = exactScoreHits,
      bttsEvaluated = bttsEvaluated,
      bttsHits = bttsHits,
      yellowEvaluated = yellowEvaluated,
      yellowInRangeHits = yellowInRangeHits,
      redEvaluated = redEvaluated,
      redHits = redHits,
    )
  }

  fun formatCategoryArchives(
    categoryName: String,
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    calibrationReport: CalibrationReport? = null,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
    format: ArchiveExportFormat,
    generatedAtMillis: Long = System.currentTimeMillis(),
  ): String {
    val exportedLabel = dateTimeFormatter.format(Date(generatedAtMillis))
    val precisionStats = computeCategoryPrecisionStats(entities, reconstructReading, weightTable)
    return when (format) {
      ArchiveExportFormat.PLAIN_TEXT ->
        buildCategoryPlainText(
          categoryName = categoryName,
          entities = entities,
          reconstructReading = reconstructReading,
          calibrationReport = calibrationReport,
          precisionStats = precisionStats,
          weightTable = weightTable,
          exportedLabel = exportedLabel,
          generatedAtMillis = generatedAtMillis,
        )
      ArchiveExportFormat.MARKDOWN_REPORT ->
        buildCategoryMarkdown(
          categoryName = categoryName,
          entities = entities,
          reconstructReading = reconstructReading,
          calibrationReport = calibrationReport,
          precisionStats = precisionStats,
          weightTable = weightTable,
          exportedLabel = exportedLabel,
          generatedAtMillis = generatedAtMillis,
        )
    }
  }

  fun formatAllArchives(
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    calibrationReport: CalibrationReport? = null,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
    format: ArchiveExportFormat,
    generatedAtMillis: Long = System.currentTimeMillis(),
  ): String {
    val exportedLabel = dateTimeFormatter.format(Date(generatedAtMillis))
    return when (format) {
      ArchiveExportFormat.PLAIN_TEXT ->
        buildAllPlainText(
          entities = entities,
          reconstructReading = reconstructReading,
          calibrationReport = calibrationReport,
          weightTable = weightTable,
          exportedLabel = exportedLabel,
          generatedAtMillis = generatedAtMillis,
        )
      ArchiveExportFormat.MARKDOWN_REPORT ->
        buildAllMarkdown(
          entities = entities,
          reconstructReading = reconstructReading,
          calibrationReport = calibrationReport,
          weightTable = weightTable,
          exportedLabel = exportedLabel,
          generatedAtMillis = generatedAtMillis,
        )
    }
  }

  fun suggestedFileName(
    entity: SavedThemeEntity?,
    format: ArchiveExportFormat,
    nowMillis: Long = System.currentTimeMillis(),
  ): String = suggestedFileName(entity = entity, categoryName = null, format = format, nowMillis = nowMillis)

  fun suggestedFileName(
    entity: SavedThemeEntity?,
    categoryName: String?,
    format: ArchiveExportFormat,
    nowMillis: Long = System.currentTimeMillis(),
  ): String {
    val ext = format.extension
    if (entity != null) {
      val normalized = slugify(entity.title).ifEmpty { "theme_${entity.id}" }
      return "al_raml_${normalized}.$ext"
    }
    val dateStamp = fileDateFormatter.format(Date(nowMillis))
    if (!categoryName.isNullOrBlank()) {
      val catSlug = slugify(categoryName).ifEmpty { "categorie" }
      return "al_raml_cat_${catSlug}_$dateStamp.$ext"
    }
    return "al_raml_archives_complet_$dateStamp.$ext"
  }

  private fun slugify(raw: String): String =
    Normalizer.normalize(raw, Normalizer.Form.NFD)
      .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
      .lowercase(Locale.US)
      .replace(Regex("[^a-z0-9]+"), "_")
      .trim('_')
      .take(42)

  private fun buildSinglePlainText(
    entity: SavedThemeEntity,
    reading: TakhtReading,
    dateLabel: String,
    exportedLabel: String,
    judgeName: String,
    outcomeName: String,
    contestPrediction: com.example.geomancy.ContestPrediction?,
    storedOutcome: MatchOutcome?,
    storedScore: String,
    league: String,
    weightTable: FigureWeightTable,
  ): String =
    buildString {
      appendLine("============================================================")
      appendLine("AL-RAML AL-TOUKHI — ANALYSE GÉOMANTIQUE ARCHIVÉE")
      appendLine("============================================================")
      appendLine("Titre : ${entity.title}")
      appendLine("Enregistré le : $dateLabel | Exporté le : $exportedLabel")
      appendLine("Domaine : ${reading.domain.title} (${reading.domain.arabicTitle})")
      if (league.isNotEmpty()) {
        appendLine("Catégorie / Compétition : $league")
      }
      if (entity.questionText.isNotBlank() && entity.questionText != entity.title) {
        appendLine("Intention (Niyya) : « ${entity.questionText} »")
      }
      appendLine(
        "Heure planétaire : ${reading.activePlanetHour.englishName} (${reading.activePlanetHour.arabicName})",
      )
      appendLine(
        "Mères (M1–M4) : " +
          (1..4).joinToString(" | ") { n ->
            val fig = reading.house(n).figure
            "M$n ${fig.westAfricanName} (${fig.code})"
          },
      )
      appendLine("Comptes de traits (16 lignes) : ${entity.motherDotCountsCsv}")
      appendLine()
      appendLine("--- VERDICT DU TAKHT ---")
      appendLine("Polarité globale : ${reading.analysis.overallPolarity.label}")
      appendLine("Juge (M15) : $judgeName")
      appendLine("Issue Finale (M16) : $outcomeName")
      appendLine("Synthèse : ${reading.analysis.verdictHeadline}")
      appendLine(reading.analysis.verdictDetail)

      if (contestPrediction != null) {
        val modeLabel =
          if (weightTable.isCalibrated) "Poids calibrés actifs" else "Poids de base Al-Toukhi"
        appendLine()
        appendLine("--- PRONOSTIC AL-GHĀLIB WA-L-MAGHLŪB ($modeLabel) ---")
        appendLine("Pronostic : ${contestPrediction.predictedOutcome.label}")
        appendLine(
          "Scores internes : Camp A = ${FigureWeightTable.formatWeight(contestPrediction.sideAScore)} | Camp B = ${FigureWeightTable.formatWeight(contestPrediction.sideBScore)}",
        )
        contestPrediction.bulughInsights?.let { b ->
          appendLine(
            "Règles Bulūgh al-Amal : ${b.totalChartDots}/128 pts | Droite ${b.rightSideOpenPoints} vs Gauche ${b.leftSideOpenPoints} pts ouverts | Mawāzīn ${b.mawazinFortunateCount}F/${b.mawazinUnfortunateCount}D",
          )
        }
        contestPrediction.matchPrecision?.let { mp ->
          if (entity.includeExactScoreOption || entity.includeYellowCardsOption || entity.includeRedCardsOption) {
            appendLine("--- OPTIONS DE PRÉCISION MATCH ---")
            if (entity.includeExactScoreOption) {
              appendLine(
                "Score exact probable : ${mp.exactScoreLabel} (alt. ${mp.alternateScoreLabel}, buts ${mp.totalGoalsRange}) | BTTS : ${if (mp.bothTeamsToScore) "Oui" else "Non"} (${mp.bttsConfidencePercent}%)",
              )
            }
            if (entity.includeYellowCardsOption) {
              appendLine(
                "Cartons jaunes prévus : ${mp.yellowCardsTotalRange} (${mp.cardIntensity.label}) · Camp A ${mp.yellowCardsCampA.first}–${mp.yellowCardsCampA.last} | Camp B ${mp.yellowCardsCampB.first}–${mp.yellowCardsCampB.last}",
              )
            }
            if (entity.includeRedCardsOption) {
              appendLine(
                "Carton rouge : ${if (mp.redCardLikely) "Risque élevé" else "Peu probable"} (${mp.redCardRiskPercent}%) — ${mp.redCardReason}",
              )
            }
          }
        }
        appendLine(contestPrediction.summaryText)
      }

      val hasObservedData =
        storedOutcome != null ||
          storedScore.isNotEmpty() ||
          entity.actualYellowCardsText.isNotBlank() ||
          entity.actualRedCardsText.isNotBlank() ||
          entity.contextOutcomeNotes.isNotBlank()
      if (hasObservedData) {
        appendLine()
        appendLine("--- CONTEXTE & RÉSULTAT RÉEL ---")
        if (storedOutcome != null || storedScore.isNotEmpty()) {
          val scoreStr = storedScore.ifEmpty { "non précisé" }
          val outcomeStr = storedOutcome?.label ?: "non précisé"
          appendLine("Résultat officiel : $outcomeStr (Score : $scoreStr)")
          if (contestPrediction != null && storedOutcome != null) {
            val status =
              if (contestPrediction.predictedOutcome == storedOutcome) "RÉUSSITE" else "ÉCHEC"
            appendLine("Vérification issue : $status")
          }
        }
        if (entity.actualYellowCardsText.isNotBlank()) {
          appendLine("Cartons jaunes réels : ${entity.actualYellowCardsText}")
        }
        if (entity.actualRedCardsText.isNotBlank()) {
          appendLine("Cartons rouges réels : ${entity.actualRedCardsText}")
        }
        if (entity.contextOutcomeNotes.isNotBlank()) {
          appendLine("Notes : ${entity.contextOutcomeNotes}")
        }
      }

      appendLine()
      appendLine("--- LES 16 MAISONS DU TAKHT ---")
      reading.houses.forEach { house ->
        val f = house.figure
        appendLine(
          String.format(
            Locale.US,
            "M%02d %-26s : %-25s (%s) | %s | %s",
            house.info.number,
            house.info.englishName,
            f.westAfricanName,
            f.code,
            f.dominantElement.englishName,
            f.polarity.label,
          ),
        )
      }

      appendLine()
      appendLine("--- LECTURES AVANCÉES (AL-TOUKHI) ---")
      appendLine("Pensée cachée (Ḍamīr) : ${reading.analysis.damirReport.unspokenFocus}")
      appendLine("Résonance Taskīn : ${reading.analysis.damirReport.taskinResonance}")
      appendLine("Migration du Consultant : ${reading.analysis.querentMigration.interpretation}")
      appendLine("Migration du Sujet : ${reading.analysis.domainMigration.interpretation}")
      appendLine("Encadrement du Juge : ${reading.analysis.judgeFlanking.status}")
      appendLine(
        "Lettre Abjad : ${reading.analysis.abjadExtraction.extractedLetter} (${reading.analysis.abjadExtraction.letterName}, total ${reading.analysis.abjadExtraction.totalAbjadSum} mod 28 = ${reading.analysis.abjadExtraction.reducedMod28}) · Délai : ${reading.analysis.abjadExtraction.timingEstimate}",
      )
      appendLine("Humeurs & Éléments : ${reading.analysis.humoralDiagnosis}")
      appendLine("Prescription spirituelle : ${reading.analysis.spiritualPrescription}")
    }

  private fun buildSingleMarkdown(
    entity: SavedThemeEntity,
    reading: TakhtReading,
    dateLabel: String,
    exportedLabel: String,
    contestPrediction: com.example.geomancy.ContestPrediction?,
    storedOutcome: MatchOutcome?,
    storedScore: String,
    league: String,
    weightTable: FigureWeightTable,
  ): String =
    buildString {
      val judge = reading.house(15).figure
      val outcome16 = reading.house(16).figure

      appendLine("# Rapport Géomantique : ${entity.title}")
      appendLine()
      appendLine("- **Date du tirage** : $dateLabel")
      appendLine("- **Rapport généré le** : $exportedLabel")
      appendLine("- **Domaine** : ${reading.domain.title} (*${reading.domain.arabicTitle}*)")
      if (league.isNotEmpty()) {
        appendLine("- **Catégorie / Compétition** : $league")
      }
      if (entity.questionText.isNotBlank() && entity.questionText != entity.title) {
        appendLine("- **Intention (Niyya)** : « ${entity.questionText} »")
      }
      appendLine(
        "- **Heure planétaire** : ${reading.activePlanetHour.englishName} (${reading.activePlanetHour.arabicName})",
      )
      appendLine("- **Traits bruts (M1–M4)** : `${entity.motherDotCountsCsv}`")
      appendLine()
      appendLine("## Synthèse & Verdict du Juge")
      appendLine()
      appendLine("- **Polarité globale** : ${reading.analysis.overallPolarity.label}")
      appendLine(
        "- **Juge (M15)** : **${judge.westAfricanName}** (*${judge.arabicName}*, `${judge.code}` — ${judge.polarity.label})",
      )
      appendLine(
        "- **Issue Finale (M16)** : **${outcome16.westAfricanName}** (*${outcome16.arabicName}*, `${outcome16.code}` — ${outcome16.motion.label})",
      )
      appendLine("- **Sentence** : ${reading.analysis.verdictHeadline}")
      appendLine()
      appendLine("> ${reading.analysis.verdictDetail}")

      if (contestPrediction != null) {
        val modeLabel =
          if (weightTable.isCalibrated) "Poids calibrés actifs" else "Poids de base d’Al-Toukhi"
        appendLine()
        appendLine("## Pronostic Sportif (*Al-Ghālib wa-l-Maghlūb*)")
        appendLine()
        appendLine("- **Moteur actif** : $modeLabel")
        appendLine("- **Issue prévue** : **${contestPrediction.predictedOutcome.label}**")
        appendLine(
          "- **Score géomantique** : Camp A `${FigureWeightTable.formatWeight(contestPrediction.sideAScore)}` vs Camp B `${FigureWeightTable.formatWeight(contestPrediction.sideBScore)}`",
        )
        contestPrediction.bulughInsights?.let { b ->
          appendLine(
            "- **Indicateurs *Bulūgh al-Amal*** : `${b.totalChartDots}/128 pts` | Droite `${b.rightSideOpenPoints}` vs Gauche `${b.leftSideOpenPoints}` pts ouverts | *Mawāzīn* `${b.mawazinFortunateCount}F/${b.mawazinUnfortunateCount}D`",
          )
        }
        contestPrediction.matchPrecision?.let { mp ->
          if (entity.includeExactScoreOption) {
            appendLine(
              "- **Score exact & BTTS** : `${mp.exactScoreLabel}` (alt. `${mp.alternateScoreLabel}`, ${mp.totalGoalsRange}) · Les 2 marquent : **${if (mp.bothTeamsToScore) "Oui" else "Non"}** (${mp.bttsConfidencePercent}%)",
            )
          }
          if (entity.includeYellowCardsOption) {
            appendLine(
              "- **Cartons jaunes prévus** : `${mp.yellowCardsTotalRange}` (${mp.cardIntensity.label}) · Camp A `${mp.yellowCardsCampA.first}–${mp.yellowCardsCampA.last}` | Camp B `${mp.yellowCardsCampB.first}–${mp.yellowCardsCampB.last}`",
            )
          }
          if (entity.includeRedCardsOption) {
            appendLine(
              "- **Carton rouge** : **${if (mp.redCardLikely) "Risque élevé" else "Peu probable"}** (${mp.redCardRiskPercent}%) — ${mp.redCardReason}",
            )
          }
        }
        appendLine("- **Détail** : ${contestPrediction.summaryText}")
      }

      val hasObservedData =
        storedOutcome != null ||
          storedScore.isNotEmpty() ||
          entity.actualYellowCardsText.isNotBlank() ||
          entity.actualRedCardsText.isNotBlank() ||
          entity.contextOutcomeNotes.isNotBlank()
      if (hasObservedData) {
        appendLine()
        appendLine("## Contexte & Suivi Réel")
        appendLine()
        if (storedOutcome != null || storedScore.isNotEmpty()) {
          appendLine(
            "- **Résultat enregistré** : ${storedOutcome?.label ?: "Non précisé"} (${storedScore.ifEmpty { "score non renseigné" }})",
          )
          if (contestPrediction != null && storedOutcome != null) {
            val badge =
              if (contestPrediction.predictedOutcome == storedOutcome) "Réussite" else "Échec"
            appendLine("- **Statut de l’issue** : **$badge**")
          }
        }
        if (entity.actualYellowCardsText.isNotBlank()) {
          appendLine("- **Cartons jaunes réels** : `${entity.actualYellowCardsText}`")
        }
        if (entity.actualRedCardsText.isNotBlank()) {
          appendLine("- **Cartons rouges réels** : `${entity.actualRedCardsText}`")
        }
        if (entity.contextOutcomeNotes.isNotBlank()) {
          appendLine("- **Notes de consultation** : ${entity.contextOutcomeNotes}")
        }
      }

      appendLine()
      appendLine("## Tableau des 16 Maisons (*Takht al-Raml*)")
      appendLine()
      appendLine("| Maison | Nom traditionnel | Figure (Afrique de l'Ouest) | Nom arabe | Code | Élément | Polarité |")
      appendLine("| :--- | :--- | :--- | :--- | :---: | :--- | :--- |")
      reading.houses.forEach { house ->
        val f = house.figure
        appendLine(
          "| M${house.info.number} | ${house.info.englishName} | **${f.westAfricanName}** | ${f.arabicName} | `${f.code}` | ${f.dominantElement.englishName} | ${f.polarity.label} |",
        )
      }

      appendLine()
      appendLine("## Analyses Approfondies (Cheikh al-Toukhi)")
      appendLine()
      appendLine("- **Pensée cachée (*Ḍamīr*)** : ${reading.analysis.damirReport.unspokenFocus}")
      appendLine("- **Résonance du Taskīn** : ${reading.analysis.damirReport.taskinResonance}")
      appendLine("- **Migration M1** : ${reading.analysis.querentMigration.interpretation}")
      appendLine("- **Migration du Sujet** : ${reading.analysis.domainMigration.interpretation}")
      appendLine("- **Encadrement M15** : ${reading.analysis.judgeFlanking.status}")
      appendLine(
        "- **Extraction Abjad** : ${reading.analysis.abjadExtraction.extractedLetter} (*${reading.analysis.abjadExtraction.letterName}*, somme ${reading.analysis.abjadExtraction.totalAbjadSum} mod 28 = ${reading.analysis.abjadExtraction.reducedMod28}) — Délai : ${reading.analysis.abjadExtraction.timingEstimate}",
      )
      appendLine("- **Diagnostic humoral** : ${reading.analysis.humoralDiagnosis}")
      appendLine("- **Conseil spirituel** : ${reading.analysis.spiritualPrescription}")
    }

  private fun buildCategoryPlainText(
    categoryName: String,
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    calibrationReport: CalibrationReport?,
    precisionStats: CategoryPrecisionStats,
    weightTable: FigureWeightTable,
    exportedLabel: String,
    generatedAtMillis: Long,
  ): String =
    buildString {
      appendLine("============================================================")
      appendLine("AL-RAML AL-TOUKHI — EXPORT CATÉGORIE : $categoryName (${entities.size} THÈMES)")
      appendLine("Généré le : $exportedLabel")
      appendLine(
        "Moteur actif : ${if (weightTable.isCalibrated) "Poids calibrés" else "Poids de base Al-Toukhi"}",
      )
      appendLine("============================================================")
      appendLine()

      if (calibrationReport != null && calibrationReport.totalSamples > 0) {
        appendLine("--- ANALYSE DE PRÉCISION DE LA CATÉGORIE ($categoryName) ---")
        appendLine(
          "Rencontres évaluées : ${calibrationReport.totalSamples} | Base : ${calibrationReport.baselineCorrectCount}/${calibrationReport.totalSamples} (${calibrationReport.baselineAccuracyPercent}%) | Calibré : ${calibrationReport.calibratedCorrectCount}/${calibrationReport.totalSamples} (${calibrationReport.calibratedAccuracyPercent}%)",
        )
      }
      if (precisionStats.exactScoreEvaluated > 0 || precisionStats.yellowEvaluated > 0 || precisionStats.redEvaluated > 0) {
        appendLine(
          "Score exact : ${precisionStats.exactScoreHits}/${precisionStats.exactScoreEvaluated} | " +
            "BTTS : ${precisionStats.bttsHits}/${precisionStats.bttsEvaluated} | " +
            "Cartons J (dans la fourchette) : ${precisionStats.yellowInRangeHits}/${precisionStats.yellowEvaluated} | " +
            "Carton R : ${precisionStats.redHits}/${precisionStats.redEvaluated}",
        )
      }
      if (calibrationReport != null) {
        val activeDeltas = calibrationReport.figureDeltas.filter { it.appearancesInSamples > 0 && kotlin.math.abs(it.delta) >= 0.05 }
        if (activeDeltas.isNotEmpty()) {
          appendLine("Poids les plus ajustés sur cette catégorie :")
          activeDeltas.take(6).forEach { d ->
            val sign = if (d.delta > 0) "+" else ""
            appendLine(
              "  • ${d.figure.westAfricanName} : ${FigureWeightTable.formatWeight(d.baselineWeight)} → ${FigureWeightTable.formatWeight(d.calibratedWeight)} ($sign${FigureWeightTable.formatWeight(d.delta)}, ${d.appearancesInSamples} appar.)",
            )
          }
        }
      }
      appendLine()

      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        val judge = AlToukhiFigures.findById(entity.judgeFigureId)
        val outcome = AlToukhiFigures.findById(entity.outcomeFigureId)
        val contestPred =
          if (reading.domain == QuestionDomain.CONTEST) {
            AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable).predictedOutcome.label
          } else {
            reading.analysis.overallPolarity.label
          }
        appendLine(
          "${idx + 1}. ${entity.title} | Juge M15: ${judge.westAfricanName} | Issue M16: ${outcome.westAfricanName} | Tendance: $contestPred | Score réel: ${entity.actualScoreText.ifBlank { "-" }} | J: ${entity.actualYellowCardsText.ifBlank { "-" }} | R: ${entity.actualRedCardsText.ifBlank { "-" }}",
        )
      }
      appendLine()

      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        appendLine()
        appendLine("########## $categoryName — THÈME #${idx + 1} ##########")
        append(
          formatSingleArchive(
            entity = entity,
            reading = reading,
            format = ArchiveExportFormat.PLAIN_TEXT,
            weightTable = weightTable,
            generatedAtMillis = generatedAtMillis,
          ),
        )
      }
    }

  private fun buildCategoryMarkdown(
    categoryName: String,
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    calibrationReport: CalibrationReport?,
    precisionStats: CategoryPrecisionStats,
    weightTable: FigureWeightTable,
    exportedLabel: String,
    generatedAtMillis: Long,
  ): String =
    buildString {
      appendLine("# Export par Catégorie : $categoryName — Al-Raml Al-Toukhi")
      appendLine()
      appendLine("- **Catégorie** : $categoryName")
      appendLine("- **Date d'exportation** : $exportedLabel")
      appendLine("- **Nombre de tirages** : ${entities.size}")
      appendLine(
        "- **Mode d'évaluation** : ${if (weightTable.isCalibrated) "Poids calibrés actifs" else "Poids de base d'Al-Toukhi"}",
      )
      appendLine()

      if ((calibrationReport != null && calibrationReport.totalSamples > 0) ||
        precisionStats.exactScoreEvaluated > 0 ||
        precisionStats.yellowEvaluated > 0 ||
        precisionStats.redEvaluated > 0
      ) {
        appendLine("## Analyse de Précision — $categoryName")
        appendLine()
        if (calibrationReport != null && calibrationReport.totalSamples > 0) {
          appendLine(
            "- **Issue 1X2 (Base Al-Toukhi)** : ${calibrationReport.baselineCorrectCount}/${calibrationReport.totalSamples} (**${calibrationReport.baselineAccuracyPercent}%**)",
          )
          appendLine(
            "- **Issue 1X2 (Modèle calibré)** : ${calibrationReport.calibratedCorrectCount}/${calibrationReport.totalSamples} (**${calibrationReport.calibratedAccuracyPercent}%**)",
          )
        }
        if (precisionStats.exactScoreEvaluated > 0) {
          appendLine(
            "- **Score exact** : ${precisionStats.exactScoreHits}/${precisionStats.exactScoreEvaluated} | **Les 2 équipes marquent (BTTS)** : ${precisionStats.bttsHits}/${precisionStats.bttsEvaluated}",
          )
        }
        if (precisionStats.yellowEvaluated > 0 || precisionStats.redEvaluated > 0) {
          appendLine(
            "- **Discipline** : Cartons jaunes dans la fourchette ${precisionStats.yellowInRangeHits}/${precisionStats.yellowEvaluated} | Carton rouge ${precisionStats.redHits}/${precisionStats.redEvaluated}",
          )
        }
        calibrationReport?.figureDeltas?.filter { it.appearancesInSamples > 0 && kotlin.math.abs(it.delta) >= 0.05 }?.let { deltas ->
          if (deltas.isNotEmpty()) {
            appendLine()
            appendLine("### Poids de figures suggérés pour cette catégorie")
            appendLine()
            appendLine("| Figure | Poids base | Poids calibré | Écart | Apparitions |")
            appendLine("| :--- | :---: | :---: | :---: | :---: |")
            deltas.take(8).forEach { d ->
              val sign = if (d.delta > 0) "+" else ""
              appendLine(
                "| ${d.figure.westAfricanName} | ${FigureWeightTable.formatWeight(d.baselineWeight)} | **${FigureWeightTable.formatWeight(d.calibratedWeight)}** | `$sign${FigureWeightTable.formatWeight(d.delta)}` | ${d.appearancesInSamples} |",
              )
            }
          }
        }
        appendLine()
      }

      appendLine("## Tableau Comparatif ($categoryName)")
      appendLine()
      appendLine("| # | Titre | Juge (M15) | Pronostic | Score prévu / réel | Cartons J (prév/réel) | Rouge (prév/réel) |")
      appendLine("| :---: | :--- | :--- | :--- | :---: | :---: | :---: |")
      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        val judge = reading.house(15).figure
        val contestPred =
          if (reading.domain == QuestionDomain.CONTEST) {
            AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable)
          } else {
            null
          }
        val predLabel = contestPred?.predictedOutcome?.shortLabel ?: reading.analysis.overallPolarity.label
        val mp = contestPred?.matchPrecision
        val scoreCol =
          if (mp != null && entity.includeExactScoreOption) {
            "${mp.exactScoreLabel} / ${entity.actualScoreText.ifBlank { "—" }}"
          } else {
            entity.actualScoreText.ifBlank { "—" }
          }
        val yellowCol =
          if (mp != null && entity.includeYellowCardsOption) {
            "${mp.yellowCardsTotalRange} / ${entity.actualYellowCardsText.ifBlank { "—" }}"
          } else {
            entity.actualYellowCardsText.ifBlank { "—" }
          }
        val redCol =
          if (mp != null && entity.includeRedCardsOption) {
            "${if (mp.redCardLikely) "Oui" else "Non"} (${mp.redCardRiskPercent}%) / ${entity.actualRedCardsText.ifBlank { "—" }}"
          } else {
            entity.actualRedCardsText.ifBlank { "—" }
          }
        appendLine(
          "| ${idx + 1} | ${entity.title} | ${judge.westAfricanName} | $predLabel | $scoreCol | $yellowCol | $redCol |",
        )
      }

      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        appendLine()
        appendLine("---")
        appendLine()
        appendLine("## Consultation #${idx + 1}")
        appendLine()
        append(
          formatSingleArchive(
            entity = entity,
            reading = reading,
            format = ArchiveExportFormat.MARKDOWN_REPORT,
            weightTable = weightTable,
            generatedAtMillis = generatedAtMillis,
          ),
        )
      }
    }

  private fun buildAllPlainText(
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    calibrationReport: CalibrationReport?,
    weightTable: FigureWeightTable,
    exportedLabel: String,
    generatedAtMillis: Long,
  ): String =
    buildString {
      appendLine("============================================================")
      appendLine("AL-RAML AL-TOUKHI — EXPORT COMPLET DES ARCHIVES (${entities.size} THÈMES)")
      appendLine("Généré le : $exportedLabel")
      appendLine(
        "Moteur actif : ${if (weightTable.isCalibrated) "Poids calibrés" else "Poids de base Al-Toukhi"}",
      )
      appendLine("============================================================")
      appendLine()

      if (calibrationReport != null && calibrationReport.totalSamples > 0) {
        appendLine("--- BILAN PRÉDICTIF DES RENCONTRES ÉVALUÉES ---")
        appendLine(
          "Rencontres avec score réel : ${calibrationReport.totalSamples} | Base : ${calibrationReport.baselineCorrectCount}/${calibrationReport.totalSamples} (${calibrationReport.baselineAccuracyPercent}%) | Calibré : ${calibrationReport.calibratedCorrectCount}/${calibrationReport.totalSamples} (${calibrationReport.calibratedAccuracyPercent}%)",
        )
        calibrationReport.accuracyByLeague.forEach { stat ->
          appendLine(
            "  • ${stat.categoryName} : ${stat.calibratedSuccessCount}/${stat.totalMatches} (${stat.calibratedAccuracyPercent}%)",
          )
        }
        appendLine()
      }

      val grouped = SavedThemeRepository.groupByCategory(entities)
      grouped.forEach { (cat, catEntities) ->
        appendLine("=== CATÉGORIE : $cat (${catEntities.size}) ===")
        catEntities.forEachIndexed { idx, entity ->
          val reading = reconstructReading(entity)
          val judge = AlToukhiFigures.findById(entity.judgeFigureId)
          val outcome = AlToukhiFigures.findById(entity.outcomeFigureId)
          val contestPred =
            if (reading.domain == QuestionDomain.CONTEST) {
              AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable).predictedOutcome.label
            } else {
              reading.analysis.overallPolarity.label
            }
          appendLine(
            "  ${idx + 1}. ${entity.title} | Juge M15: ${judge.westAfricanName} | Issue M16: ${outcome.westAfricanName} | Tendance: $contestPred",
          )
        }
        appendLine()
      }

      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        appendLine()
        appendLine("########## THÈME #${idx + 1} ##########")
        append(
          formatSingleArchive(
            entity = entity,
            reading = reading,
            format = ArchiveExportFormat.PLAIN_TEXT,
            weightTable = weightTable,
            generatedAtMillis = generatedAtMillis,
          ),
        )
      }
    }

  private fun buildAllMarkdown(
    entities: List<SavedThemeEntity>,
    reconstructReading: (SavedThemeEntity) -> TakhtReading,
    calibrationReport: CalibrationReport?,
    weightTable: FigureWeightTable,
    exportedLabel: String,
    generatedAtMillis: Long,
  ): String =
    buildString {
      appendLine("# Archives Complètes — Al-Raml Al-Toukhi")
      appendLine()
      appendLine("- **Date d'exportation** : $exportedLabel")
      appendLine("- **Nombre de tirages archivés** : ${entities.size}")
      appendLine(
        "- **Mode d'évaluation** : ${if (weightTable.isCalibrated) "Poids calibrés actifs" else "Poids de base d'Al-Toukhi"}",
      )
      appendLine()

      if (calibrationReport != null && calibrationReport.totalSamples > 0) {
        appendLine("## Synthèse de Précision Prédictive")
        appendLine()
        appendLine(
          "- **Précision de base** : ${calibrationReport.baselineCorrectCount}/${calibrationReport.totalSamples} (**${calibrationReport.baselineAccuracyPercent}%**)",
        )
        appendLine(
          "- **Précision après calibrage** : ${calibrationReport.calibratedCorrectCount}/${calibrationReport.totalSamples} (**${calibrationReport.calibratedAccuracyPercent}%**)",
        )
        if (calibrationReport.accuracyByLeague.isNotEmpty()) {
          appendLine()
          appendLine("| Ligue / Catégorie | Réussites (Base) | Réussites (Calibré) | Total |")
          appendLine("| :--- | :---: | :---: | :---: |")
          calibrationReport.accuracyByLeague.forEach { stat ->
            appendLine(
              "| ${stat.categoryName} | ${stat.baselineSuccessCount} (${stat.baselineAccuracyPercent}%) | **${stat.calibratedSuccessCount} (${stat.calibratedAccuracyPercent}%)** | ${stat.totalMatches} |",
            )
          }
        }
        appendLine()
      }

      appendLine("## Vue d'Ensemble des Consultations Archivées")
      appendLine()
      appendLine("| # | Titre | Compétition / Domaine | Juge (M15) | Issue (M16) | Pronostic / Polarité | Résultat réel |")
      appendLine("| :---: | :--- | :--- | :--- | :--- | :--- | :--- |")
      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        val judge = reading.house(15).figure
        val outcome = reading.house(16).figure
        val domainOrLeague = SavedThemeRepository.categoryFor(entity)
        val predictionLabel =
          if (reading.domain == QuestionDomain.CONTEST) {
            AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, weightTable).predictedOutcome.label
          } else {
            reading.analysis.overallPolarity.label
          }
        val (storedOutcome, storedScore) =
          MatchOutcomeParser.parse(entity.actualOutcomeName, entity.actualScoreText, entity.contextOutcomeNotes)
        val realCell =
          when {
            storedOutcome != null && storedScore.isNotEmpty() -> "$storedScore (${storedOutcome.shortLabel})"
            storedOutcome != null -> storedOutcome.label
            storedScore.isNotEmpty() -> storedScore
            else -> "En attente"
          }
        appendLine(
          "| ${idx + 1} | ${entity.title} | $domainOrLeague | ${judge.westAfricanName} | ${outcome.westAfricanName} | $predictionLabel | $realCell |",
        )
      }

      entities.forEachIndexed { idx, entity ->
        val reading = reconstructReading(entity)
        appendLine()
        appendLine("---")
        appendLine()
        appendLine("## Consultation #${idx + 1}")
        appendLine()
        append(
          formatSingleArchive(
            entity = entity,
            reading = reading,
            format = ArchiveExportFormat.MARKDOWN_REPORT,
            weightTable = weightTable,
            generatedAtMillis = generatedAtMillis,
          ),
        )
      }
    }
}
