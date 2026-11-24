package com.example.geomancy

/**
 * Les leçons 41 à 80 complètes (Al-Dars 41 à Al-Dars 80) de
 * « Manbaʿ Uṣūl al-Raml / Al-Durra al-Bahiyya fī al-ʿUlūm al-Ramliyya »
 * de Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī.
 */
internal val manbaL41To50Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 41",
      heading = "Qu'est-ce qu'Istikhrāj al-Ḍamīr (Sonder l'Intention Voilée) ?",
      arabicTerms = "الدرس الحادي والأربعون : حقيقة الضمير ومكانه في التخت",
      body =
        "La science du Ḍamīr permet de nommer le sujet auquel pense secrètement le consultant avant qu'il ne le formule à voix haute, en repérant le foyer énergétique le plus aimanté du thème.",
      keyFormulaOrTable = "Question centrale : quelle maison (1..12) attire le plus de répétitions et d'appels ?",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 42",
      heading = "Méthode du Mustawlī (Figure Répétée ≥ 3 Fois)",
      arabicTerms = "الدرس الثاني والأربعون : استخراج الضمير بالمستولي",
      body =
        "Lorsqu'une même figure apparaît trois fois ou plus dans les 16 maisons, elle devient souveraine (Mustawlī) : sa nature et les numéros des maisons où elle siège dévoilent immédiatement la pensée secrète.",
      keyFormulaOrTable = "Règle : figure répétée ≥ 3 fois → lire ses maisons d'accueil.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 43",
      heading = "Méthode de la Figure en sa Demeure Taskīn",
      arabicTerms = "الدرس الثالث والأربعون : مطابقة الشكل لبيته الأصلي",
      body =
        "Si une figure tombe exactement dans le numéro de maison correspondant à son rang naturel dans le Taskīn al-Zanātī, le sujet du consultant coïncide directement avec la signification de cette maison.",
      keyFormulaOrTable = "Figure k en Maison k → le Ḍamīr porte sur la Maison k.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 44",
      heading = "Somme des Rangs Actifs Réduite Modulo 16",
      arabicTerms = "الدرس الرابع والأربعون : جمع النقط الفردية وإسقاطها على البيوت",
      body =
        "Additionner les points impairs (Fard) des quatre Mères ou des figures directrices puis diviser par 16 : le reste désigne la maison du Ḍamīr.",
      keyFormulaOrTable = "Reste = (Σ points actifs) mod 16 (si 0 → Maison 16).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 45",
      heading = "La Voie des Témoins & du Mīzān (M13, M14, M15)",
      arabicTerms = "الدرس الخامس والأربعون : كشف الضمير من الشاهدين والميزان",
      body =
        "Confronter M13 (préoccupation passée/intime), M14 (direction vers laquelle tend l'esprit) et M15 (synthèse) pour trancher entre deux hypothèses de Ḍamīr.",
      keyFormulaOrTable = "M13 + M14 → foyer intérieur et objet espéré.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 46",
      heading = "La Khabīya (Objet Caché) : Déterminer le Règne & l'Élément",
      arabicTerms = "الدرس السادس والأربعون : الخبيئة ومعرفة جنسها من العناصر",
      body =
        "Pour identifier un objet tenu caché dans la main ou la pièce : Feu = métal, brillant ou travaillé au feu ; Air = léger, papier, tissu, plume ; Eau = liquide, verre, perle ou blanc ; Terre = pierre, bois, graine ou monnaie.",
      keyFormulaOrTable = "Élément dominant de la figure du Ḍamīr = Règne matériel de la Khabīya.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 47",
      heading = "Couleur, Forme & Emplacement de la Khabīya",
      arabicTerms = "الدرس السابع والأربعون : لون الخبيئة وشكلها وموضعها",
      body =
        "La couleur suit la planète régente (Rouge=Mars/Ḥumra, Blanc=Lune/Bayāḍ, Jaune/Doré=Soleil/Nuṣra Khārija, Noir/Sombre=Saturne/Inkis, Vert=Vénus/Naqī al-Khadd) ; les rangs ouverts/fermés indiquent rond, allongé ou creux.",
      keyFormulaOrTable = "M4 = lieu bas/sol · M10 = lieu élevé · M7 = main d'autrui · M2 = bourse/poche.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 48",
      heading = "Extraction de l'Initiale & des Lettres du Prénom (M1 & M15)",
      arabicTerms = "الدرس الثامن والأربعون : استخراج حروف الاسم من الميزان والطالع",
      body =
        "Récolter les lettres attribuées dans le Taskīn al-Ḥurūf aux figures de M1, M15 et de la maison du Ḍamīr afin d'obtenir les consonnes directrices du prénom recherché.",
      keyFormulaOrTable = "Lettres(M1) + Lettres(M15) + Lettres(Mustawlī).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 49",
      heading = "Le Calcul du Nom par les Points Impairs & la Voie Abjad",
      arabicTerms = "الدرس التاسع والأربعون : طريقة الأعداد لاستخراج الاسم",
      body =
        "Attribuer leur poids numérique aux rangs impairs des figures clés (Feu, Air, Eau, Terre selon le tableau Abjad) pour estimer le total numérique du prénom.",
      keyFormulaOrTable = "Contrôle par reste modulo 9 et modulo 28.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 50",
      heading = "Vérification de Concordance Finale du Ḍamīr",
      arabicTerms = "الدرس الخمسون : مطابقة الضمير قبل الإخبار",
      body =
        "Ne jamais prononcer un Ḍamīr ni une Khabīya sans que deux méthodes distinctes (par ex. Mustawlī + somme modulo 16) convergent vers le même élément ou la même maison.",
      keyFormulaOrTable = "Double validation obligatoire avant annonce.",
    ),
  )

internal val manbaL51To60Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 51",
      heading = "Intiqāl al-Shakl : Le Voyage d'une Figure dans le Takht",
      arabicTerms = "الدرس الحادي والخمسون : انتقال الشكل بين البيوت",
      body =
        "Lorsque la figure apparue en M1 (Demandeur) se répète ensuite en M7, M10 ou M15, le consultant se transporte littéralement vers cette affaire ou l'attire à lui.",
      keyFormulaOrTable = "Intiqāl M1 → M_k : engagement direct de la personne dans la maison k.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 52",
      heading = "Intiqāl du Maṭlūb (Sujet Espéré) vers le Ṭālib (M1)",
      arabicTerms = "الدرس الثاني والخمسون : انتقال المطلوب إلى الطالب",
      body =
        "Si la figure occupant la maison du sujet demandé réapparaît en M1, M2 ou M13, l'objet recherché vient de lui-même vers le consultant sans effort excessif.",
      keyFormulaOrTable = "Signe d'obtention spontanée lorsque le Sujet voyage vers M1.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 53",
      heading = "Intiqāl vers les Maisons Cadentes (Affaiblissement)",
      arabicTerms = "الدرس الثالث والخمسون : انتقال الدليل إلى السواقط",
      body =
        "Si le significateur principal glisse uniquement vers M6, M8 ou M12 sans revenir dans les Témoins, l'affaire perd son élan ou rencontre une entrave cachée.",
      keyFormulaOrTable = "Glissement vers M6/M8/M12 = prudence ou report.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 54",
      heading = "Al-Ittiṣāl (Jonction Directe & Rapprochement)",
      arabicTerms = "الدرس الرابع والخمسون : الاتصال السعيد بين الأشكال",
      body =
        "Il y a Ittiṣāl lorsque les figures de M1 et de la maison questionnée se reproduisent côte à côte, ou s'unissent par addition binaire en produisant une figure Faste en M13, M14 ou M15.",
      keyFormulaOrTable = "Ittiṣāl fort : M1 et Sujet convergent vers une figure Faste/Entrante.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 55",
      heading = "Ittiṣāl par Médiation d'une Figure Tierce (Naql al-Nūr)",
      arabicTerms = "الدرس الخامس والخمسون : نقل النور بين البيوت المتباعدة",
      body =
        "Même si M1 et M7 diffèrent, si une figure tierce commune (ex. Ijtimāʿ ou Naqī al-Khadd) relie successivement les deux maisons via les Nièces ou Témoins, un intermédiaire réalisera l'accord.",
      keyFormulaOrTable = "Médiateur = figure récurrente reliant le pôle A et le pôle B.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 56",
      heading = "Ittiṣāl par Harmonie Élémentaire & Aspects",
      arabicTerms = "الدرس السادس والخمسون : الاتصال بالتوافق العنصري",
      body =
        "Vérification de l'union par communauté d'élément (Feu+Feu, Feu+Air, Eau+Terre) et par trigone (4 maisons d'écart) ou sextile (2 maisons d'écart).",
      keyFormulaOrTable = "Trigone (Tathlīth) & Sextile (Tasdīs) favorisent l'accord.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 57",
      heading = "Al-Mujāwara : Voisinage Faste & Soutien Latéral",
      arabicTerms = "الدرس السابع والخمسون : المجاورة السعيدة",
      body =
        "Une figure faible ou neutre placée entre deux voisines fastes et entrantes est élevée et protégée (« encadrement heureux »).",
      keyFormulaOrTable = "Saʿd — [M_k] — Saʿd : relèvement de la maison médiane.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 58",
      heading = "Al-Ḥiṣār : L'Encerclement / Siège par Deux Figures Néfastes",
      arabicTerms = "الدرس الثامن والخمسون : الحصار بالنحوس",
      body =
        "À l'inverse, une figure faste prise en étau entre deux figures néfastes ou contraires perd la plus grande part de son secours jusqu'à ce qu'elle s'en dégage dans les Témoins.",
      keyFormulaOrTable = "Naḥs — [M_k] — Naḥs : blocage temporaire ou pression extérieure.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 59",
      heading = "Al-Infiṣāl (Rupture, Séparation & Éloignement)",
      arabicTerms = "الدرس التاسع والخمسون : الانفصال وعلاماته",
      body =
        "L'Infiṣāl se manifeste lorsque des figures Sortantes (ʿAtaba Khārija, Qabḍ Khārij, Nuṣra Khārija) ou des éléments incompatibles (Feu vs Eau) séparent le consultant de son but.",
      keyFormulaOrTable = "Infiṣāl = Sortantes interposées + Témoin dissonant.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 60",
      heading = "Les Figures Contraires (Al-Aḍdād : ⊕ Al-Ṭarīq)",
      arabicTerms = "الدرس الستون : الأضداد في التخت وعلاقتها بالانفصال",
      body =
        "Lorsque M1 et M7 (ou M13 et M14) sont des figures exactement opposées rang par rang (F₁ ⊕ F₂ = Al-Ṭarīq 1111), elles forment un couple d'Aḍdād exprimant opposition frontale ou renversement complet.",
      keyFormulaOrTable = "Exemples d'Aḍdād : Laḥyān ↔ Jūdla · Nuṣra D. ↔ Nuṣra Kh. · Bayāḍ ↔ ʿUqla.",
    ),
  )

internal val manbaL61To70Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 61",
      heading = "Consultation sur le Voyage : Départ, Sécurité & Route (M9)",
      arabicTerms = "الدرس الحادي والستون : أحكام السفر والطريق",
      body =
        "Observer M1 (le voyageur), M9 (la route lointaine), M3 (les étapes proches) et M15 : des figures mobiles et lumineuses annoncent un trajet fluide ; Inkis ou ʿUqla mettent en garde contre retards et pannes.",
      keyFormulaOrTable = "Axe du voyage : M1 + M9 + M15.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 62",
      heading = "Nouvelles de l'Absent & Date de son Retour (M4 & Entrantes)",
      arabicTerms = "الدرس الثاني والستون : الغائب وعودته إلى وطنه",
      body =
        "Pour savoir si un absent est en bonne santé et s'il reviendra bientôt : comparer la force de M1 à M8 (danger) puis compter les figures Entrantes en M1, M4 (foyer) et M15 qui ramènent l'absent chez lui.",
      keyFormulaOrTable = "Entrantes en M1/M4 + absence de Naḥs en M8 = retour sain et sauf.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 63",
      heading = "Vol & Objet Perdu : Identifier le Voleur (M7) & le Bien (M2)",
      arabicTerms = "الدرس الثالث والستون : المسروق والسارق (البيت السابع والثاني)",
      body =
        "M2 représente l'objet dérobé ou égaré ; M7 désigne la personne qui l'a pris. Si le voleur (M7) appartient aux maisons proches (M1..M4), il vient de l'entourage immédiat ; s'il siège au loin (M9), c'est un étranger.",
      keyFormulaOrTable = "M2 = Objet · M7 = Voleur · Direction élémentaire de M7 = Lieu de fuite.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 64",
      heading = "L'Objet Sera-t-il Retrouvé ? Restitution & Preuves",
      arabicTerms = "الدرس الرابع والستون : هل يرجع المسروق أم يضيع؟",
      body =
        "Si M2 ou M7 se transfèrent vers M1, M4 ou M15 en figures Entrantes ou Fixes, le bien reste à portée et sera restitué ; si Sortantes dominent M2, M8 et M15, l'objet est déjà dispersé ou vendu.",
      keyFormulaOrTable = "Entrantes (M2/M7 → M1/M4) = récupération · Sortantes (M8/M12) = perte.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 65",
      heading = "Santé & Maladie : Vitalité (M1), Cause (M6) & Gravité (M8)",
      arabicTerms = "الدرس الخامس والستون : المريض وطبيعة العلة",
      body =
        "M1 décrit la vitalité du malade, M6 la nature chaude, froide, sèche ou humide de l'affection, et M8 le degré de gravité. La victoire des figures fastes et d'Eau/Air sur M8 annonce prompt rétablissement.",
      keyFormulaOrTable = "M1 = Énergie · M6 = Diagnostic humoral · M8/M15 = Pronostic de guérison.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 66",
      heading = "Grossesse, Naissance & Descendance (M5 & M11)",
      arabicTerms = "الدرس السادس والستون : الحمل والذرية (البيت الخامس)",
      body =
        "Interroger M5 (l'enfant) et son rapport avec M1 (la mère) et M15 : les figures Entrantes et fécondes (Nuṣra Dākhila, Bayāḍ, Qabḍ Dākhil, Ijtimāʿ) promettent sécurité et naissance heureuse.",
      keyFormulaOrTable = "M5 Faste + Entrante = sécurité de la mère et de l'enfant.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 67",
      heading = "Mariage, Fiançailles & Harmonie Conjugale (M1 vs M7)",
      arabicTerms = "الدرس السابع والستون : الزواج والوفاق بين الأول والسابع",
      body =
        "M1 figure le prétendant et M7 le partenaire : observer si leurs figures se lient par Ittiṣāl, Naqī al-Khadd, Nuṣra Dākhila ou Ijtimāʿ, et si M5 (joie/enfants) et M15 scellent la durée de l'union.",
      keyFormulaOrTable = "Accord M1 ↔ M7 + M5/M15 lumineux = union durable ; Ḥumra/Inkis en M7 = discorde.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 68",
      heading = "Négoce, Commerce & Gains Matériels (M2 & M10)",
      arabicTerms = "الدرس الثامن والستون : التجارة والربح والخسارة",
      body =
        "M2 montre le capital présent, M10 le volume du marché et le profit espéré, M11 la clientèle et les soutiens. Les figures Entrantes (Qabḍ Dākhil, Nuṣra Dākhila, ʿAtaba Dākhila) annoncent bénéfice net.",
      keyFormulaOrTable = "Gain = Entrantes en M2, M10, M11 et M15 · Dépense = Qabḍ Khārij / ʿAtaba Khārija.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 69",
      heading = "Trésors Enfouis & Puits d'Eau Souterrains (M4)",
      arabicTerms = "الدرس التاسع والستون : الدفائن والكنوز والمياه الجوفية",
      body =
        "M4 régit les profondeurs de la terre : pour les eaux souterraines, rechercher les figures d'Eau (Bayāḍ, Ḥumra/Māʾ selon table) en M4 et M15 ; pour les biens enfouis, vérifier la présence d'Inkis, Qabḍ Dākhil et la pureté du signe.",
      keyFormulaOrTable = "M4 Terre/Inkis = profondeur sèche · M4 Eau/Bayāḍ = source abondante.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 70",
      heading = "Procès, Requêtes & Jugements devant l'Autorité (M10 & M15)",
      arabicTerms = "الدرس السبعون : الدعاوى والقضاء وطلب الحوائج من السلطان",
      body =
        "M10 représente le juge, le magistrat ou le dirigeant ; M1 le plaignant ; M7 la partie adverse ; M15 la sentence. Si M1 s'unit à M10 ou M11 par figures fastes et entrantes, le plaignant obtient gain de cause.",
      keyFormulaOrTable = "Axe judiciaire : (M1 + M10 + M11) face à (M7 + M12), sous la balance de M15.",
    ),
  )

internal val manbaL71To80Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 71",
      heading = "Les Sept Jours de la Semaine & Leur Maître Planétaire",
      arabicTerms = "الدرس الحادي والسبعون : الأيام السبعة وكواكبها",
      body =
        "Association classique : Dimanche (Soleil), Lundi (Lune), Mardi (Mars), Mercredi (Mercure), Jeudi (Jupiter), Vendredi (Vénus), Samedi (Saturne). La première heure diurne après le lever du soleil appartient au maître du jour.",
      keyFormulaOrTable = "Ordre chaldéen des heures : Saturne → Jupiter → Mars → Soleil → Vénus → Mercure → Lune.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 72",
      heading = "Heures Planétaires Propices & Accord avec la Question",
      arabicTerms = "الدرس الثاني والسبعون : اختيار الساعات السعيدة للضرب",
      body =
        "Choisir l'heure de Jupiter ou du Soleil pour l'honneur et le négoce, Vénus pour l'union, Mercure pour les écrits et le calcul ; réserver Saturne ou Mars aux questions de conflit ou de séparation avec circonspection.",
      keyFormulaOrTable = "Harmonie Heure + Question = clarté accrue du Takht.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 73",
      heading = "Temps Interdits & Temps Déconseillés du Raml",
      arabicTerms = "الدرس الثالث والسبعون : الأوقات المنهي عنها والمكروهة في الرمل",
      body =
        "S'abstenir au zénith solaire (Zawāl), aux crépuscules troubles, durant la lune vide de course ou aux jours lunaires réputés difficiles (notamment 3, 5, 13, 16, 21, 25, 26 et dernier mercredi), ainsi qu'en état de colère.",
      keyFormulaOrTable = "Protection intégrée dans Al-Raml Al-Toukhi : blocage des temps interdits activé par défaut.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 74",
      heading = "Rūḥāniyyāt : Dimension Spirituelle & Invocations des Noms Divins",
      arabicTerms = "الدرس الرابع والسبعون : الروحانيات والأذكار المرافقة للعلم",
      body =
        "Al-Ṭūkhī rappelle que les figures ne sont que des lettres d'un livre créé : la clairvoyance réelle vient du dhikr, de la rectitude morale et de l'invocation d'Al-Fattāḥ, Al-ʿAlīm et Al-Khabīr.",
      keyFormulaOrTable = "Règle éthique : ni superstition, ni arrogance ; le Raml est un miroir de consultation.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 75",
      heading = "Encens, Couleurs & Tempéraments des Sept Planètes",
      arabicTerms = "الدرس الخامس والسبعون : البخور والألوان والطبائع الكوكبية",
      body =
        "Tableau traditionnel complet attribuant aux heures et aux figures leurs correspondances symboliques (couleurs, métaux, parfums naturels) héritées des traités anciens.",
      keyFormulaOrTable = "Soleil=Or/Jaune · Lune=Argent/Blanc · Jupiter=Étain/Vert-Bleu · Mars=Fer/Rouge · Vénus=Cuivre · Mercure=Vif-argent · Saturne=Plomb/Noir.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 76",
      heading = "Répétitions des Figures & Loi du Souverain (Al-Mustawlī)",
      arabicTerms = "الدرس السادس والسبعون : حكم التكرار والمستولي على التخت",
      body =
        "Compter combien de fois chaque figure apparaît parmi les 16 maisons : une figure unique domine rarement ; 2 apparitions créent un accent ; 3 ou 4 apparitions imposent la loi du Mustawlī sur tout le thème.",
      keyFormulaOrTable = "3+ apparitions = Mustawlī · 4 apparitions dans M1..M4 = arrêt irrévocable.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 77",
      heading = "Al-Ghālib wa-l-Maghlūb : Duel par les Quatre Pôles (Camp A vs B)",
      arabicTerms = "الدرس السابع والسبعون : الغالب والمغلوب بين الفريقين",
      body =
        "Comparaison méthodique des maisons du premier camp {M1, M2, M10, M13} face au second camp {M7, M8, M4, M14}, pondérée par l'honneur (M10), l'espoir (M11), les obstacles (M12) et les points ouverts.",
      keyFormulaOrTable = "Axes du Duel : Camp A {M1, M2, M10, M13} vs Camp B {M7, M8, M4, M14}.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 78",
      heading = "Arbitrage du Mīzān (M15), de M16 & Cas de Partage / Match Nul",
      arabicTerms = "الدرس الثامن والسبعون : حكم الميزان والتعادل (الجماعة والطريق)",
      body =
        "Lorsque les deux plateaux s'équilibrent et que M15 porte une figure symétrique comme Al-Jamāʿa (2222), Al-Ṭarīq (1111) ou Al-Ijtimāʿ (2112), ou si l'écart pondéré reste sous le seuil d'équilibre, le thème annonce partage ou match nul.",
      keyFormulaOrTable = "Seuil d'égalité : |Score A − Score B| ≤ seuil + Jamāʿa/Ijtimāʿ en M15 → Nul.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 79",
      heading = "Éthique du Géomancien, Vérification & Conservation des Archives",
      arabicTerms = "الدرس التاسع والسبعون : أمانة الرمّال وتدوين التجارب",
      body =
        "Consigner fidèlement chaque thème avec sa date, son intitulé exact et l'issue réelle observée par la suite afin d'affiner l'expérience, sans jamais altérer a posteriori le tracé originel.",
      keyFormulaOrTable = "Discipline empirique : archiver → observer le réel → calibrer avec humilité.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 80",
      heading = "Protocole Complet de Lecture du Maître (Les 7 Étapes Finales)",
      arabicTerms = "الدرس الثمانون : المنهج الجامع لقراءة التخت الكامل",
      body =
        "Couronnement des 80 leçons : 1) Contrôler la parité paire de M15 ; 2) Compter les points totaux (seuil 60/90/96) ; 3) Identifier le Mustawlī ; 4) Comparer les 4 Awtād ; 5) Tracer Intiqāl/Ittiṣāl entre M1 et la maison du sujet ; 6) Lire M13 et M14 ; 7) Sceller par M15 et M16.",
      keyFormulaOrTable = "Chaîne magistrale : Parité M15 → Seuil → Mustawlī → Awtād → Ittiṣāl → M13/M14 → M15/M16.",
    ),
  )
