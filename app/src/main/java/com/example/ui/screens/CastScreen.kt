package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.BookmarkAdded
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.NightsStay
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Scoreboard
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.Element
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.Planet
import com.example.geomancy.PointParity
import com.example.geomancy.QuestionDomain
import com.example.geomancy.RamlTimeStatus
import com.example.ui.CastingMode
import com.example.ui.GeomancyUiState
import com.example.ui.IntentionAnchorType
import com.example.ui.OrganizationKind
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.components.SectionHeaderCard
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CastScreen(
  uiState: GeomancyUiState,
  isCalibrationEnabled: Boolean = false,
  onSelectDomain: (QuestionDomain) -> Unit,
  onSelectIntentionAnchor: (IntentionAnchorType) -> Unit = {},
  onTeamAChange: (String) -> Unit = {},
  onTeamBChange: (String) -> Unit = {},
  onConsultantNameChange: (String) -> Unit = {},
  onMotherNameChange: (String) -> Unit = {},
  onOrganizationNameChange: (String) -> Unit = {},
  onSelectOrganizationKind: (OrganizationKind) -> Unit = {},
  onQuestionChange: (String) -> Unit,
  onCategoryInputChange: (String) -> Unit = {},
  onToggleOptionExactScore: () -> Unit = {},
  onToggleOptionYellowCards: () -> Unit = {},
  onToggleOptionRedCards: () -> Unit = {},
  onToggleAutoSave: (Boolean) -> Unit = {},
  onToggleDisableRamlForbidden: (Boolean) -> Unit = {},
  onToggleDisableRamlDiscouraged: (Boolean) -> Unit = {},
  onRefreshRamlTiming: () -> Unit = {},
  onSelectPlanetHour: (Planet) -> Unit,
  onSelectCastingMode: (CastingMode) -> Unit,
  onStrikeSand: (Float, Float) -> Unit,
  onAdvanceLine: () -> Unit,
  onSelectSandLine: (Int) -> Unit,
  onAutoCompleteSand: () -> Unit,
  onResetSand: () -> Unit,
  onToggleMotherRank: (Int, Int) -> Unit,
  onSetMotherFigure: (Int, GeomanticFigure) -> Unit,
  onErectTakht: () -> Unit,
  onSpontaneousCast: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var editingMotherIndex by remember { mutableStateOf<Int?>(null) }

  Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.TopCenter,
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().widthIn(max = 720.dp).testTag("cast_screen_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
      item {
        SectionHeaderCard(
          title = stringResource(R.string.cast_header_title),
          arabicSubtitle = "ضرب الرمل · النيّة",
          body = stringResource(R.string.cast_header_desc),
        )
      }

      item {
        OutlinedTextField(
          value = uiState.questionText,
          onValueChange = onQuestionChange,
          label = { Text("Objet / But de la consultation (optionnel)") },
          placeholder = { Text(stringResource(R.string.question_placeholder)) },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("question_input"),
          shape = RoundedCornerShape(14.dp),
        )
      }

      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = stringResource(R.string.domain_label),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
          )
          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            QuestionDomain.entries.forEach { domain ->
              val selected = domain == uiState.selectedDomain
              FilterChip(
                selected = selected,
                onClick = { onSelectDomain(domain) },
                label = {
                  Text(
                    text = "${domain.title} (M${domain.primaryHouse})",
                    maxLines = 1,
                  )
                },
                colors =
                  FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    selectedLabelColor = MaterialTheme.colorScheme.primary,
                  ),
                modifier = Modifier.testTag("domain_chip_${domain.name.lowercase()}"),
              )
            }
          }
          Text(
            text = "${uiState.selectedDomain.arabicTitle} — ${uiState.selectedDomain.description}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
          if (uiState.selectedDomain == QuestionDomain.CONTEST && isCalibrationEnabled) {
            Surface(
              shape = RoundedCornerShape(999.dp),
              color = AlRamlColors.extended.fortunate.copy(alpha = 0.14f),
              border = BorderStroke(1.dp, AlRamlColors.extended.fortunate.copy(alpha = 0.4f)),
            ) {
              Text(
                text = "Poids calibrés actifs sur les futurs pronostics de duel",
                style = MaterialTheme.typography.labelSmall,
                color = AlRamlColors.extended.fortunate,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
              )
            }
          }
        }
      }

      item {
        IntentionAnchoringSection(
          uiState = uiState,
          onSelectAnchor = onSelectIntentionAnchor,
          onTeamAChange = onTeamAChange,
          onTeamBChange = onTeamBChange,
          onConsultantNameChange = onConsultantNameChange,
          onMotherNameChange = onMotherNameChange,
          onOrganizationNameChange = onOrganizationNameChange,
          onSelectOrganizationKind = onSelectOrganizationKind,
        )
      }

      item {
        CategoryAndMatchOptionsCard(
          uiState = uiState,
          onCategoryInputChange = onCategoryInputChange,
          onToggleOptionExactScore = onToggleOptionExactScore,
          onToggleOptionYellowCards = onToggleOptionYellowCards,
          onToggleOptionRedCards = onToggleOptionRedCards,
          onToggleAutoSave = onToggleAutoSave,
        )
      }

      item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Text(
            text = stringResource(R.string.planet_hour_label),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
          )
          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            listOf(
              Planet.SUN,
              Planet.MOON,
              Planet.MARS,
              Planet.MERCURY,
              Planet.JUPITER,
              Planet.VENUS,
              Planet.SATURN,
            ).forEach { planet ->
              val selected = planet == uiState.activePlanetHour
              FilterChip(
                selected = selected,
                onClick = { onSelectPlanetHour(planet) },
                label = { Text("${planet.englishName} · ${planet.dayEnglish.take(3)}") },
                colors =
                  FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                    selectedLabelColor = MaterialTheme.colorScheme.secondary,
                  ),
                modifier = Modifier.testTag("planet_chip_${planet.name.lowercase()}"),
              )
            }
          }
          RamlTimingProtectionCard(
            uiState = uiState,
            onToggleForbidden = onToggleDisableRamlForbidden,
            onToggleDiscouraged = onToggleDisableRamlDiscouraged,
            onRefreshTiming = onRefreshRamlTiming,
          )
        }
      }

      item {
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
          SegmentedButton(
            selected = uiState.castingMode == CastingMode.STRIKE_SAND,
            onClick = { onSelectCastingMode(CastingMode.STRIKE_SAND) },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            modifier = Modifier.testTag("mode_strike_sand_btn"),
          ) {
            Text(stringResource(R.string.mode_strike_sand), maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
          SegmentedButton(
            selected = uiState.castingMode == CastingMode.DIRECT_MOTHERS,
            onClick = { onSelectCastingMode(CastingMode.DIRECT_MOTHERS) },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            modifier = Modifier.testTag("mode_direct_mothers_btn"),
          ) {
            Text(stringResource(R.string.mode_direct_mothers), maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
        }
      }

      item {
        AnimatedContent(targetState = uiState.castingMode, label = "casting_mode_content") { mode ->
          when (mode) {
            CastingMode.STRIKE_SAND -> {
              SandStrikeSection(
                uiState = uiState,
                onStrikeSand = onStrikeSand,
                onAdvanceLine = onAdvanceLine,
                onSelectSandLine = onSelectSandLine,
                onAutoCompleteSand = onAutoCompleteSand,
                onResetSand = onResetSand,
                onSetAnonymousAnchor = { onSelectIntentionAnchor(IntentionAnchorType.ANONYMOUS) },
                onOverrideDisableBlock = {
                  if (uiState.ramlTimeAssessment.isForbidden && uiState.disableRamlAtForbiddenTimes) {
                    onToggleDisableRamlForbidden(false)
                  } else if (uiState.ramlTimeAssessment.isDiscouraged && uiState.disableRamlAtDiscouragedTimes) {
                    onToggleDisableRamlDiscouraged(false)
                  }
                },
              )
            }
            CastingMode.DIRECT_MOTHERS -> {
              DirectMothersSection(
                mothers = uiState.selectedMothers,
                onToggleRank = onToggleMotherRank,
                onOpenFigurePicker = { editingMotherIndex = it },
              )
            }
          }
        }
      }

      item {
        val canErectTakht =
          !uiState.isSandTappingBlocked &&
            (uiState.castingMode == CastingMode.DIRECT_MOTHERS || uiState.isSandCastingComplete)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          if (uiState.isSandTappingBlocked) {
            Text(
              text =
                "Raml désactivé (${uiState.ramlTimeAssessment.status.label}). Patiente hors du créneau ou désactive la protection ci-dessus pour continuer.",
              style = MaterialTheme.typography.labelSmall,
              color = AlRamlColors.extended.unfortunate,
              modifier = Modifier.testTag("raml_blocked_action_hint"),
            )
          } else if (uiState.castingMode == CastingMode.STRIKE_SAND && !uiState.isSandCastingComplete) {
            Text(
              text =
                "Remplis les 4 parties de toutes les Mères 1–4 (${uiState.completedSandLines}/16 tracées) avant de révéler les figures et d’ériger le Takht.",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.testTag("sand_completion_requirement_hint"),
            )
          }
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            OutlinedButton(
              onClick = onSpontaneousCast,
              enabled = !uiState.isSandTappingBlocked,
              modifier = Modifier.weight(1f).height(52.dp).testTag("spontaneous_cast_button"),
              shape = RoundedCornerShape(14.dp),
              border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)),
            ) {
              Icon(
                imageVector = Icons.Rounded.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = stringResource(R.string.btn_spontaneous_cast),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            }

            Button(
              onClick = onErectTakht,
              enabled = canErectTakht,
              modifier = Modifier.weight(1f).height(52.dp).testTag("erect_takht_button"),
              shape = RoundedCornerShape(14.dp),
              colors =
                ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.primary,
                  contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
              Icon(
                imageVector = Icons.Rounded.Bolt,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = stringResource(R.string.btn_erect_takht),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            }
          }
        }
      }

      item { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }

  editingMotherIndex?.let { motherIdx ->
    ModalBottomSheet(
      onDismissRequest = { editingMotherIndex = null },
      containerColor = MaterialTheme.colorScheme.surface,
    ) {
      Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        Text(
          text = "Choisir la figure pour Mère ${motherIdx + 1}",
          style = MaterialTheme.typography.headlineSmall,
          color = MaterialTheme.colorScheme.primary,
        )
        LazyColumn(
          modifier = Modifier.height(380.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          items(AlToukhiFigures.allInWestAfricanOrder, key = { it.id }) { fig ->
            Surface(
              modifier =
                Modifier.fillMaxWidth().clickable {
                  onSetMotherFigure(motherIdx, fig)
                  editingMotherIndex = null
                },
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.surfaceVariant,
              border = BorderStroke(1.dp, AlRamlColors.extended.cardBorder),
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
              ) {
                GeomanticFigureGlyph(figure = fig, size = 32.dp)
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "${fig.westAfricanOrder}. ${fig.westAfricanName} · ${fig.arabicName}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                  )
                  Text(
                    text = "${fig.transliteration} — ${fig.englishTitle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                  )
                }
              }
            }
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}

@Composable
private fun IntentionAnchoringSection(
  uiState: GeomancyUiState,
  onSelectAnchor: (IntentionAnchorType) -> Unit,
  onTeamAChange: (String) -> Unit,
  onTeamBChange: (String) -> Unit,
  onConsultantNameChange: (String) -> Unit,
  onMotherNameChange: (String) -> Unit,
  onOrganizationNameChange: (String) -> Unit,
  onSelectOrganizationKind: (OrganizationKind) -> Unit,
) {
  val extColors = AlRamlColors.extended
  val anchored = uiState.isIntentionAnchored

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("intention_anchoring_card"),
    shape = RoundedCornerShape(20.dp),
    accentBorderColor = if (anchored) extColors.fortunate else extColors.cardBorder,
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Ancrage de l’Intention (Niyya · النيّة)",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
          )
          Text(
            text = uiState.intentionAnchorType.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
          shape = RoundedCornerShape(999.dp),
          color =
            if (anchored) extColors.fortunate.copy(alpha = 0.14f)
            else MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
          border =
            BorderStroke(
              1.dp,
              if (anchored) extColors.fortunate.copy(alpha = 0.45f)
              else MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f),
            ),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            Icon(
              imageVector = if (anchored) Icons.Rounded.CheckCircle else Icons.Rounded.Person,
              contentDescription = null,
              tint = if (anchored) extColors.fortunate else MaterialTheme.colorScheme.secondary,
              modifier = Modifier.size(14.dp),
            )
            Text(
              text = if (anchored) "Ancrée" else "À ancrer",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.SemiBold,
              color = if (anchored) extColors.fortunate else MaterialTheme.colorScheme.secondary,
            )
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        IntentionAnchorType.entries.forEach { anchorType ->
          val selected = uiState.intentionAnchorType == anchorType
          FilterChip(
            selected = selected,
            onClick = { onSelectAnchor(anchorType) },
            label = { Text(anchorType.shortLabel, maxLines = 1) },
            colors =
              FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                selectedLabelColor = MaterialTheme.colorScheme.primary,
              ),
            modifier = Modifier.testTag("anchor_type_chip_${anchorType.name.lowercase()}"),
          )
        }
      }

      when (uiState.intentionAnchorType) {
        IntentionAnchorType.TEAMS_DUEL -> {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            OutlinedTextField(
              value = uiState.teamAName,
              onValueChange = onTeamAChange,
              label = { Text("Équipe A (Camp A)") },
              placeholder = { Text("ex. Portugalete") },
              singleLine = true,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).testTag("anchor_team_a_input"),
            )
            OutlinedTextField(
              value = uiState.teamBName,
              onValueChange = onTeamBChange,
              label = { Text("Équipe B (Camp B)") },
              placeholder = { Text("ex. Real Unión") },
              singleLine = true,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).testTag("anchor_team_b_input"),
            )
          }
        }
        IntentionAnchorType.PERSON_AND_MOTHER -> {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            OutlinedTextField(
              value = uiState.consultantFirstName,
              onValueChange = onConsultantNameChange,
              label = { Text("Prénom du consultant") },
              placeholder = { Text("ex. Amadou") },
              singleLine = true,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).testTag("anchor_consultant_input"),
            )
            OutlinedTextField(
              value = uiState.motherFirstName,
              onValueChange = onMotherNameChange,
              label = { Text("Prénom de sa mère") },
              placeholder = { Text("ex. Aminata") },
              singleLine = true,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.weight(1f).testTag("anchor_mother_input"),
            )
          }
        }
        IntentionAnchorType.ORGANIZATION -> {
          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            OrganizationKind.entries.forEach { kind ->
              FilterChip(
                selected = uiState.organizationKind == kind,
                onClick = { onSelectOrganizationKind(kind) },
                label = { Text(kind.label, maxLines = 1) },
                modifier = Modifier.testTag("anchor_org_kind_${kind.name.lowercase()}"),
              )
            }
          }
          OutlinedTextField(
            value = uiState.organizationName,
            onValueChange = onOrganizationNameChange,
            label = { Text("Nom (${uiState.organizationKind.label})") },
            placeholder = { Text("ex. Institut / Entreprise / Parti politique…") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().testTag("anchor_org_name_input"),
          )
        }
        IntentionAnchorType.ANONYMOUS -> {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
          ) {
            Row(
              modifier = Modifier.fillMaxWidth().padding(10.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              Icon(
                imageVector = Icons.Rounded.VisibilityOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp),
              )
              Text(
                text = "Consultation anonyme active : seul l’objet (${uiState.selectedDomain.title}) porte votre intention sur le sable.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun CategoryAndMatchOptionsCard(
  uiState: GeomancyUiState,
  onCategoryInputChange: (String) -> Unit,
  onToggleOptionExactScore: () -> Unit,
  onToggleOptionYellowCards: () -> Unit,
  onToggleOptionRedCards: () -> Unit,
  onToggleAutoSave: (Boolean) -> Unit,
) {
  val extColors = AlRamlColors.extended
  val isContest = uiState.selectedDomain == QuestionDomain.CONTEST
  val suggestions =
    remember(uiState.selectedDomain) {
      if (isContest) {
        listOf(
          "Duel Libre",
          "Copa del Rey",
          "Ligue des Nations UEFA",
          "Qualifications CAN 2027",
          "Championnat / Ligue",
        )
      } else {
        listOf(
          uiState.selectedDomain.title,
          "Personnel",
          "Professionnel",
          "Projet",
          "Famille",
        ).distinct()
      }
    }

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("cast_category_options_card"),
    shape = RoundedCornerShape(18.dp),
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.weight(1f),
        ) {
          Icon(
            imageVector = Icons.Rounded.Category,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp),
          )
          Text(
            text = "Catégorie & Options de suivi",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
          )
        }
        FilterChip(
          selected = uiState.autoSaveEnabled,
          onClick = { onToggleAutoSave(!uiState.autoSaveEnabled) },
          leadingIcon = {
            Icon(
              imageVector = Icons.Rounded.BookmarkAdded,
              contentDescription = null,
              modifier = Modifier.size(15.dp),
            )
          },
          label = {
            Text(
              text = if (uiState.autoSaveEnabled) "Auto-enreg. ✓" else "Manuel",
              maxLines = 1,
            )
          },
          colors =
            FilterChipDefaults.filterChipColors(
              selectedContainerColor = extColors.fortunate.copy(alpha = 0.16f),
              selectedLabelColor = extColors.fortunate,
            ),
          modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("cast_autosave_chip"),
        )
      }

      OutlinedTextField(
        value = uiState.selectedCategoryInput,
        onValueChange = onCategoryInputChange,
        label = {
          Text(
            if (isContest) "Catégorie / Ligue (regroupe vos exports)"
            else "Catégorie d’archive (ex. ${uiState.selectedDomain.title})",
          )
        },
        placeholder = {
          Text(
            if (isContest) "ex. Ligue des Nations UEFA"
            else "ex. ${uiState.selectedDomain.title}",
          )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().testTag("cast_category_input"),
      )

      Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        suggestions.forEach { cat ->
          val isSelected =
            uiState.selectedCategoryInput.equals(cat, ignoreCase = true)
          FilterChip(
            selected = isSelected,
            onClick = { onCategoryInputChange(if (isSelected) "" else cat) },
            label = { Text(cat, maxLines = 1) },
            modifier = Modifier.defaultMinSize(minHeight = 48.dp),
          )
        }
      }

      if (isContest) {
        Text(
          text = "Options de précision du duel :",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
          modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          FilterChip(
            selected = uiState.optionExactScore,
            onClick = onToggleOptionExactScore,
            leadingIcon = {
              Icon(
                imageVector = Icons.Rounded.Scoreboard,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
            },
            label = { Text("Score exact & BTTS", maxLines = 1) },
            modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("cast_option_exact_score"),
          )
          FilterChip(
            selected = uiState.optionYellowCards,
            onClick = onToggleOptionYellowCards,
            leadingIcon = {
              Icon(
                imageVector = Icons.Rounded.Style,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
            },
            label = { Text("Cartons jaunes", maxLines = 1) },
            modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("cast_option_yellow_cards"),
          )
          FilterChip(
            selected = uiState.optionRedCards,
            onClick = onToggleOptionRedCards,
            leadingIcon = {
              Icon(
                imageVector = Icons.Rounded.Style,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
            },
            label = { Text("Carton rouge", maxLines = 1) },
            modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("cast_option_red_cards"),
          )
        }
      }
    }
  }
}

@Composable
private fun SandStrikeSection(
  uiState: GeomancyUiState,
  onStrikeSand: (Float, Float) -> Unit,
  onAdvanceLine: () -> Unit,
  onSelectSandLine: (Int) -> Unit,
  onAutoCompleteSand: () -> Unit,
  onResetSand: () -> Unit,
  onSetAnonymousAnchor: () -> Unit,
  onOverrideDisableBlock: () -> Unit = {},
) {
  val lineIdx = uiState.activeSandLineIndex.coerceIn(0, 15)
  val motherNum = (lineIdx / 4) + 1
  val rankElement = Element.entries[lineIdx % 4]
  val extColors = AlRamlColors.extended
  val primaryColor = MaterialTheme.colorScheme.primary
  val borderColor = extColors.cardBorder
  val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
  val composedIntention = uiState.composedIntentionText()
  val isBlocked = uiState.isSandTappingBlocked

  Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
    LiquidGlassCard(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(20.dp),
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color =
            if (uiState.isIntentionAnchored) extColors.fortunate.copy(alpha = 0.12f)
            else MaterialTheme.colorScheme.secondary.copy(alpha = 0.12f),
          border =
            BorderStroke(
              1.dp,
              if (uiState.isIntentionAnchored) extColors.fortunate.copy(alpha = 0.35f)
              else MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f),
            ),
        ) {
          Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Row(
              modifier = Modifier.weight(1f),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
              Icon(
                imageVector = if (uiState.isIntentionAnchored) Icons.Rounded.CheckCircle else Icons.Rounded.Groups,
                contentDescription = null,
                tint = if (uiState.isIntentionAnchored) extColors.fortunate else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(16.dp),
              )
              Text(
                text =
                  if (composedIntention.isNotEmpty()) {
                    "Intention ancrée : $composedIntention"
                  } else {
                    "Ancre d’abord un nom ci-dessus (ou Anonyme) avant de frapper le sable"
                  },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
              )
            }
            if (!uiState.isIntentionAnchored) {
              TextButton(
                onClick = onSetAnonymousAnchor,
                modifier = Modifier.testTag("quick_anonymous_anchor_btn"),
              ) {
                Text("Anonyme", style = MaterialTheme.typography.labelSmall)
              }
            }
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Column {
            Text(
              text = "Ligne ${lineIdx + 1} sur 16 · Mère $motherNum",
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = "Rang : ${rankElement.englishName} (${rankElement.arabicName})",
              style = MaterialTheme.typography.bodySmall,
              color = extColors.forElement(rankElement),
            )
          }
          Text(
            text = "${uiState.completedSandLines}/16 tracées",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        if (isBlocked) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = extColors.unfortunate.copy(alpha = 0.14f),
            border = BorderStroke(1.dp, extColors.unfortunate.copy(alpha = 0.45f)),
            modifier = Modifier.fillMaxWidth().testTag("sand_raml_blocked_banner"),
          ) {
            Column(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Lock,
                  contentDescription = null,
                  tint = extColors.unfortunate,
                  modifier = Modifier.size(18.dp),
                )
                Text(
                  text = "Tapé du sable désactivé · ${uiState.ramlTimeAssessment.status.label}",
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                  color = extColors.unfortunate,
                )
              }
              if (uiState.ramlTimeAssessment.reasons.isNotEmpty()) {
                Text(
                  text = uiState.ramlTimeAssessment.reasons.joinToString(" · "),
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface,
                )
              }
              TextButton(
                onClick = onOverrideDisableBlock,
                modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("override_raml_block_button"),
              ) {
                Text("Désactiver la restriction maintenant")
              }
            }
          }
        }

        // Zone tactile de sable
        Box(
          modifier =
            Modifier.fillMaxWidth()
              .height(150.dp)
              .testTag("sand_tap_canvas")
              .drawBehind {
                drawRoundRect(
                  brush =
                    Brush.radialGradient(
                      colors =
                        if (isBlocked) {
                          listOf(surfaceColor.copy(alpha = 0.45f), surfaceColor.copy(alpha = 0.25f))
                        } else {
                          listOf(surfaceColor.copy(alpha = 0.95f), surfaceColor.copy(alpha = 0.55f))
                        },
                    ),
                  cornerRadius = androidx.compose.ui.geometry.CornerRadius(24f, 24f),
                )
                drawRoundRect(
                  color = if (isBlocked) extColors.unfortunate.copy(alpha = 0.5f) else borderColor,
                  cornerRadius = androidx.compose.ui.geometry.CornerRadius(24f, 24f),
                  style = Stroke(width = 2f),
                )
              }
              .pointerInput(lineIdx, isBlocked) {
                if (!isBlocked) {
                  detectTapGestures { offset ->
                    val xf = (offset.x / size.width.coerceAtLeast(1)).coerceIn(0f, 1f)
                    val yf = (offset.y / size.height.coerceAtLeast(1)).coerceIn(0f, 1f)
                    onStrikeSand(xf, yf)
                  }
                }
              },
          contentAlignment = Alignment.Center,
        ) {
          Canvas(modifier = Modifier.fillMaxSize()) {
            uiState.sandGrains.forEach { grain ->
              val isActiveLine = grain.lineIndex == lineIdx
              val color = if (isActiveLine) primaryColor else primaryColor.copy(alpha = 0.25f)
              drawCircle(
                color = color,
                radius = if (isActiveLine) 6.dp.toPx() else 3.dp.toPx(),
                center = Offset(grain.xFraction * size.width, grain.yFraction * size.height),
              )
            }
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
              imageVector = if (isBlocked) Icons.Rounded.Block else Icons.Rounded.TouchApp,
              contentDescription =
                if (isBlocked) "Tapé du sable désactivé"
                else "Toucher le sable pour marquer des points",
              tint =
                if (isBlocked) extColors.unfortunate.copy(alpha = 0.85f)
                else MaterialTheme.colorScheme.primary.copy(alpha = 0.75f),
              modifier = Modifier.size(28.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            val currentCount = uiState.sandLineCounts[lineIdx]
            val allCompleted = uiState.isSandCastingComplete
            Text(
              text =
                when {
                  isBlocked -> "Tapé du sable verrouillé (${uiState.ramlTimeAssessment.status.label})"
                  currentCount == 0 -> "Touche le sable librement sans compter"
                  !allCompleted -> "Frappe enregistrée · Parité pair/impair masquée"
                  else ->
                    "$currentCount traits · ${if (currentCount % 2 == 1) "• Impair (Fard)" else "•• Pair (Zawj)"}"
                },
              style = MaterialTheme.typography.bodySmall,
              color = if (isBlocked) extColors.unfortunate else MaterialTheme.colorScheme.onSurface,
              textAlign = TextAlign.Center,
              modifier = Modifier.padding(horizontal = 12.dp).testTag("sand_tap_status_text"),
            )
          }
        }

        val allCompleted = uiState.isSandCastingComplete
        val revealedMothers =
          remember(uiState.sandLineCounts, allCompleted) { uiState.revealedMotherFigures() }

        // Grille 4x4 des lignes des Mères (parité et noms masqués tant que 16/16 n'est pas atteint)
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          for (m in 0 until 4) {
            val filledInMother = (0 until 4).count { r -> uiState.sandLineCounts[m * 4 + r] > 0 }
            val revealedFig = revealedMothers?.getOrNull(m)
            Surface(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(10.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
            ) {
              Column(
                modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
              ) {
                Text(
                  text = "M${m + 1}",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.primary,
                )
                for (r in 0 until 4) {
                  val idx = m * 4 + r
                  val count = uiState.sandLineCounts[idx]
                  val isCurrent = idx == lineIdx
                  Surface(
                    modifier =
                      Modifier.fillMaxWidth().clickable { onSelectSandLine(idx) },
                    shape = RoundedCornerShape(6.dp),
                    color =
                      if (isCurrent) MaterialTheme.colorScheme.primary.copy(alpha = 0.22f)
                      else MaterialTheme.colorScheme.surface,
                    border =
                      if (isCurrent) BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                      else null,
                  ) {
                    Text(
                      text =
                        when {
                          count == 0 -> "—"
                          !allCompleted -> "✓"
                          count % 2 == 1 -> "•"
                          else -> "••"
                        },
                      style = MaterialTheme.typography.labelMedium,
                      color =
                        if (count == 0) MaterialTheme.colorScheme.onSurfaceVariant
                        else if (!allCompleted) extColors.fortunate
                        else MaterialTheme.colorScheme.primary,
                      modifier = Modifier.padding(vertical = 3.dp).fillMaxWidth(),
                      textAlign = TextAlign.Center,
                    )
                  }
                }
                Text(
                  text = revealedFig?.westAfricanName ?: "$filledInMother/4",
                  style = MaterialTheme.typography.labelSmall,
                  color =
                    if (revealedFig != null) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.testTag("mother_column_footer_$m"),
                )
              }
            }
          }
        }

        if (allCompleted && revealedMothers != null) {
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = extColors.fortunate.copy(alpha = 0.12f),
            border = BorderStroke(1.dp, extColors.fortunate.copy(alpha = 0.35f)),
            modifier = Modifier.fillMaxWidth().testTag("revealed_mothers_banner"),
          ) {
            Text(
              text =
                "4 Mères révélées : ${revealedMothers.mapIndexed { idx, fig -> "M${idx + 1} ${fig.westAfricanName}" }.joinToString(" · ")}",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.SemiBold,
              color = extColors.fortunate,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            )
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          TextButton(
            onClick = onResetSand,
            modifier = Modifier.testTag("reset_sand_button"),
          ) {
            Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.btn_reset_sand))
          }

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(
              onClick = onAutoCompleteSand,
              enabled = !allCompleted && !isBlocked,
              modifier = Modifier.testTag("autocomplete_sand_button"),
            ) {
              Text("Compléter")
            }
            OutlinedButton(
              onClick = onAdvanceLine,
              enabled = !isBlocked && uiState.sandLineCounts[lineIdx] > 0 && !allCompleted,
              modifier = Modifier.testTag("next_sand_line_button"),
              shape = RoundedCornerShape(10.dp),
            ) {
              Text(
                when {
                  allCompleted -> "16/16 ✓"
                  lineIdx < 15 -> "Suivante →"
                  else -> "Finir les vides"
                },
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun DirectMothersSection(
  mothers: List<GeomanticFigure>,
  onToggleRank: (Int, Int) -> Unit,
  onOpenFigurePicker: (Int) -> Unit,
) {
  val extColors = AlRamlColors.extended

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Text(
        text = "Touche un rang élémentaire pour basculer (• / ••) ou le nom de la Mère pour choisir parmi les 16 figures.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        mothers.forEachIndexed { motherIdx, fig ->
          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
            border = BorderStroke(1.dp, extColors.cardBorder),
          ) {
            Column(
              modifier = Modifier.padding(8.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
              Text(
                text = "M${motherIdx + 1}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
              )
              fig.ranks.forEachIndexed { rankIdx, parity ->
                val elem = Element.entries[rankIdx]
                Surface(
                  modifier =
                    Modifier.fillMaxWidth()
                      .height(34.dp)
                      .clickable { onToggleRank(motherIdx, rankIdx) }
                      .testTag("mother_${motherIdx}_rank_${rankIdx}"),
                  shape = RoundedCornerShape(8.dp),
                  color = extColors.forElement(elem).copy(alpha = 0.14f),
                  border = BorderStroke(1.dp, extColors.forElement(elem).copy(alpha = 0.35f)),
                ) {
                  Box(contentAlignment = Alignment.Center) {
                    Text(
                      text = if (parity == PointParity.ODD) "•" else "••",
                      style = MaterialTheme.typography.titleSmall,
                      color = extColors.forElement(elem),
                    )
                  }
                }
              }
              Text(
                text = fig.westAfricanName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier =
                  Modifier.clickable { onOpenFigurePicker(motherIdx) }
                    .padding(vertical = 4.dp),
              )
              Text(
                text = "Changer",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onOpenFigurePicker(motherIdx) },
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun RamlTimingProtectionCard(
  uiState: GeomancyUiState,
  onToggleForbidden: (Boolean) -> Unit,
  onToggleDiscouraged: (Boolean) -> Unit,
  onRefreshTiming: () -> Unit,
) {
  val extColors = AlRamlColors.extended
  val assessment = uiState.ramlTimeAssessment
  val accentColor =
    when (assessment.status) {
      RamlTimeStatus.FORBIDDEN -> extColors.unfortunate
      RamlTimeStatus.DISCOURAGED -> extColors.mixed
      RamlTimeStatus.FAVORABLE -> extColors.fortunate
    }
  val statusIcon =
    when (assessment.status) {
      RamlTimeStatus.FORBIDDEN -> Icons.Rounded.Lock
      RamlTimeStatus.DISCOURAGED -> Icons.Rounded.WarningAmber
      RamlTimeStatus.FAVORABLE -> Icons.Rounded.NightsStay
    }

  Surface(
    shape = RoundedCornerShape(14.dp),
    color = accentColor.copy(alpha = 0.10f),
    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.38f)),
    modifier = Modifier.fillMaxWidth().testTag("lunar_day_indicator"),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = accentColor,
            modifier = Modifier.size(18.dp),
          )
          Column {
            Text(
              text = "${assessment.status.label} · ${assessment.status.arabicLabel}",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = accentColor,
              modifier = Modifier.testTag("raml_timing_status_label"),
            )
            val clockPart =
              if (assessment.formattedClockTime.isNotEmpty()) "${assessment.formattedClockTime} · " else ""
            Text(
              text = "${clockPart}Jour lunaire ${assessment.lunarDay} · Heure de ${uiState.activePlanetHour.englishName}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
        IconButton(
          onClick = onRefreshTiming,
          modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp).testTag("refresh_raml_timing_button"),
        ) {
          Icon(
            imageVector = Icons.Rounded.Schedule,
            contentDescription = "Actualiser l’heure et le jour lunaire",
            tint = accentColor,
          )
        }
      }

      if (assessment.reasons.isNotEmpty()) {
        Text(
          text = assessment.reasons.joinToString("\n• ", prefix = "• "),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurface,
        )
      } else {
        Text(
          text = assessment.recommendedAction,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      // Contrôles d'activation / désactivation de la protection horaire
      Row(
        modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
          Text(
            text = "Désactiver le Raml aux temps & heures interdits",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = "Activé par défaut (Zawāl, lever/coucher, Al-Maḥāq, Samedi-Saturne, Mardi-Mars)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Switch(
          checked = uiState.disableRamlAtForbiddenTimes,
          onCheckedChange = onToggleForbidden,
          modifier = Modifier.testTag("disable_raml_forbidden_switch"),
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
          Text(
            text = "Désactiver aussi aux temps & heures déconseillés",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = "Bloque en plus Mars/Saturne, jours Ayyām al-Naḥs (Al-Fulk p. 31) et fin du mercredi",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Switch(
          checked = uiState.disableRamlAtDiscouragedTimes,
          onCheckedChange = onToggleDiscouraged,
          modifier = Modifier.testTag("disable_raml_discouraged_switch"),
        )
      }
    }
  }
}
