package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.geomancy.ChartHouse
import com.example.geomancy.CourseTakhtExample
import com.example.geomancy.GeomanticFigure
import com.example.ui.theme.AlRamlColors

/**
 * Aperçu interactif du Takht à 16 maisons dans un module de cours, respectant la disposition
 * traditionnelle M8..M1 / M12..M9 / M14..M13 / M15..M16.
 */
@Composable
fun CourseTakhtPreview(
  moduleId: String,
  example: CourseTakhtExample,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
  onOpenTakhtExample: (CourseTakhtExample) -> Unit,
  modifier: Modifier = Modifier,
) {
  val reading = remember(example) { example.computeReading() }
  val extColors = AlRamlColors.extended

  Surface(
    modifier = modifier.fillMaxWidth().testTag("course_takht_preview_$moduleId"),
    shape = RoundedCornerShape(14.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
    border = BorderStroke(1.dp, extColors.cardBorder),
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.GridView,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(18.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = example.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
          )
          Text(
            text = "Source : ${example.sourceRef}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }

      // Ligne 1 : M8 -> M1 (droite vers gauche, disposition traditionnelle)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        (8 downTo 1).forEach { num ->
          val house = reading.house(num)
          MiniHouseCell(
            house = house,
            onClick = { onOpenFigureDetail(house.figure) },
            modifier = Modifier.weight(1f),
          )
        }
      }

      // Ligne 2 : M12 -> M9
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        Spacer(modifier = Modifier.weight(0.5f))
        (12 downTo 9).forEach { num ->
          val house = reading.house(num)
          MiniHouseCell(
            house = house,
            onClick = { onOpenFigureDetail(house.figure) },
            modifier = Modifier.weight(1f),
          )
        }
        Spacer(modifier = Modifier.weight(0.5f))
      }

      // Ligne 3 : M14 -> M13
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Spacer(modifier = Modifier.weight(1f))
        listOf(14, 13).forEach { num ->
          val house = reading.house(num)
          MiniHouseCell(
            house = house,
            onClick = { onOpenFigureDetail(house.figure) },
            modifier = Modifier.weight(1.2f),
          )
        }
        Spacer(modifier = Modifier.weight(1f))
      }

      // Ligne 4 : M15 (Juge) & M16 (Issue)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Spacer(modifier = Modifier.weight(1f))
        listOf(15, 16).forEach { num ->
          val house = reading.house(num)
          MiniHouseCell(
            house = house,
            highlight = true,
            onClick = { onOpenFigureDetail(house.figure) },
            modifier = Modifier.weight(1.35f),
          )
        }
        Spacer(modifier = Modifier.weight(1f))
      }

      Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        example.interpretationSteps.forEach { step ->
          Text(
            text = "• $step",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
          )
        }
      }

      Button(
        onClick = { onOpenTakhtExample(example) },
        modifier =
          Modifier.fillMaxWidth()
            .minimumInteractiveComponentSize()
            .testTag("course_open_takht_$moduleId"),
      ) {
        Icon(
          imageVector = Icons.AutoMirrored.Rounded.OpenInNew,
          contentDescription = null,
          modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Ouvrir dans le Takht complet")
      }
    }
  }
}

@Composable
private fun MiniHouseCell(
  house: ChartHouse,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  highlight: Boolean = false,
) {
  val extColors = AlRamlColors.extended
  val accentColor = extColors.forElement(house.figure.dominantElement)
  Surface(
    modifier =
      modifier
        .minimumInteractiveComponentSize()
        .clickable(onClick = onClick),
    shape = RoundedCornerShape(8.dp),
    color =
      if (highlight) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.72f)
      else MaterialTheme.colorScheme.surface.copy(alpha = 0.75f),
    border =
      BorderStroke(
        width = if (highlight) 1.2.dp else 0.8.dp,
        color = if (highlight) MaterialTheme.colorScheme.primary else accentColor.copy(alpha = 0.4f),
      ),
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {
      Text(
        text = "M${house.info.number}",
        style = MaterialTheme.typography.labelSmall,
        fontWeight = if (highlight) FontWeight.Bold else FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        maxLines = 1,
      )
      GeomanticFigureGlyph(
        figure = house.figure,
        size = 18.dp,
        useElementColors = true,
      )
      Spacer(modifier = Modifier.height(1.dp))
      Text(
        text = house.figure.westAfricanName.substringBefore(" "),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
      )
    }
  }
}
