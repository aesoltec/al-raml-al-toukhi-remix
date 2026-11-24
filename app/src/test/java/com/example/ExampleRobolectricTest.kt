package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import com.example.ui.CastingMode
import com.example.ui.GeomancyUiState
import com.example.ui.screens.CastScreen
import com.example.ui.theme.AlRamlTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @get:Rule
  val composeRule = createComposeRule()

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Al-Raml Al-Toukhi", appName)
  }

  @Test
  fun `sand tapping hides pair impair indicators and blocks reveal until all 16 lines filled`() {
    val partialCounts = MutableList(16) { 0 }.apply {
      this[0] = 3 // impair
      this[1] = 4 // pair
    }
    val partialState =
      GeomancyUiState(
        castingMode = CastingMode.STRIKE_SAND,
        sandLineCounts = partialCounts,
        activeSandLineIndex = 0,
      )
    assertFalse(partialState.isSandCastingComplete)
    assertNull(partialState.revealedMotherFigures())

    composeRule.setContent {
      AlRamlTheme {
        CastScreen(
          uiState = partialState,
          onSelectDomain = {},
          onQuestionChange = {},
          onSelectPlanetHour = {},
          onSelectCastingMode = {},
          onStrikeSand = { _, _ -> },
          onAdvanceLine = {},
          onSelectSandLine = {},
          onAutoCompleteSand = {},
          onResetSand = {},
          onToggleMotherRank = { _, _ -> },
          onSetMotherFigure = { _, _ -> },
          onErectTakht = {},
          onSpontaneousCast = {},
        )
      }
    }

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(7)
    composeRule.onNodeWithTag("sand_tap_status_text")
      .performScrollTo()
      .assertTextContains("Parité pair/impair masquée", substring = true)
    composeRule.onNodeWithTag("mother_column_footer_0")
      .performScrollTo()
      .assertTextContains("2/4")
    composeRule.onNodeWithTag("revealed_mothers_banner")
      .assertDoesNotExist()
    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(8)
    composeRule.onNodeWithTag("erect_takht_button")
      .performScrollTo()
      .assertIsNotEnabled()
  }

  @Test
  fun `sand casting reveals all four mother figures once all 16 lines are filled`() {
    val completeCounts = listOf(1, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1)
    val completeState =
      GeomancyUiState(
        castingMode = CastingMode.STRIKE_SAND,
        sandLineCounts = completeCounts,
        activeSandLineIndex = 15,
      )
    assertTrue(completeState.isSandCastingComplete)
    val revealed = completeState.revealedMotherFigures()
    assertNotNull(revealed)
    assertEquals(4, revealed!!.size)
    assertEquals("Adama", revealed[0].westAfricanName)

    composeRule.setContent {
      AlRamlTheme {
        CastScreen(
          uiState = completeState,
          onSelectDomain = {},
          onQuestionChange = {},
          onSelectPlanetHour = {},
          onSelectCastingMode = {},
          onStrikeSand = { _, _ -> },
          onAdvanceLine = {},
          onSelectSandLine = {},
          onAutoCompleteSand = {},
          onResetSand = {},
          onToggleMotherRank = { _, _ -> },
          onSetMotherFigure = { _, _ -> },
          onErectTakht = {},
          onSpontaneousCast = {},
        )
      }
    }

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(7)
    composeRule.onNodeWithTag("revealed_mothers_banner")
      .performScrollTo()
      .assertIsDisplayed()
    composeRule.onNodeWithTag("mother_column_footer_0")
      .performScrollTo()
      .assertTextContains("Adama")
    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(8)
    composeRule.onNodeWithTag("erect_takht_button")
      .performScrollTo()
      .assertIsEnabled()
  }

  @Test
  fun `figure detail sheet shows Sharaf Hubut dignities and navigates Mawazin relations`() {
    val startFigure = com.example.geomancy.AlToukhiFigures.findById(14) // Djanfa Almami (1121)
    val expectedNazar = com.example.geomancy.AlToukhiFigures.relationsFor(startFigure).nazar
    var clickedFigure: com.example.geomancy.GeomanticFigure? = null

    composeRule.setContent {
      AlRamlTheme {
        com.example.ui.screens.FigureDetailBottomSheet(
          figure = startFigure,
          onDismiss = {},
          onSelectFigure = { clickedFigure = it },
        )
      }
    }

    composeRule.onNodeWithTag("figure_detail_list").performScrollToIndex(3)
    composeRule.onNodeWithTag("figure_relations_card")
      .performScrollTo()
      .assertIsDisplayed()
    composeRule.onNodeWithTag("figure_relation_nazar")
      .performScrollTo()
      .assertIsDisplayed()
    composeRule.onNodeWithTag("figure_relation_nazar")
      .assertTextContains(expectedNazar.westAfricanName, substring = true)
      .performClick()
    assertEquals(expectedNazar.id, clickedFigure?.id)
  }

  @Test
  fun `takht shield layout renders exact right to left disposition M8_M1 M12_M9 M14_M13 M15_M16`() {
    val reading =
      com.example.geomancy.AlToukhiGeomancyEngine.castFromDotCounts(
        sixteenCounts = listOf(1, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1),
      )

    composeRule.setContent {
      AlRamlTheme {
        com.example.ui.screens.TakhtScreen(
          reading = reading,
          inspectedHouseNumber = 15,
          isSaved = false,
          savedTitle = "",
          savedContextNotes = "",
          onSaveTheme = { _, _, _, _, _ -> },
          onInspectHouse = {},
          onGoToCast = {},
          onOpenFigureDetail = {},
        )
      }
    }

    composeRule.onNodeWithTag("takht_screen_list").performScrollToIndex(1)
    composeRule.onNodeWithTag("takht_shield_layout").performScrollTo().assertIsDisplayed()

    fun centerX(house: Int): Float {
      val b = composeRule.onNodeWithTag("takht_house_$house").fetchSemanticsNode().boundsInRoot
      return (b.left + b.right) / 2f
    }

    fun centerY(house: Int): Float {
      val b = composeRule.onNodeWithTag("takht_house_$house").fetchSemanticsNode().boundsInRoot
      return (b.top + b.bottom) / 2f
    }

    // Rang 1 : M8 -> M1 de gauche à droite
    for (num in 8 downTo 2) {
      assertTrue("M$num doit être à gauche de M${num - 1}", centerX(num) < centerX(num - 1))
    }
    // Rang 2 : M12 -> M9 de gauche à droite
    for (num in 12 downTo 10) {
      assertTrue("M$num doit être à gauche de M${num - 1}", centerX(num) < centerX(num - 1))
    }
    // Rang 3 : M14 à gauche de M13
    assertTrue("M14 doit être à gauche de M13", centerX(14) < centerX(13))
    // Rang 4 : M15 au centre, M16 à sa droite
    assertTrue("M15 doit être à gauche de M16", centerX(15) < centerX(16))

    // Ordre vertical des 4 rangées
    assertTrue(centerY(8) < centerY(12))
    assertTrue(centerY(12) < centerY(14))
    assertTrue(centerY(14) < centerY(15))
    assertTrue(kotlin.math.abs(centerY(15) - centerY(16)) < 5f)
  }

  @Test
  fun `contest takht displays exact score BTTS yellow cards and red card forecast`() {
    val reading =
      com.example.geomancy.AlToukhiGeomancyEngine.castFromDotCounts(
        sixteenCounts = listOf(1, 2, 2, 2, 2, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 1),
        domain = com.example.geomancy.QuestionDomain.CONTEST,
        questionText = "Équipe A vs Équipe B",
      )

    composeRule.setContent {
      AlRamlTheme {
        com.example.ui.screens.TakhtScreen(
          reading = reading,
          inspectedHouseNumber = 15,
          isSaved = false,
          savedTitle = "",
          savedContextNotes = "",
          onSaveTheme = { _, _, _, _, _ -> },
          onInspectHouse = {},
          onGoToCast = {},
          onOpenFigureDetail = {},
        )
      }
    }

    composeRule.onNodeWithTag("contest_match_precision_card").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("precision_exact_score").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("precision_btts").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("precision_yellow_cards").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("precision_red_card").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("takht_option_exact_score").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("takht_option_yellow_cards").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("takht_option_red_cards").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("al_qawl_duel_crosscheck").performScrollTo().assertIsDisplayed()

    composeRule.onNodeWithTag("takht_screen_list").performScrollToIndex(7)
    composeRule.onNodeWithTag("al_qawl_insights_card").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("al_qawl_96_pill").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("al_qawl_triangles_section").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("triangle_pill_1").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("triangle_pill_5").performScrollTo().assertIsDisplayed()
  }

  @Test
  fun `contest cast screen exposes score yellow red card options and category grouping exports`() {
    val contestState =
      GeomancyUiState(
        selectedDomain = com.example.geomancy.QuestionDomain.CONTEST,
        optionExactScore = true,
        optionYellowCards = false,
        optionRedCards = true,
      )
    var exactClicked = false
    var yellowClicked = false
    var redClicked = false

    composeRule.setContent {
      AlRamlTheme {
        CastScreen(
          uiState = contestState,
          onSelectDomain = {},
          onQuestionChange = {},
          onToggleOptionExactScore = { exactClicked = true },
          onToggleOptionYellowCards = { yellowClicked = true },
          onToggleOptionRedCards = { redClicked = true },
          onSelectPlanetHour = {},
          onSelectCastingMode = {},
          onStrikeSand = { _, _ -> },
          onAdvanceLine = {},
          onSelectSandLine = {},
          onAutoCompleteSand = {},
          onResetSand = {},
          onToggleMotherRank = { _, _ -> },
          onSetMotherFigure = { _, _ -> },
          onErectTakht = {},
          onSpontaneousCast = {},
        )
      }
    }

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(4)
    composeRule.onNodeWithTag("cast_category_options_card").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("cast_option_exact_score").performScrollTo().performClick()
    composeRule.onNodeWithTag("cast_option_yellow_cards").performScrollTo().performClick()
    composeRule.onNodeWithTag("cast_option_red_cards").performScrollTo().performClick()
    assertTrue(exactClicked && yellowClicked && redClicked)
  }

  @Test
  fun `saved themes screen groups by category and opens per category export dialog`() {
    val entities =
      listOf(
        com.example.data.SavedThemeEntity(
          id = 1L,
          title = "Portugalete vs Real Unión",
          domainName = com.example.geomancy.QuestionDomain.CONTEST.name,
          questionText = "Portugalete vs Real Unión",
          planetHourName = "MARS",
          motherDotCountsCsv = "4,5,6,8,6,4,3,5,7,5,8,3,4,3,6,4",
          judgeFigureId = 1,
          outcomeFigureId = 2,
          overallPolarityName = "FORTUNATE",
          leagueCategory = "Copa del Rey",
          actualScoreText = "1-2",
          actualYellowCardsText = "4",
          actualRedCardsText = "0",
        ),
        com.example.data.SavedThemeEntity(
          id = 2L,
          title = "Projet commerce",
          domainName = com.example.geomancy.QuestionDomain.WEALTH.name,
          questionText = "Projet commerce",
          planetHourName = "JUPITER",
          motherDotCountsCsv = "3,4,3,4,3,4,3,4,3,4,3,4,3,4,3,4",
          judgeFigureId = 3,
          outcomeFigureId = 4,
          overallPolarityName = "MIXED",
          leagueCategory = "Commerce",
        ),
      )

    var capturedExportCategory: String? = "UNSET"

    composeRule.setContent {
      AlRamlTheme {
        com.example.ui.screens.SavedThemesScreen(
          savedThemes = entities,
          calibrationReport = com.example.geomancy.PredictiveCalibrationEngine.evaluateAndCalibrate(emptyList()),
          isCalibrationEnabled = false,
          onOpenTheme = {},
          onDeleteTheme = {},
          onToggleCalibrationEnabled = {},
          onApplyCalibration = {},
          onResetCalibration = {},
          onImportNov17Matches = {},
          onBuildCategoryExportText = { _, cat, _ ->
            capturedExportCategory = cat
            "EXPORT:${cat ?: "ALL"}"
          },
          onGoToCast = {},
        )
      }
    }

    composeRule
      .onNodeWithTag("saved_themes_lazy_column")
      .performScrollToNode(hasTestTag("category_group_header_0"))
    composeRule.onNodeWithTag("category_group_header_0").assertIsDisplayed()
    composeRule.onNodeWithTag("export_category_button_0").performClick()
    composeRule.onNodeWithTag("export_preview_text").assertIsDisplayed()
    composeRule.onNodeWithTag("export_preview_text").assertTextContains("EXPORT:", substring = true)
    assertNotNull(capturedExportCategory)
  }

  @Test
  fun `figures encyclopedia screen displays Room entities meanings associations and favorite toggle`() {
    val seedEntities = com.example.data.FigureEncyclopediaRepository.buildSeedEntities()
    var favClickedId = -1
    var favNextValue = false
    var selectedFigId = -1

    composeRule.setContent {
      AlRamlTheme {
        com.example.ui.screens.FiguresCatalogScreen(
          uiState = GeomancyUiState(),
          onSearchChange = {},
          onToggleElement = {},
          onTogglePolarity = {},
          onSelectFigure = { selectedFigId = it.id },
          encyclopediaFigures = seedEntities,
          onToggleFavoritesOnly = {},
          onToggleFigureFavorite = { id, next ->
            favClickedId = id
            favNextValue = next
          },
        )
      }
    }

    composeRule.onNodeWithTag("encyclopedia_room_status_banner").assertIsDisplayed()
    composeRule
      .onNodeWithTag("figures_encyclopedia_list")
      .performScrollToNode(hasTestTag("figure_card_14"))
    composeRule.onNodeWithTag("figure_card_14").assertIsDisplayed()
    composeRule.onNodeWithTag("figure_favorite_button_14").performClick()
    assertEquals(14, favClickedId)
    assertTrue(favNextValue)

    composeRule.onNodeWithTag("figure_card_14").performClick()
    assertEquals(14, selectedFigId)
  }

  @Test
  fun `cast screen shows raml timing protection toggles and blocks sand tapping at forbidden time`() {
    val forbiddenAssessment =
      com.example.geomancy.RamlTimeAssessment(
        status = com.example.geomancy.RamlTimeStatus.FORBIDDEN,
        reasons = listOf("Zawāl solaire (12h05 : 11h55–12h15, zénith méridien)"),
        lunarDay = 10,
        formattedClockTime = "12h05",
        recommendedAction = "Tirage interdit durant ce créneau.",
      )
    val blockedState =
      GeomancyUiState(
        castingMode = CastingMode.STRIKE_SAND,
        disableRamlAtForbiddenTimes = true,
        disableRamlAtDiscouragedTimes = false,
        ramlTimeAssessment = forbiddenAssessment,
      )
    assertTrue(blockedState.isSandTappingBlocked)

    var forbiddenToggleResult: Boolean? = null
    var discouragedToggleResult: Boolean? = null

    composeRule.setContent {
      AlRamlTheme {
        CastScreen(
          uiState = blockedState,
          onSelectDomain = {},
          onQuestionChange = {},
          onToggleDisableRamlForbidden = { forbiddenToggleResult = it },
          onToggleDisableRamlDiscouraged = { discouragedToggleResult = it },
          onSelectPlanetHour = {},
          onSelectCastingMode = {},
          onStrikeSand = { _, _ -> },
          onAdvanceLine = {},
          onSelectSandLine = {},
          onAutoCompleteSand = {},
          onResetSand = {},
          onToggleMotherRank = { _, _ -> },
          onSetMotherFigure = { _, _ -> },
          onErectTakht = {},
          onSpontaneousCast = {},
        )
      }
    }

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(5)
    composeRule.onNodeWithTag("disable_raml_forbidden_switch").performScrollTo().assertIsDisplayed().assertIsOn()
    composeRule.onNodeWithTag("disable_raml_discouraged_switch").performScrollTo().assertIsDisplayed().assertIsOff()

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(7)
    composeRule.onNodeWithTag("sand_raml_blocked_banner").performScrollTo().assertIsDisplayed()

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(8)
    composeRule.onNodeWithTag("spontaneous_cast_button").performScrollTo().assertIsNotEnabled()
    composeRule.onNodeWithTag("erect_takht_button").performScrollTo().assertIsNotEnabled()

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(7)
    composeRule.onNodeWithTag("override_raml_block_button").performScrollTo().performClick()
    assertEquals(false, forbiddenToggleResult)

    composeRule.onNodeWithTag("cast_screen_list").performScrollToIndex(5)
    composeRule.onNodeWithTag("disable_raml_discouraged_switch").performScrollTo().performClick()
    assertEquals(true, discouragedToggleResult)
  }
}
