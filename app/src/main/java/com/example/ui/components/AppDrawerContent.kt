package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.RamlTimeStatus
import com.example.ui.GeomancyUiState
import com.example.ui.theme.AlRamlColors

@Composable
fun AppDrawerSheet(
  currentRoute: String?,
  uiState: GeomancyUiState,
  onNavigateToCast: () -> Unit,
  onNavigateToTakht: () -> Unit,
  onNavigateToSaved: () -> Unit,
  onNavigateToFigures: () -> Unit,
  onNavigateToLessons: () -> Unit,
  onNavigateToExercises: () -> Unit,
  onNavigateToGuideTab: (Int) -> Unit,
  onNavigateToSettings: () -> Unit,
  onCloseDrawer: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val extColors = AlRamlColors.extended
  val assessment = uiState.ramlTimeAssessment
  val timingColor =
    when (assessment.status) {
      RamlTimeStatus.FORBIDDEN -> extColors.unfortunate
      RamlTimeStatus.DISCOURAGED -> extColors.mixed
      RamlTimeStatus.FAVORABLE -> extColors.fortunate
    }
  val timingIcon =
    when (assessment.status) {
      RamlTimeStatus.FORBIDDEN -> Icons.Rounded.Lock
      RamlTimeStatus.DISCOURAGED -> Icons.Rounded.WarningAmber
      RamlTimeStatus.FAVORABLE -> Icons.Rounded.NightsStay
    }
  val completedCourses =
    uiState.completedCourseModuleIds.count { id -> GeomancyCoursesCatalog.findModule(id) != null }

  ModalDrawerSheet(
    modifier = modifier.widthIn(max = 320.dp).testTag("app_drawer_sheet"),
    drawerContainerColor = MaterialTheme.colorScheme.surface,
  ) {
    Column(
      modifier = Modifier.verticalScroll(rememberScrollState()).padding(vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Al-Raml Al-Toukhi",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
          )
          Text(
            text = "Menu, Sous-menus & Paramètres",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        IconButton(
          onClick = onCloseDrawer,
          modifier = Modifier.minimumInteractiveComponentSize().testTag("close_drawer_button"),
        ) {
          Icon(
            imageVector = Icons.Rounded.Close,
            contentDescription = "Fermer le menu",
          )
        }
      }

      Surface(
        shape = RoundedCornerShape(12.dp),
        color = timingColor.copy(alpha = 0.12f),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Icon(
            imageVector = timingIcon,
            contentDescription = null,
            tint = timingColor,
          )
          Column {
            Text(
              text = assessment.status.label,
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.SemiBold,
              color = timingColor,
            )
            Text(
              text = "Jour lunaire ${assessment.lunarDay} · ${uiState.activePlanetHour.englishName}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      DrawerSectionLabel("Sous-menus & Savoir")

      NavigationDrawerItem(
        label = { Text("Encyclopédie des 16 Figures") },
        selected = currentRoute == "figures",
        onClick = onNavigateToFigures,
        icon = { Icon(Icons.Rounded.Style, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_figures_item"),
      )

      NavigationDrawerItem(
        label = { Text("Leçons Interactives") },
        selected = currentRoute == "lessons",
        onClick = onNavigateToLessons,
        icon = { Icon(Icons.Rounded.HistoryEdu, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_lessons_item"),
      )

      NavigationDrawerItem(
        label = { Text("Exercices Pratiques") },
        selected = currentRoute == "exercises",
        onClick = onNavigateToExercises,
        icon = { Icon(Icons.Rounded.Edit, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_exercises_item"),
      )

      NavigationDrawerItem(
        label = { Text("Cours & Modules") },
        badge = {
          Text(
            text = "$completedCourses modules",
            style = MaterialTheme.typography.labelSmall,
          )
        },
        selected = currentRoute == "guide" && uiState.selectedGuideTab == 0,
        onClick = { onNavigateToGuideTab(0) },
        icon = { Icon(Icons.AutoMirrored.Rounded.MenuBook, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_courses_item"),
      )

      NavigationDrawerItem(
        label = { Text("Chronologie Ilm al-Raml") },
        selected = currentRoute == "guide" && uiState.selectedGuideTab == 1,
        onClick = { onNavigateToGuideTab(1) },
        icon = { Icon(Icons.Rounded.Timeline, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_timeline_item"),
      )

      NavigationDrawerItem(
        label = { Text("Méthode & Règles du tracé") },
        selected = currentRoute == "guide" && uiState.selectedGuideTab == 2,
        onClick = { onNavigateToGuideTab(2) },
        icon = { Icon(Icons.Rounded.HistoryEdu, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_guide_item"),
      )

      HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
      DrawerSectionLabel("Configuration")

      NavigationDrawerItem(
        label = { Text("Paramètres & Préférences") },
        selected = currentRoute == "settings",
        onClick = onNavigateToSettings,
        icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_settings_item"),
      )

      HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
      DrawerSectionLabel("Accès rapide")

      NavigationDrawerItem(
        label = { Text("Frapper le Sable (Tirer)") },
        selected = currentRoute == "cast",
        onClick = onNavigateToCast,
        icon = { Icon(Icons.Rounded.AutoAwesome, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_cast_item"),
      )

      NavigationDrawerItem(
        label = { Text("Takht à 16 Maisons") },
        selected = currentRoute == "takht",
        onClick = onNavigateToTakht,
        icon = { Icon(Icons.Rounded.GridView, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_takht_item"),
      )

      NavigationDrawerItem(
        label = { Text("Archives & Calibration") },
        selected = currentRoute == "saved",
        onClick = onNavigateToSaved,
        icon = { Icon(Icons.Rounded.Bookmarks, contentDescription = null) },
        modifier =
          Modifier.padding(NavigationDrawerItemDefaults.ItemPadding).testTag("drawer_saved_item"),
      )
    }
  }
}

@Composable
private fun DrawerSectionLabel(text: String) {
  Text(
    text = text,
    style = MaterialTheme.typography.labelLarge,
    color = MaterialTheme.colorScheme.primary,
    modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp),
  )
}
