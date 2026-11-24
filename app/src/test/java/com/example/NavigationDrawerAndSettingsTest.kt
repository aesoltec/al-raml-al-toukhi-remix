package com.example

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import com.example.ui.GeomancyUiState
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.AlRamlTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class NavigationDrawerAndSettingsTest {

  @get:Rule
  val composeRule = createComposeRule()

  @Test
  fun `main app reduces bottom bar to 4 items with Plus button opening drawer submenus and settings`() {
    composeRule.setContent {
      AlRamlTheme {
        AlRamlApp()
      }
    }

    composeRule.onNodeWithTag("nav_cast_tab").assertIsDisplayed()
    composeRule.onNodeWithTag("nav_takht_tab").assertIsDisplayed()
    composeRule.onNodeWithTag("nav_saved_tab").assertIsDisplayed()
    composeRule.onNodeWithTag("nav_more_tab").assertIsDisplayed()
    composeRule.onNodeWithTag("nav_figures_tab").assertDoesNotExist()
    composeRule.onNodeWithTag("nav_guide_tab").assertDoesNotExist()

    // Le bouton "Plus" ouvre le tiroir contenant les sous-menus et les paramètres
    composeRule.onNodeWithTag("nav_more_tab").performClick()
    composeRule.waitForIdle()
    composeRule.onNodeWithTag("app_drawer_sheet").assertIsDisplayed()
    composeRule.onNodeWithTag("drawer_figures_item").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("drawer_courses_item").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("drawer_timeline_item").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("drawer_guide_item").performScrollTo().assertIsDisplayed()
    composeRule.onNodeWithTag("drawer_settings_item").performScrollTo().assertIsDisplayed()

    // Naviguer vers Paramètres via le tiroir
    composeRule.onNodeWithTag("drawer_settings_item").performScrollTo().performClick()
    composeRule.waitForIdle()
    composeRule.onNodeWithTag("settings_screen_list").assertIsDisplayed()

    // Rouvrir via le bouton menu de la TopAppBar et naviguer vers les Cours
    composeRule.onNodeWithTag("drawer_menu_button").performClick()
    composeRule.waitForIdle()
    composeRule.onNodeWithTag("drawer_courses_item").performScrollTo().performClick()
    composeRule.waitForIdle()
    composeRule.onNodeWithTag("tab_learning_courses").assertIsDisplayed()
  }

  @Test
  fun `settings screen renders timing, prediction options and calibration switches`() {
    var forbiddenToggled: Boolean? = null
    var exactScoreToggled = false
    var calibrationToggled: Boolean? = null

    composeRule.setContent {
      AlRamlTheme {
        SettingsScreen(
          uiState =
            GeomancyUiState(
              disableRamlAtForbiddenTimes = true,
              disableRamlAtDiscouragedTimes = false,
              autoSaveEnabled = true,
              optionExactScore = true,
              optionYellowCards = true,
              optionRedCards = false,
            ),
          isCalibrationEnabled = false,
          onToggleDisableRamlForbidden = { forbiddenToggled = it },
          onToggleDisableRamlDiscouraged = {},
          onRefreshRamlTiming = {},
          onToggleAutoSave = {},
          onToggleOptionExactScore = { exactScoreToggled = true },
          onToggleOptionYellowCards = {},
          onToggleOptionRedCards = {},
          onToggleCalibrationEnabled = { calibrationToggled = it },
          onOpenCourses = {},
          onOpenArchives = {},
        )
      }
    }

    composeRule.onNodeWithTag("settings_forbidden_switch").assertIsOn().performClick()
    assertEquals(false, forbiddenToggled)

    composeRule.onNodeWithTag("settings_screen_list")
      .performScrollToNode(hasTestTag("settings_prediction_card"))
    composeRule.onNodeWithTag("settings_exact_score_switch").assertIsOn().performClick()
    assertTrue(exactScoreToggled)
    composeRule.onNodeWithTag("settings_red_cards_switch").assertIsOff()

    composeRule.onNodeWithTag("settings_screen_list")
      .performScrollToNode(hasTestTag("settings_calibration_card"))
    composeRule.onNodeWithTag("settings_calibration_switch").assertIsOff().performClick()
    assertEquals(true, calibrationToggled)
  }
}
