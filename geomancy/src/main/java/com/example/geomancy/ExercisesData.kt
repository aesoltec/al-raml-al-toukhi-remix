package com.example.geomancy

/**
 * Données des exercices pratiques pour l'application Al-Raml Al-Toukhi.
 * Chaque exercice comprend une question, une correction détaillée et le niveau de difficulté.
 */
data class GeomancyExercise(
  val id: String,
  val title: String,
  val arabicTitle: String,
  val level: Int,
  val question: String,
  val correction: String,
  val hint: String = "",
  val figureCodes: List<String> = emptyList(),
)

object ExercisesData {

  val exercises: List<GeomancyExercise> = listOf(

    // ============================================================
    // NIVEAU 1 — FONDAMENTAUX
    // ============================================================
    GeomancyExercise(
      id = "exo_1_1",
      title = "Tracer une figure",
      arabicTitle = "رسم شكل",
      level = 1,
      question = "Trace à la main les 4 lignes de la figure 1121 (Djanfa Almamy). Vérifie que la ligne 1 est un point simple (impair), la ligne 2 un point simple, la ligne 3 deux points (pair), la ligne 4 un point simple.",
      correction = "Code 1121 = 1 (impair) + 1 (impair) + 2 (pair) + 1 (impair) = Djanfa Almamy (الجودلة). Visualisation :\n•\n•\n••\n•",
      hint = "Un point (•) = impair (1), deux points (••) = pair (2).",
      figureCodes = listOf("1121"),
    ),
    GeomancyExercise(
      id = "exo_1_2",
      title = "Tracer Populus",
      arabicTitle = "رسم الجماعة",
      level = 1,
      question = "Trace à la main les 4 lignes de la figure 2222 (Moussa / Populus). Vérifie que toutes les lignes sont des points doublés (pairs).",
      correction = "Code 2222 = 2 (pair) + 2 (pair) + 2 (pair) + 2 (pair) = Moussa (الجماعة). Visualisation :\n••\n••\n••\n••\nPopulus (2222) est l'identité du groupe dans l'algèbre de la géomancie.",
      hint = "Populus est la seule figure entièrement fermée (tous les rangs pairs).",
      figureCodes = listOf("2222"),
    ),
    GeomancyExercise(
      id = "exo_1_3",
      title = "Tracer Via",
      arabicTitle = "رسم الطريق",
      level = 1,
      question = "Trace à la main les 4 lignes de la figure 1111 (Taliki / Via). Vérifie que toutes les lignes sont des points simples (impairs).",
      correction = "Code 1111 = 1 (impair) + 1 (impair) + 1 (impair) + 1 (impair) = Taliki (الطريق). Visualisation :\n•\n•\n•\n•\nVia (1111) est l'opérateur de miroir/complément : il inverse chaque ligne (1↔2).",
      hint = "Via est la figure du chemin, de la route. Elle est mobile et instable.",
      figureCodes = listOf("1111"),
    ),
    GeomancyExercise(
      id = "exo_1_4",
      title = "Identifier une figure par son code",
      arabicTitle = "تحديد شكل من رمزه",
      level = 1,
      question = "Quelle figure correspond au code 2122 ? Donne son nom ouest-africain, son nom arabe et son élément.",
      correction = "Code 2122 = 2 (pair) + 1 (impair) + 2 (pair) + 2 (pair) = Lomara (الحمرة). Élément : Feu. Planète : Mars (nocturne). Statut : Sortante. Signification : passion, feu, conflit, colère.",
      hint = "Le premier rang (2) est pair → EVEN, le deuxième (1) est impair → ODD, etc.",
      figureCodes = listOf("2122"),
    ),
    GeomancyExercise(
      id = "exo_1_5",
      title = "Identifier une figure par son nom",
      arabicTitle = "تحديد رمز من اسمه",
      level = 1,
      question = "Quel est le code binaire de la figure Nouhoun (النصرة الداخلة) ?",
      correction = "Nouhoun (النصرة الداخلة) = Acquisitio = 2211. Visualisation :\n••\n••\n•\n•\nÉlément : Air. Planète : Jupiter. Statut : Entrante. Signification : acquisition, gain, Rizq.",
      hint = "Nouhoun est une figure entrante et très bénéfique.",
      figureCodes = listOf("2211"),
    ),

    // ============================================================
    // NIVEAU 2 — GÉNÉRATION DU THÈME
    // ============================================================
    GeomancyExercise(
      id = "exo_2_1",
      title = "Générer un thème (Filles, Nièces, Témoins, Juge, Sentence)",
      arabicTitle = "توليد theme",
      level = 2,
      question = "Mères données : M1=1121 (Djanfa Almamy), M2=2122 (Lomara), M3=1211 (Tontigui), M4=2111 (Malidjou). Calcule les Filles (M5-M8), les Nièces (M9-M12), les Témoins (M13-M14), le Juge (M15) et la Sentence (M16).",
      correction = """
        M5 = Ligne FEU = 1,2,1,1 = 1211 (Tontigui)
        M6 = Ligne AIR = 1,1,2,1 = 1121 (Djanfa Almamy)
        M7 = Ligne EAU = 2,2,1,1 = 2211 (Nouhoun)
        M8 = Ligne TERRE = 1,2,1,1 = 1211 (Tontigui)
        M9 = M1⊕M2 = 1121⊕2122 = 1211 (Tontigui)
        M10 = M3⊕M4 = 1211⊕2111 = 2122 (Lomara)
        M11 = M5⊕M6 = 1211⊕1121 = 2212 (Bayaro)
        M12 = M7⊕M8 = 2211⊕1211 = 1122 (Kalalaw)
        M13 = M9⊕M10 = 1211⊕2122 = 2211 (Nouhoun)
        M14 = M11⊕M12 = 2212⊕1122 = 1212 (Inzan)
        M15 = M13⊕M14 = 2211⊕1212 = 1121 (Djanfa Almamy) — 3 points impairs → PAIR ✓
        M16 = M15⊕M1 = 1121⊕1121 = 2222 (Moussa)
      """.trimIndent(),
      hint = "Ligne FEU = 1er rang de chaque Mère. Ligne AIR = 2e rang. etc.",
      figureCodes = listOf("1121", "2122", "1211", "2111"),
    ),
    GeomancyExercise(
      id = "exo_2_2",
      title = "Vérifier la parité du Juge",
      arabicTitle = "التحقق من تكافؤ الحاكم",
      level = 2,
      question = "M15 = 2122 (Lomara). Le thème est-il valide ?",
      correction = "M15 = 2122 (Lomara) a 1 point impair (le 2e rang). 1 est impair → thème INVALIDE. Le Juge doit avoir un nombre PAIR de points impairs (0, 2 ou 4).",
      hint = "Compte les '1' dans le code. 1 → impair → invalide.",
      figureCodes = listOf("2122"),
    ),
    GeomancyExercise(
      id = "exo_2_3",
      title = "Générer les Filles",
      arabicTitle = "توليد البنات",
      level = 2,
      question = "Mères : M1=1111, M2=2222, M3=1212, M4=2121. Calcule les Filles M5, M6, M7, M8.",
      correction = """
        M5 = Ligne FEU = 1,2,1,2 = 1212 (Inzan)
        M6 = Ligne AIR = 1,2,2,1 = 1221 (Solomana)
        M7 = Ligne EAU = 1,2,1,1 = 1211 (Tontigui)
        M8 = Ligne TERRE = 1,2,2,1 = 1221 (Solomana)
      """.trimIndent(),
      hint = "M5 = 1er rang de M1, M2, M3, M4. M6 = 2e rang. etc.",
      figureCodes = listOf("1111", "2222", "1212", "2121"),
    ),

    // ============================================================
    // NIVEAU 3 — DAMIR
    // ============================================================
    GeomancyExercise(
      id = "exo_3_1",
      title = "Calculer le Damir (Part de Fortune)",
      arabicTitle = "حساب الضمير (سهم Fortune)",
      level = 3,
      question = "Thème : M1=1121, M2=2122, M3=1211, M4=2111, M5=1211, M6=1121, M7=2211, M8=1211, M9=1211, M10=2122, M11=2212, M12=1122, M13=2211, M14=1212, M15=1121, M16=2222. Calcule la Part de Fortune (Al-Sahm).",
      correction = "Total des points '1' dans les 16 maisons : compter chaque '1' dans chaque code. M1=1121 → 3 points '1'. M2=2122 → 1. M3=1211 → 3. M4=2111 → 3. M5=1211 → 3. M6=1121 → 3. M7=2211 → 2. M8=1211 → 3. M9=1211 → 3. M10=2122 → 1. M11=2212 → 2. M12=1122 → 2. M13=2211 → 2. M14=1212 → 2. M15=1121 → 3. M16=2222 → 0. Total = 3+1+3+3+3+3+2+3+3+1+2+2+2+2+3+0 = 36. indexSahm = 36 mod 16 = 4. Maison cible = M4 (Fonds du Ciel). Le désir caché concerne le foyer, le patrimoine ou le père.",
      hint = "Compte chaque '1' dans chaque code de maison.",
      figureCodes = listOf("1121", "2122", "1211", "2111"),
    ),
    GeomancyExercise(
      id = "exo_3_2",
      title = "Identifier le Damir par répétitions",
      arabicTitle = "تحديد الضمير بالتكرار",
      level = 3,
      question = "Thème : M1=2211 (Nouhoun). Les maisons contenant 2211 sont : M1, M5, M9, M13. Le Damir est-il confirmé ?",
      correction = "M1 = 2211 (Nouhoun). Les répétitions sont en M5, M9, M13 (3 répétitions). Plus il y a de répétitions, plus le Damir est confirmé. Avec 3 répétitions, le Damir est fortement confirmé. Le consultant est préoccupé par le domaine de la maison où Nouhoun (2211) repose dans la grille Taskin. Nouhoun est en M12 dans la grille Taskin → domaine = Ennemis cachés. Le consultant est préoccupé par ses ennemis cachés.",
      hint = "Compte les maisons contenant la même figure que M1.",
      figureCodes = listOf("2211"),
    ),

    // ============================================================
    // NIVEAU 4 — MÉTROLOGIE
    // ============================================================
    GeomancyExercise(
      id = "exo_4_1",
      title = "Calculer la profondeur",
      arabicTitle = "حساب العمق",
      level = 4,
      question = "M4 = 2221 (Mangouchi, abjad=44). Calcule la profondeur du puits en cm.",
      correction = "abjad(M4) = 44. Profondeur = 44 × 54 = 2 376 cm ≈ 23,76 m.",
      hint = "1 coudée royale ≈ 54 cm.",
      figureCodes = listOf("2221"),
    ),
    GeomancyExercise(
      id = "exo_4_2",
      title = "Calculer la hauteur",
      arabicTitle = "حساب الارتفاع",
      level = 4,
      question = "M9 = 1122 (Kalalaw, abjad=50). Calcule la hauteur de l'objet en cm.",
      correction = "abjad(M9) = 50. Hauteur = 50 × 22 = 1 100 cm = 11 m.",
      hint = "1 empan ≈ 22 cm.",
      figureCodes = listOf("1122"),
    ),
    GeomancyExercise(
      id = "exo_4_3",
      title = "Calculer la distance",
      arabicTitle = "حساب المسافة",
      level = 4,
      question = "M3 = 1112 (Lawsana, abjad=270). Calcule la distance en km.",
      correction = "abjad(M3) = 270. Distance = 270 × 6 = 1 620 km.",
      hint = "1 farsakh = 6 km.",
      figureCodes = listOf("1112"),
    ),
    GeomancyExercise(
      id = "exo_4_4",
      title = "Calculer la durée",
      arabicTitle = "حساب المدة",
      level = 4,
      question = "M15 = 2121 (Ousmana, abjad=756), direction = ENTRANTE. Calcule la durée de l'événement.",
      correction = "M15 = Ousmana (2121), direction = ENTRANTE → délai en JOURS = abjad(M15) = 756 jours ≈ 2 ans et 25 jours.",
      hint = "ENTRANTE → jours, SORTANTE → semaines, FIXE → mois, MOBILE → années.",
      figureCodes = listOf("2121"),
    ),

    // ============================================================
    // NIVEAU 5 — LECTURE DU JUGE
    // ============================================================
    GeomancyExercise(
      id = "exo_5_1",
      title = "Lecture du Juge (M15)",
      arabicTitle = "قراءة الحاكم",
      level = 5,
      question = "Si M15 = 1121 (Djanfa Almamy), quel est le verdict immédiat ?",
      correction = "Djanfa Almamy (1121) → OUI ABSOLU — Victoire totale. Le consultant obtiendra ce qu'il cherche.",
      hint = "Consulte le tableau des indicateurs clés de M15.",
      figureCodes = listOf("1121"),
    ),
    GeomancyExercise(
      id = "exo_5_2",
      title = "Combinaison M13/M14/M15",
      arabicTitle = "تركيبة M13/M14/M15",
      level = 5,
      question = "M13 = Bénéfique, M14 = Néfaste, M15 = Bénéfique. Quel est le verdict ?",
      correction = "M13 Bénéfique + M14 Néfaste + M15 Bénéfique → Succès après obstacles — patience. Le consultant réussira mais après avoir surmonté des obstacles.",
      hint = "Consulte le tableau des combinaisons M13/M14/M15.",
    ),

    // ============================================================
    // NIVEAU 6 — ASPECTS ET PASSATIONS
    // ============================================================
    GeomancyExercise(
      id = "exo_6_1",
      title = "Identifier les passations",
      arabicTitle = "تحديد المرورات",
      level = 6,
      question = "Thème : M1=1121, M2=2122, M3=1211, M4=2111, M5=1211, M6=1121, M7=2211, M8=1211, M9=1211, M10=2122, M11=2212, M12=1122, M13=2211, M14=1212, M15=1121, M16=2222. Quelles sont les passations de M1 ?",
      correction = "M1 = 1121 (Djanfa Almamy). Les maisons contenant 1121 sont : M1, M6, M15. Passations : M1 → M6, M1 → M15. M1 en M15 → fusion totale (le destin du consultant EST le verdict).",
      hint = "Cherche toutes les maisons contenant la même figure que M1.",
      figureCodes = listOf("1121"),
    ),
    GeomancyExercise(
      id = "exo_6_2",
      title = "Calculer la Part de Fortune (Al-Sahm)",
      arabicTitle = "حساب سهم Fortune",
      level = 6,
      question = "Thème : toutes les maisons sont 1111 (Taliki). Calcule la Part de Fortune (Al-Sahm).",
      correction = "Chaque maison 1111 a 4 points '1'. 16 maisons × 4 = 64 points '1'. indexSahm = 64 mod 16 = 0 → 16. Maison cible = M16 (Sentence Finale). Le désir caché le plus profond concerne la sentence finale, le conseil divin.",
      hint = "Compte tous les points '1' dans les 16 maisons.",
      figureCodes = listOf("1111"),
    ),

    // ============================================================
    // NIVEAU 7 — CONFIRMATION ET ANNULATION
    // ============================================================
    GeomancyExercise(
      id = "exo_7_1",
      title = "Vérifier l'annulation d'un tirage",
      arabicTitle = "التحقق من إلغاء Tirage",
      level = 7,
      question = "Thème : M1=1111, M2=1111, M3=1111, M4=1111 (toutes les Mères sont Via). Le tirage doit-il être annulé ?",
      correction = "Les 4 Mères sont identiques (Via 1111) → TIRAGE À ANNULER. Raison : les 4 Mères sont identiques, ce qui viole la règle de diversité du tirage.",
      hint = "Vérifie si les 4 Mères sont identiques.",
      figureCodes = listOf("1111"),
    ),
    GeomancyExercise(
      id = "exo_7_2",
      title = "Vérifier la confirmation d'un tirage",
      arabicTitle = "التحقق من تأكيد Tirage",
      level = 7,
      question = "Thème : M1=1121 (Djanfa Almamy), M4=1121 (Djanfa Almamy), M7=1121 (Djanfa Almamy), M10=1121 (Djanfa Almamy). Le tirage est-il confirmé ? (On est dimanche.)",
      correction = "La figure du jour (dimanche) est Djanfa Almamy (1121) ou Lomara (2122). Djanfa Almamy (1121) apparaît en M1, M4, M7, M10 — toutes des maisons angulaires. → TIRAGE CONFIRMÉ. La figure du jour apparaît dans plusieurs maisons angulaires.",
      hint = "Vérifie si la figure du jour apparaît dans une maison angulaire (M1, M4, M7, M10).",
      figureCodes = listOf("1121"),
    ),

    // ============================================================
    // NIVEAU 8 — INTERPRÉTATION
    // ============================================================
    GeomancyExercise(
      id = "exo_8_1",
      title = "Interpréter un thème complet",
      arabicTitle = "تفسير theme كامل",
      level = 8,
      question = "Thème : M1=1121 (Djanfa Almamy), M2=2211 (Nouhoun), M3=1211 (Tontigui), M4=2111 (Malidjou), M5=1211 (Tontigui), M6=1121 (Djanfa Almamy), M7=2211 (Nouhoun), M8=1211 (Tontigui), M9=1211 (Tontigui), M10=2122 (Lomara), M11=2212 (Bayaro), M12=1122 (Kalalaw), M13=2211 (Nouhoun), M14=1212 (Inzan), M15=1121 (Djanfa Almamy), M16=2222 (Moussa). Interprète ce thème.",
      correction = "M1 = Djanfa Almamy (1121) → le consultant est plein d'énergie, prêt à agir. M2 = Nouhoun (2211) → gain financier possible. M3 = Tontigui (1211) → communication paisible. M4 = Malidjou (2111) → nouveau foyer. M15 = Djanfa Almamy (1121) → OUI ABSOLU — Victoire totale. M16 = Moussa (2222) → sentence neutre, stabilité. Le consultant va réussir son projet avec de l'énergie et du gain financier. Le verdict est favorable.",
      hint = "Commence par M1 (le consultant), puis M15 (le verdict), puis les autres maisons.",
      figureCodes = listOf("1121", "2211", "1211", "2111"),
    ),
    GeomancyExercise(
      id = "exo_8_2",
      title = "Interpréter le Damir",
      arabicTitle = "تفسير الضمير",
      level = 8,
      question = "Thème : M1=2211 (Nouhoun). Nouhoun est en M12 dans la grille Taskin. Quel est le Damir ?",
      correction = "M1 = Nouhoun (2211). MaisonZanati de Nouhoun = M12 (2211 dans la grille Taskin). Domaine de M12 = Ennemis cachés. Le Damir révèle que le consultant est préoccupé par ses ennemis cachés, la jalousie ou les blocages occultes. L'état émotionnel dépend de la direction de M1 : Nouhoun est ENTRANTE → 'Prêt à recevoir, cœur ouvert, en attente'.",
      hint = "Trouve la position de la figure M1 dans la grille Taskin.",
      figureCodes = listOf("2211"),
    ),

    // ============================================================
    // NIVEAU 9 — CAS PRATIQUES
    // ============================================================
    GeomancyExercise(
      id = "exo_9_1",
      title = "Cas pratique : Consultation sur la santé",
      arabicTitle = "حالة عملية : استشارة صحية",
      level = 9,
      question = "Un consultant vient te voir pour un problème de santé. Les Mères sont : M1=1121 (Djanfa Almamy), M2=2122 (Lomara), M3=1211 (Tontigui), M4=2111 (Malidjou). Calcule le thème et interprète la santé (M6).",
      correction = "M5=1211 (Tontigui), M6=1121 (Djanfa Almamy), M7=2211 (Nouhoun), M8=1211 (Tontigui). M9=1211, M10=2122, M11=2212, M12=1122. M13=2211, M14=1212, M15=1121 (Djanfa Almamy, PAIR ✓), M16=2222 (Moussa). M6 (maladie) = Djanfa Almamy (1121, ENTRANTE) → guérison possible. M1 = Djanfa Almamy (ENTRANTE) → le consultant est prêt à recevoir. M15 = Djanfa Almamy → OUI ABSOLU. Verdict : la santé va s'améliorer. Remède : Sadaqa (coq blanc ou agneau blanc aux nécessiteux, dimanche lever soleil, heure du Soleil, direction Est).",
      hint = "Commence par calculer le thème, puis regarde M6 (maladie).",
      figureCodes = listOf("1121", "2122", "1211", "2111"),
    ),
    GeomancyExercise(
      id = "exo_9_2",
      title = "Cas pratique : Consultation sur l'argent",
      arabicTitle = "حالة عملية : استشارة مالية",
      level = 9,
      question = "Un consultant vient te voir pour un problème d'argent. Les Mères sont : M1=2211 (Nouhoun), M2=1221 (Solomana), M3=2122 (Lomara), M4=2212 (Bayaro). Calcule le thème et interprète l'argent (M2).",
      correction = "M5=2112 (Ali), M6=2222 (Moussa), M7=1222 (Adama), M8=1122 (Kalalaw). M9=1112 (Lawsana), M10=1212 (Inzan), M11=1212 (Inzan), M12=2222 (Moussa). M13=2222 (Moussa), M14=1112 (Lawsana), M15=1212 (Inzan, PAIR ✓), M16=2121 (Ousmana). M2 (argent) = Solomana (1221, ENTRANTE) → gain possible. Mais M15 = Inzan (SORTANTE) → réponse défavorable. Le consultant va avoir des difficultés financières. Damir : M1=Nouhoun, maisonZanati=Nouhoun est en M12 → domaine = Ennemis cachés. Le consultant est préoccupé par un ennemi caché qui bloque ses finances. Remède : Sadaqa (sadaqa noire, charbon, graines noires, vendredi, heure de Vénus, direction Sud).",
      hint = "Regarde M2 (argent) et M15 (verdict).",
      figureCodes = listOf("2211", "1221", "2122", "2212"),
    ),

    // ============================================================
    // NIVEAU 10 — MAÎTRE INITIÉ
    // ============================================================
    GeomancyExercise(
      id = "exo_10_1",
      title = "Cas pratique : Consultation sur le mariage",
      arabicTitle = "حالة عملية : استشارة زواج",
      level = 10,
      question = "Un consultant vient te voir pour un problème de mariage. Les Mères sont : M1=1211 (Tontigui), M2=1122 (Kalalaw), M3=2211 (Nouhoun), M4=1112 (Lawsana). Calcule le thème et interprète le mariage (M7).",
      correction = "M5=1121 (Djanfa Almamy), M6=2211 (Nouhoun), M7=1212 (Inzan), M8=1212 (Inzan). M9=2211 (Nouhoun), M10=1221 (Solomana), M11=2212 (Bayaro), M12=2222 (Moussa). M13=1112 (Lawsana), M14=1112 (Lawsana), M15=2222 (Moussa, PAIR ✓), M16=1111 (Taliki). M7 (mariage) = Inzan (1212, SORTANTE) → blocage dans le mariage. M1 = Tontigui (1211, ENTRANTE) → le consultant est ouvert. M15 = Moussa (2222, FIXE) → stagnation. Verdict : situation bloquée, pas de mouvement pour le moment. Remède : patience, Sadaqa.",
      hint = "Regarde M7 (mariage) et M15 (verdict).",
      figureCodes = listOf("1211", "1122", "2211", "1112"),
    ),
    GeomancyExercise(
      id = "exo_10_2",
      title = "Cas pratique : Consultation sur un duel sportif",
      arabicTitle = "حالة عملية : استشارة duel",
      level = 10,
      question = "Deux équipes s'affrontent. Les Mères sont : M1=1121 (Djanfa Almamy), M2=2211 (Nouhoun), M3=1212 (Inzan), M4=2122 (Lomara). Calcule le thème et interprète le duel (M1 vs M7).",
      correction = "M5=1121 (Djanfa Almamy), M6=2211 (Nouhoun), M7=1212 (Inzan), M8=1121 (Djanfa Almamy). M9=2211 (Nouhoun), M10=2121 (Ousmana), M11=2212 (Bayaro), M12=2211 (Nouhoun). M13=1112 (Lawsana), M14=1112 (Lawsana), M15=2222 (Moussa, PAIR ✓), M16=1121 (Djanfa Almamy). M1 = Djanfa Almamy (1121, ENTRANTE) → Camp A est plein d'énergie. M7 = Inzan (1212, SORTANTE) → Camp B est bloqué. M15 = Moussa (2222, FIXE) → match nul. Verdict : match nul, les deux camps sont à égalité. Remède : Sadaqa pour débloquer la situation.",
      hint = "Regarde M1 (Camp A), M7 (Camp B) et M15 (verdict).",
      figureCodes = listOf("1121", "2211", "1212", "2122"),
    ),
  )

  /**
   * Retourne les exercices d'un niveau donné.
   */
  fun getExercisesByLevel(level: Int): List<GeomancyExercise> = exercises.filter { it.level == level }

  /**
   * Retourne un exercice par son ID.
   */
  fun getExercise(id: String): GeomancyExercise? = exercises.find { it.id == id }

  /**
   * Retourne tous les exercices.
   */
  fun getAllExercises(): List<GeomancyExercise> = exercises
}
