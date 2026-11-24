package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.TrendingUp
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geomancy.ArchiveFrequencyReport
import com.example.geomancy.Element
import com.example.geomancy.FigureFrequencyScope
import com.example.geomancy.FigureFrequencyStat
import com.example.geomancy.FigureTrendStatus
import com.example.geomancy.FortunePolarity
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard
import java.util.Locale
import kotlin.math.ceil

@Composable
fun ArchiveFrequencyChartSection(
  report: ArchiveFrequencyReport,
  modifier: Modifier = Modifier,
) {
  var selectedScope by remember { mutableStateOf(FigureFrequencyScope.ALL_HOUSES) }
  var sortByFrequency by remember { mutableStateOf(true) }
  var colorByElement by remember { mutableStateOf(false) }
  var selectedFigureId by remember { mutableIntStateOf(-1) }

  val extColors = AlRamlColors.extended

  val orderedStats =
    remember(report, selectedScope, sortByFrequency) {
      val base = report.statsByFigure
      if (sortByFrequency) {
        base.sortedWith(
          compareByDescending<FigureFrequencyStat> { it.countForScope(selectedScope) }
            .thenByDescending { it.totalAppearances }
            .thenBy { it.figure.westAfricanOrder },
        )
      } else {
        base
      }
    }

  LaunchedEffect(report, selectedScope) {
    if (selectedFigureId == -1 || orderedStats.none { it.figure.id == selectedFigureId }) {
      selectedFigureId = orderedStats.firstOrNull()?.figure?.id ?: -1
    }
  }

  val activeStat =
    orderedStats.find { it.figure.id == selectedFigureId } ?: orderedStats.firstOrNull()

  if (report.totalThemes == 0) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
      LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(
          modifier = Modifier.padding(22.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Icon(
            imageVector = Icons.Rounded.BarChart,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(38.dp),
          )
          Text(
            text = "Aucune archive à analyser",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
          )
          Text(
            text =
              "Enregistre des tirages dans les Archives pour visualiser la fréquence des 16 figures géomantiques et identifier les tendances dominantes.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
          )
        }
      }
    }
    return
  }

  LazyColumn(
    modifier = modifier.fillMaxWidth().testTag("archive_frequency_list"),
    contentPadding = PaddingValues(bottom = 18.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    // 1. Synthèse des tendances détectées
    item {
      LiquidGlassCard(
        modifier = Modifier.fillMaxWidth(),
        elevated = true,
        accentBorderColor = MaterialTheme.colorScheme.primary,
      ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Rounded.TrendingUp,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
            )
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Fréquence & Tendances des 16 Figures",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
              Text(
                text = "${report.totalThemes} tirage(s) · ${report.totalHouseSlots} maisons analysées",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.52f),
          ) {
            Text(
              text = report.trendSummary,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.fillMaxWidth().padding(10.dp),
            )
          }

          if (report.topFigures.isNotEmpty() || report.topVerdictFigures.isNotEmpty()) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              HighlightRankCard(
                title = "Top Global (M1–M16)",
                items =
                  report.topFigures.map { stat ->
                    "${stat.figure.westAfricanName} (${stat.totalAppearances}×)"
                  },
                modifier = Modifier.weight(1f),
              )
              HighlightRankCard(
                title = "Juges & Issues (M15–16)",
                items =
                  report.topVerdictFigures.map { stat ->
                    "${stat.figure.westAfricanName} (${stat.judgeAndOutcomeAppearances}×)"
                  },
                modifier = Modifier.weight(1f),
              )
            }
          }
        }
      }
    }

    // 2. Graphique interactif à barres des 16 figures (style Recharts)
    item {
      LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Diagramme d’Apparition des 16 Figures",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
          )

          SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            FigureFrequencyScope.entries.forEachIndexed { index, scope ->
              SegmentedButton(
                selected = selectedScope == scope,
                onClick = { selectedScope = scope },
                shape =
                  SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = FigureFrequencyScope.entries.size,
                  ),
                modifier = Modifier.testTag("freq_scope_${scope.name.lowercase()}"),
              ) {
                Text(
                  text =
                    when (scope) {
                      FigureFrequencyScope.ALL_HOUSES -> "Toutes"
                      FigureFrequencyScope.MOTHERS_ONLY -> "Mères"
                      FigureFrequencyScope.JUDGES_AND_OUTCOMES -> "M15–M16"
                    },
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                )
              }
            }
          }

          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            FilterChip(
              selected = sortByFrequency,
              onClick = { sortByFrequency = !sortByFrequency },
              label = {
                Text(if (sortByFrequency) "Tri : Fréquence ↓" else "Tri : Ordre 1–16")
              },
              modifier = Modifier.testTag("freq_sort_chip"),
            )
            FilterChip(
              selected = colorByElement,
              onClick = { colorByElement = !colorByElement },
              label = {
                Text(if (colorByElement) "Couleur : Élément" else "Couleur : Polarité")
              },
              modifier = Modifier.testTag("freq_color_chip"),
            )
          }

          // Légende
          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            if (colorByElement) {
              Element.entries.forEach { el ->
                ChartLegendItem(color = extColors.forElement(el), label = el.englishName)
              }
            } else {
              ChartLegendItem(color = extColors.fortunate, label = "Faste")
              ChartLegendItem(color = extColors.mixed, label = "Mixte")
              ChartLegendItem(color = extColors.unfortunate, label = "Défavorable")
            }
            ChartLegendItem(color = MaterialTheme.colorScheme.primary, label = "Ligne moy. 1/16")
          }

          val scopeSlots =
            when (selectedScope) {
              FigureFrequencyScope.ALL_HOUSES -> report.totalHouseSlots
              FigureFrequencyScope.MOTHERS_ONLY -> report.totalThemes * 4
              FigureFrequencyScope.JUDGES_AND_OUTCOMES -> report.totalThemes * 2
            }
          val expectedAverageCount = scopeSlots / 16f

          FigureFrequencyBarChartCanvas(
            stats = orderedStats,
            scope = selectedScope,
            expectedAverage = expectedAverageCount,
            selectedFigureId = activeStat?.figure?.id ?: -1,
            colorForStat = { stat ->
              if (colorByElement) {
                extColors.forElement(stat.figure.dominantElement)
              } else {
                extColors.forPolarity(stat.figure.polarity)
              }
            },
            onSelectFigure = { selectedFigureId = it.figure.id },
          )

          // Tooltip Recharts pour la figure sélectionnée
          activeStat?.let { stat ->
            val scopeCount = stat.countForScope(selectedScope)
            val scopeShare =
              if (scopeSlots > 0) (scopeCount * 100.0) / scopeSlots else 0.0
            val barColor =
              if (colorByElement) extColors.forElement(stat.figure.dominantElement)
              else extColors.forPolarity(stat.figure.polarity)

            Surface(
              shape = RoundedCornerShape(14.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
              border = BorderStroke(1.2.dp, barColor.copy(alpha = 0.55f)),
              modifier = Modifier.fillMaxWidth().testTag("freq_tooltip_card"),
            ) {
              Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                ) {
                  GeomanticFigureGlyph(figure = stat.figure, size = 28.dp)
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = "${stat.figure.westAfricanOrder}. ${stat.figure.westAfricanName}",
                      style = MaterialTheme.typography.titleSmall,
                      color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                      text = "${stat.figure.arabicName} (${stat.figure.code}) · ${stat.figure.dominantElement.englishName} · ${stat.figure.polarity.label}",
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                  }
                  TrendBadge(status = stat.trendStatus)
                }

                Text(
                  text =
                    String.format(
                      Locale.FRENCH,
                      "%s : %d apparition(s) (%.1f%% contre 6,25%% attendu)",
                      selectedScope.label,
                      scopeCount,
                      scopeShare,
                    ),
                  style = MaterialTheme.typography.bodySmall,
                  fontWeight = FontWeight.SemiBold,
                  color = barColor,
                )

                Text(
                  text =
                    "Mères (M1–M4) : ${stat.motherAppearances} · Dérivées (M5–M14) : ${stat.derivedAppearances} · Juge/Issue (M15–M16) : ${stat.judgeAndOutcomeAppearances}",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
              }
            }
          }
        }
      }
    }

    // 3. Répartition des Éléments & Polarités dans les archives
    item {
      LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
          Text(
            text = "Équilibre Élémentaire & Polarité des Archives",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
          )
          StackedDistributionRow(
            title = "Quatre Éléments",
            segments =
              Element.entries.map { el ->
                val count = report.elementDistribution[el] ?: 0
                Triple(el.englishName, count, extColors.forElement(el))
              },
            total = report.totalHouseSlots,
          )
          StackedDistributionRow(
            title = "Polarités des Maisons",
            segments =
              listOf(
                Triple("Faste", report.polarityDistribution[FortunePolarity.FORTUNATE] ?: 0, extColors.fortunate),
                Triple("Mixte", report.polarityDistribution[FortunePolarity.MIXED] ?: 0, extColors.mixed),
                Triple("Défavorable", report.polarityDistribution[FortunePolarity.UNFORTUNATE] ?: 0, extColors.unfortunate),
              ),
            total = report.totalHouseSlots,
          )
          Text(
            text =
              "Note de « Bulūgh al-Amal » : la répétition d’une figure dans les Awtād (M1, M4, M7, M10) et dans les Mawāzīn (M12, M14, M15, M16) amplifie sa signature sur la tendance générale.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }

    // 4. Liste détaillée cliquable des 16 figures
    item {
      Text(
        text = "Détail par Figure (Appuie pour inspecter)",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 4.dp),
      )
    }

    items(orderedStats, key = { it.figure.id }) { stat ->
      FigureFrequencyRowCard(
        stat = stat,
        scope = selectedScope,
        isSelected = stat.figure.id == activeStat?.figure?.id,
        accentColor =
          if (colorByElement) extColors.forElement(stat.figure.dominantElement)
          else extColors.forPolarity(stat.figure.polarity),
        onClick = { selectedFigureId = stat.figure.id },
      )
    }
  }
}

@Composable
private fun FigureFrequencyBarChartCanvas(
  stats: List<FigureFrequencyStat>,
  scope: FigureFrequencyScope,
  expectedAverage: Float,
  selectedFigureId: Int,
  colorForStat: (FigureFrequencyStat) -> Color,
  onSelectFigure: (FigureFrequencyStat) -> Unit,
) {
  val textMeasurer = rememberTextMeasurer()
  val gridColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.55f)
  val avgLineColor = MaterialTheme.colorScheme.primary
  val axisLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
  val selectionBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)

  val rawMax = stats.maxOfOrNull { it.countForScope(scope) } ?: 0
  val maxScale =
    maxOf(rawMax.toFloat(), expectedAverage, 1f).let { peak ->
      ceil(peak.toDouble()).toFloat()
    }

  val animatedFractions =
    stats.map { stat ->
      val value = stat.countForScope(scope).toFloat()
      val frac by animateFloatAsState(
        targetValue = (value / maxScale).coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 480),
        label = "freq_bar_${stat.figure.id}",
      )
      frac
    }

  Canvas(
    modifier =
      Modifier
        .fillMaxWidth()
        .height(210.dp)
        .testTag("archive_frequency_bar_chart")
        .semantics {
          contentDescription =
            "Graphique de fréquence des 16 figures géomantiques (${scope.label})"
        }
        .pointerInput(stats) {
          detectTapGestures { offset ->
            val leftMargin = 28.dp.toPx()
            val chartWidth = (size.width - leftMargin - 6.dp.toPx()).coerceAtLeast(1f)
            if (offset.x >= leftMargin && stats.isNotEmpty()) {
              val slotWidth = chartWidth / stats.size
              val index = ((offset.x - leftMargin) / slotWidth).toInt().coerceIn(0, stats.lastIndex)
              onSelectFigure(stats[index])
            }
          }
        },
  ) {
    val leftMargin = 28.dp.toPx()
    val rightMargin = 6.dp.toPx()
    val topMargin = 14.dp.toPx()
    val bottomMargin = 26.dp.toPx()
    val chartWidth = (size.width - leftMargin - rightMargin).coerceAtLeast(1f)
    val chartHeight = (size.height - topMargin - bottomMargin).coerceAtLeast(1f)
    val bottomY = topMargin + chartHeight

    // Lignes horizontales de grille Recharts (0, 50%, 100%)
    val gridSteps = listOf(0f, 0.5f, 1f)
    val dashEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f), 0f)
    gridSteps.forEach { step ->
      val y = bottomY - (chartHeight * step)
      drawLine(
        color = gridColor,
        start = Offset(leftMargin, y),
        end = Offset(leftMargin + chartWidth, y),
        strokeWidth = 1.dp.toPx(),
        pathEffect = if (step > 0f) dashEffect else null,
      )
      val tickLabel = (maxScale * step).let { v ->
        if (v == v.toInt().toFloat()) v.toInt().toString()
        else String.format(Locale.US, "%.1f", v)
      }
      val measured =
        textMeasurer.measure(
          text = tickLabel,
          style = TextStyle(color = axisLabelColor, fontSize = 10.sp),
        )
      drawText(
        textLayoutResult = measured,
        topLeft = Offset(2.dp.toPx(), (y - measured.size.height / 2f).coerceAtLeast(0f)),
      )
    }

    // Ligne de moyenne théorique (1/16)
    if (expectedAverage > 0f && expectedAverage <= maxScale) {
      val avgY = bottomY - ((expectedAverage / maxScale) * chartHeight)
      drawLine(
        color = avgLineColor.copy(alpha = 0.85f),
        start = Offset(leftMargin, avgY),
        end = Offset(leftMargin + chartWidth, avgY),
        strokeWidth = 1.6.dp.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 6f), 0f),
      )
    }

    // 16 barres verticales
    if (stats.isNotEmpty()) {
      val slotWidth = chartWidth / stats.size
      val barWidth = (slotWidth * 0.62f).coerceIn(6.dp.toPx(), 20.dp.toPx())

      stats.forEachIndexed { index, stat ->
        val slotLeft = leftMargin + (index * slotWidth)
        val centerX = slotLeft + slotWidth / 2f
        val barLeft = centerX - barWidth / 2f
        val isSelected = stat.figure.id == selectedFigureId

        if (isSelected) {
          drawRoundRect(
            color = selectionBgColor,
            topLeft = Offset(slotLeft + 1.dp.toPx(), topMargin),
            size = Size((slotWidth - 2.dp.toPx()).coerceAtLeast(4f), chartHeight),
            cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx()),
          )
        }

        val count = stat.countForScope(scope)
        val barHeight =
          if (count == 0) 3.dp.toPx()
          else (animatedFractions[index] * chartHeight).coerceAtLeast(6.dp.toPx())
        val barTop = bottomY - barHeight
        val fillColor =
          if (count == 0) axisLabelColor.copy(alpha = 0.25f) else colorForStat(stat)

        drawRoundRect(
          color = fillColor,
          topLeft = Offset(barLeft, barTop),
          size = Size(barWidth, barHeight),
          cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
        )

        if (isSelected) {
          drawRoundRect(
            color = avgLineColor,
            topLeft = Offset(barLeft, barTop),
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
            style = Stroke(width = 1.6.dp.toPx()),
          )
        }

        // Index sur l'axe X (numéro ouest-africain 1..16)
        val xLabel = "${stat.figure.westAfricanOrder}"
        val xMeasured =
          textMeasurer.measure(
            text = xLabel,
            style =
              TextStyle(
                color = if (isSelected) avgLineColor else axisLabelColor,
                fontSize = 9.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              ),
          )
        drawText(
          textLayoutResult = xMeasured,
          topLeft = Offset(centerX - xMeasured.size.width / 2f, bottomY + 6.dp.toPx()),
        )
      }
    }
  }
}

@Composable
private fun HighlightRankCard(
  title: String,
  items: List<String>,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
    border = BorderStroke(1.dp, AlRamlColors.extended.cardBorder),
  ) {
    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
      )
      if (items.isEmpty()) {
        Text(
          text = "—",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      } else {
        items.forEachIndexed { idx, line ->
          Text(
            text = "${idx + 1}. $line",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }
      }
    }
  }
}

@Composable
private fun StackedDistributionRow(
  title: String,
  segments: List<Triple<String, Int, Color>>,
  total: Int,
) {
  Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelLarge,
      color = MaterialTheme.colorScheme.onSurface,
    )
    Canvas(
      modifier =
        Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(999.dp)),
    ) {
      if (total <= 0) {
        drawRect(color = Color.Gray.copy(alpha = 0.2f), size = size)
      } else {
        var currentX = 0f
        segments.forEach { (_, count, color) ->
          val segW = (count.toFloat() / total.toFloat()) * size.width
          if (segW > 0f) {
            drawRect(
              color = color,
              topLeft = Offset(currentX, 0f),
              size = Size(segW, size.height),
            )
            currentX += segW
          }
        }
      }
    }
    Row(
      modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      segments.forEach { (name, count, color) ->
        val pct = if (total > 0) ((count * 100.0) / total) else 0.0
        ChartLegendItem(
          color = color,
          label = String.format(Locale.FRENCH, "%s %d (%.0f%%)", name, count, pct),
        )
      }
    }
  }
}

@Composable
private fun FigureFrequencyRowCard(
  stat: FigureFrequencyStat,
  scope: FigureFrequencyScope,
  isSelected: Boolean,
  accentColor: Color,
  onClick: () -> Unit,
) {
  val count = stat.countForScope(scope)
  LiquidGlassCard(
    modifier =
      Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .testTag("freq_row_${stat.figure.id}"),
    accentBorderColor = if (isSelected) MaterialTheme.colorScheme.primary else null,
    elevated = isSelected,
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      GeomanticFigureGlyph(figure = stat.figure, size = 24.dp)
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "${stat.figure.westAfricanOrder}. ${stat.figure.westAfricanName}",
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurface,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text =
            String.format(
              Locale.FRENCH,
              "%s · Mères %d · M15–16 %d · Part globale %.1f%%",
              stat.figure.arabicName,
              stat.motherAppearances,
              stat.judgeAndOutcomeAppearances,
              stat.sharePercent,
            ),
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "${count}×",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = if (count > 0) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TrendBadge(status = stat.trendStatus)
      }
    }
  }
}

@Composable
private fun TrendBadge(status: FigureTrendStatus) {
  val extColors = AlRamlColors.extended
  val color =
    when (status) {
      FigureTrendStatus.DOMINANT -> MaterialTheme.colorScheme.primary
      FigureTrendStatus.ABOVE_AVERAGE -> extColors.fortunate
      FigureTrendStatus.BALANCED -> extColors.mixed
      FigureTrendStatus.RARE -> extColors.unfortunate
      FigureTrendStatus.ABSENT -> MaterialTheme.colorScheme.onSurfaceVariant
    }
  Surface(
    shape = RoundedCornerShape(999.dp),
    color = color.copy(alpha = 0.14f),
    border = BorderStroke(1.dp, color.copy(alpha = 0.35f)),
  ) {
    Text(
      text = status.label,
      style = MaterialTheme.typography.labelSmall,
      color = color,
      modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
    )
  }
}

@Composable
private fun ChartLegendItem(color: Color, label: String) {
  Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
    Box(modifier = Modifier.size(9.dp).clip(CircleShape).background(color))
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}
