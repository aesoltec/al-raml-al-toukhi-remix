package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.OpenInNew
import androidx.compose.material.icons.rounded.Bookmarks
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.ArchiveExportFormat
import com.example.data.SavedThemeEntity
import kotlinx.coroutines.launch
import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.ArchiveFrequencyReport
import com.example.geomancy.ArchiveRecurrenceReport
import com.example.geomancy.CalibrationReport
import com.example.geomancy.FortunePolarity
import com.example.geomancy.MatchOutcome
import com.example.geomancy.PredictiveCalibrationEngine
import com.example.geomancy.QuestionDomain
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.components.PolarityBadge
import com.example.ui.components.SectionHeaderCard
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard
import java.text.DateFormat
import java.util.Date
import java.util.Locale

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.icons.rounded.Category
import com.example.data.SavedThemeRepository

private data class ArchiveExportRequest(
  val entity: SavedThemeEntity? = null,
  val categoryName: String? = null,
)

@Composable
fun SavedThemesScreen(
  savedThemes: List<SavedThemeEntity>,
  calibrationReport: CalibrationReport,
  frequencyReport: ArchiveFrequencyReport =
    PredictiveCalibrationEngine.computeArchiveFrequencies(emptyList()),
  recurrenceReport: ArchiveRecurrenceReport =
    PredictiveCalibrationEngine.computeThemeRecurrences(emptyList()),
  isCalibrationEnabled: Boolean,
  onOpenTheme: (SavedThemeEntity) -> Unit,
  onUpdateContext: (Long, String, String, MatchOutcome?, String, String) -> Unit = { _, _, _, _, _, _ -> },
  onUpdateContextWithCards: ((Long, String, String, MatchOutcome?, String, String, String, String) -> Unit)? = null,
  onDeleteTheme: (Long) -> Unit,
  onToggleCalibrationEnabled: (Boolean) -> Unit,
  onApplyCalibration: () -> Unit,
  onResetCalibration: () -> Unit,
  onImportNov17Matches: () -> Unit,
  onImportNov18Draws: () -> Unit = {},
  onApplyNov18VerifiedResults: () -> Unit = {},
  onBuildExportText: (SavedThemeEntity?, ArchiveExportFormat) -> String = { _, _ -> "" },
  onBuildCategoryExportText: ((SavedThemeEntity?, String?, ArchiveExportFormat) -> String)? = null,
  onSuggestedExportFileName: (SavedThemeEntity?, ArchiveExportFormat) -> String = { _, f ->
    "al_raml_archives.${f.extension}"
  },
  onSuggestedCategoryExportFileName: ((SavedThemeEntity?, String?, ArchiveExportFormat) -> String)? = null,
  onGoToCast: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var selectedSubTab by remember { mutableIntStateOf(0) } // 0 = Thèmes, 1 = Tendances (16 figures), 2 = Précision
  var onlyRecurringFilter by remember { mutableStateOf(false) }
  var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }
  var editingEntity by remember { mutableStateOf<SavedThemeEntity?>(null) }
  var exportRequest by remember { mutableStateOf<ArchiveExportRequest?>(null) }
  var pendingExportContent by remember { mutableStateOf<String?>(null) }

  val categoryGroupsAll =
    remember(savedThemes) {
      SavedThemeRepository.groupByCategory(savedThemes)
    }
  val availableCategoryCounts =
    remember(categoryGroupsAll) {
      categoryGroupsAll.entries.map { it.key to it.value.size }
    }

  val context = LocalContext.current
  val snackbarHostState = remember { SnackbarHostState() }
  val coroutineScope = rememberCoroutineScope()

  fun writeDocumentToUri(uri: android.net.Uri?, format: ArchiveExportFormat) {
    val content = pendingExportContent
    pendingExportContent = null
    if (uri == null || content == null) return
    val result =
      runCatching {
        context.contentResolver.openOutputStream(uri)?.use { stream ->
          stream.write(content.toByteArray(Charsets.UTF_8))
          stream.flush()
        } ?: error("Flux d'écriture indisponible")
      }
    coroutineScope.launch {
      if (result.isSuccess) {
        snackbarHostState.showSnackbar("Analyse exportée en ${format.label}")
      } else {
        snackbarHostState.showSnackbar("Impossible d’enregistrer le fichier")
      }
    }
  }

  val createTxtLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/plain")) { uri ->
      writeDocumentToUri(uri, ArchiveExportFormat.PLAIN_TEXT)
    }

  val createMdLauncher =
    rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("text/markdown")) { uri ->
      writeDocumentToUri(uri, ArchiveExportFormat.MARKDOWN_REPORT)
    }

  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    Column(
      modifier = Modifier.fillMaxSize().widthIn(max = 760.dp).padding(horizontal = 16.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        SegmentedButton(
          selected = selectedSubTab == 0,
          onClick = { selectedSubTab = 0 },
          shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
          modifier = Modifier.testTag("saved_subtab_archives"),
        ) {
          Text("Thèmes (${savedThemes.size})", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        SegmentedButton(
          selected = selectedSubTab == 1,
          onClick = { selectedSubTab = 1 },
          shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
          modifier = Modifier.testTag("saved_subtab_frequencies"),
        ) {
          Text("Tendances", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        SegmentedButton(
          selected = selectedSubTab == 2,
          onClick = { selectedSubTab = 2 },
          shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
          modifier = Modifier.testTag("saved_subtab_predictive"),
        ) {
          val pctSuffix =
            if (calibrationReport.totalSamples > 0) " · ${calibrationReport.calibratedAccuracyPercent}%"
            else ""
          Text("Précision$pctSuffix", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
      }

      if (selectedSubTab == 1) {
        ArchiveFrequencyChartSection(
          report = frequencyReport,
          modifier = Modifier.weight(1f),
        )
      } else if (selectedSubTab == 2) {
        PredictiveDashboardSection(
          report = calibrationReport,
          isCalibrationEnabled = isCalibrationEnabled,
          onToggleCalibrationEnabled = onToggleCalibrationEnabled,
          onApplyCalibration = onApplyCalibration,
          onResetCalibration = onResetCalibration,
          onImportNov17Matches = onImportNov17Matches,
          modifier = Modifier.weight(1f),
        )
      } else if (savedThemes.isEmpty()) {
        Box(
          modifier = Modifier.weight(1f).fillMaxWidth(),
          contentAlignment = Alignment.Center,
        ) {
          LiquidGlassCard(
            modifier = Modifier.widthIn(max = 440.dp),
            shape = RoundedCornerShape(22.dp),
            elevated = true,
          ) {
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              Icon(
                imageVector = Icons.Rounded.Bookmarks,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(42.dp),
              )
              Text(
                text = stringResource(R.string.saved_empty_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
              )
              Text(
                text = stringResource(R.string.saved_empty_desc),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
              )
              Button(
                onClick = onGoToCast,
                modifier = Modifier.fillMaxWidth().testTag("saved_empty_cast_btn"),
                shape = RoundedCornerShape(12.dp),
              ) {
                Text(stringResource(R.string.takht_go_cast))
              }
              OutlinedButton(
                onClick = onImportNov18Draws,
                modifier = Modifier.fillMaxWidth().testTag("saved_empty_import_nov18_btn"),
                shape = RoundedCornerShape(12.dp),
              ) {
                Icon(Icons.Rounded.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Restaurer les 6 consultations (18-11)")
              }
              OutlinedButton(
                onClick = {
                  onImportNov17Matches()
                  selectedSubTab = 1
                },
                modifier = Modifier.fillMaxWidth().testTag("saved_empty_import_nov17_btn"),
                shape = RoundedCornerShape(12.dp),
              ) {
                Icon(Icons.Rounded.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Importer les 10 matchs (17-11-2026)")
              }
            }
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier.weight(1f).fillMaxWidth().testTag("saved_themes_lazy_column"),
          contentPadding = PaddingValues(bottom = 14.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          item {
            SectionHeaderCard(
              title = stringResource(R.string.saved_header_title),
              arabicSubtitle = "سجل التخت المحفوظ",
              body = stringResource(R.string.saved_header_desc),
            )
          }

          item {
            LiquidGlassCard(
              modifier = Modifier.fillMaxWidth().testTag("category_export_summary_card"),
              shape = RoundedCornerShape(16.dp),
            ) {
              Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically,
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = "Archives par Catégorie (${availableCategoryCounts.size})",
                      style = MaterialTheme.typography.titleSmall,
                      color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                      text = "Filtre ou exporte une catégorie pour analyser et calibrer la précision.",
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                  }
                  Spacer(modifier = Modifier.width(10.dp))
                  OutlinedButton(
                    onClick = {
                      exportRequest =
                        ArchiveExportRequest(
                          entity = null,
                          categoryName = selectedCategoryFilter,
                        )
                    },
                    modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("export_all_archives_button"),
                    shape = RoundedCornerShape(12.dp),
                  ) {
                    Icon(
                      imageVector = Icons.Rounded.Description,
                      contentDescription = null,
                      modifier = Modifier.size(16.dp),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      if (selectedCategoryFilter != null) "Exporter cat." else "Tout exporter",
                    )
                  }
                }

                Row(
                  modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                  FilterChip(
                    selected = selectedCategoryFilter == null,
                    onClick = { selectedCategoryFilter = null },
                    label = { Text("Toutes (${savedThemes.size})", maxLines = 1) },
                    modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("category_filter_all"),
                  )
                  availableCategoryCounts.forEachIndexed { idx, (catName, count) ->
                    val isSelected = selectedCategoryFilter.equals(catName, ignoreCase = true)
                    FilterChip(
                      selected = isSelected,
                      onClick = {
                        selectedCategoryFilter = if (isSelected) null else catName
                      },
                      label = { Text("$catName ($count)", maxLines = 1) },
                      modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("category_filter_$idx"),
                    )
                  }
                }
              }
            }
          }

          item {
            LiquidGlassCard(
              modifier = Modifier.fillMaxWidth().testTag("archive_recurrence_summary_card"),
              shape = RoundedCornerShape(16.dp),
            ) {
              Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                  Icon(
                    imageVector = Icons.Rounded.Repeat,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp),
                  )
                  Text(
                    text = "Répertoire & Récurrence des Thèmes",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                  )
                }
                Text(
                  text =
                    "${recurrenceReport.totalThemes} tirage(s) · ${recurrenceReport.uniqueSignatures} configuration(s) distincte(s) · " +
                      "${recurrenceReport.recurringSignatureCount} thème(s) déjà revenus ≥ 2×",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                  modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                  FilterChip(
                    selected = !onlyRecurringFilter,
                    onClick = { onlyRecurringFilter = false },
                    label = { Text("Tous (${savedThemes.size})") },
                    modifier = Modifier.testTag("filter_all_themes_chip"),
                  )
                  FilterChip(
                    selected = onlyRecurringFilter,
                    onClick = { onlyRecurringFilter = true },
                    label = { Text("Récurrents ≥ 2× (${recurrenceReport.recurringSignatureCount})") },
                    modifier = Modifier.testTag("filter_recurring_themes_chip"),
                  )
                }
                if (recurrenceReport.recurringThemes.isNotEmpty()) {
                  recurrenceReport.recurringThemes.take(4).forEach { rec ->
                    Surface(
                      shape = RoundedCornerShape(10.dp),
                      color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                    ) {
                      Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                      ) {
                        Column(modifier = Modifier.weight(1f)) {
                          Text(
                            text = rec.mothersLabel,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                          )
                          Text(
                            text = "${rec.signatureCode} · Juge ${rec.judgeFigure.westAfricanName} / M16 ${rec.outcomeFigure.westAfricanName}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                          )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                          shape = RoundedCornerShape(999.dp),
                          color = AlRamlColors.extended.goldAccent.copy(alpha = 0.2f),
                          border = BorderStroke(1.dp, AlRamlColors.extended.goldAccent.copy(alpha = 0.5f)),
                        ) {
                          Text(
                            text = "${rec.occurrenceCount}×",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.dp),
                          )
                        }
                      }
                    }
                  }
                } else {
                  Text(
                    text = "Chaque configuration M1–M4 archivée jusqu’ici est unique (1×) ; le compteur s’incrémentera automatiquement dès qu’un thème identique reviendra.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                  )
                }
              }
            }
          }

          val pendingNov18Count =
            savedThemes.count { entity ->
              val isPending =
                entity.actualOutcomeName == null ||
                  (entity.actualScoreText.isBlank() &&
                    entity.contextOutcomeNotes.contains("à compléter", ignoreCase = true))
              isPending &&
                PredictiveCalibrationEngine.nov18VerifiedMatchResults().any { v ->
                  entity.title.contains(v.titlePrefix, ignoreCase = true)
                }
            }
          if (pendingNov18Count > 0) {
            item {
              LiquidGlassCard(
                modifier = Modifier.fillMaxWidth().testTag("sync_nov18_verified_card"),
                shape = RoundedCornerShape(16.dp),
                accentBorderColor = AlRamlColors.extended.fortunate,
              ) {
                Row(
                  modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically,
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = "Résultats Copa del Rey (18-11) disponibles",
                      style = MaterialTheme.typography.titleSmall,
                      color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                      text = "$pendingNov18Count match(s) en attente de score vérifié",
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                  }
                  Spacer(modifier = Modifier.width(8.dp))
                  OutlinedButton(
                    onClick = {
                      onApplyNov18VerifiedResults()
                      coroutineScope.launch {
                        snackbarHostState.showSnackbar("Résultats vérifiés du 18-11 enregistrés & calibrés")
                      }
                    },
                    modifier = Modifier.testTag("sync_nov18_verified_button"),
                    shape = RoundedCornerShape(12.dp),
                  ) {
                    Icon(Icons.Rounded.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Appliquer")
                  }
                }
              }
            }
          }

          val recurringFilteredThemes =
            if (onlyRecurringFilter) {
              savedThemes.filter { entity ->
                val counts =
                  entity.motherDotCountsCsv
                    .split(",")
                    .mapNotNull { it.trim().toIntOrNull() }
                    .let { if (it.size == 16) it else List(16) { 2 } }
                val sig = PredictiveCalibrationEngine.canonicalSignatureFromMotherDotCounts(counts)
                (recurrenceReport.bySignature[sig]?.occurrenceCount ?: 1) >= 2
              }
            } else {
              savedThemes
            }

          val displayedThemes =
            if (selectedCategoryFilter != null) {
              recurringFilteredThemes.filter { entity ->
                SavedThemeRepository.categoryFor(entity).equals(selectedCategoryFilter, ignoreCase = true)
              }
            } else {
              recurringFilteredThemes
            }

          val groupedDisplayed =
            SavedThemeRepository.groupByCategory(displayedThemes).entries.toList()

          groupedDisplayed.forEachIndexed { catIndex, (categoryName, categoryEntities) ->
            val categoryLeagueStat =
              calibrationReport.accuracyByLeague.firstOrNull {
                it.categoryName.equals(categoryName, ignoreCase = true)
              }
            item(key = "cat_header_$categoryName") {
              CategoryGroupHeaderCard(
                categoryName = categoryName,
                themeCount = categoryEntities.size,
                accuracySummary =
                  categoryLeagueStat?.let { stat ->
                    "${stat.calibratedSuccessCount}/${stat.totalMatches} (${stat.calibratedAccuracyPercent}%)"
                  },
                index = catIndex,
                onExportCategory = {
                  exportRequest = ArchiveExportRequest(entity = null, categoryName = categoryName)
                },
              )
            }

            items(categoryEntities, key = { it.id }) { entity ->
              val counts =
                remember(entity.motherDotCountsCsv) {
                  entity.motherDotCountsCsv
                    .split(",")
                    .mapNotNull { it.trim().toIntOrNull() }
                    .let { if (it.size == 16) it else List(16) { 2 } }
                }
              val sig =
                remember(counts) {
                  PredictiveCalibrationEngine.canonicalSignatureFromMotherDotCounts(counts)
                }
              val recCount = recurrenceReport.bySignature[sig]?.occurrenceCount ?: 1
              SavedThemeCard(
                entity = entity,
                signatureCode = sig,
                recurrenceCount = recCount,
                onOpen = { onOpenTheme(entity) },
                onExport = { exportRequest = ArchiveExportRequest(entity = entity) },
                onEditContext = { editingEntity = entity },
                onDelete = { onDeleteTheme(entity.id) },
              )
            }
          }

          item { Spacer(modifier = Modifier.height(12.dp)) }
        }
      }
    }

    SnackbarHost(
      hostState = snackbarHostState,
      modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
    )
  }

  exportRequest?.let { request ->
    ArchiveExportDialog(
      targetEntity = request.entity,
      initialCategory = request.categoryName,
      availableCategories = availableCategoryCounts,
      totalArchiveCount = savedThemes.size,
      buildExportText = { entity, catName, format ->
        if (onBuildCategoryExportText != null) {
          onBuildCategoryExportText(entity, catName, format)
        } else {
          onBuildExportText(entity, format)
        }
      },
      onSaveToFile = { catName, format, content ->
        val fileName =
          if (onSuggestedCategoryExportFileName != null) {
            onSuggestedCategoryExportFileName(request.entity, catName, format)
          } else {
            onSuggestedExportFileName(request.entity, format)
          }
        pendingExportContent = content
        exportRequest = null
        when (format) {
          ArchiveExportFormat.PLAIN_TEXT -> createTxtLauncher.launch(fileName)
          ArchiveExportFormat.MARKDOWN_REPORT -> createMdLauncher.launch(fileName)
        }
      },
      onShareText = { catName, format, content ->
        val subject =
          when {
            request.entity != null -> "Al-Raml Al-Toukhi · ${request.entity.title}"
            !catName.isNullOrBlank() -> "Al-Raml Al-Toukhi · Catégorie $catName"
            else -> "Al-Raml Al-Toukhi · Archives (${savedThemes.size} tirages)"
          }
        val sendIntent =
          Intent(Intent.ACTION_SEND).apply {
            type = format.mimeType
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, content)
          }
        exportRequest = null
        context.startActivity(Intent.createChooser(sendIntent, "Exporter l’analyse"))
      },
      onCopyText = { catName, content ->
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val label = request.entity?.title ?: catName ?: "Archives Al-Raml Al-Toukhi"
        clipboard.setPrimaryClip(ClipData.newPlainText(label, content))
        exportRequest = null
        coroutineScope.launch {
          snackbarHostState.showSnackbar("Analyse copiée dans le presse-papiers")
        }
      },
      onDismiss = { exportRequest = null },
    )
  }

  editingEntity?.let { entity ->
    val initialOutcome =
      entity.actualOutcomeName?.let { name -> runCatching { MatchOutcome.valueOf(name) }.getOrNull() }
    SaveOrEditThemeDialog(
      dialogTitle = "Modifier le contexte et l’issue",
      initialTitle = entity.title,
      initialContext = entity.contextOutcomeNotes,
      isContestDomain = entity.domainName == QuestionDomain.CONTEST.name,
      initialOutcome = initialOutcome,
      initialScore = entity.actualScoreText,
      initialLeague = SavedThemeRepository.categoryFor(entity),
      initialYellowCards = entity.actualYellowCardsText,
      initialRedCards = entity.actualRedCardsText,
      onDismiss = { editingEntity = null },
      onConfirm = { newTitle, newContext, outcome, score, league, yellowCards, redCards ->
        if (onUpdateContextWithCards != null) {
          onUpdateContextWithCards(entity.id, newTitle, newContext, outcome, score, league, yellowCards, redCards)
        } else {
          onUpdateContext(entity.id, newTitle, newContext, outcome, score, league)
        }
        editingEntity = null
      },
    )
  }
}

@Composable
private fun CategoryGroupHeaderCard(
  categoryName: String,
  themeCount: Int,
  accuracySummary: String?,
  index: Int,
  onExportCategory: () -> Unit,
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.11f),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
    modifier = Modifier.fillMaxWidth().testTag("category_group_header_$index"),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Row(
        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.Category,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(18.dp),
        )
        Column {
          Text(
            text = "$categoryName ($themeCount)",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          if (accuracySummary != null) {
            Text(
              text = "Précision vérifiée : $accuracySummary",
              style = MaterialTheme.typography.labelSmall,
              color = AlRamlColors.extended.fortunate,
            )
          }
        }
      }
      OutlinedButton(
        onClick = onExportCategory,
        modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("export_category_button_$index"),
        shape = RoundedCornerShape(10.dp),
      ) {
        Icon(Icons.Rounded.Share, contentDescription = null, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text("Exporter")
      }
    }
  }
}

@Composable
private fun SavedThemeCard(
  entity: SavedThemeEntity,
  signatureCode: String,
  recurrenceCount: Int,
  onOpen: () -> Unit,
  onExport: () -> Unit,
  onEditContext: () -> Unit,
  onDelete: () -> Unit,
) {
  val judgeFigure = remember(entity.judgeFigureId) { AlToukhiFigures.findById(entity.judgeFigureId) }
  val outcomeFigure =
    remember(entity.outcomeFigureId) { AlToukhiFigures.findById(entity.outcomeFigureId) }
  val polarity =
    remember(entity.overallPolarityName) {
      runCatching { FortunePolarity.valueOf(entity.overallPolarityName) }
        .getOrDefault(FortunePolarity.MIXED)
    }
  val domain =
    remember(entity.domainName) {
      runCatching { QuestionDomain.valueOf(entity.domainName) }.getOrDefault(QuestionDomain.GENERAL)
    }
  val storedOutcome =
    remember(entity.actualOutcomeName) {
      entity.actualOutcomeName?.let { runCatching { MatchOutcome.valueOf(it) }.getOrNull() }
    }
  val dateLabel =
    remember(entity.createdAtMillis) {
      DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.FRENCH)
        .format(Date(entity.createdAtMillis))
    }

  LiquidGlassCard(
    modifier =
      Modifier.fillMaxWidth()
        .clickable(onClick = onOpen)
        .testTag("saved_theme_card_${entity.id}"),
    shape = RoundedCornerShape(18.dp),
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = entity.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          Text(
            text = "${domain.title} · $dateLabel",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Surface(
            shape = RoundedCornerShape(999.dp),
            color =
              if (recurrenceCount >= 2) AlRamlColors.extended.goldAccent.copy(alpha = 0.22f)
              else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
            border =
              BorderStroke(
                1.dp,
                if (recurrenceCount >= 2) AlRamlColors.extended.goldAccent.copy(alpha = 0.55f)
                else AlRamlColors.extended.cardBorder,
              ),
          ) {
            Text(
              text = if (recurrenceCount >= 2) "${recurrenceCount}× revenu" else "1× · $signatureCode",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = if (recurrenceCount >= 2) FontWeight.Bold else FontWeight.Medium,
              color =
                if (recurrenceCount >= 2) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            )
          }
          PolarityBadge(polarity = polarity)
        }
      }

      val categoryDisplay = remember(entity.leagueCategory, entity.domainName, entity.title) {
        SavedThemeRepository.categoryFor(entity)
      }
      if (storedOutcome != null ||
        categoryDisplay.isNotEmpty() ||
        entity.actualScoreText.isNotEmpty() ||
        entity.actualYellowCardsText.isNotEmpty() ||
        entity.actualRedCardsText.isNotEmpty()
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          if (categoryDisplay.isNotEmpty()) {
            Surface(
              shape = RoundedCornerShape(999.dp),
              color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.14f),
              border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f)),
            ) {
              Text(
                text = categoryDisplay,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
              )
            }
          }
          if (storedOutcome != null || entity.actualScoreText.isNotEmpty()) {
            val outcomeColor = AlRamlColors.extended.fortunate
            Surface(
              shape = RoundedCornerShape(999.dp),
              color = outcomeColor.copy(alpha = 0.14f),
              border = BorderStroke(1.dp, outcomeColor.copy(alpha = 0.4f)),
            ) {
              val text =
                buildString {
                  if (entity.actualScoreText.isNotEmpty()) {
                    append("Score ${entity.actualScoreText}")
                  }
                  if (storedOutcome != null) {
                    if (isNotEmpty()) append(" · ")
                    append(storedOutcome.label)
                  }
                }
              Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = outcomeColor,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
              )
            }
          }
          if (entity.actualYellowCardsText.isNotEmpty()) {
            val yellowColor = AlRamlColors.extended.mixed
            Surface(
              shape = RoundedCornerShape(999.dp),
              color = yellowColor.copy(alpha = 0.14f),
              border = BorderStroke(1.dp, yellowColor.copy(alpha = 0.4f)),
            ) {
              Text(
                text = "Cartons J : ${entity.actualYellowCardsText}",
                style = MaterialTheme.typography.labelSmall,
                color = yellowColor,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
              )
            }
          }
          if (entity.actualRedCardsText.isNotEmpty()) {
            val redColor = AlRamlColors.extended.unfortunate
            Surface(
              shape = RoundedCornerShape(999.dp),
              color = redColor.copy(alpha = 0.14f),
              border = BorderStroke(1.dp, redColor.copy(alpha = 0.4f)),
            ) {
              Text(
                text = "Carton R : ${entity.actualRedCardsText}",
                style = MaterialTheme.typography.labelSmall,
                color = redColor,
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp),
              )
            }
          }
        }
      }

      if (entity.questionText.isNotEmpty() && entity.questionText != entity.title) {
        Text(
          text = "« ${entity.questionText} »",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            GeomanticFigureGlyph(figure = judgeFigure, size = 24.dp)
            Column {
              Text("Juge M15", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(judgeFigure.westAfricanName, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
            }
          }
        }

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            GeomanticFigureGlyph(figure = outcomeFigure, size = 24.dp)
            Column {
              Text("Issue M16", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
              Text(outcomeFigure.westAfricanName, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
            }
          }
        }
      }

      if (entity.contextOutcomeNotes.isNotEmpty()) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
        ) {
          Text(
            text = "Contexte / Résultat enregistré : ${entity.contextOutcomeNotes}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxWidth().padding(10.dp),
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        TextButton(
          onClick = onOpen,
          modifier = Modifier.testTag("open_saved_theme_${entity.id}"),
        ) {
          Icon(Icons.AutoMirrored.Rounded.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Rouvrir le Takht")
        }

        Row {
          IconButton(
            onClick = onExport,
            modifier = Modifier.testTag("export_saved_theme_${entity.id}"),
          ) {
            Icon(
              imageVector = Icons.Rounded.Share,
              contentDescription = "Exporter l’analyse du thème",
              tint = MaterialTheme.colorScheme.secondary,
            )
          }
          IconButton(
            onClick = onEditContext,
            modifier = Modifier.testTag("edit_saved_theme_${entity.id}"),
          ) {
            Icon(
              imageVector = Icons.Rounded.EditNote,
              contentDescription = "Modifier le contexte du thème",
              tint = MaterialTheme.colorScheme.primary,
            )
          }
          IconButton(
            onClick = onDelete,
            modifier = Modifier.testTag("delete_saved_theme_${entity.id}"),
          ) {
            Icon(
              imageVector = Icons.Rounded.DeleteOutline,
              contentDescription = "Supprimer le thème enregistré",
              tint = MaterialTheme.colorScheme.error,
            )
          }
        }
      }
    }
  }
}

@Composable
fun SaveOrEditThemeDialog(
  dialogTitle: String,
  initialTitle: String,
  initialContext: String,
  isContestDomain: Boolean = false,
  initialOutcome: MatchOutcome? = null,
  initialScore: String = "",
  initialLeague: String = "",
  initialYellowCards: String = "",
  initialRedCards: String = "",
  onDismiss: () -> Unit,
  onConfirm: (String, String, MatchOutcome?, String, String, String, String) -> Unit,
) {
  var titleText by remember(initialTitle) { mutableStateOf(initialTitle) }
  var contextText by remember(initialContext) { mutableStateOf(initialContext) }
  var selectedOutcome by remember(initialOutcome) { mutableStateOf(initialOutcome) }
  var scoreText by remember(initialScore) { mutableStateOf(initialScore) }
  var leagueText by remember(initialLeague) { mutableStateOf(initialLeague) }
  var yellowCardsText by remember(initialYellowCards) { mutableStateOf(initialYellowCards) }
  var redCardsText by remember(initialRedCards) { mutableStateOf(initialRedCards) }

  val categorySuggestions =
    if (isContestDomain) {
      listOf(
        "Copa del Rey",
        "Ligue des Nations UEFA",
        "Qualifications CAN 2027",
        "Championnat / Ligue",
        "Duel Libre",
      )
    } else {
      listOf(
        "Personnel",
        "Professionnel",
        "Projet",
        "Famille",
        "Général",
      )
    }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(dialogTitle, style = MaterialTheme.typography.titleLarge) },
    text = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        OutlinedTextField(
          value = titleText,
          onValueChange = { titleText = it },
          label = { Text(stringResource(R.string.save_title_label)) },
          placeholder = { Text(stringResource(R.string.save_title_placeholder)) },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("save_theme_title_input"),
        )
        OutlinedTextField(
          value = contextText,
          onValueChange = { contextText = it },
          label = { Text(stringResource(R.string.save_context_label)) },
          placeholder = { Text(stringResource(R.string.save_context_placeholder)) },
          minLines = 2,
          maxLines = 4,
          modifier = Modifier.fillMaxWidth().testTag("save_theme_context_input"),
        )

        OutlinedTextField(
          value = leagueText,
          onValueChange = { leagueText = it },
          label = { Text(if (isContestDomain) "Catégorie / Ligue" else "Catégorie d’archive") },
          placeholder = { Text(if (isContestDomain) "ex. Ligue des Nations UEFA" else "ex. Personnel") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("save_theme_league_input"),
        )

        Row(
          modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          categorySuggestions.forEach { suggestion ->
            FilterChip(
              selected = leagueText.equals(suggestion, ignoreCase = true),
              onClick = { leagueText = suggestion },
              label = { Text(suggestion) },
            )
          }
        }

        if (isContestDomain) {
          Text(
            text = "Résultat réel (calibre automatiquement l’algorithme)",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
          )
          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            FilterChip(
              selected = selectedOutcome == null,
              onClick = { selectedOutcome = null },
              label = { Text("Non renseigné") },
            )
            MatchOutcome.entries.forEach { outcome ->
              FilterChip(
                selected = selectedOutcome == outcome,
                onClick = { selectedOutcome = outcome },
                label = { Text(outcome.label) },
                modifier = Modifier.testTag("outcome_chip_${outcome.name.lowercase()}"),
              )
            }
          }

          OutlinedTextField(
            value = scoreText,
            onValueChange = { scoreText = it },
            label = { Text("Score exact réel (ex. 2-3)") },
            placeholder = { Text("ex. 1-3") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("save_theme_score_input"),
          )

          OutlinedTextField(
            value = yellowCardsText,
            onValueChange = { yellowCardsText = it },
            label = { Text("Cartons jaunes réels (ex. 4 ou 2-2)") },
            placeholder = { Text("ex. 4") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("save_theme_yellow_cards_input"),
          )

          OutlinedTextField(
            value = redCardsText,
            onValueChange = { redCardsText = it },
            label = { Text("Cartons rouges réels (ex. 0, 1)") },
            placeholder = { Text("ex. 0") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().testTag("save_theme_red_cards_input"),
          )

          Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            listOf("0", "1", "2+").forEach { quickVal ->
              FilterChip(
                selected = redCardsText.equals(quickVal, ignoreCase = true),
                onClick = { redCardsText = quickVal },
                label = { Text("Rouge : $quickVal") },
                modifier = Modifier.testTag("red_card_quick_${quickVal.replace("+", "plus")}"),
              )
            }
          }
        }
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onConfirm(
            titleText,
            contextText,
            selectedOutcome,
            scoreText,
            leagueText,
            yellowCardsText,
            redCardsText,
          )
        },
        modifier = Modifier.testTag("confirm_save_theme_button"),
      ) {
        Text("Enregistrer")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Annuler")
      }
    },
  )
}
