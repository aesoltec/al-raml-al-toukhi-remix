package com.example.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Arrière-plan atmosphérique aux couleurs du sable réel du désert :
 * dunes ensoleillées ou dunes crépusculaires, avec courbes ondulantes douces
 * visibles en transparence à travers les cartes Liquid Glass.
 */
@Composable
fun SandDuneBackdrop(
  modifier: Modifier = Modifier,
  content: @Composable BoxScope.() -> Unit,
) {
  val ext = AlRamlColors.extended
  Box(
    modifier =
      modifier
        .fillMaxSize()
        .background(
          brush =
            Brush.verticalGradient(
              colors = listOf(ext.duneTop, ext.duneMid, ext.duneBottom),
            ),
        ),
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val w = size.width
      val h = size.height

      // Halo solaire/lunaire diffus sur la dune supérieure
      drawCircle(
        brush =
          Brush.radialGradient(
            colors =
              listOf(
                ext.duneGlow.copy(alpha = if (ext.isDarkSand) 0.34f else 0.72f),
                Color.Transparent,
              ),
            center = Offset(w * 0.82f, h * 0.14f),
            radius = w * 0.72f,
          ),
        radius = w * 0.72f,
        center = Offset(w * 0.82f, h * 0.14f),
      )

      // Halo chaud en bas à gauche pour accentuer la réfraction Liquid Glass
      drawCircle(
        brush =
          Brush.radialGradient(
            colors =
              listOf(
                ext.goldAccent.copy(alpha = if (ext.isDarkSand) 0.18f else 0.24f),
                Color.Transparent,
              ),
            center = Offset(w * 0.16f, h * 0.78f),
            radius = w * 0.65f,
          ),
        radius = w * 0.65f,
        center = Offset(w * 0.16f, h * 0.78f),
      )

      // Crête de dune principale (sable modelé par le vent)
      val ridge1 =
        Path().apply {
          moveTo(0f, h * 0.42f)
          cubicTo(w * 0.28f, h * 0.35f, w * 0.65f, h * 0.50f, w, h * 0.39f)
          lineTo(w, h)
          lineTo(0f, h)
          close()
        }
      drawPath(
        path = ridge1,
        brush =
          Brush.verticalGradient(
            colors =
              listOf(
                ext.duneBottom.copy(alpha = if (ext.isDarkSand) 0.45f else 0.42f),
                ext.duneMid.copy(alpha = 0.15f),
              ),
            startY = h * 0.35f,
            endY = h,
          ),
      )

      // Seconde ondulation de sable plus douce au premier plan
      val ridge2 =
        Path().apply {
          moveTo(0f, h * 0.70f)
          cubicTo(w * 0.35f, h * 0.78f, w * 0.68f, h * 0.62f, w, h * 0.72f)
          lineTo(w, h)
          lineTo(0f, h)
          close()
        }
      drawPath(
        path = ridge2,
        brush =
          Brush.verticalGradient(
            colors =
              listOf(
                ext.goldAccent.copy(alpha = if (ext.isDarkSand) 0.16f else 0.18f),
                Color.Transparent,
              ),
            startY = h * 0.62f,
            endY = h,
          ),
      )

      // Fins sillons lumineux de sable
      val crestStroke =
        Path().apply {
          moveTo(0f, h * 0.42f)
          cubicTo(w * 0.28f, h * 0.35f, w * 0.65f, h * 0.50f, w, h * 0.39f)
        }
      drawPath(
        path = crestStroke,
        color = ext.glassSpecularTop.copy(alpha = if (ext.isDarkSand) 0.18f else 0.45f),
        style = Stroke(width = 1.5.dp.toPx()),
      )
    }

    content()
  }
}

/**
 * Surface Liquid Glass translucide aux reflets de silice et de sable doré.
 */
@Composable
fun LiquidGlassCard(
  modifier: Modifier = Modifier,
  shape: Shape = RoundedCornerShape(20.dp),
  accentBorderColor: Color? = null,
  elevated: Boolean = false,
  content: @Composable BoxScope.() -> Unit,
) {
  val ext = AlRamlColors.extended
  val fillColor = if (elevated) ext.glassElevated else ext.glassSurface
  val borderBrush =
    if (accentBorderColor != null) {
      Brush.linearGradient(
        colors = listOf(ext.glassSpecularTop, accentBorderColor.copy(alpha = 0.55f)),
      )
    } else {
      Brush.linearGradient(
        colors = listOf(ext.glassSpecularTop, ext.glassSpecularBottom),
      )
    }

  Surface(
    modifier = modifier,
    shape = shape,
    color = Color.Transparent,
    tonalElevation = 0.dp,
    shadowElevation = 0.dp,
  ) {
    Box(
      modifier =
        Modifier
          .clip(shape)
          .background(fillColor)
          .background(
            brush =
              Brush.verticalGradient(
                colors = listOf(ext.glassSheen, Color.Transparent),
              ),
          )
          .border(
            border = BorderStroke(width = 1.1.dp, brush = borderBrush),
            shape = shape,
          ),
      content = content,
    )
  }
}

fun Modifier.liquidGlassModifier(
  shape: Shape,
  fillColor: Color,
  specularTop: Color,
  specularBottom: Color,
  sheenColor: Color,
  borderWidth: Dp = 1.dp,
): Modifier =
  this
    .clip(shape)
    .background(fillColor)
    .background(Brush.verticalGradient(listOf(sheenColor, Color.Transparent)))
    .border(
      BorderStroke(borderWidth, Brush.linearGradient(listOf(specularTop, specularBottom))),
      shape,
    )
