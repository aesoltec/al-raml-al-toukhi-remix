package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ArchiveExportFormat
import com.example.data.SavedThemeEntity

@Composable
fun ArchiveExportDialog(
  targetEntity: SavedThemeEntity?,
  initialCategory: String? = null,
  availableCategories: List<Pair<String, Int>> = emptyList(),
  totalArchiveCount: Int,
  buildExportText: (SavedThemeEntity?, String?, ArchiveExportFormat) -> String,
  onSaveToFile: (String?, ArchiveExportFormat, String) -> Unit,
  onShareText: (String?, ArchiveExportFormat, String) -> Unit,
  onCopyText: (String?, String) -> Unit,
  onDismiss: () -> Unit,
) {
  var selectedFormat by remember(targetEntity?.id, initialCategory) {
    mutableStateOf(ArchiveExportFormat.MARKDOWN_REPORT)
  }
  var selectedCategory by remember(targetEntity?.id, initialCategory) {
    mutableStateOf(if (targetEntity == null) initialCategory else null)
  }

  val previewText =
    remember(targetEntity?.id, selectedCategory, selectedFormat, totalArchiveCount) {
      buildExportText(targetEntity, selectedCategory, selectedFormat)
    }

  val scopeLabel =
    when {
      targetEntity != null -> targetEntity.title
      !selectedCategory.isNullOrBlank() -> {
        val count = availableCategories.firstOrNull { it.first.equals(selectedCategory, ignoreCase = true) }?.second
        if (count != null) "Catégorie : $selectedCategory ($count tirages)"
        else "Catégorie : $selectedCategory"
      }
      else -> "Toutes les archives ($totalArchiveCount tirages)"
    }

  AlertDialog(
    onDismissRequest = onDismiss,
    icon = {
      Icon(
        imageVector = Icons.Rounded.Description,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
      )
    },
    title = {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "Exporter l’analyse",
          style = MaterialTheme.typography.titleLarge,
        )
        Text(
          text = scopeLabel,
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        if (targetEntity == null && availableCategories.isNotEmpty()) {
          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            FilterChip(
              selected = selectedCategory == null,
              onClick = { selectedCategory = null },
              label = { Text("Toutes ($totalArchiveCount)", maxLines = 1) },
              modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("export_scope_all"),
            )
            availableCategories.forEachIndexed { idx, (catName, count) ->
              FilterChip(
                selected = selectedCategory.equals(catName, ignoreCase = true),
                onClick = { selectedCategory = catName },
                label = { Text("$catName ($count)", maxLines = 1) },
                modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("export_scope_cat_$idx"),
              )
            }
          }
        }

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
          ArchiveExportFormat.entries.forEachIndexed { index, format ->
            SegmentedButton(
              selected = selectedFormat == format,
              onClick = { selectedFormat = format },
              shape =
                SegmentedButtonDefaults.itemShape(
                  index = index,
                  count = ArchiveExportFormat.entries.size,
                ),
              modifier = Modifier.testTag("export_format_${format.extension}"),
            ) {
              Text(format.label, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
          }
        }

        Surface(
          shape = RoundedCornerShape(14.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
          border =
            BorderStroke(
              1.dp,
              MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
            ),
          modifier = Modifier.fillMaxWidth().heightIn(min = 160.dp, max = 260.dp),
        ) {
          SelectionContainer {
            Text(
              text = previewText,
              style =
                MaterialTheme.typography.bodySmall.copy(
                  fontFamily = FontFamily.Monospace,
                  fontSize = 11.sp,
                  lineHeight = 16.sp,
                ),
              color = MaterialTheme.colorScheme.onSurface,
              modifier =
                Modifier
                  .fillMaxWidth()
                  .verticalScroll(rememberScrollState())
                  .horizontalScroll(rememberScrollState())
                  .padding(12.dp)
                  .testTag("export_preview_text"),
            )
          }
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          OutlinedButton(
            onClick = { onCopyText(selectedCategory, previewText) },
            modifier = Modifier.weight(1f).testTag("export_copy_button"),
            shape = RoundedCornerShape(12.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.ContentCopy,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Copier", maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
          OutlinedButton(
            onClick = { onShareText(selectedCategory, selectedFormat, previewText) },
            modifier = Modifier.weight(1f).testTag("export_share_button"),
            shape = RoundedCornerShape(12.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.Share,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Partager", maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = { onSaveToFile(selectedCategory, selectedFormat, previewText) },
        modifier = Modifier.testTag("export_save_file_button"),
      ) {
        Icon(
          imageVector = Icons.Rounded.Download,
          contentDescription = null,
          modifier = Modifier.size(18.dp),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Enregistrer (${selectedFormat.extension.uppercase()})")
      }
    },
    dismissButton = {
      TextButton(
        onClick = onDismiss,
        modifier = Modifier.testTag("export_dismiss_button"),
      ) {
        Text("Fermer")
      }
    },
  )
}
