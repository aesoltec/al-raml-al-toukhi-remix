package com.example.geomancy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GeomancyAdditionalEngineTest {

  @Test
  fun castSpontaneousProducesValidReading() {
    val reading = AlToukhiGeomancyEngine.castSpontaneous(QuestionDomain.GENERAL)
    assertEquals(16, reading.houses.size)
    assertTrue(reading.analysis.abjadExtraction.totalAbjadSum > 0)
  }

  @Test
  fun damirComputationReturnsValidDomain() {
    val mothers = listOf(
      AlToukhiFigures.findById(1), // 1222 Adama
      AlToukhiFigures.findById(2), // 2121 Ousmana
      AlToukhiFigures.findById(11), // 2211 Nouhoun
      AlToukhiFigures.findById(16), // 1111 Taliki
    )
    val reading = AlToukhiGeomancyEngine.castFromMothers(mothers, QuestionDomain.HIDDEN_INTENT)
    val damir = reading.analysis.damirReport
    assertTrue(damir.unspokenFocus.isNotEmpty())
    assertTrue(damir.taskinResonance.isNotEmpty())
  }

  @Test
  fun allFiguresHaveValidAbjadValues() {
    AlToukhiFigures.all.forEach { fig ->
      assertTrue("Figure  has invalid abjad", fig.abjadValue > 0)
    }
  }

  @Test
  fun allFiguresHaveValidElements() {
    AlToukhiFigures.all.forEach { fig ->
      assertTrue("Figure  has invalid element", fig.dominantElement in Element.entries)
    }
  }

  @Test
  fun allFiguresHaveValidPlanets() {
    AlToukhiFigures.all.forEach { fig ->
      assertTrue("Figure  has invalid planet", fig.planet in Planet.entries)
    }
  }

  @Test
  fun allFiguresHaveValidMotion() {
    AlToukhiFigures.all.forEach { fig ->
      assertTrue("Figure  has invalid motion", fig.motion in FigureMotion.entries)
    }
  }

  @Test
  fun allFiguresHaveValidPolarity() {
    AlToukhiFigures.all.forEach { fig ->
      assertTrue("Figure  has invalid polarity", fig.polarity in FortunePolarity.entries)
    }
  }

  @Test
  fun figureCombineIsCommutative() {
    val f1 = AlToukhiFigures.findById(1)
    val f2 = AlToukhiFigures.findById(2)
    val combined1 = AlToukhiFigures.combine(f1, f2)
    val combined2 = AlToukhiFigures.combine(f2, f1)
    assertEquals(combined1.code, combined2.code)
  }

  @Test
  fun figureCombineWithSelfProducesPopulus() {
    val f = AlToukhiFigures.findById(5) // Lawsana
    val combined = AlToukhiFigures.combine(f, f)
    assertEquals("2222", combined.code) // Populus
  }

  @Test
  fun figureCombineWithViaProducesComplement() {
    val via = AlToukhiFigures.findById(16) // Taliki (1111)
    val f = AlToukhiFigures.findById(1) // Adama (1222)
    val combined = AlToukhiFigures.combine(f, via)
    // Via inverts all bits: 1222 XOR 1111 = 2111 (Malidjou)
    assertEquals("2111", combined.code)
  }

  @Test
  fun taskinRulerForAllHouses() {
    for (i in 1..16) {
      val ruler = AlToukhiHouses.taskinRulerFor(i)
      assertTrue("House  has invalid ruler", ruler.id in 1..16)
    }
  }

  @Test
  fun sharafAndHubutAreDifferent() {
    Planet.entries.forEach { planet ->
      val sharaf = AlToukhiHouses.sharafHouseFor(planet)
      val hubut = AlToukhiHouses.hubutHouseFor(planet)
      assertTrue("Planet  has same sharaf and hubut", sharaf != hubut)
    }
  }

  @Test
  fun allHousesHaveValidElement() {
    AlToukhiHouses.all.forEach { house ->
      assertTrue("House  has invalid element", house.element in Element.entries)
    }
  }

  @Test
  fun allHousesHaveValidTemporalRole() {
    for (i in 1..16) {
      val role = AlToukhiHouses.temporalRoleFor(i)
      assertTrue("House  has invalid temporal role", role in HouseTemporalRole.entries)
    }
  }

  @Test
  fun encyclopediaHasAll16Entries() {
    assertEquals(16, GeomanticFigureEncyclopedia.entries.size)
  }

  @Test
  fun encyclopediaFindByIdReturnsCorrectEntry() {
    val entry = GeomanticFigureEncyclopedia.findById(1)
    assertTrue(entry != null)
    assertTrue(entry!!.westAfricanName.isNotEmpty())
  }

  @Test
  fun encyclopediaFindByCodeReturnsCorrectEntry() {
    val entry = GeomanticFigureEncyclopedia.findByCode("1111")
    assertTrue(entry != null)
    assertEquals("Taliki", entry!!.westAfricanName)
  }

  @Test
  fun exercisesDataHasEntries() {
    assertTrue(ExercisesData.getAllExercises().isNotEmpty())
  }

  @Test
  fun pedagogicalContentHasEntries() {
    assertTrue(PedagogicalContent.getAllConcepts().isNotEmpty())
  }

  @Test
  fun themeExamplesHasEntries() {
    assertTrue(ThemeExamplesData.getAllExamples().isNotEmpty())
  }

  @Test
  fun interactiveLessonsHasEntries() {
    assertTrue(InteractiveLessonsData.getAllLessons().isNotEmpty())
  }

  @Test
  fun reliabilityMarkersHasLevels() {
    assertTrue(ReliabilityLevel.entries.size == 4)
  }

  @Test
  fun humanTranslationLayerProducesNonEmptyOutput() {
    val result = HumanTranslationLayer.humanize("Test verdict")
    assertTrue(result.isNotEmpty())
  }
}
