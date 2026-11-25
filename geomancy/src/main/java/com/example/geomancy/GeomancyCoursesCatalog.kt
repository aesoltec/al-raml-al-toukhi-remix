package com.example.geomancy

/**
 * Catalogue des cours de géomancie (Ilm al-Raml),
 * commençant obligatoirement par Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī
 * (Bulūgh al-Amal & Manbaʿ Uṣūl al-Raml).
 */
object GeomancyCoursesCatalog {

  val allBooks: List<CourseBook> =
    listOf(
      toukhiCourseBook,
      toukhiManbaCourseBook,
      bahriCourseBook,
      qawlCourseBook,
      hawiCourseBook,
      ms2380CourseBook,
      ms2405CourseBook,
    )

  val totalModulesCount: Int
    get() = allBooks.sumOf { it.modules.size }

  fun findBook(bookId: String): CourseBook? = allBooks.firstOrNull { it.id == bookId }

  fun findModule(moduleId: String): Pair<CourseBook, CourseModule>? {
    for (book in allBooks) {
      val module = book.modules.firstOrNull { it.id == moduleId }
      if (module != null) return book to module
    }
    return null
  }
}
