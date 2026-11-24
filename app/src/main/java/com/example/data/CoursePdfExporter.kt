package com.example.data

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import com.example.geomancy.CourseBook
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.PointParity
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Générateur PDF multi-pages A4 (595 × 842 pts) pour exporter un livre sélectionné ou
 * l'intégralité des 7 traités et manuscrits (avec leçons traduites, formules,
 * figures géomantiques dessinées et thèmes Takht à 16 maisons).
 *
 * Utilise nativement [android.graphics.pdf.PdfDocument] sur appareil Android, avec repli
 * autonome vers un encodeur PDF 1.4 pur Kotlin si l'environnement (ex. JVM/Robolectric)
 * ne fournit pas la bibliothèque native Skia PDF.
 */
object CoursePdfExporter {

  private const val PAGE_WIDTH = 595
  private const val PAGE_HEIGHT = 842
  private const val MARGIN_LEFT = 40f
  private const val MARGIN_RIGHT = 40f
  private const val MARGIN_TOP = 44f
  private const val MARGIN_BOTTOM = 44f
  private const val CONTENT_WIDTH = PAGE_WIDTH - MARGIN_LEFT - MARGIN_RIGHT

  fun suggestedPdfFileName(book: CourseBook?): String {
    if (book == null) {
      return "al_raml_cours_complet_7_livres.pdf"
    }
    val slug =
      book.id
        .lowercase(Locale.ROOT)
        .replace(Regex("[^a-z0-9]+"), "_")
        .trim('_')
        .ifEmpty { "livre_${book.orderIndex}" }
    return "al_raml_cours_${slug}.pdf"
  }

  fun exportBookToPdfBytes(book: CourseBook): ByteArray {
    val baos = ByteArrayOutputStream()
    exportBookToPdf(book, baos)
    return baos.toByteArray()
  }

  fun exportAllBooksToPdfBytes(
    books: List<CourseBook> = GeomancyCoursesCatalog.allBooks,
  ): ByteArray {
    val baos = ByteArrayOutputStream()
    exportAllBooksToPdf(books, baos)
    return baos.toByteArray()
  }

  fun exportBookToPdf(book: CourseBook, outputStream: OutputStream) {
    exportBooksInternal(
      books = listOf(book),
      documentTitle = book.frenchTitle,
      documentSubtitle = "${book.author} · ${book.editionOrManuscript}",
      outputStream = outputStream,
    )
  }

  fun exportAllBooksToPdf(
    books: List<CourseBook> = GeomancyCoursesCatalog.allBooks,
    outputStream: OutputStream,
  ) {
    exportBooksInternal(
      books = books,
      documentTitle = "Al-Raml Al-Toukhi — Cursus Complet en Français (${books.size} Traités)",
      documentSubtitle =
        "Bulūgh al-Amal & Manbaʿ Uṣūl al-Raml (80 Leçons d'Al-Ṭūkhī), Al-Fulk al-Mashḥūn, Al-Qawl al-Jāmiʿ, Ḥāwī al-Akhbār, MS 2380 & MS 2405",
      outputStream = outputStream,
    )
  }

  private fun exportBooksInternal(
    books: List<CourseBook>,
    documentTitle: String,
    documentSubtitle: String,
    outputStream: OutputStream,
  ) {
    val exportedDateLabel = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE).format(Date())
    val renderedNative =
      tryExportWithAndroidPdfDocument(
        books = books,
        documentTitle = documentTitle,
        documentSubtitle = documentSubtitle,
        exportedDateLabel = exportedDateLabel,
        outputStream = outputStream,
      )
    if (!renderedNative) {
      PurePdfDocumentWriter.write(
        books = books,
        documentTitle = documentTitle,
        documentSubtitle = documentSubtitle,
        exportedDateLabel = exportedDateLabel,
        outputStream = outputStream,
      )
    }
  }

  private fun tryExportWithAndroidPdfDocument(
    books: List<CourseBook>,
    documentTitle: String,
    documentSubtitle: String,
    exportedDateLabel: String,
    outputStream: OutputStream,
  ): Boolean {
    var document: PdfDocument? = null
    return try {
      document = PdfDocument()
      val writer = PdfPageWriter(document, documentTitle, exportedDateLabel)
      writer.drawCoverHeader(documentTitle, documentSubtitle, books)
      books.forEach { book ->
        writer.drawBookSection(book)
      }
      writer.finishCurrentPage()
      document.writeTo(outputStream)
      outputStream.flush()
      true
    } catch (_: Throwable) {
      false
    } finally {
      runCatching { document?.close() }
    }
  }

  private class PdfPageWriter(
    private val document: PdfDocument,
    private val runningHeader: String,
    private val exportedDateLabel: String,
  ) {
    private val goldColor = Color.rgb(122, 86, 24)
    private val darkInk = Color.rgb(31, 36, 33)
    private val mutedInk = Color.rgb(92, 95, 92)
    private val boxFill = Color.rgb(248, 243, 232)
    private val boxStroke = Color.rgb(214, 194, 152)
    private val highlightFill = Color.rgb(241, 231, 206)

    private val titlePaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = goldColor
        textSize = 18f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
      }
    private val h1Paint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = goldColor
        textSize = 14.5f
        typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
      }
    private val h2Paint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = darkInk
        textSize = 12f
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
      }
    private val h3Paint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = goldColor
        textSize = 10.5f
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
      }
    private val bodyPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = darkInk
        textSize = 9.8f
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
      }
    private val italicPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mutedInk
        textSize = 9.2f
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC)
      }
    private val formulaPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(66, 46, 12)
        textSize = 9.2f
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
      }
    private val footerPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mutedInk
        textSize = 8f
        typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
      }
    private val fillBoxPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = boxFill
      }
    private val fillHighlightPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = highlightFill
      }
    private val strokeBoxPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 0.8f
        color = boxStroke
      }
    private val dotPaint =
      Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = goldColor
      }

    private var pageNumber = 0
    private var currentPage: PdfDocument.Page? = null
    private var canvas: Canvas? = null
    private var cursorY = MARGIN_TOP

    init {
      startNewPage()
    }

    private fun startNewPage() {
      currentPage?.let { page ->
        drawFooter(page.canvas)
        document.finishPage(page)
      }
      pageNumber++
      val pageInfo = PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create()
      val newPage = document.startPage(pageInfo)
      currentPage = newPage
      canvas = newPage.canvas
      cursorY = MARGIN_TOP

      if (pageNumber > 1) {
        val headerShort = runningHeader.take(78)
        newPage.canvas.drawText(headerShort, MARGIN_LEFT, 26f, footerPaint)
        newPage.canvas.drawLine(MARGIN_LEFT, 31f, PAGE_WIDTH - MARGIN_RIGHT, 31f, strokeBoxPaint)
        cursorY = MARGIN_TOP + 4f
      }
    }

    private fun drawFooter(pageCanvas: Canvas) {
      val bottomY = PAGE_HEIGHT - 22f
      pageCanvas.drawLine(MARGIN_LEFT, bottomY - 10f, PAGE_WIDTH - MARGIN_RIGHT, bottomY - 10f, strokeBoxPaint)
      pageCanvas.drawText("Al-Raml Al-Toukhi · Export le $exportedDateLabel", MARGIN_LEFT, bottomY, footerPaint)
      val pageText = "Page $pageNumber"
      val textWidth = footerPaint.measureText(pageText)
      pageCanvas.drawText(pageText, PAGE_WIDTH - MARGIN_RIGHT - textWidth, bottomY, footerPaint)
    }

    fun finishCurrentPage() {
      currentPage?.let { page ->
        drawFooter(page.canvas)
        document.finishPage(page)
      }
      currentPage = null
      canvas = null
    }

    private fun ensureSpace(neededHeight: Float) {
      if (cursorY + neededHeight > PAGE_HEIGHT - MARGIN_BOTTOM) {
        startNewPage()
      }
    }

    private fun drawWrappedText(
      text: String,
      paint: Paint,
      x: Float = MARGIN_LEFT,
      maxWidth: Float = CONTENT_WIDTH,
      extraBottomGap: Float = 4f,
    ) {
      val lineHeight = paint.textSize * 1.38f
      val lines = wrapLines(text, paint, maxWidth)
      for (line in lines) {
        ensureSpace(lineHeight + 2f)
        canvas?.drawText(line, x, cursorY + paint.textSize, paint)
        cursorY += lineHeight
      }
      cursorY += extraBottomGap
    }

    fun drawCoverHeader(
      title: String,
      subtitle: String,
      books: List<CourseBook>,
    ) {
      val titleLines = wrapLines(title, titlePaint, CONTENT_WIDTH - 24f)
      val subtitleLines = wrapLines(subtitle, italicPaint, CONTENT_WIDTH - 24f)
      val boxHeight =
        24f + titleLines.size * (titlePaint.textSize * 1.35f) +
          subtitleLines.size * (italicPaint.textSize * 1.35f) + 22f
      ensureSpace(boxHeight + 12f)
      val rect = RectF(MARGIN_LEFT, cursorY, PAGE_WIDTH - MARGIN_RIGHT, cursorY + boxHeight)
      canvas?.drawRoundRect(rect, 10f, 10f, fillHighlightPaint)
      canvas?.drawRoundRect(rect, 10f, 10f, strokeBoxPaint)

      cursorY += 16f
      titleLines.forEach { line ->
        canvas?.drawText(line, MARGIN_LEFT + 12f, cursorY + titlePaint.textSize, titlePaint)
        cursorY += titlePaint.textSize * 1.35f
      }
      cursorY += 4f
      subtitleLines.forEach { line ->
        canvas?.drawText(line, MARGIN_LEFT + 12f, cursorY + italicPaint.textSize, italicPaint)
        cursorY += italicPaint.textSize * 1.35f
      }
      canvas?.drawText(
        "Document généré le $exportedDateLabel · ${books.size} ouvrage(s) · ${books.sumOf { it.modules.size }} modules",
        MARGIN_LEFT + 12f,
        cursorY + bodyPaint.textSize + 2f,
        bodyPaint,
      )
      cursorY = rect.bottom + 14f

      if (books.size > 1) {
        drawWrappedText("Sommaire des Traités & Manuscrits", h2Paint, extraBottomGap = 4f)
        books.forEach { book ->
          val lessonsCount = book.modules.sumOf { it.detailedLessons.size }
          drawWrappedText(
            "• Livre ${book.orderIndex} — ${book.frenchTitle} (${book.modules.size} modules, $lessonsCount leçons/chapitres) · ${book.author}",
            bodyPaint,
            x = MARGIN_LEFT + 8f,
            maxWidth = CONTENT_WIDTH - 8f,
            extraBottomGap = 2f,
          )
        }
        cursorY += 8f
      }
    }

    fun drawBookSection(book: CourseBook) {
      ensureSpace(95f)
      val headerText = "Livre ${book.orderIndex} · ${book.frenchTitle}"
      val headerLines = wrapLines(headerText, h1Paint, CONTENT_WIDTH - 20f)
      val metaLine = "Auteur : ${book.author} · Source : ${book.editionOrManuscript} (${book.totalPagesOrFolios})"
      val metaLines = wrapLines(metaLine, bodyPaint, CONTENT_WIDTH - 20f)
      val boxHeight =
        18f + headerLines.size * (h1Paint.textSize * 1.35f) +
          (italicPaint.textSize * 1.4f) +
          metaLines.size * (bodyPaint.textSize * 1.35f) + 10f

      ensureSpace(boxHeight + 8f)
      val rect = RectF(MARGIN_LEFT, cursorY, PAGE_WIDTH - MARGIN_RIGHT, cursorY + boxHeight)
      canvas?.drawRoundRect(rect, 8f, 8f, fillBoxPaint)
      canvas?.drawRoundRect(rect, 8f, 8f, strokeBoxPaint)

      cursorY += 12f
      headerLines.forEach { line ->
        canvas?.drawText(line, MARGIN_LEFT + 10f, cursorY + h1Paint.textSize, h1Paint)
        cursorY += h1Paint.textSize * 1.35f
      }
      canvas?.drawText(
        "${book.levelBadge} — ${book.arabicTitle}".take(90),
        MARGIN_LEFT + 10f,
        cursorY + italicPaint.textSize,
        italicPaint,
      )
      cursorY += italicPaint.textSize * 1.4f
      metaLines.forEach { line ->
        canvas?.drawText(line, MARGIN_LEFT + 10f, cursorY + bodyPaint.textSize, bodyPaint)
        cursorY += bodyPaint.textSize * 1.35f
      }
      cursorY = rect.bottom + 8f

      drawWrappedText(book.overview, bodyPaint, extraBottomGap = 8f)

      book.modules.forEach { module ->
        drawModuleSection(module)
      }
    }

    private fun drawModuleSection(module: com.example.geomancy.CourseModule) {
      ensureSpace(72f)
      drawWrappedText("${module.moduleNumber} · ${module.chapterRef} — ${module.title}", h2Paint, extraBottomGap = 2f)
      drawWrappedText("Référence source : ${module.bookCitation}", italicPaint, extraBottomGap = 4f)
      drawWrappedText(module.summary, bodyPaint, extraBottomGap = 5f)

      if (module.classicalRules.isNotEmpty()) {
        drawWrappedText("Règles doctrinales du traité :", h3Paint, extraBottomGap = 2f)
        module.classicalRules.forEachIndexed { idx, rule ->
          drawWrappedText(
            "${idx + 1}. $rule",
            bodyPaint,
            x = MARGIN_LEFT + 10f,
            maxWidth = CONTENT_WIDTH - 10f,
            extraBottomGap = 2.5f,
          )
        }
        cursorY += 3f
      }

      if (module.detailedLessons.isNotEmpty()) {
        drawWrappedText("Cours & Chapitres détaillés traduits de la source :", h3Paint, extraBottomGap = 3f)
        module.detailedLessons.forEach { lesson ->
          ensureSpace(48f)
          drawWrappedText(
            "${lesson.lessonRef} — ${lesson.heading}",
            h3Paint,
            x = MARGIN_LEFT + 6f,
            maxWidth = CONTENT_WIDTH - 6f,
            extraBottomGap = 1.5f,
          )
          if (lesson.arabicTerms.isNotBlank()) {
            drawWrappedText(
              "Termes arabes : ${lesson.arabicTerms}",
              italicPaint,
              x = MARGIN_LEFT + 10f,
              maxWidth = CONTENT_WIDTH - 10f,
              extraBottomGap = 2f,
            )
          }
          drawWrappedText(
            lesson.body,
            bodyPaint,
            x = MARGIN_LEFT + 10f,
            maxWidth = CONTENT_WIDTH - 10f,
            extraBottomGap = 3f,
          )
          if (lesson.keyFormulaOrTable.isNotBlank()) {
            val fLines = wrapLines(lesson.keyFormulaOrTable, formulaPaint, CONTENT_WIDTH - 28f)
            val fHeight = fLines.size * (formulaPaint.textSize * 1.35f) + 10f
            ensureSpace(fHeight + 4f)
            val fRect = RectF(MARGIN_LEFT + 10f, cursorY, PAGE_WIDTH - MARGIN_RIGHT - 4f, cursorY + fHeight)
            canvas?.drawRoundRect(fRect, 5f, 5f, fillHighlightPaint)
            canvas?.drawRoundRect(fRect, 5f, 5f, strokeBoxPaint)
            var fy = cursorY + 5f
            fLines.forEach { fl ->
              canvas?.drawText(fl, MARGIN_LEFT + 16f, fy + formulaPaint.textSize, formulaPaint)
              fy += formulaPaint.textSize * 1.35f
            }
            cursorY = fRect.bottom + 5f
          }
        }
      }

      if (module.featuredFigures.isNotEmpty()) {
        drawWrappedText("Figures à l'appui :", h3Paint, extraBottomGap = 3f)
        module.featuredFigures.forEach { focus ->
          val fig = focus.figure
          val descText = "${fig.westAfricanName} · ${fig.transliteration} (${fig.code}) — ${focus.roleInLesson}"
          val lines = wrapLines(descText, bodyPaint, CONTENT_WIDTH - 34f)
          val needed = maxOf(28f, lines.size * (bodyPaint.textSize * 1.38f) + 4f)
          ensureSpace(needed)
          drawFigureGlyph(canvas, fig, MARGIN_LEFT + 10f, cursorY + 2f, 22f)
          var textY = cursorY
          lines.forEach { line ->
            canvas?.drawText(line, MARGIN_LEFT + 34f, textY + bodyPaint.textSize, bodyPaint)
            textY += bodyPaint.textSize * 1.38f
          }
          cursorY += needed
        }
        cursorY += 3f
      }

      module.takhtExample?.let { example ->
        drawTakhtExampleBlock(example)
      }

      cursorY += 6f
      ensureSpace(8f)
      canvas?.drawLine(MARGIN_LEFT, cursorY, PAGE_WIDTH - MARGIN_RIGHT, cursorY, strokeBoxPaint)
      cursorY += 10f
    }

    private fun drawTakhtExampleBlock(example: com.example.geomancy.CourseTakhtExample) {
      val reading = example.computeReading()
      drawWrappedText("Thème Takht d'application : ${example.title}", h3Paint, extraBottomGap = 2f)
      drawWrappedText("Source : ${example.sourceRef} · Question : ${example.question}", italicPaint, extraBottomGap = 4f)

      val cellWidth = CONTENT_WIDTH / 4f
      val cellHeight = 34f
      for (row in 0..3) {
        ensureSpace(cellHeight + 4f)
        for (col in 0..3) {
          val houseNumber = row * 4 + col + 1
          val house = reading.house(houseNumber)
          val x = MARGIN_LEFT + col * cellWidth
          val rect = RectF(x + 2f, cursorY, x + cellWidth - 2f, cursorY + cellHeight)
          canvas?.drawRoundRect(rect, 4f, 4f, fillBoxPaint)
          canvas?.drawRoundRect(rect, 4f, 4f, strokeBoxPaint)
          drawFigureGlyph(canvas, house.figure, x + 7f, cursorY + 5f, 24f)
          val label = "M$houseNumber ${house.figure.westAfricanName.take(11)}"
          val sub = house.figure.transliteration.take(14)
          canvas?.drawText(label, x + 28f, cursorY + 13f, formulaPaint)
          canvas?.drawText(sub, x + 28f, cursorY + 25f, footerPaint)
        }
        cursorY += cellHeight + 4f
      }

      example.interpretationSteps.forEachIndexed { idx, step ->
        drawWrappedText(
          "• $step",
          bodyPaint,
          x = MARGIN_LEFT + 10f,
          maxWidth = CONTENT_WIDTH - 10f,
          extraBottomGap = if (idx == example.interpretationSteps.lastIndex) 4f else 2f,
        )
      }
    }

    private fun drawFigureGlyph(
      targetCanvas: Canvas?,
      figure: GeomanticFigure,
      x: Float,
      y: Float,
      totalHeight: Float,
    ) {
      targetCanvas ?: return
      val stepY = totalHeight / 4f
      val radius = 1.9f
      figure.ranks.forEachIndexed { idx, parity ->
        val cy = y + idx * stepY + stepY * 0.5f
        if (parity == PointParity.ODD) {
          targetCanvas.drawCircle(x + 6f, cy, radius, dotPaint)
        } else {
          targetCanvas.drawCircle(x + 3.2f, cy, radius, dotPaint)
          targetCanvas.drawCircle(x + 8.8f, cy, radius, dotPaint)
        }
      }
    }

    private fun wrapLines(text: String, paint: Paint, maxWidth: Float): List<String> {
      val result = mutableListOf<String>()
      val safeWidth = maxWidth.coerceAtLeast(60f)
      val paragraphs = text.split('\n')
      for (paragraph in paragraphs) {
        val trimmed = paragraph.trim()
        if (trimmed.isEmpty()) {
          result.add("")
          continue
        }
        val words = trimmed.split(Regex("\\s+"))
        var currentLine = StringBuilder()
        for (word in words) {
          val candidate = if (currentLine.isEmpty()) word else "$currentLine $word"
          if (paint.measureText(candidate) <= safeWidth) {
            currentLine = StringBuilder(candidate)
          } else {
            if (currentLine.isNotEmpty()) {
              result.add(currentLine.toString())
              currentLine = StringBuilder(word)
            } else {
              var chunk = word
              while (chunk.isNotEmpty()) {
                var cut = chunk.length
                while (cut > 1 && paint.measureText(chunk.substring(0, cut)) > safeWidth) {
                  cut--
                }
                result.add(chunk.substring(0, cut))
                chunk = chunk.substring(cut)
              }
              currentLine = StringBuilder()
            }
          }
        }
        if (currentLine.isNotEmpty()) {
          result.add(currentLine.toString())
        }
      }
      return if (result.isEmpty()) listOf("") else result
    }
  }

  /**
   * Encodeur PDF 1.4 autonome pur Kotlin assurant une génération valide multi-pages
   * même lorsque Skia native PDF n'est pas disponible (ex. tests JVM Robolectric).
   */
  private object PurePdfDocumentWriter {
    fun write(
      books: List<CourseBook>,
      documentTitle: String,
      documentSubtitle: String,
      exportedDateLabel: String,
      outputStream: OutputStream,
    ) {
      val pages = mutableListOf<StringBuilder>()
      var currentStream = StringBuilder()
      var cursorY = PAGE_HEIGHT - MARGIN_TOP
      var pageNumber = 0

      fun startPage() {
        if (pageNumber > 0) {
          currentStream.append(
            "0.48 0.34 0.09 RG 0.5 w $MARGIN_LEFT 34 m ${PAGE_WIDTH - MARGIN_RIGHT} 34 l S\n",
          )
          currentStream.append(pdfText(MARGIN_LEFT, 22f, 8f, false, "Al-Raml Al-Toukhi · Export le $exportedDateLabel · Page $pageNumber"))
          pages.add(currentStream)
        }
        pageNumber++
        currentStream = StringBuilder()
        cursorY = PAGE_HEIGHT - MARGIN_TOP
        if (pageNumber > 1) {
          currentStream.append(pdfText(MARGIN_LEFT, PAGE_HEIGHT - 26f, 8f, false, documentTitle.take(78)))
          currentStream.append(
            "0.80 0.72 0.55 RG 0.5 w $MARGIN_LEFT ${PAGE_HEIGHT - 31f} m ${PAGE_WIDTH - MARGIN_RIGHT} ${PAGE_HEIGHT - 31f} l S\n",
          )
          cursorY = PAGE_HEIGHT - MARGIN_TOP - 8f
        }
      }

      fun ensure(height: Float) {
        if (cursorY - height < MARGIN_BOTTOM) {
          startPage()
        }
      }

      fun emitWrapped(text: String, fontSize: Float, bold: Boolean, indent: Float = 0f, maxChars: Int = 86) {
        val lineStep = fontSize * 1.38f
        for (line in wrapChars(text, maxChars)) {
          ensure(lineStep)
          currentStream.append(pdfText(MARGIN_LEFT + indent, cursorY, fontSize, bold, line))
          cursorY -= lineStep
        }
        cursorY -= 3f
      }

      startPage()
      emitWrapped(documentTitle, 14f, true, maxChars = 68)
      emitWrapped(documentSubtitle, 9.5f, false, maxChars = 88)
      emitWrapped("Document genere le $exportedDateLabel · ${books.size} ouvrage(s) · ${books.sumOf { it.modules.size }} modules", 9.5f, false)

      books.forEach { book ->
        ensure(60f)
        emitWrapped("Livre ${book.orderIndex} : ${book.frenchTitle}", 12.5f, true, maxChars = 72)
        emitWrapped("Auteur : ${book.author} · Source : ${book.editionOrManuscript} (${book.totalPagesOrFolios})", 9.5f, false)
        emitWrapped(book.overview, 9.5f, false)
        book.modules.forEach { module ->
          ensure(48f)
          emitWrapped("${module.moduleNumber} · ${module.chapterRef} — ${module.title}", 11f, true, maxChars = 76)
          emitWrapped("Reference source : ${module.bookCitation}", 9f, false)
          emitWrapped(module.summary, 9.5f, false)
          if (module.classicalRules.isNotEmpty()) {
            emitWrapped("Regles doctrinales :", 9.5f, true)
            module.classicalRules.forEachIndexed { idx, rule ->
              emitWrapped("${idx + 1}. $rule", 9f, false, indent = 8f, maxChars = 82)
            }
          }
          if (module.detailedLessons.isNotEmpty()) {
            emitWrapped("Cours fideles traduits de la source :", 9.5f, true)
            module.detailedLessons.forEach { lesson ->
              emitWrapped("${lesson.lessonRef} — ${lesson.heading}", 9.5f, true, indent = 6f, maxChars = 80)
              emitWrapped(lesson.body, 9f, false, indent = 10f, maxChars = 82)
              if (lesson.keyFormulaOrTable.isNotBlank()) {
                emitWrapped("Formule : ${lesson.keyFormulaOrTable}", 8.8f, true, indent = 10f, maxChars = 82)
              }
            }
          }
          if (module.featuredFigures.isNotEmpty()) {
            emitWrapped("Figures a l'appui :", 9.5f, true)
            module.featuredFigures.forEach { focus ->
              val fig = focus.figure
              val lineStep = 12.5f
              ensure(18f)
              drawPureGlyph(currentStream, fig, MARGIN_LEFT + 6f, cursorY - 11f)
              emitWrapped("${fig.westAfricanName} · ${fig.transliteration} (${fig.code}) — ${focus.roleInLesson}", 9f, false, indent = 22f, maxChars = 78)
              if (cursorY > lineStep) cursorY -= 1f
            }
          }
          module.takhtExample?.let { ex ->
            val reading = ex.computeReading()
            emitWrapped("Exemple Takht : ${ex.title} (${ex.sourceRef})", 9.5f, true)
            val housesSummary =
              (1..16).joinToString(" | ") { h -> "M$h:${reading.house(h).figure.westAfricanName}" }
            emitWrapped(housesSummary, 8.5f, false, indent = 6f, maxChars = 88)
            ex.interpretationSteps.forEach { step ->
              emitWrapped("- $step", 8.8f, false, indent = 8f, maxChars = 82)
            }
          }
        }
      }

      currentStream.append("0.48 0.34 0.09 RG 0.5 w $MARGIN_LEFT 34 m ${PAGE_WIDTH - MARGIN_RIGHT} 34 l S\n")
      currentStream.append(pdfText(MARGIN_LEFT, 22f, 8f, false, "Al-Raml Al-Toukhi · Export le $exportedDateLabel · Page $pageNumber"))
      pages.add(currentStream)

      val objects = mutableListOf<String>()
      val pageObjNums = mutableListOf<Int>()
      // 1 = Catalog, 2 = Pages, 3 = Font Regular, 4 = Font Bold
      objects.add("") // placeholder 1
      objects.add("") // placeholder 2
      objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>")
      objects.add("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >>")

      pages.forEach { streamBuilder ->
        val contentBytes = streamBuilder.toString().toByteArray(Charsets.ISO_8859_1)
        val contentObjNum = objects.size + 1
        objects.add("<< /Length ${contentBytes.size} >>\nstream\n${streamBuilder}endstream")
        val pageObjNum = objects.size + 1
        pageObjNums.add(pageObjNum)
        objects.add(
          "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 $PAGE_WIDTH $PAGE_HEIGHT] " +
            "/Resources << /Font << /F1 3 0 R /F2 4 0 R >> >> /Contents $contentObjNum 0 R >>",
        )
      }
      val kids = pageObjNums.joinToString(" ") { "$it 0 R" }
      objects[0] = "<< /Type /Catalog /Pages 2 0 R >>"
      objects[1] = "<< /Type /Pages /Kids [$kids] /Count ${pageObjNums.size} >>"

      val out = ByteArrayOutputStream()
      fun writeAscii(s: String) = out.write(s.toByteArray(Charsets.ISO_8859_1))
      writeAscii("%PDF-1.4\n")
      val offsets = mutableListOf<Int>()
      objects.forEachIndexed { idx, body ->
        offsets.add(out.size())
        writeAscii("${idx + 1} 0 obj\n$body\nendobj\n")
      }
      val xrefStart = out.size()
      writeAscii("xref\n0 ${objects.size + 1}\n")
      writeAscii("0000000000 65535 f \n")
      offsets.forEach { offset ->
        writeAscii(String.format(Locale.US, "%010d 00000 n \n", offset))
      }
      writeAscii("trailer\n<< /Size ${objects.size + 1} /Root 1 0 R >>\nstartxref\n$xrefStart\n%%EOF\n")
      out.writeTo(outputStream)
      outputStream.flush()
    }

    private fun drawPureGlyph(sb: StringBuilder, figure: GeomanticFigure, x: Float, yBottom: Float) {
      sb.append("0.48 0.34 0.09 rg ")
      figure.ranks.forEachIndexed { idx, parity ->
        val y = yBottom + (3 - idx) * 3.5f
        if (parity == PointParity.ODD) {
          sb.append("${x + 4f} $y 2.2 2.2 re f ")
        } else {
          sb.append("${x + 1.5f} $y 2.2 2.2 re f ${x + 6.5f} $y 2.2 2.2 re f ")
        }
      }
      sb.append("0 0 0 rg\n")
    }

    private fun pdfText(x: Float, y: Float, size: Float, bold: Boolean, raw: String): String {
      val fontRef = if (bold) "/F2" else "/F1"
      val sanitized = sanitizeForWinAnsi(raw)
      return "BT $fontRef $size Tf $x $y Td ($sanitized) Tj ET\n"
    }

    private fun sanitizeForWinAnsi(input: String): String {
      val mapped =
        input
          .replace("ʿ", "'")
          .replace("ʾ", "'")
          .replace("ḍ", "d").replace("Ḍ", "D")
          .replace("ḥ", "h").replace("Ḥ", "H")
          .replace("ṣ", "s").replace("Ṣ", "S")
          .replace("ṭ", "t").replace("Ṭ", "T")
          .replace("ẓ", "z").replace("Ẓ", "Z")
          .replace("ū", "u").replace("Ū", "U")
          .replace("ī", "i").replace("Ī", "I")
          .replace("ā", "a").replace("Ā", "A")
          .replace("⊕", "+")
          .replace("→", "->")
          .replace("↔", "<->")
          .replace("≥", ">=")
          .replace("≤", "<=")
          .replace("·", "-")
          .replace("—", "-")
          .replace("’", "'")
          .replace("«", "\"")
          .replace("»", "\"")
      val out = StringBuilder(mapped.length)
      for (ch in mapped) {
        val code = ch.code
        when {
          ch == '\\' -> out.append("\\\\")
          ch == '(' -> out.append("\\(")
          ch == ')' -> out.append("\\)")
          code in 32..126 || code in 160..255 -> out.append(ch)
          else -> {
            val nfd = Normalizer.normalize(ch.toString(), Normalizer.Form.NFD)
              .filter { it.code in 32..126 || it.code in 160..255 }
            if (nfd.isNotEmpty()) out.append(nfd)
          }
        }
      }
      return out.toString()
    }

    private fun wrapChars(text: String, maxChars: Int): List<String> {
      val res = mutableListOf<String>()
      text.split('\n').forEach { para ->
        val words = para.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        if (words.isEmpty()) {
          res.add("")
        } else {
          var line = StringBuilder()
          words.forEach { w ->
            if (line.isEmpty()) {
              line.append(w)
            } else if (line.length + 1 + w.length <= maxChars) {
              line.append(' ').append(w)
            } else {
              res.add(line.toString())
              line = StringBuilder(w)
            }
          }
          if (line.isNotEmpty()) res.add(line.toString())
        }
      }
      return res
    }
  }
}
