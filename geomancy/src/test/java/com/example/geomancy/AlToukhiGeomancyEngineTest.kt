package com.example.geomancy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AlToukhiGeomancyEngineTest {

  @Test
  fun allSixteenFiguresAreUniqueAndComplete() {
    val figures = AlToukhiFigures.all
    assertEquals(16, figures.size)
    assertEquals(16, figures.map { it.code }.toSet().size)
    assertEquals(16, figures.map { it.id }.toSet().size)
    assertEquals((1..16).toSet(), figures.map { it.id }.toSet())
    assertEquals((1..16).toSet(), figures.map { it.westAfricanOrder }.toSet())
    assertEquals(16, figures.map { it.westAfricanName }.toSet().size)
  }

  @Test
  fun allSixteenHousesMapToValidTaskinRulers() {
    val houses = AlToukhiHouses.all
    assertEquals(16, houses.size)
    assertEquals((1..16).toSet(), houses.map { it.number }.toSet())
    assertEquals((1..16).toSet(), houses.map { it.taskinRulerId }.toSet())
  }

  @Test
  fun daughterAndNieceAndJudgeDerivationsFollowExactParityRules() {
    val m1 = AlToukhiFigures.findById(1) // 1222 Adama
    val m2 = AlToukhiFigures.findById(2) // 2121 Ousmana
    val m3 = AlToukhiFigures.findById(11) // 2211 Nouhoun
    val m4 = AlToukhiFigures.findById(16) // 1111 Taliki

    val reading =
      AlToukhiGeomancyEngine.castFromMothers(
        mothers = listOf(m1, m2, m3, m4),
        domain = QuestionDomain.WEALTH,
      )

    assertEquals("1221", reading.house(5).figure.code)
    assertEquals("Solomana", reading.house(5).figure.westAfricanName)
    assertEquals("2121", reading.house(6).figure.code)
    assertEquals("Ousmana", reading.house(6).figure.westAfricanName)
    assertEquals("2211", reading.house(7).figure.code)
    assertEquals("2111", reading.house(8).figure.code)
    assertEquals("Malidjou", reading.house(8).figure.westAfricanName)
    assertEquals("1121", reading.house(9).figure.code)
    assertEquals("Djanfa Almami", reading.house(9).figure.westAfricanName)
    assertEquals("1122", reading.house(10).figure.code)
    assertEquals("Kalalaw", reading.house(10).figure.westAfricanName)

    val expectedJudge = AlToukhiFigures.combine(reading.house(13).figure, reading.house(14).figure)
    assertEquals(expectedJudge.code, reading.house(15).figure.code)

    val expected16 = AlToukhiFigures.combine(reading.house(15).figure, reading.house(1).figure)
    assertEquals(expected16.code, reading.house(16).figure.code)
  }

  @Test
  fun dotCountCastingProducesValidSixteenHouseTakht() {
    val counts = listOf(7, 8, 10, 12, 9, 11, 6, 14, 5, 13, 8, 10, 7, 9, 11, 13)
    val reading = AlToukhiGeomancyEngine.castFromDotCounts(counts, QuestionDomain.HIDDEN_INTENT)

    assertEquals(16, reading.houses.size)
    assertTrue(reading.analysis.abjadExtraction.totalAbjadSum > 0)
    assertTrue(reading.analysis.abjadExtraction.extractedLetter.isNotEmpty())
  }

  @Test
  fun contestAnalysisAccountsForFoundationMujawaraAndRepeatedJudge() {
    val m1 = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.ODD, PointParity.ODD, PointParity.ODD) // Taliki
    val m2 = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.ODD, PointParity.ODD, PointParity.EVEN) // Lawsana
    val m3 = AlToukhiFigures.findByRanks(PointParity.EVEN, PointParity.ODD, PointParity.ODD, PointParity.ODD) // Malidjou
    val m4 = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.EVEN, PointParity.ODD, PointParity.EVEN) // Inzan

    val reading =
      AlToukhiGeomancyEngine.castFromMothers(
        mothers = listOf(m1, m2, m3, m4),
        domain = QuestionDomain.CONTEST,
        questionText = "Mauritanie vs Burkina Faso",
      )

    assertEquals("Inzan", reading.house(15).figure.westAfricanName)
    assertEquals("Ousmana", reading.house(16).figure.westAfricanName)
    val contest = reading.analysis.contestComparison ?: ""
    assertTrue("Camp B attendu, reçu : $contest", contest.contains("Camp B"))
  }

  private fun figFromCode(code: String): GeomanticFigure =
    AlToukhiFigures.findByRanks(
      fire = if (code[0] == '1') PointParity.ODD else PointParity.EVEN,
      air = if (code[1] == '1') PointParity.ODD else PointParity.EVEN,
      water = if (code[2] == '1') PointParity.ODD else PointParity.EVEN,
      earth = if (code[3] == '1') PointParity.ODD else PointParity.EVEN,
    )

  @Test
  fun evaluateTenMatchesFromCurrentEngine() {
    data class MatchInput(val name: String, val codes: List<String>, val expectedTag: String)
    val matches =
      listOf(
        MatchInput("Danemark vs Norvège", listOf("1221", "2121", "2121", "2222"), "Camp B"),
        MatchInput("Pays de Galles vs Portugal", listOf("2221", "2211", "1222", "1112"), "Camp B"),
        MatchInput("Hongrie vs Irlande du Nord", listOf("2212", "2112", "2122", "2222"), "match nul"),
        MatchInput("Ukraine vs Géorgie", listOf("2211", "1111", "1121", "1121"), "Camp A"),
        MatchInput("Israël vs Autriche", listOf("2111", "2121", "1121", "1111"), "Juge Inzan"),
        MatchInput("Irlande vs Kosovo", listOf("1211", "2222", "2121", "1222"), "Camp A"),
        MatchInput("Bosnie-Herzégovine vs Roumanie", listOf("2112", "2212", "2121", "2211"), "Camp A"),
        MatchInput("Pologne vs Suède", listOf("2211", "2121", "2121", "2221"), "M16 Moussa"),
        MatchInput("Botswana vs Ouganda", listOf("1121", "1222", "2112", "1221"), "match nul"),
        MatchInput("Mauritanie vs Burkina Faso", listOf("1111", "1112", "2111", "1212"), "Camp B"),
      )

    matches.forEach { m ->
      val reading =
        AlToukhiGeomancyEngine.castFromMothers(
          mothers = m.codes.map(::figFromCode),
          domain = QuestionDomain.CONTEST,
          questionText = m.name,
        )
      assertEquals(16, reading.houses.size)
      val contest = reading.analysis.contestComparison ?: ""
      println("CALIBRATED: ${m.name} -> $contest")
      assertTrue(
        "Attendu '${m.expectedTag}' pour ${m.name}, reçu : $contest",
        contest.contains(m.expectedTag),
      )
    }
  }

  @Test
  fun predictiveCalibrationImprovesOrPreservesAccuracyAndAggregatesLeagues() {
    val samples = PredictiveCalibrationEngine.nov17RealMatchSamples()
    val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)

    assertEquals(10, report.totalSamples)
    assertTrue(
      "Le calibrage ne doit jamais dégrader la précision (base=${report.baselineCorrectCount}, cal=${report.calibratedCorrectCount})",
      report.calibratedCorrectCount >= report.baselineCorrectCount,
    )
    assertTrue(report.accuracyByLeague.any { it.categoryName == "Ligue des Nations UEFA" && it.totalMatches == 8 })
    assertTrue(report.accuracyByLeague.any { it.categoryName == "Qualifications CAN 2027" && it.totalMatches == 2 })
    assertEquals(3, report.accuracyByMatchType.size)
    assertEquals(16, report.figureDeltas.size)

    println(
      "REPORT_SUMMARY: base=${report.baselineCorrectCount}/${report.totalSamples} (${report.baselineAccuracyPercent}%) -> " +
        "calibrated=${report.calibratedCorrectCount}/${report.totalSamples} (${report.calibratedAccuracyPercent}%)",
    )
    val beforeById = report.evaluationsBefore.associateBy { it.sample.id }
    report.evaluationsAfter.forEach { after ->
      val before = beforeById[after.sample.id]!!
      val basePred = AlToukhiGeomancyEngine.castFromDotCounts(after.sample.motherDotCounts, QuestionDomain.CONTEST, after.sample.title, weightTable = FigureWeightTable.default())
      val calPred = AlToukhiGeomancyEngine.castFromDotCounts(after.sample.motherDotCounts, QuestionDomain.CONTEST, after.sample.title, weightTable = report.calibratedWeights)
      println(
        "MATCH_${after.sample.id}: ${after.sample.title} | League=${after.sample.leagueOrType} | " +
          "Base=${before.predictedOutcome.shortLabel} (${FigureWeightTable.formatWeight(before.sideAScore)}-${FigureWeightTable.formatWeight(before.sideBScore)}, ok=${before.isCorrect}) | " +
          "Cal=${after.predictedOutcome.shortLabel} (${FigureWeightTable.formatWeight(after.sideAScore)}-${FigureWeightTable.formatWeight(after.sideBScore)}, ok=${after.isCorrect}) | " +
          "Real=${after.sample.scoreLabel} (${after.sample.actualOutcome.shortLabel}) | " +
          "M15=${after.judgeFigure.westAfricanName} M16=${after.outcome16Figure.westAfricanName} | Summary=${calPred.analysis.contestComparison}",
      )
      assertTrue(basePred.houses.size == 16)
    }
    report.accuracyByLeague.forEach { stat ->
      println("LEAGUE_STAT: ${stat.categoryName} -> cal=${stat.calibratedSuccessCount}/${stat.totalMatches} (${stat.calibratedAccuracyPercent}%), base=${stat.baselineSuccessCount}/${stat.totalMatches} (${stat.baselineAccuracyPercent}%)")
    }
    report.accuracyByMatchType.forEach { stat ->
      println("TYPE_STAT: ${stat.categoryName} -> cal=${stat.calibratedSuccessCount}/${stat.totalMatches} (${stat.calibratedAccuracyPercent}%), base=${stat.baselineSuccessCount}/${stat.totalMatches} (${stat.baselineAccuracyPercent}%)")
    }
  }

  @Test
  fun matchOutcomeParserExtractsScoresAndLeagues() {
    val (outcome1, score1) = MatchOutcomeParser.parse(null, "", "17-11-2026: 1-3")
    assertEquals(MatchOutcome.CAMP_B_WIN, outcome1)
    assertEquals("1-3", score1)

    val (outcome2, score2) = MatchOutcomeParser.parse(null, "1-1", "")
    assertEquals(MatchOutcome.DRAW, outcome2)
    assertEquals("1-1", score2)

    val (outcome3, score3) = MatchOutcomeParser.parse(null, "3–0", "")
    assertEquals(MatchOutcome.CAMP_A_WIN, outcome3)
    assertEquals("3-0", score3)

    assertEquals(
      "Ligue des Nations UEFA",
      MatchOutcomeParser.inferLeague("", "Danemark vs Norvège", "Ligue des Nations"),
    )
  }

  @Test
  fun evaluateTodaysSixDrawsWithBaseAndCalibratedEngine() {
    val calibratedTable =
      PredictiveCalibrationEngine.evaluateAndCalibrate(
        PredictiveCalibrationEngine.nov17RealMatchSamples(),
      ).calibratedWeights

    data class DrawInput(val title: String, val drawTime: String, val codes: List<String>)
    val draws =
      listOf(
        DrawInput("Club Portugalete vs Real Unión (18h00)", "13h18", listOf("2122", "2211", "1121", "2122")),
        DrawInput("Utebo vs Sabadell (18h00)", "13h23", listOf("1111", "1222", "1112", "2121")),
        DrawInput("Alcoyano vs Majorque (19h00)", "13h37", listOf("2112", "1112", "2121", "1221")),
        DrawInput("Badajoz vs Tenerife (19h00)", "12h54", listOf("2221", "1122", "1111", "1111")),
        DrawInput("Deportiva Minera vs Cartagena (19h30)", "13h05", listOf("2211", "2222", "2212", "1111")),
        DrawInput("Reus Reddis vs Barakaldo (19h30)", "13h11", listOf("2122", "2122", "2121", "2211")),
      )

    draws.forEachIndexed { idx, draw ->
      val mothers = draw.codes.map(::figFromCode)
      val baseReading =
        AlToukhiGeomancyEngine.castFromMothers(
          mothers = mothers,
          domain = QuestionDomain.CONTEST,
          questionText = draw.title,
          activePlanetHour = Planet.MERCURY,
          weightTable = FigureWeightTable.default(),
        )
      val calReading =
        AlToukhiGeomancyEngine.castFromMothers(
          mothers = mothers,
          domain = QuestionDomain.CONTEST,
          questionText = draw.title,
          activePlanetHour = Planet.MERCURY,
          weightTable = calibratedTable,
        )
      val basePred =
        AlToukhiGeomancyEngine.predictContestOutcome(baseReading.houses, FigureWeightTable.default())
      val calPred =
        AlToukhiGeomancyEngine.predictContestOutcome(calReading.houses, calibratedTable)
      assertEquals(16, baseReading.houses.size)
      val housesStr =
        baseReading.houses.joinToString(", ") { h ->
          "M${h.info.number}=${h.figure.westAfricanName}(${h.figure.code})"
        }
      println("TODAY_${idx + 1}: ${draw.title} [tiré à ${draw.drawTime}]")
      println("  HOUSES: $housesStr")
      println(
        "  BASE: outcome=${basePred.predictedOutcome.label} | A=${FigureWeightTable.formatWeight(basePred.sideAScore)} vs B=${FigureWeightTable.formatWeight(basePred.sideBScore)} | ${basePred.summaryText}",
      )
      println(
        "  CAL:  outcome=${calPred.predictedOutcome.label} | A=${FigureWeightTable.formatWeight(calPred.sideAScore)} vs B=${FigureWeightTable.formatWeight(calPred.sideBScore)} | ${calPred.summaryText}",
      )
    }
  }

  @Test
  fun archiveFrequencyComputationTracksAllSixteenFiguresAndTrends() {
    val nov17Readings =
      PredictiveCalibrationEngine.nov17RealMatchSamples().map { sample ->
        AlToukhiGeomancyEngine.castFromDotCounts(
          sixteenCounts = sample.motherDotCounts,
          domain = QuestionDomain.CONTEST,
          questionText = sample.title,
        )
      }

    val freqReport = PredictiveCalibrationEngine.computeArchiveFrequencies(nov17Readings)
    assertEquals(10, freqReport.totalThemes)
    assertEquals(160, freqReport.totalHouseSlots)
    assertEquals(16, freqReport.statsByFigure.size)
    assertEquals(160, freqReport.statsByFigure.sumOf { it.totalAppearances })
    assertEquals(40, freqReport.statsByFigure.sumOf { it.motherAppearances })
    assertEquals(20, freqReport.statsByFigure.sumOf { it.judgeAndOutcomeAppearances })
    assertTrue(freqReport.topFigures.isNotEmpty())
    assertTrue(freqReport.topVerdictFigures.isNotEmpty())
    assertTrue(freqReport.trendSummary.contains("10 tirage"))
  }

  @Test
  fun bulughAlAmalRulesIntegrateIntoContestPredictionAndPreserveAccuracy() {
    val samples = PredictiveCalibrationEngine.nov17RealMatchSamples()
    val report = PredictiveCalibrationEngine.evaluateAndCalibrate(samples)

    assertTrue(
      "La précision de base avec Bulūgh al-Amal doit rester >= 8/10, reçu ${report.baselineCorrectCount}/10",
      report.baselineCorrectCount >= 8,
    )
    assertTrue(
      "La précision calibrée avec Bulūgh al-Amal doit rester >= 9/10, reçu ${report.calibratedCorrectCount}/10",
      report.calibratedCorrectCount >= 9,
    )

    val sample1 = samples.first()
    val reading =
      AlToukhiGeomancyEngine.castFromDotCounts(
        sixteenCounts = sample1.motherDotCounts,
        domain = QuestionDomain.CONTEST,
        questionText = sample1.title,
      )
    val prediction = reading.analysis.contestPrediction!!
    val insights = prediction.bulughInsights!!
    assertTrue(insights.totalChartDots in 64..128)
    assertTrue(insights.judgeParityValid)
    assertTrue(insights.activeRulesNotes.isNotEmpty())
  }

  @Test
  fun allSixteenHousesHaveExplicitElementAndTaskinRuler() {
    assertEquals(16, AlToukhiHouses.all.size)
    AlToukhiHouses.all.forEach { house ->
      val expectedCycleElement = Element.entries[(house.number - 1) % 4]
      assertEquals(expectedCycleElement, house.element)
      val ruler = AlToukhiHouses.taskinRulerFor(house.number)
      assertEquals(house.taskinRulerId, ruler.id)
    }
  }

  @Test
  fun themeRecurrenceCountsIdenticalFourMotherSignaturesRegardlessOfRawDotCounts() {
    val entries =
      listOf(
        ThemeArchiveInput(
          id = 1L,
          title = "Tirage A",
          domain = QuestionDomain.GENERAL,
          motherDotCounts = listOf(1, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1),
          createdAtMillis = 1000L,
        ),
        ThemeArchiveInput(
          id = 2L,
          title = "Tirage B (même parité)",
          domain = QuestionDomain.WEALTH,
          motherDotCounts = listOf(5, 8, 6, 4, 10, 7, 12, 9, 4, 8, 5, 7, 3, 9, 11, 13),
          createdAtMillis = 2000L,
        ),
        ThemeArchiveInput(
          id = 3L,
          title = "Tirage C (différent)",
          domain = QuestionDomain.CONTEST,
          motherDotCounts = listOf(2, 2, 2, 2, 1, 1, 1, 1, 2, 1, 2, 1, 1, 2, 2, 2),
          createdAtMillis = 3000L,
        ),
      )

    val report = PredictiveCalibrationEngine.computeThemeRecurrences(entries)
    assertEquals(3, report.totalThemes)
    assertEquals(2, report.uniqueSignatures)
    assertEquals(1, report.recurringSignatureCount)
    val recurring = report.recurringThemes.first()
    assertEquals("1222/2121/2211/1111", recurring.signatureCode)
    assertEquals(2, recurring.occurrenceCount)
    assertEquals("Tirage B (même parité)", recurring.occurrences.first().title)
  }

  @Test
  fun alFulkAlMashhunInsightsAndDuelDiagnosticsAcrossSixteenMatches() {
    val nov17 = PredictiveCalibrationEngine.nov17RealMatchSamples()
    val nov18Seeds = PredictiveCalibrationEngine.nov18ConsultationDraws()
    val nov18Results = PredictiveCalibrationEngine.nov18VerifiedMatchResults()
    val nov18Samples =
      nov18Seeds.mapIndexed { idx, seed ->
        val res = nov18Results.first { seed.title.startsWith(it.titlePrefix) }
        MatchCalibrationSample(
          id = 100L + idx,
          title = seed.title,
          motherDotCounts = seed.motherDotCounts,
          actualOutcome = res.outcome90Min,
          scoreLabel = res.scoreDisplay,
          leagueOrType = seed.league,
        )
      }

    val all16 = nov17 + nov18Samples
    val calibrated17 = PredictiveCalibrationEngine.evaluateAndCalibrate(nov17).calibratedWeights
    val calibrated16 = PredictiveCalibrationEngine.evaluateAndCalibrate(all16).calibratedWeights

    var mod9Ok = 0
    var oddEvenOk = 0
    var awtadOk = 0
    var mod4Ok = 0
    var baseOk16 = 0
    var cal17Ok16 = 0
    var cal16Ok16 = 0

    all16.forEach { sample ->
      val reading =
        AlToukhiGeomancyEngine.castFromDotCounts(
          sixteenCounts = sample.motherDotCounts,
          domain = QuestionDomain.CONTEST,
          questionText = sample.title,
        )
      val general = reading.analysis.alFulkInsights!!
      val duel = reading.analysis.contestPrediction!!.alFulkInsights!!
      val basePred = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, FigureWeightTable.default())
      val cal17Pred = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, calibrated17)
      val cal16Pred = AlToukhiGeomancyEngine.predictContestOutcome(reading.houses, calibrated16)

      if (duel.mod9Verdict == sample.actualOutcome) mod9Ok++
      if (duel.oddVsEvenVerdict == sample.actualOutcome) oddEvenOk++
      if (duel.awtadVerdict == sample.actualOutcome) awtadOk++
      if (duel.mod4Verdict == sample.actualOutcome) mod4Ok++
      if (basePred.predictedOutcome == sample.actualOutcome) baseOk16++
      if (cal17Pred.predictedOutcome == sample.actualOutcome) cal17Ok16++
      if (cal16Pred.predictedOutcome == sample.actualOutcome) cal16Ok16++

      assertEquals(16, general.allBatinFigures.size)
      assertTrue(general.reliability.explanation.isNotEmpty())
      assertTrue(general.m12ObstacleDiagnostic.isNotEmpty())
      assertTrue(duel.activeNotes.size >= 4)

      println(
        "ALFULK_${sample.id}: ${sample.title} | Real=${sample.actualOutcome.shortLabel} (${sample.scoreLabel}) | " +
          "Sidq=${general.reliability.isSincere}(${general.reliability.validationFigure.westAfricanName}) | " +
          "Base=${basePred.predictedOutcome.shortLabel}(ok=${basePred.predictedOutcome == sample.actualOutcome}) | " +
          "Cal17=${cal17Pred.predictedOutcome.shortLabel}(ok=${cal17Pred.predictedOutcome == sample.actualOutcome}) | " +
          "Mod9=${duel.campAMod9Remainder}:${duel.campBMod9Remainder}->${duel.mod9Verdict.shortLabel} | " +
          "OddEven=${duel.oddHousesFortunateCount}:${duel.evenHousesFortunateCount}->${duel.oddVsEvenVerdict.shortLabel} | " +
          "Awtad=${duel.awtadSubJudge.westAfricanName}/${duel.awtadSubOutcome.westAfricanName}->${duel.awtadVerdict.shortLabel} | " +
          "Mod4=${duel.mod4Verdict.shortLabel}",
      )
    }

    println(
      "ALFULK_SUMMARY_16: Base=$baseOk16/16, Cal17=$cal17Ok16/16, Cal16=$cal16Ok16/16 | " +
        "Mod9=$mod9Ok/16, OddEven=$oddEvenOk/16, Awtad=$awtadOk/16, Mod4=$mod4Ok/16",
    )
  }

  @Test
  fun alFulkMathematicalRulesAndBatinValidationAreExact() {
    // 4 Mères toutes Fixes (Adama, Moussa, Solomana, Bayada) -> doit déclencher l'alerte Nukta (p. 90)
    val fixedMothers =
      listOf(
        AlToukhiFigures.findById(1), // Adama FIXED
        AlToukhiFigures.findById(4), // Moussa FIXED
        AlToukhiFigures.findById(6), // Solomana FIXED
        AlToukhiFigures.findById(9), // Bayada FIXED
      )
    val fixedReading =
      AlToukhiGeomancyEngine.castFromMothers(
        mothers = fixedMothers,
        domain = QuestionDomain.AUTHORITY,
      )
    val insights = fixedReading.analysis.alFulkInsights!!
    assertTrue(insights.reliability.mothersUniformMotionWarning != null)
    assertTrue(insights.reliability.m15EvenParityValid)

    val expectedSeal =
      AlToukhiFigures.combine(
        AlToukhiFigures.combine(fixedReading.house(3).figure, fixedReading.house(5).figure),
        AlToukhiFigures.combine(fixedReading.house(11).figure, fixedReading.house(15).figure),
      )
    assertEquals(expectedSeal.code, insights.reliability.validationFigure.code)

    // Vérification exacte des 4 Figures Pures (pp. 77–78)
    val expectedFire =
      AlToukhiFigures.findByRanks(
        fixedReading.house(1).figure.fire,
        fixedReading.house(5).figure.fire,
        fixedReading.house(9).figure.fire,
        fixedReading.house(13).figure.fire,
      )
    val expectedWater =
      AlToukhiFigures.findByRanks(
        fixedReading.house(3).figure.water,
        fixedReading.house(7).figure.water,
        fixedReading.house(11).figure.water,
        fixedReading.house(15).figure.water,
      )
    assertEquals(expectedFire.code, insights.pureElements.fireOfFire.code)
    assertEquals(expectedWater.code, insights.pureElements.waterOfWater.code)

    // Vérification exacte du Bāṭin al-Takht (pp. 141–143)
    for (houseNum in 1..16) {
      val expectedBatin =
        AlToukhiFigures.combine(
          fixedReading.house(houseNum).figure,
          AlToukhiHouses.taskinRulerFor(houseNum),
        )
      assertEquals(expectedBatin.code, insights.allBatinFigures[houseNum - 1].code)
    }

    // Vérification des 5 cas du verset Mod-9 sur le jeu de données réel
    val samples = PredictiveCalibrationEngine.nov17RealMatchSamples()
    // Match 6: remA=9 (impair), remB=4 (pair) -> parités croisées, plus grand gagne => Camp A
    val m6 = AlToukhiGeomancyEngine.castFromDotCounts(samples[5].motherDotCounts, QuestionDomain.CONTEST)
    assertEquals(9, m6.analysis.contestPrediction!!.alFulkInsights!!.campAMod9Remainder)
    assertEquals(4, m6.analysis.contestPrediction!!.alFulkInsights!!.campBMod9Remainder)
    assertEquals(MatchOutcome.CAMP_A_WIN, m6.analysis.contestPrediction!!.alFulkInsights!!.mod9Verdict)

    // Match 7: remA=4 (pair), remB=4 (pair) -> égalité paire => Camp B (Maṭlūb)
    val m7 = AlToukhiGeomancyEngine.castFromDotCounts(samples[6].motherDotCounts, QuestionDomain.CONTEST)
    assertEquals(4, m7.analysis.contestPrediction!!.alFulkInsights!!.campAMod9Remainder)
    assertEquals(4, m7.analysis.contestPrediction!!.alFulkInsights!!.campBMod9Remainder)
    assertEquals(MatchOutcome.CAMP_B_WIN, m7.analysis.contestPrediction!!.alFulkInsights!!.mod9Verdict)

    // Match 1: remA=5 (impair), remB=3 (impair) -> même parité, plus petit gagne => Camp B
    val m1 = AlToukhiGeomancyEngine.castFromDotCounts(samples[0].motherDotCounts, QuestionDomain.CONTEST)
    assertEquals(5, m1.analysis.contestPrediction!!.alFulkInsights!!.campAMod9Remainder)
    assertEquals(3, m1.analysis.contestPrediction!!.alFulkInsights!!.campBMod9Remainder)
    assertEquals(MatchOutcome.CAMP_B_WIN, m1.analysis.contestPrediction!!.alFulkInsights!!.mod9Verdict)
  }

  @Test
  fun hawiRelationalTransformsAndInvolutionLawsAreExact() {
    AlToukhiFigures.all.forEach { fig ->
      val rel = AlToukhiFigures.relationsFor(fig)
      assertEquals(fig.id, AlToukhiFigures.relationsFor(rel.nazar).nazar.id)
      assertEquals(fig.id, AlToukhiFigures.relationsFor(rel.nutq).nutq.id)
      assertEquals(fig.id, AlToukhiFigures.relationsFor(rel.ittisal).ittisal.id)
      assertEquals(fig.id, AlToukhiFigures.relationsFor(rel.infisal).infisal.id)
      assertEquals(fig.id, AlToukhiFigures.relationsFor(rel.opposite).opposite.id)
      assertEquals(fig.id, AlToukhiFigures.relationsFor(rel.verticalMirror).verticalMirror.id)

      // Vérifier l'axe exact inversé pour chaque balance
      assertTrue(rel.nazar.fire != fig.fire && rel.nazar.air == fig.air && rel.nazar.water == fig.water && rel.nazar.earth == fig.earth)
      assertTrue(rel.nutq.fire == fig.fire && rel.nutq.air != fig.air && rel.nutq.water == fig.water && rel.nutq.earth == fig.earth)
      assertTrue(rel.ittisal.fire == fig.fire && rel.ittisal.air == fig.air && rel.ittisal.water != fig.water && rel.ittisal.earth == fig.earth)
      assertTrue(rel.infisal.fire == fig.fire && rel.infisal.air == fig.air && rel.infisal.water == fig.water && rel.infisal.earth != fig.earth)
    }

    val tierCounts = AlToukhiFigures.all.groupingBy { AlToukhiFigures.hawiStrengthTier(it) }.eachCount()
    assertEquals(4, tierCounts[HawiStrengthTier.STRONG_FORTUNE])
    assertEquals(4, tierCounts[HawiStrengthTier.MIXED_FORTUNE])
    assertEquals(4, tierCounts[HawiStrengthTier.MIXED_MISFORTUNE])
    assertEquals(4, tierCounts[HawiStrengthTier.STRONG_MISFORTUNE])
  }

  @Test
  fun planetaryDignitiesTemporalZonesAndStrengthRankAreConsistent() {
    Planet.entries.forEach { planet ->
      assertTrue(AlToukhiHouses.sharafHouseFor(planet) in 1..16)
      assertTrue(AlToukhiHouses.hubutHouseFor(planet) in 1..16)
    }

    val adama = AlToukhiFigures.findById(1) // Jupiter, Taskīn M1
    val solomana = AlToukhiFigures.findById(6) // Saturne, Hubūṭ M1
    val mangouchi = AlToukhiFigures.findById(7) // Saturne, Taskīn M7 & Sharaf M7
    val kalalaw = AlToukhiFigures.findById(10) // Soleil, Sharaf M1

    assertEquals(PlanetaryDignity.OWN_HOUSE, AlToukhiHouses.dignityOf(adama, 1))
    assertEquals(PlanetaryDignity.EXALTATION, AlToukhiHouses.dignityOf(adama, 4))
    assertEquals(PlanetaryDignity.FALL, AlToukhiHouses.dignityOf(solomana, 1))
    assertEquals(PlanetaryDignity.OWN_AND_EXALTATION, AlToukhiHouses.dignityOf(mangouchi, 7))
    assertEquals(PlanetaryDignity.EXALTATION, AlToukhiHouses.dignityOf(kalalaw, 1))

    assertEquals(HouseTemporalRole.AWTAD_PRESENT, AlToukhiHouses.temporalRoleFor(1))
    assertEquals(HouseTemporalRole.SUCCEDENT_FUTURE, AlToukhiHouses.temporalRoleFor(5))
    assertEquals(HouseTemporalRole.CADENT_PAST, AlToukhiHouses.temporalRoleFor(9))
    assertEquals(HouseTemporalRole.ZAWAYID_SYNTHESIS, AlToukhiHouses.temporalRoleFor(15))

    assertEquals((1..16).toSet(), (1..16).map { AlToukhiHouses.houseStrengthRank(it) }.toSet())
    assertEquals(1, AlToukhiHouses.houseStrengthRank(1))
    assertEquals(2, AlToukhiHouses.houseStrengthRank(10))
    assertEquals(136, AlToukhiHouses.hawiTriangularValue(16))
  }

  @Test
  fun crossedAwtadHawiDuelAndTemporalDynamicsIntegrateCleanly() {
    val sample = PredictiveCalibrationEngine.nov17RealMatchSamples().first()
    val reading = AlToukhiGeomancyEngine.castFromDotCounts(sample.motherDotCounts, QuestionDomain.CONTEST, sample.title)
    val pred = reading.analysis.contestPrediction!!
    val alFulkDuel = pred.alFulkInsights!!
    val hawiDuel = pred.hawiInsights!!
    val gen = reading.analysis.alFulkInsights!!

    assertTrue(alFulkDuel.crossedAwtadA != null && alFulkDuel.crossedAwtadB != null && alFulkDuel.crossedAwtadVerdict != null)
    assertTrue(alFulkDuel.dignityBalanceNote.contains("Sharaf/Hubūṭ"))
    assertEquals(AlToukhiFigures.combine(reading.house(1).figure, reading.house(2).figure).id, hawiDuel.campAPairComposite.id)
    assertEquals(AlToukhiFigures.combine(reading.house(7).figure, reading.house(8).figure).id, hawiDuel.campBPairComposite.id)
    assertTrue(gen.temporalDynamics != null)
    assertTrue(gen.relationalResonances.isNotEmpty())
    assertTrue(gen.dignitySummaryNote.isNotEmpty())
  }

  @Test
  fun matchPrecisionForecastIsConsistentWithOutcomeAndDisciplineRules() {
    val samples = PredictiveCalibrationEngine.nov17RealMatchSamples()
    samples.forEach { sample ->
      val reading = AlToukhiGeomancyEngine.castFromDotCounts(sample.motherDotCounts, QuestionDomain.CONTEST, sample.title)
      val pred = reading.analysis.contestPrediction!!
      val mp = pred.matchPrecision!!

      when (pred.predictedOutcome) {
        MatchOutcome.CAMP_A_WIN -> assertTrue("${sample.title}: Camp A score doit > Camp B (${mp.exactScoreLabel})", mp.exactScoreCampA > mp.exactScoreCampB)
        MatchOutcome.CAMP_B_WIN -> assertTrue("${sample.title}: Camp B score doit > Camp A (${mp.exactScoreLabel})", mp.exactScoreCampB > mp.exactScoreCampA)
        MatchOutcome.DRAW -> assertEquals("${sample.title}: Match nul doit avoir score égal (${mp.exactScoreLabel})", mp.exactScoreCampA, mp.exactScoreCampB)
      }
      assertEquals(mp.exactScoreCampA > 0 && mp.exactScoreCampB > 0, mp.bothTeamsToScore)
      assertTrue(mp.bttsConfidencePercent in 50..95)
      assertTrue(mp.yellowCardsCampA.first in 0..5 && mp.yellowCardsCampA.last >= mp.yellowCardsCampA.first)
      assertTrue(mp.yellowCardsCampB.first in 0..5 && mp.yellowCardsCampB.last >= mp.yellowCardsCampB.first)
      assertTrue(mp.redCardRiskPercent in 10..85)
    }
  }

  @Test
  fun alQawlAspectsPlanetaryCompatibilityAndTarabulusiScaleAreExact() {
    assertEquals(AspectRelationType.SEXTILE, AlToukhiHouses.aspectBetweenHouses(1, 3))
    assertEquals(AspectRelationType.SEXTILE, AlToukhiHouses.aspectBetweenHouses(1, 11))
    assertEquals(AspectRelationType.SQUARE, AlToukhiHouses.aspectBetweenHouses(1, 10))
    assertEquals(AspectRelationType.TRINE, AlToukhiHouses.aspectBetweenHouses(1, 5))
    assertEquals(AspectRelationType.TRINE, AlToukhiHouses.aspectBetweenHouses(2, 10))
    assertEquals(AspectRelationType.OPPOSITION, AlToukhiHouses.aspectBetweenHouses(1, 7))
    assertEquals(null, AlToukhiHouses.aspectBetweenHouses(1, 2))
    assertEquals(null, AlToukhiHouses.aspectBetweenHouses(1, 13))

    assertTrue(AlToukhiHouses.planetaryCompatibility(Planet.JUPITER, Planet.MARS).contains("Inimitié"))
    assertTrue(AlToukhiHouses.planetaryCompatibility(Planet.JUPITER, Planet.VENUS).contains("Amitié"))
    assertTrue(AlToukhiHouses.elementalCompatibility(Element.FIRE, Element.AIR).contains("amis"))
    assertTrue(AlToukhiHouses.elementalCompatibility(Element.FIRE, Element.WATER).contains("contraires"))
    assertEquals("6 mois", AlToukhiHouses.tarabulusiDurationForHouse(10))
  }

  @Test
  fun alQawlGeneralAndDuelInsightsCompute96ThresholdFiveTrianglesAndAspects() {
    // 1) 4 Mères Djamâa (2222) -> toutes les 16 maisons = Djamâa (8 pts/maison = 128 pts -> Raml Kāmil >= 96)
    val djamaa = AlToukhiFigures.findByRanks(PointParity.EVEN, PointParity.EVEN, PointParity.EVEN, PointParity.EVEN)
    val completeReading =
      AlToukhiGeomancyEngine.castFromMothers(
        mothers = listOf(djamaa, djamaa, djamaa, djamaa),
        domain = QuestionDomain.CONTEST,
        questionText = "Test Al-Qawl Kāmil",
      )
    val kamil = completeReading.analysis.alQawlInsights!!
    assertEquals(128, kamil.totalChartDots)
    assertTrue(kamil.isComplete96)
    assertEquals(5, kamil.fiveTriangles.size)
    assertEquals(listOf(9, 10, 11, 12, 15), kamil.fiveTriangles.map { it.apexHouseNumber })
    assertTrue(kamil.aspectLinks.isNotEmpty())
    assertTrue(kamil.tarabulusiTimingHint.contains("Échelle Al-Ṭarābulusī"))

    val duelQawl = completeReading.analysis.contestPrediction!!.alQawlInsights!!
    assertEquals(AlToukhiFigures.combine(completeReading.house(1).figure, completeReading.house(10).figure).id, duelQawl.m1M10Composite.id)
    assertEquals(AlToukhiFigures.combine(completeReading.house(7).figure, completeReading.house(4).figure).id, duelQawl.m7M4Composite.id)
    assertTrue(duelQawl.summaryNote.contains("Zanātī"))

    // 2) 4 Mères Taliki (1111) -> 8 maisons à 4 pts + 8 maisons à 8 pts = 92 pts (< 96 -> Raml Nāqiṣ)
    val taliki = AlToukhiFigures.findByRanks(PointParity.ODD, PointParity.ODD, PointParity.ODD, PointParity.ODD)
    val naqisReading =
      AlToukhiGeomancyEngine.castFromMothers(
        mothers = listOf(taliki, taliki, taliki, taliki),
        domain = QuestionDomain.GENERAL,
      )
    val naqis = naqisReading.analysis.alQawlInsights!!
    assertEquals(92, naqis.totalChartDots)
    assertTrue(!naqis.isComplete96)
    assertTrue(naqis.aspectLinks.isNotEmpty())
  }
}
