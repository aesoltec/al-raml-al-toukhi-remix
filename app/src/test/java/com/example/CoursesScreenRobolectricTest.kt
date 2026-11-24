package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import com.example.data.CoursePdfExporter
import com.example.geomancy.CourseTakhtExample
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.GeomanticFigure
import com.example.ui.screens.GuideScreen
import com.example.ui.theme.AlRamlTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CoursesScreenRobolectricTest {

  @get:Rule
  val composeRule = createComposeRule()

  @Test
  fun `guide screen defaults to courses starting with Toukhi and allows figure and Takht opening`() {
    val selectedBookId = GeomancyCoursesCatalog.allBooks.first().id
    val completed = mutableSetOf<String>()
    var openedFigure: GeomanticFigure? = null
    var openedExample: CourseTakhtExample? = null

    composeRule.setContent {
      AlRamlTheme {
        GuideScreen(
          selectedCourseBookId = selectedBookId,
          completedCourseModuleIds = completed,
          onSelectCourseBook = {},
          onToggleCourseModuleCompleted = { id ->
            if (id in completed) completed.remove(id) else completed.add(id)
          },
          onOpenFigureDetail = { openedFigure = it },
          onOpenTakhtExample = { openedExample = it },
        )
      }
    }

    composeRule.onNodeWithTag("tab_learning_courses").assertIsDisplayed()
    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("courses_pdf_export_card"))
    composeRule.onNodeWithTag("export_book_pdf_button").assertIsDisplayed()
    composeRule.onNodeWithTag("export_all_courses_pdf_button").assertIsDisplayed()

    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("active_course_book_header"))
    composeRule.onNodeWithTag("active_course_book_header").assertIsDisplayed()
    composeRule.onNodeWithText("98 pages · 40 Bāb + Jadāwil", substring = true)
      .assertIsDisplayed()

    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("course_module_card_m1_1_trace_16_lignes"))
    composeRule.onNodeWithTag("course_detailed_lessons_m1_1_trace_16_lignes")
      .performScrollTo()
      .assertIsDisplayed()
    composeRule.onNodeWithTag("course_figure_m1_1_trace_16_lignes_1")
      .performScrollTo()
      .performClick()
    assertNotNull(openedFigure)
    assertEquals(1, openedFigure!!.id)

    composeRule.onNodeWithTag("course_open_takht_m1_1_trace_16_lignes")
      .performScrollTo()
      .performClick()
    assertNotNull(openedExample)
    assertEquals("Bulūgh al-Amal (Al-Ṭūkhī), Bāb 1, pp. 6–8", openedExample!!.sourceRef)

    composeRule.onNodeWithTag("course_toggle_complete_m1_1_trace_16_lignes")
      .performScrollTo()
      .performClick()
    assertTrue("m1_1_trace_16_lignes" in completed)
  }

  @Test
  fun `switching books and tabs updates displayed curriculum and timeline`() {
    var selectedBook = "book_2_bahri"

    composeRule.setContent {
      AlRamlTheme {
        GuideScreen(
          selectedCourseBookId = selectedBook,
          completedCourseModuleIds = setOf("m2_1_origines_temps"),
          onSelectCourseBook = { selectedBook = it },
        )
      }
    }

    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("active_course_book_header"))
    composeRule.onNodeWithTag("active_course_book_header").assertIsDisplayed()
    composeRule.onNodeWithText("401 pages numérotées", substring = true)
      .assertIsDisplayed()
    composeRule.onNodeWithText("1/5 assimilés", substring = true).assertIsDisplayed()

    composeRule.onNodeWithTag("guide_screen_list").performScrollToIndex(0)
    composeRule.onNodeWithTag("tab_historical_timeline").performClick()
    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("timeline_milestone_1"))
    composeRule.onNodeWithTag("timeline_milestone_1").assertIsDisplayed()

    composeRule.onNodeWithTag("guide_screen_list").performScrollToIndex(0)
    composeRule.onNodeWithTag("tab_casting_guide").performClick()
    composeRule.onNodeWithText("1. Formuler l'intention", substring = true).assertIsDisplayed()
  }

  @Test
  fun `manba usul al raml course renders all 8 modules and opens Takht example`() {
    var openedExample: CourseTakhtExample? = null

    composeRule.setContent {
      AlRamlTheme {
        GuideScreen(
          selectedCourseBookId = "book_2_toukhi_manba",
          completedCourseModuleIds = setOf("m_manba_1_origines_point"),
          onSelectCourseBook = {},
          onOpenTakhtExample = { openedExample = it },
        )
      }
    }

    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("active_course_book_header"))
    composeRule.onNodeWithText("80 Leçons progressives (Thamānūn Darsan)", substring = true)
      .assertIsDisplayed()
    composeRule.onNodeWithText("1/8 assimilés", substring = true).assertIsDisplayed()

    composeRule.onNodeWithTag("guide_screen_list")
      .performScrollToNode(hasTestTag("course_module_card_m_manba_5_damir_khabiya"))
    composeRule.onNodeWithTag("course_module_header_m_manba_5_damir_khabiya")
      .performScrollTo()
      .performClick()
    composeRule.onNodeWithTag("course_open_takht_m_manba_5_damir_khabiya")
      .performScrollTo()
      .performClick()

    assertNotNull(openedExample)
    assertTrue(openedExample!!.sourceRef.contains("Manbaʿ Uṣūl al-Raml"))
  }

  @Test
  fun `course pdf exporter generates valid multi-page pdfs for single books and full curriculum`() {
    val toukhiBook = GeomancyCoursesCatalog.findBook("book_1_toukhi")!!
    val manbaBook = GeomancyCoursesCatalog.findBook("book_2_toukhi_manba")!!

    val toukhiBytes = CoursePdfExporter.exportBookToPdfBytes(toukhiBook)
    val manbaBytes = CoursePdfExporter.exportBookToPdfBytes(manbaBook)
    val allBytes = CoursePdfExporter.exportAllBooksToPdfBytes()

    val toukhiHeader = String(toukhiBytes, 0, minOf(16, toukhiBytes.size), Charsets.US_ASCII)
    val allText = String(allBytes, Charsets.ISO_8859_1)

    assertTrue("Toukhi PDF must start with %PDF-", toukhiHeader.startsWith("%PDF-"))
    assertTrue("Toukhi PDF should not be empty", toukhiBytes.size > 4_000)
    assertTrue("Manba 80-lesson PDF should be multi-page", manbaBytes.size > 8_000)
    assertTrue("Full curriculum PDF must be larger than single book", allBytes.size > toukhiBytes.size)
    assertTrue("Full PDF must contain PDF pages", allText.contains("/Type /Page"))
    assertEquals("al_raml_cours_book_1_toukhi.pdf", CoursePdfExporter.suggestedPdfFileName(toukhiBook))
    assertEquals("al_raml_cours_complet_7_livres.pdf", CoursePdfExporter.suggestedPdfFileName(null))
  }
}
