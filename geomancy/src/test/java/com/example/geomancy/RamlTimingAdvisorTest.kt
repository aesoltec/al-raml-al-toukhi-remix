package com.example.geomancy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class RamlTimingAdvisorTest {

  @Test
  fun `zawal sunrise sunset mahaq and saturn saturday are forbidden`() {
    val zawal = RamlTimingAdvisor.evaluate(Planet.JUPITER, lunarDay = 10, hourOfDay = 12, minute = 2, dayOfWeek = Calendar.THURSDAY)
    assertEquals(RamlTimeStatus.FORBIDDEN, zawal.status)
    assertTrue(zawal.reasons.any { it.contains("Zawāl") })

    val sunrise = RamlTimingAdvisor.evaluate(Planet.SUN, lunarDay = 10, hourOfDay = 6, minute = 0, dayOfWeek = Calendar.SUNDAY)
    assertEquals(RamlTimeStatus.FORBIDDEN, sunrise.status)

    val sunset = RamlTimingAdvisor.evaluate(Planet.VENUS, lunarDay = 10, hourOfDay = 18, minute = 0, dayOfWeek = Calendar.FRIDAY)
    assertEquals(RamlTimeStatus.FORBIDDEN, sunset.status)

    val mahaq = RamlTimingAdvisor.evaluate(Planet.JUPITER, lunarDay = 29, hourOfDay = 10, minute = 0, dayOfWeek = Calendar.THURSDAY)
    assertEquals(RamlTimeStatus.FORBIDDEN, mahaq.status)

    val saturnSaturday = RamlTimingAdvisor.evaluate(Planet.SATURN, lunarDay = 10, hourOfDay = 10, minute = 0, dayOfWeek = Calendar.SATURDAY)
    assertEquals(RamlTimeStatus.FORBIDDEN, saturnSaturday.status)

    val marsTuesday = RamlTimingAdvisor.evaluate(Planet.MARS, lunarDay = 10, hourOfDay = 10, minute = 0, dayOfWeek = Calendar.TUESDAY)
    assertEquals(RamlTimeStatus.FORBIDDEN, marsTuesday.status)
  }

  @Test
  fun `nahs lunar days saturn on other days and wednesday late afternoon are discouraged`() {
    val nahsDay = RamlTimingAdvisor.evaluate(Planet.JUPITER, lunarDay = 13, hourOfDay = 10, minute = 0, dayOfWeek = Calendar.THURSDAY)
    assertEquals(RamlTimeStatus.DISCOURAGED, nahsDay.status)

    val saturnOtherDay = RamlTimingAdvisor.evaluate(Planet.SATURN, lunarDay = 10, hourOfDay = 10, minute = 0, dayOfWeek = Calendar.MONDAY)
    assertEquals(RamlTimeStatus.DISCOURAGED, saturnOtherDay.status)

    val marsOtherDay = RamlTimingAdvisor.evaluate(Planet.MARS, lunarDay = 10, hourOfDay = 10, minute = 0, dayOfWeek = Calendar.FRIDAY)
    assertEquals(RamlTimeStatus.DISCOURAGED, marsOtherDay.status)

    val wednesdayLate = RamlTimingAdvisor.evaluate(Planet.MERCURY, lunarDay = 10, hourOfDay = 16, minute = 0, dayOfWeek = Calendar.WEDNESDAY)
    assertEquals(RamlTimeStatus.DISCOURAGED, wednesdayLate.status)
  }

  @Test
  fun `jupiter thursday morning on lunar day 10 is favorable`() {
    val favorable = RamlTimingAdvisor.evaluate(Planet.JUPITER, lunarDay = 10, hourOfDay = 9, minute = 30, dayOfWeek = Calendar.THURSDAY)
    assertEquals(RamlTimeStatus.FAVORABLE, favorable.status)
    assertTrue(favorable.reasons.isEmpty())
  }
}
