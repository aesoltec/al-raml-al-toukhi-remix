package com.example.geomancy

/**
 * Niveaux de fiabilité pour les composants du moteur de géomancie.
 * Basé sur le fichier canonique skillsGeomancie.md (v2.13).
 */
enum class ReliabilityLevel(val label: String, val description: String) {
  A("Attesté", "Source académique consultée, catalogue de bibliothèque, notice d'autorité"),
  B("Source unique", "Forum de praticiens, site ésotérique, presse — utilisable comme donnée sur une tradition"),
  C("Calculé/vérifié", "Cohérence mathématique éprouvée, reproductible"),
  D("NON SOURCÉ", "Présent dans la demande, absent de toute source — INTERDIT de l'utiliser comme règle"),
}

/**
 * Marqueurs de fiabilité pour chaque composant du moteur.
 */
object ReliabilityMarkers {
  // Règles authentiques [A]
  val GENERATION_ALGORITHM = ReliabilityLevel.A // Mères → Filles → Nièces → Témoins → Juge → Sentence
  val TASKIN_GRID = ReliabilityLevel.A // M1=1121, M2=1222, ..., M16=2222
  val FIGURE_NAMES = ReliabilityLevel.A // Noms des 16 figures
  val M15_PARITY = ReliabilityLevel.A // M15 doit être pair
  val M16_FORMULA = ReliabilityLevel.A // M16 = M15 ⊕ M1
  val DAMIR_ALGORITHM = ReliabilityLevel.A // 4 étapes du Damir
  val CONTEST_RULES = ReliabilityLevel.A // Rencontre des deux armées (Manbaʿ p.77)
  val LITIGE_RULES = ReliabilityLevel.A // Litige (Manbaʿ p.75)
  val QUARTERS = ReliabilityLevel.A // Awtād, Succédentes, Cadentes (Bulūgh Bāb IV)
  val ETHICAL_CODE = ReliabilityLevel.A // Code éthique (Jansen 2011)
  val BAMBARA_REMEDIES = ReliabilityLevel.A // Remèdes bambara (Bertaux 1984)

  // Données non vérifiées [B]
  val FIGURE_PLANET = ReliabilityLevel.B // Correspondances figure ↔ planète
  val FIGURE_ELEMENT = ReliabilityLevel.B // Correspondances figure ↔ élément
  val PLANETARY_HOURS = ReliabilityLevel.B // Heures planétaires
  val TASKIN_GRIDS = ReliabilityLevel.B // Grilles de tasākīn (forums)
  val WAFQ = ReliabilityLevel.B // Carrés magiques

  // Inventions [D]
  val SPORT_SCORE = ReliabilityLevel.D // Prédiction de score sportif (buts, cartons)
  val WEATHER = ReliabilityLevel.D // Prédiction de météo
  val METROLOGY = ReliabilityLevel.D // Calculs métrologiques (profondeur, hauteur, distance, durée)
  val COLORS = ReliabilityLevel.D // Prédiction de couleur
  val ODORS = ReliabilityLevel.D // Prédiction d'odeur
  val ABJAD_EXTRACTION = ReliabilityLevel.D // Extraction Abjad pour les figures
  val DURATION = ReliabilityLevel.D // Prédiction de durée
  val FIGURE_WEIGHTS = ReliabilityLevel.D // Poids des figures
  val OPEN_POINTS = ReliabilityLevel.D // Points ouverts (droite vs gauche)
  val BTTS = ReliabilityLevel.D // Both teams to score
  val YELLOW_CARDS = ReliabilityLevel.D // Cartons jaunes
  val RED_CARDS = ReliabilityLevel.D // Cartons rouges
  val TOTAL_GOALS = ReliabilityLevel.D // Total de buts
  val ALTERNATE_SCORE = ReliabilityLevel.D // Score alternatif

  /**
   * Vérifie si un composant est fiable (niveau A, B ou C).
   */
  fun isReliable(level: ReliabilityLevel): Boolean = level != ReliabilityLevel.D

  /**
   * Vérifie si un composant est authentique (niveau A).
   */
  fun isAuthentic(level: ReliabilityLevel): Boolean = level == ReliabilityLevel.A
}
