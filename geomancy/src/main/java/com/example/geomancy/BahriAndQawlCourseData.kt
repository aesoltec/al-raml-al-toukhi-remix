package com.example.geomancy

/**
 * Cours N° 3 : « Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn » (الفُلْك المشحون في علم الرمل المصون)
 * par Cheikh Muḥammad ibn Shammās al-Baḥrī (Oman, 2e éd. 1431 H / 2010, 401 pages).
 *
 * Cours N° 4 : « Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml » (القول الجامع في علم الرمل)
 * par Shaykh Muḥammad Sās (58 pages, 30 chapitres).
 */
val bahriCourseBook: CourseBook =
  CourseBook(
    id = "book_2_bahri",
    orderIndex = 3,
    levelBadge = "Cours N° 3 · Encyclopédie Omanaise",
    frenchTitle = "Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn",
    arabicTitle = "الفُلْك المشحون في علم الرمل المصون",
    author = "Cheikh Muḥammad ibn Shammās ibn Nāṣir al-Baḥrī al-Azdī (Oman)",
    editionOrManuscript = "2e éd., Maktab al-Mustashār al-Khāṣṣ, Mascate, 1431 H / 2010",
    totalPagesOrFolios = "401 pages numérotées",
    accentElement = Element.AIR,
    overview =
      "Monument analytique d'Oman : Cheikh al-Baḥrī y rassemble les traditions d'Idrīs, d'al-Zanātī et de Khalaf al-Barbarī, en systématisant les Tasākīn (Bazdaḥ vs Abdaḥ), le contrôle de sincérité Ṣidq al-Raml (p. 90), le thème caché Bāṭin al-Takht (p. 142) et l'arbitrage des Awtād croisés (p. 316).",
    modules =
      listOf(
        CourseModule(
          id = "m2_1_origines_temps",
          moduleNumber = "Module 2.1",
          bookCitation = "Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn — Cheikh al-Baḥrī (2010)",
          chapterRef = "Muqaddima & Adab al-Raml · pp. 19–35, 58–59",
          title = "Origines prophétiques, Conditions de perfection & Temps interdits",
          summary =
            "Dans l'introduction d'« Al-Fulk al-Mashḥūn » (pp. 19–35), Cheikh al-Baḥrī rapporte le récit du prophète Idrīs et de l'ange Jibrīl sur le rivage, les deux rakaʿāt préparatoires, le tracé avec l'annulaire de la main droite et l'abstention durant les jours lunaires néfastes.",
          classicalRules =
            listOf(
              "Règle des 6 qualités de l'étudiant (pp. 26–27) : intelligence, passion, patience, sobriété, guidance d'un maître et longueur de pratique.",
              "Jours lunaires interdits ou déconseillés (pp. 58–59) : jours 3, 5, 13, 16, 21, 25 et 26 du mois lunaire, plus le dernier mercredi du mois.",
              "Condition de concentration : ne frapper le sable ni dans la colère, ni dans l'ivresse émotionnelle, ni sous la contrainte de la foule.",
              "Chaque rang des 16 lignes est frappé sans compter afin que l'esprit reçoive l'empreinte de l'instant.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(12, "Malidjou (Al-ʿAtaba al-Dākhila) — Symbole d'entrée bénie et d'ouverture du savoir"),
              CourseFigureFocus(9, "Bayada (Al-Bayāḍ) — Pureté de l'âme exigée avant de toucher le sable"),
              CourseFigureFocus(16, "Taliki (Al-Ṭarīq) — Le tracé originel sur le rivage d'Idrīs"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Consultation d'ouverture en heure propice (Al-Fulk, pp. 19–35)",
              sourceRef = "Al-Fulk al-Mashḥūn (Al-Baḥrī), Muqaddima, pp. 19–35",
              domain = QuestionDomain.GENERAL,
              planetHour = Planet.JUPITER,
              question = "Vérification d'un thème tracé dans la sérénité et un créneau lunaire favorable",
              motherDotCounts = listOf(6, 5, 5, 5, 6, 6, 5, 6, 5, 5, 5, 5, 5, 6, 5, 6),
              interpretationSteps =
                listOf(
                  "M1 (Malidjou 2111) et M2 (Bayada 2212) posent clarté et bénédiction dès l'ouverture.",
                  "M3 (Taliki 1111) et M4 (Ousmana 2121) montrent la voie droite suivie d'un acquis concret.",
                  "Le contrôle Ṣidq al-Raml valide la sincérité de l'intention avant toute décision.",
                ),
            ),
          detailedLessons = bahriM1Lessons,
        ),
        CourseModule(
          id = "m2_2_tasakin_abdah_bazdah",
          moduleNumber = "Module 2.2",
          bookCitation = "Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn — Cheikh al-Baḥrī (2010)",
          chapterRef = "Al-Tasākīn · pp. 34–57, 302–303",
          title = "Architecture des Tasākīn : Ṭabīʿa, Ḥurūf, Bazdaḥ vs Abdaḥ",
          summary =
            "Aux pages 34 à 57 puis 302–303 d'« Al-Fulk al-Mashḥūn », al-Baḥrī compare les grandes tables de demeure : Taskīn al-Ṭabīʿa (p. 34), Taskīn al-Ḥurūf (p. 35), Taskīn Abjad (pp. 42–43), le Taskīn Bazdaḥ d'al-Zanātī (N=2, A=7, E=4, T=8) et le Taskīn Abdaḥ de Khalaf al-Barbarī (1,2,4,8).",
          classicalRules =
            listOf(
              "Taskīn Bazdaḥ d'al-Zanātī (pp. 46–49) : associe les valeurs élémentaires Feu=2, Air=7, Eau=4, Terre=8 pour ordonner les 16 demeures.",
              "Taskīn Abdaḥ de Khalaf al-Barbarī (p. 54) : pondère les 4 rangs selon la suite géométrique 1, 2, 4, 8 pour le calcul du poids caché.",
              "Al-Fulk al-Dāʾir (pp. 302–303) : correction circulaire des maisons permettant de vérifier les correspondances entre figures et planètes.",
              "Chaque Taskīn possède une finalité : al-Zanātī pour le jugement général, Abdaḥ pour les mesures temporelles et les noms.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Chef de file du Taskīn al-Zanātī (Maison 1)"),
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Seconde demeure du Taskīn al-Zanātī"),
              CourseFigureFocus(14, "Djanfa Almami (Al-Jūdla) — Tête de la série ouest-africaine et rang actif"),
              CourseFigureFocus(15, "Badra Ali (Al-Ijtimāʿ) — Pivot du quinzième rang dans Al-Fulk al-Dāʾir"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Correspondance des Tasākīn Bazdaḥ & Abdaḥ (pp. 34–57)",
              sourceRef = "Al-Fulk al-Mashḥūn (Al-Baḥrī), Al-Tasākīn, pp. 34–57",
              domain = QuestionDomain.HIDDEN_INTENT,
              planetHour = Planet.MERCURY,
              question = "Observer les rangs Taskīn des 4 Mères dans Bazdaḥ et Abdaḥ",
              motherDotCounts = listOf(5, 6, 6, 6, 6, 5, 6, 5, 5, 5, 6, 5, 6, 6, 5, 5),
              interpretationSteps =
                listOf(
                  "M1 = Adama (1222), M2 = Ousmana (2121), M3 = Djanfa (1121), M4 = Nouhoun (2211).",
                  "Comparer le poids binaire Abdaḥ (1, 2, 4, 8) de chaque Mère aux valeurs Bazdaḥ (N=2, A=7, E=4, T=8).",
                  "Suivre dans le Bāṭin al-Takht comment le gouverneur naturel éclaire chaque maison tirée.",
                ),
            ),
          detailedLessons = bahriM2Lessons,
        ),
        CourseModule(
          id = "m2_3_sidq_batin_pures",
          moduleNumber = "Module 2.3",
          bookCitation = "Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn — Cheikh al-Baḥrī (2010)",
          chapterRef = "Ṣidq al-Raml & Bāṭin al-Takht · pp. 77–90, 141–145",
          title = "Contrôle Ṣidq al-Raml (p. 90), 4 Pures (p. 79) & Bāṭin (p. 142)",
          summary =
            "Dans les pages 77 à 145 d'« Al-Fulk al-Mashḥūn », al-Baḥrī livre trois outils de haute précision : les 4 Figures Pures élémentaires (Nār al-Nār à Turāb al-Turāb, p. 79), le test de sincérité Ṣidq al-Raml (pp. 89–90) et le Thème Intérieur Bāṭin al-Takht (pp. 141–143).",
          classicalRules =
            listOf(
              "Test de sincérité Ṣidq al-Raml (pp. 89–90) : calculer le sceau (M3 ⊕ M5) ⊕ (M11 ⊕ M15) ; il doit apparaître dans l'une des 16 maisons pour prouver l'authenticité du thème.",
              "Alerte d'intention trouble (pp. 89–90) : si les 4 Mères M1–M4 sont toutes Fixes ou toutes Sortantes, le consultant doute ou change d'avis.",
              "Les 4 Pures Élémentaires (pp. 77–79) : Nār al-Nār (rangs Feu de M1..M4), Hawāʾ al-Hawāʾ, Māʾ al-Māʾ et Turāb al-Turāb.",
              "Bāṭin al-Takht (pp. 141–143) : croiser chaque maison avec la figure maîtresse de sa demeure Taskīn pour dévoiler l'arrière-plan caché.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Figure d'ouverture dans l'exemple p. 90"),
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Deuxième Mère de l'exemple p. 90"),
              CourseFigureFocus(11, "Nouhoun (Al-Nuṣra al-Dākhila) — Force solaire stable"),
              CourseFigureFocus(4, "Moussa (Al-Jamāʿa) — Quatrième Mère et miroir collectif"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Vérification de sincérité Ṣidq al-Raml & Bāṭin (pp. 90, 142)",
              sourceRef = "Al-Fulk al-Mashḥūn (Al-Baḥrī), pp. 89–90 & 142–143",
              domain = QuestionDomain.GENERAL,
              planetHour = Planet.JUPITER,
              question = "Le thème est-il sincère selon la formule (M3⊕M5) ⊕ (M11⊕M15) ?",
              motherDotCounts = listOf(6, 5, 6, 5, 5, 6, 6, 6, 6, 6, 6, 6, 5, 6, 6, 5),
              interpretationSteps =
                listOf(
                  "M1 = Ousmana (2121), M2 = Adama (1222), M3 = Moussa (2222), M4 = Solomana (1221).",
                  "M5 = Ousmana (2121), M11 = Djanfa (1121) et M15 = Moussa (2222) donnent le sceau (M3⊕M5) ⊕ (M11⊕M15) = Adama (1222).",
                  "Comme Adama réapparaît en M2 et M6, le bandeau Ṣidq al-Raml confirme « Thème Sincère (Ṣādiq) ».",
                ),
            ),
          detailedLessons = bahriM3Lessons,
        ),
        CourseModule(
          id = "m2_4_dignites_consultations",
          moduleNumber = "Module 2.4",
          bookCitation = "Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn — Cheikh al-Baḥrī (2010)",
          chapterRef = "Sharaf/Hubūṭ, Aspects & Thématiques · pp. 65–70, 174–266",
          title = "Dignités (Sharaf/Hubūṭ), Aspects & Voyage / Durées (p. 255)",
          summary =
            "Entre les pages 65 et 266 d'« Al-Fulk al-Mashḥūn », al-Baḥrī détaille les exaltations et chutes planétaires des figures (pp. 67–68), les aspects géométriques (pp. 223–224) et les chapitres pratiques du mariage, des trésors, du voyage et des durées (Ibn Ḥabīb, p. 255).",
          classicalRules =
            listOf(
              "Dignités planétaires (pp. 67–68) : Exaltation (Sharaf) du Soleil en M1, Lune en M2, Saturne en M7, Jupiter en M4, Mars en M10, Vénus en M12, Mercure en M6.",
              "Aspects du thème (pp. 223–224) : Tasbīʿ, Tarbīʿ (Carré), Tathlīth (Trine) et Muqābala (Opposition) entre maisons liées au sujet.",
              "Calculs de durées (p. 255) : emploi des rangs Ayqagh, Bakar, Jalash et Damat avec méthode d'Ibn Ḥabīb.",
              "Voyage & Retour (pp. 174–210) : comparer M1 (voyageur), M9 (route) et M4 (patrie/retour) ; les Entrantes en M1 ou M4 ramènent l'absent.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(10, "Kalalaw (Al-Nuṣra al-Khārija) — Soleil en exaltation ou secours rapide en route"),
              CourseFigureFocus(16, "Taliki (Al-Ṭarīq) — Itinéraire dégagé pour le voyageur"),
              CourseFigureFocus(13, "Tontigui Garya (Al-Naqī al-Khadd) — Concorde dans les chapitres du mariage"),
              CourseFigureFocus(6, "Solomana (Al-ʿUqla) — Retard de voyage ou entrave sur le retour"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Consultation de Voyage & Dignités planétaires (pp. 67, 255)",
              sourceRef = "Al-Fulk al-Mashḥūn (Al-Baḥrī), pp. 67–68 & 255",
              domain = QuestionDomain.TRAVEL,
              planetHour = Planet.MOON,
              question = "Issue d'un grand voyage lointain et délai du retour",
              motherDotCounts = listOf(5, 5, 5, 5, 5, 6, 5, 5, 6, 6, 5, 5, 6, 5, 6, 6),
              interpretationSteps =
                listOf(
                  "M1 (Taliki 1111) place immédiatement le thème sous le signe de la route et du mouvement.",
                  "M2 (Tontigui Garya 1211) et M3 (Nuṣra Dākhila 2211) sécurisent les ressources et l'étape.",
                  "M4 (Bayada 2212) assure un retour paisible ; consulte l'onglet Bāṭin & Dignités sur le Takht complet.",
                ),
            ),
          detailedLessons = bahriM4Lessons,
        ),
        CourseModule(
          id = "m2_5_duel_mod9_zairja",
          moduleNumber = "Module 2.5",
          bookCitation = "Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn — Cheikh al-Baḥrī (2010)",
          chapterRef = "Al-Ghālib wa-l-Maghlūb & Zāʾirja · pp. 151–165, 280–316, 368–387",
          title = "Arbitrage Duel Mod-9, Awtād croisés (p. 316) & Zāʾirja",
          summary =
            "Dans ses chapitres militaires et divinatoires (pp. 151–165, 280–316 et 368–387), « Al-Fulk al-Mashḥūn » introduit le Taḥlīf Mod-9, les armées Impaires vs Paires, les Awtād croisés de la p. 316, la Zāʾirja des 16 lettres et la Qurʿa Ramliyya.",
          classicalRules =
            listOf(
              "Taḥlīf Mod-9 : additionner les valeurs numériques des deux camps et réduire modulo 9 ; selon le vers classique, le reste le plus bas l'emporte souvent (« yasmū aqalluhā ») sauf cas d'inversion.",
              "Armées Impaires vs Paires : recenser les rangs Fard contre les rangs Zawj entre Camp A et Camp B.",
              "Awtād croisés (p. 316) : former deux figures arbitres — Axe A (Feu M1 · Air M4 · Eau M7 · Terre M10) vs Axe B (Feu M10 · Air M7 · Eau M4 · Terre M1).",
              "Zāʾirja des 16 lettres (pp. 295–300) & Qurʿa Ramliyya (pp. 368–387) : extraire des réponses littérales à 16 questions codifiées.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(14, "Djanfa Almami (Al-Jūdla) — Camp d'attaque dans les armées impaires"),
              CourseFigureFocus(11, "Nouhoun (Al-Nuṣra al-Dākhila) — Supériorité durable dans les Awtād croisés"),
              CourseFigureFocus(8, "Lomara (Al-Ḥumra) — Choc et agressivité dans les combats"),
              CourseFigureFocus(15, "Badra Ali (Al-Ijtimāʿ) — Arbitre central au croisement des axes"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Arbitrage par les Awtād croisés & Mod-9 (p. 316)",
              sourceRef = "Al-Fulk al-Mashḥūn (Al-Baḥrī), p. 316",
              domain = QuestionDomain.CONTEST,
              planetHour = Planet.MARS,
              question = "Confrontation des axes M1·M4·M7·M10 vs M10·M7·M4·M1",
              motherDotCounts = listOf(5, 5, 6, 5, 6, 6, 5, 5, 6, 5, 6, 6, 5, 6, 6, 6),
              interpretationSteps =
                listOf(
                  "M1 (Jūdla 1121) et M2 (Nuṣra Dākhila 2211) arment le Camp A d'élan offensif et de tenue.",
                  "M3 (Ḥumra 2122) et M4 (Inkis 2221) pèsent sur le camp opposé par friction et pesanteur.",
                  "Ouvre dans le Takht complet pour consulter directement le calcul Taḥlīf Mod-9 et les Awtād croisés d'Al-Fulk.",
                ),
            ),
          detailedLessons = bahriM5Lessons,
        ),
      ),
  )

val qawlCourseBook: CourseBook =
  CourseBook(
    id = "book_3_qawl",
    orderIndex = 4,
    levelBadge = "Cours N° 4 · Synthèse des Triangles",
    frenchTitle = "Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml",
    arabicTitle = "القول الجامع في علم الرمل",
    author = "Shaykh Muḥammad Sās",
    editionOrManuscript = "Traité structuré en 30 chapitres",
    totalPagesOrFolios = "58 pages",
    accentElement = Element.EARTH,
    overview =
      "Ouvrage concis et mathématiquement très riche : « Al-Qawl al-Jāmiʿ » codifie la surface maximale de 96 points (Raml Kāmil / Nāqiṣ), la balance Vie (Air+Eau) vs Fermeture (Feu+Terre), les 5 Triangles du Takht (Muthallathāt), les aspects astrologiques sur M1–M12 et la méthode de cheminement Tasīr (p. 37).",
    modules =
      listOf(
        CourseModule(
          id = "m3_1_modele_96_p34",
          moduleNumber = "Module 3.1",
          bookCitation = "Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml — Shaykh Muḥammad Sās",
          chapterRef = "Chapitres 1–14 & Thème modèle · pp. 2–19, 34–36",
          title = "Thème modèle de la p. 34, Seuil des 96 pts & Balance Vie/Fermeture",
          summary =
            "Aux pages 34 à 36 d'« Al-Qawl al-Jāmiʿ », Shaykh Muḥammad Sās dresse un thème complet commenté maison par maison avec sa valeur de points, introduisant la distinction entre Raml Kāmil (plein) et Raml Nāqiṣ ainsi que l'équilibre Vie (Air+Eau) face à Fermeture (Feu+Terre).",
          classicalRules =
            listOf(
              "Surface maximale de 96 points (16 maisons × 6 pts) : au-delà du seuil de plénitude, le thème est dit Raml Kāmil ; en deçà, Raml Nāqiṣ.",
              "Balance Vie vs Fermeture : additionner les rangs Air + Eau (ouverture, circulation) face aux rangs Feu + Terre (blocage ou solidité).",
              "Thème modèle de la p. 34 : M1 = Nuṣra Khārija (Shīn/300), M2 = Ḥumra (Qāf/100), M3 = Nuṣra Khārija (300), M4 = Inkis (Ṣād/90).",
              "Suivre la chaîne des Filles M5–M8 jusqu'au Juge M15 et observer quel élément domine la table.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(10, "Kalalaw (Al-Nuṣra al-Khārija) — M1 et M3 du thème modèle p. 34"),
              CourseFigureFocus(8, "Lomara (Al-Ḥumra) — M2 du thème modèle p. 34"),
              CourseFigureFocus(7, "Mangouchi (Al-Inkis) — M4 du thème modèle p. 34"),
              CourseFigureFocus(4, "Moussa (Al-Jamāʿa) — Figure pivot d'assemblée et de synthèse"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Thème modèle exact d'« Al-Qawl al-Jāmiʿ » (p. 34)",
              sourceRef = "Al-Qawl al-Jāmiʿ (Shaykh Muḥammad Sās), p. 34",
              domain = QuestionDomain.GENERAL,
              planetHour = Planet.SUN,
              question = "Lecture complète du Takht modèle (Nuṣra Khārija, Ḥumra, Nuṣra Khārija, Inkis)",
              motherDotCounts = listOf(5, 5, 6, 6, 6, 5, 6, 6, 5, 5, 6, 6, 6, 6, 6, 5),
              interpretationSteps =
                listOf(
                  "Mères de la p. 34 : M1 = Kalalaw (1122), M2 = Lomara (2122), M3 = Kalalaw (1122), M4 = Mangouchi (2221).",
                  "Extraction exacte des Filles : M5 = Inzan (1212), M6 = Lawsana (1112), M7 = Moussa (2222), M8 = Mangouchi (2221).",
                  "Nièces & Témoins : M9 = Adama, M10 = Djanfa, M11 = Lomara, M12 = Mangouchi ; M13 = Ousmana, M14 = Ousmana -> Juge M15 = Moussa.",
                  "Consulte le panneau Al-Qawl al-Jāmiʿ sur le Takht complet pour lire la balance des 96 points et Vie vs Fermeture.",
                ),
            ),
          detailedLessons = qawlM1Lessons,
        ),
        CourseModule(
          id = "m3_2_triangles_aspects_tasir",
          moduleNumber = "Module 3.2",
          bookCitation = "Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml — Shaykh Muḥammad Sās",
          chapterRef = "Muthallathāt, Aspects & Tasīr · pp. 20–32, 37",
          title = "Les 5 Triangles, Aspects sur M1–M12 & Cheminement Tasīr (p. 37)",
          summary =
            "Les chapitres centraux d'« Al-Qawl al-Jāmiʿ » (pp. 20–32, 37) présentent les 51 triangles possibles (35 + 16), la lecture des 5 Triangles du Takht (T1 à T5), les aspects du zodiaque sur les 12 premières maisons et le cheminement dynamique du Mīzān (Tasīr, p. 37).",
          classicalRules =
            listOf(
              "Les 5 Triangles (Muthallathāt al-Buyūt) : T1 (M1+M2→M9), T2 Dalīl (M3+M4→M10), T3 (M5+M6→M11), T4 (M7+M8→M12) et T5 ʿĀqiba (M13+M14→M15).",
              "Comparer la qualité du triangle Dalīl (T2, maison 10) face au triangle de l'Issue ʿĀqiba (T5, maison 15).",
              "Aspects astrologiques sur M1–M12 (p. 28) : Tasdīs (Sextile 1/6), Tarbīʿ (Carré 1/4), Tathlīth (Trine 1/3) et Muqābala (Opposition 1/2).",
              "Cheminement Tasīr (p. 37) : faire progresser l'index de la figure significatrice selon la valeur des rangs jusqu'à la maison d'aboutissement.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(11, "Nouhoun (Al-Nuṣra al-Dākhila) — Sommet favorable au Triangle T2 (Dalīl) ou T5 (ʿĀqiba)"),
              CourseFigureFocus(15, "Badra Ali (Al-Ijtimāʿ) — Point de convergence du 5e Triangle"),
              CourseFigureFocus(6, "Solomana (Al-ʿUqla) — Aspect carré (Tarbīʿ) de frein ou de délai"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Application des 5 Triangles & Tasīr (pp. 28–37)",
              sourceRef = "Al-Qawl al-Jāmiʿ (Shaykh Muḥammad Sās), pp. 28–37",
              domain = QuestionDomain.AUTHORITY,
              planetHour = Planet.JUPITER,
              question = "Soutien des Triangles T2 (Dalīl) et T5 (ʿĀqiba) pour une promotion",
              motherDotCounts = listOf(6, 5, 5, 5, 6, 6, 5, 5, 5, 6, 6, 6, 6, 5, 6, 5),
              interpretationSteps =
                listOf(
                  "M1 (Malidjou 2111) et M2 (Nouhoun 2211) bâtissent le Triangle T1 terminé en M9.",
                  "M3 (Adama 1222) et M4 (Ousmana 2121) forment le Triangle T2 Dalīl culminant en M10.",
                  "Ouvre le Takht complet pour comparer l'harmonie entre T2 (Dalīl) et T5 (ʿĀqiba) ainsi que les aspects M1–M12.",
                ),
            ),
          detailedLessons = qawlM2Lessons,
        ),
        CourseModule(
          id = "m3_3_consultations_specialisees",
          moduleNumber = "Module 3.3",
          bookCitation = "Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml — Shaykh Muḥammad Sās",
          chapterRef = "Masāʾil pratiques · pp. 38–58",
          title = "Consultations spécialisées : Vol, Mariage, Prison & Soins",
          summary =
            "La dernière partie d'« Al-Qawl al-Jāmiʿ » (pp. 38–58) réunit des règles brèves et incisives pour les questions urgentes : libération d'un détenu, récupération d'un bien volé, compatibilité d'un mariage et pronostic de guérison.",
          classicalRules =
            listOf(
              "Prisonnier & Retenue (M12) : si des figures Sortantes (Inzan, Nuṣra Khārija, ʿAtaba Khārija) gouvernent M12 et M15, la libération est proche.",
              "Mariage (M1 vs M7) : observer le Tasdīs ou Tathlīth entre M1 et M7 ainsi que l'absence d'Inkis ou de Ḥumra en M15.",
              "Vol & Restitution : vérifier si le maître de M2 se transfère vers M1 ou M4 ; si M8/M12 l'emportent, le bien s'éloigne.",
              "Soins & Santé (M6) : relier l'élément de M6 à l'humeur corporelle dominante pour conseiller le tempérament opposé.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(5, "Lawsana (Al-ʿAtaba al-Khārija) — Signe de sortie de prison ou fin d'un blocage"),
              CourseFigureFocus(13, "Tontigui Garya (Al-Naqī al-Khadd) — Accord heureux entre M1 et M7"),
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Préservation et retour du bien en M2"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Consultation spécialisée : Restitution & Mariage (pp. 38–58)",
              sourceRef = "Al-Qawl al-Jāmiʿ (Shaykh Muḥammad Sās), Ch. 23–30, pp. 38–58",
              domain = QuestionDomain.PARTNERSHIP,
              planetHour = Planet.VENUS,
              question = "Accord entre M1 et M7 par aspect harmonique et absence d'obstacle en M12/M15",
              motherDotCounts = listOf(5, 6, 5, 5, 6, 5, 6, 6, 6, 6, 5, 5, 5, 6, 6, 5),
              interpretationSteps =
                listOf(
                  "M1 (Tontigui Garya 1211) et M2 (Ousmana 2121) reflètent bienveillance et attachement sincère.",
                  "M3 (Nouhoun 2211) et M4 (Inzan 1212) invitent à préserver l'équilibre entre don et retenue.",
                  "Vérifie l'aspect entre M1 et M7 ainsi que les Isqāṭāt modulo 7 dans la synthèse complète.",
                ),
            ),
          detailedLessons = qawlM3Lessons,
        ),
      ),
  )
