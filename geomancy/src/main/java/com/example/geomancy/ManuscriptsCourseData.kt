package com.example.geomancy

/**
 * Cours N° 5 : « Ḥāwī al-Akhbār wa Kanz al-Asrār » (حاوي الأخبار وكنز الأسرار)
 * de Shaykh Ḥāzim al-Maqdisī & Urjūza « Dhāt al-Durar / al-Muʿizzī » d'Abū Bakr al-Fāsī (196 pages).
 *
 * Cours N° 6 : « Kitāb fī ʿIlm al-Raml wa-l-Aḥkām » (Manuscrit MS 2380 — جامعة الملك سعود)
 * par Al-Ḥājj Maḥmūd Muḥammad Yūnus al-Rūmānī (1091 H / 1680, 68 feuillets / 76 pp. PDF).
 *
 * Cours N° 7 : « Kitāb fī ʿIlm al-Raml » (Manuscrit MS 2405 — جامعة الملك سعود)
 * Compilation mecquoise (al-Zanātī, Ṭumṭum al-Hindī, Khalaf al-Barbarī, al-Ṭarābulusī, al-Bastī, 72 pp. PDF).
 */
val hawiCourseBook: CourseBook =
  CourseBook(
    id = "book_4_hawi",
    orderIndex = 5,
    levelBadge = "Cours N° 5 · Balances & Versification",
    frenchTitle = "Ḥāwī al-Akhbār wa Kanz al-Asrār & Urjūza Dhāt al-Durar",
    arabicTitle = "حاوي الأخبار وكنز الأسرار والبحر الزخار",
    author = "Shaykh Ḥāzim al-Maqdisī & Abū Bakr ibn Muḥammad al-Maghribī al-Fāsī",
    editionOrManuscript = "Manuscrit classique maghrébo-levantin",
    totalPagesOrFolios = "196 pages",
    accentElement = Element.WATER,
    overview =
      "Recueil majeur réunissant le poème didactique « Dhāt al-Durar » et les développements de Shaykh Ḥāzim al-Maqdisī sur les 4 Mawāzīn relationnels (Naẓar, Nuṭq, Ittiṣāl, Infiṣāl), les symétries Ḍidd/Aṣḥāb, la hiérarchie quaternaire des 16 figures et la règle de guerre des Deux Armées.",
    modules =
      listOf(
        CourseModule(
          id = "m4_1_urjuza_maitres",
          moduleNumber = "Module 4.1",
          bookCitation = "Ḥāwī al-Akhbār wa Kanz al-Asrār — Shaykh Ḥāzim al-Maqdisī & al-Fāsī",
          chapterRef = "Urjūza Dhāt al-Durar · pp. 2, 28–44",
          title = "L’Urjūza Dhāt al-Durar & l’Héritage des Quatre Maîtres",
          summary =
            "Dans « Ḥāwī al-Akhbār », les pages 2 et 28–44 transmettent la Qaṣīda versifiée d'Abū Bakr al-Fāsī, qui synthétise les enseignements d'al-Zanātī, Ṭumṭum al-Hindī, Khalaf al-Barbarī et al-Ṭarābulusī sur les moments propices, l'orientation et la nature des 16 figures.",
          classicalRules =
            listOf(
              "Mémoriser en vers les correspondances entre les 16 figures, leurs 4 éléments et leurs planètes régentes.",
              "Distinguer les figures de lumière et de vérité (Bayāḍ, Laḥyān, Nuṣra Dākhila) des figures de contrainte ou de perte.",
              "Vérifier l'accord entre l'heure planétaire du tirage et la planète de la maison questionnée.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Figure lumineuse chantée en tête de l'Urjūza"),
              CourseFigureFocus(9, "Bayada (Al-Bayāḍ) — Symbole de clarté doctrinale et de paix"),
              CourseFigureFocus(11, "Nouhoun (Al-Nuṣra al-Dākhila) — Souveraineté et force royale"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Concordance Planétaire de l'Urjūza Dhāt al-Durar (pp. 28–44)",
              sourceRef = "Ḥāwī al-Akhbār (al-Maqdisī & al-Fāsī), pp. 28–44",
              domain = QuestionDomain.AUTHORITY,
              planetHour = Planet.SUN,
              question = "Harmonie entre l'heure solaire et les figures lumineuses des Awtād",
              motherDotCounts = listOf(5, 6, 6, 6, 6, 6, 5, 5, 5, 5, 6, 6, 5, 6, 5, 6),
              interpretationSteps =
                listOf(
                  "M1 (Adama 1222) et M2 (Nouhoun 2211) manifestent la clarté solaire chantée par al-Fāsī.",
                  "M3 (Kalalaw 1122) et M4 (Bayada 2212) soutiennent l'entreprise avec honneur et loyauté.",
                  "Vérifier dans l'onglet Ḥāwī al-Akhbār la hiérarchie quaternaire des 4 degrés.",
                ),
            ),
          detailedLessons = hawiM1Lessons,
        ),
        CourseModule(
          id = "m4_2_mawazin_relationnels",
          moduleNumber = "Module 4.2",
          bookCitation = "Ḥāwī al-Akhbār wa Kanz al-Asrār — Shaykh Ḥāzim al-Maqdisī",
          chapterRef = "Roues des 4 Mawāzīn & Symétries · pp. 112–113, 144, 148",
          title = "Les 4 Mawāzīn Relationnels, Al-Ḍidd & Miroir Aṣḥāb",
          summary =
            "Aux pages 112–113, 144 et 148 de « Ḥāwī al-Akhbār », l'auteur dévoile comment chaque figure engendre quatre visages relationnels par bascule d'un seul rang élémentaire, plus son Opposé total (Al-Ḍidd) et son Miroir vertical (Aṣḥāb).",
          classicalRules =
            listOf(
              "Naẓar (Regard / Intention, p. 112) = Figure ⊕ Al-Laḥyān (1222), qui inverse uniquement le rang de Feu.",
              "Nuṭq (Parole / Signal, p. 112) = Figure ⊕ Al-Ḥumra (2122), qui inverse uniquement le rang d'Air.",
              "Ittiṣāl (Jonction / Accord, p. 113) = Figure ⊕ Al-Bayāḍ (2212), qui inverse uniquement le rang d'Eau.",
              "Infiṣāl (Séparation / Blocage, p. 113) = Figure ⊕ Al-Inkis (2221), qui inverse uniquement le rang de Terre.",
              "Al-Ḍidd (p. 144) = Figure ⊕ Al-Ṭarīq (1111) et Miroir Aṣḥāb (p. 148) = lecture renversée de Terre vers Feu.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Opérateur du Naẓar (bascule du Feu)"),
              CourseFigureFocus(8, "Lomara (Al-Ḥumra) — Opérateur du Nuṭq (bascule de l'Air)"),
              CourseFigureFocus(9, "Bayada (Al-Bayāḍ) — Opérateur de l'Ittiṣāl (bascule de l'Eau)"),
              CourseFigureFocus(7, "Mangouchi (Al-Inkis) — Opérateur de l'Infiṣāl (bascule de la Terre)"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Calcul des 4 Mawāzīn Relationnels & Miroir Aṣḥāb (pp. 112–148)",
              sourceRef = "Ḥāwī al-Akhbār (al-Maqdisī), pp. 112–113, 144, 148",
              domain = QuestionDomain.HIDDEN_INTENT,
              planetHour = Planet.MERCURY,
              question = "Observer Naẓar, Nuṭq, Ittiṣāl, Infiṣāl et Al-Ḍidd à partir de M1",
              motherDotCounts = listOf(6, 5, 6, 6, 6, 6, 5, 6, 5, 6, 5, 5, 6, 5, 5, 5),
              interpretationSteps =
                listOf(
                  "M1 (Ousmana 2121) engendre par Naẓar (⊕ 1222) = Inzan, par Nuṭq (⊕ 2122) = Ḥumra/Tarīq-air, par Ittiṣāl (⊕ 2212) = Nuṣra Dākhila.",
                  "Son Opposé total (Al-Ḍidd ⊕ 1111) donne Qabḍ Khārij (1212), inversant flux entrant et sortant.",
                  "Inspecter chaque maison du Takht pour consulter automatiquement ses 4 Mawāzīn relationnels.",
                ),
            ),
          detailedLessons = hawiM2Lessons,
        ),
        CourseModule(
          id = "m4_3_deux_armees_hierarchie",
          moduleNumber = "Module 4.3",
          bookCitation = "Ḥāwī al-Akhbār wa Kanz al-Asrār — Shaykh Ḥāzim al-Maqdisī",
          chapterRef = "Al-ʿAskarayn & Hiérarchie Quaternaire · pp. 61–62, 114–115, 155",
          title = "Règle des Deux Armées (Al-ʿAskarayn) & Hiérarchie Quaternaire",
          summary =
            "Les pages 114–115 et 155 de « Ḥāwī al-Akhbār » exposent la confrontation des deux armées issues des paires fondatrices (M1⊕M2 contre M7⊕M8), pondérée selon la hiérarchie quaternaire à 4 degrés de force (Qawiyya / Mumtazija) ainsi que les correspondances anatomiques (pp. 61–62).",
          classicalRules =
            listOf(
              "Camp A = M1 ⊕ M2 (l'état et la ressource du demandeur) face à Camp B = M7 ⊕ M8 (l'adversaire et son appui).",
              "Hiérarchie Quaternaire (p. 155) : Fortes Fastes (Adama, Ousmana, Nouhoun, Malidjou) > Mixtes Fastes > Mixtes Défavorables > Fortes Défavorables.",
              "Présence d'Al-Jamāʿa (Moussa) entre les deux armées : annonce armistice, match nul ou partage équilibré.",
              "Anatomie (Taskīn al-Aʿḍāʾ, pp. 61–62) : relie chaque figure à une région du corps pour localiser douleurs et signes.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Rang 1 des Fortes Fastes (Qawiyya)"),
              CourseFigureFocus(12, "Malidjou (Al-ʿAtaba al-Dākhila) — Rang 1 des Fortes Fastes"),
              CourseFigureFocus(4, "Moussa (Al-Jamāʿa) — Signal de trêve ou d'égalité entre les deux camps"),
              CourseFigureFocus(3, "Inzan (Qabḍ al-Khārij) — Rang 4 (perte ou recul d'une armée)"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Confrontation Al-ʿAskarayn (M1⊕M2 vs M7⊕M8, p. 114 & p. 155)",
              sourceRef = "Ḥāwī al-Akhbār (al-Maqdisī), pp. 114–115 & 155",
              domain = QuestionDomain.CONTEST,
              planetHour = Planet.JUPITER,
              question = "Comparaison des Deux Armées selon la hiérarchie quaternaire",
              motherDotCounts = listOf(5, 6, 6, 6, 6, 5, 6, 5, 6, 5, 6, 6, 5, 6, 5, 6),
              interpretationSteps =
                listOf(
                  "Camp A réunit M1 (Adama, Forte Faste) et M2 (Ousmana, Forte Faste) -> combinaison M9 = M1⊕M2.",
                  "Camp B oppose M3 (Lomara, Forte Défavorable) et M4 (Inzan, Forte Défavorable), prolongé en M7⊕M8.",
                  "Ouvre dans le Takht complet pour comparer les points de la hiérarchie quaternaire de Ḥāwī al-Akhbār.",
                ),
            ),
          detailedLessons = hawiM3Lessons,
        ),
      ),
  )

val ms2380CourseBook: CourseBook =
  CourseBook(
    id = "book_5_ms2380",
    orderIndex = 6,
    levelBadge = "Cours N° 6 · Manuscrit MS 2380",
    frenchTitle = "Kitāb fī ʿIlm al-Raml wa-l-Aḥkām (MS 2380)",
    arabicTitle = "كتاب في علم الرمل والأحكام — مخطوط 2380",
    author = "Al-Ḥājj Maḥmūd Muḥammad Yūnus al-Makkī al-Rūmānī",
    editionOrManuscript = "Manuscrit daté de 1091 H / 1680 (Université du Roi Saoud)",
    totalPagesOrFolios = "68 feuillets (76 pages PDF)",
    accentElement = Element.FIRE,
    overview =
      "Copie précieuse achevée en 1091 H à La Mecque, « Kitāb fī ʿIlm al-Raml wa-l-Aḥkām (MS 2380) » marie la doctrine binaire Wujūd wa-l-ʿAdam (présence/absence), la description des 16 figures par maison, des cas pratiques datés (p. 36 PDF) et les échelles temporelles d'Al-Ṭarābulusī.",
    modules =
      listOf(
        CourseModule(
          id = "m5_1_wujud_16_maisons",
          moduleNumber = "Module 5.1",
          bookCitation = "Kitāb fī ʿIlm al-Raml wa-l-Aḥkām (MS 2380) — Al-Rūmānī (1091 H)",
          chapterRef = "Feuillets 13, 28–36",
          title = "Wujūd wa-l-ʿAdam & Les 16 Figures dans chaque Maison",
          summary =
            "Dans les feuillets 13 et 28–36 du manuscrit MS 2380, al-Rūmānī analyse chaque figure selon le principe binaire d'Existence (Wujūd / Fard actif) ou d'Absence (ʿAdam / Zawj fermé) et expose le verdict des 16 figures lorsqu'elles entrent dans les maisons 1 à 16.",
          classicalRules =
            listOf(
              "Wujūd (1 point impair) manifeste la présence, l'énergie rayonnante et la lumière (Nūr al-Raml) ; ʿAdam (2 points pairs) marque la réserve ou le caché.",
              "Figures pleines vs figures vides : repérer l'équilibre des points ouverts sur les 4 rangs.",
              "Lecture par maison (ff. 28–36) : une même figure Faste devient ambivalente en Maison 6 (maladie), 8 (angoisse) ou 12 (ennemis).",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(16, "Taliki (Al-Ṭarīq) — Quatre rangs en Wujūd (1111) : ouverture pure"),
              CourseFigureFocus(4, "Moussa (Al-Jamāʿa) — Quatre rangs en ʿAdam (2222) : réceptivité totale"),
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Feu en Wujūd dominant trois rangs en ʿAdam"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Lecture Wujūd / ʿAdam à travers les 16 Maisons (MS 2380, ff. 28–36)",
              sourceRef = "MS 2380 (Al-Rūmānī, 1091 H), ff. 13 & 28–36",
              domain = QuestionDomain.GENERAL,
              planetHour = Planet.JUPITER,
              question = "Suivre la proportion Wujūd (impair) vs ʿAdam (pair) dans le thème",
              motherDotCounts = listOf(5, 5, 5, 5, 6, 6, 6, 6, 5, 6, 6, 6, 5, 6, 5, 6),
              interpretationSteps =
                listOf(
                  "M1 (Taliki 1111) incarne le pur Wujūd rayonnant face à M2 (Moussa 2222) qui incarne le ʿAdam réceptif.",
                  "M3 (Adama 1222) et M4 (Ousmana 2121) tempèrent lumière et conservation matérielle.",
                  "Vérifier dans chaque maison comment M6, M8 ou M12 modulent la portée d'une figure.",
                ),
            ),
          detailedLessons = ms2380M1Lessons,
        ),
        CourseModule(
          id = "m5_2_cas_pratiques_p36",
          moduleNumber = "Module 5.2",
          bookCitation = "Kitāb fī ʿIlm al-Raml wa-l-Aḥkām (MS 2380) — Al-Rūmānī (1091 H)",
          chapterRef = "Cas documentés du manuscrit · Page 36 PDF",
          title = "Cas pratiques datés : Duel Luṭfī vs Sulaymān & Consultations",
          summary =
            "La page 36 du manuscrit MS 2380 conserve plusieurs thèmes historiques datés réellement tirés par le copiste, notamment la confrontation « Muḥammad Luṭfī contre Muḥammad Sulaymān » ainsi que les consultations de « ʿĀʾisha », « Ḥasan al-Zaynī » et « ʿAbd al-Munʿim ».",
          classicalRules =
            listOf(
              "Inscrire toujours le nom des parties ou du demandeur avant de frapper le sable (ancrage de la Niyya).",
              "Observer comment le manuscrit applique l'arbitrage croisé M1 ⊕ M10 face à M7 ⊕ M4 (méthode d'Al-Zanātī).",
              "Conserver la date et le contexte observé après coup afin de vérifier la justesse du pronostic.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(10, "Kalalaw (Al-Nuṣra al-Khārija) — Dynamique de victoire extérieure en duel"),
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Maintien des acquis du premier camp"),
              CourseFigureFocus(6, "Solomana (Al-ʿUqla) — Blocage ou défaite d'un camp entravé"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Cas d'archive du MS 2380 : Confrontation Luṭfī vs Sulaymān",
              sourceRef = "MS 2380 (Al-Rūmānī, 1091 H), p. 36 PDF",
              domain = QuestionDomain.CONTEST,
              planetHour = Planet.JUPITER,
              question = "Reconstitution pédagogique : Camp Luṭfī face à Camp Sulaymān (MS 2380)",
              motherDotCounts = listOf(5, 5, 6, 6, 6, 5, 6, 5, 5, 6, 6, 5, 6, 5, 5, 6),
              interpretationSteps =
                listOf(
                  "M1 (Kalalaw 1122) et M2 (Ousmana 2121) placent le premier nom en offensive gagnante.",
                  "M3 (Solomana 1221) et M4 (Ijtimāʿ 2112) décrivent un adversaire hésitant entre retenue et compromis.",
                  "Ouvre dans le Takht complet pour comparer l'arbitrage croisé d'Al-Zanātī (M1⊕M10 vs M7⊕M4).",
                ),
            ),
          detailedLessons = ms2380M2Lessons,
        ),
        CourseModule(
          id = "m5_3_tarabulusi_qirat_geographie",
          moduleNumber = "Module 5.3",
          bookCitation = "Kitāb fī ʿIlm al-Raml wa-l-Aḥkām (MS 2380) — Al-Rūmānī (1091 H)",
          chapterRef = "Feuillets 53–54",
          title = "Échelles d’Al-Ṭarābulusī, Taskīn al-Qīrāṭ & Géographie",
          summary =
            "Aux feuillets 53 et 54 du MS 2380, al-Rūmānī regroupe l'échelle temporelle attribuée à al-Ṭarābulusī (de 1 jour en M1 jusqu'à 10 ans en M15), le calcul des poids et mesures (Taskīn al-Qīrāṭ) et l'orientation géographique des villes et régions (Taskīn al-Madīna).",
          classicalRules =
            listOf(
              "Échelle d'Al-Ṭarābulusī (f. 53) : graduée de M1 (1 jour / immédiat) jusqu'à M15 (cycles longs pluriannuels).",
              "Nār al-Nār dans MS 2380 : vérification des quatre éléments purs pour apprécier l'intensité d'un phénomène.",
              "Taskīn al-Madīna (f. 54) : relier chaque figure à une direction cardinale et à une contrée.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(14, "Djanfa Almami (Al-Jūdla) — Délai très bref en maison rapide"),
              CourseFigureFocus(7, "Mangouchi (Al-Inkis) — Prolongation lente et cycles de plusieurs années"),
              CourseFigureFocus(15, "Badra Ali (Al-Ijtimāʿ) — Clé des durées longues en M15"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Échelle d'Al-Ṭarābulusī & Directions Cardinale (MS 2380, ff. 53–54)",
              sourceRef = "MS 2380 (Al-Rūmānī, 1091 H), ff. 53–54",
              domain = QuestionDomain.TRAVEL,
              planetHour = Planet.SATURN,
              question = "Estimer la durée de réalisation (jours, mois ou années) et la direction",
              motherDotCounts = listOf(5, 5, 6, 5, 6, 6, 6, 5, 6, 5, 5, 6, 5, 6, 5, 6),
              interpretationSteps =
                listOf(
                  "M1 (Djanfa Almami 1121) et M2 (Mangouchi 2221) opposent impulsion rapide et rétention terrestre.",
                  "M3 (Ijtimāʿ 2112) et M4 (Ousmana 2121) ancrent les délais dans les mois ou les saisons.",
                  "Lire les durées estimées et l'orientation géographique de Taskīn al-Madīna dans le Takht complet.",
                ),
            ),
          detailedLessons = ms2380M3Lessons,
        ),
      ),
  )

val ms2405CourseBook: CourseBook =
  CourseBook(
    id = "book_6_ms2405",
    orderIndex = 7,
    levelBadge = "Cours N° 7 · Manuscrit MS 2405",
    frenchTitle = "Kitāb fī ʿIlm al-Raml (Compilation MS 2405)",
    arabicTitle = "كتاب في علم الرمل — مخطوط 2405",
    author = "Compilation mecquoise (al-Zanātī, Ṭumṭum, al-Barbarī, al-Bastī, al-Ḥabashī)",
    editionOrManuscript = "Manuscrit MS 2405 (Bibliothèque du Roi Saoud, Riyad)",
    totalPagesOrFolios = "72 pages PDF",
    accentElement = Element.WATER,
    overview =
      "Anthologie de terrain réunissant les maîtres al-Zanātī, Ṭumṭum al-Hindī, Khalaf al-Barbarī, al-Ṭarābulusī, Muḥammad al-Bastī (ff. 124–125) et Abū Saʿīd al-Ḥabashī. Elle brille par ses tables de nombres triangulaires (1..136), sa roue des 28 manses lunaires, les 7 Climats et plus de 20 questions pratiques.",
    modules =
      listOf(
        CourseModule(
          id = "m6_1_triangulaires_manses",
          moduleNumber = "Module 6.1",
          bookCitation = "Kitāb fī ʿIlm al-Raml (Manuscrit MS 2405)",
          chapterRef = "Tables finales & Roue lunaire · pp. 67–71 PDF",
          title = "Nombres Triangulaires (1..136) & Roue des 28 Manses Lunaires",
          summary =
            "Les dernières pages du manuscrit MS 2405 (pp. 67–71 PDF) renferment des tables géométriques rares associant les 16 figures à la suite des nombres triangulaires (1, 3, 6, 10, ..., 136) ainsi qu'une roue complète des 28 manses lunaires (Manāzil al-Qamar) et des 12 signes.",
          classicalRules =
            listOf(
              "Suite triangulaire (pp. 67–68 PDF) : associer chaque rang 1..16 au nombre triangulaire n(n+1)/2 jusqu'à 136 pour les comptes d'âge, de biens ou de quantités.",
              "Roue des 28 Manses Lunaires (pp. 69–71 PDF) : faire correspondre les figures aux stations de la Lune (Al-Sharaṭān jusqu'à Baṭn al-Ḥūt).",
              "Articuler l'élément de la figure dominante avec l'élément zodiacal du signe dans la roue.",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(1, "Adama (Al-Laḥyān) — Rang 1 (nombre triangulaire 1) et Taureau/Sagittaire"),
              CourseFigureFocus(4, "Moussa (Al-Jamāʿa) — Figure lunaire reliée aux manses d'Eau (Cancer)"),
              CourseFigureFocus(16, "Taliki (Al-Ṭarīq) — Cheminement cyclique à travers les 28 manses"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Application des Nombres Triangulaires & Manses Lunaires (MS 2405)",
              sourceRef = "MS 2405 (Compilation mecquoise), pp. 67–71 PDF",
              domain = QuestionDomain.WEALTH,
              planetHour = Planet.MOON,
              question = "Calculer la quantité et la manzil lunaire associée à la figure dominante",
              motherDotCounts = listOf(5, 6, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 6, 5, 6, 6),
              interpretationSteps =
                listOf(
                  "M1 (Adama 1222, T1 = 1) et M2 (Moussa 2222) associent stabilité lunaire et croissance collective.",
                  "M3 (Taliki 1111) et M4 (Bayada 2212) rattachent le cycle aux manses d'Eau et de clarté.",
                  "Comparer le rang Taskīn du Juge M15 à la table des nombres triangulaires 1..136.",
                ),
            ),
          detailedLessons = ms2405M1Lessons,
        ),
        CourseModule(
          id = "m6_2_climats_masail",
          moduleNumber = "Module 6.2",
          bookCitation = "Kitāb fī ʿIlm al-Raml (Manuscrit MS 2405)",
          chapterRef = "7 Climats & 20+ Questions Classiques (Masāʾil)",
          title = "Les 7 Climats & les 20 Questions Classiques (Masāʾil)",
          summary =
            "Dans le corps du manuscrit MS 2405, le compilateur passe en revue les 7 Climats géographiques (Iqlīm al-Shām, Rūm, ʿIrāq, Miṣr, Maghrib, Turk, Ḥabash) puis donne des clefs directes pour plus de vingt questions courantes : requêtes, commerce, mercuriales, vol, prisonnier et guerre.",
          classicalRules =
            listOf(
              "Les 7 Climats (Al-Aqālīm al-Sabʿa) : relier la figure directrice à l'une des 7 zones géographiques et planétaires.",
              "Mercuriales & Prix du marché (Al-Asʿār) : les figures Entrantes et de Terre tendent à la hausse ou rétention ; les Sortantes et de Feu accélèrent la baisse.",
              "Requêtes auprès d'un dirigeant (Al-Ḥawāʾij) : juger par le rapport entre M1 (demandeur), M10 (autorité) et M11 (espoir).",
              "Attribution aux maîtres cités : règles spéciales de Muḥammad al-Bastī (ff. 124–125) et d'Abū Saʿīd al-Ḥabashī (f. 124).",
            ),
          featuredFigures =
            listOf(
              CourseFigureFocus(12, "Malidjou (Al-ʿAtaba al-Dākhila) — Succès d'une requête en Maison 10 ou 11"),
              CourseFigureFocus(2, "Ousmana (Qabḍ al-Dākhil) — Hausse ou conservation dans les mercuriales"),
              CourseFigureFocus(3, "Inzan (Qabḍ al-Khārij) — Dépense, perte commerciale ou fuite du voleur"),
            ),
          takhtExample =
            CourseTakhtExample(
              title = "Consultation des Requêtes & Climats (MS 2405, Masāʾil)",
              sourceRef = "MS 2405 (Compilation mecquoise), chapitre des Ḥawāʾij & Climats",
              domain = QuestionDomain.AUTHORITY,
              planetHour = Planet.SUN,
              question = "Accueil d'une requête officielle (M1 vs M10 et appui de M11)",
              motherDotCounts = listOf(6, 5, 5, 5, 5, 6, 6, 6, 6, 5, 6, 5, 6, 6, 5, 5),
              interpretationSteps =
                listOf(
                  "M1 (Malidjou 2111) ouvre la porte devant l'autorité (M10) dès le premier pas.",
                  "M2 (Adama 1222) et M3 (Ousmana 2121) apportent bienveillance et obtention du dû.",
                  "M4 (Nuṣra Dākhila 2211) ancre le résultat final ; observe M10, M11 et M15 sur le Takht complet.",
                ),
            ),
          detailedLessons = ms2405M2Lessons,
        ),
      ),
  )
