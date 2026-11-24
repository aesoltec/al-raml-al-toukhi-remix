package com.example.geomancy

/**
 * Les 40 premières leçons complètes (Al-Dars 1 à Al-Dars 40) de
 * « Manbaʿ Uṣūl al-Raml / Al-Durra al-Bahiyya fī al-ʿUlūm al-Ramliyya »
 * de Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī.
 */
internal val manbaL1To10Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 1",
      heading = "Définition du ʿIlm al-Raml, légitimité et origine d'Idrīs",
      arabicTerms = "الدرس الأول : تعريف علم الرمل وأصله عند إدريس عليه السلام",
      body =
        "Al-Ṭūkhī définit la géomancie comme la science qui extrait les indices cachés à partir des points tracés sur le sable ou le papier, rattachée dans la tradition islamique à l'enseignement reçu par le prophète Idrīs (Hermès).",
      keyFormulaOrTable = "Fondement : observation ordonnée des 16 combinaisons binaires.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 2",
      heading = "Pureté rituelle (Ṭahāra), Qibla & Formulation de la Niyya",
      arabicTerms = "الدرس الثاني : الطهارة واستقبال القبلة وحضور النية",
      body =
        "Le praticien se purifie, s'oriente vers la Qibla, apaise son cœur et formule une intention unique et précise (Niyya) avant de toucher le stylet ou le sable.",
      keyFormulaOrTable = "Condition : une seule intention claire par tirage · silence et recueillement.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 3",
      heading = "Le Point Primordial (Al-Nuqṭa) : Principe de l'Unité",
      arabicTerms = "الدرس الثالث : النقطة وأصل الأشكال",
      body =
        "Tout le système procède du point unique (Nuqṭa). Du point naît la ligne, de la ligne le couple pair/impair, et de quatre rangs superposés naît l'alphabet géomantique complet.",
      keyFormulaOrTable = "Nuqṭa (1) → Khaṭṭ (ligne) → Shakl (4 rangs).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 4",
      heading = "Fard (Impair = 1) vs Zawj (Pair = 2) : Wujūd & ʿAdam",
      arabicTerms = "الدرس الرابع : الفرد والزوج (الوجود والعدم)",
      body =
        "Le reste impair (Fard = •, valeur 1) exprime l'existence manifestée (Wujūd), l'élan et la lumière ; le reste pair (Zawj = ••, valeur 2) exprime la latence (ʿAdam), l'accueil et la forme.",
      keyFormulaOrTable = "Fard = 1 point (actif, impair) · Zawj = 2 points (réceptif, pair).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 5",
      heading = "Les Quatre Rangs d'une Figure : Feu, Air, Eau, Terre",
      arabicTerms = "الدرس الخامس : مراتب الشكل الأربع (نار، هواء، ماء، تراب)",
      body =
        "De haut en bas, toute figure possède quatre rangs immuables : 1) Rās / Tête = Feu ; 2) Ṣadr / Poitrine = Air ; 3) Baṭn / Ventre = Eau ; 4) Qadam / Pieds = Terre.",
      keyFormulaOrTable = "Structure verticale : [1:Feu, 2:Air, 3:Eau, 4:Terre] → 2⁴ = 16 figures.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 6",
      heading = "Les Quatre Figures Pures & les Figures Symétriques",
      arabicTerms = "الدرس السادس : الأشكال الأربعة المفردة",
      body =
        "Présentation des figures dont un seul rang est actif ou dont les quatre rangs sont identiques : Al-Ṭarīq (1111), Al-Jamāʿa (2222), Al-Laḥyān (1222), Al-Ḥumra (2122), Al-Bayāḍ (2212) et Al-Inkis (2221).",
      keyFormulaOrTable = "4 rangs ouverts = Ṭarīq · 4 rangs fermés = Jamāʿa · 1 seul rang ouvert = 4 mères élémentaires.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 7",
      heading = "Les Figures Entrantes, Sortantes, Fixes et Mobiles",
      arabicTerms = "الدرس السابع : الداخل والخارج والثابت والمنقلب",
      body =
        "Classement dynamique des 16 figures : les Entrantes (Dākhila) attirent et conservent ; les Sortantes (Khārija) dispersent ou libèrent ; les Fixes (Thābita) stabilisent ; les Mobiles (Munqaliba) font basculer.",
      keyFormulaOrTable = "Entrantes (4) · Sortantes (3) · Fixes (5) · Mobiles (4).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 8",
      heading = "Polarités Fastes (Saʿd), Néfastes (Naḥs) & Mixtes (Mumtazij)",
      arabicTerms = "الدرس الثامن : السعد والنحس والممتزج",
      body =
        "Al-Ṭūkhī détaille quelles figures apportent secours, joie et acquisition (Saʿd), quelles figures signalent épreuve, rupture ou dureté (Naḥs), et quelles figures s'adaptent au voisinage (Mumtazij).",
      keyFormulaOrTable = "Fastes pures : Laḥyān, Qabḍ Dākhil, Nuṣra Dākhila, ʿAtaba Dākhila, Naqī al-Khadd, Bayāḍ.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 9",
      heading = "Sexe, Luminaires & Correspondances Planétaires Primordiales",
      arabicTerms = "الدرس التاسع : الذكر والأنثى والكواكب السبعة",
      body =
        "Répartition des figures en masculines/diurnes et féminines/nocturnes, et attribution de chacune aux sept planètes classiques (Soleil, Lune, Mars, Mercure, Jupiter, Vénus, Saturne) ainsi qu'aux nœuds lunaires.",
      keyFormulaOrTable = "Exemple : Jupiter = Laḥyān/ʿAtaba D. · Soleil = Nuṣratayn · Mars = Ḥumra/Jūdla · Lune = Bayāḍ/Jamāʿa.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 10",
      heading = "Récapitulatif & Mémorisation des 16 Figures",
      arabicTerms = "الدرس العاشر : تثبيت حفظ الأشكال الستة عشر",
      body =
        "Exercice complet de reconnaissance instantanée : savoir tracer, nommer en arabe, donner l'élément dominant, le mouvement et la planète de n'importe laquelle des 16 figures en un coup d'œil.",
      keyFormulaOrTable = "16 figures × (Parité + Nom + Mouvement + Fortune + Planète).",
    ),
  )

internal val manbaL11To20Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 11",
      heading = "Frapper les 16 Lignes dans le Sable sans Compter",
      arabicTerms = "الدرس الحادي عشر : ضرب الستة عشر خطاً من غير عدّ",
      body =
        "Méthode pratique pour tracer 4 paquets de 4 lignes de droite à gauche d'un geste continu et souple, sans jamais dénombrer mentalement le total pendant la frappe.",
      keyFormulaOrTable = "16 lignes réparties en 4 groupes de 4 rangs (M1, M2, M3, M4).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 12",
      heading = "Réduction par Paires (Isqāṭ al-Azwāj)",
      arabicTerms = "الدرس الثاني عشر : إسقاط النقاط اثنين اثنين",
      body =
        "À la fin du tracé, on élimine les points deux par deux sur chaque ligne : un reste de 1 donne Fard (•) ; un reste nul donne Zawj (••).",
      keyFormulaOrTable = "Compte ligne impair → • (1) · Compte ligne pair → •• (2).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 13",
      heading = "Pose des Quatre Mères (Al-Ummahāt M1–M4)",
      arabicTerms = "الدرس الثالث عشر : وضع الأمهات الأربع",
      body =
        "Les quatre groupes forment respectivement M1 (l'Âme/Demandeur), M2 (les Biens), M3 (les Proches/Démarches) et M4 (le Foyer/Base), alignés de droite à gauche sur la ligne supérieure du Takht.",
      keyFormulaOrTable = "Ligne 1–4 = M1 · Lignes 5–8 = M2 · Lignes 9–12 = M3 · Lignes 13–16 = M4.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 14",
      heading = "Extraction Horizontale des Quatre Filles (Al-Banāt M5–M8)",
      arabicTerms = "الدرس الرابع عشر : استخراج البنات الأربع بالعرض",
      body =
        "M5 prend successivement le rang Feu de M1, M2, M3, M4 ; M6 prend le rang Air de M1..M4 ; M7 prend le rang Eau de M1..M4 ; M8 prend le rang Terre de M1..M4.",
      keyFormulaOrTable = "M5 = [M1.F, M2.F, M3.F, M4.F] · M6 = Air · M7 = Eau · M8 = Terre.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 15",
      heading = "Vérification Croisée des Filles & Symétrie Matrice 4×4",
      arabicTerms = "الدرس الخامس عشر : مطابقة الأمهات والبنات",
      body =
        "Les Mères (colonnes) et les Filles (lignes transposées) forment une matrice carrée 4×4 parfaite : vérifier immédiatement que la tête de M5 vaut celle de M1 et son pied celui de M4.",
      keyFormulaOrTable = "Contrôle visuel : M1..M4 transposées = M5..M8.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 16",
      heading = "Loi de l'Addition Géomantique (Ḍarb al-Ashkāl)",
      arabicTerms = "الدرس السادس عشر : ضرب الأشكال (الفرد والزوج)",
      body =
        "L'opération fondamentale associe deux figures rang par rang : Impair + Impair = Pair (1+1→2) ; Pair + Pair = Pair (2+2→2) ; Impair + Pair = Impair (1+2→1).",
      keyFormulaOrTable = "• + • = •• · •• + •• = •• · • + •• = • (addition modulo 2).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 17",
      heading = "Génération des Quatre Nièces (Al-Ḥafīdāt M9–M12)",
      arabicTerms = "الدرس السابع عشر : استخراج الحفيدات (9 إلى 12)",
      body =
        "En combinant les huit premières figures deux par deux naissent les quatre Nièces : M9 = M1 ⊕ M2, M10 = M3 ⊕ M4, M11 = M5 ⊕ M6, M12 = M7 ⊕ M8.",
      keyFormulaOrTable = "M9 = M1⊕M2 · M10 = M3⊕M4 · M11 = M5⊕M6 · M12 = M7⊕M8.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 18",
      heading = "Engendrement des Deux Témoins (Al-Shāhidān M13 & M14)",
      arabicTerms = "الدرس الثامن عشر : الشاهد الأول (13) والشاهد الثاني (14)",
      body =
        "Le Témoin droit M13 = M9 ⊕ M10 synthétise les Mères et la part du consultant ; le Témoin gauche M14 = M11 ⊕ M12 synthétise les Filles et l'aboutissement objectif.",
      keyFormulaOrTable = "M13 (Témoin droit) = M9⊕M10 · M14 (Témoin gauche) = M11⊕M12.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 19",
      heading = "Le Juge (Al-Mīzān M15) & la Loi de Parité Absolue",
      arabicTerms = "الدرس التاسع عشر : الميزان (الحاكم) ووجوب كونه زوجياً",
      body =
        "M15 = M13 ⊕ M14 tranche le thème. Par nécessité mathématique (la somme de parité sur tout rang des Mères intervenant un nombre pair de fois), M15 a toujours 4, 6 ou 8 points, jamais un total impair.",
      keyFormulaOrTable = "Parité M15 ∈ {4, 6, 8} points (jamais 5 ni 7). Si impair → erreur de calcul.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 20",
      heading = "La Seizième Maison (ʿĀqibat al-ʿĀqiba) & Clôture du Cycle",
      arabicTerms = "الدرس العشرون : البيت السادس عشر (عاقبة العاقبة = M15 ⊕ M1)",
      body =
        "M16 résulte de M15 ⊕ M1 : elle referme le thème sur la source originelle et indique ce qui survient après la décision du Juge. Aucun thème n'est complet sans lire M16.",
      keyFormulaOrTable = "M16 = M15 ⊕ M1 (Retour de la sentence sur le demandeur).",
    ),
  )

internal val manbaL21To30Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 21",
      heading = "Concept Fondamental de Demeure Fixe (Bayt al-Taskīn)",
      arabicTerms = "الدرس الحادي والعشرون : معنى التسكين والفرق بين البيت الأصلي والعارض",
      body =
        "Al-Ṭūkhī distingue le lieu accidentel (où tombe une figure dans un tirage) de sa demeure fixe dans le Taskīn (où elle règne naturellement). Lorsqu'une figure tombe dans sa propre maison de Taskīn, sa force double.",
      keyFormulaOrTable = "Coïncidence Bayt al-Ṭālib == Bayt al-Taskīn → signal fort (Qawiyya).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 22",
      heading = "Le Taskīn Naturel d'Al-Zanātī (1 à 16)",
      arabicTerms = "الدرس الثاني والعشرون : تسكين الزناتي المشهور",
      body =
        "Exposé du grand ordre classique d'al-Zanātī adopté comme socle général de l'application, ouvrant sur Al-Laḥyān en Maison 1 jusqu'à Al-Ṭarīq (ou Al-Jamāʿa/Ijtimāʿ selon l'alignement) en fin de série.",
      keyFormulaOrTable = "Usage principal : jugements généraux, dignités des maisons et Bāṭin al-Takht.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 23",
      heading = "Le Taskīn d'Al-Ḥadbaʾ (تسكين الحدباء)",
      arabicTerms = "الدرس الثالث والعشرون : تسكين الحدباء وترتيبه",
      body =
        "Étude de la formule mnémonique classique « Ḥadbaʾ » qui classe les figures selon les bascules binaires de leur tête et de leurs rangs internes pour la recherche des lettres et des temps.",
      keyFormulaOrTable = "Clef mnémonique : suite structurée des initiales consonantiques arabes.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 24",
      heading = "Le Taskīn d'ʿAlkhad (تسكين ألخد)",
      arabicTerms = "الدرس الرابع والعشرون : تسكين ألخد واستعماله",
      body =
        "Deuxième grande formule mnémonique des maîtres orientaux permettant de retrouver l'ordre des 16 figures sans table écrite et de vérifier les oppositions internes entre maisons.",
      keyFormulaOrTable = "Usage : mémorisation rapide des correspondances entre les 16 figures.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 25",
      heading = "Le Taskīn de Sakzaj (تسكين سكذج) & des Éléments",
      arabicTerms = "الدرس الخامس والعشرون : تسكين سكذج وتسكين الطبائع",
      body =
        "Troisième grand classement regroupant les figures par affinité élémentaire (Feu avec Feu, Air avec Air, Eau avec Eau, Terre avec Terre), utilisé pour évaluer les alliances ou rivalités.",
      keyFormulaOrTable = "Regroupement par quatuors élémentaires : Nāriyyāt · Hawāʾiyyāt · Māʾiyyāt · Turābiyyāt.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 26",
      heading = "Le Taskīn d'Abdaḥ (1, 2, 4, 8) & Numération Binaire",
      arabicTerms = "الدرس السادس والعشرون : تسكين أبده (1-2-4-8)",
      body =
        "Chaque rang pair ou impair reçoit un poids géométrique (1 pour Feu, 2 pour Air, 4 pour Eau, 8 pour Terre), créant une bijection mathématique exacte entre les 16 figures et les rangs 1 à 16.",
      keyFormulaOrTable = "Rangs actifs × (1, 2, 4, 8) → index unique de 0 à 15.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 27",
      heading = "Correspondance des 16 Figures aux Lettres Arabes",
      arabicTerms = "الدرس السابع والعشرون : حروف الأشكال الستة عشر",
      body =
        "Attribution des lettres de l'alphabet arabe (Alif à Ghayn selon les tables d'al-Ṭūkhī) aux 16 figures afin de faire parler le Takht sur des noms de personnes, de villes ou de choses.",
      keyFormulaOrTable = "Chaque figure porte sa lettre directrice et ses lettres subsidiaires.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 28",
      heading = "Calcul Abjad (Ḥisāb al-Jummal) de 1 à 1000",
      arabicTerms = "الدرس الثامن والعشرون : حساب الجُمّل الكبير والصغير",
      body =
        "Rappel complet de l'ordre Abjad (Abjad Hawwaz Ḥuṭṭī Kalaman Saʿfaṣ Qarashat Thakhadh Ḍaẓagh) : unités 1..9, dizaines 10..90, centaines 100..900 et 1000, utilisé pour convertir un nom en figure ou Maison.",
      keyFormulaOrTable = "Abjad (ا=1, ب=2, ج=3, د=4... ق=100, ر=200, ش=300... غ=1000) mod 16.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 29",
      heading = "Conversion des Rangs Actifs en Syllabes & Noms",
      arabicTerms = "الدرس التاسع والعشرون : استنطاق الحروف من النقط المفتوحة",
      body =
        "Comment lire les rangs impairs (Fard) des figures dominantes M1, M15 et M16 pour déterminer les initiales et la sonorité dominante d'un nom recherché.",
      keyFormulaOrTable = "Croiser les rangs impairs + lettres du Taskīn al-Ḥurūf.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 30",
      heading = "Vérification du Nom du Consultant avant la Sentence",
      arabicTerms = "الدرس الثلاثون : مطابقة اسم الطالب والطالع",
      body =
        "Application pratique : calculer le reste mod 16 du prénom du consultant (et de sa mère si connu) et le confronter à la maison d'ouverture M1 ou au Mustawlī pour valider la concordance du tirage.",
      keyFormulaOrTable = "Somme Abjad(Prénom) mod 16 → comparaison avec M1 et M15.",
    ),
  )

internal val manbaL31To40Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Leçon 31",
      heading = "Les 4 Piliers Cardinaux (Al-Awtād : M1, M4, M7, M10)",
      arabicTerms = "الدرس الحادي والثلاثون : الأوتاد الأربعة وقوتها",
      body =
        "M1 (L'Âme/Demandeur), M4 (Les Racines/Père/Fin cachée), M7 (L'Autre/Conjoint/Adversaire) et M10 (L'Autorité/Métier/Honneur) forment la croix centrale : toute figure qui y siège agit pleinement au présent.",
      keyFormulaOrTable = "Awtād = Présent actif · M1 (Soi) · M4 (Base) · M7 (Autre) · M10 (Sommet).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 32",
      heading = "Les 4 Maisons Succédentes (Mā Yalī al-Awtād : M2, M5, M8, M11)",
      arabicTerms = "الدرس الثاني والثلاثون : ما يلي الأوتاد (المستقبل القريب)",
      body =
        "M2 (Biens et gains), M5 (Enfants, joie et nouvelles), M8 (Héritage, angoisse et pertes) et M11 (Espoirs et alliés) décrivent le devenir immédiat et les appuis ou faiblesses matérielles.",
      keyFormulaOrTable = "Succédentes = Futur proche et moyens concrets.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 33",
      heading = "Les 4 Maisons Cadentes (Al-Sawāqiṭ : M3, M6, M9, M12)",
      arabicTerms = "الدرس الثالث والثلاثون : السواقط الأربع والماضي",
      body =
        "M3 (Frères et petits trajets), M6 (Maladies et serviteurs), M9 (Grands voyages, savoir, foi) et M12 (Ennemis secrets, prisons, épreuves) renvoient à ce qui passe, s'éloigne ou demande vigilance.",
      keyFormulaOrTable = "Cadentes = Passé récent, déplacements et zones d'ombre.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 34",
      heading = "Maisons 1 à 6 en Détail : Moi, Richesse, Entourage, Foyer, Joie, Épreuve",
      arabicTerms = "الدرس الرابع والثلاثون : دلالات البيوت من الأول إلى السادس",
      body =
        "Inventaire complet des champs d'application des six premières maisons : morphologie et humeur en M1, bourse et bétail en M2, lettres et voisins en M3, terres et fin d'affaire en M4, grossesse et plaisir en M5, santé et subordonnés en M6.",
      keyFormulaOrTable = "Hémisphère personnel : M1..M6 (Sujet, Ressources, Proches, Foyer, Création, Charge).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 35",
      heading = "Maisons 7 à 12 en Détail : Autrui, Crise, Voyage, Pouvoir, Espoir, Épreuve",
      arabicTerms = "الدرس الخامس والثلاثون : دلالات البيوت من السابع إلى الثاني عشر",
      body =
        "Inventaire des maisons relationnelles et sociales : M7 (époux, associé, voleur, adversaire), M8 (peurs, legs, mort), M9 (pèlerinage, rêves, science), M10 (roi, juge, carrière), M11 (amis, chance espérée), M12 (adversités cachées).",
      keyFormulaOrTable = "Hémisphère relationnel : M7..M12 (Autrui, Crise, Horizon, Rang, Appui, Voile).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 36",
      heading = "Les Quatre Mawāzīn Finaux : M13, M14, M15 & M16",
      arabicTerms = "الدرس السادس والثلاثون : السائل (13)، المسؤول عنه (14)، الميزان (15)، العاقبة (16)",
      body =
        "M13 résume l'état et le bagage du demandeur ; M14 l'état de l'objet demandé ; M15 rend la sentence finale ; M16 montre l'écho ultime de cette sentence sur M1.",
      keyFormulaOrTable = "Chaîne de décision : M13 (Demandeur) + M14 (Demande) → M15 (Arrêt) → M16 (Suite).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 37",
      heading = "Amitiés & Contrariétés Élémentaires entre Maisons",
      arabicTerms = "الدرس السابع والثلاثون : الصداقة والعداوة بين العناصر الأربعة",
      body =
        "Feu (Chaud/Sec) et Air (Chaud/Humide) s'entraident par la chaleur commune ; Eau (Froid/Humide) et Terre (Froid/Sec) s'accordent par le froid commun ; Feu contre Eau et Air contre Terre engendrent friction.",
      keyFormulaOrTable = "Affinités : (Feu ↔ Air) & (Eau ↔ Terre) · Oppositions : (Feu ⚔ Eau) & (Air ⚔ Terre).",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 38",
      heading = "Les Quatre Humeurs Corporelles dans le Takht",
      arabicTerms = "الدرس الثامن والثلاثون : الأخلاط الأربعة (الصفراء، الدم، البلغم، السوداء)",
      body =
        "Traduction médicale et psychologique des figures : la prédominance du Feu indique excès bilieux/fièvre ; l'Air indique pléthore sanguine ; l'Eau signale humidité/flegme ; la Terre traduit sécheresse mélancolique.",
      keyFormulaOrTable = "Feu = Bile jaune · Air = Sang · Eau = Flegme · Terre = Atrabile.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 39",
      heading = "Vitesse d'Exécution selon l'Élément & le Mouvement",
      arabicTerms = "الدرس التاسع والثلاثون : سرعة التنفيذ وبطؤه",
      body =
        "Les figures Mobiles et de Feu/Air accélèrent l'événement ; les figures Entrantes consolident sur place ; les figures Fixes et de Terre prolongent ou figent la situation.",
      keyFormulaOrTable = "Rapide = Mobiles + Feu/Air · Moyen = Entrantes/Sortantes · Lent = Fixes + Terre.",
    ),
    CourseLessonSection(
      lessonRef = "Leçon 40",
      heading = "Synthèse des 40 Premières Leçons : Diagnostic d'Ouverture",
      arabicTerms = "الدرس الأربعون : خلاصة النصف الأول من المنبع",
      body =
        "Protocole de contrôle appris à mi-parcours : dresser le Takht sans erreur de parité sur M15, identifier les Awtād, évaluer la force élémentaire globale et vérifier les sympathies entre M1, la maison du sujet et M15.",
      keyFormulaOrTable = "Bilan 1–40 : Structure complète + Parité M15 + Carte des Awtād + Équilibre des 4 éléments.",
    ),
  )
