package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoGraph
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.geomancy.AccuracyCategoryStat
import com.example.geomancy.CalibrationReport
import com.example.geomancy.FigureWeightDelta
import com.example.geomancy.FigureWeightTable
import com.example.geomancy.MatchEvaluationDetail
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard
import kotlin.math.abs

@Composable
fun PredictiveDashboardSection(
  report: CalibrationReport,
  isCalibrationEnabled: Boolean,
  onToggleCalibrationEnabled: (Boolean) -> Unit,
  onApplyCalibration: () -> Unit,
  onResetCalibration: () -> Unit,
  onImportNov17Matches: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var dimensionIndex by remember { mutableIntStateOf(0) } // 0 = Ligue, 1 = Type de match
  var useCalibratedSeries by remember { mutableStateOf(true) }
  var selectedCategoryName by remember { mutableStateOf<String?>(null) }
  var showOnlyAdjustedWeights by remember { mutableStateOf(false) }

  val extColors = AlRamlColors.extended
  val successColor = extColors.fortunate
  val failureColor = extColors.unfortunate

  val activeCategories =
    remember(report, dimensionIndex) {
      if (dimensionIndex == 0) report.accuracyByLeague else report.accuracyByMatchType
    }

  val displayedWeights =
    remember(report.figureDeltas, showOnlyAdjustedWeights) {
      if (showOnlyAdjustedWeights) {
        report.figureDeltas.filter { abs(it.delta) >= 0.05 }.ifEmpty { report.figureDeltas }
      } else {
        report.figureDeltas
      }
    }

  val pairedEvaluations =
    remember(report.evaluationsBefore, report.evaluationsAfter) {
      val beforeById = report.evaluationsBefore.associateBy { it.sample.id }
      report.evaluationsAfter.map { after ->
        beforeById[after.sample.id] to after
      }
    }

  LazyColumn(
    modifier = modifier.fillMaxWidth(),
    contentPadding = PaddingValues(bottom = 18.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    // 1. Panneau de contrôle du calibrage
    item {
      LiquidGlassCard(
        modifier = Modifier.fillMaxWidth(),
        elevated = true,
        accentBorderColor = MaterialTheme.colorScheme.primary,
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.AutoGraph,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
            )
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Module d’Analyse Prédictive & Précision",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
              Text(
                text = "${report.totalSamples} thème(s) Duel évalué(s) · Règles Bulūgh al-Amal actives",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Poids calibrés dans les futurs pronostics",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
              )
              Text(
                text =
                  if (isCalibrationEnabled) {
                    "Actif : les 16 figures s’ajustent selon vos matchs archivés."
                  } else {
                    "Désactivé : table classique de Cheikh Al-Toukhi."
                  },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
              checked = isCalibrationEnabled,
              onCheckedChange = onToggleCalibrationEnabled,
              modifier = Modifier.testTag("calibration_enabled_switch"),
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Button(
              onClick = onImportNov17Matches,
              modifier = Modifier.weight(1f).testTag("import_nov17_button"),
              shape = RoundedCornerShape(12.dp),
            ) {
              Icon(Icons.Rounded.Download, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Importer 17-11 (10 matchs)", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            OutlinedButton(
              onClick = onApplyCalibration,
              enabled = report.totalSamples > 0,
              modifier = Modifier.testTag("apply_calibration_button"),
              shape = RoundedCornerShape(12.dp),
            ) {
              Icon(Icons.Rounded.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Calibrer")
            }
          }

          if (isCalibrationEnabled) {
            TextButton(
              onClick = onResetCalibration,
              modifier = Modifier.align(Alignment.End).testTag("reset_calibration_button"),
            ) {
              Icon(Icons.Rounded.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Réinitialiser aux poids d’origine Al-Toukhi")
            }
          }
        }
      }
    }

    if (report.totalSamples == 0) {
      item {
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
          Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Text(
              text = "Aucune rencontre réelle encore archivée",
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.primary,
              textAlign = TextAlign.Center,
            )
            Text(
              text =
                "Enregistre un thème de domaine Duel avec le score réel observé, ou importe les 10 matchs vérifiés du 17-11-2026 pour lancer le calibrage et visualiser le tableau de précision.",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              textAlign = TextAlign.Center,
            )
          }
        }
      }
      return@LazyColumn
    }

    // 2. Jauges globales Réussites vs Échecs (Avant vs Après calibrage)
    item {
      AccuracyOverviewCard(
        report = report,
        successColor = successColor,
        failureColor = failureColor,
      )
    }

    // 3. Tableau de bord graphique par Ligue ou par Type de match
    item {
      LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            text = "Tableau de Précision : Réussites vs Échecs",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
          )

          SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
              selected = dimensionIndex == 0,
              onClick = {
                dimensionIndex = 0
                selectedCategoryName = null
              },
              shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
              modifier = Modifier.testTag("dashboard_dim_league"),
            ) {
              Text("Par Ligue", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            SegmentedButton(
              selected = dimensionIndex == 1,
              onClick = {
                dimensionIndex = 1
                selectedCategoryName = null
              },
              shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
              modifier = Modifier.testTag("dashboard_dim_type"),
            ) {
              Text("Par Type de Match", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            FilterChip(
              selected = useCalibratedSeries,
              onClick = { useCalibratedSeries = true },
              label = { Text("Après calibrage (${report.calibratedAccuracyPercent}%)") },
            )
            FilterChip(
              selected = !useCalibratedSeries,
              onClick = { useCalibratedSeries = false },
              label = { Text("Base Al-Toukhi (${report.baselineAccuracyPercent}%)") },
            )
          }

          Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            LegendDotLabel(color = successColor, label = "Réussites")
            LegendDotLabel(color = failureColor, label = "Échecs")
          }

          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            activeCategories.forEach { stat ->
              CategoryAccuracyBarRow(
                stat = stat,
                useCalibrated = useCalibratedSeries,
                isSelected = selectedCategoryName == stat.categoryName,
                successColor = successColor,
                failureColor = failureColor,
                onClick = {
                  selectedCategoryName =
                    if (selectedCategoryName == stat.categoryName) null else stat.categoryName
                },
              )
            }
          }

          selectedCategoryName?.let { chosen ->
            val stat = activeCategories.find { it.categoryName == chosen }
            if (stat != null) {
              val ok = if (useCalibratedSeries) stat.calibratedSuccessCount else stat.baselineSuccessCount
              val ko = if (useCalibratedSeries) stat.calibratedFailCount else stat.baselineFailCount
              val pct = if (useCalibratedSeries) stat.calibratedAccuracyPercent else stat.baselineAccuracyPercent
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
              ) {
                Text(
                  text =
                    "${stat.categoryName} : $ok réussite(s) vs $ko échec(s) sur ${stat.totalMatches} match(s) ($pct% de précision).",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.fillMaxWidth().padding(10.dp),
                )
              }
            }
          }
        }
      }
    }

    // 4. Ajustements des poids des 16 figures
    item {
      LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
              text = "Poids des 16 Figures",
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.primary,
            )
            FilterChip(
              selected = showOnlyAdjustedWeights,
              onClick = { showOnlyAdjustedWeights = !showOnlyAdjustedWeights },
              label = { Text(if (showOnlyAdjustedWeights) "Modifiées" else "Toutes (16)") },
            )
          }
          Text(
            text = "Comparaison entre le poids de base d’Al-Toukhi et le poids calibré automatiquement sur vos matchs.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
          displayedWeights.forEach { delta ->
            FigureWeightDeltaRow(
              delta = delta,
              positiveColor = successColor,
              negativeColor = failureColor,
            )
          }
        }
      }
    }

    // 5. Détail comparatif match par match
    item {
      Text(
        text = "Comparatif Match par Match",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 4.dp),
      )
    }

    items(pairedEvaluations, key = { (_, after) -> after.sample.id }) { (before, after) ->
      MatchEvaluationRowCard(
        before = before,
        after = after,
        successColor = successColor,
        failureColor = failureColor,
      )
    }
  }
}

@Composable
private fun AccuracyOverviewCard(
  report: CalibrationReport,
  successColor: Color,
  failureColor: Color,
) {
  val baseSweep by animateFloatAsState(
    targetValue = (report.baselineAccuracyPercent.coerceIn(0, 100) / 100f) * 360f,
    animationSpec = tween(650),
    label = "baseline_arc",
  )
  val calSweep by animateFloatAsState(
    targetValue = (report.calibratedAccuracyPercent.coerceIn(0, 100) / 100f) * 360f,
    animationSpec = tween(750),
    label = "calibrated_arc",
  )

  LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
      Text(
        text = "Taux de Précision Global (Réussites vs Échecs)",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        DonutGaugeTile(
          title = "Base Al-Toukhi",
          percent = report.baselineAccuracyPercent,
          correct = report.baselineCorrectCount,
          total = report.totalSamples,
          sweepAngle = baseSweep,
          ringColor = MaterialTheme.colorScheme.secondary,
          trackColor = failureColor.copy(alpha = 0.25f),
          modifier = Modifier.weight(1f),
        )
        DonutGaugeTile(
          title = "Modèle Calibré",
          percent = report.calibratedAccuracyPercent,
          correct = report.calibratedCorrectCount,
          total = report.totalSamples,
          sweepAngle = calSweep,
          ringColor = successColor,
          trackColor = failureColor.copy(alpha = 0.25f),
          modifier = Modifier.weight(1f),
        )
      }
    }
  }
}

@Composable
private fun DonutGaugeTile(
  title: String,
  percent: Int,
  correct: Int,
  total: Int,
  sweepAngle: Float,
  ringColor: Color,
  trackColor: Color,
  modifier: Modifier = Modifier,
) {
  val failures = total - correct
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(14.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
    border = BorderStroke(1.dp, AlRamlColors.extended.cardBorder),
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      Box(contentAlignment = Alignment.Center, modifier = Modifier.size(86.dp)) {
        Canvas(
          modifier =
            Modifier.size(82.dp).semantics {
              contentDescription = "$title : $percent% de précision, $correct réussites et $failures échecs"
            },
        ) {
          val strokeWidth = 10.dp.toPx()
          val inset = strokeWidth / 2f
          val arcSize = Size(size.width - strokeWidth, size.height - strokeWidth)
          drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(inset, inset),
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
          )
          drawArc(
            color = ringColor,
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(inset, inset),
            size = arcSize,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
          )
        }
        Text(
          text = "$percent%",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = ringColor,
        )
      }
      Text(
        text = "$correct ✅ · $failures ❌",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}

@Composable
private fun LegendDotLabel(color: Color, label: String) {
  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
    Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
    Text(text = label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
  }
}

@Composable
private fun CategoryAccuracyBarRow(
  stat: AccuracyCategoryStat,
  useCalibrated: Boolean,
  isSelected: Boolean,
  successColor: Color,
  failureColor: Color,
  onClick: () -> Unit,
) {
  val successCount = if (useCalibrated) stat.calibratedSuccessCount else stat.baselineSuccessCount
  val failCount = if (useCalibrated) stat.calibratedFailCount else stat.baselineFailCount
  val accuracyPct = if (useCalibrated) stat.calibratedAccuracyPercent else stat.baselineAccuracyPercent
  val successFraction by animateFloatAsState(
    targetValue = if (stat.totalMatches == 0) 0f else successCount.toFloat() / stat.totalMatches.toFloat(),
    animationSpec = tween(550),
    label = "bar_${stat.categoryName}",
  )

  Surface(
    modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
    shape = RoundedCornerShape(12.dp),
    color =
      if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
      else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
    border =
      BorderStroke(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) MaterialTheme.colorScheme.primary else AlRamlColors.extended.cardBorder,
      ),
  ) {
    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = stat.categoryName,
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.weight(1f),
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text = "$successCount/${stat.totalMatches} · $accuracyPct%",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = if (accuracyPct >= 60) successColor else failureColor,
        )
      }

      Canvas(
        modifier =
          Modifier.fillMaxWidth().height(16.dp).semantics {
            contentDescription =
              "${stat.categoryName} : $successCount réussites, $failCount échecs, $accuracyPct%"
          },
      ) {
        val w = size.width
        val h = size.height
        val radius = CornerRadius(h / 2f, h / 2f)
        // Fond total (échecs)
        drawRoundRect(
          color = failureColor.copy(alpha = 0.72f),
          topLeft = Offset.Zero,
          size = Size(w, h),
          cornerRadius = radius,
        )
        // Barre de réussites par-dessus
        if (successFraction > 0f) {
          drawRoundRect(
            color = successColor,
            topLeft = Offset.Zero,
            size = Size((w * successFraction).coerceAtLeast(h), h),
            cornerRadius = radius,
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(
          text = "Réussites : $successCount",
          style = MaterialTheme.typography.labelSmall,
          color = successColor,
        )
        Text(
          text = "Échecs : $failCount",
          style = MaterialTheme.typography.labelSmall,
          color = failureColor,
        )
      }
    }
  }
}

@Composable
private fun FigureWeightDeltaRow(
  delta: FigureWeightDelta,
  positiveColor: Color,
  negativeColor: Color,
) {
  val baseStr = FigureWeightTable.formatWeight(delta.baselineWeight)
  val calStr = FigureWeightTable.formatWeight(delta.calibratedWeight)
  val diff = delta.delta
  val diffLabel =
    when {
      diff >= 0.05 -> "+${FigureWeightTable.formatWeight(diff)}"
      diff <= -0.05 -> FigureWeightTable.formatWeight(diff)
      else -> "="
    }
  val diffColor =
    when {
      diff >= 0.05 -> positiveColor
      diff <= -0.05 -> negativeColor
      else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    GeomanticFigureGlyph(figure = delta.figure, size = 22.dp)
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = delta.figure.westAfricanName,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
      Text(
        text = "Base $baseStr → Calibré $calStr (${delta.appearancesInSamples} apparitions)",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }

    // Mini barre symétrique centrée sur 0 (-5..+5)
    Canvas(modifier = Modifier.width(68.dp).height(12.dp)) {
      val center = size.width / 2f
      drawLine(
        color = Color.Gray.copy(alpha = 0.45f),
        start = Offset(center, 0f),
        end = Offset(center, size.height),
        strokeWidth = 1.5.dp.toPx(),
      )
      val norm = (delta.calibratedWeight / FigureWeightTable.MAX_WEIGHT).toFloat().coerceIn(-1f, 1f)
      val barWidth = abs(norm) * (center - 2f)
      val startX = if (norm >= 0f) center else center - barWidth
      drawRoundRect(
        color = if (norm >= 0f) positiveColor else negativeColor,
        topLeft = Offset(startX, 2.dp.toPx()),
        size = Size(barWidth.coerceAtLeast(2f), size.height - 4.dp.toPx()),
        cornerRadius = CornerRadius(4f, 4f),
      )
    }

    Text(
      text = diffLabel,
      style = MaterialTheme.typography.labelMedium,
      fontWeight = FontWeight.Bold,
      color = diffColor,
      modifier = Modifier.width(38.dp),
      textAlign = TextAlign.End,
    )
  }
}

@Composable
private fun MatchEvaluationRowCard(
  before: MatchEvaluationDetail?,
  after: MatchEvaluationDetail,
  successColor: Color,
  failureColor: Color,
) {
  val statusColor = if (after.isCorrect) successColor else failureColor
  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth(),
    accentBorderColor = statusColor.copy(alpha = 0.7f),
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = after.sample.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          Text(
            text = "${after.sample.leagueOrType} · Score réel : ${after.sample.scoreLabel} (${after.sample.actualOutcome.shortLabel})",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Icon(
          imageVector = if (after.isCorrect) Icons.Rounded.CheckCircle else Icons.Rounded.Close,
          contentDescription = if (after.isCorrect) "Prédiction réussie" else "Prédiction échouée",
          tint = statusColor,
          modifier = Modifier.size(20.dp),
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        if (before != null) {
          Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
          ) {
            Text(
              text = "Base : ${before.predictedOutcome.shortLabel} (${if (before.isCorrect) "✓" else "✗"})",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            )
          }
        }
        Surface(
          modifier = Modifier.weight(1f),
          shape = RoundedCornerShape(8.dp),
          color = statusColor.copy(alpha = 0.14f),
        ) {
          Text(
            text = "Calibré : ${after.predictedOutcome.shortLabel} (${if (after.isCorrect) "Réussite" else "Échec"})",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = statusColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
          )
        }
      }

      Text(
        text =
          "Juge M15 : ${after.judgeFigure.westAfricanName} · Issue M16 : ${after.outcome16Figure.westAfricanName} · " +
            "Score duel ${FigureWeightTable.formatWeight(after.sideAScore)} vs ${FigureWeightTable.formatWeight(after.sideBScore)}",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}
