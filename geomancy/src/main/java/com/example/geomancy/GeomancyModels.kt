package com.example.geomancy

/**
 * Point impair (1 point / Fard) ou pair (2 points / Zawj) pour chacun des quatre rangs
 * élémentaires d'une figure géomantique (Feu en haut vers Terre en bas).
 */
enum class PointParity(val dots: Int, val arabicLabel: String) {
  ODD(1, "فرد (نقطة)"),
  EVEN(2, "زوج (نقطتان)");

  /** Addition binaire modulo 2 utilisée dans le calcul de Ilm al-Raml. */
  operator fun plus(other: PointParity): PointParity {
    val total = this.dots + other.dots
    return if (total % 2 == 1) ODD else EVEN
  }

  val isActive: Boolean
    get() = this == ODD
}

enum class Element(
  val arabicName: String,
  val englishName: String,
  val humor: String,
  val quality: String,
  val direction: String,
) {
  FIRE("نار", "Feu", "Bile jaune (Ṣafrāʾ)", "Chaud & Sec", "Est"),
  AIR("هواء", "Air", "Sang (Dam)", "Chaud & Humide", "Nord"),
  WATER("ماء", "Eau", "Flegme (Balgham)", "Froid & Humide", "Ouest"),
  EARTH("تراب", "Terre", "Bile noire (Sawdāʾ)", "Froid & Sec", "Sud"),
}

enum class FigureMotion(val arabicName: String, val label: String) {
  ENTERING("داخل", "Entrante (Dākhil)"),
  EXITING("خارج", "Sortante (Khārij)"),
  FIXED("ثابت", "Fixe (Thābit)"),
  MOBILE("منقلب", "Mobile (Munqalib)"),
}

enum class FortunePolarity(val arabicName: String, val label: String) {
  FORTUNATE("سعيد", "Faste (Saʿīd)"),
  UNFORTUNATE("نحس", "Défavorable (Naḥs)"),
  MIXED("ممتزج", "Mixte (Mumtazij)"),
}

enum class Planet(
  val arabicName: String,
  val englishName: String,
  val dayArabic: String,
  val dayEnglish: String,
  val spiritualRegent: String,
) {
  SUN("الشمس", "Soleil", "الأحد", "Dimanche", "Rūqyāʾīl — Illumination & Souveraineté"),
  MOON("القمر", "Lune", "الإثنين", "Lundi", "Jibrāʾīl — Réflexion & Cycles"),
  MARS("المريخ", "Mars", "الثلاثاء", "Mardi", "Samsamāʾīl — Courage & Tranchant"),
  MERCURY("عطارد", "Mercure", "الأربعاء", "Mercredi", "Mīkāʾīl — Intellect, Lettres & Parole"),
  JUPITER("المشتري", "Jupiter", "الخميس", "Jeudi", "Ṣarfayāʾīl — Miséricorde, Expansion & Sagesse"),
  VENUS("الزهرة", "Vénus", "الجمعة", "Vendredi", "ʿAnyāʾīl — Harmonie, Grâce & Union"),
  SATURN("زحل", "Saturne", "السبت", "Samedi", "Kasfayāʾīl — Profondeur, Patience & Retenue"),
  NORTH_NODE("الرأس", "Nœud Nord (Tête)", "الخميس", "Jeudi", "Seuil Ascendant — Ouverture & Croissance"),
  SOUTH_NODE("الذنب", "Nœud Sud (Queue)", "السبت", "Samedi", "Seuil Descendant — Libération & Clôture"),
}

enum class QuestionDomain(
  val title: String,
  val arabicTitle: String,
  val primaryHouse: Int,
  val secondaryHouse: Int,
  val description: String,
) {
  GENERAL(
    title = "Consultation générale",
    arabicTitle = "الكشف العام",
    primaryHouse = 1,
    secondaryHouse = 15,
    description = "État global du consultant, tendance immédiate et sentence du Juge.",
  ),
  HIDDEN_INTENT(
    title = "Pensée cachée (Ḍamīr)",
    arabicTitle = "استخراج الضمير",
    primaryHouse = 15,
    secondaryHouse = 16,
    description = "Méthode de Cheikh al-Toukhi pour dévoiler l'intention secrète du cœur.",
  ),
  WEALTH(
    title = "Biens & Subsistance",
    arabicTitle = "المال والرزق",
    primaryHouse = 2,
    secondaryHouse = 11,
    description = "Gains matériels, fortune, ressources et appuis financiers.",
  ),
  PARTNERSHIP(
    title = "Mariage & Association",
    arabicTitle = "الزواج والشراكة",
    primaryHouse = 7,
    secondaryHouse = 5,
    description = "Conjoint, contrat, entente ou opposition avec un partenaire.",
  ),
  TRAVEL(
    title = "Voyage & Déplacement",
    arabicTitle = "السفر والتنقل",
    primaryHouse = 9,
    secondaryHouse = 3,
    description = "Grands voyages, quête spirituelle, déménagement et trajets proches.",
  ),
  AUTHORITY(
    title = "Travail & Autorité",
    arabicTitle = "العمل والسلطان",
    primaryHouse = 10,
    secondaryHouse = 11,
    description = "Carrière, honneur, fonction publique et accomplissement des ambitions.",
  ),
  HEALTH(
    title = "Santé & Humeurs",
    arabicTitle = "الصحة والطبائع",
    primaryHouse = 6,
    secondaryHouse = 1,
    description = "Équilibre humoral (chaud/froid/humide/sec) et rétablissement physique.",
  ),
  OBSTACLES(
    title = "Épreuves & Secrets",
    arabicTitle = "الأسرار والعوائق",
    primaryHouse = 12,
    secondaryHouse = 8,
    description = "Obstacles voilés, jalousies, craintes et voies de protection spirituelle.",
  ),
  CONTEST(
    title = "Duel / Deux Équipes",
    arabicTitle = "الغالب والمغلوب",
    primaryHouse = 7,
    secondaryHouse = 10,
    description = "Compare le Camp A (M1, M2, M10, M13) au Camp B adverse (M7, M8, M4, M14).",
  ),
}

data class SpiritualProfile(
  val ruhaniyyaCorrespondence: String,
  val contemplativeDhikr: String,
  val spiritualTemperament: String,
  val operativeNote: String,
  val counsel: String,
)

data class GeomanticFigure(
  val id: Int, // 1..16 selon le Taskin al-Zanati / ordre de Cheikh al-Toukhi
  val westAfricanOrder: Int, // 1..16 selon l'ordre ouest-africain (Djanfa Almami -> Moussa)
  val code: String, // ex. "1222" (Feu, Air, Eau, Terre)
  val fire: PointParity,
  val air: PointParity,
  val water: PointParity,
  val earth: PointParity,
  val westAfricanName: String,
  val arabicName: String,
  val transliteration: String,
  val englishTitle: String,
  val dominantElement: Element,
  val motion: FigureMotion,
  val polarity: FortunePolarity,
  val planet: Planet,
  val zodiacSign: String,
  val arabicLetter: String,
  val abjadValue: Int,
  val timingSpeed: String,
  val generalMeaning: String,
  val spiritualProfile: SpiritualProfile,
) {
  val ranks: List<PointParity>
    get() = listOf(fire, air, water, earth)

  val activeRankCount: Int
    get() = ranks.count { it == PointParity.ODD }

  /** Représentation visuelle de la figure avec des points (• pour ODD, •• pour EVEN). */
  val visualRepresentation: String
    get() = ranks.joinToString("\n") { if (it == PointParity.ODD) "•" else "••" }

  val totalDots: Int
    get() = fire.dots + air.dots + water.dots + earth.dots
}

enum class HouseGroup(val label: String, val arabicLabel: String) {
  MOTHERS("Mères (1–4)", "الأمهات"),
  DAUGHTERS("Filles (5–8)", "البنات"),
  NIECES("Nièces (9–12)", "الحفيدات"),
  WITNESSES("Témoins (13–14)", "الشاهدان"),
  JUDGE("Le Juge (15)", "الميزان"),
  OUTCOME("Issue Finale (16)", "عاقبة العاقبة"),
}

data class HouseInfo(
  val number: Int,
  val arabicName: String,
  val englishName: String,
  val group: HouseGroup,
  val taskinRulerId: Int,
  val element: Element,
  val direction: String,
  val domainMeaning: String,
  val derivationRule: String,
)

data class ChartHouse(
  val info: HouseInfo,
  val figure: GeomanticFigure,
)

data class MigrationLink(
  val sourceHouse: Int,
  val sourceLabel: String,
  val figure: GeomanticFigure,
  val repeatedHouses: List<Int>,
  val interpretation: String,
)

data class FlankingReport(
  val targetHouse: Int,
  val targetFigure: GeomanticFigure,
  val leftNeighbor: ChartHouse?,
  val rightNeighbor: ChartHouse?,
  val status: String,
)

data class DamirReport(
  val dominantElement: Element,
  val elementCounts: Map<Element, Int>,
  val unspokenFocus: String,
  val taskinResonance: String,
)

data class AbjadExtraction(
  val totalAbjadSum: Int,
  val reducedMod28: Int,
  val extractedLetter: String,
  val letterName: String,
  val timingEstimate: String,
  val favorableDirection: String,
)

data class ChartReliabilityReport(
  val validationFigure: GeomanticFigure,
  val appearsInHouses: List<Int>,
  val isSincere: Boolean,
  val m15EvenParityValid: Boolean,
  val mothersUniformMotionWarning: String?,
  val summaryLabel: String,
  val explanation: String,
)

data class PureElementalQuadrant(
  val fireOfFire: GeomanticFigure,
  val airOfAir: GeomanticFigure,
  val waterOfWater: GeomanticFigure,
  val earthOfEarth: GeomanticFigure,
  val realizationSpeedNote: String,
)

data class BatinHighlight(
  val houseNumber: Int,
  val houseLabel: String,
  val surfaceFigure: GeomanticFigure,
  val taskinRuler: GeomanticFigure,
  val batinFigure: GeomanticFigure,
)

enum class PlanetaryDignity(val label: String, val arabicLabel: String) {
  OWN_AND_EXALTATION("Domicile & Exaltation · Sharaf", "بيت وشرف"),
  EXALTATION("Exaltation · Sharaf", "شرف"),
  OWN_HOUSE("Domicile · Taskīn", "بيت التسكين"),
  FALL("Chute · Hubūṭ", "هبوط"),
  NEUTRAL("État neutre", "مستوٍ"),
}

enum class HouseTemporalRole(val label: String, val arabicLabel: String, val groupTitle: String) {
  AWTAD_PRESENT("Présent · Piliers (Awtād)", "الحال (الأوتاد)", "Awtād (M1, M4, M7, M10)"),
  SUCCEDENT_FUTURE("Futur · Succédentes (Mā Yalī)", "المستقبل (ما يلي)", "Succédentes (M2, M5, M8, M11)"),
  CADENT_PAST("Passé · Cadentes (Sawāqiṭ)", "الماضي (السواقط)", "Cadentes (M3, M6, M9, M12)"),
  ZAWAYID_SYNTHESIS("Balance & Verdict (Zawāʾid)", "الميزان والعاقبة", "Témoins & Juge (M13–M16)"),
}

enum class HawiStrengthTier(val label: String, val arabicLabel: String, val rankWeight: Int) {
  STRONG_FORTUNE("Faste majeure (Qawiyya)", "قوية السعود", 2),
  MIXED_FORTUNE("Faste tempérée (Mumtazija)", "ممتزجة السعود", 1),
  MIXED_MISFORTUNE("Défavorable modérée (Mumtazija)", "ممتزجة النحوس", -1),
  STRONG_MISFORTUNE("Défavorable majeure (Qawiyya)", "قوية النحوس", -2),
}

data class FigureRelations(
  val source: GeomanticFigure,
  val nazar: GeomanticFigure,
  val nutq: GeomanticFigure,
  val ittisal: GeomanticFigure,
  val infisal: GeomanticFigure,
  val opposite: GeomanticFigure,
  val verticalMirror: GeomanticFigure,
)

data class TemporalDynamicsReport(
  val presentFortunateCount: Int,
  val presentUnfortunateCount: Int,
  val presentDominantElement: Element,
  val futureFortunateCount: Int,
  val futureUnfortunateCount: Int,
  val futureDominantElement: Element,
  val pastFortunateCount: Int,
  val pastUnfortunateCount: Int,
  val pastDominantElement: Element,
  val trajectorySummary: String,
)

data class RelationalResonanceSummary(
  val relationType: String,
  val targetHouseNumber: Int,
  val targetFigure: GeomanticFigure,
  val interpretation: String,
)

data class AlFulkGeneralInsights(
  val reliability: ChartReliabilityReport,
  val pureElements: PureElementalQuadrant,
  val batinHighlights: List<BatinHighlight>,
  val allBatinFigures: List<GeomanticFigure>,
  val m12ObstacleDiagnostic: String,
  val traditionalDurationHint: String,
  val temporalDynamics: TemporalDynamicsReport? = null,
  val relationalResonances: List<RelationalResonanceSummary> = emptyList(),
  val dignitySummaryNote: String = "",
)

enum class AspectRelationType(
  val label: String,
  val arabicLabel: String,
  val isHarmonious: Boolean,
) {
  SEXTILE("Tasdīs · Sextile (1/6)", "تسديس", true),
  SQUARE("Tarbīʿ · Carré (1/4)", "تربيع", false),
  TRINE("Tathlīth · Trine (1/3)", "تثليث", true),
  OPPOSITION("Muqābala · Opposition (1/2)", "مقابلة", false),
}

data class HouseAspectLink(
  val figure: GeomanticFigure,
  val firstHouse: Int,
  val secondHouse: Int,
  val aspectType: AspectRelationType,
  val interpretation: String,
)

data class TriangleSummary(
  val index: Int,
  val title: String,
  val housesLabel: String,
  val apexHouseNumber: Int,
  val apexFigure: GeomanticFigure,
)

data class AlQawlAlJamiGeneralInsights(
  val totalChartDots: Int,
  val isComplete96: Boolean,
  val airWaterOpenPoints: Int,
  val fireEarthOpenPoints: Int,
  val vitalityBalanceNote: String,
  val fiveTriangles: List<TriangleSummary>,
  val trianglesVerdictNote: String,
  val planetarySympathyNote: String,
  val aspectLinks: List<HouseAspectLink>,
  val tarabulusiTimingHint: String,
)

data class TakhtAnalysis(
  val overallPolarity: FortunePolarity,
  val verdictHeadline: String,
  val verdictDetail: String,
  val damirReport: DamirReport,
  val querentMigration: MigrationLink,
  val domainMigration: MigrationLink,
  val judgeFlanking: FlankingReport,
  val abjadExtraction: AbjadExtraction,
  val planetaryAlignmentNote: String,
  val humoralDiagnosis: String,
  val spiritualPrescription: String,
  val contestComparison: String? = null,
  val contestPrediction: ContestPrediction? = null,
  val alFulkInsights: AlFulkGeneralInsights? = null,
  val alQawlInsights: AlQawlAlJamiGeneralInsights? = null,
)

data class TakhtReading(
  val domain: QuestionDomain,
  val questionText: String,
  val activePlanetHour: Planet,
  val rawMotherDotCounts: List<Int>,
  val houses: List<ChartHouse>,
  val analysis: TakhtAnalysis,
) {
  fun house(number: Int): ChartHouse = houses[number - 1]
}
