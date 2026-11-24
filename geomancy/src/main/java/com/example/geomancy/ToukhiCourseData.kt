package com.example.geomancy

/**
 * Cours N° 1 (obligatoirement en premier) :
 * « Bulūgh al-Amal fī ʿIlm al-Raml » (بلوغ الأمل في علم الرمل)
 * de Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī (Beyrouth, 1411 H / 1991, 98 pages).
 */
val toukhiCourseBook: CourseBook =
  CourseBook(
    id = "book_1_toukhi",
    orderIndex = 1,
    levelBadge = "Cours N° 1 · Fondamentaux",
    frenchTitle = "Bulūgh al-Amal fī ʿIlm al-Raml (L’Atteinte de l’Espérance)",
    arabicTitle = "بلوغ الأمل في علم الرمل",
    author = "Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī (Directeur d’Al-Futūḥ al-Falakī)",
    editionOrManuscript = "Al-Maktaba al-Thaqāfiyya, Beyrouth, 1411 H / 1991",
    totalPagesOrFolios = "98 pages · 40 Bāb + Jadāwil",
    accentElement = Element.FIRE,
    overview =
      "Traité pilier de l'application : Cheikh al-Ṭūkhī codifie en 40 chapitres clairs la pureté rituelle du tracé dans le sable, l'érection des 16 maisons, les Tasākīn, les 7 procédés d'Istikhrāj al-Ḍamīr, les jugements thématiques et la synthèse finale du duel (p. 97).",
    modules =
      listOf(
        CourseModule(
          id = "m1_1_trace_16_lignes",
          moduleNumber = "Module 1.1",
          bookCitation = "Bulūgh al-Amal fī ʿIlm al-Raml — Cheikh al-Ṭūkhī (1991)",
          chapterRef = "Bāb 1 · pp. 6–8",
          title = "Pureté du tracé, 16 lignes & Érection du Takht",
          summary =
            "Dans le Bāb 1 de « Bulūgh al-Amal », Cheikh al-Ṭūkhī enseigne que le géomancien doit se tenir en état de pureté (ṭahāra), face à la Qibla, réciter Al-Fātiḥa et la sourate Al-Zalzala avant de tracer 4 × 4 lignes de points sans les compter à l'avance.",
          classicalRules =
            listOf(
              "Ne jamais dépasser 12 points par ligne ; réduire par paires jusqu'à un reste Fard (1 point) ou Zawj (2 points).",
              "Les 4 premières lignes forment M1, puis M2, M3 et M4 (Les Mères / Al-Ummahāt).",
              "Extraction horizontale des 4 Filles (Banāt M5–M8) à partir des rangs Feu, Air, Eau et Terre des Mères.",
              "Dérivation par addition binaire des Nièces (M9–M12), Témoins (M13–M14), Juge (M15, obligatoirement pair) et 16e Maison (M15 ⊕ M1).",
              "Discipline du sable : limiter la consultation à 3 tirages sérieux par jour et ne pas répéter une même question le même jour.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Ouverture noble et droiture de l'intention"),
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Réception et rassemblement des points"),
              CourseFigureFocus(15, "Badra Ali (Al-Ijtimāʿ) — Gouverneur naturel du Juge M15"),
              CourseFigureFocus(16, "Taliki (Al-Ṭarīq) — Gouverneur naturel de l'Issue M16"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Thème d'initiation : Consultation d'ouverture (Bāb 1)",
              sourceRef = "Bulūgh al-Amal (Al-Ṭūkhī), Bāb 1, pp. 6–8",
              domain = QuestionDomain.GENERAL,
              planetHour = Planet.JUPITER,
              question = "Ouverture et réussite d'une entreprise entamée avec rectitude",
              motherDotCounts = listOf(5, 6, 8, 4, 6, 7, 8, 5, 4, 6, 5, 7, 6, 4, 8, 6),
              interpretationSteps =
                listOf(
                  "M1 (Adama 1222) et M2 (Ousmana 2121) annoncent une intention droite et un gain entrant.",
                  "M3 (Nuṣra Dākhila 2211) et M4 (Jamāʿa 2222) apportent soutien collectif et ancrage solide.",
                  "Les Filles M5–M8 redistribuent les quatre éléments (Feu à Terre) vers les Nièces M9–M12.",
                  "Vérifie la parité du Juge M15 (nombre pair de points total) avant de conclure sur M16.",
                ),
            ),
          detailedLessons = bulughM1Lessons,
        ),
        CourseModule(
          id = "m1_2_natures_maisons",
          moduleNumber = "Module 1.2",
          bookCitation = "Bulūgh al-Amal fī ʿIlm al-Raml — Cheikh al-Ṭūkhī (1991)",
          chapterRef = "Bāb 2–6 · pp. 8–11",
          title = "Natures des 16 figures, Awtād, Maisons & Éléments",
          summary =
            "Dans les chapitres 2 à 6 de « Bulūgh al-Amal », al-Ṭūkhī classe les 16 figures selon leur mouvement (Entrantes, Sortantes, Fixes, Mobiles), leur fortune (Fastes, Néfastes, Mixtes) et répartit les 16 maisons entre Awtād, Succédentes et Cadentes.",
          classicalRules =
            listOf(
              "Mouvements : figures Entrantes (Ousmana, Nuṣra Dākhila, ʿAtaba Dākhila, Ijtimāʿ), Sortantes (Inzan, ʿAtaba Khārija, Nuṣra Khārija), Fixes (Adama, Moussa, ʿUqla, Inkis, Bayāḍ) et Mobiles (Ḥumra, Naqī al-Khadd, Jūdla, Ṭarīq).",
              "Awtād (Piliers M1, M4, M7, M10) : état présent, force et fondations immédiates.",
              "Succédentes (M2, M5, M8, M11) : devenir proche et suites concrètes de la question.",
              "Cadentes (M3, M6, M9, M12) : passé accompli, déplacements mineurs et affaiblissement.",
              "Les 4 Mawāzīn du thème : M12, M14, M15 et M16 forment la balance finale de l'arrêt.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(12, "Malidjou (Al-ʿAtaba al-Dākhila) — Figure Entrante des commencements"),
              CourseFigureFocus(5, "Lawsana (Al-ʿAtaba al-Khārija) — Figure Sortante de rupture ou départ"),
              CourseFigureFocus(6, "Solomana (Al-ʿUqla) — Figure Fixe de retenue et d'attente"),
              CourseFigureFocus(13, "Tontigui Garya (Al-Naqī al-Khadd) — Figure Mobile de grâce et d'entente"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Lecture des 4 Awtād (M1, M4, M7, M10) et des 4 Mawāzīn (Bāb 3–6)",
              sourceRef = "Bulūgh al-Amal (Al-Ṭūkhī), Bāb 3–6, pp. 8–10",
              domain = QuestionDomain.GENERAL,
              planetHour = Planet.VENUS,
              question = "Observer la répartition Entrantes / Fixes / Sortantes dans les Awtād",
              motherDotCounts = listOf(6, 5, 5, 5, 5, 6, 5, 6, 5, 6, 6, 5, 5, 6, 5, 5),
              interpretationSteps =
                listOf(
                  "M1 (Malidjou 2111, Entrante) et M4 (Tontigui Garya 1211, Mobile) ouvrent le thème avec souplesse et accueil.",
                  "M2 (Ousmana 2121, Entrante) consolide le devenir proche tandis que M3 (Solomana 1221, Fixe) stabilise l'acquis.",
                  "Observer la complémentarité des éléments Feu/Air et Eau/Terre à travers M7, M10 et les 4 Mawāzīn.",
                ),
            ),
          detailedLessons = bulughM2Lessons,
        ),
        CourseModule(
          id = "m1_3_tasakin_damir",
          moduleNumber = "Module 1.3",
          bookCitation = "Bulūgh al-Amal fī ʿIlm al-Raml — Cheikh al-Ṭūkhī (1991)",
          chapterRef = "Bāb 7–15 · pp. 11–26",
          title = "Tasākīn, Durées & 7 méthodes d’Istikhrāj al-Ḍamīr",
          summary =
            "Le cœur des Bāb 7 à 15 de « Bulūgh al-Amal » expose les ordres de demeure (Taskīn al-Zanātī, al-Ayyām, al-Ḥurūf, al-Jūdala), le calcul des délais et les 7 méthodes pour dévoiler la pensée secrète (Ḍamīr) et l'objet caché (Khabīya).",
          classicalRules =
            listOf(
              "Seuil des 90 et 60 points (Bāb 14) : > 90 points annonce une décision rapide et franche ; < 60 indique jeu fermé ou rétention.",
              "Istikhrāj al-Ḍamīr : additionner le rang Taskīn et le réduire mod 16 ou repérer la figure répétée ≥ 3 fois (Mustawlī).",
              "Extraction du prénom & Khabīya (Bāb 10–13) : correspondance Abjad des rangs actifs de M1, M15 et de la figure dominante.",
              "Tableau des contraires (Al-Aḍdād) : inverser les 4 rangs d'une figure (⊕ Ṭarīq) pour connaître son opposé.",
              "Durées temporelles (Bāb 8) : selon que la figure significative siège en Watad (jours/mois), Succédente ou Cadente.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(7, "Mangouchi (Al-Inkis) — Indice des choses enfouies et secrets (Khabīya)"),
              CourseFigureFocus(9, "Bayaro (Al-Bayāḍ) — Clarté de l'esprit, écriture et vérité"),
              CourseFigureFocus(11, "Nouhoun (Al-Nuṣra al-Dākhila) — Intention noble et victoire assurée"),
              CourseFigureFocus(3, "Inzan (Qabḍ al-Khārij) — Dépense ou objet sorti de la main"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Istikhrāj al-Ḍamīr : Découvrir l'objet ou le souci secret (Bāb 12–14)",
              sourceRef = "Bulūgh al-Amal (Al-Ṭūkhī), Bāb 12–14, pp. 18–25",
              domain = QuestionDomain.HIDDEN_INTENT,
              planetHour = Planet.MERCURY,
              question = "Identifier le souci caché du consultant et le délai de résolution",
              motherDotCounts = listOf(8, 5, 8, 6, 8, 8, 5, 6, 6, 7, 5, 5, 5, 5, 5, 7),
              interpretationSteps =
                listOf(
                  "M1 (Lomara (Al-Ḥumra) 2122) révèle une préoccupation ardente ou un conflit récent.",
                  "M2 (Bayaro (Al-Bayāḍ) 2212) et M3 (Malidjou (Al-ʿAtaba al-Dākhila) 2111) orientent vers un écrit, un document ou une démarche officielle.",
                  "M4 (Taliki (Al-Ṭarīq) 1111) place le souci sur un déplacement ou une décision en suspens.",
                  "Compare le total des points aux seuils de 60 et 90 (Bāb 14) et relève l'élément dominant dans le Ḍamīr.",
                ),
            ),
          detailedLessons = bulughM3Lessons,
        ),
        CourseModule(
          id = "m1_4_jugements_duel",
          moduleNumber = "Module 1.4",
          bookCitation = "Bulūgh al-Amal fī ʿIlm al-Raml — Cheikh al-Ṭūkhī (1991)",
          chapterRef = "Bāb 16–40 · pp. 26–43",
          title = "Consultations thématiques : Voyage, Vol, Maladie & Duel",
          summary =
            "Dans la seconde moitié théorique de « Bulūgh al-Amal » (Bāb 16 à 40), al-Ṭūkhī applique le Takht à chaque cas de la vie : nouvelles de l'absent, vol, maladie, trésors, prévisions annuelles (Nayrūz) et confrontation entre deux adversaires (Bāb 26 & 29).",
          classicalRules =
            listOf(
              "Voyageur absent (Bāb 16–18) : observer M1, M9 (route lointaine) et la direction élémentaire du Témoin M13.",
              "Vol & Objet perdu (Bāb 20–22) : M7 désigne le voleur, M2 le bien ; si M7 et M15 se rejoignent en Entrantes, le bien revient.",
              "Al-Ghālib wa-l-Maghlūb (Bāb 26 & 29) : Camp A (M1, M2, M10, M13) contre Camp B (M7, M8, M4, M14), arbitrage par M15.",
              "Maladie & Humeurs (Bāb 24) : la figure de M6 indique la nature chaude/froide du mal ; comparer M1 et M8.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(14, "Djanfa Almami (Al-Jūdla) — Audace offensive dans le duel et l'action"),
              CourseFigureFocus(8, "Lomara (Al-Ḥumra) — Ardeur martiale, tension et cartons disciplinaires"),
              CourseFigureFocus(10, "Kalalaw (Al-Nuṣra al-Khārija) — Percée extérieure et victoire rapide"),
              CourseFigureFocus(4, "Moussa (Al-Jamāʿa) — Partage des points, match nul ou foule"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Duel sportif & Al-Ghālib wa-l-Maghlūb (Bāb 26 & 29)",
              sourceRef = "Bulūgh al-Amal (Al-Ṭūkhī), Bāb 26 & 29, pp. 34–37",
              domain = QuestionDomain.CONTEST,
              planetHour = Planet.SUN,
              question = "Camp A vs Camp B : lecture des deux camps et rôle du Juge",
              motherDotCounts = listOf(7, 8, 6, 8, 6, 6, 5, 5, 5, 8, 5, 6, 8, 5, 6, 6),
              interpretationSteps =
                listOf(
                  "Camp A ouvre sur M1 = Adama (1222) et M2 = Nuṣra Dākhila (2211), figures solaires et fastes.",
                  "Camp B repose sur M3 = Inzan (1212) et M4 = Lomara (2122), marquant dépense d'énergie et tension.",
                  "Le moteur compare les axes (M1, M2, M10, M13) contre (M7, M8, M4, M14) puis tranche par M15.",
                ),
            ),
          detailedLessons = bulughM4Lessons,
        ),
        CourseModule(
          id = "m1_5_hulul_jadawil_p97",
          moduleNumber = "Module 1.5",
          bookCitation = "Bulūgh al-Amal fī ʿIlm al-Raml — Cheikh al-Ṭūkhī (1991)",
          chapterRef = "Ḥulūl al-Ashkāl & Synthèse finale · pp. 43–97",
          title = "Les 16 Figures dans les 16 Maisons & Synthèse p. 94–97",
          summary =
            "La dernière grande section de « Bulūgh al-Amal » répertorie les traits physiques et moraux (Ṣifāt, pp. 43–72), les 256 combinaisons Ḥulūl al-Ashkāl (pp. 73–93), le tableau des rapports Jadwal al-Nisab (p. 94) et la règle finale du vainqueur (p. 97).",
          classicalRules =
            listOf(
              "Ḥulūl al-Ashkāl (pp. 73–93) : chaque figure prend une nuance particulière selon la maison 1..16 où elle apparaît.",
              "Jadwal al-Nisab (p. 94) : distinguer Ṭālib (M1), Maṭlūb (M7), Dalīl, Shāhid, Sirr et Mustawlī (figure répétée ≥ 3 fois).",
              "Contrôle de véracité par Tarbīʿ (pp. 95–96) : croiser les angles pour vérifier si le thème répond sans trouble.",
              "Règle finale du duel (p. 97) : comparer les points ouverts de la Droite (M1 + M13 + M15) face à la Gauche (M7 + M14 + M16), avec transfert vers l'arbitre M10.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(11, "Nouhoun (Al-Nuṣra al-Dākhila) — Souveraineté et stabilité en M1/M10/M15"),
              CourseFigureFocus(15, "Badra Ali (Al-Ijtimāʿ) — Jonction des témoins et sentence équilibrée"),
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Élévation et loyauté sur le côté droit (M1/M13)"),
              CourseFigureFocus(5, "Lawsana (Al-ʿAtaba al-Khārija) — Affaiblissement lorsqu'elle occupe M16"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Application p. 97 : Balance Droite (M1+M13+M15) vs Gauche (M7+M14+M16)",
              sourceRef = "Bulūgh al-Amal (Al-Ṭūkhī), p. 97",
              domain = QuestionDomain.CONTEST,
              planetHour = Planet.MARS,
              question = "Vérification finale du côté droit face au côté gauche (p. 97)",
              motherDotCounts = listOf(6, 5, 5, 6, 5, 6, 8, 8, 8, 5, 7, 7, 5, 7, 5, 8),
              interpretationSteps =
                listOf(
                  "Additionne les points impairs (actifs) sur l'axe droit M1 + M13 + M15 puis sur l'axe gauche M7 + M14 + M16.",
                  "Observe la bascule vers M10 (maison d'honneur et d'arbitrage) lorsque les deux plateaux sont proches.",
                  "Repère toute figure répétée trois fois ou plus (Mustawlī) qui impose sa sentence à l'ensemble du thème.",
                ),
            ),
          detailedLessons = bulughM5Lessons,
        ),
      ),
  )
