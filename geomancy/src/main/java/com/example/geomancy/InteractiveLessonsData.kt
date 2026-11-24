package com.example.geomancy

/**
 * Données des leçons interactives pour l'application Al-Raml Al-Toukhi.
 * Terminologie unifiée : "NomOuestAfricain (NomArabe)".
 * Contenu pédagogique simplifié pour les débutants (enfants de maternelle).
 */
object InteractiveLessonsData {

  val lessons: List<InteractiveLesson> = listOf(

    // ============================================================
    // LEÇON 1 : Les fondements de la géomancie
    // ============================================================
    InteractiveLesson(
      id = "lesson_1_fondements",
      title = "Les fondements de la géomancie",
      arabicTitle = "أساس علم الرمل",
      level = 1,
      content = """
        La géomancie (Ilm al-Raml) est l'art de diviner par le sable. 
        Le mot arabe "raml" signifie "sable". C'est la science qui consiste à tracer des points dans le sable, 
        à les réduire par paires, et à interpréter les figures qui en résultent.

        PRINCIPE BINAIRE FONDAMENTAL :
        - 1 = point simple (impair, Khalwah — "l'ouvert")
        - 2 = point doublé (pair, Zawjiyyah — "le jumelé")

        Chaque figure est composée de 4 lignes lues de haut en bas :
        1. Feu (نار, Nār) — la Tête
        2. Air (هواء, Hawāʾ) — le Cou
        3. Eau (ماء, Māʾ) — le Ventre
        4. Terre (تراب, Turāb) — le Pied
        Mnémotechnique : NHMT (نهمت).

        LA LOI DE COPULATION (addition) :
        - impair + impair = pair → 1 + 1 = 2
        - pair + pair = pair → 2 + 2 = 2
        - impair + pair = impair → 1 + 2 = 1
        - pair + impair = impair → 2 + 1 = 1

        Cette règle est universelle et ne souffre aucune exception.
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : la figure Djanfa Almamy (الجودلة) a le code 1121 = 1 (impair) + 1 (impair) + 2 (pair) + 1 (impair)",
        "Exemple : la figure Moussa (الجماعة) a le code 2222 = 2 (pair) + 2 (pair) + 2 (pair) + 2 (pair)",
      ),
      exercises = listOf(
        "Trace à la main les 4 lignes de la figure 1121 (Djanfa Almamy). Vérifie que la ligne 1 est un point simple, la ligne 2 un point simple, la ligne 3 deux points, la ligne 4 un point simple.",
        "Trace à la main les 4 lignes de la figure 2222 (Moussa). Vérifie que toutes les lignes sont des points doublés.",
      ),
      visualAids = listOf(
        "1111 = • • • • (Taliki)",
        "1112 = • • • •• (Lawsana)",
        "1121 = • • •• • (Djanfa Almamy)",
        "1122 = • • •• •• (Kalalaw)",
        "1211 = • •• • • (Tontigui)",
        "1212 = • •• • •• (Inzan)",
        "1221 = • •• •• • (Solomana)",
        "1222 = • •• •• •• (Adama)",
        "2111 = •• • • • (Malidjou)",
        "2112 = •• • • •• (Ali)",
        "2121 = •• • •• • (Ousmana)",
        "2122 = •• • •• •• (Lomara)",
        "2211 = •• •• • • (Nouhoun)",
        "2212 = •• •• • •• (Bayaro)",
        "2221 = •• •• •• • (Mangouchi)",
        "2222 = •• •• •• •• (Moussa)",
      ),
    ),

    // ============================================================
    // LEÇON 2 : Le tracé dans le sable
    // ============================================================
    InteractiveLesson(
      id = "lesson_2_trace",
      title = "Le tracé dans le sable",
      arabicTitle = "رسم النقاط في الرمل",
      level = 1,
      content = """
        Le protocole de tirage selon Cheikh Al-Toukhi (Bulūgh al-Amal, Bāb 1) :

        1. PRÉPARATION : S'asseoir à califourchon sur un sol propre et plat. Prendre du sable fin tamisé d'un tas derrière soi.
        2. TRACÉ : Tracer avec la main droite un grand nimisa (~50 cm) dans lequel on place un siké à droite et un kumadibiné à gauche.
        3. RÉCITATION : Réciter les formules appropriées (istighfār, ṣalāt al-fātiḥ, tahlīl).
        4. TRACÉ DES POINTS : Tracer les points sans les compter — l'intuition guide la main. Compter ensuite deux par deux ; le reste donne 1 point si impair, 2 si pair.
        5. DIRECTION : Tracer d'est en ouest.
        6. PURETÉ : Le consultant doit être en état de pureté rituelle, silencieux, concentré.
        7. TROIS JETS PAR JOUR maximum.

        LES 4 MÈRES (Ummahāt / Matres) :
        Les 4 premières figures obtenues sont les Mères (M1 à M4). Elles sont aléatoires et placées de droite à gauche (1ʳᵉ mère à l'extrême droite).

        LES FILLES (Banāt) :
        La 1ʳᵉ Fille reprend la 1ʳᵉ ligne des 4 Mères, la 2ᵉ Fille la 2ᵉ ligne, etc.
        - M5 = Ligne FEU de [M1, M2, M3, M4]
        - M6 = Ligne AIR de [M1, M2, M3, M4]
        - M7 = Ligne EAU de [M1, M2, M3, M4]
        - M8 = Ligne TERRE de [M1, M2, M3, M4]

        LES NIÈCES (Zawāʾid / Radā) :
        Additions deux à deux (pair → 2, impair → 1) :
        - M9 = M1 ⊕ M2
        - M10 = M3 ⊕ M4
        - M11 = M5 ⊕ M6
        - M12 = M7 ⊕ M8

        LES TÉMOINS (Shāhidān) :
        - M13 (Témoin Droit / Passé) = M9 ⊕ M10
        - M14 (Témoin Gauche / Avenir) = M11 ⊕ M12

        LE JUGE (Qāḍī) :
        - M15 = M13 ⊕ M14
        Validation : M15 doit avoir un nombre PAIR de points impairs (0, 2 ou 4). Si impair → thème invalide.

        LA SENTENCE (ʿĀqiba) :
        - M16 = M15 ⊕ M1 (LOI ABSOLUE)
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : Mères = 1121, 2122, 1211, 2111 → Filles = 1211, 1121, 2211, 1211",
        "Exemple : M9 = 1121 ⊕ 2122 = 1211 (Tontigui)",
      ),
      exercises = listOf(
        "Génère un thème avec les Mères : M1=1121, M2=2122, M3=1211, M4=2111. Calcule les Filles, Nièces, Témoins, Juge et Sentence.",
        "Vérifie que M15 a un nombre pair de points impairs.",
      ),
    ),

    // ============================================================
    // LEÇON 3 : La grille de repos (Taskin)
    // ============================================================
    InteractiveLesson(
      id = "lesson_3_taskin",
      title = "La grille de repos (Taskin al-Falak al-Dair)",
      arabicTitle = "تسكين الفلك الدائر",
      level = 1,
      content = """
        Chaque figure a une maison de repos fixe dans Al-Falak al-Dair (le Ciel Tournant). C'est la grille immuable d'Al-Toukhi :

        M1=1121  M2=1222  M3=2111  M4=2212
        M5=1111  M6=1212  M7=2122  M8=2221
        M9=1122  M10=1221 M11=2112 M12=2211
        M13=1112 M14=1211 M15=2121 M16=2222

        DISPOSITION DE L'ÉCU (bouclier du takht) :

        M8  M7  M6  M5  M4  M3  M2  M1
            M12  M11  M10  M9
                M14          M13
                    M15          M16

        Règle : une clé de placement n'est pas un élément. La table figure → élément est saisie code par code.

        DOMAINES DES 16 MAISONS :
        - M1 Ascendant/Consultant : Âme, santé, vitalité
        - M2 Foyer des Biens : Argent, biens, subsistance (Rizq)
        - M3 Entourage Proche : Frères/sœurs, voisins, déplacements courts
        - M4 Fonds du Ciel : Père, foyer, patrimoine, racines
        - M5 Maison des Enfants : Enfants, grossesse, créations
        - M6 Maison du Service : Maladie, obstacles quotidiens
        - M7 Descendant/Conjoint : Conjoint, partenaire, adversaire
        - M8 Maison de la Transformation : Peur, perte, mort, héritage
        - M9 Maison des Grands Voyages : Longs voyages, religion, science
        - M10 Milieu du Ciel/Autorité : Travail, carrière, honneur
        - M11 Maison des Espoirs : Amis, appuis, espoirs, gains futurs
        - M12 Maison des Ennemis Cachés : Ennemis cachés, jalousie, blocages
        - M13 Témoin Droit (Passé) : Origine de l'affaire
        - M14 Témoin Gauche (Futur) : Évolution prochaine
        - M15 Juge Suprême : Verdict global définitif
        - M16 Sentence Finale : Conseil divin, conséquence ultime
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M1=1121 (Djanfa Almamy) correspond à la position 1 de la grille Taskin",
        "Exemple : M16=2222 (Moussa) est la Sentence Finale",
      ),
      exercises = listOf(
        "Mémorise la grille Taskin al-Falak al-Dair.",
        "Dessine la disposition de l'écu (bouclier du takht) sur une feuille.",
      ),
    ),

    // ============================================================
    // LEÇON 4 : La science du Damir
    // ============================================================
    InteractiveLesson(
      id = "lesson_4_damir",
      title = "La science du Damir (l'intention cachée)",
      arabicTitle = "علم الضمير",
      level = 2,
      content = """
        Le Damir (الضمير) est la pensée secrète du consultant. Le maître la lit avant que le consultant ait parlé. C'est le cœur de toute consultation.

        ALGORITHME DU DAMIR (4 étapes) :

        Étape 1 — Le Miroir Zanatî de M1 :
        - figureM1 = figure tombée en Maison 1
        - maisonCible = figureM1.maisonZanati (position fixe dans Al-Falak al-Dair)
        - domaineSecret = significationMaison(maisonCible)

        Étape 2 — Les Répétitions (Confirmation) :
        - Pour chaque maison i de 2 à 16 : si maisons[i] == figureM1 → maisonsRepetition.add(i)
        - Plus il y a de répétitions, plus le Damir est confirmé.

        Étape 3 — La Part de Fortune / Al-Sahm :
        - totalImpairs = somme de tous les points '1' dans les 16 maisons
        - indexSahm = if (totalImpairs % 16 == 0) 16 else (totalImpairs % 16)
        - figureSahm = maisons[indexSahm - 1]
        - domaineSecretSahm = significationMaison(figureSahm.maisonZanati)

        Étape 4 — L'État Émotionnel (Direction de M1) :
        - ENTRANTE → "Prêt à recevoir, cœur ouvert, en attente"
        - SORTANTE → "Fuite, peur, situation critique, pression"
        - FIXE → "Stagnation, stabilité, refus d'agir"
        - MOBILE → "Instabilité, hésitation, chemin incertain"

        Variante : Damir par Modulo 16 sur les points de la Tête :
        - Somme des points simples (impairs) sur la ligne supérieure (Feu/Tête) des Mères M1–M4
        - Modulo 16 ; reste 0 → M16, sinon la maison indiquée
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M1 = Djanfa Almamy (1121), maisonZanati = M1 → domaine = Âme/santé",
        "Exemple : Si M1 apparaît en M7, M10 et M15, le Damir est fortement confirmé",
      ),
      exercises = listOf(
        "Calcule le Damir pour un thème donné.",
        "Identifie la Part de Fortune (Al-Sahm) et la maison cible.",
      ),
    ),

    // ============================================================
    // LEÇON 5 : La métrologie
    // ============================================================
    InteractiveLesson(
      id = "lesson_5_metrologie",
      title = "La métrologie et les dimensions",
      arabicTitle = "المقاييس والأبعاد",
      level = 2,
      content = """
        Al-Toukhi a codifié des calculs métrologiques permettant de quantifier l'invisible :

        1. PROFONDEUR (puits, trésor enfoui) :
           Valeur_Abjad(figureM4) = profondeur en COUDÉES ROYALES
           1 coudée royale ≈ 54 cm
           profondeur_cm = abjad(M4) × 54

        2. HAUTEUR (objet, mur, arbre) :
           Valeur_Abjad(figureM9) = hauteur en EMPANS (Shibr)
           1 empan ≈ 22 cm
           hauteur_cm = abjad(M9) × 22

        3. DISTANCE :
           Valeur_Abjad(figureM3) = distance en FARSAKHS
           1 farsakh = 6 km
           distance_km = abjad(M3) × 6

        4. DURÉE D'UN ÉVÉNEMENT :
           Direction de M15 :
           - ENTRANTE → délai en JOURS = abjad(M15)
           - SORTANTE → délai en SEMAINES = abjad(M15)
           - FIXE → délai en MOIS = abjad(M15)
           - MOBILE → délai en ANNÉES = abjad(M15) ÷ 10

        5. QUANTITÉ / NOMBRE :
           abjad(figureM2) = quantité ou somme en DINARS/UNITÉS
           Pour affiner : (abjad(M2) + abjad(M11)) ÷ 2

        6. COULEUR :
           - FEU → Rouge, Orange, Or
           - AIR → Jaune, Vert clair, Blanc lumineux
           - EAU → Bleu, Blanc lunaire, Argent, Vert d'eau
           - TERRE → Brun, Noir, Gris, Ocre

        7. ODEUR :
           - FEU → Âcre, brûlé, épicé, encens fort
           - AIR → Floral, léger, parfum discret
           - EAU → Humide, marin, fade, savon
           - TERRE → Terreux, moisi, lourd, racines

        8. MÉTÉO :
           M9 (maison des grands espaces) + M4 (sol/terre) :
           - EAU dominant → Pluie certaine (délai = abjad(M9) × 2 jours)
           - AIR dominant → Vent, temps changeant
           - FEU dominant → Sécheresse, chaleur
           - TERRE dominant → Famine, stabilité froide

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
        - Adama (1222) : [D] (non documenté)
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M4 = 2221 (Mangouchi, abjad=44) → profondeur = 44 × 54 = 2 376 cm ≈ 23,76 m",
        "Exemple : M9 = 1122 (Kalalaw, abjad=50) → hauteur = 50 × 22 = 1 100 cm = 11 m",
      ),
      exercises = listOf(
        "Si M4 = 1121 (Djanfa Almamy, abjad=108), calcule la profondeur du puits en cm.",
        "Si M3 = 1112 (Lawsana, abjad=270), calcule la distance en km.",
      ),
    ),

    // ============================================================
    // LEÇON 6 : La lecture des témoins et du juge
    // ============================================================
    InteractiveLesson(
      id = "lesson_6_temoins_juge",
      title = "La lecture des témoins et du juge",
      arabicTitle = "قراءة الشاهدين والحاكم",
      level = 2,
      content = """
        COMBINAISONS M13/M14/M15 → VERDICT :

        | M13 | M14 | M15 | Verdict |
        |-----|-----|-----|---------|
        | Bénéfique | Bénéfique | Bénéfique | Succès total, réponse OUI absolue |
        | Néfaste | Néfaste | Néfaste | Échec total, réponse NON absolue |
        | Bénéfique | Néfaste | Bénéfique | Succès après obstacles — patience |
        | Néfaste | Bénéfique | Bénéfique | Mauvais début, bonne fin |
        | Bénéfique | Bénéfique | Néfaste | Bon début, mauvaise conclusion |
        | Néfaste | Bénéfique | Néfaste | Lueur d'espoir, verdict défavorable |
        | Bénéfique | Néfaste | Néfaste | Effort inutile — danger réel |
        | Néfaste | Néfaste | Bénéfique | Épreuves puis récompense finale |

        RÈGLE DE M15 (Juge) :
        - ENTRANTE → Réponse favorable
        - SORTANTE → Réponse défavorable, perte, refus
        - FIXE → Stagnation, ni gain ni perte
        - MOBILE → Incertitude totale

        INDICATEURS CLÉS — LECTURE RAPIDE DE M15 :
        - Djanfa Almamy (1121) → OUI ABSOLU — Victoire totale
        - Tontigui (1211) → OUI — Joie, réussite rapide
        - Lawsana (1112) → OUI — Montée, début favorable
        - Nouhoun (2211) → OUI — Gain certain
        - Kalalaw (1122) → OUI paisible — succès tranquille
        - Ali (2112) → NEUTRE — Jonction, à voir M16
        - Taliki (1111) → NEUTRE-INSTABLE — Chemin libre mais incertain
        - Moussa (2222) → NEUTRE — Stagnation collective
        - Lomara (2122) → NON instable — Succès bref puis chute
        - Malidjou (2111) → MIXTE — Énergie mal orientée
        - Bayaro (2212) → NON — Violence, blocage, danger
        - Mangouchi (2221) → NON DÉFINITIF — Chute, trahison
        - Solomana (1221) → NON — Perte certaine
        - Ousmana (2121) → NON — Perte déclarée
        - Inzan (1212) → NON TOTAL — Blocage durable
        - Adama (1222) → NON VIOLENT — Danger immédiat
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M13 = Bénéfique, M14 = Bénéfique, M15 = Bénéfique → Succès total, OUI absolue",
        "Exemple : M13 = Néfaste, M14 = Néfaste, M15 = Néfaste → Échec total, NON absolue",
      ),
      exercises = listOf(
        "Calcule le verdict pour M13=Bénéfique, M14=Néfaste, M15=Bénéfique.",
        "Si M15 = 2121 (Ousmana), quel est le verdict immédiat ?",
      ),
    ),

    // ============================================================
    // LEÇON 7 : Les aspects et passations
    // ============================================================
    InteractiveLesson(
      id = "lesson_7_aspects",
      title = "Les aspects et passations",
      arabicTitle = "الجوانب والمرورات",
      level = 3,
      content = """
        PASSATIONS (Transmissions) :
        Une "passation" = même figure apparaissant en plusieurs maisons.
        - M1 → Maison de la Question : confirmation directe (tirage validé)
        - M1 en M7 : le consultant est dans une relation ou confrontation directe
        - M1 en M15 : le destin du consultant EST le verdict — fusion totale

        ASPECTS :
        - Même élément entre deux maisons → alliance, harmonie
        - Éléments opposés (Feu↔Eau, Air↔Terre) → friction, opposition
        - Réaction Mutuelle : si figureA.maisonZanati == positionActuelle(figureB) ET vice versa → synergie parfaite

        PART DE FORTUNE (Al-Sahm) :
        Al-Sahm : Comptage des points impairs dans tout le thème
        indexSahm = totalPointsImpairs mod 16 (si 0 → 16)
        La maison indexSahm révèle LE DÉSIR CACHÉ le plus profond
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : M1 = Djanfa Almamy (1121) apparaît en M7 et M15 → fusion totale",
        "Exemple : M1 et M7 ont le même élément (Feu) → alliance, harmonie",
      ),
      exercises = listOf(
        "Identifie les passations dans un thème donné.",
        "Calcule la Part de Fortune (Al-Sahm) pour un thème.",
      ),
    ),

    // ============================================================
    // LEÇON 8 : La confirmation et l'annulation
    // ============================================================
    InteractiveLesson(
      id = "lesson_8_confirmation",
      title = "La confirmation et l'annulation",
      arabicTitle = "التأكيد والإلغاء",
      level = 3,
      content = """
        TIRAGE À ANNULER SI :
        - Les 4 Mères sont identiques
        - Via (1111) ou Populus (2222) répétées partout
        - M15 a un nombre impair de points impairs (violation de la loi binaire)
        - Oumar/Lomara (2122) ou Mangouchi (2221) en M1 → tirage de mauvais présage

        TIRAGE CONFIRMÉ SI :
        - La figure du jour de la semaine apparaît dans une maison angulaire (M1, M4, M7, M10)
        - La Part de Fortune tombe dans la Maison de la Question
        - La figure M1 se répète dans la Maison de la Question

        FIGURES DU JOUR :
        - Lundi → Moussa (2222) ou Taliki (1111)
        - Mardi → Bayaro (2212) ou Malidjou (2111)
        - Mercredi → Solomana (1221) ou Ali (2112)
        - Jeudi → Nouhoun (2211), Tontigui (1211) ou Lawsana (1112)
        - Vendredi → Kalalaw (1122) ou Tontigui (1211)
        - Samedi → Mangouchi (2221) ou Inzan (1212)
        - Dimanche → Djanfa Almamy (1121) ou Lomara (2122)
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : Les 4 Mères sont identiques → annuler le tirage",
        "Exemple : M15 a 3 points impairs → annuler le tirage",
      ),
      exercises = listOf(
        "Vérifie si un tirage doit être annulé ou confirmé.",
        "Identifie la figure du jour pour un jour donné.",
      ),
    ),

    // ============================================================
    // LEÇON 9 : Le code éthique et le secret
    // ============================================================
    InteractiveLesson(
      id = "lesson_9_ethique",
      title = "Le code éthique et le secret",
      arabicTitle = "الأخلاق والسر",
      level = 4,
      content = """
        CODE ÉTHIQUE DES INITIÉS (Jansen 2011) — Six leçons d'apprentissage :
        1. Les 16 codes, noms et tracés.
        2. Quatre jubidisè, règles de parité et génération des 22 cases (la position 19 est le point dur).
        3. Deux siké, deux tamanakaté.
        4. Un nimisa, trois kumadisè (un même signe peut résulter de plusieurs combinaisons).
        5. Stabilité des premières positions, siké aux positions 8 et 11.
        6. ka yèlèma, construite depuis la 2ᵉ rangée sauf la position 19 = mécanisme de sécurité.

        PÉDAGOGIE ET DÉONTOLOGIE :
        - Imitation, patience, services rendus et cadeaux au maître.
        - L'élève ne demande pas le pourquoi.
        - Le maître n'enseigne que s'il est satisfait du comportement de l'élève.
        - La libération (labila) sanctionne le tracé sans faute, mais d'autres compétences restent nécessaires.
        - L'expert doit rester modeste, respecter ses maîtres et ne pas se présenter comme spécialiste.

        SECRET : le dalilu (secret personnel) et les formules kirisiw ne sont pas publiables. Une base honnête contient des cases volontairement vides ; le code ne doit jamais les remplir par génération.

        LES REMÈDES (Saraka) :
        Table « remèdes » = clé composée (figure, devin/tradition, consultation) et valeurs multiples, jamais figure → remède unique. Les correspondances varient d'un devin à l'autre et d'une séance à l'autre (Bertaux 1984).
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : Un maître n'enseigne que s'il est satisfait du comportement de l'élève.",
        "Exemple : La libération (labila) sanctionne le tracé sans faute.",
      ),
      exercises = listOf(
        "Explique le code éthique des initiés.",
        "Pourquoi les kirisiw ne sont-ils pas publiables ?",
      ),
    ),

    // ============================================================
    // LEÇON 10 : La posture du maître
    // ============================================================
    InteractiveLesson(
      id = "lesson_10_posture",
      title = "La posture du maître dans la restitution",
      arabicTitle = "موقف المعلم في الإفصاح",
      level = 4,
      content = """
        STRUCTURE DES 5 SECTIONS DE RESTITUTION :

        1. L'ACCUEIL SACRÉ
        "Salutations, voyageur de la Terre. Les points se sont posés, le sable s'est figé et la vérité s'est scellée sous l'œil des planètes. Écoute ce que le Raml révèle sur ton âme et tes préoccupations, sans que tu n'aies eu à prononcer un mot..."

        2. LA RÉVÉLATION DU DAMIR
        "Le souffle du thème s'est concentré. La Clé de Jafr désigne la Maison [X] occupée par [Nom]. Tu es venu ici l'esprit lourd, préoccupé par [domaine]. C'est là que réside ta vérité aujourd'hui."

        3. LA JUSTIFICATION TECHNIQUE
        "VRAI ET CLAIR : En Maison 1, [Figure A] est apparue. Elle a voyagé jusqu'en Maison [Y]. Le Juge Suprême (M15) [Figure B] rencontre la Sentence (M16) [Figure C]."

        4. LE VERDICT TRANCHANT
        "LA SENTENCE EST SANS APPEL : [Prédiction directe]."

        5. LE REMÈDE SACRÉ
        "Pour sceller cette vérité dans le monde réel : [Sadaqa]. [Wafq si nécessaire]."

        RÈGLES DE TON :
        - Aucune formulation conditionnelle ("peut-être", "il se pourrait", "possiblement")
        - Phrases courtes, affirmatives, décisives — comme Al-Toukhi parlait
        - Aucun jargon informatique ou algorithmique
        - Toujours inclure le code binaire de la figure principale pour ancrer la vérité
      """.trimIndent(),
      figures = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16),
      examples = listOf(
        "Exemple : 'En observant Massa Solomana en Maison 10...'",
        "Exemple : 'LA SENTENCE EST SANS APPEL : Oui, tu réussiras.'",
      ),
      exercises = listOf(
        "Rédige une restitution complète pour un thème donné.",
        "Applique les règles de ton dans ta restitution.",
      ),
    ),
  )

  /**
   * Retourne les leçons d'un niveau donné.
   */
  fun getLessonsByLevel(level: Int): List<InteractiveLesson> = lessons.filter { it.level == level }

  /**
   * Retourne une leçon par son ID.
   */
  fun getLesson(id: String): InteractiveLesson? = lessons.find { it.id == id }

  /**
   * Retourne toutes les leçons.
   */
  fun getAllLessons(): List<InteractiveLesson> = lessons
}
