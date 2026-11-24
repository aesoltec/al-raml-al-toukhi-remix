package com.example.geomancy

/**
 * Modules 2.5 à 2.8 (Leçons 41 à 80) et fiche complète du traité
 * « Manbaʿ Uṣūl al-Raml » (منبع أصول الرمل — الدرة البهية في العلوم الرملية)
 * de Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī.
 */
internal val toukhiManbaModulesPart2: List<CourseModule> =
  listOf(
    CourseModule(
      id = "m_manba_5_damir_khabiya",
      moduleNumber = "Module 2.5",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 41 à 50 · Ḍamīr, Khabīya & Noms",
      title = "Istikhrāj al-Ḍamīr, l’Objet Caché (Khabīya) & Prénoms",
      summary =
        "Les Leçons 41 à 50 forment l'un des joyaux de « Manbaʿ Uṣūl al-Raml » : al-Ṭūkhī y expose pas à pas la science de l'Istikhrāj al-Ḍamīr (découvrir la pensée que tait le consultant), l'examen de l'objet dissimulé (Khabīya : règne, couleur, usage) et l'extraction des lettres du prénom.",
      classicalRules =
        listOf(
          "Leçons 41–43 (Le Mustawlī du Ḍamīr) : repérer si une figure se répète ≥ 3 fois dans le Takht ou s'établit dans la maison correspondant à son propre rang Taskīn.",
          "Leçons 44–45 (Addition de contrôle) : additionner les rangs Taskīn des figures dominantes ou de M1+M15 et réduire modulo 16 pour désigner la maison abritant le souci secret.",
          "Leçons 46–47 (La Khabīya — objet caché) : l'élément du Témoin ou de la figure du Ḍamīr révèle le règne (Feu = brillant/métal/chaud ; Air = léger/écrit/tissu ; Eau = liquide/blanc/verre ; Terre = pierre/bois/monnaie enfouie).",
          "Leçons 48–50 (Extraction du prénom) : croiser M1, M15 et la figure de l'objet via les lettres Abjad des rangs impairs afin de cerner l'initiale ou la sonorité du nom.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(7, "Mangouchi (Mangouchi (Al-Inkis) · 2221) — Maître des secrets enfouis, des coffres et de la Khabīya"),
          CourseFigureFocus(9, "Bayada (Bayaro (Al-Bayāḍ) · 2212) — Révèle les papiers, objets blancs, argent et pensées limpides"),
          CourseFigureFocus(8, "Lomara (Lomara (Al-Ḥumra) · 2122) — Désigne un objet rouge, métallique ou une préoccupation vive"),
          CourseFigureFocus(2, "Ousmana (Ousmana (Qabḍ al-Dākhil) · 2121) — Objet conservé dans la main, bourse ou épargne"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 41–47 : Dévoilement du Ḍamīr et de la Khabīya",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 41–50",
          domain = QuestionDomain.HIDDEN_INTENT,
          planetHour = Planet.MERCURY,
          question = "Cerner la préoccupation cachée du consultant et la nature de la Khabīya",
          motherDotCounts = listOf(6, 6, 8, 5, 6, 6, 5, 6, 8, 5, 6, 7, 5, 7, 6, 8),
          interpretationSteps =
            listOf(
              "M1 = Mangouchi (2221), M2 = Bayada (2212), M3 = Ousmana (2121), M4 = Kalalaw (1122).",
              "Mangouchi se répète en M1 et M5, tandis que Bayada revient en M2 et M11 : objet ou écrit blanc gardé à l'abri.",
              "Le Juge M15 = Badra Ali (2112) réunit les pistes et M16 = Malidjou (2111) annonce la mise au jour prochaine.",
            ),
        ),
      detailedLessons = manbaL41To50Lessons,
    ),
    CourseModule(
      id = "m_manba_6_ittisal_infisal",
      moduleNumber = "Module 2.6",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 51 à 60 · Ittiṣāl, Infiṣāl & Intiqāl",
      title = "Mouvement des Figures (Intiqāl), Jonction (Ittiṣāl) & Rupture",
      summary =
        "Dans les Leçons 51 à 60 de « Manbaʿ Uṣūl al-Raml », Cheikh al-Ṭūkhī passe de la lecture statique à l'analyse dynamique : comment une figure voyage d'une maison à une autre (Intiqāl), s'unit au but espéré (Ittiṣāl) ou s'en détache (Infiṣāl).",
      classicalRules =
        listOf(
          "Leçons 51–53 (Intiqāl al-Shakl / Translation) : lorsque la figure du Demandeur (M1) ou du Sujet réapparaît dans une maison supérieure, l'intention se déplace concrètement vers ce domaine.",
          "Leçons 54–56 (Ittiṣāl / Jonction) : l'union entre Ṭālib et Maṭlūb se réalise si les deux figures s'harmonisent par élément ou convergent ensemble vers M13, M14 ou M15.",
          "Leçons 57–58 (Mujāwara & Encadrement) : une figure médiane gagne en force si ses deux voisines sont fastes et entrantes ; elle est assiégée entre deux figures néfastes.",
          "Leçons 59–60 (Infiṣāl & Al-Aḍdād) : la présence de figures contraires (opposées par ⊕ Ṭarīq) ou d'une figure Sortante entre M1 et M7 signale séparation ou désaccord.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(15, "Badra Ali (Badra Ali (Al-Ijtimāʿ) · 2112) — Figure par excellence de la jonction (Ittiṣāl) et des retrouvailles"),
          CourseFigureFocus(5, "Lawsana (Lawsana (Al-ʿAtaba al-Khārija) · 1112) — Signal classique de séparation (Infiṣāl) ou sortie"),
          CourseFigureFocus(6, "Solomana (Solomana (Al-ʿUqla) · 1221) — Suspension ou frein empêchant la jonction immédiate"),
          CourseFigureFocus(13, "Tontigui Garya (Tontigui Garya (Al-Naqī al-Khadd) · 1211) — Rapprochement doux et réconciliation des cœurs"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 54–60 : Ittiṣāl (Jonction), Intiqāl & Mujāwara",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 51–60",
          domain = QuestionDomain.GENERAL,
          planetHour = Planet.VENUS,
          question = "Observer la jonction par translation d'une figure entre Demandeur et But",
          motherDotCounts = listOf(6, 5, 5, 6, 5, 6, 5, 5, 5, 6, 6, 6, 6, 5, 6, 6),
          interpretationSteps =
            listOf(
              "M1 = Badra Ali (2112, figure par excellence de l'Ittiṣāl) et M2 = Tontigui Garya (1211, concorde) établissent un climat d'accord.",
              "Observer comment le mouvement Intiqāl transporte les figures fastes des Mères jusque dans les Témoins.",
              "Vérifier l'absence d'Al-Aḍdād entre M1 et M7 avant de confirmer la réconciliation.",
            ),
        ),
      detailedLessons = manbaL51To60Lessons,
    ),
    CourseModule(
      id = "m_manba_7_masail_pratiques",
      moduleNumber = "Module 2.7",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 61 à 70 · Consultations Thématiques",
      title = "Grands Chapitres Pratiques : Voyage, Vol, Santé, Mariage & Procès",
      summary =
        "Les Leçons 61 à 70 de « Manbaʿ Uṣūl al-Raml » constituent le manuel appliqué du géomancien : comment interroger le sable sur le voyageur absent, retrouver un bien dérobé, sonder une maladie ou une grossesse, évaluer un mariage, un négoce ou une affaire devant la justice.",
      classicalRules =
        listOf(
          "Leçons 61–62 (Voyage & Absent) : lire M1 (le voyageur), M9 (la route) et M4 (le pays d'origine) ; des Entrantes en M1/M4 et M15 annoncent un retour sûr et rapide.",
          "Leçons 63–64 (Vol & Objet perdu) : M7 figure la main qui a pris le bien, M2 le bien lui-même ; si M2 se transfère en M1 ou M4, la chose reste proche et récupérable.",
          "Leçons 65–66 (Maladie & Grossesse) : juger la vitalité en comparant M1 (corps), M6 (mal) et M8 (crise) ; pour la grossesse, examiner M5 (l'enfant) et la pureté de M15.",
          "Leçons 67–70 (Mariage, Commerce & Tribunaux) : en mariage, observer l'accord M1/M7 (Tontigui, Nouhoun, Badra Ali) ; en commerce ou procès, comparer M1 face à M7 sous l'arbitrage de M10 (le Juge civil) et M15.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(16, "Taliki (Taliki (Al-Ṭarīq) · 1111) — Direction de la route, départ ou retour du voyageur"),
          CourseFigureFocus(3, "Inzan (Inzan (Qabḍ al-Khārij) · 1212) — Dépense commerciale ou éloignement de l'objet perdu"),
          CourseFigureFocus(13, "Tontigui Garya (Tontigui Garya (Al-Naqī al-Khadd) · 1211) — Harmonie conjugale et issue pacifiée d'un litige"),
          CourseFigureFocus(11, "Nouhoun (Nouhoun (Al-Nuṣra al-Dākhila) · 2211) — Gain de cause devant l'autorité (M10) et prospérité"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 67–70 : Contrat commercial & Décision d'autorité (M1 vs M10)",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 67–70",
          domain = QuestionDomain.WEALTH,
          planetHour = Planet.JUPITER,
          question = "Issue d'une négociation commerciale et appui des appuis officiels",
          motherDotCounts = listOf(6, 5, 5, 7, 6, 7, 6, 5, 8, 6, 5, 5, 5, 6, 8, 6),
          interpretationSteps =
            listOf(
              "M1 = Malidjou (2111), M2 = Ousmana (2121), M3 = Nouhoun (2211), M4 = Adama (1222) : quatuor de Mères entièrement fastes.",
              "M9 = Bayada (2212) et M10 = Tontigui Garya (1211) montrent une négociation honnête et fluide auprès de l'autorité.",
              "Les Témoins Solomana invitent à la patience sur les délais, mais M15 Moussa (2222) et M16 Malidjou (2111) confirment un accord conclu.",
            ),
        ),
      detailedLessons = manbaL61To70Lessons,
    ),
    CourseModule(
      id = "m_manba_8_synthese_arcanes",
      moduleNumber = "Module 2.8",
      bookCitation = "Manbaʿ Uṣūl al-Raml (Al-Durra al-Bahiyya) — Cheikh al-Ṭūkhī",
      chapterRef = "Leçons 71 à 80 · Arcanes, Duel & Maîtrise",
      title = "Temps Propices, Rūḥāniyya, Duel & Synthèse des 80 Leçons",
      summary =
        "Les Leçons 71 à 80 couronnent « Manbaʿ Uṣūl al-Raml » : al-Ṭūkhī y réunit les règles d'horlogerie planétaire (heures propices et temps interdits), les correspondances spirituelles (Rūḥāniyyāt), l'arbitrage des compétitions (Al-Ghālib wa-l-Maghlūb) et la méthode complète de révision d'un Takht.",
      classicalRules =
        listOf(
          "Leçons 71–73 (Temps & Heures du Raml) : respecter les heures planétaires concordantes et s'abstenir lors des moments de confusion ou d'agitation intérieure.",
          "Leçons 74–76 (Rūḥāniyyāt & Tempéraments) : associer chaque figure à sa planète, son jour (Dimanche Soleil à Samedi Saturne), son encens et son invocation contemplative.",
          "Leçons 77–78 (Al-Ghālib wa-l-Maghlūb) : confronter l'axe du premier camp (M1, M2, M10, M13) à l'axe opposé (M7, M8, M4, M14) en vérifiant les répétitions dominantes.",
          "Leçons 79–80 (Synthèse du Maître) : vérifier systématiquement la chaîne M1 → Maison du Sujet → Témoins M13/M14 → Juge M15 → M16 avant de prononcer tout jugement.",
        ),
      featuredFigures =
        listOf(
          CourseFigureFocus(1, "Adama (Adama (Al-Laḥyān) · Jupiter) — Vertu, discernement éthique et bénédiction du maître"),
          CourseFigureFocus(10, "Kalalaw (Kalalaw (Al-Nuṣra al-Khārija) · Soleil) — Élan victorieux en compétition"),
          CourseFigureFocus(14, "Djanfa Almami (Djanfa Almami (Al-Jūdla) · Mars) — Initiative rapide dans le duel"),
          CourseFigureFocus(4, "Moussa (Moussa (Al-Jamāʿa) · Lune) — Modération, nul ou décision portée par le groupe"),
        ),
      takhtExample =
        CourseTakhtExample(
          title = "Leçons 77–80 : Synthèse finale & Duel (Al-Ghālib wa-l-Maghlūb)",
          sourceRef = "Manbaʿ Uṣūl al-Raml (Al-Ṭūkhī), Leçons 77–80",
          domain = QuestionDomain.CONTEST,
          planetHour = Planet.SUN,
          question = "Lecture complète des 16 maisons sur une confrontation équilibrée",
          motherDotCounts = listOf(5, 6, 6, 8, 6, 8, 5, 7, 6, 5, 8, 6, 5, 7, 6, 5),
          interpretationSteps =
            listOf(
              "Camp A ouvre avec M1 = Adama (1222) et M2 = Nouhoun (2211), deux figures solaires/jupitériennes d'assise solide.",
              "Camp B aligne M3 = Lomara (2122) et M4 = Djanfa Almami (1121), figures martiales très combatives.",
              "Les Témoins M13 = Bayada (2212) et M14 = Tontigui Garya (1211) mènent vers un Juge serré M15 = Solomana (1221) : bataille disputée.",
            ),
        ),
      detailedLessons = manbaL71To80Lessons,
    ),
  )

/**
 * Cours N° 2 (Cheikh al-Ṭūkhī) :
 * « Manbaʿ Uṣūl al-Raml / Al-Durra al-Bahiyya fī al-ʿUlūm al-Ramliyya »
 * (منبع أصول الرمل — الدرة البهية في العلوم الرملية) — 80 Leçons complètes en français.
 */
val toukhiManbaCourseBook: CourseBook =
  CourseBook(
    id = "book_2_toukhi_manba",
    orderIndex = 2,
    levelBadge = "Cours N° 2 · 80 Leçons d’Al-Ṭūkhī",
    frenchTitle = "Manbaʿ Uṣūl al-Raml (La Source des Fondements de la Géomancie)",
    arabicTitle = "منبع أصول الرمل (الدرة البهية في العلوم الرملية)",
    author = "Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī",
    editionOrManuscript = "Al-Maktaba al-Thaqāfiyya / Al-Shaʿbiyya, Le Caire & Beyrouth",
    totalPagesOrFolios = "80 Leçons progressives (Thamānūn Darsan)",
    accentElement = Element.AIR,
    overview =
      "Deuxième grand pilier pédagogique de Cheikh al-Ṭūkhī (sous-titré « Al-Durra al-Bahiyya fī al-ʿUlūm al-Ramliyya ») : l'ouvrage déploie en 80 leçons progressives la genèse du point, l'érection du Takht, les grands Tasākīn (Ḥadbaʾ, ʿAlkhad, Sakzaj, Zanātī), l'Istikhrāj al-Ḍamīr, les liaisons Ittiṣāl/Infiṣāl, toutes les consultations de la vie courante et les règles de synthèse.",
    modules = toukhiManbaModulesPart1 + toukhiManbaModulesPart2,
  )
