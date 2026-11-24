package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.geomancy.Element
import com.example.geomancy.FortunePolarity
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.PointParity
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard

@Composable
fun GeomanticFigureGlyph(
  figure: GeomanticFigure,
  modifier: Modifier = Modifier,
  size: Dp = 44.dp,
  dotColor: Color = MaterialTheme.colorScheme.primary,
  useElementColors: Boolean = false,
) {
  val extColors = AlRamlColors.extended
  val rankElements = listOf(Element.FIRE, Element.AIR, Element.WATER, Element.EARTH)

  Canvas(
    modifier =
      modifier.size(width = size, height = size * 1.15f).semantics {
        contentDescription = "Figure géomantique ${figure.westAfricanName} (${figure.code})"
      },
  ) {
    val rowStep = this.size.height / 4f
    val centerX = this.size.width / 2f
    val dotRadius = (this.size.width * 0.115f).coerceAtLeast(2.2f)
    val pairSpread = this.size.width * 0.22f

    figure.ranks.forEachIndexed { index, parity ->
      val y = rowStep * (index + 0.5f)
      val color = if (useElementColors) extColors.forElement(rankElements[index]) else dotColor

      if (parity == PointParity.ODD) {
        drawCircle(color = color, radius = dotRadius * 1.15f, center = Offset(centerX, y))
        drawCircle(
          color = color.copy(alpha = 0.25f),
          radius = dotRadius * 1.65f,
          center = Offset(centerX, y),
        )
      } else {
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX - pairSpread, y))
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX + pairSpread, y))
      }
    }
  }
}

@Composable
fun PolarityBadge(
  polarity: FortunePolarity,
  modifier: Modifier = Modifier,
) {
  val badgeColor = AlRamlColors.extended.forPolarity(polarity)
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(999.dp),
    color = badgeColor.copy(alpha = 0.14f),
    border = BorderStroke(1.dp, badgeColor.copy(alpha = 0.45f)),
  ) {
    Text(
      text = polarity.label,
      color = badgeColor,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = FontWeight.SemiBold,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
    )
  }
}

@Composable
fun ElementTag(
  element: Element,
  modifier: Modifier = Modifier,
) {
  val color = AlRamlColors.extended.forElement(element)
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(8.dp),
    color = color.copy(alpha = 0.14f),
    border = BorderStroke(1.dp, color.copy(alpha = 0.4f)),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
    ) {
      Text(
        text = "${element.englishName} · ${element.arabicName}",
        color = color,
        style = MaterialTheme.typography.labelSmall,
        maxLines = 1,
      )
    }
  }
}

@Composable
fun SectionHeaderCard(
  title: String,
  arabicSubtitle: String,
  body: String,
  modifier: Modifier = Modifier,
) {
  LiquidGlassCard(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    elevated = true,
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.headlineSmall,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = arabicSubtitle,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}
