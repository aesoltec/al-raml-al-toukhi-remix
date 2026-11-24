package com.example.geomancy

/**
 * Modules 2.1 à 2.4 (Leçons 1 à 40) du traité « Manbaʿ Uṣūl al-Raml » (منبع أصول الرمل —
 * الدرة البهية في العلوم الرملية) de Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī (80 leçons).
 */
internal val toukhiManbaModulesPart1: List<CourseModule> =
  listOf(
    CourseModule(
      id = "m_manba_1_origines_point",
      moduleNumber = "Module 2.1",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 1 à 10 · Origines & Point primordial",
      title = "Genèse du Point (Nuqṭa), Impair/Pair & 16 Figures",
      summary =
        "Dans les Leçons 1 à 10 de « Manbaʿ Uṣūl al-Raml », Cheikh al-Ṭūkhī remonte à la genèse même du sable divinatoire : toute science du Raml naît d'un point unique (Nuqṭa) tracé dans la pureté, dédoublé en Fard (1) et Zawj (2), pour engendrer les 16 figures réparties par nature, mouvement et polarité.",
      classicalRules =
        listOf(
          "Leçons 1–2 (Le Point & l'Intention) : tracer dans un état de recueillement (ṭahāra), face à la Qibla, après avoir formulé clairement la Niyya sans aucune distraction.",
          "Leçons 3–4 (Fard & Zawj) : l'impair (1 point, Wujūd) incarne le mouvement, la lumière et l'impulsion ; le pair (2 points, ʿAdam) incarne la forme, le repos et la matière.",
          "Leçons 5–7 (Les Quatre Rangs) : chaque figure superpose de haut en bas le Feu (Tête), l'Air (Poitrine), l'Eau (Hanches) et la Terre (Pieds), formant 2⁴ = 16 matrices universelles.",
          "Leçons 8–10 (Taxinomie primordiale) : distinguer dès l'abord les figures Entrantes (Dākhila), Sortantes (Khārija), Fixes (Thābita) et Mobiles (Munqaliba), ainsi que Fastes (Saʿd), Néfastes (Naḥs) et Mixtes (Mumtazija).",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(16, "Taliki (Taliki (Al-Ṭarīq) · 1111) — Quatre rangs impairs (le point pur en action sur les 4 éléments)"),
          CourseFigureFocus(4, "Moussa (Moussa (Al-Jamāʿa) · 2222) — Quatre rangs pairs (l'équilibre réceptif et collectif)"),
          CourseFigureFocus(1, "Adama (Adama (Al-Laḥyān) · 1222) — Feu actif sur trois rangs pairs : noblesse d'ouverture"),
          CourseFigureFocus(12, "Malidjou (Malidjou (Al-ʿAtaba al-Dākhila) · 2111) — Porte d'entrée vers les débuts bénis"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 8–10 : De l'Unité du Point aux 16 Maisons complètes",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 1–10",
          domain = QuestionDomain.GENERAL,
          planetHour = Planet.JUPITER,
          question = "Observation de la naissance des Filles, Témoins et Juge depuis 4 Mères fondatrices",
          motherDotCounts = listOf(5, 7, 5, 7, 5, 6, 8, 6, 6, 5, 8, 7, 6, 5, 7, 5),
          interpretationSteps =
            listOf(
              "M1 = Taliki (1111), M2 = Adama (1222), M3 = Ousmana (2121), M4 = Malidjou (2111).",
              "Par lecture horizontale des rangs naissent les 4 Filles : M5 Kalalaw, M6 Tontigui Garya, M7 Solomana, M8 Tontigui Garya.",
              "Les Témoins M13 et M14 convergent vers Ousmana (2121), donnant le Juge M15 = Moussa (2222) puis M16 = Taliki (1111).",
            ),
        ),
      detailedLessons = manbaL1To10Lessons,
    ),
    CourseModule(
      id = "m_manba_2_takht_biyout",
      moduleNumber = "Module 2.2",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 11 à 20 · Construction du Takht",
      title = "Érection du Takht (Ummahāt, Banāt, Ḥafīdāt, Shuhūd & Mīzān)",
      summary =
        "Les Leçons 11 à 20 de « Manbaʿ Uṣūl al-Raml » détaillent étape par étape l'architecture mathématique du tableau à 16 maisons (Al-Takht), l'art d'extraire les Filles sans erreur de rang, la loi de parité absolue du Juge (M15) et le bouclage du cycle par la 16e maison (M15 ⊕ M1).",
      classicalRules =
        listOf(
          "Leçons 11–13 (Frapper les 16 lignes) : tracer 4 groupes de 4 lignes spontanées sans compter d'avance, puis effacer par deux pour conserver 1 ou 2 points sur les 4 rangs de M1 à M4.",
          "Leçons 14–15 (Extraction des Filles M5–M8) : M5 rassemble les quatre têtes (Feu) de M1..M4 ; M6 les rangs d'Air ; M7 les rangs d'Eau ; M8 les rangs de Terre.",
          "Leçons 16–18 (Génération des Nièces & Témoins) : combiner rang par rang M1⊕M2→M9, M3⊕M4→M10, M5⊕M6→M11, M7⊕M8→M12, puis M9⊕M10→M13 et M11⊕M12→M14.",
          "Leçons 19–20 (Vérification du Mīzān M15 & M16) : le Juge M15 = M13⊕M14 possède toujours un nombre pair de points (4, 6 ou 8) ; s'il est impair, le calcul contient une erreur. La 16e maison M16 = M15⊕M1 relie le verdict à la source.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(15, "Badra Ali (Badra Ali (Al-Ijtimāʿ) · 2112) — Figure de parité paire, demeure naturelle du Juge M15"),
          CourseFigureFocus(2, "Ousmana (Ousmana (Qabḍ al-Dākhil) · 2121) — Réception des biens et ancrage des Nièces"),
          CourseFigureFocus(11, "Nouhoun (Nouhoun (Al-Nuṣra al-Dākhila) · 2211) — Victoire souveraine lorsqu'elle couronne M15"),
          CourseFigureFocus(5, "Lawsana (Lawsana (Al-ʿAtaba al-Khārija) · 1112) — Seuil sortant exigeant vigilance en M15/M16"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 14–20 : Extraction des Banāt & Contrôle de Parité M15",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 11–20",
          domain = QuestionDomain.GENERAL,
          planetHour = Planet.JUPITER,
          question = "Suivre pas à pas M1..M4 → M5..M8 → M9..M12 → M13..M14 → M15 (pair) & M16",
          motherDotCounts = listOf(6, 5, 6, 5, 5, 6, 5, 6, 6, 6, 5, 5, 5, 6, 6, 6),
          interpretationSteps =
            listOf(
              "M1 = Ousmana (2121), M2 = Inzan (1212), M3 = Nouhoun (2211), M4 = Adama (1222).",
              "Par lecture horizontale des rangs, les Filles M5..M8 forment le second quatuor avant l'addition des Nièces M9..M12.",
              "Vérifie que le Juge M15 = M13 ⊕ M14 totalise toujours un nombre pair de points (4, 6 ou 8) avant M16 = M15 ⊕ M1.",
            ),
        ),
      detailedLessons = manbaL11To20Lessons,
    ),
    CourseModule(
      id = "m_manba_3_tasakin_huruf",
      moduleNumber = "Module 2.3",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 21 à 30 · Tasākīn & Lettres Abjad",
      title = "Les Grands Tasākīn (Zanātī, Ḥadbaʾ, ʿAlkhad, Sakzaj) & Abjad",
      summary =
        "Aux Leçons 21 à 30 de « Manbaʿ Uṣūl al-Raml », al-Ṭūkhī enseigne comment chaque figure possède une demeure fixe (Bayt al-Taskīn) distincte de sa maison de tirage, expose les formules mnémoniques classiques des Tasākīn (Ḥadbaʾ, ʿAlkhad, Sakzaj, Zanātī) et relie les 16 figures aux lettres arabes Abjad.",
      classicalRules =
        listOf(
          "Leçons 21–23 (Notion de Taskīn) : une figure tirée dans une maison occupe une position accidentelle, mais reste reliée à sa demeure naturelle dans l'ordre du Taskīn.",
          "Leçons 24–26 (Formules classiques) : maîtriser le Taskīn al-Zanātī (1..16 de Laḥyān à Ṭarīq) ainsi que les clefs élémentaires « Ḥadbaʾ / ʿAlkhad / Sakzaj » utilisées pour indexer les lettres et les éléments.",
          "Leçons 27–28 (Correspondance des Lettres) : associer aux 16 figures leurs lettres arabes (ا jusqu'à ع puis les lettres complémentaires) et leur valeur numérique Abjad (1 à 1000).",
          "Leçons 29–30 (Lecture par rangs actifs) : convertir la parité impaire (Fard) des rangs d'une figure dominante pour composer des syllabes et vérifier le nom du questionneur.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(9, "Bayada (Bayaro (Al-Bayāḍ) · ط = 9) — Clarté intellectuelle, écriture et lettres pures"),
          CourseFigureFocus(14, "Djanfa Almami (Djanfa Almami (Al-Jūdla) · ن = 50) — Lettre Nūn et premier rang de la série ouest-africaine"),
          CourseFigureFocus(6, "Solomana (Solomana (Al-ʿUqla) · و = 6) — Lettre Wāw, lien et calcul des retards"),
          CourseFigureFocus(13, "Tontigui Garya (Tontigui Garya (Al-Naqī al-Khadd) · m = 40) — Lettre Mīm, douceur et concorde"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 21–30 : Correspondance Taskīn & Extraction Abjad",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 21–30",
          domain = QuestionDomain.HIDDEN_INTENT,
          planetHour = Planet.MERCURY,
          question = "Identifier la figure assise en sa propre demeure Taskīn et ses lettres Abjad",
          motherDotCounts = listOf(5, 6, 6, 6, 6, 5, 6, 5, 5, 6, 5, 5, 6, 6, 5, 6),
          interpretationSteps =
            listOf(
              "M1 = Adama (1222) occupe d'emblée sa propre Maison 1 du Taskīn al-Zanātī : signal direct et lucide.",
              "M2 = Ousmana (2121) confirme la Maison 2 du Taskīn al-Zanātī (double concordance à l'ouverture).",
              "Relever les rangs impairs (Fard) des figures clés pour l'analyse par Taskīn al-Ḥurūf et Abjad.",
            ),
        ),
      detailedLessons = manbaL21To30Lessons,
    ),
    CourseModule(
      id = "m_manba_4_etats_maisons",
      moduleNumber = "Module 2.4",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 31 à 40 · Piliers, Maisons & Humeurs",
      title = "Les 16 Maisons (Awtād, Succédentes, Cadentes) & Éléments",
      summary =
        "Les Leçons 31 à 40 de « Manbaʿ Uṣūl al-Raml » dressent la carte complète des 16 maisons : les 4 Piliers cardinaux (Awtād M1, M4, M7, M10), les Succédentes (M2, M5, M8, M11), les Cadentes (M3, M6, M9, M12), les 4 Mawāzīn (M13–M16), ainsi que le jeu des 4 éléments et tempéraments.",
      classicalRules =
        listOf(
          "Leçons 31–33 (Les 4 Awtād) : M1 (Âme/Demandeur), M4 (Foyer/Base/Issue cachée), M7 (Partenaire/Adversaire) et M10 (Autorité/Rang/But) portent le poids principal de la réponse présente.",
          "Leçons 34–36 (Succédentes & Cadentes) : M2 (Biens), M5 (Enfants/Joie), M8 (Pertes/Mort/Legs) et M11 (Espoirs/Alliés) montrent le devenir immédiat ; M3, M6, M9, M12 indiquent les déplacements, épreuves ou retraites.",
          "Leçons 37–38 (Témoins & Juge) : M13 synthétise l'état intérieur et le passé du consultant ; M14 l'environnement et le résultat visé ; M15 tranche ; M16 scelle la conséquence.",
          "Leçons 39–40 (Sympathies Élémentaires) : Feu (Chaud/Sec) et Air (Chaud/Humide) s'entraident ; Eau (Froid/Humide) et Terre (Froid/Sec) s'accordent ; Feu contre Eau et Air contre Terre engendrent friction.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(8, "Lomara (Lomara (Al-Ḥumra) · Feu) — Tempérament chaud et sec : urgence, conflit ou ardeur"),
          CourseFigureFocus(3, "Inzan (Inzan (Qabḍ al-Khārij) · Eau) — Tempérament froid et humide : dispersion ou fuite"),
          CourseFigureFocus(7, "Mangouchi (Mangouchi (Al-Inkis) · Terre) — Tempérament froid et sec : enfouissement et lenteur"),
          CourseFigureFocus(10, "Kalalaw (Kalalaw (Al-Nuṣra al-Khārija) · Feu) — Secours solaire sur l'axe des Awtād"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 35–40 : Force des Awtād et équilibre élémentaire",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 31–40",
          domain = QuestionDomain.AUTHORITY,
          planetHour = Planet.SUN,
          question = "Évaluer la solidité d'une démarche en observant M1, M4, M7, M10 et M15",
          motherDotCounts = listOf(6, 8, 5, 7, 6, 5, 6, 7, 6, 6, 5, 8, 5, 6, 6, 8),
          interpretationSteps =
            listOf(
              "Les 4 Mères : M1 = Nouhoun (2211), M2 = Ousmana (2121), M3 = Bayada (2212), M4 = Adama (1222).",
              "Dans les Awtād principaux : M1 Nouhoun soutient M4 Adama, tandis que M7 Inzan et M10 Inzan signalent une dépense nécessaire pour atteindre le rang.",
              "Les Témoins M13 Kalalaw et M14 Nouhoun se résolvent en M15 Taliki (1111) : mouvement rapide et voie ouverte.",
            ),
        ),
      detailedLessons = manbaL31To40Lessons,
    ),
  )
