package com.example.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material.icons.rounded.PictureAsPdf
import androidx.compose.material.icons.rounded.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.CoursePdfExporter
import com.example.geomancy.CourseBook
import com.example.geomancy.CourseFigureFocus
import com.example.geomancy.CourseLessonSection
import com.example.geomancy.CourseModule
import com.example.geomancy.CourseTakhtExample
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.GeomanticFigure
import com.example.ui.components.CourseTakhtPreview
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CoursesScreen(
  selectedBookId: String,
  completedModuleIds: Set<String>,
  onSelectBook: (String) -> Unit,
  onToggleModuleCompleted: (String) -> Unit,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
  onOpenTakhtExample: (CourseTakhtExample) -> Unit,
  modifier: Modifier = Modifier,
) {
  var expandedModuleId by
    remember(selectedBookId) {
      mutableStateOf(
        GeomancyCoursesCatalog.findBook(selectedBookId)?.modules?.firstOrNull()?.id
          ?: GeomancyCoursesCatalog.allBooks.first().modules.first().id,
      )
    }
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().widthIn(max = 760.dp).testTag("courses_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      coursesCurriculumItems(
        selectedBookId = selectedBookId,
        completedModuleIds = completedModuleIds,
        expandedModuleId = expandedModuleId,
        onSelectBook = onSelectBook,
        onToggleExpandedModule = { moduleId ->
          expandedModuleId = if (expandedModuleId == moduleId) "" else moduleId
        },
        onToggleModuleCompleted = onToggleModuleCompleted,
        onOpenFigureDetail = onOpenFigureDetail,
        onOpenTakhtExample = onOpenTakhtExample,
        onExportMessage = { message ->
          scope.launch { snackbarHostState.showSnackbar(message) }
        },
      )
    }

    SnackbarHost(
      hostState = snackbarHostState,
      modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
    )
  }
}

fun LazyListScope.coursesCurriculumItems(
  selectedBookId: String,
  completedModuleIds: Set<String>,
  expandedModuleId: String,
  onSelectBook: (String) -> Unit,
  onToggleExpandedModule: (String) -> Unit,
  onToggleModuleCompleted: (String) -> Unit,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
  onOpenTakhtExample: (CourseTakhtExample) -> Unit,
  onExportMessage: ((String) -> Unit)? = null,
) {
  val books = GeomancyCoursesCatalog.allBooks
  val activeBook = GeomancyCoursesCatalog.findBook(selectedBookId) ?: books.first()
  val totalModules = GeomancyCoursesCatalog.totalModulesCount
  val completedTotal = completedModuleIds.count { id -> GeomancyCoursesCatalog.findModule(id) != null }

  item(key = "courses_progress_banner") {
    CurriculumProgressBanner(
      completedCount = completedTotal,
      totalCount = totalModules,
      bookCount = books.size,
    )
  }

  item(key = "courses_pdf_export_bar") {
    CoursePdfExportCard(
      activeBook = activeBook,
      totalBooksCount = books.size,
      onExportMessage = onExportMessage,
    )
  }

  item(key = "courses_books_carousel") {
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxWidth().testTag("courses_books_row"),
    ) {
      items(books, key = { it.id }) { book ->
        val bookCompleted = book.modules.count { it.id in completedModuleIds }
        CourseBookSelectorCard(
          book = book,
          isSelected = book.id == activeBook.id,
          completedInBook = bookCompleted,
          onClick = { onSelectBook(book.id) },
        )
      }
    }
  }

  item(key = "active_book_header_${activeBook.id}") {
    ActiveCourseBookCard(
      book = activeBook,
      completedInBook = activeBook.modules.count { it.id in completedModuleIds },
    )
  }

  items(activeBook.modules, key = { it.id }) { module ->
    CourseModuleCard(
      module = module,
      isExpanded = expandedModuleId == module.id,
      isCompleted = module.id in completedModuleIds,
      onToggleExpanded = { onToggleExpandedModule(module.id) },
      onToggleCompleted = { onToggleModuleCompleted(module.id) },
      onOpenFigureDetail = onOpenFigureDetail,
      onOpenTakhtExample = onOpenTakhtExample,
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CoursePdfExportCard(
  activeBook: CourseBook,
  totalBooksCount: Int,
  onExportMessage: ((String) -> Unit)?,
) {
  val context = LocalContext.current
  val scope = rememberCoroutineScope()
  var pendingBookToExport by remember { mutableStateOf<CourseBook?>(null) }
  var isExporting by remember { mutableStateOf(false) }

  val pdfLauncher =
    rememberLauncherForActivityResult(
      contract = ActivityResultContracts.CreateDocument("application/pdf"),
    ) { uri ->
      val targetBook = pendingBookToExport
      if (uri == null) return@rememberLauncherForActivityResult
      isExporting = true
      scope.launch {
        val result =
          withContext(Dispatchers.IO) {
            runCatching {
              context.contentResolver.openOutputStream(uri)?.use { stream ->
                if (targetBook != null) {
                  CoursePdfExporter.exportBookToPdf(targetBook, stream)
                } else {
                  CoursePdfExporter.exportAllBooksToPdf(GeomancyCoursesCatalog.allBooks, stream)
                }
              } ?: error("Impossible d'ouvrir le fichier PDF de destination.")
            }
          }
        isExporting = false
        val feedback =
          result.fold(
            onSuccess = {
              if (targetBook != null) {
                "Cours PDF enregistré : ${targetBook.frenchTitle}"
              } else {
                "Cursus complet PDF enregistré ($totalBooksCount traités)."
              }
            },
            onFailure = { err ->
              "Échec de l'export PDF : ${err.localizedMessage ?: "erreur inconnue"}"
            },
          )
        if (onExportMessage != null) {
          onExportMessage(feedback)
        } else {
          Toast.makeText(context, feedback, Toast.LENGTH_LONG).show()
        }
      }
    }

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("courses_pdf_export_card"),
    shape = RoundedCornerShape(16.dp),
  ) {
    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "Exporter les cours fidèles en PDF",
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.primary,
        )
        if (isExporting) {
          CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
        }
      }
      FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Button(
          onClick = {
            pendingBookToExport = activeBook
            pdfLauncher.launch(CoursePdfExporter.suggestedPdfFileName(activeBook))
          },
          enabled = !isExporting,
          modifier =
            Modifier.minimumInteractiveComponentSize().testTag("export_book_pdf_button"),
        ) {
          Icon(
            imageVector = Icons.Rounded.PictureAsPdf,
            contentDescription = "Exporter ce livre en PDF",
            modifier = Modifier.size(18.dp),
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text("Exporter ce livre (PDF)", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }

        OutlinedButton(
          onClick = {
            pendingBookToExport = null
            pdfLauncher.launch(CoursePdfExporter.suggestedPdfFileName(null))
          },
          enabled = !isExporting,
          modifier =
            Modifier.minimumInteractiveComponentSize().testTag("export_all_courses_pdf_button"),
        ) {
          Icon(
            imageVector = Icons.Rounded.PictureAsPdf,
            contentDescription = "Exporter tous les cours en PDF",
            modifier = Modifier.size(18.dp),
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text("Tout le cursus ($totalBooksCount livres)", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
      }
    }
  }
}

@Composable
private fun CurriculumProgressBanner(
  completedCount: Int,
  totalCount: Int,
  bookCount: Int,
) {
  val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("courses_progress_banner"),
    shape = RoundedCornerShape(18.dp),
    elevated = true,
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Cursus Complet en Français · $bookCount Livres",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
          )
          Text(
            text =
              "Cours fidèlement traduits des traités originaux, débutant par Cheikh al-Ṭūkhī (Bulūgh al-Amal & Manbaʿ Uṣūl al-Raml en 80 leçons), suivis d'al-Baḥrī, al-Qawl al-Jāmiʿ, Ḥāwī al-Akhbār et des manuscrits MS 2380 / 2405.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
          shape = RoundedCornerShape(999.dp),
          color = MaterialTheme.colorScheme.primaryContainer,
        ) {
          Text(
            text = "$completedCount / $totalCount",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
          )
        }
      }
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier.fillMaxWidth().height(6.dp),
      )
    }
  }
}

@Composable
private fun CourseBookSelectorCard(
  book: CourseBook,
  isSelected: Boolean,
  completedInBook: Int,
  onClick: () -> Unit,
) {
  val extColors = AlRamlColors.extended
  val accentColor = extColors.forElement(book.accentElement)

  LiquidGlassCard(
    modifier =
      Modifier.width(230.dp)
        .minimumInteractiveComponentSize()
        .clickable(onClick = onClick)
        .testTag("course_book_card_${book.id}"),
    shape = RoundedCornerShape(16.dp),
    elevated = isSelected,
    accentBorderColor = if (isSelected) accentColor else null,
  ) {
    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = accentColor.copy(alpha = 0.16f),
        ) {
          Text(
            text = "Livre ${book.orderIndex}",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = accentColor,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
          )
        }
        Text(
          text = "$completedInBook/${book.modules.size} mod.",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
      Text(
        text = book.frenchTitle,
        style = MaterialTheme.typography.titleSmall,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
      )
      Text(
        text = book.author,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}

@Composable
private fun ActiveCourseBookCard(
  book: CourseBook,
  completedInBook: Int,
) {
  val accentColor = AlRamlColors.extended.forElement(book.accentElement)
  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("active_course_book_header"),
    shape = RoundedCornerShape(18.dp),
    elevated = true,
    accentBorderColor = accentColor,
  ) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = book.levelBadge,
          style = MaterialTheme.typography.labelMedium,
          color = accentColor,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "$completedInBook/${book.modules.size} assimilés",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
      Text(
        text = book.frenchTitle,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
      )
      Text(
        text = book.arabicTitle,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Rounded.MenuBook,
            contentDescription = "Référence du livre",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp),
          )
          Column {
            Text(
              text = "Auteur : ${book.author}",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
            Text(
              text = "Source : ${book.editionOrManuscript} (${book.totalPagesOrFolios})",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
      Text(
        text = book.overview,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
    }
  }
}

@Composable
private fun CourseModuleCard(
  module: CourseModule,
  isExpanded: Boolean,
  isCompleted: Boolean,
  onToggleExpanded: () -> Unit,
  onToggleCompleted: () -> Unit,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
  onOpenTakhtExample: (CourseTakhtExample) -> Unit,
) {
  val extColors = AlRamlColors.extended
  val borderColor =
    when {
      isCompleted -> extColors.fortunate
      isExpanded -> MaterialTheme.colorScheme.primary
      else -> null
    }

  LiquidGlassCard(
    modifier =
      Modifier.fillMaxWidth()
        .testTag("course_module_card_${module.id}"),
    shape = RoundedCornerShape(18.dp),
    elevated = isExpanded,
    accentBorderColor = borderColor,
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        modifier =
          Modifier.fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clickable(onClick = onToggleExpanded)
            .testTag("course_module_header_${module.id}"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.primaryContainer,
            ) {
              Text(
                text = "${module.moduleNumber} · ${module.chapterRef}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
              )
            }
            if (isCompleted) {
              Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = "Module assimilé",
                tint = extColors.fortunate,
                modifier = Modifier.size(16.dp),
              )
            }
          }
          Text(
            text = module.title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = "Livre : ${module.bookCitation}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
          )
        }
        Icon(
          imageVector = if (isExpanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
          contentDescription = if (isExpanded) "Réduire le module" else "Déplier le module",
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      AnimatedVisibility(visible = isExpanded) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
          Text(
            text = module.summary,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
          )

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f),
            border = BorderStroke(1.dp, extColors.cardBorder),
          ) {
            Column(
              modifier = Modifier.fillMaxWidth().padding(12.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
              Text(
                text = "Règles doctrinales du livre",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
              )
              module.classicalRules.forEachIndexed { idx, rule ->
                Text(
                  text = "${idx + 1}. $rule",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.onSurface,
                )
              }
            }
          }

          if (module.detailedLessons.isNotEmpty()) {
            Column(
              verticalArrangement = Arrangement.spacedBy(8.dp),
              modifier = Modifier.testTag("course_detailed_lessons_${module.id}"),
            ) {
              Text(
                text = "Cours complets traduits de la source (${module.detailedLessons.size})",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
              )
              module.detailedLessons.forEach { lesson ->
                CourseLessonSectionCard(lesson = lesson)
              }
            }
          }

          if (module.featuredFigures.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Text(
                text = "Figures à l'appui (appuyer pour détailler)",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
              )
              module.featuredFigures.forEach { focus ->
                CourseFigureFocusRow(
                  moduleId = module.id,
                  focus = focus,
                  onClick = { onOpenFigureDetail(focus.figure) },
                )
              }
            }
          }

          module.takhtExample?.let { example ->
            CourseTakhtPreview(
              moduleId = module.id,
              example = example,
              onOpenFigureDetail = onOpenFigureDetail,
              onOpenTakhtExample = onOpenTakhtExample,
            )
          }

          if (isCompleted) {
            OutlinedButton(
              onClick = onToggleCompleted,
              modifier =
                Modifier.fillMaxWidth()
                  .minimumInteractiveComponentSize()
                  .testTag("course_toggle_complete_${module.id}"),
            ) {
              Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = null,
                tint = extColors.fortunate,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("Module assimilé (retirer)")
            }
          } else {
            Button(
              onClick = onToggleCompleted,
              modifier =
                Modifier.fillMaxWidth()
                  .minimumInteractiveComponentSize()
                  .testTag("course_toggle_complete_${module.id}"),
            ) {
              Icon(
                imageVector = Icons.Rounded.RadioButtonUnchecked,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text("Marquer le module comme assimilé")
            }
          }
        }
      }
    }
  }
}

@Composable
private fun CourseLessonSectionCard(lesson: CourseLessonSection) {
  val extColors = AlRamlColors.extended
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.78f),
    border = BorderStroke(1.dp, extColors.cardBorder),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth().padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = MaterialTheme.colorScheme.secondaryContainer,
        ) {
          Text(
            text = lesson.lessonRef,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp),
          )
        }
        Text(
          text = lesson.heading,
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.weight(1f),
        )
      }
      if (lesson.arabicTerms.isNotBlank()) {
        Text(
          text = lesson.arabicTerms,
          style = MaterialTheme.typography.labelSmall,
          fontStyle = FontStyle.Italic,
          color = MaterialTheme.colorScheme.primary,
        )
      }
      Text(
        text = lesson.body,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
      )
      if (lesson.keyFormulaOrTable.isNotBlank()) {
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
        ) {
          Text(
            text = lesson.keyFormulaOrTable,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 9.dp, vertical = 6.dp),
          )
        }
      }
    }
  }
}

@Composable
private fun CourseFigureFocusRow(
  moduleId: String,
  focus: CourseFigureFocus,
  onClick: () -> Unit,
) {
  val fig = focus.figure
  val accentColor = AlRamlColors.extended.forElement(fig.dominantElement)
  Surface(
    modifier =
      Modifier.fillMaxWidth()
        .minimumInteractiveComponentSize()
        .clickable(onClick = onClick)
        .testTag("course_figure_${moduleId}_${fig.id}"),
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.45f)),
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      GeomanticFigureGlyph(
        figure = fig,
        size = 26.dp,
        useElementColors = true,
      )
      Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
          text = "${fig.westAfricanName} · ${fig.transliteration} (${fig.arabicName})",
          style = MaterialTheme.typography.labelLarge,
          color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
          text = focus.roleInLesson,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
}
