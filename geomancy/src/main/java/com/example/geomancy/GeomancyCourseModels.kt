package com.example.geomancy

/**
 * Figure géomantique mise en avant dans un module de cours avec son rôle doctrinal précis
 * dans l'ouvrage cité.
 */
data class CourseFigureFocus(
  val figureId: Int,
  val roleInLesson: String,
) {
  val figure: GeomanticFigure
    get() = AlToukhiFigures.findById(figureId)
}

/**
 * Exemple complet de thème Takht à 16 maisons tiré ou reconstitué à partir des règles d'un livre,
 * accompagné de ses 16 comptes de points d'origine et d'une lecture pas à pas.
 */
data class CourseTakhtExample(
  val title: String,
  val sourceRef: String,
  val domain: QuestionDomain,
  val planetHour: Planet,
  val question: String,
  val motherDotCounts: List<Int>,
  val interpretationSteps: List<String>,
) {
  init {
    require(motherDotCounts.size == 16 && motherDotCounts.all { it > 0 }) {
      "Un thème d'exemple de cours exige exactement 16 comptes positifs."
    }
  }

  fun computeReading(weightTable: FigureWeightTable = FigureWeightTable.default()): TakhtReading =
    AlToukhiGeomancyEngine.castFromDotCounts(
      sixteenCounts = motherDotCounts,
      domain = domain,
      questionText = question,
      activePlanetHour = planetHour,
      weightTable = weightTable,
    )
}

/**
 * Section de cours fidèlement traduite d'un chapitre, Bāb, leçon (Dars) ou feuillet du livre / manuscrit source.
 */
data class CourseLessonSection(
  val lessonRef: String,
  val heading: String,
  val arabicTerms: String = "",
  val body: String,
  val keyFormulaOrTable: String = "",
)

/**
 * Module pédagogique structuré rattaché explicitement à son traité source.
 */
data class CourseModule(
  val id: String,
  val moduleNumber: String,
  val bookCitation: String,
  val chapterRef: String,
  val title: String,
  val summary: String,
  val classicalRules: List<String>,
  val featuredFigures: List<CourseFigureFocus>,
  val takhtExample: CourseTakhtExample? = null,
  val detailedLessons: List<CourseLessonSection> = emptyList(),
)

/**
 * Livre / Cours complet issu des 7 traités et manuscrits de référence (dont Al-Ṭūkhī).
 */
data class CourseBook(
  val id: String,
  val orderIndex: Int,
  val levelBadge: String,
  val frenchTitle: String,
  val arabicTitle: String,
  val author: String,
  val editionOrManuscript: String,
  val totalPagesOrFolios: String,
  val accentElement: Element,
  val overview: String,
  val modules: List<CourseModule>,
)
