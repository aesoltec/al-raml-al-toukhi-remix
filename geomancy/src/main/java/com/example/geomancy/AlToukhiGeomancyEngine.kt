package com.example.geomancy

import kotlin.random.Random

/**
 * Moteur de calcul et d'interprétation de la méthode géomantique de Cheikh al-Toukhi (Ilm al-Raml)
 * avec appellations ouest-africaines traditionnelles.
 */
object AlToukhiGeomancyEngine {

  private val abjadAlphabet28 =
    listOf(
      "ا" to "Alif",
      "ب" to "Bāʾ",
      "ج" to "Jīm",
      "د" to "Dāl",
      "هـ" to "Hāʾ",
      "و" to "Wāw",
      "ز" to "Zāy",
      "ح" to "Ḥāʾ",
      "ط" to "Ṭāʾ",
      "ي" to "Yāʾ",
      "ك" to "Kāf",
      "ل" to "Lām",
      "م" to "Mīm",
      "ن" to "Nūn",
      "س" to "Sīn",
      "ع" to "ʿAyn",
      "ف" to "Fāʾ",
      "ص" to "Ṣād",
      "ق" to "Qāf",
      "ر" to "Rāʾ",
      "ش" to "Shīn",
      "ت" to "Tāʾ",
      "ث" to "Thāʾ",
      "خ" to "Khāʾ",
      "ذ" to "Dhāl",
      "ض" to "Ḍād",
      "ظ" to "Ẓāʾ",
      "غ" to "Ghayn",
    )

  /**
   * Génère un thème à partir de 16 comptes bruts de traits/points (4 rangs pour chacune des 4 Mères).
   */
  fun castFromDotCounts(
    sixteenCounts: List<Int>,
    domain: QuestionDomain = QuestionDomain.GENERAL,
    questionText: String = "",
    activePlanetHour: Planet = Planet.JUPITER,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): TakhtReading {
    require(sixteenCounts.size == 16) {
      "Le tirage requiert exactement 16 lignes de points (4 par Mère)."
    }
    val parities =
      sixteenCounts.map { count ->
        val safeCount = if (count <= 0) 2 else count
        if (safeCount % 2 == 1) PointParity.ODD else PointParity.EVEN
      }

    val mothers =
      (0 until 4).map { motherIdx ->
        val base = motherIdx * 4
        AlToukhiFigures.findByRanks(
          fire = parities[base],
          air = parities[base + 1],
          water = parities[base + 2],
          earth = parities[base + 3],
        )
      }

    return buildReading(
      mothers = mothers,
      rawMotherDotCounts = sixteenCounts,
      domain = domain,
      questionText = questionText,
      activePlanetHour = activePlanetHour,
      weightTable = weightTable,
    )
  }

  /**
   * Génère un thème directement à partir de 4 figures Mères choisies (Maisons 1 à 4).
   */
  fun castFromMothers(
    mothers: List<GeomanticFigure>,
    domain: QuestionDomain = QuestionDomain.GENERAL,
    questionText: String = "",
    activePlanetHour: Planet = Planet.JUPITER,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): TakhtReading {
    require(mothers.size == 4) { "Exactement 4 figures Mères sont requises." }
    val rawCounts = mothers.flatMap { fig -> fig.ranks.map { it.dots } }
    return buildReading(
      mothers = mothers,
      rawMotherDotCounts = rawCounts,
      domain = domain,
      questionText = questionText,
      activePlanetHour = activePlanetHour,
      weightTable = weightTable,
    )
  }

  /**
   * Génère 16 lignes spontanées (valeurs 5..18) pour un tirage d'intention immédiat.
   */
  fun castSpontaneous(
    domain: QuestionDomain = QuestionDomain.GENERAL,
    questionText: String = "",
    activePlanetHour: Planet = Planet.JUPITER,
    random: Random = Random.Default,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): TakhtReading {
    val counts = List(16) { random.nextInt(5, 19) }
    return castFromDotCounts(
      sixteenCounts = counts,
      domain = domain,
      questionText = questionText,
      activePlanetHour = activePlanetHour,
      weightTable = weightTable,
    )
  }

  private fun buildReading(
    mothers: List<GeomanticFigure>,
    rawMotherDotCounts: List<Int>,
    domain: QuestionDomain,
    questionText: String,
    activePlanetHour: Planet,
    weightTable: FigureWeightTable,
  ): TakhtReading {
    val h1 = mothers[0]
    val h2 = mothers[1]
    val h3 = mothers[2]
    val h4 = mothers[3]

    // Filles (Banat 5..8) : extraction horizontale depuis les 4 Mères
    val h5 = AlToukhiFigures.findByRanks(h1.fire, h2.fire, h3.fire, h4.fire)
    val h6 = AlToukhiFigures.findByRanks(h1.air, h2.air, h3.air, h4.air)
    val h7 = AlToukhiFigures.findByRanks(h1.water, h2.water, h3.water, h4.water)
    val h8 = AlToukhiFigures.findByRanks(h1.earth, h2.earth, h3.earth, h4.earth)

    // Nièces (Hafidat 9..12)
    val h9 = AlToukhiFigures.combine(h1, h2)
    val h10 = AlToukhiFigures.combine(h3, h4)
    val h11 = AlToukhiFigures.combine(h5, h6)
    val h12 = AlToukhiFigures.combine(h7, h8)

    // Témoins (13..14)
    val h13 = AlToukhiFigures.combine(h9, h10)
    val h14 = AlToukhiFigures.combine(h11, h12)

    // Juge (Al-Mizan, 15)
    val h15 = AlToukhiFigures.combine(h13, h14)

    // 16e Maison (Aqibat al-Aqiba) : Juge combiné avec le Consultant (M1)
    val h16 = AlToukhiFigures.combine(h15, h1)

    val allFigures = listOf(h1, h2, h3, h4, h5, h6, h7, h8, h9, h10, h11, h12, h13, h14, h15, h16)
    val chartHouses =
      allFigures.mapIndexed { index, fig ->
        ChartHouse(info = AlToukhiHouses.findByNumber(index + 1), figure = fig)
      }

    val analysis =
      analyzeTakht(
        houses = chartHouses,
        domain = domain,
        activePlanetHour = activePlanetHour,
        weightTable = weightTable,
      )

    return TakhtReading(
      domain = domain,
      questionText = questionText.trim(),
      activePlanetHour = activePlanetHour,
      rawMotherDotCounts = rawMotherDotCounts,
      houses = chartHouses,
      analysis = analysis,
    )
  }

  private fun analyzeTakht(
    houses: List<ChartHouse>,
    domain: QuestionDomain,
    activePlanetHour: Planet,
    weightTable: FigureWeightTable,
  ): TakhtAnalysis {
    val h1 = houses[0].figure
    val domainHouseNum = domain.primaryHouse
    val domainFig = houses[domainHouseNum - 1].figure
    val rightWitness = houses[12].figure
    val leftWitness = houses[13].figure
    val judge = houses[14].figure
    val outcome16 = houses[15].figure

    // 1. Polarité globale et verdict du Juge (M15 + Témoins + M16)
    val keyFigures = listOf(rightWitness, leftWitness, judge, outcome16)
    val score =
      keyFigures.sumOf { fig ->
        when (fig.polarity) {
          FortunePolarity.FORTUNATE -> 1
          FortunePolarity.UNFORTUNATE -> -1
          FortunePolarity.MIXED -> 0
        }
      } +
        when (judge.polarity) {
          FortunePolarity.FORTUNATE -> 1
          FortunePolarity.UNFORTUNATE -> -1
          FortunePolarity.MIXED -> 0
        }

    val overallPolarity =
      when {
        score >= 2 -> FortunePolarity.FORTUNATE
        score <= -2 -> FortunePolarity.UNFORTUNATE
        else -> FortunePolarity.MIXED
      }

    val motionDirectionNote =
      when (outcome16.motion) {
        FigureMotion.ENTERING ->
          "La 16e maison (${outcome16.westAfricanName} / ${outcome16.transliteration}) est Entrante : elle ramène les fruits vers la concrétisation."
        FigureMotion.EXITING ->
          "La 16e maison (${outcome16.westAfricanName} / ${outcome16.transliteration}) est Sortante : elle favorise le dégagement, la dépense ou la séparation."
        FigureMotion.FIXED ->
          "La 16e maison (${outcome16.westAfricanName} / ${outcome16.transliteration}) est Fixe : la situation tend à s'installer durablement."
        FigureMotion.MOBILE ->
          "La 16e maison (${outcome16.westAfricanName} / ${outcome16.transliteration}) est Mobile : évolution rapide et conditions changeantes."
      }

    val verdictHeadline =
      when (overallPolarity) {
        FortunePolarity.FORTUNATE ->
          "Orientation faste sous ${judge.westAfricanName} (${judge.transliteration})"
        FortunePolarity.UNFORTUNATE ->
          "Épreuve ou mise en garde sous ${judge.westAfricanName} (${judge.transliteration})"
        FortunePolarity.MIXED ->
          "Sentence équilibrée / conditionnelle sous ${judge.westAfricanName} (${judge.transliteration})"
      }

    val verdictDetail =
      "Le Juge (M15) ${judge.westAfricanName} (${judge.arabicName}) synthétise le Témoin droit " +
        "${rightWitness.westAfricanName} et le Témoin gauche ${leftWitness.westAfricanName}. " +
        motionDirectionNote

    // 2. Istikhraj al-Damir (Extraction de la pensée cachée)
    val fireCount = houses.count { it.figure.fire == PointParity.ODD }
    val airCount = houses.count { it.figure.air == PointParity.ODD }
    val waterCount = houses.count { it.figure.water == PointParity.ODD }
    val earthCount = houses.count { it.figure.earth == PointParity.ODD }
    val elementCounts =
      mapOf(
        Element.FIRE to fireCount,
        Element.AIR to airCount,
        Element.WATER to waterCount,
        Element.EARTH to earthCount,
      )
    val dominantElement = elementCounts.maxByOrNull { it.value }?.key ?: judge.dominantElement

    val unspokenFocus =
      when (dominantElement) {
        Element.FIRE ->
          "Le rang actif du Feu domine ($fireCount points impairs) : l'esprit est occupé par l'initiative, l'honneur, l'urgence ou une rivalité."
        Element.AIR ->
          "Le rang actif de l'Air domine ($airCount points impairs) : l'esprit est centré sur les échanges, décisions, accords, paroles ou déplacements."
        Element.WATER ->
          "Le rang actif de l'Eau domine ($waterCount points impairs) : l'esprit se porte sur les liens affectifs, la famille, l'intuition ou des sentiments voilés."
        Element.EARTH ->
          "Le rang actif de la Terre domine ($earthCount points impairs) : l'esprit recherche la sécurité matérielle, les biens, la stabilité ou la santé."
      }

    val taskinResonance =
      buildString {
        val naturalSeatOfJudge = AlToukhiHouses.findByNumber(judge.id)
        append(
          "Selon le Taskīn al-Zanātī, ${judge.westAfricanName} (${judge.transliteration}) gouverne naturellement la Maison ${judge.id} (${naturalSeatOfJudge.englishName}), ",
        )
        val matchingHouses = houses.filter { it.figure.id == it.info.taskinRulerId }
        if (matchingHouses.isNotEmpty()) {
          val labels =
            matchingHouses.joinToString { "M${it.info.number} (${it.figure.westAfricanName})" }
          append("et ${matchingHouses.size} maison(s) siègent dans leur demeure naturelle : $labels.")
        } else {
          append("orientant l'attention cachée vers : ${naturalSeatOfJudge.englishName.lowercase()}.")
        }
      }

    // 3. Intiqal al-Shakl (Migration du Consultant M1 et du Sujet)
    val querentRepeats = houses.filter { it.figure.id == h1.id }.map { it.info.number }
    val querentMigrationText =
      if (querentRepeats.size <= 1) {
        "Le Consultant (${h1.westAfricanName}) reste localisé en Maison 1 : autonomie et recentrage."
      } else {
        val destinations = querentRepeats.filter { it != 1 }.joinToString { "M$it" }
        val connectsToDomain = domainHouseNum in querentRepeats
        val base = "Le Consultant (${h1.westAfricanName}) se déplace en $destinations."
        if (connectsToDomain && domainHouseNum != 1) {
          "$base Lien direct : le consultant rejoint la Maison $domainHouseNum (${domain.title})."
        } else {
          base
        }
      }

    val domainRepeats = houses.filter { it.figure.id == domainFig.id }.map { it.info.number }
    val domainMigrationText =
      if (1 in domainRepeats && domainHouseNum != 1) {
        "Le Significateur (${domainFig.westAfricanName}, M$domainHouseNum) apparaît aussi en Maison 1 : l'objet de la quête vient au-devant du consultant."
      } else if (domainRepeats.size > 1) {
        val others = domainRepeats.filter { it != domainHouseNum }.joinToString { "M$it" }
        "Le Significateur (${domainFig.westAfricanName}) résonne également en $others, reliant ce domaine à ces maisons."
      } else {
        "Le Significateur (${domainFig.westAfricanName}) demeure uniquement en Maison $domainHouseNum sans se disperser."
      }

    // 4. Mujawara (Voisinage)
    val targetHouse = if (domainHouseNum in 2..14) domainHouseNum else 13
    val targetChartHouse = houses[targetHouse - 1]
    val leftNeighbor = if (targetHouse > 1) houses[targetHouse - 2] else null
    val rightNeighbor = if (targetHouse < 16) houses[targetHouse] else null
    val neighborList = listOfNotNull(leftNeighbor, rightNeighbor)
    val beneficNeighbors = neighborList.count { it.figure.polarity == FortunePolarity.FORTUNATE }
    val maleficNeighbors = neighborList.count { it.figure.polarity == FortunePolarity.UNFORTUNATE }
    val flankingStatus =
      when {
        beneficNeighbors == neighborList.size && neighborList.isNotEmpty() ->
          "Soutenu (Muʾayyad) : entouré de figures fastes qui renforcent la stabilité."
        maleficNeighbors == neighborList.size && neighborList.isNotEmpty() ->
          "Assiégé (Maḥṣūr) : encadré par des figures défavorables, appelant à la prudence."
        else ->
          "Compagnie partagée (Mujāwara) : des influences voisines mixtes tempèrent les extrêmes."
      }

    // 5. Extraction Abjad & Délais (Ilm al-Huruf)
    val keyAbjadSum = h1.abjadValue + domainFig.abjadValue + judge.abjadValue + outcome16.abjadValue
    val mod28Index = ((keyAbjadSum - 1) % 28 + 28) % 28
    val (extractedLetter, letterName) = abjadAlphabet28[mod28Index]
    val activePointsTotal = houses.sumOf { it.figure.activeRankCount }
    val timingUnit =
      when (judge.motion) {
        FigureMotion.MOBILE -> "Jours (${(activePointsTotal % 7) + 1}–${(activePointsTotal % 14) + 3} j)"
        FigureMotion.ENTERING, FigureMotion.EXITING -> "Semaines (${judge.activeRankCount + 1}–${outcome16.activeRankCount + 3} sem.)"
        FigureMotion.FIXED -> "Mois (${(judge.id % 4) + 1}–${(judge.id % 6) + 2} mois)"
      }

    // 6. Heure planétaire & Conseil spirituel
    val planetaryMatch =
      judge.planet == activePlanetHour ||
        h1.planet == activePlanetHour ||
        domainFig.planet == activePlanetHour

    val planetaryAlignmentNote =
      if (planetaryMatch) {
        "Résonance forte : l'heure planétaire choisie (${activePlanetHour.englishName} / ${activePlanetHour.arabicName}) " +
          "correspond à une figure maîtresse du thème, renforçant la clarté."
      } else {
        "Décalage planétaire : tirage effectué sous ${activePlanetHour.englishName}, tandis que le Juge dépend de " +
          "${judge.planet.englishName} (${judge.planet.dayEnglish}). Al-Toukhi conseille d'agir le ${judge.planet.dayEnglish.lowercase()} ou à l'heure de ${judge.planet.englishName}."
      }

    val humoralDiagnosis =
      "Tempérament du consultant : ${h1.dominantElement.englishName} (${h1.dominantElement.quality} — ${h1.dominantElement.humor}) ; " +
        "atmosphère dominante du thème : ${dominantElement.englishName} (${dominantElement.quality})."

    val spiritualPrescription =
      "Juge ${judge.westAfricanName} (${judge.spiritualProfile.ruhaniyyaCorrespondence}) : " +
        "Médite sur « ${judge.spiritualProfile.contemplativeDhikr} ». " +
        "${judge.spiritualProfile.counsel} ${outcome16.spiritualProfile.operativeNote}"

    // 7. Duel / Al-Ghalib wa-l-Maghlub (pondération symétrique + règles de Bulūgh al-Amal & Al-Fulk al-Mashḥūn)
    val contestPrediction =
      if (domain == QuestionDomain.CONTEST) {
        predictContestOutcome(houses = houses, weightTable = weightTable)
      } else {
        null
      }

    // 8. Synthèse doctrinale d'Al-Fulk al-Mashḥūn (Ṣidq al-Raml, 4 Pures, Bāṭin al-Takht, M12 & Délais)
    val alFulkInsights = computeAlFulkGeneralInsights(houses = houses, domain = domain)

    // 9. Synthèse d'Al-Qawl al-Jāmiʿ & Manuscrits de La Mecque (Seuil 96, Vie/Fermeture, 5 Triangles, Aspects)
    val alQawlInsights = computeAlQawlGeneralInsights(houses = houses, domain = domain)

    return TakhtAnalysis(
      overallPolarity = overallPolarity,
      verdictHeadline = HumanTranslationLayer.humanize(verdictHeadline),
      verdictDetail = HumanTranslationLayer.humanize(verdictDetail),
      damirReport =
        DamirReport(
          dominantElement = dominantElement,
          elementCounts = elementCounts,
          unspokenFocus = HumanTranslationLayer.humanize(unspokenFocus),
          taskinResonance = HumanTranslationLayer.humanize(taskinResonance),
        ),
      querentMigration =
        MigrationLink(
          sourceHouse = 1,
          sourceLabel = "Consultant (M1)",
          figure = h1,
          repeatedHouses = querentRepeats,
          interpretation = HumanTranslationLayer.humanize(querentMigrationText),
        ),
      domainMigration =
        MigrationLink(
          sourceHouse = domainHouseNum,
          sourceLabel = "${domain.title} (M$domainHouseNum)",
          figure = domainFig,
          repeatedHouses = domainRepeats,
          interpretation = HumanTranslationLayer.humanize(domainMigrationText),
        ),
      judgeFlanking =
        FlankingReport(
          targetHouse = targetHouse,
          targetFigure = targetChartHouse.figure,
          leftNeighbor = leftNeighbor,
          rightNeighbor = rightNeighbor,
          status = HumanTranslationLayer.humanize(flankingStatus),
        ),
      abjadExtraction =
        AbjadExtraction(
          totalAbjadSum = keyAbjadSum,
          reducedMod28 = mod28Index + 1,
          extractedLetter = extractedLetter,
          letterName = letterName,
          timingEstimate = timingUnit,
          favorableDirection = judge.dominantElement.direction,
        ),
      planetaryAlignmentNote = HumanTranslationLayer.humanize(planetaryAlignmentNote),
      humoralDiagnosis = HumanTranslationLayer.humanize(humoralDiagnosis),
      spiritualPrescription = HumanTranslationLayer.humanize(spiritualPrescription),
      contestComparison = contestPrediction?.summaryText,
      contestPrediction = contestPrediction,
      alFulkInsights = alFulkInsights,
      alQawlInsights = alQawlInsights,
    )
  }

  /**
   * Calcule la prédiction de duel (Al-Ghālib wa-l-Maghlūb) pour les 16 maisons selon une table
   * de poids des figures [weightTable] enrichie par les règles de « Bulūgh al-Amal »
   * (Bāb 1, 4, 14, 26, 29 et Faṣl fī al-Khuṣūma p. 97).
   */
  @Reliability(ReliabilityLevel.D)
  fun predictContestOutcome(
    houses: List<ChartHouse>,
    weightTable: FigureWeightTable = FigureWeightTable.default(),
  ): ContestPrediction {
    require(houses.size == 16) { "Un Takht complet à 16 maisons est requis." }

    val h1 = houses[0].figure
    val h2 = houses[1].figure
    val h4 = houses[3].figure
    val h7 = houses[6].figure
    val h8 = houses[7].figure
    val h9 = houses[8].figure
    val h10 = houses[9].figure
    val h11 = houses[10].figure
    val h12 = houses[11].figure
    val rightWitness = houses[12].figure
    val leftWitness = houses[13].figure
    val judge = houses[14].figure
    val outcome16 = houses[15].figure

    val judgeRepetitions = houses.count { it.figure.id == judge.id }

    var sideAScore =
      (weightTable.weightFor(h1) * 3.0) +
        (weightTable.weightFor(rightWitness) * 3.0) +
        (weightTable.weightFor(h10) * 3.0) +
        (weightTable.weightFor(h2) * 2.0) +
        weightTable.weightFor(h9)

    var sideBScore =
      (weightTable.weightFor(h7) * 3.0) +
        (weightTable.weightFor(leftWitness) * 3.0) +
        (weightTable.weightFor(h4) * 2.0) +
        (weightTable.weightFor(h8) * 2.0) +
        weightTable.weightFor(h11) +
        weightTable.weightFor(h12)

    if (judge.polarity == FortunePolarity.UNFORTUNATE) {
      sideAScore -= if (judgeRepetitions >= 2) 4.0 else 2.0
    }
    if (judge.id == h1.id) {
      sideAScore += if (h1.polarity == FortunePolarity.UNFORTUNATE) -2.0 else 2.0
    }
    if (judge.id == h7.id) {
      sideBScore += if (h7.polarity == FortunePolarity.UNFORTUNATE) -2.0 else 2.0
    }

    val campBCapturesOutcome =
      outcome16.id == h7.id && outcome16.id != h1.id && outcome16.id != rightWitness.id
    if (campBCapturesOutcome) {
      sideBScore += 3.0
    } else if (outcome16.polarity == FortunePolarity.FORTUNATE) {
      sideAScore += 2.0
    } else if (outcome16.polarity == FortunePolarity.UNFORTUNATE) {
      sideAScore -= 2.0
    }

    // --- Intégration doctrinale de Bulūgh al-Amal fī ʿIlm al-Raml ---
    val activeRulesNotes = mutableListOf<String>()
    var doctrinalDeltaA = 0.0
    var doctrinalDeltaB = 0.0

    // 1) Bāb 1 & Bāb 14 : Parité de M15 et seuil quantitatif des points (> 90 / < 90)
    val totalChartDots = houses.sumOf { it.figure.totalDots }
    val isOpenThemeAbove90 = totalChartDots >= 90
    val judgeParityValid = judge.totalDots % 2 == 0
    when {
      totalChartDots > 90 ->
        activeRulesNotes += "Bāb 14 : $totalChartDots pts (> 90, thème ouvert propice à un verdict franc)"
      totalChartDots == 90 ->
        activeRulesNotes += "Bāb 14 : 90 pts (seuil d'équilibre du thème)"
      else ->
        activeRulesNotes += "Bāb 14 : $totalChartDots pts (< 90, thème retenu favorisant les scores serrés)"
    }

    // 2) Faṣl fī al-Khuṣūma (p. 97) : Points ouverts Droite (M1+M13+M15) vs Gauche (M7+M14+M16) & M10
    val rightSideOpenPoints = h1.activeRankCount + rightWitness.activeRankCount + judge.activeRankCount
    val leftSideOpenPoints = h7.activeRankCount + leftWitness.activeRankCount + outcome16.activeRankCount
    if (rightSideOpenPoints >= leftSideOpenPoints + 3) {
      doctrinalDeltaA += 1.0
      activeRulesNotes += "Faṣl al-Khuṣūma (p. 97) : points ouverts droite ($rightSideOpenPoints) > gauche ($leftSideOpenPoints)"
    } else if (leftSideOpenPoints >= rightSideOpenPoints + 3) {
      doctrinalDeltaB += 1.0
      activeRulesNotes += "Faṣl al-Khuṣūma (p. 97) : points ouverts gauche ($leftSideOpenPoints) > droite ($rightSideOpenPoints)"
    }

    val campAReachesCrownM10 = h1.id == h10.id
    val campBReachesCrownM10 = h7.id == h10.id
    if (campAReachesCrownM10 && h10.polarity != FortunePolarity.UNFORTUNATE) {
      doctrinalDeltaA += 1.0
      activeRulesNotes += "Intiqāl p. 97 : Camp A (M1) occupe l'arbitre M10 (${h10.westAfricanName})"
    }
    if (campBReachesCrownM10 && h10.polarity != FortunePolarity.UNFORTUNATE) {
      doctrinalDeltaB += 1.0
      activeRulesNotes += "Intiqāl p. 97 : Camp B (M7) occupe l'arbitre M10 (${h10.westAfricanName})"
    }

    // 3) Bāb 14 & Bāb 4 : Les 4 Mawāzīn (M12, M14, M15, M16), M11 en arbitrage, et bilan des Awtād
    val mawazin = listOf(h12, leftWitness, judge, outcome16)
    val mawazinFortunate = mawazin.count { it.polarity == FortunePolarity.FORTUNATE }
    val mawazinUnfortunate = mawazin.count { it.polarity == FortunePolarity.UNFORTUNATE }
    if (mawazinFortunate >= 3 && mawazinUnfortunate == 0) {
      doctrinalDeltaA += 1.0
      activeRulesNotes += "Bāb 14 (Mawāzīn) : $mawazinFortunate/4 fastes soutiennent le Camp A"
    } else if (mawazinUnfortunate >= 3) {
      doctrinalDeltaA -= 1.0
      activeRulesNotes += "Bāb 14 (Mawāzīn) : $mawazinUnfortunate/4 défavorables freinent le Camp A"
    } else if (mawazinFortunate == mawazinUnfortunate) {
      when (h11.polarity) {
        FortunePolarity.FORTUNATE -> {
          doctrinalDeltaA += 0.5
          activeRulesNotes += "Mawāzīn équilibrées ($mawazinFortunate/$mawazinUnfortunate) : M11 (${h11.westAfricanName}) appuie Camp A"
        }
        FortunePolarity.UNFORTUNATE -> {
          doctrinalDeltaB += 0.5
          activeRulesNotes += "Mawāzīn équilibrées ($mawazinFortunate/$mawazinUnfortunate) : M11 (${h11.westAfricanName}) appuie Camp B"
        }
        FortunePolarity.MIXED -> Unit
      }
    }

    fun awtadValue(fig: GeomanticFigure): Int =
      when (fig.polarity) {
        FortunePolarity.FORTUNATE -> 1
        FortunePolarity.UNFORTUNATE -> -1
        FortunePolarity.MIXED -> 0
      }
    val awtadCampABalance = (awtadValue(h1) + awtadValue(h10)) - (awtadValue(h7) + awtadValue(h4))

    // 4) Bāb 26 (Al-Ghālib wa-l-Maghlūb) & Bāb 29 (Liqāʾ al-ʿAskarayn) : répétitions dominantes (≥ 3)
    val countsById = houses.groupingBy { it.figure.id }.eachCount()
    val campAHouseNums = setOf(1, 2, 9, 10, 13)
    val campBHouseNums = setOf(4, 7, 8, 11, 12, 14)

    var bab2629DeltaA = 0.0
    var bab2629DeltaB = 0.0

    val adamaCount = countsById[1] ?: 0
    val kalalawCount = countsById[10] ?: 0
    val moussaCount = countsById[4] ?: 0
    val talikiCount = countsById[16] ?: 0
    val ousmanaCount = countsById[2] ?: 0
    val bayadaCount = countsById[9] ?: 0
    val lomaraCount = countsById[8] ?: 0
    val mangouchiCount = countsById[7] ?: 0
    val malidjouCount = countsById[12] ?: 0
    val badraAliCount = countsById[15] ?: 0
    val solomanaCount = countsById[6] ?: 0
    val inzanCount = countsById[3] ?: 0
    val djanfaCount = countsById[14] ?: 0
    val tontiguiCount = countsById[13] ?: 0

    if (adamaCount >= 3 && kalalawCount < 2) {
      bab2629DeltaA += 1.0
      activeRulesNotes += "Bāb 26 : Adama (×$adamaCount) favorise l'ascendant du Camp A"
    }
    if (bayadaCount >= 3) {
      bab2629DeltaA += 1.0
      activeRulesNotes += "Bāb 26 : Bayada (×$bayadaCount) accorde la plus grande part au Camp A"
    }
    if (malidjouCount >= 3 || badraAliCount >= 3) {
      bab2629DeltaA += 0.5
      activeRulesNotes += "Bāb 29 : Malidjou/Badra Ali dominant favorise l'initiateur (Camp A)"
    }
    if (lomaraCount >= 3) {
      val lomaraInA = houses.count { it.info.number in campAHouseNums && it.figure.id == 8 }
      val lomaraInB = houses.count { it.info.number in campBHouseNums && it.figure.id == 8 }
      if (lomaraInA > lomaraInB) {
        bab2629DeltaA += 1.0
        activeRulesNotes += "Bāb 29 : Lomara (×$lomaraCount) majoritaire côté Est (Camp A)"
      } else if (lomaraInB > lomaraInA) {
        bab2629DeltaB += 0.5
        activeRulesNotes += "Bāb 29 : Lomara (×$lomaraCount) concentrée dans les maisons du Camp B"
      }
    }
    if (mangouchiCount >= 3) {
      bab2629DeltaA += 0.5
      activeRulesNotes += "Bāb 29 : Mangouchi (×$mangouchiCount) fragilise l'aile Ouest"
    }

    if (ousmanaCount >= 3) {
      bab2629DeltaB += 1.0
      activeRulesNotes += "Bāb 29 : Ousmana (×$ousmanaCount) annonce la retenue du Camp A face au Camp B"
    }
    if (moussaCount >= 2 && talikiCount >= 2) {
      bab2629DeltaB += 1.0
      activeRulesNotes += "Bāb 26 : duo Moussa (×$moussaCount) + Taliki (×$talikiCount) avantage l'adversaire"
    }
    if (adamaCount >= 2 && kalalawCount >= 2) {
      bab2629DeltaB += 1.0
      activeRulesNotes += "Bāb 29 : duo Adama (×$adamaCount) + Kalalaw (×$kalalawCount) favorise l'Ouest (Camp B)"
    }

    doctrinalDeltaA += bab2629DeltaA.coerceAtMost(1.5)
    doctrinalDeltaB += bab2629DeltaB.coerceAtMost(1.5)

    val preAlFulkDuel = computeAlFulkDuelInsights(houses)
    val hawiDuel = computeHawiAlAkhbarDuelInsights(houses)
    val alFulkDeltaA = if (preAlFulkDuel.consensusVerdict == MatchOutcome.CAMP_A_WIN) 1.0 else 0.0
    val alFulkDeltaB = if (preAlFulkDuel.consensusVerdict == MatchOutcome.CAMP_B_WIN) 1.0 else 0.0
    if (alFulkDeltaA > 0.0) {
      doctrinalDeltaA += alFulkDeltaA
      activeRulesNotes += "Al-Fulk al-Mashḥūn : consensus ≥ 3/4 (+1,0) en faveur du Camp A"
    } else if (alFulkDeltaB > 0.0) {
      doctrinalDeltaB += alFulkDeltaB
      activeRulesNotes += "Al-Fulk al-Mashḥūn : consensus ≥ 3/4 (+1,0) en faveur du Camp B"
    }

    // Dignité planétaire (Sharaf/Hubūṭ) & Ḥāwī al-Akhbār en appoint léger (±0,25)
    if (hawiDuel.dignityBalanceA > hawiDuel.dignityBalanceB) {
      doctrinalDeltaA += 0.25
    } else if (hawiDuel.dignityBalanceB > hawiDuel.dignityBalanceA) {
      doctrinalDeltaB += 0.25
    }
    activeRulesNotes += "Ḥāwī al-Akhbār : ${hawiDuel.summaryNote}"

    sideAScore += doctrinalDeltaA
    sideBScore += doctrinalDeltaB

    // Signaux de partage / trêve selon Bāb 26 & 29 (tempérés par le seuil > 90 de Bāb 14)
    val strongBab29DrawPattern =
      h1.id == h7.id ||
        solomanaCount >= 3 ||
        djanfaCount >= 3 ||
        inzanCount >= 3
    val conditionalBab29DrawPattern =
      (!isOpenThemeAbove90 || judge.polarity == FortunePolarity.UNFORTUNATE) &&
        (tontiguiCount >= 3 || moussaCount >= 3 || talikiCount >= 3)

    if (h1.id == h7.id) {
      activeRulesNotes += "Miroir M1 = M7 (${h1.westAfricanName}) : équilibre direct entre les deux camps"
    }
    if (solomanaCount >= 3) {
      activeRulesNotes += "Bāb 29 : Solomana (×$solomanaCount) incline à la trêve / partage"
    } else if (djanfaCount >= 3 || inzanCount >= 3) {
      activeRulesNotes += "Bāb 29 : neutralisation mutuelle par répétition (${if (djanfaCount >= 3) "Djanfa Almami" else "Inzan"})"
    } else if (conditionalBab29DrawPattern) {
      activeRulesNotes += "Bāb 26/29 : résistance fermée favorable au nul"
    }

    val diff = kotlin.math.abs(sideAScore - sideBScore)
    val classicDrawSignal =
      judge.id == 15 /* Badra Ali */ ||
        judge.id == 4 /* Moussa */ ||
        outcome16.id == 4 /* Moussa */ ||
        judge.polarity == FortunePolarity.UNFORTUNATE
    val hasDrawSignal =
      !campBCapturesOutcome && (classicDrawSignal || strongBab29DrawPattern || conditionalBab29DrawPattern)

    val effectiveDrawThreshold =
      if (!isOpenThemeAbove90 && (classicDrawSignal || strongBab29DrawPattern || conditionalBab29DrawPattern)) {
        weightTable.drawThreshold + 0.5
      } else {
        weightTable.drawThreshold
      }

    val predictedOutcome =
      when {
        diff < 0.25 -> MatchOutcome.DRAW
        diff <= effectiveDrawThreshold && hasDrawSignal -> MatchOutcome.DRAW
        sideAScore > sideBScore -> MatchOutcome.CAMP_A_WIN
        else -> MatchOutcome.CAMP_B_WIN
      }

    val nuanceNotes =
      buildList {
        if (judge.polarity == FortunePolarity.UNFORTUNATE) {
          add("le Juge ${judge.westAfricanName} (×$judgeRepetitions) pèse contre le Camp A")
        }
        if (campBCapturesOutcome) {
          add("l'Issue M16 (${outcome16.westAfricanName}) bascule vers le Camp B (M7)")
        }
        if (h10.polarity == FortunePolarity.UNFORTUNATE) {
          add("M10 (${h10.westAfricanName}) fragilise la concrétisation du Camp A")
        }
        if (outcome16.id == 4) {
          add("M16 Moussa indique un fort facteur de partage")
        }
        if (!isOpenThemeAbove90) {
          add("Bāb 14 : $totalChartDots pts (< 90, jeu retenu)")
        }
      }
    val nuanceSuffix = if (nuanceNotes.isEmpty()) "" else " — " + nuanceNotes.joinToString("; ") + "."

    val scoreAStr = FigureWeightTable.formatWeight(sideAScore)
    val scoreBStr = FigureWeightTable.formatWeight(sideBScore)
    val calibratedTag = if (weightTable.isCalibrated) " [poids calibrés · Bulūgh al-Amal]" else " [Bulūgh al-Amal]"

    val edgeText =
      when {
        diff < 0.25 ->
          "Équilibre strict ($scoreAStr à $scoreBStr) : forte probabilité de match nul ou de départage sur le Juge ${judge.westAfricanName}$nuanceSuffix"
        diff <= effectiveDrawThreshold && hasDrawSignal ->
          "Duel très serré avec fort risque de match nul ($scoreAStr contre $scoreBStr, léger penchant ${if (sideAScore > sideBScore) "Camp A" else "Camp B"})$nuanceSuffix"
        sideAScore > sideBScore ->
          "Le Camp A (M1 ${h1.westAfricanName}, M10 ${h10.westAfricanName}, M13 ${rightWitness.westAfricanName}) tient l'avantage ($scoreAStr contre $scoreBStr)$nuanceSuffix"
        else ->
          "Le Camp B (M7 ${h7.westAfricanName}, M4 ${h4.westAfricanName}, M14 ${leftWitness.westAfricanName}) tient l'avantage ($scoreBStr contre $scoreAStr)$nuanceSuffix"
      }

    val insights =
      BulughAlAmalDuelInsights(
        totalChartDots = totalChartDots,
        isOpenThemeAbove90 = isOpenThemeAbove90,
        judgeParityValid = judgeParityValid,
        rightSideOpenPoints = rightSideOpenPoints,
        leftSideOpenPoints = leftSideOpenPoints,
        mawazinFortunateCount = mawazinFortunate,
        mawazinUnfortunateCount = mawazinUnfortunate,
        m11HopePolarity = h11.polarity,
        awtadCampABalance = awtadCampABalance,
        campAReachesCrownM10 = campAReachesCrownM10,
        campBReachesCrownM10 = campBReachesCrownM10,
        doctrinalDeltaA = doctrinalDeltaA,
        doctrinalDeltaB = doctrinalDeltaB,
        activeRulesNotes = activeRulesNotes,
      )

    val alFulkDuel =
      finalizeAlFulkConvergence(
        base = preAlFulkDuel,
        primaryOutcome = predictedOutcome,
        deltaA = alFulkDeltaA,
        deltaB = alFulkDeltaB,
      )

    val matchPrecision =
      computeContestMatchPrecision(
        houses = houses,
        predictedOutcome = predictedOutcome,
        sideAScore = sideAScore,
        sideBScore = sideBScore,
        totalChartDots = totalChartDots,
        rightSideOpenPoints = rightSideOpenPoints,
        leftSideOpenPoints = leftSideOpenPoints,
      )

    val alQawlDuel = computeAlQawlDuelInsights(houses)

    return ContestPrediction(
      predictedOutcome = predictedOutcome,
      sideAScore = sideAScore,
      sideBScore = sideBScore,
      campBCapturesOutcome = campBCapturesOutcome,
      hasDrawSignal = hasDrawSignal,
      summaryText = HumanTranslationLayer.humanize("Al-Ghālib wa-l-Maghlūb$calibratedTag : $edgeText"),
      bulughInsights = insights,
      alFulkInsights = alFulkDuel,
      hawiInsights = hawiDuel,
      alQawlInsights = alQawlDuel,
      matchPrecision = matchPrecision,
    )
  }

  @Reliability(ReliabilityLevel.D)
  private fun computeContestMatchPrecision(
    houses: List<ChartHouse>,
    predictedOutcome: MatchOutcome,
    sideAScore: Double,
    sideBScore: Double,
    totalChartDots: Int,
    rightSideOpenPoints: Int,
    leftSideOpenPoints: Int,
  ): ContestMatchPrecisionForecast {
    val activeFireTotal = houses.count { it.figure.fire == PointParity.ODD }
    val solomanaCount = houses.count { it.figure.id == 6 }
    val totalOddPoints = houses.sumOf { it.figure.activeRankCount }
    val h1 = houses[0].figure
    val h7 = houses[6].figure
    val judge = houses[14].figure

    fun attackSpark(fig: GeomanticFigure): Int {
      var pts = 0
      if (fig.dominantElement == Element.FIRE || fig.dominantElement == Element.AIR) pts += 1
      if (fig.motion == FigureMotion.ENTERING || fig.motion == FigureMotion.MOBILE) pts += 1
      if (fig.id in setOf(1, 2, 8, 10, 11, 12, 14)) pts += 1
      if (fig.id in setOf(6, 7)) pts -= 1
      return pts.coerceAtLeast(0)
    }

    val attackA = listOf(1, 2, 5, 10, 13).sumOf { attackSpark(houses[it - 1].figure) }
    val attackB = listOf(4, 7, 8, 11, 14).sumOf { attackSpark(houses[it - 1].figure) }
    val margin = kotlin.math.abs(sideAScore - sideBScore)

    var bttsScore = 0
    if (totalChartDots >= 90) bttsScore += 2 else if (totalChartDots < 84) bttsScore -= 2 else bttsScore -= 1
    when {
      attackA >= 4 && attackB >= 4 -> bttsScore += 2
      attackA >= 3 && attackB >= 3 -> bttsScore += 1
      else -> bttsScore -= 2
    }
    if (rightSideOpenPoints >= 5 && leftSideOpenPoints >= 5) bttsScore += 1
    if (activeFireTotal >= 8) bttsScore += 1 else if (activeFireTotal <= 5) bttsScore -= 1
    if (solomanaCount >= 2 || h1.id == 6 || h7.id == 6) bttsScore -= 2
    if (margin >= 8.0) bttsScore -= 1

    val sterileLock =
      totalChartDots < 84 ||
        (solomanaCount >= 2 && activeFireTotal <= 7) ||
        ((h1.id == 6 || h7.id == 6) && activeFireTotal <= 6) ||
        (totalOddPoints <= 26 && activeFireTotal <= 6)

    val (scoreA, scoreB, altScore) =
      when (predictedOutcome) {
        MatchOutcome.DRAW -> {
          when {
            sterileLock || bttsScore < 1 -> Triple(0, 0, "1–1 ou 1–0")
            totalChartDots >= 98 && activeFireTotal >= 9 && attackA >= 5 && attackB >= 5 ->
              Triple(2, 2, "1–1 ou 2–1")
            else -> Triple(1, 1, if (totalChartDots < 90) "0–0 ou 2–2" else "2–2 ou 0–0")
          }
        }
        MatchOutcome.CAMP_A_WIN -> {
          val bttsFlag = bttsScore >= 2
          val gap =
            when {
              margin >= 9.5 && attackA >= 6 -> 3
              margin >= 5.0 -> 2
              else -> 1
            }
          val loser =
            when {
              !bttsFlag -> 0
              totalChartDots >= 98 && attackA >= 5 && attackB >= 5 && gap <= 2 -> 2
              else -> 1
            }
          val winner = loser + gap
          val alt =
            when {
              loser == 0 && gap == 1 -> "2–0 ou 2–1"
              loser == 0 && gap == 2 -> "1–0 ou 3–1"
              loser == 0 -> "2–0 ou 3–1"
              gap == 1 -> "${winner - 1}–${loser - 1} ou ${winner + 1}–$loser"
              else -> "$winner–0 ou ${winner - 1}–$loser"
            }
          Triple(winner, loser, alt)
        }
        MatchOutcome.CAMP_B_WIN -> {
          val bttsFlag = bttsScore >= 2
          val gap =
            when {
              margin >= 9.5 && attackB >= 6 -> 3
              margin >= 5.0 -> 2
              else -> 1
            }
          val loser =
            when {
              !bttsFlag -> 0
              totalChartDots >= 98 && attackA >= 5 && attackB >= 5 && gap <= 2 -> 2
              else -> 1
            }
          val winner = loser + gap
          val alt =
            when {
              loser == 0 && gap == 1 -> "0–2 ou 1–2"
              loser == 0 && gap == 2 -> "0–1 ou 1–3"
              loser == 0 -> "0–2 ou 1–3"
              gap == 1 -> "${loser - 1}–${winner - 1} ou $loser–${winner + 1}"
              else -> "0–$winner ou $loser–${winner - 1}"
            }
          Triple(loser, winner, alt)
        }
      }

    val bothScore = scoreA > 0 && scoreB > 0
    val bttsConfidence = (54 + kotlin.math.abs(bttsScore) * 6).coerceIn(54, 89)
    val bttsReason =
      if (bothScore) {
        "Thème ${if (totalChartDots >= 90) "ouvert ($totalChartDots pts)" else "animé en Feu ($activeFireTotal/16)"} avec potentiel offensif équilibré (A=$attackA, B=$attackB)."
      } else {
        "Fermeture défensive (points=$totalChartDots, Solomana×$solomanaCount, Feu=$activeFireTotal/16) limitant l’un des deux camps."
      }

    val totalGoals = scoreA + scoreB
    val totalGoalsRange =
      when {
        totalGoals <= 1 -> "0–2 buts"
        totalGoals == 2 -> "1–3 buts"
        totalGoals == 3 -> "2–4 buts"
        else -> "3–5+ buts"
      }

    val scoreReasoning =
      when (predictedOutcome) {
        MatchOutcome.DRAW ->
          if (scoreA == 0) {
            "Partage fermé : faible ouverture ($totalChartDots pts) et présence de retenue."
          } else {
            "Parité attendue autour de $scoreA–$scoreB selon l’équilibre du Juge ${judge.westAfricanName} et l’indice d’ouverture ($totalChartDots pts)."
          }
        MatchOutcome.CAMP_A_WIN, MatchOutcome.CAMP_B_WIN ->
          "Écart géomantique de ${FigureWeightTable.formatWeight(margin)} pt(s) (${if (totalChartDots >= 90) "thème ouvert > 90" else "jeu retenu < 90"}) donnant l’avantage ${predictedOutcome.shortLabel}."
      }

    fun houseTension(houseNum: Int): Int {
      val fig = houses[houseNum - 1].figure
      var t =
        when (fig.id) {
          8 -> 3 // Lomara (Mars, Feu)
          14 -> 2 // Djanfa Almami (Mars, Air)
          5 -> 2 // Lawsana (Dhanab)
          6 -> 2 // Solomana (Saturne)
          3, 7 -> 1 // Inzan, Mangouchi
          else -> 0
        }
      if (AlToukhiHouses.dignityOf(fig, houseNum) == PlanetaryDignity.FALL) {
        t += 1
      }
      return t
    }

    val sharedHeat = listOf(6, 12, 15).sumOf(::houseTension)
    val tensionA = listOf(1, 2, 5, 10, 13).sumOf(::houseTension) + (sharedHeat / 2)
    val tensionB = listOf(4, 7, 8, 11, 14).sumOf(::houseTension) + ((sharedHeat + 1) / 2)
    val totalTension = tensionA + tensionB

    fun tensionToYellowRange(t: Int): IntRange =
      when {
        t <= 1 -> 0..2
        t <= 3 -> 1..2
        t <= 5 -> 1..3
        t <= 7 -> 2..4
        else -> 3..5
      }

    val yellowA = tensionToYellowRange(tensionA)
    val yellowB = tensionToYellowRange(tensionB)
    val totalMinY = (yellowA.first + yellowB.first).coerceAtLeast(1)
    val totalMaxY = (yellowA.last + yellowB.last).coerceAtMost(9)
    val yellowTotalRange = "$totalMinY–$totalMaxY cartons"
    val cardIntensity =
      when {
        totalTension >= 14 -> CardIntensityLevel.CRITICAL
        totalTension >= 10 -> CardIntensityLevel.HIGH
        totalTension >= 6 -> CardIntensityLevel.MODERATE
        else -> CardIntensityLevel.LOW
      }

    val marsCount = houses.count { it.figure.id == 8 || it.figure.id == 14 }
    val marsCritical = listOf(6, 8, 12, 15).filter { houses[it - 1].figure.id == 8 || houses[it - 1].figure.id == 14 }
    val ruptureCritical = listOf(12, 15).filter { houses[it - 1].figure.id in setOf(5, 6, 8) }
    val fallCount = houses.count { AlToukhiHouses.dignityOf(it.figure, it.info.number) == PlanetaryDignity.FALL }

    val redScore =
      (if (marsCritical.isNotEmpty()) 3 else 0) +
        (if (marsCount >= 3) 2 else 0) +
        (if (ruptureCritical.isNotEmpty()) 2 else 0) +
        fallCount.coerceAtMost(3) +
        (if (totalTension >= 12) 2 else 0)
    val redRiskPercent = (12 + redScore * 7).coerceIn(12, 78)
    val redLikely = redRiskPercent >= 50

    val redTriggers = mutableListOf<String>()
    if (marsCritical.isNotEmpty()) {
      redTriggers += "Mars présent en ${marsCritical.joinToString { "M$it" }}"
    }
    if (ruptureCritical.isNotEmpty()) {
      redTriggers += "figure de sanction/rupture en ${ruptureCritical.joinToString { "M$it" }}"
    }
    if (marsCount >= 3) {
      redTriggers += "Mars répété ×$marsCount"
    }
    val redReason =
      if (redTriggers.isEmpty()) {
        "Aucune signature martienne critique en M6/M8/M12/M15 : expulsion peu probable."
      } else {
        "${redTriggers.joinToString(" ; ").replaceFirstChar { it.uppercase() }} (tension=$totalTension)."
      }

    return ContestMatchPrecisionForecast(
      exactScoreCampA = scoreA,
      exactScoreCampB = scoreB,
      alternateScoreLabel = altScore,
      totalGoalsRange = totalGoalsRange,
      bothTeamsToScore = bothScore,
      bttsConfidencePercent = bttsConfidence,
      bttsReason = bttsReason,
      yellowCardsCampA = yellowA,
      yellowCardsCampB = yellowB,
      yellowCardsTotalRange = yellowTotalRange,
      cardIntensity = cardIntensity,
      redCardLikely = redLikely,
      redCardRiskPercent = redRiskPercent,
      redCardReason = redReason,
      scoreReasoning = scoreReasoning,
    )
  }

  /**
   * Calcule les indicateurs généraux d'« Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn » (Al-Baḥrī) :
   * 1) Validation / Sincérité du thème (Ṣidq al-Raml, pp. 89–90)
   * 2) Quatre figures élémentaires pures (Nār al-Nār, Hawāʾ al-Hawāʾ, Māʾ al-Māʾ, Turāb al-Turāb, pp. 77–78)
   * 3) Thème intérieur caché (Bāṭin al-Takht : M_i + gouverneur Taskīn, pp. 141–143)
   * 4) Nature de l'obstacle en M12 (p. 136) et cadence traditionnelle des délais (pp. 250–265)
   */
  @Reliability(ReliabilityLevel.D)
  fun computeAlFulkGeneralInsights(
    houses: List<ChartHouse>,
    domain: QuestionDomain = QuestionDomain.GENERAL,
  ): AlFulkGeneralInsights {
    require(houses.size == 16) { "Un Takht complet à 16 maisons est requis." }

    // 1) Ṣidq al-Raml wa Kadhibuh (pp. 89–90) : (M3 + M5) + (M11 + M15)
    val partA = AlToukhiFigures.combine(houses[2].figure, houses[4].figure)
    val partB = AlToukhiFigures.combine(houses[10].figure, houses[14].figure)
    val validationFig = AlToukhiFigures.combine(partA, partB)
    val appearsInHouses = houses.filter { it.figure.id == validationFig.id }.map { it.info.number }
    val isSincere = appearsInHouses.isNotEmpty()
    val m15EvenValid = houses[14].figure.totalDots % 2 == 0

    val mothers = houses.subList(0, 4).map { it.figure }
    val mothersUniformMotionWarning =
      when {
        mothers.all { it.motion == FigureMotion.FIXED } ->
          "Nukta (p. 90) : les 4 Mères sont toutes Fixes (Thawābit) — l’intention était figée ou hésitante ; à reconsidérer si le contexte est confus."
        mothers.all { it.motion == FigureMotion.EXITING } ->
          "Nukta (p. 90) : les 4 Mères sont toutes Sortantes (Khawārij) — l’esprit a fluctué pendant la frappe ; un thème complémentaire est conseillé."
        else -> null
      }

    val reliability =
      ChartReliabilityReport(
        validationFigure = validationFig,
        appearsInHouses = appearsInHouses,
        isSincere = isSincere,
        m15EvenParityValid = m15EvenValid,
        mothersUniformMotionWarning = mothersUniformMotionWarning,
        summaryLabel = if (isSincere) "Thème Sincère (Ṣādiq)" else "À confirmer (Qillat Ṣidq)",
        explanation =
          if (isSincere) {
            val locs = appearsInHouses.joinToString(", ") { "M$it" }
            "La figure-sceau ${validationFig.westAfricanName} (M3+M5 ⊕ M11+M15) réapparaît en $locs : le thème est jugé sincère et fiable selon Al-Fulk al-Mashḥūn (pp. 89–90)."
          } else {
            "La figure-sceau ${validationFig.westAfricanName} (M3+M5 ⊕ M11+M15) est absente des 16 maisons : sa sincérité est atténuée ; vérifier l'intention ou croiser avec le Bāṭin."
          },
      )

    // 2) Quatre figures élémentaires pures (pp. 77–78 & vitesse p. 134)
    val fireOfFire =
      AlToukhiFigures.findByRanks(
        houses[0].figure.fire,
        houses[4].figure.fire,
        houses[8].figure.fire,
        houses[12].figure.fire,
      )
    val airOfAir =
      AlToukhiFigures.findByRanks(
        houses[1].figure.air,
        houses[5].figure.air,
        houses[9].figure.air,
        houses[13].figure.air,
      )
    val waterOfWater =
      AlToukhiFigures.findByRanks(
        houses[2].figure.water,
        houses[6].figure.water,
        houses[10].figure.water,
        houses[14].figure.water,
      )
    val earthOfEarth =
      AlToukhiFigures.findByRanks(
        houses[3].figure.earth,
        houses[7].figure.earth,
        houses[11].figure.earth,
        houses[15].figure.earth,
      )
    val realizationSpeedNote =
      when {
        waterOfWater.activeRankCount >= 3 ->
          "Eau-d’Eau ouverte (${waterOfWater.westAfricanName}, ${waterOfWater.activeRankCount} pts actifs) : accomplissement fluide et rapide (p. 134)."
        waterOfWater.activeRankCount <= 1 ->
          "Eau-d’Eau resserrée (${waterOfWater.westAfricanName}) : mûrissement progressif demandant patience."
        else ->
          "Eau-d’Eau modérée (${waterOfWater.westAfricanName}) : rythme mesuré sans précipitation."
      }
    val pureElements =
      PureElementalQuadrant(
        fireOfFire = fireOfFire,
        airOfAir = airOfAir,
        waterOfWater = waterOfWater,
        earthOfEarth = earthOfEarth,
        realizationSpeedNote = realizationSpeedNote,
      )

    // 3) Bāṭin al-Takht (pp. 141–143) : M_i combinée au gouverneur naturel Taskīn(i)
    val allBatin =
      houses.map { ch ->
        val ruler = AlToukhiHouses.taskinRulerFor(ch.info.number)
        AlToukhiFigures.combine(ch.figure, ruler)
      }
    val keyHouseNumbers = listOf(1, domain.primaryHouse, 12, 15, 16).distinct().sorted()
    val batinHighlights =
      keyHouseNumbers.map { num ->
        val ch = houses[num - 1]
        BatinHighlight(
          houseNumber = num,
          houseLabel = "M$num · ${ch.info.englishName}",
          surfaceFigure = ch.figure,
          taskinRuler = AlToukhiHouses.taskinRulerFor(num),
          batinFigure = allBatin[num - 1],
        )
      }

    // 4) Diagnostic d'obstacle en M12 (p. 136)
    val m12 = houses[11].figure
    val m12Diagnostic =
      when (m12.dominantElement) {
        Element.FIRE ->
          "M12 ${m12.westAfricanName} (Feu) : vigilance, regard critique ou rivalité déclarée (Naẓar)."
        Element.AIR ->
          "M12 ${m12.westAfricanName} (Air) : blocage par rumeurs, discours, hésitations ou écrits (Nuṭq)."
        Element.WATER ->
          "M12 ${m12.westAfricanName} (Eau) : interférence affective, attachements ou accords cachés (Ittiṣāl)."
        Element.EARTH ->
          "M12 ${m12.westAfricanName} (Terre) : lenteur matérielle, distance ou séparation (Infiṣāl)."
      }

    // 5) Cadence traditionnelle des délais (pp. 250–265)
    val domainId = houses[domain.primaryHouse - 1].figure.id
    val domainOccurrences = houses.filter { it.figure.id == domainId }.map { it.info.number }
    val earliestGroupHouse = domainOccurrences.firstOrNull() ?: domain.primaryHouse
    val traditionalDurationHint =
      when (earliestGroupHouse) {
        in 1..4 -> "Échelle Mères (M1–M4) : échéance rapide en jours."
        in 5..8 -> "Échelle Filles (M5–M8) : échéance intermédiaire en semaines."
        in 9..12 -> "Échelle Nièces (M9–M12) : échéance étendue en mois."
        else -> "Échelle Témoins/Juge (M13–M16) : cycle long ou consolidation progressive (mois/années)."
      }

    // 6) Dynamique temporelle : Awtād (Présent), Succédentes (Futur), Cadentes (Passé)
    val presentHouses = listOf(0, 3, 6, 9).map { houses[it].figure }
    val futureHouses = listOf(1, 4, 7, 10).map { houses[it].figure }
    val pastHouses = listOf(2, 5, 8, 11).map { houses[it].figure }

    fun zoneDominantElement(figs: List<GeomanticFigure>): Element =
      figs.groupingBy { it.dominantElement }.eachCount().maxByOrNull { it.value }?.key ?: figs.first().dominantElement

    val presentFort = presentHouses.count { it.polarity == FortunePolarity.FORTUNATE }
    val presentUnfort = presentHouses.count { it.polarity == FortunePolarity.UNFORTUNATE }
    val futureFort = futureHouses.count { it.polarity == FortunePolarity.FORTUNATE }
    val futureUnfort = futureHouses.count { it.polarity == FortunePolarity.UNFORTUNATE }
    val pastFort = pastHouses.count { it.polarity == FortunePolarity.FORTUNATE }
    val pastUnfort = pastHouses.count { it.polarity == FortunePolarity.UNFORTUNATE }

    val presentScore = presentFort - presentUnfort
    val futureScore = futureFort - futureUnfort
    val pastScore = pastFort - pastUnfort
    val trajectorySummary =
      when {
        futureScore > presentScore ->
          "Élan ascendant : les Succédentes (Futur : $futureFort F / $futureUnfort D) améliorent les Awtād (Présent : $presentFort F / $presentUnfort D)."
        futureScore < presentScore ->
          "Tendance à consolider : le Présent ($presentFort F / $presentUnfort D) demande prudence face aux Succédentes ($futureFort F / $futureUnfort D)."
        else ->
          "Continuité temporelle : équilibre stable du Passé ($pastScore) vers le Présent ($presentScore) et le Futur ($futureScore)."
      }

    val temporalDynamics =
      TemporalDynamicsReport(
        presentFortunateCount = presentFort,
        presentUnfortunateCount = presentUnfort,
        presentDominantElement = zoneDominantElement(presentHouses),
        futureFortunateCount = futureFort,
        futureUnfortunateCount = futureUnfort,
        futureDominantElement = zoneDominantElement(futureHouses),
        pastFortunateCount = pastFort,
        pastUnfortunateCount = pastUnfort,
        pastDominantElement = zoneDominantElement(pastHouses),
        trajectorySummary = trajectorySummary,
      )

    // 7) Résonances relationnelles (Ḥāwī al-Akhbār pp. 112–113, 144, 148)
    val h1 = houses[0].figure
    val m1Rel = AlToukhiFigures.relationsFor(h1)
    val watchedHouses = listOf(domain.primaryHouse, 7, 10, 13, 14, 15, 16).distinct().filter { it != 1 }
    val relationalResonances = mutableListOf<RelationalResonanceSummary>()
    watchedHouses.forEach { houseNum ->
      val fig = houses[houseNum - 1].figure
      when (fig.id) {
        m1Rel.ittisal.id ->
          relationalResonances += RelationalResonanceSummary(
            relationType = "Ittiṣāl (Jonction)",
            targetHouseNumber = houseNum,
            targetFigure = fig,
            interpretation = "M$houseNum (${fig.westAfricanName}) est la figure d'Ittiṣāl de M1 : lien direct et convergence vers le sujet.",
          )
        m1Rel.nazar.id ->
          relationalResonances += RelationalResonanceSummary(
            relationType = "Naẓar (Regard)",
            targetHouseNumber = houseNum,
            targetFigure = fig,
            interpretation = "M$houseNum (${fig.westAfricanName}) répond en Naẓar (rang Feu) à M1 : attention active et mise en lumière.",
          )
        m1Rel.nutq.id ->
          relationalResonances += RelationalResonanceSummary(
            relationType = "Nuṭq (Parole)",
            targetHouseNumber = houseNum,
            targetFigure = fig,
            interpretation = "M$houseNum (${fig.westAfricanName}) porte le Nuṭq (rang Air) de M1 : nouvelles, contrat ou dialogue décisif.",
          )
        m1Rel.infisal.id ->
          relationalResonances += RelationalResonanceSummary(
            relationType = "Infiṣāl (Séparation)",
            targetHouseNumber = houseNum,
            targetFigure = fig,
            interpretation = "M$houseNum (${fig.westAfricanName}) correspond à l'Infiṣāl (Terre) de M1 : prise de distance ou clôture d'étape.",
          )
        m1Rel.opposite.id ->
          relationalResonances += RelationalResonanceSummary(
            relationType = "Al-Ḍidd (Opposé)",
            targetHouseNumber = houseNum,
            targetFigure = fig,
            interpretation = "M$houseNum (${fig.westAfricanName}) inverse les 4 rangs de M1 : polarité opposée à concilier.",
          )
        m1Rel.verticalMirror.id ->
          relationalResonances += RelationalResonanceSummary(
            relationType = "Aṣḥāb (Miroir)",
            targetHouseNumber = houseNum,
            targetFigure = fig,
            interpretation = "M$houseNum (${fig.westAfricanName}) forme le miroir vertical de M1 : résonance complémentaire.",
          )
      }
    }
    if (relationalResonances.isEmpty()) {
      relationalResonances += RelationalResonanceSummary(
        relationType = "Mīzān Ittiṣāl de M1",
        targetHouseNumber = 1,
        targetFigure = m1Rel.ittisal,
        interpretation = "Pour joindre l'intention de M1 (${h1.westAfricanName}), observer l'arrivée de ${m1Rel.ittisal.westAfricanName} (Ittiṣāl) ou ${m1Rel.nazar.westAfricanName} (Naẓar).",
      )
    }

    // 8) Bilan des dignités planétaires (Al-Fulk pp. 67–68)
    val exaltedHouses = mutableListOf<String>()
    val ownHouses = mutableListOf<String>()
    val fallenHouses = mutableListOf<String>()
    houses.forEach { ch ->
      when (AlToukhiHouses.dignityOf(ch.figure, ch.info.number)) {
        PlanetaryDignity.OWN_AND_EXALTATION -> exaltedHouses += "M${ch.info.number}(${ch.figure.westAfricanName}★)"
        PlanetaryDignity.EXALTATION -> exaltedHouses += "M${ch.info.number}(${ch.figure.westAfricanName})"
        PlanetaryDignity.OWN_HOUSE -> ownHouses += "M${ch.info.number}(${ch.figure.westAfricanName})"
        PlanetaryDignity.FALL -> fallenHouses += "M${ch.info.number}(${ch.figure.westAfricanName})"
        PlanetaryDignity.NEUTRAL -> Unit
      }
    }
    val dignityParts = mutableListOf<String>()
    if (exaltedHouses.isNotEmpty()) dignityParts += "Sharaf (Exaltation) : ${exaltedHouses.joinToString()}"
    if (ownHouses.isNotEmpty()) dignityParts += "Domicile Taskīn : ${ownHouses.joinToString()}"
    if (fallenHouses.isNotEmpty()) dignityParts += "Hubūṭ (Chute) : ${fallenHouses.joinToString()}"
    val dignitySummaryNote =
      if (dignityParts.isEmpty()) {
        "Dignités planétaires (pp. 67–68) : aucune figure en Sharaf ou Hubūṭ extrême — équilibre tempéré."
      } else {
        dignityParts.joinToString(" · ")
      }

    return AlFulkGeneralInsights(
      reliability = reliability,
      pureElements = pureElements,
      batinHighlights = batinHighlights,
      allBatinFigures = allBatin,
      m12ObstacleDiagnostic = m12Diagnostic,
      traditionalDurationHint = traditionalDurationHint,
      temporalDynamics = temporalDynamics,
      relationalResonances = relationalResonances.take(4),
      dignitySummaryNote = dignitySummaryNote,
    )
  }

  /**
   * Calcule les critères de duel d'« Al-Fulk al-Mashḥūn » (Al-Baḥrī, pp. 152–158, 164, 316) :
   * - Règle poétique du modulo 9 (isqāṭ 9-9) des points ouverts Mères vs Filles (p. 155)
   * - Bataille des maisons Impaires vs Paires (Iltiqāʾ al-Jayshayn, p. 156)
   * - Sous-Takht de vérification sur les 4 Awtād (Taḥlīf al-Khaṭṭ, p. 155)
   * - Modulo 4 des points actifs totaux (p. 164) & synthèse Feu croisée (p. 164 / 316)
   */
  fun computeAlFulkDuelInsights(houses: List<ChartHouse>): AlFulkDuelInsights {
    require(houses.size == 16) { "Un Takht complet à 16 maisons est requis." }
    val activeNotes = mutableListOf<String>()

    // 1) Isqāṭ 9-9 des points ouverts : Camp Mères (1,2,3,4,9,10,13,15) vs Filles (5,6,7,8,11,12,14,16)
    val mothersIndices = listOf(0, 1, 2, 3, 8, 9, 12, 14)
    val daughtersIndices = listOf(4, 5, 6, 7, 10, 11, 13, 15)
    val openA = mothersIndices.sumOf { houses[it].figure.activeRankCount }
    val openB = daughtersIndices.sumOf { houses[it].figure.activeRankCount }
    fun reduceMod9(value: Int): Int = if (value <= 0) 9 else ((value - 1) % 9) + 1
    val remA = reduceMod9(openA)
    val remB = reduceMod9(openB)

    val (mod9Verdict, mod9RuleDesc) =
      when {
        remA == remB && remA % 2 == 1 ->
          MatchOutcome.CAMP_A_WIN to "Égalité impaire ($remA = $remB) : le demandeur (Camp A / Ṭālib) l’emporte"
        remA == remB && remA % 2 == 0 ->
          MatchOutcome.CAMP_B_WIN to "Égalité paire ($remA = $remB) : l’adversaire (Camp B / Maṭlūb) l’emporte"
        (remA % 2) == (remB % 2) ->
          if (remA < remB) {
            MatchOutcome.CAMP_A_WIN to "Parité identique ($remA vs $remB) : le plus petit ($remA) domine Camp A"
          } else {
            MatchOutcome.CAMP_B_WIN to "Parité identique ($remA vs $remB) : le plus petit ($remB) domine Camp B"
          }
        else ->
          if (remA > remB) {
            MatchOutcome.CAMP_A_WIN to "Parités croisées ($remA vs $remB) : le plus grand ($remA) donne Camp A"
          } else {
            MatchOutcome.CAMP_B_WIN to "Parités croisées ($remA vs $remB) : le plus grand ($remB) donne Camp B"
          }
      }
    activeNotes += "Taḥlīf Mod-9 ($openA→$remA vs $openB→$remB) : $mod9RuleDesc"

    // 2) Bataille Maisons Impaires (1,3,5,7,9,11,13,15) vs Paires (2,4,6,8,10,12,14,16) (p. 156)
    val oddHouses = listOf(0, 2, 4, 6, 8, 10, 12, 14).map { houses[it].figure }
    val evenHouses = listOf(1, 3, 5, 7, 9, 11, 13, 15).map { houses[it].figure }
    val oddFortunate = oddHouses.count { it.polarity == FortunePolarity.FORTUNATE }
    val evenFortunate = evenHouses.count { it.polarity == FortunePolarity.FORTUNATE }
    val oddMaleficExit = oddHouses.count { it.polarity == FortunePolarity.UNFORTUNATE && it.motion == FigureMotion.EXITING }
    val evenMaleficExit = evenHouses.count { it.polarity == FortunePolarity.UNFORTUNATE && it.motion == FigureMotion.EXITING }
    val oddBalance = oddFortunate - oddMaleficExit
    val evenBalance = evenFortunate - evenMaleficExit
    val oddVsEvenVerdict =
      when {
        oddBalance > evenBalance -> MatchOutcome.CAMP_A_WIN
        evenBalance > oddBalance -> MatchOutcome.CAMP_B_WIN
        else -> MatchOutcome.DRAW
      }
    activeNotes += "Iltiqāʾ al-Jayshayn (Impaires vs Paires) : $oddFortunate Suʿūd (bal. $oddBalance) vs $evenFortunate Suʿūd (bal. $evenBalance)"

    // 3) Sous-Takht des 4 Awtād (M1, M4, M7, M10) (pp. 131, 155)
    val s1 = houses[0].figure
    val s2 = houses[3].figure
    val s3 = houses[6].figure
    val s4 = houses[9].figure
    val s5 = AlToukhiFigures.findByRanks(s1.fire, s2.fire, s3.fire, s4.fire)
    val s6 = AlToukhiFigures.findByRanks(s1.air, s2.air, s3.air, s4.air)
    val s7 = AlToukhiFigures.findByRanks(s1.water, s2.water, s3.water, s4.water)
    val s8 = AlToukhiFigures.findByRanks(s1.earth, s2.earth, s3.earth, s4.earth)
    val s9 = AlToukhiFigures.combine(s1, s2)
    val s10 = AlToukhiFigures.combine(s3, s4)
    val s11 = AlToukhiFigures.combine(s5, s6)
    val s12 = AlToukhiFigures.combine(s7, s8)
    val s13 = AlToukhiFigures.combine(s9, s10)
    val s14 = AlToukhiFigures.combine(s11, s12)
    val s15 = AlToukhiFigures.combine(s13, s14)
    val s16 = AlToukhiFigures.combine(s15, s1)

    fun bahriHierarchyScore(fig: GeomanticFigure): Int =
      when (fig.polarity) {
        FortunePolarity.FORTUNATE ->
          when (fig.motion) {
            FigureMotion.ENTERING -> 4
            FigureMotion.FIXED -> 3
            FigureMotion.EXITING -> 2
            FigureMotion.MOBILE -> 1
          }
        FortunePolarity.MIXED -> 0
        FortunePolarity.UNFORTUNATE ->
          when (fig.motion) {
            FigureMotion.EXITING -> -1
            FigureMotion.MOBILE -> -2
            FigureMotion.ENTERING -> -3
            FigureMotion.FIXED -> -4
          }
      }

    val subScoreA = bahriHierarchyScore(s1) + bahriHierarchyScore(s10) + bahriHierarchyScore(s13)
    val subScoreB = bahriHierarchyScore(s7) + bahriHierarchyScore(s4) + bahriHierarchyScore(s14)
    val awtadVerdict =
      when {
        s15.id == 4 || s16.id == 4 || kotlin.math.abs(subScoreA - subScoreB) <= 1 -> MatchOutcome.DRAW
        subScoreA > subScoreB -> MatchOutcome.CAMP_A_WIN
        else -> MatchOutcome.CAMP_B_WIN
      }
    activeNotes += "Sous-Takht Awtād (M1,M4,M7,M10) : Juge' ${s15.westAfricanName} / Issue' ${s16.westAfricanName} → ${awtadVerdict.shortLabel}"

    // 4) Mod-4 total des points ouverts (p. 164) & composite Feu croisé (p. 164 / 316)
    val totalOdd = houses.sumOf { it.figure.activeRankCount }
    val mod4 = if (totalOdd <= 0) 4 else ((totalOdd - 1) % 4) + 1
    val mod4Verdict =
      when (mod4) {
        1 -> MatchOutcome.CAMP_A_WIN
        2 -> MatchOutcome.CAMP_B_WIN
        else -> MatchOutcome.DRAW
      }
    val campAFire =
      AlToukhiFigures.findByRanks(
        houses[0].figure.fire,
        houses[1].figure.fire,
        houses[8].figure.fire,
        houses[12].figure.fire,
      )
    val campBFire =
      AlToukhiFigures.findByRanks(
        houses[4].figure.fire,
        houses[5].figure.fire,
        houses[10].figure.fire,
        houses[13].figure.fire,
      )
    activeNotes += "Feu croisé : Camp A ${campAFire.westAfricanName} vs Camp B ${campBFire.westAfricanName} · Mod-4=$mod4 (${mod4Verdict.shortLabel})"

    val verdicts = listOf(mod9Verdict, oddVsEvenVerdict, awtadVerdict, mod4Verdict)
    val countA = verdicts.count { it == MatchOutcome.CAMP_A_WIN }
    val countB = verdicts.count { it == MatchOutcome.CAMP_B_WIN }
    val countDraw = verdicts.count { it == MatchOutcome.DRAW }
    val consensusVerdict =
      when {
        countA >= 3 -> MatchOutcome.CAMP_A_WIN
        countB >= 3 -> MatchOutcome.CAMP_B_WIN
        countDraw >= 3 -> MatchOutcome.DRAW
        else -> null
      }

    // 5) Duel des Awtād Croisés (Al-Fulk al-Mashḥūn p. 316) & Dignités Sharaf/Hubūṭ
    val crossedAwtadA =
      AlToukhiFigures.findByRanks(
        houses[0].figure.fire,
        houses[3].figure.air,
        houses[6].figure.water,
        houses[9].figure.earth,
      )
    val crossedAwtadB =
      AlToukhiFigures.findByRanks(
        houses[9].figure.fire,
        houses[6].figure.air,
        houses[3].figure.water,
        houses[0].figure.earth,
      )
    val crossedCombined = AlToukhiFigures.combine(crossedAwtadA, crossedAwtadB)
    val crossScoreA = AlToukhiFigures.hawiStrengthTier(crossedAwtadA).rankWeight * 2 + crossedAwtadA.activeRankCount
    val crossScoreB = AlToukhiFigures.hawiStrengthTier(crossedAwtadB).rankWeight * 2 + crossedAwtadB.activeRankCount
    val crossedAwtadVerdict =
      when {
        crossedAwtadA.id == crossedAwtadB.id || crossedCombined.id == 4 -> MatchOutcome.DRAW
        crossScoreA > crossScoreB -> MatchOutcome.CAMP_A_WIN
        crossScoreB > crossScoreA -> MatchOutcome.CAMP_B_WIN
        else -> MatchOutcome.DRAW
      }
    activeNotes += "Awtād croisés (p. 316) : ${crossedAwtadA.westAfricanName} vs ${crossedAwtadB.westAfricanName} → ${crossedAwtadVerdict.shortLabel}"

    val campAHousesDignity = listOf(1, 2, 10, 13).sumOf { hNum ->
      when (AlToukhiHouses.dignityOf(houses[hNum - 1].figure, hNum)) {
        PlanetaryDignity.OWN_AND_EXALTATION -> 2
        PlanetaryDignity.EXALTATION, PlanetaryDignity.OWN_HOUSE -> 1
        PlanetaryDignity.FALL -> -1
        PlanetaryDignity.NEUTRAL -> 0
      }
    }
    val campBHousesDignity = listOf(4, 7, 8, 14).sumOf { hNum ->
      when (AlToukhiHouses.dignityOf(houses[hNum - 1].figure, hNum)) {
        PlanetaryDignity.OWN_AND_EXALTATION -> 2
        PlanetaryDignity.EXALTATION, PlanetaryDignity.OWN_HOUSE -> 1
        PlanetaryDignity.FALL -> -1
        PlanetaryDignity.NEUTRAL -> 0
      }
    }
    val dignityBalanceNote = "Dignités Sharaf/Hubūṭ : Camp A ($campAHousesDignity) vs Camp B ($campBHousesDignity)"

    return AlFulkDuelInsights(
      campAMod9Remainder = remA,
      campBMod9Remainder = remB,
      mod9Verdict = mod9Verdict,
      mod9RuleNote = mod9RuleDesc,
      oddHousesFortunateCount = oddFortunate,
      evenHousesFortunateCount = evenFortunate,
      oddVsEvenVerdict = oddVsEvenVerdict,
      awtadSubJudge = s15,
      awtadSubOutcome = s16,
      awtadVerdict = awtadVerdict,
      mod4TotalOddPoints = totalOdd,
      mod4Verdict = mod4Verdict,
      campAFireComposite = campAFire,
      campBFireComposite = campBFire,
      consensusVerdict = consensusVerdict,
      convergenceLabel =
        consensusVerdict?.let { "Consensus Al-Fulk : ${it.shortLabel}" }
          ?: "Indices partagés (Awtād : ${awtadVerdict.shortLabel})",
      doctrinalDeltaA = 0.0,
      doctrinalDeltaB = 0.0,
      activeNotes = activeNotes,
      crossedAwtadA = crossedAwtadA,
      crossedAwtadB = crossedAwtadB,
      crossedAwtadVerdict = crossedAwtadVerdict,
      dignityBalanceNote = dignityBalanceNote,
    )
  }

  /**
   * Calcule les indicateurs de duel issus de « Ḥāwī al-Akhbār wa Kanz al-Asrār » (Shaykh Ḥāzim al-Maqdisī,
   * pp. 9, 114–115, 155) :
   * - Confrontation des paires fondatrices : Camp A (M1 ⊕ M2) vs Camp B (M7 ⊕ M8)
   * - Hiérarchie quaternaire (Fortes/Mixtes Saʿīd vs Naḥs)
   * - Présence de figures Sulṭānī (11, 2, 9, 1) ou d'Armistice Jamāʿa (4).
   */
  fun computeHawiAlAkhbarDuelInsights(houses: List<ChartHouse>): HawiAlAkhbarDuelInsights {
    require(houses.size == 16) { "Un Takht complet à 16 maisons est requis." }
    val h1 = houses[0].figure
    val h2 = houses[1].figure
    val h4 = houses[3].figure
    val h7 = houses[6].figure
    val h8 = houses[7].figure
    val h10 = houses[9].figure
    val h13 = houses[12].figure
    val h14 = houses[13].figure
    val h15 = houses[14].figure

    val pairA = AlToukhiFigures.combine(h1, h2)
    val pairB = AlToukhiFigures.combine(h7, h8)

    val pairScoreA =
      AlToukhiFigures.hawiStrengthTier(h1).rankWeight +
        AlToukhiFigures.hawiStrengthTier(h2).rankWeight +
        AlToukhiFigures.hawiStrengthTier(pairA).rankWeight
    val pairScoreB =
      AlToukhiFigures.hawiStrengthTier(h7).rankWeight +
        AlToukhiFigures.hawiStrengthTier(h8).rankWeight +
        AlToukhiFigures.hawiStrengthTier(pairB).rankWeight

    val pairVerdict =
      when {
        pairScoreA - pairScoreB >= 2 -> MatchOutcome.CAMP_A_WIN
        pairScoreB - pairScoreA >= 2 -> MatchOutcome.CAMP_B_WIN
        else -> MatchOutcome.DRAW
      }

    val sultaniIds = setOf(11, 2, 9, 1)
    val sultaniInA = listOf(h1, h10, h13, h15).count { it.id in sultaniIds }
    val sultaniInB = listOf(h4, h7, h8, h14).count { it.id in sultaniIds }
    val easternSultaniAdvantage = sultaniInA - sultaniInB

    val truceJamaa = h15.id == 4 || (h1.id == 4 && h7.id == 4)

    fun houseDignityScore(houseNum: Int): Int =
      when (AlToukhiHouses.dignityOf(houses[houseNum - 1].figure, houseNum)) {
        PlanetaryDignity.OWN_AND_EXALTATION -> 2
        PlanetaryDignity.EXALTATION, PlanetaryDignity.OWN_HOUSE -> 1
        PlanetaryDignity.FALL -> -1
        PlanetaryDignity.NEUTRAL -> 0
      }
    val dignityA = listOf(1, 2, 10, 13).sumOf(::houseDignityScore)
    val dignityB = listOf(4, 7, 8, 14).sumOf(::houseDignityScore)

    val totalHawiBalance = (pairScoreA - pairScoreB) + easternSultaniAdvantage + (dignityA - dignityB)
    val hawiVerdict =
      when {
        truceJamaa && kotlin.math.abs(totalHawiBalance) <= 2 -> MatchOutcome.DRAW
        totalHawiBalance >= 2 -> MatchOutcome.CAMP_A_WIN
        totalHawiBalance <= -2 -> MatchOutcome.CAMP_B_WIN
        else -> MatchOutcome.DRAW
      }

    val summaryNote =
      "Paires M1+M2 (${pairA.westAfricanName}) vs M7+M8 (${pairB.westAfricanName}) → ${hawiVerdict.shortLabel}" +
        if (truceJamaa) " · Jamāʿa en trêve" else ""

    return HawiAlAkhbarDuelInsights(
      campAPairComposite = pairA,
      campBPairComposite = pairB,
      pairBalanceVerdict = pairVerdict,
      easternSultaniAdvantage = easternSultaniAdvantage,
      truceJamaaPresent = truceJamaa,
      dignityBalanceA = dignityA,
      dignityBalanceB = dignityB,
      hawiVerdict = hawiVerdict,
      summaryNote = summaryNote,
    )
  }

  private fun finalizeAlFulkConvergence(
    base: AlFulkDuelInsights,
    primaryOutcome: MatchOutcome,
    deltaA: Double,
    deltaB: Double,
  ): AlFulkDuelInsights {
    val label =
      when {
        base.consensusVerdict == primaryOutcome ->
          "Convergence Al-Fulk ✓ (${primaryOutcome.shortLabel})"
        base.consensusVerdict != null && base.consensusVerdict != primaryOutcome ->
          "Alerte divergence Al-Baḥrī : ≥3/4 critères pointent ${base.consensusVerdict.shortLabel}"
        base.awtadVerdict == MatchOutcome.DRAW && primaryOutcome != MatchOutcome.DRAW ->
          "Prudence Awtād : le sous-Takht (M1,M4,M7,M10) penche vers Match Nul"
        base.awtadVerdict == primaryOutcome ->
          "Confirmation du sous-Takht Awtād ✓ (${primaryOutcome.shortLabel})"
        else ->
          "Lecture croisée nuancée (Awtād : ${base.awtadVerdict.shortLabel} · Mod-9 : ${base.mod9Verdict.shortLabel})"
      }
    return base.copy(
      convergenceLabel = label,
      doctrinalDeltaA = deltaA,
      doctrinalDeltaB = deltaB,
    )
  }

  /**
   * Calcule les indicateurs doctrinaux d'« Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml » (Shaykh Muḥammad Sās)
   * et des Manuscrits de La Mecque (MS 2380 / MS 2405) :
   * 1) Seuil géométrique des 96 points (Raml Kāmil ≥ 96 vs Raml Nāqiṣ < 96, pp. 35–36, 50)
   * 2) Balance Vie (Air+Eau) vs Fermeture (Feu+Terre) sur les 16 maisons (p. 50)
   * 3) Les 5 Triangles du Takht (Muthallathāt al-Buyūt, pp. 20, 28, 52) & T2 (Dalīl) vs T5 (ʿĀqiba)
   * 4) Aspects astrologiques entre maisons répétées (Ittiṣālāt 1..12, p. 28) & Amitiés/Inimitiés planétaires
   * 5) Échelle temporelle d'Al-Ṭarābulusī (MS 2380 p. 29 / MS 2405 p. 24).
   */
  @Reliability(ReliabilityLevel.D)
  fun computeAlQawlGeneralInsights(
    houses: List<ChartHouse>,
    domain: QuestionDomain = QuestionDomain.GENERAL,
  ): AlQawlAlJamiGeneralInsights {
    require(houses.size == 16) { "Un Takht complet à 16 maisons est requis." }

    val totalChartDots = houses.sumOf { it.figure.totalDots }
    val isComplete96 = totalChartDots >= 96

    val airWaterOpen =
      houses.sumOf { ch ->
        (if (ch.figure.air == PointParity.ODD) 1 else 0) +
          (if (ch.figure.water == PointParity.ODD) 1 else 0)
      }
    val fireEarthOpen =
      houses.sumOf { ch ->
        (if (ch.figure.fire == PointParity.ODD) 1 else 0) +
          (if (ch.figure.earth == PointParity.ODD) 1 else 0)
      }

    val status96 =
      if (isComplete96) {
        "Raml Kāmil ($totalChartDots/128 pts ≥ 96 : thème complet, accomplissement facilité)"
      } else {
        "Raml Nāqiṣ ($totalChartDots/128 pts < 96 : thème retenu, patience ou effort supplémentaire)"
      }
    val vitalityStatus =
      when {
        airWaterOpen > fireEarthOpen ->
          "souffle de Vie (Air+Eau = $airWaterOpen) supérieur à la Fermeture (Feu+Terre = $fireEarthOpen) : ouverture favorable"
        fireEarthOpen > airWaterOpen ->
          "Fermeture (Feu+Terre = $fireEarthOpen) supérieure à la Vie (Air+Eau = $airWaterOpen) : résistance à lever"
        else ->
          "équilibre exact Vie / Fermeture ($airWaterOpen vs $fireEarthOpen)"
      }
    val vitalityBalanceNote = "$status96 · Balance p. 50 : $vitalityStatus."

    val fiveTriangles =
      listOf(
        TriangleSummary(1, "T1 · Âme & Ressources", "M1 + M2 → M9", 9, houses[8].figure),
        TriangleSummary(2, "T2 · Dalīl al-Ḥawāʾij", "M3 + M4 → M10", 10, houses[9].figure),
        TriangleSummary(3, "T3 · Élan & Espoirs", "M5 + M6 → M11", 11, houses[10].figure),
        TriangleSummary(4, "T4 · Épreuves & Adversaire", "M7 + M8 → M12", 12, houses[11].figure),
        TriangleSummary(5, "T5 · ʿĀqiba (Sentence)", "M13 + M14 → M15", 15, houses[14].figure),
      )

    val dalilFig = fiveTriangles[1].apexFigure // T2 -> M10
    val aqibaFig = fiveTriangles[4].apexFigure // T5 -> M15
    val fortunateTriangles = fiveTriangles.count { it.apexFigure.polarity == FortunePolarity.FORTUNATE }
    val trianglesVerdictNote =
      "Triangles fastes : $fortunateTriangles/5 · " +
        "Dalīl T2 (M10 ${dalilFig.westAfricanName} — ${dalilFig.polarity.label}) vs " +
        "ʿĀqiba T5 (M15 ${aqibaFig.westAfricanName} — ${aqibaFig.polarity.label})."

    val h1 = houses[0].figure
    val targetHouseNum = if (domain.primaryHouse != 1) domain.primaryHouse else 15
    val targetFig = houses[targetHouseNum - 1].figure
    val judge = houses[14].figure

    val pCompatTarget = AlToukhiHouses.planetaryCompatibility(h1.planet, targetFig.planet)
    val eCompatTarget = AlToukhiHouses.elementalCompatibility(h1.dominantElement, targetFig.dominantElement)
    val pCompatJudge = AlToukhiHouses.planetaryCompatibility(h1.planet, judge.planet)
    val planetarySympathyNote =
      "M1 (${h1.westAfricanName}) ↔ M$targetHouseNum (${targetFig.westAfricanName}) : $pCompatTarget ; $eCompatTarget · " +
        "M1 ↔ Juge M15 : $pCompatJudge."

    val importantHouses = setOf(1, 7, 10, domain.primaryHouse)
    val aspectLinks = mutableListOf<HouseAspectLink>()
    for (a in 1..11) {
      for (b in (a + 1)..12) {
        val figA = houses[a - 1].figure
        val figB = houses[b - 1].figure
        if (figA.id == figB.id) {
          val aspect = AlToukhiHouses.aspectBetweenHouses(a, b)
          if (aspect != null) {
            aspectLinks +=
              HouseAspectLink(
                figure = figA,
                firstHouse = a,
                secondHouse = b,
                aspectType = aspect,
                interpretation =
                  "Répétition de ${figA.westAfricanName} entre M$a et M$b : ${aspect.label} (${aspect.arabicLabel}).",
              )
          }
        }
      }
    }
    val sortedAspects =
      aspectLinks
        .sortedByDescending { link ->
          val prio = if (link.firstHouse in importantHouses || link.secondHouse in importantHouses) 10 else 0
          val harm = if (link.aspectType == AspectRelationType.OPPOSITION || link.aspectType == AspectRelationType.TRINE) 2 else 1
          prio + harm
        }
        .take(5)

    val domainFigId = houses[domain.primaryHouse - 1].figure.id
    val appearances = houses.filter { it.figure.id == domainFigId }.map { it.info.number }
    val primaryDuration = AlToukhiHouses.tarabulusiDurationForHouse(domain.primaryHouse)
    val firstRepeat = appearances.firstOrNull { it != domain.primaryHouse }
    val tarabulusiTimingHint =
      if (firstRepeat != null) {
        "Échelle Al-Ṭarābulusī (MS 2380/2405) : M${domain.primaryHouse} ($primaryDuration) résonne avec M$firstRepeat (${AlToukhiHouses.tarabulusiDurationForHouse(firstRepeat)})."
      } else {
        "Échelle Al-Ṭarābulusī (MS 2380/2405) : cadence de base de M${domain.primaryHouse} ≈ $primaryDuration."
      }

    return AlQawlAlJamiGeneralInsights(
      totalChartDots = totalChartDots,
      isComplete96 = isComplete96,
      airWaterOpenPoints = airWaterOpen,
      fireEarthOpenPoints = fireEarthOpen,
      vitalityBalanceNote = vitalityBalanceNote,
      fiveTriangles = fiveTriangles,
      trianglesVerdictNote = trianglesVerdictNote,
      planetarySympathyNote = planetarySympathyNote,
      aspectLinks = sortedAspects,
      tarabulusiTimingHint = tarabulusiTimingHint,
    )
  }

  /**
   * Calcule le contrôle duel d'« Al-Qawl al-Jāmiʿ » et des Manuscrits de La Mecque (MS 2380 p. 40, 69 ; MS 2405 p. 34–35) :
   * - Points ouverts Mères+Droit (M1..M4 + M13) vs Filles+Gauche (M5..M8 + M14)
   * - Règle d'Al-Zanātī : M10 gouverne M1 vs M7 (M1 ⊕ M10 vs M7 ⊕ M4) + aspects M1↔M10 vs M7↔M10.
   */
  fun computeAlQawlDuelInsights(houses: List<ChartHouse>): AlQawlDuelInsights {
    require(houses.size == 16) { "Un Takht complet à 16 maisons est requis." }

    val mothersOpenPoints = listOf(0, 1, 2, 3, 12).sumOf { houses[it].figure.activeRankCount }
    val daughtersOpenPoints = listOf(4, 5, 6, 7, 13).sumOf { houses[it].figure.activeRankCount }

    val h1 = houses[0].figure
    val h4 = houses[3].figure
    val h7 = houses[6].figure
    val h10 = houses[9].figure
    val judge = houses[14].figure

    val m1M10Composite = AlToukhiFigures.combine(h1, h10)
    val m7M4Composite = AlToukhiFigures.combine(h7, h4)

    var scoreA = 0
    var scoreB = 0
    if (mothersOpenPoints > daughtersOpenPoints) scoreA += 1 else if (daughtersOpenPoints > mothersOpenPoints) scoreB += 1

    val tierA = AlToukhiFigures.hawiStrengthTier(m1M10Composite).rankWeight
    val tierB = AlToukhiFigures.hawiStrengthTier(m7M4Composite).rankWeight
    if (tierA > tierB) scoreA += 2 else if (tierB > tierA) scoreB += 2

    if (m1M10Composite.id == judge.id) scoreA += 1
    if (m7M4Composite.id == judge.id) scoreB += 1

    val sameM1M10 = houses.filter { it.info.number in 1..12 && it.figure.id == h1.id }.map { it.info.number }
    val sameM7M10 = houses.filter { it.info.number in 1..12 && it.figure.id == h7.id }.map { it.info.number }
    var aspectScoreA = if (10 in sameM1M10) 2 else 0
    var aspectScoreB = if (10 in sameM7M10) 2 else 0

    sameM1M10.forEach { hNum ->
      val asp = AlToukhiHouses.aspectBetweenHouses(1, hNum)
      if (asp != null) aspectScoreA += if (asp.isHarmonious) 1 else -1
    }
    sameM7M10.forEach { hNum ->
      val asp = AlToukhiHouses.aspectBetweenHouses(7, hNum)
      if (asp != null) aspectScoreB += if (asp.isHarmonious) 1 else -1
    }

    val aspectAdvantage =
      when {
        aspectScoreA > aspectScoreB -> MatchOutcome.CAMP_A_WIN
        aspectScoreB > aspectScoreA -> MatchOutcome.CAMP_B_WIN
        else -> MatchOutcome.DRAW
      }

    scoreA += aspectScoreA
    scoreB += aspectScoreB

    val alQawlVerdict =
      when {
        m1M10Composite.id == m7M4Composite.id || kotlin.math.abs(scoreA - scoreB) <= 1 -> MatchOutcome.DRAW
        scoreA > scoreB -> MatchOutcome.CAMP_A_WIN
        else -> MatchOutcome.CAMP_B_WIN
      }

    val summaryNote =
      "Zanātī M1⊕M10 (${m1M10Composite.westAfricanName}) vs M7⊕M4 (${m7M4Composite.westAfricanName}) · " +
        "Ouverts $mothersOpenPoints vs $daughtersOpenPoints → ${alQawlVerdict.shortLabel}"

    return AlQawlDuelInsights(
      mothersOpenPoints = mothersOpenPoints,
      daughtersOpenPoints = daughtersOpenPoints,
      m1M10Composite = m1M10Composite,
      m7M4Composite = m7M4Composite,
      aspectAdvantage = aspectAdvantage,
      alQawlVerdict = alQawlVerdict,
      summaryNote = summaryNote,
    )
  }
}
