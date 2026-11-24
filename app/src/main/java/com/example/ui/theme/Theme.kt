package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.geomancy.Element
import com.example.geomancy.FortunePolarity

@Immutable
data class GeomancyExtendedColors(
  val fire: Color,
  val air: Color,
  val water: Color,
  val earth: Color,
  val fortunate: Color,
  val unfortunate: Color,
  val mixed: Color,
  val cardBorder: Color,
  val goldAccent: Color,
  val glassSurface: Color,
  val glassElevated: Color,
  val glassSpecularTop: Color,
  val glassSpecularBottom: Color,
  val glassSheen: Color,
  val duneTop: Color,
  val duneMid: Color,
  val duneBottom: Color,
  val duneGlow: Color,
  val isDarkSand: Boolean,
) {
  fun forElement(element: Element): Color =
    when (element) {
      Element.FIRE -> fire
      Element.AIR -> air
      Element.WATER -> water
      Element.EARTH -> earth
    }

  fun forPolarity(polarity: FortunePolarity): Color =
    when (polarity) {
      FortunePolarity.FORTUNATE -> fortunate
      FortunePolarity.UNFORTUNATE -> unfortunate
      FortunePolarity.MIXED -> mixed
    }
}

val LocalGeomancyColors =
  staticCompositionLocalOf {
    GeomancyExtendedColors(
      fire = FireCinnabar,
      air = AirCeladon,
      water = WaterLapis,
      earth = EarthUmber,
      fortunate = FortuneEmeraldLight,
      unfortunate = CautionCrimsonLight,
      mixed = MixedAmberLight,
      cardBorder = SandBronzeGold.copy(alpha = 0.28f),
      goldAccent = SandBronzeGold,
      glassSurface = SandGlassSurface,
      glassElevated = SandGlassElevated,
      glassSpecularTop = SandGlassSpecular,
      glassSpecularBottom = SandBronzeGold.copy(alpha = 0.24f),
      glassSheen = Color.White.copy(alpha = 0.34f),
      duneTop = SandDuneCream,
      duneMid = SandQuartzBase,
      duneBottom = SandDuneOchre,
      duneGlow = Color(0xFFFFF4DC),
      isDarkSand = false,
    )
  }

private val LightSandColorScheme =
  lightColorScheme(
    primary = SandBronzeGold,
    onPrimary = Color(0xFFFFF9EF),
    primaryContainer = SandGlassElevated,
    onPrimaryContainer = SandEspressoInk,
    secondary = SandOasisTeal,
    onSecondary = Color(0xFFFFF9EF),
    secondaryContainer = SandDuneCream,
    onSecondaryContainer = SandEspressoInk,
    tertiary = AirCeladon,
    onTertiary = SandEspressoInk,
    background = SandQuartzBase,
    onBackground = SandEspressoInk,
    surface = SandGlassSurface,
    onSurface = SandEspressoInk,
    surfaceVariant = SandGlassElevated,
    onSurfaceVariant = SandMutedInk,
    outline = SandBronzeGold.copy(alpha = 0.32f),
    error = CautionCrimsonLight,
    onError = Color(0xFFFFF9EF),
  )

private val DarkSandColorScheme =
  darkColorScheme(
    primary = DuskSandGold,
    onPrimary = DuskSandDeep,
    primaryContainer = DuskSandGlassElevated,
    onPrimaryContainer = DuskSandSaffron,
    secondary = AirCeladonBright,
    onSecondary = DuskSandDeep,
    secondaryContainer = DuskSandMid,
    onSecondaryContainer = DuskSandCream,
    tertiary = WaterLapisBright,
    onTertiary = DuskSandDeep,
    background = DuskSandDeep,
    onBackground = DuskSandCream,
    surface = DuskSandGlassSurface,
    onSurface = DuskSandCream,
    surfaceVariant = DuskSandGlassElevated,
    onSurfaceVariant = DuskSandMuted,
    outline = DuskSandBorder,
    error = CautionCrimsonDark,
    onError = DuskSandDeep,
  )

@Composable
fun AlRamlTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkSandColorScheme else LightSandColorScheme
  val extendedColors =
    if (darkTheme) {
      GeomancyExtendedColors(
        fire = FireCinnabarBright,
        air = AirCeladonBright,
        water = WaterLapisBright,
        earth = EarthUmberBright,
        fortunate = FortuneEmeraldDark,
        unfortunate = CautionCrimsonDark,
        mixed = MixedAmberDark,
        cardBorder = DuskSandGold.copy(alpha = 0.28f),
        goldAccent = DuskSandGold,
        glassSurface = DuskSandGlassSurface,
        glassElevated = DuskSandGlassElevated,
        glassSpecularTop = DuskSandSpecular,
        glassSpecularBottom = DuskSandGold.copy(alpha = 0.14f),
        glassSheen = DuskSandSaffron.copy(alpha = 0.10f),
        duneTop = DuskSandDeep,
        duneMid = DuskSandMid,
        duneBottom = DuskSandDune,
        duneGlow = Color(0xFF6B4824),
        isDarkSand = true,
      )
    } else {
      GeomancyExtendedColors(
        fire = FireCinnabar,
        air = AirCeladon,
        water = WaterLapis,
        earth = EarthUmber,
        fortunate = FortuneEmeraldLight,
        unfortunate = CautionCrimsonLight,
        mixed = MixedAmberLight,
        cardBorder = SandBronzeGold.copy(alpha = 0.24f),
        goldAccent = SandBronzeGold,
        glassSurface = SandGlassSurface,
        glassElevated = SandGlassElevated,
        glassSpecularTop = SandGlassSpecular,
        glassSpecularBottom = SandBronzeGold.copy(alpha = 0.22f),
        glassSheen = Color.White.copy(alpha = 0.36f),
        duneTop = SandDuneCream,
        duneMid = SandQuartzBase,
        duneBottom = SandDuneOchre,
        duneGlow = Color(0xFFFFF5DF),
        isDarkSand = false,
      )
    }

  CompositionLocalProvider(LocalGeomancyColors provides extendedColors) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = AlRamlTypography,
      content = content,
    )
  }
}

object AlRamlColors {
  val extended: GeomancyExtendedColors
    @Composable get() = LocalGeomancyColors.current
}
