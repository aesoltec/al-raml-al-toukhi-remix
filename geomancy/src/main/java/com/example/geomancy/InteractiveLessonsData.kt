package com.example.geomancy

/**
 * DonnÃ©es des leÃ§ons interactives pour l'application Al-Raml Al-Toukhi.
 * Terminologie unifiÃ©e : "NomOuestAfricain (NomArabe)".
 * Contenu pÃ©dagogique simplifiÃ© pour les dÃ©butants (enfants de maternelle).
 */
object InteractiveLessonsData {

  val lessons: List<InteractiveLesson> = listOf(

    // ============================================================
    // LEÃ‡ON 1 : Les fondements de la gÃ©omancie
    // ============================================================
    InteractiveLesson(
      id = "lesson_1_fondements",
      title = "Les fondements de la gÃ©omancie",
      arabicTitle = "Ø£Ø³Ø§Ø³ Ø¹Ù„Ù… Ø§Ù„Ø±Ù…Ù„",
      level = 1,
      content = """
        La gÃ©omancie (Ilm al-Raml) est l'art de diviner par le sable. 
        Le mot arabe "raml" signifie "sable". C'est la science qui consiste Ã  tracer des points dans le sable, 
        Ã  les rÃ©duire par paires, et Ã  interprÃ©ter les figures qui en rÃ©sultent.

        PRINCIPE BINAIRE FONDAMENTAL :
        - 1 = point simple (impair, Khalwah â€” "l'ouvert")
        - 2 = point doublÃ© (pair, Zawjiyyah â€” "le jumelÃ©")

        Chaque figure est composÃ©e de 4 lignes lues de haut en bas :
        1. Feu (Ù†Ø§Ø±, NÄr) â€” la TÃªte
        2. Air (Ù‡ÙˆØ§Ø¡, HawÄÊ¾) â€” le Cou
        3. Eau (Ù…Ø§Ø¡, MÄÊ¾) â€” le Ventre
        4. Terre (ØªØ±Ø§Ø¨, TurÄb) â€” le Pied
        MnÃ©motechnique : NHMT (Ù†Ù‡Ù…Øª).

        LA LOI DE COPULATION (addition) :
        - impair + impair = pair â†’ 1 + 1 = 2
        - pair + pair = pair â†’ 2 + 2 = 2
        - impair + pair = impair â†’ 1 + 2 = 1
        - pair + impair = impair â†’ 2 + 1 = 1

        Cette rÃ¨gle est universelle et ne souffre aucune exception.
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : la figure Djanfa Almamy (Ø§Ù„Ø¬ÙˆØ¯Ù„Ø©) a le code 1121 = 1 (impair) + 1 (impair) + 2 (pair) + 1 (impair)",
        "Exemple : la figure Moussa (Ø§Ù„Ø¬Ù…Ø§Ø¹Ø©) a le code 2222 = 2 (pair) + 2 (pair) + 2 (pair) + 2 (pair)",
      ),
      exercises = listOf(
        "Trace Ã  la main les 4 lignes de la figure 1121 (Djanfa Almamy). VÃ©rifie que la ligne 1 est un point simple, la ligne 2 un point simple, la ligne 3 deux points, la ligne 4 un point simple.",
        "Trace Ã  la main les 4 lignes de la figure 2222 (Moussa). VÃ©rifie que toutes les lignes sont des points doublÃ©s.",
      ),
      visualAids = listOf(
        "1111 = â€¢ â€¢ â€¢ â€¢ (Taliki)",
        "1112 = â€¢ â€¢ â€¢ â€¢â€¢ (Lawsana)",
        "1121 = â€¢ â€¢ â€¢â€¢ â€¢ (Djanfa Almamy)",
        "1122 = â€¢ â€¢ â€¢â€¢ â€¢â€¢ (Kalalaw)",
        "1211 = â€¢ â€¢â€¢ â€¢ â€¢ (Tontigui)",
        "1212 = â€¢ â€¢â€¢ â€¢ â€¢â€¢ (Inzan)",
        "1221 = â€¢ â€¢â€¢ â€¢â€¢ â€¢ (Solomana)",
        "1222 = â€¢ â€¢â€¢ â€¢â€¢ â€¢â€¢ (Adama)",
        "2111 = â€¢â€¢ â€¢ â€¢ â€¢ (Malidjou)",
        "2112 = â€¢â€¢ â€¢ â€¢ â€¢â€¢ (Ali)",
        "2121 = â€¢â€¢ â€¢ â€¢â€¢ â€¢ (Ousmana)",
        "2122 = â€¢â€¢ â€¢ â€¢â€¢ â€¢â€¢ (Lomara)",
        "2211 = â€¢â€¢ â€¢â€¢ â€¢ â€¢ (Nouhoun)",
        "2212 = â€¢â€¢ â€¢â€¢ â€¢ â€¢â€¢ (Bayaro)",
        "2221 = â€¢â€¢ â€¢â€¢ â€¢â€¢ â€¢ (Mangouchi)",
        "2222 = â€¢â€¢ â€¢â€¢ â€¢â€¢ â€¢â€¢ (Moussa)",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 2 : Le tracÃ© dans le sable
    // ============================================================
    InteractiveLesson(
      id = "lesson_2_trace",
      title = "Le tracÃ© dans le sable",
      arabicTitle = "Ø±Ø³Ù… Ø§Ù„Ù†Ù‚Ø§Ø· ÙÙŠ Ø§Ù„Ø±Ù…Ù„",
      level = 1,
      content = """
        Le protocole de tirage selon Cheikh Al-Toukhi (BulÅ«gh al-Amal, BÄb 1) :

        1. PRÃ‰PARATION : S'asseoir Ã  califourchon sur un sol propre et plat. Prendre du sable fin tamisÃ© d'un tas derriÃ¨re soi.
        2. TRACÃ‰ : Tracer avec la main droite un grand nimisa (~50 cm) dans lequel on place un sikÃ© Ã  droite et un kumadibinÃ© Ã  gauche.
        3. RÃ‰CITATION : RÃ©citer les formules appropriÃ©es (istighfÄr, á¹£alÄt al-fÄtiá¸¥, tahlÄ«l).
        4. TRACÃ‰ DES POINTS : Tracer les points sans les compter â€” l'intuition guide la main. Compter ensuite deux par deux ; le reste donne 1 point si impair, 2 si pair.
        5. DIRECTION : Tracer d'est en ouest.
        6. PURETÃ‰ : Le consultant doit Ãªtre en Ã©tat de puretÃ© rituelle, silencieux, concentrÃ©.
        7. TROIS JETS PAR JOUR maximum.

        LES 4 MÃˆRES (UmmahÄt / Matres) :
        Les 4 premiÃ¨res figures obtenues sont les MÃ¨res (M1 Ã  M4). Elles sont alÃ©atoires et placÃ©es de droite Ã  gauche (1Ê³áµ‰ mÃ¨re Ã  l'extrÃªme droite).

        LES FILLES (BanÄt) :
        La 1Ê³áµ‰ Fille reprend la 1Ê³áµ‰ ligne des 4 MÃ¨res, la 2áµ‰ Fille la 2áµ‰ ligne, etc.
        - M5 = Ligne FEU de [M1, M2, M3, M4]
        - M6 = Ligne AIR de [M1, M2, M3, M4]
        - M7 = Ligne EAU de [M1, M2, M3, M4]
        - M8 = Ligne TERRE de [M1, M2, M3, M4]

        LES NIÃˆCES (ZawÄÊ¾id / RadÄ) :
        Additions deux Ã  deux (pair â†’ 2, impair â†’ 1) :
        - M9 = M1 âŠ• M2
        - M10 = M3 âŠ• M4
        - M11 = M5 âŠ• M6
        - M12 = M7 âŠ• M8

        LES TÃ‰MOINS (ShÄhidÄn) :
        - M13 (TÃ©moin Droit / PassÃ©) = M9 âŠ• M10
        - M14 (TÃ©moin Gauche / Avenir) = M11 âŠ• M12

        LE JUGE (QÄá¸Ä«) :
        - M15 = M13 âŠ• M14
        Validation : M15 doit avoir un nombre PAIR de points impairs (0, 2 ou 4). Si impair â†’ thÃ¨me invalide.

        LA SENTENCE (Ê¿Ä€qiba) :
        - M16 = M15 âŠ• M1 (LOI ABSOLUE)
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : MÃ¨res = 1121, 2122, 1211, 2111 â†’ Filles = 1211, 1121, 2211, 1211",
        "Exemple : M9 = 1121 âŠ• 2122 = 1211 (Tontigui)",
      ),
      exercises = listOf(
        "GÃ©nÃ¨re un thÃ¨me avec les MÃ¨res : M1=1121, M2=2122, M3=1211, M4=2111. Calcule les Filles, NiÃ¨ces, TÃ©moins, Juge et Sentence.",
        "VÃ©rifie que M15 a un nombre pair de points impairs.",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 3 : La grille de repos (Taskin)
    // ============================================================
    InteractiveLesson(
      id = "lesson_3_taskin",
      title = "La grille de repos (Taskin al-Falak al-Dair)",
      arabicTitle = "ØªØ³ÙƒÙŠÙ† Ø§Ù„ÙÙ„Ùƒ Ø§Ù„Ø¯Ø§Ø¦Ø±",
      level = 1,
      content = """
        Chaque figure a une maison de repos fixe dans Al-Falak al-Dair (le Ciel Tournant). C'est la grille immuable d'Al-Toukhi :

        M1=1121  M2=1222  M3=2111  M4=2212
        M5=1111  M6=1212  M7=2122  M8=2221
        M9=1122  M10=1221 M11=2112 M12=2211
        M13=1112 M14=1211 M15=2121 M16=2222

        DISPOSITION DE L'Ã‰CU (bouclier du takht) :

        M8  M7  M6  M5  M4  M3  M2  M1
            M12  M11  M10  M9
                M14          M13
                    M15          M16

        RÃ¨gle : une clÃ© de placement n'est pas un Ã©lÃ©ment. La table figure â†’ Ã©lÃ©ment est saisie code par code.

        DOMAINES DES 16 MAISONS :
        - M1 Ascendant/Consultant : Ã‚me, santÃ©, vitalitÃ©
        - M2 Foyer des Biens : Argent, biens, subsistance (Rizq)
        - M3 Entourage Proche : FrÃ¨res/sÅ“urs, voisins, dÃ©placements courts
        - M4 Fonds du Ciel : PÃ¨re, foyer, patrimoine, racines
        - M5 Maison des Enfants : Enfants, grossesse, crÃ©ations
        - M6 Maison du Service : Maladie, obstacles quotidiens
        - M7 Descendant/Conjoint : Conjoint, partenaire, adversaire
        - M8 Maison de la Transformation : Peur, perte, mort, hÃ©ritage
        - M9 Maison des Grands Voyages : Longs voyages, religion, science
        - M10 Milieu du Ciel/AutoritÃ© : Travail, carriÃ¨re, honneur
        - M11 Maison des Espoirs : Amis, appuis, espoirs, gains futurs
        - M12 Maison des Ennemis CachÃ©s : Ennemis cachÃ©s, jalousie, blocages
        - M13 TÃ©moin Droit (PassÃ©) : Origine de l'affaire
        - M14 TÃ©moin Gauche (Futur) : Ã‰volution prochaine
        - M15 Juge SuprÃªme : Verdict global dÃ©finitif
        - M16 Sentence Finale : Conseil divin, consÃ©quence ultime
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M1=1121 (Djanfa Almamy) correspond Ã  la position 1 de la grille Taskin",
        "Exemple : M16=2222 (Moussa) est la Sentence Finale",
      ),
      exercises = listOf(
        "MÃ©morise la grille Taskin al-Falak al-Dair.",
        "Dessine la disposition de l'Ã©cu (bouclier du takht) sur une feuille.",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 4 : La science du Damir
    // ============================================================
    InteractiveLesson(
      id = "lesson_4_damir",
      title = "La science du Damir (l'intention cachÃ©e)",
      arabicTitle = "Ø¹Ù„Ù… Ø§Ù„Ø¶Ù…ÙŠØ±",
      level = 2,
      content = """
        Le Damir (Ø§Ù„Ø¶Ù…ÙŠØ±) est la pensÃ©e secrÃ¨te du consultant. Le maÃ®tre la lit avant que le consultant ait parlÃ©. C'est le cÅ“ur de toute consultation.

        ALGORITHME DU DAMIR (4 Ã©tapes) :

        Ã‰tape 1 â€” Le Miroir ZanatÃ® de M1 :
        - figureM1 = figure tombÃ©e en Maison 1
        - maisonCible = figureM1.maisonZanati (position fixe dans Al-Falak al-Dair)
        - domaineSecret = significationMaison(maisonCible)

        Ã‰tape 2 â€” Les RÃ©pÃ©titions (Confirmation) :
        - Pour chaque maison i de 2 Ã  16 : si maisons[i] == figureM1 â†’ maisonsRepetition.add(i)
        - Plus il y a de rÃ©pÃ©titions, plus le Damir est confirmÃ©.

        Ã‰tape 3 â€” La Part de Fortune / Al-Sahm :
        - totalImpairs = somme de tous les points '1' dans les 16 maisons
        - indexSahm = if (totalImpairs % 16 == 0) 16 else (totalImpairs % 16)
        - figureSahm = maisons[indexSahm - 1]
        - domaineSecretSahm = significationMaison(figureSahm.maisonZanati)

        Ã‰tape 4 â€” L'Ã‰tat Ã‰motionnel (Direction de M1) :
        - ENTRANTE â†’ "PrÃªt Ã  recevoir, cÅ“ur ouvert, en attente"
        - SORTANTE â†’ "Fuite, peur, situation critique, pression"
        - FIXE â†’ "Stagnation, stabilitÃ©, refus d'agir"
        - MOBILE â†’ "InstabilitÃ©, hÃ©sitation, chemin incertain"

        Variante : Damir par Modulo 16 sur les points de la TÃªte :
        - Somme des points simples (impairs) sur la ligne supÃ©rieure (Feu/TÃªte) des MÃ¨res M1â€“M4
        - Modulo 16 ; reste 0 â†’ M16, sinon la maison indiquÃ©e
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M1 = Djanfa Almamy (1121), maisonZanati = M1 â†’ domaine = Ã‚me/santÃ©",
        "Exemple : Si M1 apparaÃ®t en M7, M10 et M15, le Damir est fortement confirmÃ©",
      ),
      exercises = listOf(
        "Calcule le Damir pour un thÃ¨me donnÃ©.",
        "Identifie la Part de Fortune (Al-Sahm) et la maison cible.",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 5 : La mÃ©trologie
    // ============================================================
    InteractiveLesson(
      id = "lesson_5_metrologie",
      title = "La mÃ©trologie et les dimensions",
      arabicTitle = "Ø§Ù„Ù…Ù‚Ø§ÙŠÙŠØ³ ÙˆØ§Ù„Ø£Ø¨Ø¹Ø§Ø¯",
      level = 2,
      content = """
        Al-Toukhi a codifiÃ© des calculs mÃ©trologiques permettant de quantifier l'invisible :

        1. PROFONDEUR (puits, trÃ©sor enfoui) :
           Valeur_Abjad(figureM4) = profondeur en COUDÃ‰ES ROYALES
           1 coudÃ©e royale â‰ˆ 54 cm
           profondeur_cm = abjad(M4) Ã— 54

        2. HAUTEUR (objet, mur, arbre) :
           Valeur_Abjad(figureM9) = hauteur en EMPANS (Shibr)
           1 empan â‰ˆ 22 cm
           hauteur_cm = abjad(M9) Ã— 22

        3. DISTANCE :
           Valeur_Abjad(figureM3) = distance en FARSAKHS
           1 farsakh = 6 km
           distance_km = abjad(M3) Ã— 6

        4. DURÃ‰E D'UN Ã‰VÃ‰NEMENT :
           Direction de M15 :
           - ENTRANTE â†’ dÃ©lai en JOURS = abjad(M15)
           - SORTANTE â†’ dÃ©lai en SEMAINES = abjad(M15)
           - FIXE â†’ dÃ©lai en MOIS = abjad(M15)
           - MOBILE â†’ dÃ©lai en ANNÃ‰ES = abjad(M15) Ã· 10

        5. QUANTITÃ‰ / NOMBRE :
           abjad(figureM2) = quantitÃ© ou somme en DINARS/UNITÃ‰S
           Pour affiner : (abjad(M2) + abjad(M11)) Ã· 2

        6. COULEUR :
           - FEU â†’ Rouge, Orange, Or
           - AIR â†’ Jaune, Vert clair, Blanc lumineux
           - EAU â†’ Bleu, Blanc lunaire, Argent, Vert d'eau
           - TERRE â†’ Brun, Noir, Gris, Ocre

        7. ODEUR :
           - FEU â†’ Ã‚cre, brÃ»lÃ©, Ã©picÃ©, encens fort
           - AIR â†’ Floral, lÃ©ger, parfum discret
           - EAU â†’ Humide, marin, fade, savon
           - TERRE â†’ Terreux, moisi, lourd, racines

        8. MÃ‰TÃ‰O :
           M9 (maison des grands espaces) + M4 (sol/terre) :
           - EAU dominant â†’ Pluie certaine (dÃ©lai = abjad(M9) Ã— 2 jours)
           - AIR dominant â†’ Vent, temps changeant
           - FEU dominant â†’ SÃ©cheresse, chaleur
           - TERRE dominant â†’ Famine, stabilitÃ© froide

        VALEURS ABJAD DES 16 FIGURES :
        - Djanfa Almamy (1121) : 108
        - Lomara (2122) : 109
        - Tontigui (1211) : 36
        - Malidjou (2111) : 36
        - Bayaro (2212) : 37
        - Lawsana (1112) : 270
        - Nouhoun (2211) : 59
        - Kalalaw (1122) : 50
        - Mangouchi (2221) : 44
        - Solomana (1221) : 756
        - Ali (2112) : 108
        - Inzan (1212) : 111
        - Ousmana (2121) : 756
        - Moussa (2222) : 114
        - Taliki (1111) : 460
        - Adama (1222) : [D] (non documentÃ©)
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M4 = 2221 (Mangouchi, abjad=44) â†’ profondeur = 44 Ã— 54 = 2 376 cm â‰ˆ 23,76 m",
        "Exemple : M9 = 1122 (Kalalaw, abjad=50) â†’ hauteur = 50 Ã— 22 = 1 100 cm = 11 m",
      ),
      exercises = listOf(
        "Si M4 = 1121 (Djanfa Almamy, abjad=108), calcule la profondeur du puits en cm.",
        "Si M3 = 1112 (Lawsana, abjad=270), calcule la distance en km.",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 6 : La lecture des tÃ©moins et du juge
    // ============================================================
    InteractiveLesson(
      id = "lesson_6_temoins_juge",
      title = "La lecture des tÃ©moins et du juge",
      arabicTitle = "Ù‚Ø±Ø§Ø¡Ø© Ø§Ù„Ø´Ø§Ù‡Ø¯ÙŠÙ† ÙˆØ§Ù„Ø­Ø§ÙƒÙ…",
      level = 2,
      content = """
        COMBINAISONS M13/M14/M15 â†’ VERDICT :

        | M13 | M14 | M15 | Verdict |
        |-----|-----|-----|---------|
        | BÃ©nÃ©fique | BÃ©nÃ©fique | BÃ©nÃ©fique | SuccÃ¨s total, rÃ©ponse OUI absolue |
        | NÃ©faste | NÃ©faste | NÃ©faste | Ã‰chec total, rÃ©ponse NON absolue |
        | BÃ©nÃ©fique | NÃ©faste | BÃ©nÃ©fique | SuccÃ¨s aprÃ¨s obstacles â€” patience |
        | NÃ©faste | BÃ©nÃ©fique | BÃ©nÃ©fique | Mauvais dÃ©but, bonne fin |
        | BÃ©nÃ©fique | BÃ©nÃ©fique | NÃ©faste | Bon dÃ©but, mauvaise conclusion |
        | NÃ©faste | BÃ©nÃ©fique | NÃ©faste | Lueur d'espoir, verdict dÃ©favorable |
        | BÃ©nÃ©fique | NÃ©faste | NÃ©faste | Effort inutile â€” danger rÃ©el |
        | NÃ©faste | NÃ©faste | BÃ©nÃ©fique | Ã‰preuves puis rÃ©compense finale |

        RÃˆGLE DE M15 (Juge) :
        - ENTRANTE â†’ RÃ©ponse favorable
        - SORTANTE â†’ RÃ©ponse dÃ©favorable, perte, refus
        - FIXE â†’ Stagnation, ni gain ni perte
        - MOBILE â†’ Incertitude totale

        INDICATEURS CLÃ‰S â€” LECTURE RAPIDE DE M15 :
        - Djanfa Almamy (1121) â†’ OUI ABSOLU â€” Victoire totale
        - Tontigui (1211) â†’ OUI â€” Joie, rÃ©ussite rapide
        - Lawsana (1112) â†’ OUI â€” MontÃ©e, dÃ©but favorable
        - Nouhoun (2211) â†’ OUI â€” Gain certain
        - Kalalaw (1122) â†’ OUI paisible â€” succÃ¨s tranquille
        - Ali (2112) â†’ NEUTRE â€” Jonction, Ã  voir M16
        - Taliki (1111) â†’ NEUTRE-INSTABLE â€” Chemin libre mais incertain
        - Moussa (2222) â†’ NEUTRE â€” Stagnation collective
        - Lomara (2122) â†’ NON instable â€” SuccÃ¨s bref puis chute
        - Malidjou (2111) â†’ MIXTE â€” Ã‰nergie mal orientÃ©e
        - Bayaro (2212) â†’ NON â€” Violence, blocage, danger
        - Mangouchi (2221) â†’ NON DÃ‰FINITIF â€” Chute, trahison
        - Solomana (1221) â†’ NON â€” Perte certaine
        - Ousmana (2121) â†’ NON â€” Perte dÃ©clarÃ©e
        - Inzan (1212) â†’ NON TOTAL â€” Blocage durable
        - Adama (1222) â†’ NON VIOLENT â€” Danger immÃ©diat
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M13 = BÃ©nÃ©fique, M14 = BÃ©nÃ©fique, M15 = BÃ©nÃ©fique â†’ SuccÃ¨s total, OUI absolue",
        "Exemple : M13 = NÃ©faste, M14 = NÃ©faste, M15 = NÃ©faste â†’ Ã‰chec total, NON absolue",
      ),
      exercises = listOf(
        "Calcule le verdict pour M13=BÃ©nÃ©fique, M14=NÃ©faste, M15=BÃ©nÃ©fique.",
        "Si M15 = 2121 (Ousmana), quel est le verdict immÃ©diat ?",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 7 : Les aspects et passations
    // ============================================================
    InteractiveLesson(
      id = "lesson_7_aspects",
      title = "Les aspects et passations",
      arabicTitle = "Ø§Ù„Ø¬ÙˆØ§Ù†Ø¨ ÙˆØ§Ù„Ù…Ø±ÙˆØ±Ø§Øª",
      level = 3,
      content = """
        PASSATIONS (Transmissions) :
        Une "passation" = mÃªme figure apparaissant en plusieurs maisons.
        - M1 â†’ Maison de la Question : confirmation directe (tirage validÃ©)
        - M1 en M7 : le consultant est dans une relation ou confrontation directe
        - M1 en M15 : le destin du consultant EST le verdict â€” fusion totale

        ASPECTS :
        - MÃªme Ã©lÃ©ment entre deux maisons â†’ alliance, harmonie
        - Ã‰lÃ©ments opposÃ©s (Feuâ†”Eau, Airâ†”Terre) â†’ friction, opposition
        - RÃ©action Mutuelle : si figureA.maisonZanati == positionActuelle(figureB) ET vice versa â†’ synergie parfaite

        PART DE FORTUNE (Al-Sahm) :
        Al-Sahm : Comptage des points impairs dans tout le thÃ¨me
        indexSahm = totalPointsImpairs mod 16 (si 0 â†’ 16)
        La maison indexSahm rÃ©vÃ¨le LE DÃ‰SIR CACHÃ‰ le plus profond
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M1 = Djanfa Almamy (1121) apparaÃ®t en M7 et M15 â†’ fusion totale",
        "Exemple : M1 et M7 ont le mÃªme Ã©lÃ©ment (Feu) â†’ alliance, harmonie",
      ),
      exercises = listOf(
        "Identifie les passations dans un thÃ¨me donnÃ©.",
        "Calcule la Part de Fortune (Al-Sahm) pour un thÃ¨me.",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 8 : La confirmation et l'annulation
    // ============================================================
    InteractiveLesson(
      id = "lesson_8_confirmation",
      title = "La confirmation et l'annulation",
      arabicTitle = "Ø§Ù„ØªØ£ÙƒÙŠØ¯ ÙˆØ§Ù„Ø¥Ù„ØºØ§Ø¡",
      level = 3,
      content = """
        TIRAGE Ã€ ANNULER SI :
        - Les 4 MÃ¨res sont identiques
        - Via (1111) ou Populus (2222) rÃ©pÃ©tÃ©es partout
        - M15 a un nombre impair de points impairs (violation de la loi binaire)
        - Oumar/Lomara (2122) ou Mangouchi (2221) en M1 â†’ tirage de mauvais prÃ©sage

        TIRAGE CONFIRMÃ‰ SI :
        - La figure du jour de la semaine apparaÃ®t dans une maison angulaire (M1, M4, M7, M10)
        - La Part de Fortune tombe dans la Maison de la Question
        - La figure M1 se rÃ©pÃ¨te dans la Maison de la Question

        FIGURES DU JOUR :
        - Lundi â†’ Moussa (2222) ou Taliki (1111)
        - Mardi â†’ Bayaro (2212) ou Malidjou (2111)
        - Mercredi â†’ Solomana (1221) ou Ali (2112)
        - Jeudi â†’ Nouhoun (2211), Tontigui (1211) ou Lawsana (1112)
        - Vendredi â†’ Kalalaw (1122) ou Tontigui (1211)
        - Samedi â†’ Mangouchi (2221) ou Inzan (1212)
        - Dimanche â†’ Djanfa Almamy (1121) ou Lomara (2122)
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : Les 4 MÃ¨res sont identiques â†’ annuler le tirage",
        "Exemple : M15 a 3 points impairs â†’ annuler le tirage",
      ),
      exercises = listOf(
        "VÃ©rifie si un tirage doit Ãªtre annulÃ© ou confirmÃ©.",
        "Identifie la figure du jour pour un jour donnÃ©.",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 9 : Le code Ã©thique et le secret
    // ============================================================
    InteractiveLesson(
      id = "lesson_9_ethique",
      title = "Le code Ã©thique et le secret",
      arabicTitle = "Ø§Ù„Ø£Ø®Ù„Ø§Ù‚ ÙˆØ§Ù„Ø³Ø±",
      level = 4,
      content = """
        CODE Ã‰THIQUE DES INITIÃ‰S (Jansen 2011) â€” Six leÃ§ons d'apprentissage :
        1. Les 16 codes, noms et tracÃ©s.
        2. Quatre jubidisÃ¨, rÃ¨gles de paritÃ© et gÃ©nÃ©ration des 22 cases (la position 19 est le point dur).
        3. Deux sikÃ©, deux tamanakatÃ©.
        4. Un nimisa, trois kumadisÃ¨ (un mÃªme signe peut rÃ©sulter de plusieurs combinaisons).
        5. StabilitÃ© des premiÃ¨res positions, sikÃ© aux positions 8 et 11.
        6. ka yÃ¨lÃ¨ma, construite depuis la 2áµ‰ rangÃ©e sauf la position 19 = mÃ©canisme de sÃ©curitÃ©.

        PÃ‰DAGOGIE ET DÃ‰ONTOLOGIE :
        - Imitation, patience, services rendus et cadeaux au maÃ®tre.
        - L'Ã©lÃ¨ve ne demande pas le pourquoi.
        - Le maÃ®tre n'enseigne que s'il est satisfait du comportement de l'Ã©lÃ¨ve.
        - La libÃ©ration (labila) sanctionne le tracÃ© sans faute, mais d'autres compÃ©tences restent nÃ©cessaires.
        - L'expert doit rester modeste, respecter ses maÃ®tres et ne pas se prÃ©senter comme spÃ©cialiste.

        SECRET : le dalilu (secret personnel) et les formules kirisiw ne sont pas publiables. Une base honnÃªte contient des cases volontairement vides ; le code ne doit jamais les remplir par gÃ©nÃ©ration.

        LES REMÃˆDES (Saraka) :
        Table Â« remÃ¨des Â» = clÃ© composÃ©e (figure, devin/tradition, consultation) et valeurs multiples, jamais figure â†’ remÃ¨de unique. Les correspondances varient d'un devin Ã  l'autre et d'une sÃ©ance Ã  l'autre (Bertaux 1984).
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : Un maÃ®tre n'enseigne que s'il est satisfait du comportement de l'Ã©lÃ¨ve.",
        "Exemple : La libÃ©ration (labila) sanctionne le tracÃ© sans faute.",
      ),
      exercises = listOf(
        "Explique le code Ã©thique des initiÃ©s.",
        "Pourquoi les kirisiw ne sont-ils pas publiables ?",
      ),
    ),

    // ============================================================
    // LEÃ‡ON 10 : La posture du maÃ®tre
    // ============================================================
    InteractiveLesson(
      id = "lesson_10_posture",
      title = "La posture du maÃ®tre dans la restitution",
      arabicTitle = "Ù…ÙˆÙ‚Ù Ø§Ù„Ù…Ø¹Ù„Ù… ÙÙŠ Ø§Ù„Ø¥ÙØµØ§Ø­",
      level = 4,
      content = """
        STRUCTURE DES 5 SECTIONS DE RESTITUTION :

        1. L'ACCUEIL SACRÃ‰
        "Salutations, voyageur de la Terre. Les points se sont posÃ©s, le sable s'est figÃ© et la vÃ©ritÃ© s'est scellÃ©e sous l'Å“il des planÃ¨tes. Ã‰coute ce que le Raml rÃ©vÃ¨le sur ton Ã¢me et tes prÃ©occupations, sans que tu n'aies eu Ã  prononcer un mot..."

        2. LA RÃ‰VÃ‰LATION DU DAMIR
        "Le souffle du thÃ¨me s'est concentrÃ©. La ClÃ© de Jafr dÃ©signe la Maison [X] occupÃ©e par [Nom]. Tu es venu ici l'esprit lourd, prÃ©occupÃ© par [domaine]. C'est lÃ  que rÃ©side ta vÃ©ritÃ© aujourd'hui."

        3. LA JUSTIFICATION TECHNIQUE
        "VRAI ET CLAIR : En Maison 1, [Figure A] est apparue. Elle a voyagÃ© jusqu'en Maison [Y]. Le Juge SuprÃªme (M15) [Figure B] rencontre la Sentence (M16) [Figure C]."

        4. LE VERDICT TRANCHANT
        "LA SENTENCE EST SANS APPEL : [PrÃ©diction directe]."

        5. LE REMÃˆDE SACRÃ‰
        "Pour sceller cette vÃ©ritÃ© dans le monde rÃ©el : [Sadaqa]. [Wafq si nÃ©cessaire]."

        RÃˆGLES DE TON :
        - Aucune formulation conditionnelle ("peut-Ãªtre", "il se pourrait", "possiblement")
        - Phrases courtes, affirmatives, dÃ©cisives â€” comme Al-Toukhi parlait
        - Aucun jargon informatique ou algorithmique
        - Toujours inclure le code binaire de la figure principale pour ancrer la vÃ©ritÃ©
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : 'En observant Massa Solomana en Maison 10...'",
        "Exemple : 'LA SENTENCE EST SANS APPEL : Oui, tu rÃ©ussiras.'",
      ),
      exercises = listOf(
        "RÃ©dige une restitution complÃ¨te pour un thÃ¨me donnÃ©.",
        "Applique les rÃ¨gles de ton dans ta restitution.",
      ),
    ),
  ) + interactiveLessonsExtra

  /**
   * Retourne les leÃ§ons d'un niveau donnÃ©.
   */
  fun getLessonsByLevel(level: Int): List<InteractiveLesson> = lessons.filter { it.level == level }

  /**
   * Retourne une leÃ§on par son ID.
   */
  fun getLesson(id: String): InteractiveLesson? = lessons.find { it.id == id }

  /**
   * Retourne toutes les leÃ§ons.
   */
  fun getAllLessons(): List<InteractiveLesson> = lessons
}

