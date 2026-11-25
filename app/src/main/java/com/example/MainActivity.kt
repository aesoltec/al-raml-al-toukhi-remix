package com.example

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.AlRamlDatabase
import com.example.data.FigureEncyclopediaDatabase
import com.example.data.FigureEncyclopediaRepository
import com.example.data.SavedThemeRepository
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.PredictiveCalibrationEngine
import com.example.ui.GeomancyViewModel
import com.example.ui.GeomancyViewModelFactory
import com.example.ui.components.AppDrawerSheet
import com.example.ui.screens.CastScreen
import com.example.ui.screens.FigureDetailBottomSheet
import com.example.ui.screens.EncyclopediaScreen
import com.example.ui.screens.FiguresCatalogScreen
import com.example.ui.screens.GuideScreen
import com.example.ui.screens.InteractiveLessonsScreen
import com.example.ui.screens.ExercisesScreen
import com.example.ui.screens.SavedThemesScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.TakhtScreen
import com.example.ui.theme.AlRamlTheme
import com.example.ui.theme.SandDuneBackdrop
import kotlinx.coroutines.launch

const val ROUTE_CAST = "cast"
const val ROUTE_TAKHT = "takht"
const val ROUTE_SAVED = "saved"
const val ROUTE_FIGURES = "figures"
const val ROUTE_GUIDE = "guide"
const val ROUTE_LESSONS = "lessons"
const val ROUTE_EXERCISES = "exercises"
const val ROUTE_SETTINGS = "settings"

private data class NavTab(
  val route: String,
  val labelRes: Int,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val testTag: String,
)

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AlRamlTheme {
        AlRamlApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlRamlApp() {
  val context = LocalContext.current
  val repository =
    remember(context) {
      val db = AlRamlDatabase.getInstance(context)
      SavedThemeRepository(
        dao = db.savedThemeDao(),
        calibrationDao = db.calibrationDao(),
      )
    }
  val figureEncyclopediaRepository =
    remember(context) {
      val encDb = FigureEncyclopediaDatabase.getInstance(context)
      FigureEncyclopediaRepository(encDb.geomanticFigureDao())
    }
  val viewModel: GeomancyViewModel =
    viewModel(factory = GeomancyViewModelFactory(repository, figureEncyclopediaRepository))
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val savedThemes by viewModel.savedThemes.collectAsStateWithLifecycle()
  val encyclopediaFigures by viewModel.encyclopediaFigures.collectAsStateWithLifecycle()
  val calibrationReport by viewModel.calibrationReport.collectAsStateWithLifecycle()
  val archiveFrequencyReport by viewModel.archiveFrequencyReport.collectAsStateWithLifecycle()
  val archiveRecurrenceReport by viewModel.archiveRecurrenceReport.collectAsStateWithLifecycle()
  val isCalibrationEnabled by viewModel.isCalibrationEnabled.collectAsStateWithLifecycle()

  val prefs =
    remember(context) {
      context.getSharedPreferences("al_raml_archives_prefs", Context.MODE_PRIVATE)
    }
  LaunchedEffect(Unit) {
    val forbidDefault = prefs.getBoolean("disable_raml_forbidden_times", true)
    val discourageDefault = prefs.getBoolean("disable_raml_discouraged_times", false)
    val completedModules = prefs.getStringSet("completed_course_modules", emptySet()).orEmpty()
    viewModel.updateDisableRamlAtForbiddenTimes(forbidDefault)
    viewModel.updateDisableRamlAtDiscouragedTimes(discourageDefault)
    viewModel.setCompletedCourseModules(completedModules.toSet())
    viewModel.refreshRamlTiming()
    if (!prefs.getBoolean("seeded_nov18_draws_v1", false)) {
      viewModel.seedNov18ConsultationArchives()
      prefs.edit().putBoolean("seeded_nov18_draws_v1", true).apply()
    }
  }

  val navController = rememberNavController()
  val backStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = backStackEntry?.destination
  var sheetFigure by remember { mutableStateOf<GeomanticFigure?>(null) }
  val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
  val coroutineScope = rememberCoroutineScope()

  val primaryTabs =
    remember {
      listOf(
        NavTab(
          route = ROUTE_CAST,
          labelRes = R.string.nav_cast,
          selectedIcon = Icons.Rounded.AutoAwesome,
          unselectedIcon = Icons.Outlined.AutoAwesome,
          testTag = "nav_cast_tab",
        ),
        NavTab(
          route = ROUTE_TAKHT,
          labelRes = R.string.nav_takht,
          selectedIcon = Icons.Rounded.GridView,
          unselectedIcon = Icons.Outlined.GridView,
          testTag = "nav_takht_tab",
        ),
        NavTab(
          route = ROUTE_SAVED,
          labelRes = R.string.nav_saved,
          selectedIcon = Icons.Rounded.Bookmarks,
          unselectedIcon = Icons.Outlined.Bookmarks,
          testTag = "nav_saved_tab",
        ),
      )
    }

  val navigateToTab: (String) -> Unit = { route ->
    navController.navigate(route) {
      popUpTo(navController.graph.findStartDestination().id) { saveState = true }
      launchSingleTop = true
      restoreState = true
    }
  }

  val isSecondaryRouteActive =
    currentDestination?.hierarchy?.any {
      it.route == ROUTE_FIGURES || it.route == ROUTE_GUIDE || it.route == ROUTE_SETTINGS
    } == true

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      AppDrawerSheet(
        currentRoute = currentDestination?.route,
        uiState = uiState,
        onNavigateToCast = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_CAST)
        },
        onNavigateToTakht = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_TAKHT)
        },
        onNavigateToSaved = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_SAVED)
        },
        onNavigateToFigures = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_FIGURES)
        },
        onNavigateToLessons = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_LESSONS)
        },
        onNavigateToExercises = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_EXERCISES)
        },
        onNavigateToGuideTab = { tabIndex ->
          viewModel.selectGuideTab(tabIndex)
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_GUIDE)
        },
        onNavigateToSettings = {
          coroutineScope.launch { drawerState.close() }
          navigateToTab(ROUTE_SETTINGS)
        },
        onCloseDrawer = {
          coroutineScope.launch { drawerState.close() }
        },
      )
    },
  ) {
    SandDuneBackdrop {
      BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 640.dp

        Scaffold(
          containerColor = Color.Transparent,
          contentWindowInsets = WindowInsets.safeDrawing,
          topBar = {
            CenterAlignedTopAppBar(
              navigationIcon = {
                IconButton(
                  onClick = { coroutineScope.launch { drawerState.open() } },
                  modifier =
                    Modifier.minimumInteractiveComponentSize().testTag("drawer_menu_button"),
                ) {
                  Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = stringResource(R.string.menu_open_desc),
                    tint = MaterialTheme.colorScheme.primary,
                  )
                }
              },
              title = {
                Text(
                  text = stringResource(R.string.app_name),
                  style = MaterialTheme.typography.titleLarge,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                )
              },
              colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                  containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.62f),
                  titleContentColor = MaterialTheme.colorScheme.primary,
                ),
            )
          },
          bottomBar = {
            if (!useNavigationRail) {
              NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
              ) {
                primaryTabs.forEach { tab ->
                  val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                  val label = stringResource(tab.labelRes)
                  NavigationBarItem(
                    selected = selected,
                    onClick = { navigateToTab(tab.route) },
                    icon = {
                      Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = label,
                      )
                    },
                    label = { Text(label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    modifier = Modifier.testTag(tab.testTag),
                  )
                }
                val moreLabel = stringResource(R.string.nav_more)
                val moreSelected = drawerState.isOpen || isSecondaryRouteActive
                NavigationBarItem(
                  selected = moreSelected,
                  onClick = { coroutineScope.launch { drawerState.open() } },
                  icon = {
                    Icon(
                      imageVector = if (moreSelected) Icons.Rounded.MoreHoriz else Icons.Outlined.MoreHoriz,
                      contentDescription = moreLabel,
                    )
                  },
                  label = { Text(moreLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                  modifier = Modifier.testTag("nav_more_tab"),
                )
              }
            }
          },
        ) { innerPadding ->
          Row(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (useNavigationRail) {
              NavigationRail(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
              ) {
                primaryTabs.forEach { tab ->
                  val selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true
                  val label = stringResource(tab.labelRes)
                  NavigationRailItem(
                    selected = selected,
                    onClick = { navigateToTab(tab.route) },
                    icon = {
                      Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = label,
                      )
                    },
                    label = { Text(label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    modifier = Modifier.testTag(tab.testTag),
                  )
                }
                val moreLabel = stringResource(R.string.nav_more)
                val moreSelected = drawerState.isOpen || isSecondaryRouteActive
                NavigationRailItem(
                  selected = moreSelected,
                  onClick = { coroutineScope.launch { drawerState.open() } },
                  icon = {
                    Icon(
                      imageVector = if (moreSelected) Icons.Rounded.MoreHoriz else Icons.Outlined.MoreHoriz,
                      contentDescription = moreLabel,
                    )
                  },
                  label = { Text(moreLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                  modifier = Modifier.testTag("nav_more_tab"),
                )
              }
            }

        NavHost(
          navController = navController,
          startDestination = ROUTE_CAST,
          modifier = Modifier.weight(1f).fillMaxSize(),
        ) {
          composable(ROUTE_CAST) {
            CastScreen(
              uiState = uiState,
              isCalibrationEnabled = isCalibrationEnabled,
              onSelectDomain = viewModel::updateDomain,
              onSelectIntentionAnchor = viewModel::updateIntentionAnchorType,
              onTeamAChange = viewModel::updateTeamAName,
              onTeamBChange = viewModel::updateTeamBName,
              onConsultantNameChange = viewModel::updateConsultantFirstName,
              onMotherNameChange = viewModel::updateMotherFirstName,
              onOrganizationNameChange = viewModel::updateOrganizationName,
              onSelectOrganizationKind = viewModel::updateOrganizationKind,
              onQuestionChange = viewModel::updateQuestionText,
              onCategoryInputChange = viewModel::updateSelectedCategoryInput,
              onToggleOptionExactScore = viewModel::toggleOptionExactScore,
              onToggleOptionYellowCards = viewModel::toggleOptionYellowCards,
              onToggleOptionRedCards = viewModel::toggleOptionRedCards,
              onToggleAutoSave = viewModel::updateAutoSaveEnabled,
              onToggleDisableRamlForbidden = { enabled ->
                prefs.edit().putBoolean("disable_raml_forbidden_times", enabled).apply()
                viewModel.updateDisableRamlAtForbiddenTimes(enabled)
              },
              onToggleDisableRamlDiscouraged = { enabled ->
                prefs.edit().putBoolean("disable_raml_discouraged_times", enabled).apply()
                viewModel.updateDisableRamlAtDiscouragedTimes(enabled)
              },
              onRefreshRamlTiming = viewModel::refreshRamlTiming,
              onSelectPlanetHour = viewModel::updatePlanetHour,
              onSelectCastingMode = viewModel::updateCastingMode,
              onStrikeSand = viewModel::strikeSandAt,
              onAdvanceLine = viewModel::advanceSandLine,
              onSelectSandLine = viewModel::selectSandLine,
              onAutoCompleteSand = viewModel::autoCompleteRemainingSandLines,
              onResetSand = viewModel::resetSand,
              onToggleMotherRank = viewModel::toggleMotherRank,
              onSetMotherFigure = viewModel::setMotherFigure,
              onErectTakht = {
                if (viewModel.erectTakhtFromCurrentMode() != null) {
                  navigateToTab(ROUTE_TAKHT)
                }
              },
              onSpontaneousCast = {
                if (viewModel.castSpontaneous() != null) {
                  navigateToTab(ROUTE_TAKHT)
                }
              },
            )
          }

          composable(ROUTE_TAKHT) {
            val currentRecurrence =
              uiState.currentReading?.let { reading ->
                archiveRecurrenceReport.bySignature[
                  PredictiveCalibrationEngine.canonicalSignature(reading),
                ]
              }
            TakhtScreen(
              reading = uiState.currentReading,
              inspectedHouseNumber = uiState.inspectedHouseNumber,
              isSaved = uiState.activeSavedThemeId != null,
              savedTitle = uiState.activeSavedTitle,
              savedContextNotes = uiState.activeContextNotes,
              savedActualOutcome = uiState.activeActualOutcome,
              savedActualScore = uiState.activeActualScore,
              savedLeagueCategory = uiState.activeLeagueCategory,
              savedActualYellowCards = uiState.activeActualYellowCards,
              savedActualRedCards = uiState.activeActualRedCards,
              autoSaveEnabled = uiState.autoSaveEnabled,
              optionExactScore = uiState.optionExactScore,
              optionYellowCards = uiState.optionYellowCards,
              optionRedCards = uiState.optionRedCards,
              themeRecurrence = currentRecurrence,
              isCalibrationEnabled = isCalibrationEnabled,
              onSaveThemeWithCards = { title, notes, outcome, score, league, yellowCards, redCards ->
                viewModel.saveCurrentReading(
                  title = title,
                  contextNotes = notes,
                  actualOutcome = outcome,
                  actualScoreText = score,
                  leagueCategory = league,
                  actualYellowCardsText = yellowCards,
                  actualRedCardsText = redCards,
                )
              },
              onToggleOptionExactScore = viewModel::toggleOptionExactScore,
              onToggleOptionYellowCards = viewModel::toggleOptionYellowCards,
              onToggleOptionRedCards = viewModel::toggleOptionRedCards,
              onInspectHouse = viewModel::inspectHouse,
              onGoToCast = { navigateToTab(ROUTE_CAST) },
              onOpenFigureDetail = { sheetFigure = it },
            )
          }

          composable(ROUTE_SAVED) {
            SavedThemesScreen(
              savedThemes = savedThemes,
              calibrationReport = calibrationReport,
              frequencyReport = archiveFrequencyReport,
              recurrenceReport = archiveRecurrenceReport,
              isCalibrationEnabled = isCalibrationEnabled,
              onOpenTheme = { entity ->
                viewModel.loadSavedTheme(entity)
                navigateToTab(ROUTE_TAKHT)
              },
              onUpdateContextWithCards = { id, title, notes, outcome, score, league, yellowCards, redCards ->
                viewModel.updateSavedThemeContext(
                  id = id,
                  title = title,
                  notes = notes,
                  actualOutcome = outcome,
                  actualScoreText = score,
                  leagueCategory = league,
                  actualYellowCardsText = yellowCards,
                  actualRedCardsText = redCards,
                )
              },
              onDeleteTheme = viewModel::deleteSavedTheme,
              onToggleCalibrationEnabled = viewModel::toggleCalibrationEnabled,
              onApplyCalibration = viewModel::applyAndPersistCalibration,
              onResetCalibration = viewModel::resetCalibrationToBaseline,
              onImportNov17Matches = viewModel::importNov17Matches,
              onImportNov18Draws = viewModel::seedNov18ConsultationArchives,
              onApplyNov18VerifiedResults = viewModel::applyNov18VerifiedResults,
              onBuildCategoryExportText = { entity, catName, format ->
                viewModel.buildArchiveExportText(entity, catName, format)
              },
              onSuggestedCategoryExportFileName = { entity, catName, format ->
                viewModel.suggestedArchiveExportFileName(entity, catName, format)
              },
              onGoToCast = { navigateToTab(ROUTE_CAST) },
            )
          }

          composable(ROUTE_FIGURES) {
            FiguresCatalogScreen(
              uiState = uiState,
              onSearchChange = viewModel::updateFigureSearch,
              onToggleElement = viewModel::toggleElementFilter,
              onTogglePolarity = viewModel::togglePolarityFilter,
              onSelectFigure = { sheetFigure = it },
              encyclopediaFigures = encyclopediaFigures,
              onToggleFavoritesOnly = viewModel::toggleFavoritesOnlyFilter,
              onToggleFigureFavorite = viewModel::toggleFigureFavorite,
            )
          }

          composable(ROUTE_GUIDE) {
            GuideScreen(
              selectedCourseBookId = uiState.selectedCourseBookId,
              completedCourseModuleIds = uiState.completedCourseModuleIds,
              onSelectCourseBook = viewModel::selectCourseBook,
              onToggleCourseModuleCompleted = { moduleId ->
                val updated = viewModel.toggleCourseModuleCompleted(moduleId)
                prefs.edit().putStringSet("completed_course_modules", updated).apply()
              },
              onOpenFigureDetail = { sheetFigure = it },
              onOpenTakhtExample = { example ->
                viewModel.loadCourseTakhtExample(example)
                navigateToTab(ROUTE_TAKHT)
              },
              externalSelectedTab = uiState.selectedGuideTab,
              onSelectTab = viewModel::selectGuideTab,
            )
          }

          composable(ROUTE_LESSONS) {
            InteractiveLessonsScreen()
          
          composable(ROUTE_EXERCISES) {
            ExercisesScreen()
          }
          }

          composable(ROUTE_SETTINGS) {
            SettingsScreen(
              uiState = uiState,
              isCalibrationEnabled = isCalibrationEnabled,
              onToggleDisableRamlForbidden = { enabled ->
                prefs.edit().putBoolean("disable_raml_forbidden_times", enabled).apply()
                viewModel.updateDisableRamlAtForbiddenTimes(enabled)
              },
              onToggleDisableRamlDiscouraged = { enabled ->
                prefs.edit().putBoolean("disable_raml_discouraged_times", enabled).apply()
                viewModel.updateDisableRamlAtDiscouragedTimes(enabled)
              },
              onRefreshRamlTiming = viewModel::refreshRamlTiming,
              onToggleAutoSave = viewModel::updateAutoSaveEnabled,
              onToggleOptionExactScore = viewModel::toggleOptionExactScore,
              onToggleOptionYellowCards = viewModel::toggleOptionYellowCards,
              onToggleOptionRedCards = viewModel::toggleOptionRedCards,
              onToggleCalibrationEnabled = viewModel::toggleCalibrationEnabled,
              onOpenCourses = {
                viewModel.selectGuideTab(0)
                navigateToTab(ROUTE_GUIDE)
              },
              onOpenArchives = { navigateToTab(ROUTE_SAVED) },
            )
          }
        }
      }
    }
  }
  }
  }

  sheetFigure?.let { fig ->
    FigureDetailBottomSheet(
      figure = fig,
      onDismiss = { sheetFigure = null },
      onSelectFigure = { sheetFigure = it },
    )
  }
}

