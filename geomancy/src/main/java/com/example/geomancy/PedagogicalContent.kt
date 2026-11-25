package com.example.geomancy

/**
 * Contenu pédagogique simplifié pour les débutants.
 * Explique les concepts de la géomancie comme à des enfants de maternelle.
 * Terminologie unifiée : "NomOuestAfricain (NomArabe)".
 */
data class PedagogicalConcept(
  val id: String,
  val title: String,
  val arabicTitle: String,
  val simpleExplanation: String,
  val analogy: String,
  val stepByStep: List<String>,
  val visualAid: String = "",
  val example: String = "",
)

object PedagogicalContent {

  val concepts: List<PedagogicalConcept> = listOf(

    // ============================================================
    // CONCEPT 1 : Qu'est-ce que la géomancie ?
    // ============================================================
    PedagogicalConcept(
      id = "concept_1_what_is_geomancy",
      title = "Qu'est-ce que la géomancie ?",
      arabicTitle = "ما هي علم الرمل؟",
      simpleExplanation = "La géomancie, c'est comme jouer avec du sable pour connaître l'avenir. Tu traces des points dans le sable, et le sable te répond avec des figures.",
      analogy = "C'est comme quand tu dessines dans le sable à la plage. Tu fais des points, et ces points forment des images qui te disent des choses.",
      stepByStep = listOf(
        "1. Tu prends du sable fin.",
        "2. Tu traces des points avec ton doigt.",
        "3. Tu comptes les points deux par deux.",
        "4. Les points forment des figures.",
        "5. Tu lis les figures pour connaître la réponse.",
      ),
      visualAid = "• = un point (impair)\n•• = deux points (pair)",
      example = "Si tu traces 5 points, tu gardes 1 point (car 5 est impair). Si tu traces 4 points, tu gardes 2 points (car 4 est pair).",
    ),

    // ============================================================
    // CONCEPT 2 : Les 16 figures
    // ============================================================
    PedagogicalConcept(
      id = "concept_2_16_figures",
      title = "Les 16 figures géomantiques",
      arabicTitle = "الأشكال الستة عشر",
      simpleExplanation = "Il existe 16 figures possibles. Chaque figure est composée de 4 lignes. Chaque ligne est soit un point (•) soit deux points (••).",
      analogy = "C'est comme un code secret avec 4 cases. Chaque case peut être vide (•) ou pleine (••). Il y a 16 combinaisons possibles.",
      stepByStep = listOf(
        "1. La 1ère ligne = la Tête (Feu)",
        "2. La 2ème ligne = le Cou (Air)",
        "3. La 3ème ligne = le Ventre (Eau)",
        "4. La 4ème ligne = le Pied (Terre)",
        "5. Chaque ligne est soit • (impair) soit •• (pair)",
        "6. Il y a 16 combinaisons possibles (2×2×2×2 = 16)",
      ),
      visualAid = "Taliki (الطريق) = • • • • (toutes les lignes sont des points simples)\nMoussa (الجماعة) = •• •• •• •• (toutes les lignes sont des points doublés)",
      example = "La figure Djanfa Almamy (الجودلة) a le code 1121 : • • •• • (point, point, deux points, point)",
    ),

    // ============================================================
    // CONCEPT 3 : La grille de repos (Taskin)
    // ============================================================
    PedagogicalConcept(
      id = "concept_3_taskin",
      title = "La grille de repos (Taskin al-Falak al-Dair)",
      arabicTitle = "تسكين الفلك الدائر",
      simpleExplanation = "Chaque figure a une maison fixe dans le ciel. C'est comme une carte avec 16 cases, et chaque figure a sa place attitrée.",
      analogy = "C'est comme un parking avec 16 places. Chaque voiture (figure) a sa place réservée. Quand une voiture est à sa place, tout va bien.",
      stepByStep = listOf(
        "1. Il y a 16 maisons (M1 à M16).",
        "2. Chaque maison a une figure qui lui appartient.",
        "3. M1 = 1121 (Djanfa Almamy), M2 = 1222 (Adama), etc.",
        "4. Quand une figure tombe dans sa maison de repos, c'est un bon signe.",
        "5. La disposition de l'écu est : M8 M7 M6 M5 M4 M3 M2 M1 (rangée du haut).",
      ),
      visualAid = "M8  M7  M6  M5  M4  M3  M2  M1\n  M12    M11      M10     M9\n      M14             M13\n            M15             M16",
      example = "M1 = 1121 (Djanfa Almamy) est la maison du consultant. Si Djanfa Almamy tombe en M1, c'est un bon signe.",
    ),

    // ============================================================
    // CONCEPT 4 : Le Damir
    // ============================================================
    PedagogicalConcept(
      id = "concept_4_damir",
      title = "La science du Damir (l'intention cachée)",
      arabicTitle = "علم الضمير",
      simpleExplanation = "Le Damir, c'est la pensée secrète du consultant. Le maître la lit avant que le consultant ait parlé. C'est comme lire dans les pensées.",
      analogy = "C'est comme quand tu regardes quelqu'un et tu sais ce qu'il pense sans qu'il parle. Le sable fait la même chose.",
      stepByStep = listOf(
        "1. Le maître regarde la figure M1 (le consultant).",
        "2. Il trouve la maison où cette figure repose (maison cible).",
        "3. Cette maison révèle le domaine secret du consultant.",
        "4. Il compte les répétitions (plus il y en a, plus c'est confirmé).",
        "5. Il calcule la Part de Fortune (Al-Sahm) pour trouver le désir caché.",
        "6. Il regarde la direction de M1 pour connaître l'état émotionnel.",
      ),
      visualAid = "M1 = Djanfa Almamy (1121) → maison cible = M1 → domaine = Âme/santé\nLe consultant est préoccupé par sa santé.",
      example = "Si M1 = Nouhoun (2211), la maison cible est M12 (Ennemis cachés). Le consultant est préoccupé par ses ennemis.",
    ),

    // ============================================================
    // CONCEPT 5 : La métrologie
    // ============================================================
    PedagogicalConcept(
      id = "concept_5_metrologie",
      title = "La métrologie (calculer l'invisible)",
      arabicTitle = "المقاييس",
      simpleExplanation = "La métrologie, c'est calculer des choses invisibles comme la profondeur, la hauteur, la distance, la durée, etc.",
      analogy = "C'est comme mesurer avec une règle, mais avec le sable. Le sable te donne des nombres, et tu les convertis en centimètres, kilomètres, jours, etc.",
      stepByStep = listOf(
        "1. Profondeur (puits) : abjad(M4) × 54 cm",
        "2. Hauteur (objet) : abjad(M9) × 22 cm",
        "3. Distance : abjad(M3) × 6 km",
        "4. Durée : selon la direction de M15 (jours/semaines/mois/années)",
        "5. Couleur : FEU=rouge, AIR=jaune, EAU=bleu, TERRE=brun",
        "6. Odeur : FEU=âcre, AIR=floral, EAU=humide, TERRE=terreux",
      ),
      visualAid = "M4 = Mangouchi (2221, abjad=44) → profondeur = 44 × 54 = 2 376 cm ≈ 23,76 m",
      example = "Si M4 = Djanfa Almamy (1121, abjad=108) → profondeur = 108 × 54 = 5 832 cm ≈ 58,32 m",
    ),

    // ============================================================
    // CONCEPT 6 : Le Juge et la Sentence
    // ============================================================
    PedagogicalConcept(
      id = "concept_6_juge_sentence",
      title = "Le Juge (M15) et la Sentence (M16)",
      arabicTitle = "الحاكم والعاقبة",
      simpleExplanation = "Le Juge (M15) est le verdict final de la consultation. La Sentence (M16) est le conseil divin. C'est comme un tribunal : le Juge donne le verdict, la Sentence donne le conseil.",
      analogy = "C'est comme un procès : le Juge (M15) dit 'coupable' ou 'non coupable', et la Sentence (M16) dit 'voici ce que tu dois faire'.",
      stepByStep = listOf(
        "1. M15 = M13 ⊕ M14 (Juge Suprême)",
        "2. M15 doit avoir un nombre PAIR de points impairs (0, 2 ou 4)",
        "3. Si M15 est impair → thème invalide",
        "4. M16 = M15 ⊕ M1 (Sentence Finale)",
        "5. ENTRANTE → favorable, SORTANTE → défavorable, FIXE → stagnation, MOBILE → incertitude",
      ),
      visualAid = "M15 = 1121 (Djanfa Almamy) → OUI ABSOLU — Victoire totale\nM16 = 2222 (Moussa) → Sentence neutre, stabilité",
      example = "Si M15 = 2121 (Ousmana) → NON — Perte déclarée. Le consultant va perdre.",
    ),

    // ============================================================
    // CONCEPT 7 : Les aspects et passations
    // ============================================================
    PedagogicalConcept(
      id = "concept_7_aspects",
      title = "Les aspects et passations",
      arabicTitle = "الجوانب والمرورات",
      simpleExplanation = "Les passations, c'est quand la même figure apparaît dans plusieurs maisons. Les aspects, c'est les relations entre les figures selon leurs éléments.",
      analogy = "C'est comme des amis qui se retrouvent dans plusieurs endroits. Si deux amis ont le même caractère (élément), ils s'entendent bien. S'ils sont opposés, ils se disputent.",
      stepByStep = listOf(
        "1. Passation = même figure en plusieurs maisons",
        "2. M1 en M7 → relation/confrontation directe",
        "3. M1 en M15 → fusion totale (destin = verdict)",
        "4. Même élément → alliance, harmonie",
        "5. Éléments opposés (Feu↔Eau, Air↔Terre) → friction, opposition",
        "6. Réaction Mutuelle : si figureA.maisonZanati == positionActuelle(figureB) ET vice versa → synergie parfaite",
      ),
      visualAid = "Feu ↔ Eau = opposition\nAir ↔ Terre = opposition\nFeu ↔ Feu = alliance",
      example = "M1 = Djanfa Almamy (Feu) et M7 = Malidjou (Feu) → même élément → alliance",
    ),

    // ============================================================
    // CONCEPT 8 : La confirmation et l'annulation
    // ============================================================
    PedagogicalConcept(
      id = "concept_8_confirmation",
      title = "La confirmation et l'annulation",
      arabicTitle = "التأكيد والإلغاء",
      simpleExplanation = "Parfois, un tirage doit être annulé. Parfois, il est confirmé. C'est comme un test : si les résultats sont cohérents, le tirage est bon. Sinon, il faut recommencer.",
      analogy = "C'est comme une recette de cuisine : si tu as oublié un ingrédient, la recette est ratée (annulée). Si tout est correct, la recette est réussie (confirmée).",
      stepByStep = listOf(
        "1. ANNULER si : les 4 Mères sont identiques",
        "2. ANNULER si : Via (1111) ou Populus (2222) répétées partout",
        "3. ANNULER si : M15 a un nombre impair de points impairs",
        "4. CONFIRMER si : la figure du jour apparaît dans une maison angulaire (M1, M4, M7, M10)",
        "5. CONFIRMER si : la Part de Fortune tombe dans la Maison de la Question",
        "6. CONFIRMER si : M1 se répète dans la Maison de la Question",
      ),
      visualAid = "Lundi → Moussa (2222) ou Taliki (1111)\nMardi → Bayaro (2212) ou Malidjou (2111)\nMercredi → Solomana (1221) ou Ali (2112)",
      example = "Si on est dimanche et Djanfa Almamy (1121) apparaît en M1, M4, M7 et M10 → tirage confirmé.",
    ),

    // ============================================================
    // CONCEPT 9 : Le code éthique
    // ============================================================
    PedagogicalConcept(
      id = "concept_9_ethique",
      title = "Le code éthique et le secret",
      arabicTitle = "الأخلاق والسر",
      simpleExplanation = "Le géomancien doit être modeste, patient et respectueux. Il ne doit pas se vanter de ses connaissances. Certains secrets ne sont jamais révélés.",
      analogy = "C'est comme un médecin : il sait des choses sur toi, mais il ne les dit à personne. Le géomancien est comme un médecin de l'âme.",
      stepByStep = listOf(
        "1. L'élève ne demande pas le pourquoi au maître.",
        "2. Le maître n'enseigne que s'il est satisfait du comportement de l'élève.",
        "3. La libération (labila) sanctionne le tracé sans faute.",
        "4. L'expert doit rester modeste et respecter ses maîtres.",
        "5. Le dalilu (secret personnel) et les kirisiw (formules) ne sont pas publiables.",
        "6. Une base honnête contient des cases volontairement vides.",
      ),
      visualAid = "Imitation → Patience → Services → Cadeaux → Libération (labila)",
      example = "Un maître n'enseigne que s'il est satisfait du comportement de l'élève. Si l'élève est impatient, le maître ne lui enseigne pas.",
    ),

    // ============================================================
    // CONCEPT 10 : La posture du maître
    // ============================================================
    PedagogicalConcept(
      id = "concept_10_posture",
      title = "La posture du maître dans la restitution",
      arabicTitle = "موقف المعلم في الإفصاح",
      simpleExplanation = "Le maître doit parler comme un sage : avec autorité, bienveillance et précision. Il ne doit jamais dire 'peut-être'.",
      analogy = "C'est comme un juge au tribunal : il donne son verdict avec autorité et clarté. Il ne dit pas 'peut-être coupable'.",
      stepByStep = listOf(
        "1. L'Accueil Sacré : saluer le consultant avec respect.",
        "2. La Révélation du Damir : révéler la pensée secrète.",
        "3. La Justification Technique : expliquer avec l'équation géomantique.",
        "4. Le Verdict Tranchant : donner la réponse directe.",
        "5. Le Remède Sacré : proposer le remède.",
      ),
      visualAid = "Accueil → Damir → Justification → Verdict → Remède",
      example = "LA SENTENCE EST SANS APPEL : Oui, tu réussiras. (pas 'peut-être tu réussiras')",
    ),
  )

  /**
   * Retourne les concepts d'un niveau donné.
   */
  fun getConceptsByLevel(level: Int): List<PedagogicalConcept> {
    // Map level to concept index range
    return when (level) {
      1 -> concepts.subList(0, 3)
      2 -> concepts.subList(3, 6)
      3 -> concepts.subList(6, 8)
      4 -> concepts.subList(8, 10)
      else -> concepts
    }
  }

  /**
   * Retourne un concept par son ID.
   */
  fun getConcept(id: String): PedagogicalConcept? = concepts.find { it.id == id }

  /**
   * Retourne tous les concepts.
   */
  fun getAllConcepts(): List<PedagogicalConcept> = concepts
}
