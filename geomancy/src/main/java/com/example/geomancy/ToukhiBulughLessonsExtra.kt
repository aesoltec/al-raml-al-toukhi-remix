package com.example.geomancy

/**
 * Leçons supplémentaires pour le cours Bulūgh al-Amal fī ʿIlm al-Raml
 * de Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī (Beyrouth, 1411 H / 1991, 98 pp.).
 * Ces leçons complètent les leçons existantes pour un enseignement complet.
 */
internal val bulughExtraLessons: List<CourseLessonSection> = listOf(
  CourseLessonSection(
    lessonRef = "Bāb 1 · pp. 5–8",
    heading = "Rites préparatoires, Qibla, Verset de l'Heure Nocturne & Tracé des 16 Lignes",
    arabicTerms = "طهارة، استقبال القبلة، الفاتحة، الزلزلة، يا فتّاح يا عليم، هيئة الأنامل، ثلاث مرات في اليوم",
    body = "Avant de tracer les lignes, le consultant ou le maître doit être en état de pureté rituelle (ṭahāra), se tourner vers la Qibla et éviter toute distraction. Al-Ṭūkhī rapporte le vers classique affirmant la supériorité du silence nocturne : « Wa-fī dujā al-layli bayānan / yunbīka ʿan mā aḍmarta ʿiyānan ». Les heures du jour conviennent néanmoins si le cœur est recueilli. On trace quatre lignes de points d'Est en Ouest (de droite à gauche) sans jamais compter à l'avance, selon le rythme naturel de la main (ʿalā hayʾat al-anāmil) : la première ligne reste modérée (sans excéder 12 points dans le premier élan), la seconde légèrement plus ample, la troisième un peu plus large encore. On répète pour former 4 groupes de 4 lignes (16 lignes), puis on barre les points deux par deux pour garder Fard (1 point) ou Zawj (2 points). Interdits formels : tracer sans ablution, par dérision ou simple curiosité, sous l'emprise de la colère ou d'une faim aiguë, ou pour plus de trois demandes sérieuses dans une même journée.",
    keyFormulaOrTable = "Récitations prescrites (Bāb 1) : 1re ligne = Al-Fātiḥa · 2e ligne = « Wa-ʿindahu mafātiḥu al-ghayb lā yaʿlamuhā illā huwa… » (Coran VI, 59) · 3e & 4e lignes = Sourate Al-Zalzala · Clôture : « Allāhumma arinī mā huwa al-maknūn fī sirrika al-makhzūn yā Fattāḥ yā ʿAlīm ». Règle d'or : max. 3 tirages par jour · Ne jamais répéter deux fois la même question le même jour · Tracé d'Est en Ouest.",
    visualAids = listOf(
      "Taliki (الطريق) = • • • •",
      "Lawsana (العتبة الخارجة) = • • • ••",
      "Djanfa Almamy (الجودلة) = • • •• •",
      "Kalalaw (النصرة الخارجة) = • • •• ••",
    ),
  ),
  CourseLessonSection(
    lessonRef = "Bāb 2–6 · pp. 8–11",
    heading = "Natures des 16 figures, Mouvements, Polarités, Awtād, Maisons & Éléments",
    arabicTerms = "داخل، خارج، ثابت، منقلب · ذكر، أنثى · الأوتاد (1،4،7،10)، ما يلي الأوتاد (2،5،8،11)، السواقط (3،6،9،12)",
    body = "Cheikh al-Ṭūkhī classe les 16 figures selon leur mouvement (Entrantes, Sortantes, Fixes, Mobiles), leur fortune (Fastes, Néfastes, Mixtes) et répartit les 16 maisons entre Awtād (Piliers M1, M4, M7, M10), Succédentes (M2, M5, M8, M11) et Cadentes (M3, M6, M9, M12). Les 4 Mawāzīn (M12, M14, M15, M16) forment la balance finale. Mouvements : figures Entrantes (Ousmana, Nouhoun, Malidjou, Badra Ali), Sortantes (Inzan, Lawsana, Kalalaw, Lomara), Fixes (Adama, Moussa, Solomana, Mangouchi, Bayaro) et Mobiles (Djanfa Almamy, Tontigui, Taliki). Chaque rang des figures correspond à un élément, une humeur médicale, un règne pour la Khabīʾa (objet caché) et une direction cardinale.",
    keyFormulaOrTable = "Awtād (1, 4, 7, 10) = Présent & Force · Succédentes (2, 5, 8, 11) = Futur · Cadentes (3, 6, 9, 12) = Passé · M12-M14-M15-M16 = Les 4 Mawāzīn. 1er rang = Feu (Chaud/Sec · Bile jaune) · 2e rang = Air (Chaud/Humide · Sang) · 3e rang = Eau (Froid/Humide · Flegme) · 4e rang = Terre (Froid/Sec · Atrabile).",
    visualAids = listOf(
      "Entrantes : Ousmana (القبض الداخلي) = •• • •• •, Nouhoun (النصرة الداخلة) = •• •• • •, Malidjou (العتبة الداخلة) = •• • • •, Badra Ali (الاجتماع) = •• • • ••",
      "Sortantes : Inzan (القبض الخارج) = • •• • ••, Lawsana (العتبة الخارجة) = • • • ••, Kalalaw (النصرة الخارجة) = • • •• ••, Lomara (الحمرة) = •• • •• ••",
      "Fixes : Adama (اللحيان) = • •• •• ••, Moussa (الجماعة) = •• •• •• ••, Solomana (العقلة) = • •• •• •, Mangouchi (الإنكيس) = •• •• •• •, Bayaro (البياض) = •• •• • ••",
      "Mobiles : Djanfa Almamy (الجودلة) = • • •• •, Tontigui (النقي الخد) = • •• • •, Taliki (الطريق) = • • • •",
    ),
  ),
  CourseLessonSection(
    lessonRef = "Bāb 7–15 · pp. 11–26",
    heading = "Tasākīn, Durées, Istikhrāj al-Ḍamīr & Khabīya",
    arabicTerms = "تسكين، مقدار العدد، استخراج الضمير، الخبيئة، حد التسعين والستين",
    body = "Le cœur des Bāb 7 à 15 expose les ordres de demeure (Taskīn al-Zanātī, al-Ayyām, al-Ḥurūf, al-Jūdala), le calcul des délais et les 7 méthodes pour dévoiler la pensée secrète (Ḍamīr) et l'objet caché (Khabīya). Le seuil des 90 et 60 points : > 90 points annonce une décision rapide et franche ; < 60 indique jeu fermé ou rétention. Istikhrāj al-Ḍamīr : additionner le rang Taskīn et le réduire mod 16 ou repérer la figure répétée ≥ 3 fois (Mustawlī). Extraction du prénom & Khabīya : correspondance Abjad des rangs actifs de M1, M15 et de la figure dominante. Tableau des contraires (Al-Aḍdād) : inverser les 4 rangs d'une figure (⊕ Ṭarīq) pour connaître son opposé. Durées temporelles : selon que la figure significative siège en Watad (jours/mois), Succédente ou Cadente.",
    keyFormulaOrTable = "Seuil des 90 et 60 points (Bāb 14) : > 90 pts = décision rapide et franche ; < 60 pts = jeu fermé ou rétention. Istikhrāj al-Ḍamīr : additionner le rang Taskīn et réduire mod 16. Khabīya : M1 = Celui qui cache · M4 = Lieu enfoui · M15 = Nature matérielle · M16 = Révélation. Ḍidd(F) = F ⊕ 1111. Watad (M1/4/7/10) = Délai court · Succédente (M2/5/8/11) = Moyen terme · Cadente (M3/6/9/12) = Retard ou accompli.",
  ),
  CourseLessonSection(
    lessonRef = "Bāb 16–40 · pp. 26–43",
    heading = "Consultations thématiques : Voyage, Vol, Maladie & Duel",
    arabicTerms = "المسافر (16–18)، المسروق (19–22،30)، المريض (24)، التجارة (25)، الغالب والمغلوب (26،29)",
    body = "Dans la seconde moitié théorique de « Bulūgh al-Amal » (Bāb 16 à 40), al-Ṭūkhī applique le Takht à chaque cas de la vie : nouvelles de l'absent, vol, maladie, trésors, prévisions annuelles (Nayrūz) et confrontation entre deux adversaires (Bāb 26 & 29). Voyageur absent (Bāb 16–18) : observer M1, M9 (route lointaine) et la direction élémentaire du Témoin M13. Vol & Objet perdu (Bāb 20–22) : M7 désigne le voleur, M2 le bien ; si M7 et M15 se rejoignent en Entrantes, le bien revient. Al-Ghālib wa-l-Maghlūb (Bāb 26 & 29) : Camp A (M1, M2, M10, M13) contre Camp B (M7, M8, M4, M14), arbitrage par M15. Maladie & Humeurs (Bāb 24) : la figure de M6 indique la nature chaude/froide du mal ; comparer M1 et M8.",
    keyFormulaOrTable = "Vol : M7 = Voleur, M2 = Bien dérobé · Malade : M1 = Patient, M6 = Nature du mal, M8 = Issue critique · Négoce : M1 + M2 + M10 · Duel : Camp A = {M1, M2, M10, M13} vs Camp B = {M7, M8, M4, M14} · Arbitres = M15, M16 et M10 (couronne).",
  ),
  CourseLessonSection(
    lessonRef = "Ḥulūl al-Ashkāl & Synthèse finale · pp. 43–97",
    heading = "Les 16 Figures dans les 16 Maisons & Synthèse p. 94–97",
    arabicTerms = "حلول الأشكال، جدول النسب (ص 94)، التربيع (ص 95–96)، الغالب والمغلوب (ص 97)",
    body = "La dernière grande section de « Bulūgh al-Amal » répertorie les traits physiques et moraux (Ṣifāt, pp. 43–72), les 256 combinaisons Ḥulūl al-Ashkāl (pp. 73–93), le tableau des rapports Jadwal al-Nisab (p. 94) et la règle finale du vainqueur (p. 97). Ḥulūl al-Ashkāl (pp. 73–93) : chaque figure prend une nuance particulière selon la maison 1..16 où elle apparaît. Jadwal al-Nisab (p. 94) : distinguer Ṭālib (M1), Maṭlūb (M7), Dalīl, Shāhid, Sirr et Mustawlī (figure répétée ≥ 3 fois). Contrôle de véracité par Tarbīʿ (pp. 95–96) : croiser les angles pour vérifier si le thème répond sans trouble. Règle finale du duel (p. 97) : comparer les points ouverts de la Droite (M1 + M13 + M15) face à la Gauche (M7 + M14 + M16), avec transfert vers l'arbitre M10.",
    keyFormulaOrTable = "Synthèse finale p. 97 : Points impairs Droite (M1 + M13 + M15) vs Gauche (M7 + M14 + M16) + transfert arbitre M10. Ḥulūl al-Ashkāl : 16 figures × 16 maisons = 256 sentences contextualisées.",
  ),
)

// ============================================================
// Leçons supplémentaires pour le Manbaʿ
// ============================================================

internal val manbaExtraLessons: List<CourseLessonSection> = listOf(
  CourseLessonSection(
    lessonRef = "Leçons 1–20 · pp. 1–40",
    heading = "Les fondements du Raml : Pureté, Tracé & Érection du Takht",
    arabicTerms = "طهارة، استقبال القبلة، الفاتحة، الزلزلة، يا فتّاح يا عليم، هيئة الأنامل، ثلاث مرات في اليوم",
    body = "Le Manbaʿ Usūl al-Raml est le manuel de référence du genre en Égypte contemporaine. Cheikh al-Ṭūkhī y codifie en 80 leçons la pureté rituelle du tracé dans le sable, l'érection des 16 maisons, les Tasākīn, les 7 procédés d'Istikhrāj al-Ḍamīr, les jugements thématiques et la synthèse finale du duel. Le géomancien doit se tenir en état de pureté (ṭahāra), face à la Qibla, réciter Al-Fātiḥa et la sourate Al-Zalzala avant de tracer 4 × 4 lignes de points sans les compter à l'avance. Ne jamais dépasser 12 points par ligne ; réduire par paires jusqu'à un reste Fard (1 point) ou Zawj (2 points). Les 4 premières lignes forment M1, puis M2, M3 et M4 (Les Mères / Al-Ummahāt). Extraction horizontale des 4 Filles (Banāt M5–M8) à partir des rangs Feu, Air, Eau et Terre des Mères. Dérivation par addition binaire des Nièces (M9–M12), Témoins (M13–M14), Juge (M15, obligatoirement pair) et 16e Maison (M15 ⊕ M1). Discipline du sable : limiter la consultation à 3 tirages sérieux par jour et ne pas répéter une même question le même jour.",
    keyFormulaOrTable = "Règle d'or : max. 3 tirages par jour · Ne jamais répéter deux fois la même question le même jour · Tracé d'Est en Ouest · M15 = M13 ⊕ M14 (doit être pair) · M16 = M15 ⊕ M1 (loi absolue).",
    visualAids = listOf(
      "Taliki (الطريق) = • • • •",
      "Lawsana (العتبة الخارجة) = • • • ••",
      "Djanfa Almamy (الجودلة) = • • •• •",
      "Kalalaw (النصرة الخارجة) = • • •• ••",
      "Tontigui (النقي الخد) = • •• • •",
      "Inzan (القبض الخارج) = • •• • ••",
      "Solomana (العقلة) = • •• •• •",
      "Adama (اللحيان) = • •• •• ••",
      "Malidjou (العتبة الداخلة) = •• • • •",
      "Ali (الاجتماع) = •• • • ••",
      "Ousmana (القبض الداخلي) = •• • •• •",
      "Lomara (الحمرة) = •• • •• ••",
      "Nouhoun (النصرة الداخلة) = •• •• • •",
      "Bayaro (البياض) = •• •• • ••",
      "Mangouchi (الإنكيس) = •• •• •• •",
      "Moussa (الجماعة) = •• •• •• ••",
    ),
  ),
  CourseLessonSection(
    lessonRef = "Leçons 21–40 · pp. 41–80",
    heading = "Les Tasākīn, les Damir & les Jugements Thématiques",
    arabicTerms = "تسكين، ضمير، أحكام، مسائل",
    body = "Le Manbaʿ expose les ordres de demeure (Taskīn al-Zanātī, al-Ayyām, al-Ḥurūf, al-Jūdala), le calcul des délais et les 7 méthodes pour dévoiler la pensée secrète (Ḍamīr) et l'objet caché (Khabīya). Le seuil des 90 et 60 points : > 90 points annonce une décision rapide et franche ; < 60 indique jeu fermé ou rétention. Istikhrāj al-Ḍamīr : additionner le rang Taskīn et le réduire mod 16 ou repérer la figure répétée ≥ 3 fois (Mustawlī). Extraction du prénom & Khabīya : correspondance Abjad des rangs actifs de M1, M15 et de la figure dominante. Tableau des contraires (Al-Aḍdād) : inverser les 4 rangs d'une figure (⊕ Ṭarīq) pour connaître son opposé. Durées temporelles : selon que la figure significative siège en Watad (jours/mois), Succédente ou Cadente. Le Manbaʿ expose aussi la rencontre des deux armées (p. 77) : cases impaires = demandeur, paires = demandé, décompte des saoud, égalité = paix, nahis sortants = défaite. Et le litige (p. 75) : M1 demandeur, M7 défendeur, M10 juge, M5 objet, M15 issue.",
    keyFormulaOrTable = "Rencontre (p. 77) : cases impaires = demandeur, paires = demandé, décompte des saoud, égalité = paix, nahis sortants = défaite. Litige (p. 75) : M1 demandeur, M7 défendeur, M10 juge, M5 objet, M15 issue. Seuil des 90 et 60 points : > 90 pts = décision rapide et franche ; < 60 pts = jeu fermé ou rétention.",
  ),
  CourseLessonSection(
    lessonRef = "Leçons 41–80 · pp. 81–264",
    heading = "Les Jugements Thématiques & la Synthèse Finale",
    arabicTerms = "أحكام، مسائل، خاتمة",
    body = "Le Manbaʿ expose les jugements thématiques pour chaque cas de la vie : voyageur absent (M1, M9, M13), vol et objet perdu (M7 voleur, M2 bien), maladie et humeurs (M6 nature du mal, M1/M8), commerce et négoce (M1 + M2 + M10), mariage (M1/M7 entente, M5 joie), duel et confrontation (Camp A vs Camp B, arbitrage par M15), prisonnier (Sortantes en M12 et M15 = libération), mariage (Bāb 33 : entente M1/M7 et joie en M5), Ḥajj (pureté de M9 et retour en M4), navigation (surveiller les figures d'Eau et d'Inkis), et les prévisions annuelles mondiales à l'entrée du Nayrūz (Bāb 40).",
    keyFormulaOrTable = "Synthèse finale : Points impairs Droite (M1 + M13 + M15) vs Gauche (M7 + M14 + M16) + transfert arbitre M10. Ḥulūl al-Ashkāl : 16 figures × 16 maisons = 256 sentences contextualisées.",
  ),
)

// ============================================================
// Leçons supplémentaires pour Bahri (Al-Fulk al-Mashḥūn)
// ============================================================

internal val bahriExtraLessons: List<CourseLessonSection> = listOf(
  CourseLessonSection(
    lessonRef = "Muqaddima · pp. 9–28",
    heading = "Chaîne d'Idrīs & Jibrīl, les 6 Qualités & Adab du Rammāl",
    arabicTerms = "إدريس عليه السلام، آداب الرمّال، صفات الطالب الست",
    body = "Cheikh Muḥammad b. Shammās al-Baḥrī ouvre « Al-Fulk al-Mashḥūn » (2e éd., Mascate 2010) par la transmission prophétique attribuant les fondements du Raml à Idrīs instruit par Jibrīl sur le sable du rivage. Il exige du disciple six vertus : intelligence vive, amour de la science, constance patiente, sobriété de vie, accompagnement par un maître chevronné et fidélité quotidienne à l'exercice. Préparation rituelle (al-Baḥrī) : 2 rakaʿāt de recueillement · Orientation Qibla · Tracé avec l'annulaire droit sans compter.",
    keyFormulaOrTable = "Préparation rituelle (al-Baḥrī) : 2 rakaʿāt de recueillement · Orientation Qibla · Tracé avec l'annulaire droit sans compter.",
  ),
  CourseLessonSection(
    lessonRef = "Tasākīn · pp. 34–57",
    heading = "Tables Comparées : Ṭabīʿa, Ḥurūf, Bazdaḥ (Zanātī) & Abdaḥ (Barbarī)",
    arabicTerms = "تسكين الطبيعة، تسكين الحروف، بزدح للزناتي، أبده لخلف البربري",
    body = "Al-Baḥrī confronte méthodiquement les différentes tables de classement des 16 figures : le Taskīn al-Ṭabīʿa (p. 34, ordre élémentaire), le Taskīn al-Ḥurūf (p. 35, lettres arabes), le Taskīn Abjad (pp. 42–43), le Taskīn Bazdaḥ attribué à al-Zanātī (Feu=2, Air=7, Eau=4, Terre=8) et le Taskīn Abdaḥ de Khalaf al-Barbarī pondérant les quatre rangs par 1, 2, 4 et 8 pour convertir toute figure en rang décimal unique de 0 à 15. Abdaḥ (Khalaf al-Barbarī, p. 54) : Poids des rangs = Feu(1) + Air(2) + Eau(4) + Terre(8) · Bazdaḥ (Zanātī) : N=2, A=7, E=4, T=8.",
    keyFormulaOrTable = "Abdaḥ (Khalaf al-Barbarī, p. 54) : Poids des rangs = Feu(1) + Air(2) + Eau(4) + Terre(8) · Bazdaḥ (Zanātī) : N=2, A=7, E=4, T=8.",
  ),
  CourseLessonSection(
    lessonRef = "Pures & Ṣidq · pp. 77–90",
    heading = "Les 4 Figures Pures & le Contrôle Ṣidq al-Raml",
    arabicTerms = "الأشكال الأربعة الصافية، صدق الرمل، الميزان الباطني",
    body = "Avant toute sentence, al-Baḥrī enseigne comment extraire les 4 Figures Élémentaires Pures (Nār al-Nār, Hawāʾ al-Hawāʾ, Māʾ al-Māʾ, Turāb al-Turāb) puis soumettre le Takht à l'épreuve mathématique de véracité « Ṣidq al-Raml » (pp. 89–90) : former (M3 ⊕ M5) puis (M11 ⊕ M15), et croiser les deux résultats pour obtenir la figure-sceau. Si cette figure siège dans l'une des 16 maisons, le thème est déclaré sincère (Ṣādiq). Sceau Ṣidq al-Raml = (M3 ⊕ M5) ⊕ (M11 ⊕ M15) ∈ {M1..M16} · Rejet si 4 Mères toutes Fixes ou toutes Sortantes.",
    keyFormulaOrTable = "Sceau Ṣidq al-Raml = (M3 ⊕ M5) ⊕ (M11 ⊕ M15) ∈ {M1..M16} · Rejet si 4 Mères toutes Fixes ou toutes Sortantes.",
  ),
  CourseLessonSection(
    lessonRef = "Bāṭin · pp. 141–145",
    heading = "Extraction du Thème Intérieur Caché (Bāṭin al-Takht)",
    arabicTerms = "باطن التخت، استخراج الخفي من الظاهر",
    body = "Aux pages 141–143, al-Baḥrī révèle le Bāṭin al-Takht : pour chaque maison k (1..16), on combine la figure tirée M_k par addition binaire (⊕) avec la figure gouvernante naturelle de la demeure k dans le Taskīn. Le second tableau à 16 maisons ainsi généré dévoile les ressorts souterrains, non-dits et appuis invisibles du tirage apparent. Formule du Bāṭin : Bāṭin(k) = Figure(Maison k) ⊕ Gouverneur_Taskīn(k) pour k = 1..16.",
    keyFormulaOrTable = "Formule du Bāṭin : Bāṭin(k) = Figure(Maison k) ⊕ Gouverneur_Taskīn(k) pour k = 1..16.",
  ),
  CourseLessonSection(
    lessonRef = "Dignités · pp. 65–70",
    heading = "Exaltations (Sharaf) & Chutes (Hubūṭ) Planétaires des Maisons",
    arabicTerms = "شرف الأشكال وهبوطها، الكواكب السبعة في البيوت",
    body = "Al-Baḥrī établit l'équivalent géomantique des dignités essentielles de l'astrologie arabe : une figure gagne une puissance rayonnante lorsqu'elle siège dans la maison d'exaltation (Sharaf) de sa planète régente (Soleil en M1, Lune en M2, Jupiter en M4, Mercure en M6, Saturne en M7, Mars en M10, Vénus en M12), et s'affaiblit dans la maison opposée de sa chute (Hubūṭ). Sharaf : Soleil→M1 · Lune→M2 · Jupiter→M4 · Mercure→M6 · Saturne→M7 · Mars→M10 · Vénus→M12 (Hubūṭ à l'opposé).",
    keyFormulaOrTable = "Sharaf : Soleil→M1 · Lune→M2 · Jupiter→M4 · Mercure→M6 · Saturne→M7 · Mars→M10 · Vénus→M12 (Hubūṭ à l'opposé).",
  ),
  CourseLessonSection(
    lessonRef = "Duel · pp. 151–166 & 316",
    heading = "Taḥlīf Mod-9, Armées Impaires/Paires & Awtād Croisés (p. 316)",
    arabicTerms = "التحليف بميزان 9، الغالب والمغلوب، الأوتاد المعكوسة (ص 316)",
    body = "Pour les compétitions et combats, al-Baḥrī croise quatre protocoles complémentaires : 1) le Taḥlīf modulo 9 sur la somme numérique des deux camps (« yasmū aqalluhā ») ; 2) le décompte des maisons Impaires (M1,3,5,7,9,11,13,15) face aux Paires ; 3) le sous-Takht interne extrait des seuls 4 Piliers ; 4) les célèbres Awtād croisés de la page 316 opposant Axe A (Feu M1 · Air M4 · Eau M7 · Terre M10) à Axe B (Feu M10 · Air M7 · Eau M4 · Terre M1). Awtād croisés (p. 316) : Axe A = (F:M1, A:M4, E:M7, T:M10) vs Axe B = (F:M10, A:M7, E:M4, T:M1).",
    keyFormulaOrTable = "Awtād croisés (p. 316) : Axe A = (F:M1, A:M4, E:M7, T:M10) vs Axe B = (F:M10, A:M7, E:M4, T:M1).",
  ),
)

// ============================================================
// Leçons supplémentaires pour Qawl al-Jāmiʿ
// ============================================================

internal val qawlExtraLessons: List<CourseLessonSection> = listOf(
  CourseLessonSection(
    lessonRef = "Ch. 1–14 · pp. 2–19",
    heading = "Plan Réel des Chapitres 1–14 : Raml, Planètes, Jours & Lettres",
    arabicTerms = "فهرس القول الجامع، طالع وبيوت، أيام السبعة والحروف",
    body = "Dans la table des matières originale de « Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml » (pp. 2–3), Shaykh Muḥammad Sās enchaîne l'ouverture (ch. 1), l'ordre naturel et les natures des 16 figures (ch. 2–5), l'attribution aux 7 planètes et aux 12 signes du zodiaque (ch. 6–8), les heures des 7 jours et les correspondances des 28 lettres arabes (ch. 9–12), jusqu'aux règles d'extraction des Mères et des Filles (ch. 13–14). Architecture (ch. 1–14) : 16 figures → 4 Éléments → 7 Planètes → 12 Signes → 28 Lettres Abjad.",
    keyFormulaOrTable = "Architecture (ch. 1–14) : 16 figures → 4 Éléments → 7 Planètes → 12 Signes → 28 Lettres Abjad.",
  ),
  CourseLessonSection(
    lessonRef = "Modèle · pp. 34–36",
    heading = "Le Thème Modèle p. 34, Seuil des 96 Points & Vie vs Fermeture",
    arabicTerms = "الرمل الكامل والناقص (96 نقطة)، ميزان الحياة والانغلاق",
    body = "Aux pages 34–36, Sās commente ligne par ligne un Takht complet partant de M1 Nuṣra Khārija (Shīn=300), M2 Ḥumra (Qāf=100), M3 Nuṣra Khārija (300) et M4 Inkis (Ṣād=90). Il y formule la règle des 96 points maximum (16 maisons × 6 points moyens = Raml Kāmil si plein ou élevé, Raml Nāqiṣ en deçà) ainsi que le rapport entre les points des rangs d'Air et d'Eau (Vie / Ouverture) face à ceux de Feu et de Terre (Fermeture / Rétention). Seuil = 96 pts (surface pleine) · Balance Sās = Sum(Air + Eau) [Vie] vs Sum(Feu + Terre) [Fermeture].",
    keyFormulaOrTable = "Seuil = 96 pts (surface pleine) · Balance Sās = Sum(Air + Eau) [Vie] vs Sum(Feu + Terre) [Fermeture].",
  ),
  CourseLessonSection(
    lessonRef = "Ch. 15–22 · pp. 20–32",
    heading = "Table des 35+16 Muthallathāt & les 8 Tasākīn",
    arabicTerms = "مثلثات الأشكال (51)، التساكين الثمانية، مثلث الدليل والعاقبة",
    body = "À la page 20, l'auteur dresse la table exhaustive des 51 triangles (35 combinaisons ternaires + 16 propres) permettant d'évaluer l'engendrement mutuel des figures. Dans le thème de 16 maisons, il distingue cinq Triangles structurants : T1 (M1+M2→M9), T2 nommé Al-Dalīl (M3+M4→M10), T3 (M5+M6→M11), T4 (M7+M8→M12) et T5 nommé Al-ʿĀqiba (M13+M14→M15). Aux pages 30–32, il confronte huit systèmes de Taskīn pour le Ḍamīr et le nom. T1: M1+M2→M9 · T2 (Dalīl): M3+M4→M10 · T3: M5+M6→M11 · T4: M7+M8→M12 · T5 (ʿĀqiba): M13+M14→M15.",
    keyFormulaOrTable = "T1: M1+M2→M9 · T2 (Dalīl): M3+M4→M10 · T3: M5+M6→M11 · T4: M7+M8→M12 · T5 (ʿĀqiba): M13+M14→M15.",
  ),
  CourseLessonSection(
    lessonRef = "Aspects & Tasīr · p. 28 & 37",
    heading = "Aspects Géométriques & Schéma Circulaire du Tasīr (p. 37)",
    arabicTerms = "تسديس، تربيع، تثليث، مقابلة، تسيير نقطة الميزان (ص 37)",
    body = "La page 28 applique les quatre aspects géométriques sur les 12 premières maisons : Tasdīs (Sextile, 1/6 = 2 maisons d'écart, amitié modérée), Tarbīʿ (Carré, 1/4 = 3 maisons, tension/obstacle), Tathlīth (Trigone, 1/3 = 4 maisons, plein accord) et Muqābala (Opposition, 1/2 = 6 maisons, duel ou rupture). À la page 37, un diagramme circulaire détaille le Tasīr de la pointe du Mīzān (M15) pour dater l'événement ou désigner la maison d'aboutissement. Tasdīs (±2, amical) · Tarbīʿ (±3, friction) · Tathlīth (±4, harmonie) · Muqābala (±6, face-à-face) · Tasīr depuis M15 (p. 37).",
    keyFormulaOrTable = "Tasdīs (±2, amical) · Tarbīʿ (±3, friction) · Tathlīth (±4, harmonie) · Muqābala (±6, face-à-face) · Tasīr depuis M15 (p. 37).",
  ),
  CourseLessonSection(
    lessonRef = "Ch. 23–30 · pp. 38–58",
    heading = "Masāʾil : Prisonnier, Mariage, Vol, Santé & Diagnostic du Siḥr",
    arabicTerms = "المسجون، الزواج، السرقة، المريض، الكشف عن السحر والعارض، إسقاطات السبعة",
    body = "Les chapitres 23 à 30 condensent les décisions rapides sur les affaires urgentes : pour le prisonnier, examiner M12 (chaînes) et M15 (verdict) — les Sortantes libèrent, les Fixes retiennent ; pour le mariage, vérifier le Tathlīth ou Tasdīs entre M1 et M7 ; pour le vol, suivre le déplacement de M2 ; pour la maladie, confronter l'élément de M6 à l'humeur du patient. Les pages finales (pp. 56–58) traitent des causes subtiles et spirituelles (Siḥr, mauvais œil, trouble ʿĀriḍ) en croisant M6, M12 et M15, et clôturent l'ouvrage par la méthode des Isqāṭāt modulo 7 (retrancher 7 successivement des sommes numériques des figures actives) afin d'assigner à chaque reste 1..7 son jour planétaire et sa conduite appropriée. Libération = Sortantes en M12 & M15 · Union = Accord M1/M7 (sans Ḥumra ni Inkis en M15). Isqāṭāt p. 58 : Somme des lettres/points mod 7 → Reste 1..7 indexé sur Dimanche (1) à Samedi (7).",
    keyFormulaOrTable = "Libération = Sortantes en M12 & M15 · Union = Accord M1/M7 (sans Ḥumra ni Inkis en M15) · Isqāṭāt p. 58 : Somme des lettres/points mod 7 → Reste 1..7 indexé sur Dimanche (1) à Samedi (7).",
  ),
)

// ============================================================
// Leçons supplémentaires pour les Manuscrits
// ============================================================

internal val manuscriptsExtraLessons: List<CourseLessonSection> = listOf(
  CourseLessonSection(
    lessonRef = "MS 2380 & 2405 · Folios 1–50",
    heading = "Manuscrits de La Mecque : Tasākīn, Ḍamīr & Verdicts",
    arabicTerms = "تسكين، ضمير، أحكام، مسائل",
    body = "Les manuscrits MS 2380 et MS 2405 de La Mecque contiennent des leçons complémentaires sur les Tasākīn, le Ḍamīr et les verdicts. Ils complètent les traités d'Al-Toukhi et d'al-Baḥrī avec des exemples pratiques et des règles supplémentaires. Le thème se construit par mères aléatoires → filles → nièces → témoins → juge (qāḍī) → réconciliateur, et se lit par les figures radical/secondaire, le ḥākil, le bilkhas (la figure finale de la réponse).",
    keyFormulaOrTable = "Thème = mother[4] + derivation + houses[N_HOUSES] + reader. Rien de rituel n'est déductible sans source externe.",
  ),
)
