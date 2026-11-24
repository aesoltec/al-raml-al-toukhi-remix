package com.example.geomancy

/**
 * Traductions françaises fidèles des chapitres (Bāb 1 à 40 et Annexes) de
 * « Bulūgh al-Amal fī ʿIlm al-Raml » (بلوغ الأمل في علم الرمل),
 * Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī (Beyrouth, 1411 H / 1991, 98 pp.).
 */
internal val bulughM1Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Bāb 1 · pp. 5–6",
      heading = "Rites préparatoires, Qibla et Verset de l'Heure Nocturne",
      arabicTerms = "طهارة، استقبال القبلة، الفاتحة، الزلزلة، يا فتّاح يا عليم",
      body =
        "Avant de tracer les lignes, le consultant ou le maître doit être en état de pureté rituelle (ṭahāra), se tourner vers la Qibla et éviter toute distraction. Al-Ṭūkhī rapporte le vers classique affirmant la supériorité du silence nocturne : « Wa-fī dujā al-layli bayānan / yunbīka ʿan mā aḍmarta ʿiyānan » (« Dans l'obscurité de la nuit réside une clarté / qui t'informe ouvertement de ce que tu as dissimulé »). Les heures du jour conviennent néanmoins si le cœur est recueilli.",
      keyFormulaOrTable =
        "Récitations prescrites (Bāb 1) : 1re ligne = Al-Fātiḥa · 2e ligne = « Wa-ʿindahu mafātiḥu al-ghayb lā yaʿlamuhā illā huwa… » (Coran VI, 59) · 3e & 4e lignes = Sourate Al-Zalzala · Clôture : « Allāhumma arinī mā huwa al-maknūn fī sirrika al-makhzūn yā Fattāḥ yā ʿAlīm ».",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 1 · pp. 6–7",
      heading = "Tracé des 16 Lignes d'Est en Ouest & Discipline des 3 Tirages",
      arabicTerms = "هيئة الأنامل، إسقاط الأزواج، ثلاث مرات في اليوم",
      body =
        "On trace quatre lignes de points d'Est en Ouest (de droite à gauche) sans jamais compter à l'avance, selon le rythme naturel de la main (ʿalā hayʾat al-anāmil) : la première ligne reste modérée (sans excéder 12 points dans le premier élan), la seconde légèrement plus ample, la troisième un peu plus large encore. On répète pour former 4 groupes de 4 lignes (16 lignes), puis on barre les points deux par deux pour garder Fard (1 point) ou Zawj (2 points). Interdits formels : tracer sans ablution, par dérision ou simple curiosité, sous l'emprise de la colère ou d'une faim aiguë, ou pour plus de trois demandes sérieuses dans une même journée.",
      keyFormulaOrTable =
        "Règle d'or de Bāb 1 : max. 3 tirages par jour · Ne jamais répéter deux fois la même question le même jour · Tracé d'Est en Ouest.",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 1 · pp. 7–8",
      heading = "Extraction des Filles (Banāt), Nièces, Témoins & Parité du Juge",
      arabicTerms = "الأمهات (1–4)، البنات (5–8)، الحفيدات (9–12)، الشاهدان (13–14)، الحاكم/الميزان (15)، عاقبة العاقبة (16)",
      body =
        "Les quatre Mères (M1–M4) posées de droite à gauche engendrent les quatre Filles (M5–M8) par lecture horizontale des rangs : M5 rassemble les quatre têtes (Feu) de M1 à M4, M6 les poitrines (Air), M7 les ventres (Eau) et M8 les pieds (Terre). Par addition binaire rang par rang (1+1→2, 2+2→2, 1+2→1) naissent les Nièces M9 (M1⊕M2), M10 (M3⊕M4), M11 (M5⊕M6), M12 (M7⊕M8), puis les deux Témoins M13 (M9⊕M10) et M14 (M11⊕M12), le Juge M15 (M13⊕M14) et la 16e maison M16 = M15 ⊕ M1 (ʿĀqibat al-ʿāqiba).",
      keyFormulaOrTable =
        "Contrôle impératif de justesse : le Juge M15 (Al-Mīzān) contient toujours un nombre PAIR de points (4, 6 ou 8 points au total). S'il apparaît impair, le calcul comporte une erreur et doit être refait.",
    ),
  )

internal val bulughM2Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Bāb 2 · p. 8",
      heading = "Polarités, Mouvements & Controverse Jūdala / Naqī al-Khadd",
      arabicTerms = "داخل، خارج، ثابت، منقلب · ذكر، أنثى · الجودلة ونقي الخد",
      body =
        "Cheikh al-Ṭūkhī répartit les 16 figures selon quatre mouvements : Entrantes (Qabḍ Dākhil, Nuṣra Dākhila, ʿAtaba Dākhila, Ijtimāʿ), Sortantes (Qabḍ Khārij, Nuṣra Khārija, ʿAtaba Khārija), Fixes (Laḥyān, Jamāʿa, ʿUqla, Inkis, Bayāḍ) et Mobiles (Ḥumra, Naqī al-Khadd, Jūdla, Ṭarīq). Il expose la controverse doctrinale autour de deux figures inclinées : Al-Jūdla (Kawsaj, Mars/Vénus) jugée néfaste ou instable par la majorité, face à Naqī al-Khadd jugée propice mais changeante.",
      keyFormulaOrTable =
        "4 Entrantes (acquisition, retour) · 3 Sortantes (perte, départ) · 5 Fixes (durée, ancrage) · 4 Mobiles (transition rapide).",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 3–4 · pp. 8–9",
      heading = "Awtād, Mā Yalī, Sawāqiṭ & Shawāhid (Les 4 Piliers et Témoins)",
      arabicTerms = "الأوتاد (1،4،7،10)، ما يلي الأوتاد (2،5،8،11)، السواقط (3،6،9،12)، الشواهد",
      body =
        "Les 12 premières maisons se divisent en trois ordres : les 4 Awtād ou Piliers (M1, M4, M7, M10) qui gouvernent le temps présent et la charpente du thème ; les 4 Succédentes ou Mā yalī al-Awtād (M2, M5, M8, M11) qui annoncent le futur proche et la substance ; les 4 Cadentes ou Sawāqiṭ (M3, M6, M9, M12) qui renvoient au passé et à l'affaiblissement. De plus, chaque troisième maison joue le rôle de témoin (Shāhid) en chaîne : 1↔3, 3↔5, 5↔7, 7↔9, 9↔11, 11↔13, 13↔15.",
      keyFormulaOrTable =
        "Awtād (1, 4, 7, 10) = Présent & Force · Succédentes (2, 5, 8, 11) = Futur · Cadentes (3, 6, 9, 12) = Passé · M12-M14-M15-M16 = Les 4 Mawāzīn.",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 5–6 · pp. 9–10",
      heading = "Les 4 Natures Élémentaires, la Khabīʾa, les Humeurs & les Directions",
      arabicTerms = "نار، هواء، ماء، تراب · صفراء، دم، بلغم، سوداء · المشرق والمغرب",
      body =
        "Chaque rang des figures correspond à un élément, une humeur médicale, un règne pour la Khabīʾa (objet caché) et une direction cardinale. Le Feu gouverne l'Est, la bile jaune et tout objet cuit, brillant ou métallique ; l'Air gouverne l'Ouest (ou le Sud selon les tables), le sang et ce qui est suspendu, léger ou écrit ; l'Eau gouverne le Nord, le flegme et les liquides, verres ou lieux humides ; la Terre gouverne le Sud/Est bas, l'atrabile et ce qui est enfoui, pierreux ou ligneux.",
      keyFormulaOrTable =
        "1er rang = Feu (Chaud/Sec · Bile jaune) · 2e rang = Air (Chaud/Humide · Sang) · 3e rang = Eau (Froid/Humide · Flegme) · 4e rang = Terre (Froid/Sec · Atrabile).",
    ),
  )

internal val bulughM3Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Bāb 7–8 · pp. 10–12",
      heading = "Calcul des Quantités, Délais & Taskīn des Jours et des Nuits",
      arabicTerms = "مقدار العدد، تسكين الأيام والليالي، قسمة 16",
      body =
        "Pour évaluer un nombre, un poids, une durée d'attente ou une distance (Bāb 7), on relève la somme des points ou les rangs actifs et l'on opère la réduction sur les cycles élémentaires (modulo 16 ou valeur Abjad réduite), en appréciant la vitesse selon que le significateur siège en Watad (rapide : heures/jours), en Succédente (moyen : semaines/mois) ou en Cadente (lent ou dépassé). Le Bāb 8 distribue ensuite les 16 figures sur les jours et nuits de la semaine en lien avec leurs planètes diurnes et nocturnes.",
      keyFormulaOrTable =
        "Watad (M1/4/7/10) = Délai court · Succédente (M2/5/8/11) = Moyen terme · Cadente (M3/6/9/12) = Retard ou accompli.",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 9–11 · pp. 12–19",
      heading = "Les 7 Voies d'Istikhrāj al-Ḍamīr & les 2 Grands Tasākīn",
      arabicTerms = "استخراج الضمير، تسكين الحروف، تسكين الجودلة، طريق ابن ربيد",
      body =
        "Al-Ṭūkhī expose sept procédés rigoureux pour découvrir la pensée secrète (Ḍamīr) : 1) repérer la figure occupant son propre foyer dans le Taskīn ; 2) suivre le mouvement du Mustawlī (figure la plus fréquente) ; 3) additionner les maisons des rangs actifs modulo 16 ; 4) combiner M1 ⊕ M15 ; 5) méthode d'Ibn Rabīd associant Feu et Terre réduits modulo 12 (les 12 maisons du zodiaque) ; 6) repérer la figure répétée 3 fois et lire sa maison d'accueil ; 7) faire cheminer (Tasīr) la pointe active depuis le Juge M15. Il compare ensuite le Taskīn al-Ḥurūf au Taskīn al-Jūdala et donne 5 voies pour extraire le prénom (Ism).",
      keyFormulaOrTable =
        "Voie 5 (Ibn Rabīd) : (Somme Feu + Terre) mod 12 → Maison du Ḍamīr · Voie 7 : Tasīr depuis la pointe de M15.",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 12–13 · pp. 19–22",
      heading = "Les 7 Voies de la Khabīʾa (Rashīd, Maghreb) & Critique de la 7e Voie",
      arabicTerms = "طرق الخبيئة السبع، طريقة رشيد، طريقة المغاربة",
      body =
        "Dans les chapitres 12 et 13, Cheikh al-Ṭūkhī détaille comment localiser et décrire un objet dissimulé (Khabīʾa) par sept méthodes classiques : celle de Rashīd (fondée sur le Témoin et la 16e maison), la méthode maghrébine (qui examine la main du cacheur en M1, la nature en M4 et l'issue en M15), l'extraction de la couleur et du règne élémentaire, et la vérification critique de la 7e voie dont l'auteur signale les failles lorsqu'on néglige la parité des Mères.",
      keyFormulaOrTable =
        "Khabīʾa : M1 = Celui qui cache · M4 = Lieu enfoui · M15 = Nature matérielle de l'objet · M16 = Révélation.",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 14–15 · pp. 22–25",
      heading = "Seuil des 90 et 60 Points, Quṭrī/Ḍilʿī & Table des Contraires (Aḍdād)",
      arabicTerms = "حد التسعين والستين، القطري (9) والضلعي (12)، جدول الأضداد",
      body =
        "Le Bāb 14 formule l'une des lois quantitatives les plus célèbres d'al-Ṭūkhī : compter le total des points de l'ensemble du Takht (M1 à M16). S'il dépasse 90 points, le thème est large, franc et tourné vers l'accomplissement ; s'il descend sous 60 points, la question est resserrée, bloquée ou secrète (entre 60 et 90 : état intermédiaire). Le Bāb 15 expose la combinaison diagonale Quṭrī (réduite modulo 9) opposée à la combinaison latérale Ḍilʿī (modulo 12), suivie du tableau complet des contraires (Al-Aḍdād : chaque figure inversée par ⊕ Al-Ṭarīq 1111).",
      keyFormulaOrTable =
        "Total Takht > 90 pts = Ouverture franche · < 60 pts = Jeu fermé / Rétention · Quṭrī mod 9 vs Ḍilʿī mod 12 · Ḍidd(F) = F ⊕ 1111.",
    ),
  )

internal val bulughM4Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Bāb 16–25 · pp. 25–33",
      heading = "Prédominance (Al-Akthar), Répétitions (1× à 4×), Vol, Maladie & Négoce",
      arabicTerms = "الحكم بالأكثر، تكرار الشكل، المسروق (19،30)، المريض (24)، التجارة (25)",
      body =
        "Dans les chapitres 16 à 25, al-Ṭūkhī établit d'abord le jugement par prédominance élémentaire (Al-Akthar) et détaille ce que signifie l'apparition d'une même figure 1 fois, 2 fois, 3 fois ou 4 fois dès les Mères : 3 ou 4 répétitions à l'ouverture figent le thème dans l'arrêt irrévocable de cette figure. Puis il traite du voyageur et de l'absent (M1, M9, M13), du vol et des objets en bois ou en métal (Bāb 19–22 & 30 : M7 voleur, M2 bien, jonction M7/M15), de la maladie (Bāb 24 : vitalité de M1 vs gravité de M6 et M8) et du commerce (Bāb 25 : gain par Entrantes en M2 et M10).",
      keyFormulaOrTable =
        "Vol : M7 = Voleur, M2 = Bien dérobé · Malade : M1 = Patient, M6 = Nature du mal, M8 = Issue critique · Négoce : M1 + M2 + M10.",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 26–32 · pp. 33–38",
      heading = "Al-Ghālib wa-l-Maghlūb (Bāb 26 & 29), Procès & Armées",
      arabicTerms = "الغالب والمغلوب، باب 26 وباب 29، المحاكمة",
      body =
        "Les célèbres chapitres 26 et 29 exposent les lois complètes du duel et de l'affrontement : pour savoir qui l'emportera entre deux adversaires, deux armées ou deux parties en procès, on oppose le premier camp (M1 demandeur, M2 ses ressources, M10 son autorité, M13 son témoin propre) au second camp (M7 adversaire, M8 ses ressources/pertes, M4 son soutien caché, M14 son témoin), puis on examine les parentés M9/M10 vs M11/M12 et l'arbitrage final de M15 (avec retour sur M11 si M15 est neutre).",
      keyFormulaOrTable =
        "Camp A = {M1, M2, M10, M13} vs Camp B = {M7, M8, M4, M14} · Arbitres = M15, M16 et M10 (couronne).",
    ),
    CourseLessonSection(
      lessonRef = "Bāb 33–40 · pp. 38–42",
      heading = "Mariage (Bāb 33), Ḥajj (34), Navigation (36), Prisonnier (38) & Nayrūz (40)",
      arabicTerms = "النكاح (33)، الحج (34)، الحاجة (35،39)، السفر بالبحر (36)، المسجون (38)، النيروز (40)",
      body =
        "Al-Ṭūkhī clôt les 40 chapitres par les grandes questions humaines et collectives : le mariage (Bāb 33 : entente M1/M7 et joie en M5), le pèlerinage du Ḥajj (Bāb 34 : pureté de M9 et retour en M4), la satisfaction d'un besoin urgent (Bāb 35 & 39), la sécurité en mer (Bāb 36 : surveiller les figures d'Eau et d'Inkis), la mise en garde avant toute association ou caution (Bāb 37), la libération d'un prisonnier (Bāb 38 : prédominance des Sortantes en M12 et M15) et les prévisions annuelles mondiales à l'entrée du Nayrūz (Bāb 40).",
      keyFormulaOrTable =
        "Bāb 38 (Prisonnier) : Sortantes en M12 + M15 = Libération · Fixes = Maintien en détention.",
    ),
  )

internal val bulughM5Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Notices & Poids · pp. 42–71",
      heading = "Les 16 Figures : Poids Numériques Exacts, Noms & Ṣifāt",
      arabicTerms = "الصفات والأوزان العددية للأشكال الستة عشر",
      body =
        "Aux pages 42 à 71, al-Ṭūkhī consacre une fiche détaillée à chacun des 16 signes : physionomie, caractère moral (Ṣifāt), correspondances planétaires, noms arabes/berbères et valeur numérique propre (ex. Laḥyān = 3, ʿAtaba Dākhila = 3, Naqī al-Khadd = 15, Bayāḍ = 16, Inkis = 18, Ḥumra = 31, ʿUqla = 36, Qabḍ Dākhil = 45/112, Ijtimāʿ = 55, Nuṣra Dākhila = 66, Ṭarīq = 78, Qabḍ Khārij = 91, Jamāʿa = 105, Nuṣra Khārija = 136).",
      keyFormulaOrTable =
        "Poids remarquables (pp. 42–48) : Bayāḍ 16 · Naqī 15 · Ḥumra 31 · ʿUqla 36 · Ijtimāʿ 55 · Nuṣra D. 66 · Ṭarīq 78 · Qabḍ Kh. 91 · Jamāʿa 105 · Nuṣra Kh. 136.",
    ),
    CourseLessonSection(
      lessonRef = "Ḥulūl al-Ashkāl · pp. 72–92",
      heading = "Les 256 Rencontres : Chaque Figure dans les 16 Maisons",
      arabicTerms = "حلول الأشكال الستة عشر في البيوت الستة عشر (256 حالة)",
      body =
        "Cette section fondamentale de vingt pages déroule systématiquement les 256 cas possibles (16 figures × 16 maisons) : ce qu'annonce précisément Laḥyān en M1, en M2, ..., en M16, et de même pour chacune des quinze autres figures. Une figure réputée faste change de nuance lorsqu'elle siège en M6 (épreuve/soin) ou M8 (perte/crise), tandis qu'une figure fixe ou terrestre s'affermit en M4.",
      keyFormulaOrTable =
        "Matrice complète : 16 figures × 16 maisons = 256 sentences contextualisées (disponibles dans l'onglet Encyclopédie & le inspecteur de Takht).",
    ),
    CourseLessonSection(
      lessonRef = "Jadwal & p. 94–97",
      heading = "Jadwal al-Nisab (p. 93), Tarbīʿ de Véracité & Balance Finale p. 97",
      arabicTerms = "جدول النسب (16×10)، التربيع للصدق، اليمين مقابل الشمال (ص 97)",
      body =
        "Les quatre dernières pages couronnent l'ouvrage par trois clefs opératoires : 1) le grand tableau Jadwal al-Nisab (p. 93) croisant les 16 figures sur 10 fonctions (Ṭālib, Maṭlūb, Naẓar, Nuṭq, Ittiṣāl, Infiṣāl, Shāhid, Dalīl, Sirr, Mustawlī) ; 2) les contrôles de véracité par Tarbīʿ (pp. 95–96) ; 3) l'ultime règle d'arbitrage p. 97 comparant les points ouverts de la Droite (M1 + M13 + M15) à ceux de la Gauche (M7 + M14 + M16), avec bascule vers la couronne d'honneur M10 si l'écart est serré.",
      keyFormulaOrTable =
        "Synthèse finale p. 97 : Points impairs Droite (M1 + M13 + M15) vs Gauche (M7 + M14 + M16) + transfert arbitre M10.",
    ),
  )

internal val bahriM1Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Muqaddima · pp. 9–28",
      heading = "Chaîne d'Idrīs & Jibrīl, les 6 Qualités & Adab du Rammāl",
      arabicTerms = "إدريس عليه السلام، آداب الرمّال، صفات الطالب الست",
      body =
        "Cheikh Muḥammad b. Shammās al-Baḥrī ouvre « Al-Fulk al-Mashḥūn » (2e éd., Mascate 2010) par la transmission prophétique attribuant les fondements du Raml à Idrīs instruit par Jibrīl sur le sable du rivage. Il exige du disciple six vertus : intelligence vive, amour de la science, constance patiente, sobriété de vie, accompagnement par un maître chevronné et fidélité quotidienne à l'exercice.",
      keyFormulaOrTable =
        "Préparation rituelle (al-Baḥrī) : 2 rakaʿāt de recueillement · Orientation Qibla · Tracé avec l'annulaire droit sans compter.",
    ),
    CourseLessonSection(
      lessonRef = "Temps & Lune · pp. 58–61",
      heading = "Jours Lunaires Néfastes, Heures Interdites & État Intérieur",
      arabicTerms = "الأيام النحسات، آخر أربعاء في الشهر، حضور القلب",
      body =
        "Aux pages 58–61, al-Baḥrī dresse la liste explicite des créneaux où il convient de suspendre le sable : les jours lunaires réputés difficiles (3, 5, 13, 16, 21, 25 et 26 du mois hijrī), le dernier mercredi du mois, ainsi que les moments où l'âme du géomancien est troublée par la colère, le tumulte extérieur ou la légèreté.",
      keyFormulaOrTable =
        "Jours lunaires signalés : 3 · 5 · 13 · 16 · 21 · 25 · 26 + dernier mercredi du mois hijrī (gérés par le gardien horaire de l'app).",
    ),
  )

internal val bahriM2Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Tasākīn · pp. 34–57",
      heading = "Tables Comparées : Ṭabīʿa, Ḥurūf, Bazdaḥ (Zanātī) & Abdaḥ (Barbarī)",
      arabicTerms = "تسكين الطبيعة، تسكين الحروف، بزدح للزناتي، أبده لخلف البربري",
      body =
        "Al-Baḥrī confronte méthodiquement les différentes tables de classement des 16 figures : le Taskīn al-Ṭabīʿa (p. 34, ordre élémentaire), le Taskīn al-Ḥurūf (p. 35, lettres arabes), le Taskīn Abjad (pp. 42–43), le Taskīn Bazdaḥ attribué à al-Zanātī (Feu=2, Air=7, Eau=4, Terre=8) et le Taskīn Abdaḥ de Khalaf al-Barbarī pondérant les quatre rangs par 1, 2, 4 et 8 pour convertir toute figure en rang décimal unique de 0 à 15.",
      keyFormulaOrTable =
        "Abdaḥ (Khalaf al-Barbarī, p. 54) : Poids des rangs = Feu(1) + Air(2) + Eau(4) + Terre(8) · Bazdaḥ (Zanātī) : N=2, A=7, E=4, T=8.",
    ),
    CourseLessonSection(
      lessonRef = "Al-Fulk al-Dāʾir · pp. 302–303",
      heading = "La Roue Circulaire des Demeures (Al-Fulk al-Dāʾir)",
      arabicTerms = "الفلك الدائر، تسكين حسّان والعدّاد",
      body =
        "En fin de traité (pp. 302–303), al-Baḥrī réunit en cercle l'ensemble des demeures rotatives (Al-Fulk al-Dāʾir), permettant de transposer une maison observée en son foyer naturel et de suivre la révolution des figures selon les douze signes et les quatre angles.",
      keyFormulaOrTable =
        "Usage du Fulk al-Dāʾir : relier la maison d'apparition (accidentelle) à la demeure Taskīn originelle pour corriger l'interprétation.",
    ),
  )

internal val bahriM3Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Pures & Ṣidq · pp. 77–90",
      heading = "Les 4 Figures Pures (p. 79) & le Contrôle Ṣidq al-Raml (pp. 89–90)",
      arabicTerms = "الأشكال الأربعة الصافية، صدق الرمل، الميزان الباطني",
      body =
        "Avant toute sentence, al-Baḥrī enseigne comment extraire les 4 Figures Élémentaires Pures (Nār al-Nār, Hawāʾ al-Hawāʾ, Māʾ al-Māʾ, Turāb al-Turāb) puis soumettre le Takht à l'épreuve mathématique de véracité « Ṣidq al-Raml » (pp. 89–90) : former (M3 ⊕ M5) puis (M11 ⊕ M15), et croiser les deux résultats pour obtenir la figure-sceau. Si cette figure siège dans l'une des 16 maisons, le thème est déclaré sincère (Ṣādiq).",
      keyFormulaOrTable =
        "Sceau Ṣidq al-Raml = (M3 ⊕ M5) ⊕ (M11 ⊕ M15) ∈ {M1..M16} · Rejet si 4 Mères toutes Fixes ou toutes Sortantes.",
    ),
    CourseLessonSection(
      lessonRef = "Bāṭin · pp. 141–145",
      heading = "Extraction du Thème Intérieur Caché (Bāṭin al-Takht)",
      arabicTerms = "باطن التخت، استخراج الخفي من الظاهر",
      body =
        "Aux pages 141–143, al-Baḥrī révèle le Bāṭin al-Takht : pour chaque maison k (1..16), on combine la figure tirée M_k par addition binaire (⊕) avec la figure gouvernante naturelle de la demeure k dans le Taskīn. Le second tableau à 16 maisons ainsi généré dévoile les ressorts souterrains, non-dits et appuis invisibles du tirage apparent.",
      keyFormulaOrTable =
        "Formule du Bāṭin : Bāṭin(k) = Figure(Maison k) ⊕ Gouverneur_Taskīn(k) pour k = 1..16.",
    ),
  )

internal val bahriM4Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Dignités · pp. 65–70",
      heading = "Exaltations (Sharaf) & Chutes (Hubūṭ) Planétaires des Maisons",
      arabicTerms = "شرف الأشكال وهبوطها، الكواكب السبعة في البيوت",
      body =
        "Al-Baḥrī établit l'équivalent géomantique des dignités essentielles de l'astrologie arabe : une figure gagne une puissance rayonnante lorsqu'elle siège dans la maison d'exaltation (Sharaf) de sa planète régente (Soleil en M1, Lune en M2, Jupiter en M4, Mercure en M6, Saturne en M7, Mars en M10, Vénus en M12), et s'affaiblit dans la maison opposée de sa chute (Hubūṭ).",
      keyFormulaOrTable =
        "Sharaf : Soleil→M1 · Lune→M2 · Jupiter→M4 · Mercure→M6 · Saturne→M7 · Mars→M10 · Vénus→M12 (Hubūṭ à l'opposé).",
    ),
    CourseLessonSection(
      lessonRef = "Aspects & Durées · pp. 174–266",
      heading = "Aspects Géométriques, Voyage & Calculs d'Ibn Ḥabīb (p. 255)",
      arabicTerms = "التسبيع، التربيع، التثليث، المقابلة، حساب المدة لابن حبيب",
      body =
        "Sur le cycle M1–M12, les maisons dialoguent par sextile (Tasdīs), carré (Tarbīʿ), trigone (Tathlīth) et opposition (Muqābala), distinguant amitié (mawadda) et inimitié (ʿadāwa). Pour chiffrer les délais du voyageur absent, de la grossesse ou d'une attente, la page 255 applique le système d'Ibn Ḥabīb sur les séries Ayqagh, Bakar, Jalash et Damat.",
      keyFormulaOrTable =
        "Voyage : M1 (Voyageur) · M9 (Route) · M4 (Retour au foyer) · Chiffres de durée selon Ibn Ḥabīb (p. 255).",
    ),
  )

internal val bahriM5Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Duel · pp. 151–166 & 316",
      heading = "Taḥlīf Mod-9, Armées Impaires/Paires & Awtād Croisés (p. 316)",
      arabicTerms = "التحليف بميزان 9، الغالب والمغلوب، الأوتاد المعكوسة (ص 316)",
      body =
        "Pour les compétitions et combats, al-Baḥrī croise quatre protocoles complémentaires : 1) le Taḥlīf modulo 9 sur la somme numérique des deux camps (« yasmū aqalluhā ») ; 2) le décompte des maisons Impaires (M1,3,5,7,9,11,13,15) face aux Paires ; 3) le sous-Takht interne extrait des seuls 4 Piliers ; 4) les célèbres Awtād croisés de la page 316 opposant Axe A (Feu M1 · Air M4 · Eau M7 · Terre M10) à Axe B (Feu M10 · Air M7 · Eau M4 · Terre M1).",
      keyFormulaOrTable =
        "Awtād croisés (p. 316) : Axe A = (F:M1, A:M4, E:M7, T:M10) vs Axe B = (F:M10, A:M7, E:M4, T:M1).",
    ),
    CourseLessonSection(
      lessonRef = "Arisṭāṭālīs & Qurʿa · pp. 295–389",
      heading = "Zāʾirja des 16 Lettres, Sentences d'Arisṭāṭālīs & Jadāwil al-Qurʿa",
      arabicTerms = "الزائرجة الرملية، أحكام أرسطاطاليس، جداول القرعة الرملية",
      body =
        "Les chapitres finaux (pp. 295–389) exposent la Zāʾirja ramlique à 16 lettres permettant d'extraire une phrase de réponse, le recueil d'aḥkām attribué à Arisṭāṭālīs (pp. 317–353) et les grands tableaux combinatoires de la Qurʿa al-Ramliyya (pp. 369–389).",
      keyFormulaOrTable =
        "Corpus final d'Al-Fulk : Zāʾirja (pp. 295–300) · Aḥkām Arisṭāṭālīs (pp. 317–353) · Qurʿa (pp. 369–389).",
    ),
  )

internal val qawlM1Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Ch. 1–14 · pp. 2–19",
      heading = "Plan Réel des Chapitres 1–14 : Raml, Planètes, Jours & Lettres",
      arabicTerms = "فهرس القول الجامع، طالع وبيوت، أيام السبعة والحروف",
      body =
        "Dans la table des matières originale de « Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml » (pp. 2–3), Shaykh Muḥammad Sās enchaîne l'ouverture (ch. 1), l'ordre naturel et les natures des 16 figures (ch. 2–5), l'attribution aux 7 planètes et aux 12 signes du zodiaque (ch. 6–8), les heures des 7 jours et les correspondances des 28 lettres arabes (ch. 9–12), jusqu'aux règles d'extraction des Mères et des Filles (ch. 13–14).",
      keyFormulaOrTable =
        "Architecture (ch. 1–14) : 16 figures → 4 Éléments → 7 Planètes → 12 Signes → 28 Lettres Abjad.",
    ),
    CourseLessonSection(
      lessonRef = "Modèle · pp. 34–36",
      heading = "Le Thème Modèle p. 34, Seuil des 96 Points & Vie vs Fermeture",
      arabicTerms = "الرمل الكامل والناقص (96 نقطة)، ميزان الحياة والانغلاق",
      body =
        "Aux pages 34–36, Sās commente ligne par ligne un Takht complet partant de M1 Nuṣra Khārija (Shīn=300), M2 Ḥumra (Qāf=100), M3 Nuṣra Khārija (300) et M4 Inkis (Ṣād=90). Il y formule la règle des 96 points maximum (16 maisons × 6 points moyens = Raml Kāmil si plein ou élevé, Raml Nāqiṣ en deçà) ainsi que le rapport entre les points des rangs d'Air et d'Eau (Vie / Ouverture) face à ceux de Feu et de Terre (Fermeture / Rétention).",
      keyFormulaOrTable =
        "Seuil = 96 pts (surface pleine) · Balance Sās = Sum(Air + Eau) [Vie] vs Sum(Feu + Terre) [Fermeture].",
    ),
  )

internal val qawlM2Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Ch. 15–22 · pp. 20–32",
      heading = "Table des 35+16 Muthallathāt (p. 20) & les 8 Tasākīn (pp. 30–32)",
      arabicTerms = "مثلثات الأشكال (51)، التساكين الثمانية، مثلث الدليل والعاقبة",
      body =
        "À la page 20, l'auteur dresse la table exhaustive des 51 triangles (35 combinaisons ternaires + 16 propres) permettant d'évaluer l'engendrement mutuel des figures. Dans le thème de 16 maisons, il distingue cinq Triangles structurants : T1 (M1+M2→M9), T2 nommé Al-Dalīl (M3+M4→M10), T3 (M5+M6→M11), T4 (M7+M8→M12) et T5 nommé Al-ʿĀqiba (M13+M14→M15). Aux pages 30–32, il confronte huit systèmes de Taskīn pour le Ḍamīr et le nom.",
      keyFormulaOrTable =
        "T1: M1+M2→M9 · T2 (Dalīl): M3+M4→M10 · T3: M5+M6→M11 · T4: M7+M8→M12 · T5 (ʿĀqiba): M13+M14→M15.",
    ),
    CourseLessonSection(
      lessonRef = "Aspects & Tasīr · p. 28 & 37",
      heading = "Aspects sur M1–M12 & Schéma Circulaire du Tasīr (p. 37)",
      arabicTerms = "تسديس، تربيع، تثليث، مقابلة، تسيير نقطة الميزان (ص 37)",
      body =
        "La page 28 applique les quatre aspects géométriques sur les 12 premières maisons : Tasdīs (Sextile, 1/6 = 2 maisons d'écart, amitié modérée), Tarbīʿ (Carré, 1/4 = 3 maisons, tension/obstacle), Tathlīth (Trigone, 1/3 = 4 maisons, plein accord) et Muqābala (Opposition, 1/2 = 6 maisons, duel ou rupture). À la page 37, un diagramme circulaire détaille le Tasīr de la pointe du Mīzān (M15) pour dater l'événement ou désigner la maison d'aboutissement.",
      keyFormulaOrTable =
        "Tasdīs (±2, amical) · Tarbīʿ (±3, friction) · Tathlīth (±4, harmonie) · Muqābala (±6, face-à-face) · Tasīr depuis M15 (p. 37).",
    ),
  )

internal val qawlM3Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Ch. 23–28 · pp. 38–55",
      heading = "Masāʾil : Prisonnier (M12), Mariage (M1/M7), Vol (M2) & Santé (M6)",
      arabicTerms = "المسجون، الزواج، السرقة، المريض، الحمل والولادة",
      body =
        "Les chapitres 23 à 28 condensent les décisions rapides sur les affaires urgentes : pour le prisonnier, examiner M12 (chaînes) et M15 (verdict) — les Sortantes libèrent, les Fixes retiennent ; pour le mariage, vérifier le Tathlīth ou Tasdīs entre M1 et M7 ; pour le vol, suivre le déplacement de M2 ; pour la maladie, confronter l'élément de M6 à l'humeur du patient.",
      keyFormulaOrTable =
        "Libération = Sortantes en M12 & M15 · Union = Accord M1/M7 (sans Ḥumra ni Inkis en M15).",
    ),
    CourseLessonSection(
      lessonRef = "Ch. 29–30 · pp. 56–58",
      heading = "Diagnostic du Siḥr / ʿĀriḍ (Jinn) & Calcul des Isqāṭāt Modulo 7",
      arabicTerms = "الكشف عن السحر والعارض، إسقاطات السبعة (حساب الأيام والأسباب)",
      body =
        "Les pages finales (pp. 56–58) traitent des causes subtiles et spirituelles (Siḥr, mauvais œil, trouble ʿĀriḍ) en croisant M6, M12 et M15, et clôturent l'ouvrage par la méthode des Isqāṭāt modulo 7 (retrancher 7 successivement des sommes numériques des figures actives) afin d'assigner à chaque reste 1..7 son jour planétaire et sa conduite appropriée.",
      keyFormulaOrTable =
        "Isqāṭāt p. 58 : Somme des lettres/points mod 7 → Reste 1..7 indexé sur Dimanche (1) à Samedi (7).",
    ),
  )
