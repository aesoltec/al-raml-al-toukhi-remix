package com.example.geomancy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CourseDataValidationTest {

  @Test
  fun allCourseBooksHaveValidModules() {
    val books = GeomancyCoursesCatalog.allBooks
    assertTrue(books.isNotEmpty())
    books.forEach { book ->
      assertTrue("Book  has no modules", book.modules.isNotEmpty())
      book.modules.forEach { module ->
        assertTrue("Module  has no classicalRules", module.classicalRules.isNotEmpty())
        assertTrue("Module  has no featuredFigures", module.featuredFigures.isNotEmpty())
        assertTrue("Module  has no detailedLessons", module.detailedLessons.isNotEmpty())
      }
    }
  }

  @Test
  fun allDetailedLessonsHaveRequiredFields() {
    val books = GeomancyCoursesCatalog.allBooks
    books.forEach { book ->
      book.modules.forEach { module ->
        module.detailedLessons.forEach { lesson ->
          assertTrue("Lesson in  has blank lessonRef", lesson.lessonRef.isNotBlank())
          assertTrue("Lesson in  has blank heading", lesson.heading.isNotBlank())
          assertTrue("Lesson in  has blank body", lesson.body.isNotBlank())
        }
      }
    }
  }

  @Test
  fun allFeaturedFiguresHaveValidIds() {
    val books = GeomancyCoursesCatalog.allBooks
    books.forEach { book ->
      book.modules.forEach { module ->
        module.featuredFigures.forEach { focus ->
          assertTrue("Figure id  in  is invalid", focus.figureId in 1..16)
          assertTrue("Role in  is blank", focus.roleInLesson.isNotBlank())
        }
      }
    }
  }

  @Test
  fun allInteractiveLessonsHaveValidData() {
    val lessons = InteractiveLessonsData.getAllLessons()
    assertTrue(lessons.isNotEmpty())
    lessons.forEach { lesson ->
      assertTrue("Lesson  has blank title", lesson.title.isNotBlank())
      assertTrue("Lesson  has blank content", lesson.content.isNotBlank())
      assertTrue("Lesson  has invalid level", lesson.level in 1..4)
      assertTrue("Lesson  has no figures", lesson.figures.isNotEmpty())
    }
  }

  @Test
  fun allExercisesHaveValidData() {
    val exercises = ExercisesData.getAllExercises()
    assertTrue(exercises.isNotEmpty())
    exercises.forEach { exercise ->
      assertTrue("Exercise  has blank title", exercise.title.isNotBlank())
      assertTrue("Exercise  has blank question", exercise.question.isNotBlank())
      assertTrue("Exercise  has blank correction", exercise.correction.isNotBlank())
      assertTrue("Exercise  has invalid level", exercise.level in 1..10)
    }
  }

  @Test
  fun allPedagogicalConceptsHaveValidData() {
    val concepts = PedagogicalContent.getAllConcepts()
    assertTrue(concepts.isNotEmpty())
    concepts.forEach { concept ->
      assertTrue("Concept  has blank title", concept.title.isNotBlank())
      assertTrue("Concept  has blank simpleExplanation", concept.simpleExplanation.isNotBlank())
      assertTrue("Concept  has blank analogy", concept.analogy.isNotBlank())
      assertTrue("Concept  has no steps", concept.stepByStep.isNotEmpty())
    }
  }

  @Test
  fun allThemeExamplesHaveValidData() {
    val examples = ThemeExamplesData.getAllExamples()
    assertTrue(examples.isNotEmpty())
    examples.forEach { example ->
      assertTrue("Example  has blank title", example.title.isNotBlank())
      assertTrue("Example  has blank question", example.question.isNotBlank())
      assertTrue("Example  has blank interpretation", example.interpretation.isNotBlank())
      assertEquals("Example  must have 4 mothers", 4, example.mothers.size)
      assertEquals("Example  must have 4 daughters", 4, example.daughters.size)
      assertEquals("Example  must have 4 nieces", 4, example.nieces.size)
      assertEquals("Example  must have 2 witnesses", 2, example.witnesses.size)
    }
  }

  @Test
  fun comprehensiveEnrichmentLessonsHaveValidData() {
    assertTrue(comprehensiveEnrichmentLessons.isNotEmpty())
    comprehensiveEnrichmentLessons.forEach { lesson ->
      assertTrue("Lesson has blank heading", lesson.heading.isNotBlank())
      assertTrue("Lesson has blank body", lesson.body.isNotBlank())
      assertTrue("Lesson has blank keyFormula", lesson.keyFormulaOrTable.isNotBlank())
    }
  }

  @Test
  fun fondementsCourseLessonsHaveValidData() {
    assertTrue(fondementsCourseLessons.isNotEmpty())
    fondementsCourseLessons.forEach { lesson ->
      assertTrue("Lesson has blank heading", lesson.heading.isNotBlank())
      assertTrue("Lesson has blank body", lesson.body.isNotBlank())
    }
  }

  @Test
  fun totalModulesCountIsReasonable() {
    val count = GeomancyCoursesCatalog.totalModulesCount
    assertTrue("Total modules should be >= 20, got " + count, count >= 20)
  }

  @Test
  fun allBooksHaveUniqueIds() {
    val ids = GeomancyCoursesCatalog.allBooks.map { it.id }
    assertEquals(ids.size, ids.toSet().size)
  }

  @Test
  fun allModulesHaveUniqueIds() {
    val ids = GeomancyCoursesCatalog.allBooks.flatMap { it.modules.map { m -> m.id } }
    assertEquals(ids.size, ids.toSet().size)
  }
}
