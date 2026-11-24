package com.example.geomancy

/**
 * Traductions françaises fidèles des manuscrits :
 * - « Ḥāwī al-Akhbār wa Kanz al-Asrār » (Shaykh Ḥāzim al-Maqdisī & al-Fāsī)
 * - « Kitāb fī ʿIlm al-Raml wa-l-Aḥkām » (MS 2380 — Al-Rūmānī, 1091 H)
 * - « Kitāb fī ʿIlm al-Raml » (MS 2405 — Compilation mecquoise)
 */
internal val hawiM1Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Urjūza · pp. 2 & 28–35",
      heading = "La Qaṣīda « Dhāt al-Durar » et la Chaîne des Maîtres",
      arabicTerms = "أرجوزة ذات الدرر (المعزّي)، الزناتي، طمطم الهندي، خلف البربري، الطرابلسي",
      body =
        "Dans l'ouverture de « Ḥāwī al-Akhbār wa Kanz al-Asrār », Shaykh Ḥāzim al-Maqdisī recueille les vers mnémoniques de l'Urjūza « Dhāt al-Durar » (attribuée à Abū Bakr b. Muḥammad al-Maghribī al-Fāsī). Le poème résume l'enseignement transmis depuis Idrīs à travers les quatre grands maîtres : Abū ʿAbd Allāh al-Zanātī, Ṭumṭum al-Hindī, Khalaf al-Barbarī et al-Ṭarābulusī.",
      keyFormulaOrTable =
        "Ordre versifié de l'Urjūza : pureté d'intention → correspondances des 16 figures → heures planétaires → lecture des 16 maisons.",
    ),
    CourseLessonSection(
      lessonRef = "Doctrine · pp. 36–44",
      heading = "Concordance Planétaire, Lumière & Obscurité des Figures",
      arabicTerms = "موافقة الكوكب للساعة، الأشكال النيرانية والمظلمة",
      body =
        "Al-Maqdisī insiste sur la vérification systématique entre le maître de l'heure (Ṣāḥib al-Sāʿa) et la planète régissant la maison questionnée. Il distingue les figures lumineuses et ouvertes (Bayāḍ, Laḥyān, Nuṣra Dākhila, Nuṣra Khārija) qui révèlent la chose au grand jour, des figures sombres ou nocturnes (Inkis, ʿUqla, Ijtimāʿ caché) qui travaillent dans l'ombre.",
      keyFormulaOrTable =
        "Règle horaire : Planète de l'heure ≅ Planète du Significateur → accélération et clarté ; opposition → retard ou déviation.",
    ),
  )

internal val hawiM2Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Roues · pp. 112–113",
      heading = "Les Cercles des 4 Mawāzīn : Naẓar, Nuṭq, Ittiṣāl & Infiṣāl",
      arabicTerms = "موازين النظر (⊕ اللحيان)، النطق (⊕ الحمرة)، الاتصال (⊕ البياض)، الانفصال (⊕ الإنكيس)",
      body =
        "Les célèbres diagrammes circulaires des pages 112–113 montrent comment chaque figure engendre ses quatre visages relationnels par bascule d'un seul rang élémentaire : 1) Naẓar (le Regard / l'intention) par addition binaire avec Al-Laḥyān (1222), qui retourne le rang de Feu ; 2) Nuṭq (la Parole / l'annonce) ⊕ Al-Ḥumra (2122), qui retourne le rang d'Air ; 3) Ittiṣāl (la Jonction / l'accord) ⊕ Al-Bayāḍ (2212), qui retourne le rang d'Eau ; 4) Infiṣāl (la Rupture) ⊕ Al-Inkis (2221), qui retourne le rang de Terre.",
      keyFormulaOrTable =
        "Naẓar(F) = F ⊕ 1222 · Nuṭq(F) = F ⊕ 2122 · Ittiṣāl(F) = F ⊕ 2212 · Infiṣāl(F) = F ⊕ 2221.",
    ),
    CourseLessonSection(
      lessonRef = "Symétries · pp. 144 & 148",
      heading = "Al-Ḍidd (Opposé Total), Miroir Aṣḥāb & Taḍāʿuf al-ʿAdad",
      arabicTerms = "الضد (⊕ الطريق)، الأصحاب (العكس العمودي)، تضاعف العدد، بيت وميزان",
      body =
        "À côté des quatre Mawāzīn, le traité étudie trois transformations structurelles : Al-Ḍidd (p. 144), obtenu en inversant les quatre rangs d'une figure (F ⊕ Al-Ṭarīq 1111) pour connaître l'adversaire ou le contraire absolu ; Al-Aṣḥāb ou miroir vertical (p. 148), qui renverse la figure de bas en haut (Terre↔Feu, Eau↔Air) ; et le principe du Taḍāʿuf al-ʿAdad (« doublement numérique » / Bayt wa-Mīzān) pour mesurer la répétition croissante d'une même tendance.",
      keyFormulaOrTable =
        "Ḍidd(F) = F ⊕ 1111 · Miroir Aṣḥāb([f, a, e, t]) = [t, e, a, f] · Bayt wa-Mīzān = Maison ⊕ son gouverneur.",
    ),
  )

internal val hawiM3Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Al-ʿAskarayn · pp. 114–115",
      heading = "Règle Militaire des Deux Armées (Camp A M1⊕M2 vs Camp B M7⊕M8)",
      arabicTerms = "قاعدة العسكرين (M1⊕M2 ضد M7⊕M8)، الجماعة صلح",
      body =
        "Pour tout duel, procès ou bataille (« Bāb al-ʿAskarayn »), al-Maqdisī réduit chaque camp à sa résultante binaire fondatrice : l'Armée du Demandeur = M1 ⊕ M2 (soit la Nièce M9) et l'Armée de l'Adversaire = M7 ⊕ M8 (soit la Nièce M12). L'affrontement direct entre ces deux composites révèle l'avantage tactique ; la présence d'Al-Jamāʿa (2222) entre les deux armées annonce trêve, compromis ou match nul.",
      keyFormulaOrTable =
        "Armée A = M1 ⊕ M2 (= M9) vs Armée B = M7 ⊕ M8 (= M12) · Apparition de M15 ou Jamāʿa médiane = armistice / nul.",
    ),
    CourseLessonSection(
      lessonRef = "Hiérarchie & Corps · pp. 61–62, 155",
      heading = "Hiérarchie Quaternaire (Qawiyya / Mumtazija) & Taskīn al-Aʿḍāʾ",
      arabicTerms = "المراتب الأربع (قوية سعيدة، ممتزجة، ضعيفة، نحسة)، تسكين الأعضاء",
      body =
        "À la page 155, les 16 figures sont classées en quatre degrés de puissance : 1) Fortes Fastes (Laḥyān, Qabḍ Dākhil, Nuṣra Dākhila, ʿAtaba Dākhila) ; 2) Mixtes Fastes (Bayāḍ, Naqī al-Khadd, Nuṣra Khārija, Ijtimāʿ) ; 3) Mixtes ou Instables (Ṭarīq, Jamāʿa, Jūdla, ʿUqla) ; 4) Fortes Néfastes (Ḥumra, Inkis, ʿAtaba Khārija, Qabḍ Khārij). Les pp. 61–62 associent enfin chaque figure à une partie du corps humain (Taskīn al-Aʿḍāʾ) pour localiser une blessure ou un mal.",
      keyFormulaOrTable =
        "Degrés p. 155 : 4 Fortes Fastes > 4 Mixtes Fastes > 4 Intermédiaires > 4 Défavorables · Taskīn al-Aʿḍāʾ = Tête (Feu) à Pieds (Terre).",
    ),
  )

internal val ms2380M1Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Folio 1 · Sommaire",
      heading = "Fihrist Authentique du Manuscrit MS 2380 (68 Feuillets)",
      arabicTerms = "مخطوط جامعة الملك سعود 2380 — المحمود المكي الروماني (1091 هـ)",
      body =
        "Le premier feuillet du manuscrit MS 2380 (copié et possédé par al-Ḥājj Maḥmūd Muḥammad Yūnus al-Makkī al-Rūmābī / al-Rūmānī en 1091 H) s'ouvre sur une table détaillée des traités réunis : médecine et humeurs par le sable, extraction du Ḍamīr, mariage et entente, victoires et défaites (Al-Ghālib wa-l-Maghlūb), vols et biens perdus, objet enfoui (Khabīʾa) et jugements des 16 figures à travers les 16 maisons.",
      keyFormulaOrTable =
        "Colophon : 68 feuillets · La Mecque · 1091 H / 1680 · Corpus complet allant des principes binaires aux cas datés.",
    ),
    CourseLessonSection(
      lessonRef = "Folios 13 & 28–36",
      heading = "Doctrine Wujūd wa-l-ʿAdam & Séquences des 16 Maisons",
      arabicTerms = "الوجود (نقطة فرد) والعدم (نقطتان زوج)، نور الرمل، أحكام البيوت",
      body =
        "Al-Rūmānī fonde la valeur des figures sur le principe ontologique du Wujūd (Existence / Lumière, représentée par le point impair Fard = 1) opposé au ʿAdam (Absence / Voile, représenté par les deux points pairs Zawj = 2). Sur les feuillets 28 à 36, il décrit la modification de sens de chacune des 16 figures lorsqu'elle habite successivement chacune des 16 maisons, notant qu'une figure de lumière en M6 (épreuves), M8 (pertes) ou M12 (ennemis) voit son rayonnement inversé ou retardé.",
      keyFormulaOrTable =
        "Wujūd (1) = Manifestation active · ʿAdam (2) = Réserve latente · Al-Ṭarīq = 4 Wujūd · Al-Jamāʿa = 4 ʿAdam.",
    ),
  )

internal val ms2380M2Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Page 36 PDF · Archives",
      heading = "Le Duel Daté « Muḥammad Luṭfī contre Muḥammad Sulaymān »",
      arabicTerms = "مسألة الغالب والمغلوب بين محمد لطفي ومحمد سليمان (ص 36)",
      body =
        "Document exceptionnel conservé à la page 36 du PDF du MS 2380 : le praticien a consigné dans la marge même du manuscrit plusieurs consultations datées, dont l'affrontement nominal entre « Muḥammad Luṭfī » et « Muḥammad Sulaymān », ainsi que les tirages nominatifs pour « ʿĀʾisha », « Ḥasan al-Zaynī » et « ʿAbd al-Munʿim », illustrant la règle d'inscrire le nom exact des parties avant le tracé.",
      keyFormulaOrTable =
        "Protocole du copiste : 1) Nommer explicitement les deux parties · 2) Dater le tirage · 3) Archiver l'issue vérifiée.",
    ),
    CourseLessonSection(
      lessonRef = "Méthode Al-Zanātī",
      heading = "Arbitrage Croisé (M1 ⊕ M10 vs M7 ⊕ M4) dans MS 2380",
      arabicTerms = "قاعدة الزناتي في المبارزة (M1⊕M10 ضد M7⊕M4)",
      body =
        "Pour trancher ces cas concrets, le manuscrit croise la méthode d'al-Zanātī : d'un côté le Demandeur uni à son Autorité/Couronne (M1 ⊕ M10) face à l'Adversaire uni à sa Base cachée (M7 ⊕ M4), puis vérification de la translation du Témoin (M13 ou M14) vers le Juge (M15). C'est précisément ce calcul croisé qui est intégré dans notre module Al-Qawl / MS 2380.",
      keyFormulaOrTable =
        "Croisement Al-Zanātī : Axe Camp A = (M1 ⊕ M10) vs Axe Camp B = (M7 ⊕ M4), départagés par M15.",
    ),
  )

internal val ms2380M3Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Folio 53",
      heading = "Échelle Temporelle d'Al-Ṭarābulusī (1 Jour à 10 Ans) & Nār al-Nār",
      arabicTerms = "مقياس الطرابلسي للأزمنة، نار النار، هواء الهواء",
      body =
        "Au folio 53, le MS 2380 transcrit la table des délais attribuée à al-Ṭarābulusī : chaque maison et figure reçoit une unité de temps croissante allant de l'immédiat (1 jour en M1 pour une figure rapide) à plusieurs semaines ou mois dans les maisons centrales, jusqu'aux cycles pluriannuels (jusqu'à 10 ans en M15 pour les figures Fixes et terrestres). Il y adjoint le calcul du Nār al-Nār (pureté élémentaire) pour mesurer l'intensité de l'événement.",
      keyFormulaOrTable =
        "Échelle d'Al-Ṭarābulusī : M1 (1 jour) → M2–M5 (semaines) → M6–M12 (mois) → M13–M15 (1 à 10 ans selon fixité).",
    ),
    CourseLessonSection(
      lessonRef = "Folio 54",
      heading = "Taskīn al-Qīrāṭ (Poids & Mesures) & Taskīn al-Madīna (Géographie)",
      arabicTerms = "تسكين القيراط والمكاييل، تسكين المدينة والجهات الأربع",
      body =
        "Le folio 54 rassemble deux tableaux utilitaires indispensables dans la pratique mecquoise : le Taskīn al-Qīrāṭ, qui convertit la densité de points d'un groupe de maisons en fractions de poids, prix ou parts (sur une base de 24 qīrāṭ), et le Taskīn al-Madīna, qui associe aux 16 figures les directions cardinales et les grandes provinces pour localiser un voyageur, un objet dérobé ou un courrier.",
      keyFormulaOrTable =
        "Taskīn al-Qīrāṭ : ratio / 24 parts · Taskīn al-Madīna : Est (Feu) · Ouest (Air) · Nord (Eau) · Sud (Terre).",
    ),
  )

internal val ms2405M1Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Pages 67–68 PDF",
      heading = "Suite des Nombres Triangulaires (1, 3, 6, 10, ..., 136)",
      arabicTerms = "الأعداد المثلثة في الرمل (من 1 إلى 136)، حساب الكميات والأعمار",
      body =
        "Dans les folios finaux du manuscrit MS 2405 (pp. 67–68 PDF), le compilateur associe les 16 figures ordonnées à la progression arithmétique des nombres triangulaires T_n = n(n+1)/2 : 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66, 78, 91, 105, 120 et 136. Cette table sert à évaluer l'âge d'une personne, le montant d'un trésor ou la durée exacte d'un cycle.",
      keyFormulaOrTable =
        "T(n) = n(n+1)/2 : 1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 66, 78, 91, 105, 120, 136.",
    ),
    CourseLessonSection(
      lessonRef = "Pages 69–71 PDF",
      heading = "Roue des 28 Manses Lunaires (Manāzil al-Qamar) & 12 Signes",
      arabicTerms = "دائرة منازل القمر الثمانية والعشرين (الشرطين إلى بطن الحوت) والبروج الاثني عشر",
      body =
        "Les trois dernières planches du manuscrit forment une grande roue astronomico-ramlique reliant les 16 figures aux 12 signes du zodiaque et aux 28 stations lunaires (d'Al-Sharaṭān jusqu'à Baṭn al-Ḥūt). En faisant correspondre la lune du jour (manzil) avec la figure dominante du Takht, on vérifie si l'influence céleste favorise les voyages, les semailles, le négoce ou les traités.",
      keyFormulaOrTable =
        "Roue lunaire : 28 Manāzil al-Qamar répartis par groupes de 2⅓ par signe zodiacal (Feu, Terre, Air, Eau).",
    ),
  )

internal val ms2405M2Lessons: List<CourseLessonSection> =
  listOf(
    CourseLessonSection(
      lessonRef = "Corps MS 2405 · Climats",
      heading = "Les 7 Climats Géographiques (Al-Aqālīm al-Sabʿa) & Planètes",
      arabicTerms = "الأقاليم السبعة (الشام، الروم، العراق، مصر، المغرب، الترك، الحبش)",
      body =
        "Suivant la géographie ptoléméenne islamique, le MS 2405 répartit le monde habité en 7 Climats rattachés chacun à l'une des 7 planètes et à un groupe de figures géomantiques : Iqlīm al-Ḥabash (Saturne), Miṣr, al-Shām (Soleil/Vénus), al-ʿIrāq (Jupiter), al-Rūm, al-Maghrib (Lune) et al-Turk (Mars), permettant d'orienter les questions de provenance et d'état des régions.",
      keyFormulaOrTable =
        "7 Climats × 7 Planètes → Chaque figure hérite de l'indice climatique de sa planète régente.",
    ),
    CourseLessonSection(
      lessonRef = "ff. 124–125 · Masāʾil",
      heading = "Les 20+ Questions Classiques d'Al-Bastī & d'Abū Saʿīd al-Ḥabashī",
      arabicTerms = "مسائل محمد البستي وأبي سعيد الحبشي، الأسعار، الحوائج، المحابيس",
      body =
        "Le corps du manuscrit réunit plus de vingt règles pratiques attribuées nommément à Muḥammad al-Bastī (ff. 124–125) et à Abū Saʿīd al-Ḥabashī (f. 124) : fluctuation des prix du blé et du marché (al-Asʿār : les Entrantes et la Terre retiennent/haussent, les Sortantes font baisser), accueil des requêtes auprès du prince (al-Ḥawāʾij : accord M1/M10/M11), évasion ou libération, dettes, créances et litiges fonciers.",
      keyFormulaOrTable =
        "Mercuriales (Al-Asʿār) : Entrantes + Terre = Hausse / Stockage · Sortantes + Feu = Baisse / Écoulement.",
    ),
  )
