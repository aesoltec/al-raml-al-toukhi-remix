package com.example.geomancy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GeomancyCoursesCatalogTest {

  @Test
  fun `catalog starts strictly with Toukhi treatises followed by all 7 books`() {
    val books = GeomancyCoursesCatalog.allBooks
    assertEquals(7, books.size)
    assertEquals("book_1_toukhi", books[0].id)
    assertEquals("book_2_toukhi_manba", books[1].id)
    assertEquals(1, books[0].orderIndex)
    assertEquals(2, books[1].orderIndex)
    assertTrue(books[0].author.contains("Ṭūkhī"))
    assertTrue(books[1].author.contains("Ṭūkhī"))
    assertEquals(listOf(1, 2, 3, 4, 5, 6, 7), books.map { it.orderIndex })
    assertEquals(8, books[1].modules.size)
    assertTrue(books[1].modules.first().chapterRef.contains("Leçons 1 à 10"))
    assertTrue(books[1].modules.last().chapterRef.contains("Leçons 71 à 80"))
  }

  @Test
  fun `every book and module cites its source book and validates figures and Takht examples`() {
    val books = GeomancyCoursesCatalog.allBooks
    assertTrue(GeomancyCoursesCatalog.totalModulesCount >= 29)

    for (book in books) {
      assertTrue(book.frenchTitle.isNotBlank())
      assertTrue(book.arabicTitle.isNotBlank())
      assertTrue(book.author.isNotBlank())
      assertTrue(book.modules.size >= 2)

      for (module in book.modules) {
        assertTrue(
          "Module ${module.id} must cite its source book",
          module.bookCitation.isNotBlank(),
        )
        assertTrue(module.chapterRef.isNotBlank())
        assertTrue(module.classicalRules.isNotEmpty())
        assertTrue(module.featuredFigures.isNotEmpty())
        assertTrue(
          "Module ${module.id} must have faithful detailedLessons",
          module.detailedLessons.isNotEmpty(),
        )
        module.detailedLessons.forEach { section ->
          assertTrue(section.lessonRef.isNotBlank())
          assertTrue(section.heading.isNotBlank())
          assertTrue(section.body.isNotBlank())
        }

        module.featuredFigures.forEach { focus ->
          val fig = focus.figure
          assertTrue(fig.id in 1..16)
          assertTrue(focus.roleInLesson.isNotBlank())
        }

        val example = module.takhtExample
        assertNotNull("Module ${module.id} must provide a worked Takht example", example)
        example!!
        assertEquals(16, example.motherDotCounts.size)
        val reading = example.computeReading()
        assertEquals(16, reading.houses.size)
        assertTrue(example.sourceRef.isNotBlank())
        assertTrue(example.interpretationSteps.isNotEmpty())
      }
    }

    val manbaBook = books.first { it.id == "book_2_toukhi_manba" }
    val manbaLessons = manbaBook.modules.flatMap { it.detailedLessons }
    assertEquals(80, manbaLessons.size)
    (1..80).forEach { n ->
      assertEquals("Leçon $n", manbaLessons[n - 1].lessonRef)
    }
  }

  @Test
  fun `worked Takht examples match exact textbook house derivations`() {
    // Manbaʿ Uṣūl al-Raml M2.1 (Leçons 1-10) : Taliki, Adama, Ousmana, Malidjou -> Juge Moussa
    val (_, manbaM1) = GeomancyCoursesCatalog.findModule("m_manba_1_origines_point")!!
    val manbaR1 = manbaM1.takhtExample!!.computeReading()
    assertEquals(16, manbaR1.house(1).figure.id) // Taliki
    assertEquals(1, manbaR1.house(2).figure.id)  // Adama
    assertEquals(2, manbaR1.house(3).figure.id)  // Ousmana
    assertEquals(12, manbaR1.house(4).figure.id) // Malidjou
    assertEquals(10, manbaR1.house(5).figure.id) // Kalalaw
    assertEquals(13, manbaR1.house(6).figure.id) // Tontigui Garya
    assertEquals(6, manbaR1.house(7).figure.id)  // Solomana
    assertEquals(13, manbaR1.house(8).figure.id) // Tontigui Garya
    assertEquals(2, manbaR1.house(13).figure.id) // Ousmana
    assertEquals(2, manbaR1.house(14).figure.id) // Ousmana
    assertEquals(4, manbaR1.house(15).figure.id) // Moussa
    assertEquals(16, manbaR1.house(16).figure.id) // Taliki

    // Manbaʿ Uṣūl al-Raml M2.4 (Leçons 31-40) : Nouhoun, Ousmana, Bayada, Adama -> Taliki
    val (_, manbaM4) = GeomancyCoursesCatalog.findModule("m_manba_4_etats_maisons")!!
    val manbaR4 = manbaM4.takhtExample!!.computeReading()
    assertEquals(11, manbaR4.house(1).figure.id) // Nouhoun
    assertEquals(1, manbaR4.house(4).figure.id)  // Adama
    assertEquals(3, manbaR4.house(7).figure.id)  // Inzan
    assertEquals(3, manbaR4.house(10).figure.id) // Inzan
    assertEquals(10, manbaR4.house(13).figure.id) // Kalalaw
    assertEquals(11, manbaR4.house(14).figure.id) // Nouhoun
    assertEquals(16, manbaR4.house(15).figure.id) // Taliki

    // Manbaʿ Uṣūl al-Raml M2.5 (Leçons 41-50, Ḍamīr) : Mangouchi, Bayada -> Badra Ali / Malidjou
    val (_, manbaM5) = GeomancyCoursesCatalog.findModule("m_manba_5_damir_khabiya")!!
    val manbaR5 = manbaM5.takhtExample!!.computeReading()
    assertEquals(7, manbaR5.house(1).figure.id)  // Mangouchi
    assertEquals(9, manbaR5.house(2).figure.id)  // Bayada
    assertEquals(7, manbaR5.house(5).figure.id)  // Mangouchi
    assertEquals(9, manbaR5.house(11).figure.id) // Bayada
    assertEquals(15, manbaR5.house(15).figure.id) // Badra Ali
    assertEquals(12, manbaR5.house(16).figure.id) // Malidjou

    // Manbaʿ Uṣūl al-Raml M2.7 (Leçons 61-70) : Malidjou, Ousmana, Nouhoun, Adama -> Moussa / Malidjou
    val (_, manbaM7) = GeomancyCoursesCatalog.findModule("m_manba_7_masail_pratiques")!!
    val manbaR7 = manbaM7.takhtExample!!.computeReading()
    assertEquals(12, manbaR7.house(1).figure.id) // Malidjou
    assertEquals(9, manbaR7.house(9).figure.id)  // Bayada
    assertEquals(13, manbaR7.house(10).figure.id) // Tontigui Garya
    assertEquals(4, manbaR7.house(15).figure.id)  // Moussa
    assertEquals(12, manbaR7.house(16).figure.id) // Malidjou

    // Manbaʿ Uṣūl al-Raml M2.8 (Leçons 71-80) : Adama, Nouhoun, Lomara, Djanfa -> Solomana
    val (_, manbaM8) = GeomancyCoursesCatalog.findModule("m_manba_8_synthese_arcanes")!!
    val manbaR8 = manbaM8.takhtExample!!.computeReading()
    assertEquals(1, manbaR8.house(1).figure.id)  // Adama
    assertEquals(11, manbaR8.house(2).figure.id) // Nouhoun
    assertEquals(8, manbaR8.house(3).figure.id)  // Lomara
    assertEquals(14, manbaR8.house(4).figure.id) // Djanfa Almami
    assertEquals(9, manbaR8.house(13).figure.id) // Bayada
    assertEquals(13, manbaR8.house(14).figure.id) // Tontigui Garya
    assertEquals(6, manbaR8.house(15).figure.id)  // Solomana

    // Module 3.3 (Al-Fulk al-Mashḥūn, pp. 89-90) : Ṣidq al-Raml doit être sincère
    val (_, bahriM23) = GeomancyCoursesCatalog.findModule("m2_3_sidq_batin_pures")!!
    val bahriReading = bahriM23.takhtExample!!.computeReading()
    val reliability = bahriReading.analysis.alFulkInsights?.reliability
    assertNotNull(reliability)
    assertTrue("L'exemple p. 90 doit vérifier Ṣidq al-Raml", reliability!!.isSincere)

    // Module 4.1 (Al-Qawl al-Jāmiʿ, p. 34) : exactitude M1..M15
    val (_, qawlM31) = GeomancyCoursesCatalog.findModule("m3_1_modele_96_p34")!!
    val qawlReading = qawlM31.takhtExample!!.computeReading()
    assertEquals(10, qawlReading.house(1).figure.id) // Nuṣra Khārija (Kalalaw)
    assertEquals(8, qawlReading.house(2).figure.id)  // Ḥumra (Lomara)
    assertEquals(10, qawlReading.house(3).figure.id) // Nuṣra Khārija (Kalalaw)
    assertEquals(7, qawlReading.house(4).figure.id)  // Inkis (Mangouchi)
    assertEquals(3, qawlReading.house(5).figure.id)  // Inzan
    assertEquals(5, qawlReading.house(6).figure.id)  // Lawsana
    assertEquals(4, qawlReading.house(7).figure.id)  // Moussa
    assertEquals(7, qawlReading.house(8).figure.id)  // Mangouchi
    assertEquals(1, qawlReading.house(9).figure.id)  // Adama
    assertEquals(14, qawlReading.house(10).figure.id) // Djanfa Almami
    assertEquals(8, qawlReading.house(11).figure.id)  // Lomara
    assertEquals(7, qawlReading.house(12).figure.id)  // Mangouchi
    assertEquals(2, qawlReading.house(13).figure.id)  // Ousmana
    assertEquals(2, qawlReading.house(14).figure.id)  // Ousmana
    assertEquals(4, qawlReading.house(15).figure.id)  // Moussa (Juge)
  }
}
