package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Scoreboard
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.RamlTimeStatus
import com.example.ui.GeomancyUiState
import com.example.ui.components.SectionHeaderCard
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard

@Composable
fun SettingsScreen(
  uiState: GeomancyUiState,
  isCalibrationEnabled: Boolean,
  onToggleDisableRamlForbidden: (Boolean) -> Unit,
  onToggleDisableRamlDiscouraged: (Boolean) -> Unit,
  onRefreshRamlTiming: () -> Unit,
  onToggleAutoSave: (Boolean) -> Unit,
  onToggleOptionExactScore: () -> Unit,
  onToggleOptionYellowCards: () -> Unit,
  onToggleOptionRedCards: () -> Unit,
  onToggleCalibrationEnabled: (Boolean) -> Unit,
  onOpenCourses: () -> Unit,
  onOpenArchives: () -> Unit,
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

  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().widthIn(max = 760.dp).testTag("settings_screen_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
      item {
        SectionHeaderCard(
          title = stringResource(R.string.settings_title),
          arabicSubtitle = "الإعدادات والضوابط",
          body = stringResource(R.string.settings_desc),
        )
      }

      // 1. Protection horaire du Raml
      item {
        LiquidGlassCard(
          modifier = Modifier.fillMaxWidth().testTag("settings_timing_card"),
          shape = RoundedCornerShape(18.dp),
          elevated = true,
          accentBorderColor = timingColor,
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f),
              ) {
                Icon(
                  imageVector = timingIcon,
                  contentDescription = null,
                  tint = timingColor,
                  modifier = Modifier.size(20.dp),
                )
                Column {
                  Text(
                    text = "Protection horaire du Raml",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                  )
                  Text(
                    text = "${assessment.status.label} · Jour lunaire ${assessment.lunarDay}",
                    style = MaterialTheme.typography.labelSmall,
                    color = timingColor,
                    fontWeight = FontWeight.SemiBold,
                  )
                }
              }
              IconButton(
                onClick = onRefreshRamlTiming,
                modifier =
                  Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                    .testTag("settings_refresh_timing_button"),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Schedule,
                  contentDescription = "Actualiser l’heure et le jour lunaire",
                  tint = timingColor,
                )
              }
            }

            if (assessment.reasons.isNotEmpty()) {
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = timingColor.copy(alpha = 0.10f),
                border = BorderStroke(1.dp, timingColor.copy(alpha = 0.35f)),
              ) {
                Text(
                  text = assessment.reasons.joinToString("\n• ", prefix = "• "),
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface,
                  modifier = Modifier.fillMaxWidth().padding(10.dp),
                )
              }
            }

            SettingSwitchRow(
              title = "Désactiver le Raml aux temps & heures interdits",
              subtitle = "Activé par défaut (Zawāl, lever/coucher, Al-Maḥāq, Samedi-Saturne, Mardi-Mars)",
              checked = uiState.disableRamlAtForbiddenTimes,
              onCheckedChange = onToggleDisableRamlForbidden,
              testTag = "settings_forbidden_switch",
            )

            SettingSwitchRow(
              title = "Désactiver aussi aux temps & heures déconseillés",
              subtitle = "Bloque Mars/Saturne, jours Ayyām al-Naḥs (Al-Fulk p. 31) et fin du mercredi",
              checked = uiState.disableRamlAtDiscouragedTimes,
              onCheckedChange = onToggleDisableRamlDiscouraged,
              testTag = "settings_discouraged_switch",
            )
          }
        }
      }

      // 2. Options de prédiction & d'archivage
      item {
        LiquidGlassCard(
          modifier = Modifier.fillMaxWidth().testTag("settings_prediction_card"),
          shape = RoundedCornerShape(18.dp),
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              Icon(
                imageVector = Icons.Rounded.Scoreboard,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
              )
              Text(
                text = "Options de tirage & Duel sportif",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
            }

            SettingSwitchRow(
              title = "Auto-enregistrement des thèmes",
              subtitle = "Sauvegarde automatiquement chaque nouveau Takht dans les Archives locales",
              checked = uiState.autoSaveEnabled,
              onCheckedChange = onToggleAutoSave,
              testTag = "settings_autosave_switch",
            )

            SettingSwitchRow(
              title = "Score exact & Les deux équipes marquent (BTTS)",
              subtitle = "Affiche les prévisions de score exact, BTTS Oui/Non et fourchette de buts",
              checked = uiState.optionExactScore,
              onCheckedChange = { onToggleOptionExactScore() },
              testTag = "settings_exact_score_switch",
            )

            SettingSwitchRow(
              title = "Précision Cartons jaunes",
              subtitle = "Active l'estimation géomantique des avertissements par camp",
              checked = uiState.optionYellowCards,
              onCheckedChange = { onToggleOptionYellowCards() },
              testTag = "settings_yellow_cards_switch",
            )

            SettingSwitchRow(
              title = "Précision Cartons rouges",
              subtitle = "Active la détection de risque d'expulsion et jeu dur",
              checked = uiState.optionRedCards,
              onCheckedChange = { onToggleOptionRedCards() },
              testTag = "settings_red_cards_switch",
            )
          }
        }
      }

      // 3. Moteur & Calibration + Raccourcis
      item {
        LiquidGlassCard(
          modifier = Modifier.fillMaxWidth().testTag("settings_calibration_card"),
          shape = RoundedCornerShape(18.dp),
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              Icon(
                imageVector = Icons.Rounded.Tune,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp),
              )
              Text(
                text = "Moteur prédictif & Apprentissage",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
            }

            SettingSwitchRow(
              title = "Calibration bayésienne par les archives",
              subtitle = "Ajuste les poids des maisons selon vos résultats vérifiés",
              checked = isCalibrationEnabled,
              onCheckedChange = onToggleCalibrationEnabled,
              testTag = "settings_calibration_switch",
            )

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              OutlinedButton(
                onClick = onOpenCourses,
                modifier = Modifier.weight(1f).minimumInteractiveComponentSize(),
              ) {
                Icon(
                  imageVector = Icons.AutoMirrored.Rounded.MenuBook,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Cours ($completedCourses/${GeomancyCoursesCatalog.totalModulesCount})")
              }
              OutlinedButton(
                onClick = onOpenArchives,
                modifier = Modifier.weight(1f).minimumInteractiveComponentSize(),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Bookmarks,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Archives")
              }
            }
          }
        }
      }

      item { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }
}

@Composable
private fun SettingSwitchRow(
  title: String,
  subtitle: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  testTag: String,
) {
  Row(
    modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(modifier = Modifier.weight(1f).padding(end = 10.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      modifier = Modifier.testTag(testTag),
    )
  }
}
