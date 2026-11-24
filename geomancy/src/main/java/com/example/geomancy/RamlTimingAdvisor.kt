package com.example.geomancy

import java.util.Calendar
import java.util.Locale

enum class RamlTimeStatus(
  val label: String,
  val arabicLabel: String,
) {
  FAVORABLE("Temps & heure propices", "وقت صالح ومأذون"),
  DISCOURAGED("Temps / heure déconseillé(e)", "وقت مكروه / مرجوح"),
  FORBIDDEN("Temps / heure interdit(e)", "وقت منهي / محظور"),
}

data class RamlTimeAssessment(
  val status: RamlTimeStatus,
  val reasons: List<String>,
  val lunarDay: Int,
  val formattedClockTime: String,
  val recommendedAction: String,
) {
  val isForbidden: Boolean
    get() = status == RamlTimeStatus.FORBIDDEN

  val isDiscouraged: Boolean
    get() = status == RamlTimeStatus.DISCOURAGED

  companion object {
    fun favorableDefault(): RamlTimeAssessment =
      RamlTimeAssessment(
        status = RamlTimeStatus.FAVORABLE,
        reasons = emptyList(),
        lunarDay = 10,
        formattedClockTime = "",
        recommendedAction =
          "Conditions favorables pour tracer le sable avec une intention claire (Niyya).",
      )
  }
}

/**
 * Évaluateur traditionnel des temps et heures propices, déconseillés ou interdits en ʿIlm al-Raml
 * (Al-Fulk al-Mashḥūn p. 31, Bulūgh al-Amal d'al-Ṭūkhī et règle des Naḥsayn).
 */
object RamlTimingAdvisor {

  /** Jours lunaires de retenue / vigilance (Ayyām al-Naḥs, Al-Fulk al-Mashḥūn p. 31). */
  val nahsLunarDays: Set<Int> = setOf(3, 5, 13, 16, 21, 25, 26)

  /** Derniers jours sombres du mois lunaire (Al-Maḥāq). */
  val mahaqLunarDays: Set<Int> = setOf(28, 29, 30)

  fun evaluate(
    activePlanetHour: Planet,
    lunarDay: Int,
    hourOfDay: Int,
    minute: Int,
    dayOfWeek: Int,
  ): RamlTimeAssessment {
    val totalMinutes = hourOfDay * 60 + minute
    val clockLabel = String.format(Locale.FRANCE, "%02dh%02d", hourOfDay, minute)

    val forbiddenReasons = mutableListOf<String>()
    val discouragedReasons = mutableListOf<String>()

    // --- 1. Temps & Heures strictement interdits ---
    if (totalMinutes in (11 * 60 + 55)..(12 * 60 + 15)) {
      forbiddenReasons += "Zawāl solaire ($clockLabel : 11h55–12h15, zénith méridien)"
    }
    if (totalMinutes in (5 * 60 + 45)..(6 * 60 + 15)) {
      forbiddenReasons += "Ṭulūʿ al-Shams ($clockLabel : lever solaire strict 05h45–06h15)"
    }
    if (totalMinutes in (17 * 60 + 45)..(18 * 60 + 15)) {
      forbiddenReasons += "Iṣfirār / Ghurūb ($clockLabel : coucher solaire strict 17h45–18h15)"
    }
    if (lunarDay in mahaqLunarDays) {
      forbiddenReasons += "Al-Maḥāq (jour lunaire $lunarDay : lune cachée de fin de mois)"
    }
    if (dayOfWeek == Calendar.SATURDAY && activePlanetHour == Planet.SATURN) {
      forbiddenReasons += "Sāʿat al-Naḥs al-Akbar : heure de Saturne un Samedi"
    }
    if (dayOfWeek == Calendar.TUESDAY && activePlanetHour == Planet.MARS) {
      forbiddenReasons += "Sāʿat al-Naḥs al-Aṣghar : heure de Mars un Mardi"
    }
    if (activePlanetHour == Planet.SOUTH_NODE) {
      forbiddenReasons += "Heure du Nœud Descendant (Al-Dhanab)"
    }

    // --- 2. Temps & Heures déconseillés ---
    if (activePlanetHour == Planet.SATURN && dayOfWeek != Calendar.SATURDAY) {
      discouragedReasons += "Heure planétaire de Saturne (Zuḥal · lenteur et blocage)"
    }
    if (activePlanetHour == Planet.MARS && dayOfWeek != Calendar.TUESDAY) {
      discouragedReasons += "Heure planétaire de Mars (Al-Mirrīkh · tension et conflit)"
    }
    if (lunarDay in nahsLunarDays) {
      discouragedReasons += "Ayyām al-Naḥs : jour lunaire $lunarDay (Al-Fulk p. 31)"
    }
    if (dayOfWeek == Calendar.WEDNESDAY && totalMinutes in (15 * 60)..(17 * 60 + 44)) {
      discouragedReasons += "Ākhir al-Arbiʿāʾ : fin d’après-midi du Mercredi ($clockLabel)"
    }
    if (totalMinutes in 0..(3 * 60 + 30)) {
      discouragedReasons += "Fin de nuit tardive ($clockLabel : 00h00–03h30, fatigue de la Niyya)"
    }

    val status =
      when {
        forbiddenReasons.isNotEmpty() -> RamlTimeStatus.FORBIDDEN
        discouragedReasons.isNotEmpty() -> RamlTimeStatus.DISCOURAGED
        else -> RamlTimeStatus.FAVORABLE
      }

    val allReasons = forbiddenReasons + discouragedReasons
    val recommendation =
      when (status) {
        RamlTimeStatus.FORBIDDEN ->
          "Tirage sur le sable interdit durant ce créneau : patiente après le passage ou choisis une heure planétaire faste (Jupiter, Soleil, Vénus, Lune, Mercure)."
        RamlTimeStatus.DISCOURAGED ->
          "Créneau déconseillé : privilégie une heure faste (Jupiter/Vénus) ou renforce l’intention avant de tracer."
        RamlTimeStatus.FAVORABLE ->
          "Jour lunaire $lunarDay · $clockLabel · Heure de ${activePlanetHour.englishName} propice au tirage."
      }

    return RamlTimeAssessment(
      status = status,
      reasons = allReasons,
      lunarDay = lunarDay,
      formattedClockTime = clockLabel,
      recommendedAction = recommendation,
    )
  }

  fun evaluateFromCalendar(
    activePlanetHour: Planet,
    calendar: Calendar,
    lunarDay: Int,
  ): RamlTimeAssessment =
    evaluate(
      activePlanetHour = activePlanetHour,
      lunarDay = lunarDay,
      hourOfDay = calendar.get(Calendar.HOUR_OF_DAY),
      minute = calendar.get(Calendar.MINUTE),
      dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK),
    )
}
