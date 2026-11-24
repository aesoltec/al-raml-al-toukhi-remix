package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.geomancy.CourseTakhtExample
import com.example.geomancy.Element
import com.example.geomancy.GeomancyCoursesCatalog
import com.example.geomancy.GeomanticFigure
import com.example.ui.components.SectionHeaderCard
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard

private data class TimelineMilestone(
  val id: Int,
  val eraBadge: String,
  val period: String,
  val title: String,
  val arabicSubtitle: String,
  val region: String,
  val accentElement: Element,
  val summary: String,
  val keyContributions: String,
)

private val ilmAlRamlMilestones =
  listOf(
    TimelineMilestone(
      id = 1,
      eraBadge = "Antiquité & Récits Sacrés",
      period = "Racines anciennes",
      title = "Divination dans le Sable & Traditions Primordiales",
      arabicSubtitle = "إدريس ودانيال",
      region = "Proche-Orient & Sahara",
      accentElement = Element.EARTH,
      summary =
        "Bien avant sa codification livresque, des devins traçaient des lignes spontanées dans le sable fin avant de les réduire par paires. La tradition islamique classique en attribue l'inspiration au prophète Idrīs (Hermès) et à Daniel.",
      keyContributions =
        "A posé le principe binaire fondamental (Fard / Zawj) et le geste tactile de lecture de la terre sans compter à l'avance.",
    ),
    TimelineMilestone(
      id = 2,
      eraBadge = "Parentés Transsahariennes",
      period = "Époque Ancienne–Médiévale",
      title = "Ifá, Fâ, Sikidy & Appellations Mandingues",
      arabicSubtitle = "التقاليد الإفريقية الشقيقة",
      region = "Afrique de l'Ouest & Madagascar",
      accentElement = Element.WATER,
      summary =
        "Des systèmes binaires jumeaux à 16 configurations (Ifá, Fâ, Sikidy) ont fleuri en Afrique de l'Ouest et dans l'océan Indien, donnant en milieu bambara et mandingue la nomenclature vivante de Djanfa Almami à Moussa.",
      keyContributions =
        "A illustré la puissance d'une grammaire mathématique à 4 rangs (2⁴ = 16) comme mémoire communautaire et outil de discernement.",
    ),
    TimelineMilestone(
      id = 3,
      eraBadge = "Codification Arabe Classique",
      period = "XIIe–XIIIe siècle",
      title = "Al-Zanātī & Kitāb al-Faṣl",
      arabicSubtitle = "محمد بن عثمان الزناتي",
      region = "Maghreb & Afrique du Nord",
      accentElement = Element.FIRE,
      summary =
        "Abū ʿAbd Allāh Muḥammad al-Zanātī rédigea le traité fondateur Kitāb al-Faṣl fī Uṣūl ʿIlm al-Raml, unifiant les techniques orales en un calcul rigoureux à 16 maisons.",
      keyContributions =
        "Codification des 16 figures, de l'ordre naturel Taskīn al-Zanātī, de l'extraction des Filles (Banāt), d'Istikhrāj al-Ḍamīr et de la 16e maison.",
    ),
    TimelineMilestone(
      id = 4,
      eraBadge = "Traductions Méditerranéennes",
      period = "XIIe–XIIIe siècle",
      title = "Tolède Latine & Rabolion Byzantin",
      arabicSubtitle = "الترجمة إلى اللاتينية والبيزنطية",
      region = "Péninsule Ibérique, Italie & Byzance",
      accentElement = Element.AIR,
      summary =
        "Des érudits comme Hugues de Santalla et Gérard de Crémone traduisirent les traités de raml en latin (Ars Geomantiae), tandis que Byzance compilait les manuels grecs du Rabolion.",
      keyContributions =
        "Introduction des 16 figures dans les cours et universités européennes sous des noms latins (Laetitia, Acquisitio, Fortuna Major, Via).",
    ),
    TimelineMilestone(
      id = 5,
      eraBadge = "Apogée Mamelouk, Ottoman & Renaissance",
      period = "XVIe siècle",
      title = "Ibn Zunbul al-Rammālī & Maîtres de la Renaissance",
      arabicSubtitle = "أحمد بن زنبل الرمّالي",
      region = "Le Caire & Europe",
      accentElement = Element.FIRE,
      summary =
        "Au Caire, Aḥmad ibn ʿAlī ibn Zunbul étendit la géomancie arabe aux affaires d'État et aux correspondances planétaires, tandis qu'Agrippa et Robert Fludd l'intégraient à la philosophie naturelle européenne.",
      keyContributions =
        "Approfondissement du déplacement des figures (Intiqāl al-Shakl), du voisinage (Mujāwara) et des règles du duel (Al-Ghālib wa-l-Maghlūb).",
    ),
    TimelineMilestone(
      id = 6,
      eraBadge = "Synthèse Cairote Moderne",
      period = "XXe siècle (éd. 1991 / 1411 H)",
      title = "Cheikh ʿAbd al-Fattāḥ al-Sayyid al-Ṭūkhī",
      arabicSubtitle = "بلوغ الأمل ومنبع أصول الرمل — عبد الفتاح السيد الطوخي",
      region = "Le Caire & Beyrouth",
      accentElement = Element.WATER,
      summary =
        "Directeur de l'Institut Al-Futūḥ al-Falakī et auteur des traités majeurs « Bulūgh al-Amal fī ʿIlm al-Raml » (40 chapitres) et « Manbaʿ Uṣūl al-Raml / Al-Durra al-Bahiyya » (80 leçons progressives), Cheikh al-Toukhi a systématisé l'ensemble des règles classiques.",
      keyContributions =
        "Codification des 80 leçons du Manbaʿ (Nuqṭa, Tasākīn Ḥadbaʾ/ʿAlkhad/Sakzaj, Ittiṣāl/Infiṣāl, Abjad), des Awtād et Mawāzīn, de l'Istikhrāj al-Ḍamīr et des chapitres sur le duel (Bāb 26 & 29).",
    ),
    TimelineMilestone(
      id = 7,
      eraBadge = "Tradition Omanaise & Synthèse Analytique",
      period = "XXIe siècle (2e éd. 1431 H / 2010)",
      title = "Cheikh Muḥammad ibn Shammās al-Baḥrī",
      arabicSubtitle = "الفُلْك المشحون في علم الرمل المصون — محمد بن شماس البحري",
      region = "Oman & Péninsule Arabique",
      accentElement = Element.AIR,
      summary =
        "Auteur du traité encyclopédique « Al-Fulk al-Mashḥūn fī ʿIlm al-Raml al-Maṣūn », Cheikh al-Baḥrī rassemble les méthodes de vérification interne du Takht, le calcul du Bāṭin (thème caché) et les procédés arithmétiques d'arbitrage.",
      keyContributions =
        "Formalisation du contrôle de sincérité (Ṣidq al-Raml : M3+M5 ⊕ M11+M15), des 4 Figures Élémentaires Pures, du Bāṭin al-Takht (⊕ Taskīn), du Taḥlīf Mod-9, du sous-Takht et des Awtād croisés (p. 316).",
    ),
    TimelineMilestone(
      id = 8,
      eraBadge = "Recueil Maghrebo-Levantine & Balances",
      period = "Manuscrit classique (al-Maqdisī & al-Fāsī)",
      title = "Ḥāwī al-Akhbār & Urjūza Dhāt al-Durar",
      arabicSubtitle = "حاوي الأخبار وكنز الأسرار — حازم المقدسي / أبو بكر الفاسي",
      region = "Jérusalem, Fès & Sijilmāsa",
      accentElement = Element.EARTH,
      summary =
        "Compilé par Shaykh Ḥāzim al-Maqdisī à partir d'al-Zanātī, Ṭumṭum al-Hindī, Khalaf al-Barbarī, al-Ṭarābulusī et de l'Urjūza al-Muʿizzī d'Abū Bakr al-Maghribī al-Fāsī, ce manuscrit détaille les symétries géométriques et les règles de guerre entre deux camps.",
      keyContributions =
        "Codification des 4 Mawāzīn (Naẓar, Nuṭq, Ittiṣāl, Infiṣāl), de l'opposé (Ḍidd), du miroir vertical (Aṣḥāb), de la hiérarchie quaternaire des 16 figures et du duel par paires fondatrices (M1⊕M2 vs M7⊕M8).",
    ),
    TimelineMilestone(
      id = 9,
      eraBadge = "Synthèse Al-Qawl & Manuscrits de La Mecque",
      period = "Traités classiques (MS 2380 & MS 2405)",
      title = "Al-Qawl al-Jāmiʿ & Tradition d'Al-Ṭarābulusī",
      arabicSubtitle = "القول الجامع في علم الرمل — الشيخ محمد ساس",
      region = "La Mecque, Levant & Maghreb",
      accentElement = Element.FIRE,
      summary =
        "Ces trois sources (« Al-Qawl al-Jāmiʿ fī ʿIlm al-Raml » de Shaykh Muḥammad Sās, « ʿIlm al-Raml wa-l-Aḥkām » d'al-Ḥājj Maḥmūd al-Rūmānī MS 2380 et le traité MS 2405) approfondissent la géométrie des 5 triangles, les aspects astrologiques sur M1–M12 et les sympathies planétaires.",
      keyContributions =
        "Seuil des 96 points (Raml Kāmil / Nāqiṣ), balance Vie (Air+Eau) vs Fermeture (Feu+Terre), les 5 Triangles (T1–T5, Dalīl vs ʿĀqiba), aspects Tasdīs/Tarbīʿ/Tathlīth/Muqābala, échelle temporelle d'Al-Ṭarābulusī et contrôle duel d'Al-Zanātī (M1⊕M10 vs M7⊕M4).",
    ),
  )

@Composable
fun GuideScreen(
  selectedCourseBookId: String = GeomancyCoursesCatalog.allBooks.first().id,
  completedCourseModuleIds: Set<String> = emptySet(),
  onSelectCourseBook: (String) -> Unit = {},
  onToggleCourseModuleCompleted: (String) -> Unit = {},
  onOpenFigureDetail: (GeomanticFigure) -> Unit = {},
  onOpenTakhtExample: (CourseTakhtExample) -> Unit = {},
  initialTab: Int = 0,
  externalSelectedTab: Int? = null,
  onSelectTab: ((Int) -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  var localSelectedTab by remember(initialTab) { mutableIntStateOf(initialTab) }
  val selectedTab = externalSelectedTab ?: localSelectedTab
  val updateTab: (Int) -> Unit = { tab ->
    localSelectedTab = tab
    onSelectTab?.invoke(tab)
  }
  var expandedMilestoneId by remember { mutableIntStateOf(6) }
  var expandedModuleId by
    remember(selectedCourseBookId) {
      mutableStateOf(
        GeomancyCoursesCatalog.findBook(selectedCourseBookId)?.modules?.firstOrNull()?.id
          ?: GeomancyCoursesCatalog.allBooks.first().modules.first().id,
      )
    }
  val extColors = AlRamlColors.extended
  val snackbarHostState = remember { SnackbarHostState() }
  val scope = rememberCoroutineScope()

  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().widthIn(max = 760.dp).testTag("guide_screen_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      item {
        SectionHeaderCard(
          title = stringResource(R.string.tradition_title),
          arabicSubtitle = "دروس وتاريخ علم الرمل",
          body =
            "Cours complets en français tirés des ${GeomancyCoursesCatalog.allBooks.size} traités (dont Bulūgh al-Amal et les 80 leçons de Manbaʿ Uṣūl al-Raml d'al-Toukhi), chronologie historique et guide pratique du tracé.",
        )
      }

      item {
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
          SegmentedButton(
            selected = selectedTab == 0,
            onClick = { updateTab(0) },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3),
            modifier = Modifier.testTag("tab_learning_courses"),
          ) {
            Text(stringResource(R.string.tab_courses), maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
          SegmentedButton(
            selected = selectedTab == 1,
            onClick = { updateTab(1) },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3),
            modifier = Modifier.testTag("tab_historical_timeline"),
          ) {
            Text(stringResource(R.string.tab_timeline), maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
          SegmentedButton(
            selected = selectedTab == 2,
            onClick = { updateTab(2) },
            shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3),
            modifier = Modifier.testTag("tab_casting_guide"),
          ) {
            Text(stringResource(R.string.tab_guide), maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
        }
      }

      when (selectedTab) {
        0 -> {
          coursesCurriculumItems(
            selectedBookId = selectedCourseBookId,
            completedModuleIds = completedCourseModuleIds,
            expandedModuleId = expandedModuleId,
            onSelectBook = onSelectCourseBook,
            onToggleExpandedModule = { moduleId ->
              expandedModuleId = if (expandedModuleId == moduleId) "" else moduleId
            },
            onToggleModuleCompleted = onToggleCourseModuleCompleted,
            onOpenFigureDetail = onOpenFigureDetail,
            onOpenTakhtExample = onOpenTakhtExample,
            onExportMessage = { message ->
              scope.launch { snackbarHostState.showSnackbar(message) }
            },
          )
        }
        1 -> {
          itemsIndexed(ilmAlRamlMilestones, key = { _, item -> item.id }) { index, milestone ->
            val nodeColor = extColors.forElement(milestone.accentElement)
            val spineColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.45f)
            val isExpanded = expandedMilestoneId == milestone.id
            val isFirst = index == 0
            val isLast = index == ilmAlRamlMilestones.lastIndex

            TimelineMilestoneRow(
              milestone = milestone,
              nodeColor = nodeColor,
              spineColor = spineColor,
              isFirst = isFirst,
              isLast = isLast,
              isExpanded = isExpanded,
              onToggle = {
                expandedMilestoneId = if (isExpanded) -1 else milestone.id
              },
            )
          }
        }
        else -> {
        item {
          GuideSectionCard(
            title = "1. Formuler l'intention & tracer les 16 lignes",
            body =
              "Commence par une question claire (Niyya) et note l'heure planétaire. Trace 16 lignes de points dans le sable sans compter (4 lignes par Mère) puis réduis par paires : un reste impair donne 1 point (Fard), un reste pair donne 2 points (Zawj).",
          )
        }
        item {
          GuideSectionCard(
            title = "2. Ériger le Takht à 16 Maisons",
            body =
              "• Maisons 1–4 (Mères) : Issues directement de tes 16 lignes (de Djanfa Almami, Adama, Malidjou… jusqu'à Moussa).\n" +
                "• Maisons 5–8 (Filles) : Extraites horizontalement depuis les rangs Feu, Air, Eau, Terre des 4 Mères.\n" +
                "• Maisons 9–12 (Nièces), 13–14 (Témoins), 15 (Le Juge / Al-Mīzān) et 16 (ʿĀqibat al-ʿĀqiba) : Dérivées par addition binaire des figures adjacentes.",
          )
        }
        item {
          GuideSectionCard(
            title = "3. Interpréter selon Cheikh Al-Toukhi",
            body =
              "• Istikhrāj al-Ḍamīr : Observe quel élément actif domine et quelles figures occupent leur demeure Taskīn pour cerner l'intention voilée.\n" +
                "• Intiqāl & Mujāwara : Suis le déplacement du Consultant (M1) et du Sujet, puis vérifie si les Témoins sont soutenus ou assiégés.\n" +
                "• Duel (Al-Ghālib wa-l-Maghlūb) : Compare le Camp A (M1, M2, M10, M13) au Camp B (M7, M8, M4, M14), en tenant compte des parents des Témoins (M9/M10 vs M11/M12) et de la répétition du Juge (M15).",
          )
        }
        item {
          GuideSectionCard(
            title = "4. Règles clés de « Bulūgh al-Amal » (actives dans le moteur)",
            body =
              "• Awtād, Succédentes & Cadentes (Bāb 4) : Les Awtād (M1, M4, M7, M10) gouvernent l'état présent ; les Succédentes (M2, M5, M8, M11) l'avenir proche ; les Cadentes (M3, M6, M9, M12) le passé.\n" +
                "• Les 4 Mawāzīn & Parité de M15 (Bāb 1 & 14) : Les maisons M12, M14, M15 et M16 forment la balance du thème ; le Juge M15 doit toujours être pair en points, avec recours à M11 en cas d'égalité.\n" +
                "• Seuil des 90 points (Bāb 14) : Plus de 90 points dans les 16 maisons favorise une décision franche ; moins de 90 (< 90) ou de 60 signale rétention, jeu fermé ou partage.\n" +
                "• Duel (Bāb 26, Bāb 29 & p. 97) : Intègre la comparaison des points ouverts Droite (M1+M13+M15) vs Gauche (M7+M14+M16), le transfert vers l'arbitre M10, et les répétitions dominantes (≥ 3) directement dans Al-Ghālib wa-l-Maghlūb.",
          )
        }
        item {
          GuideSectionCard(
            title = "5. Apports d’« Al-Fulk al-Mashḥūn » (Cheikh al-Baḥrī)",
            body =
              "• Fiabilité du thème (Ṣidq al-Raml, pp. 89–90) : La figure-sceau issue de (M3+M5) ⊕ (M11+M15) doit apparaître dans le Takht pour confirmer sa sincérité ; si les 4 Mères sont toutes Fixes ou toutes Sortantes, l'intention est jugée troublée.\n" +
                "• Thème Intérieur (Bāṭin al-Takht, pp. 141–143) & 4 Pures (pp. 77–78) : Chaque maison est croisée avec son gouverneur Taskīn pour révéler les courants cachés, complétés par Feu-de-Feu, Air-d'Air, Eau-d'Eau et Terre-de-Terre.\n" +
                "• Dignités Planétaires (Sharaf & Hubūṭ, pp. 67–68) : Mesure si une figure siège en Exaltation (ex. Soleil en M1, Jupiter en M4, Saturne en M7, Vénus en M12) ou en Chute.\n" +
                "• Arbitrage Duel (Taḥlīf Mod-9, Sous-Takht Awtād & Awtād croisés p. 316) : Ajoute le reste modulo 9, Impaires vs Paires, le sous-Takht des Awtād et la confrontation croisée (M1F·M4A·M7E·M10T vs M10F·M7A·M4E·M1T).",
          )
        }
        item {
          GuideSectionCard(
            title = "6. Apports de « Ḥāwī al-Akhbār » (al-Maqdisī & al-Fāsī)",
            body =
              "• Les 4 Mawāzīn Relationnels (pp. 112–113) : Naẓar (⊕ Al-Laḥyān, rang Feu), Nuṭq (⊕ Al-Ḥumra, Air), Ittiṣāl (⊕ Al-Bayāḍ, Eau) et Infiṣāl (⊕ Al-Inkis, Terre), complétés par l'Opposé total (Al-Ḍidd ⊕ Ṭarīq) et le Miroir vertical (Aṣḥāb).\n" +
                "• Zones Temporelles & Force des Maisons : Awtād (Présent), Succédentes (Futur), Cadentes (Passé) avec suivi de l'évolution élémentaire et hiérarchie de force des 16 maisons.\n" +
                "• Règles de Guerre & Duel (pp. 9, 114–115, 155) : Confrontation des paires fondatrices Camp A (M1⊕M2) vs Camp B (M7⊕M8) pondérée selon les 4 degrés de force (Qawiyya / Mumtazija) et détection d'armistice (Jamāʿa).",
          )
        }
        item {
          GuideSectionCard(
            title = "7. Apports d’« Al-Qawl al-Jāmiʿ » & Manuscrits de La Mecque (MS 2380 / 2405)",
            body =
              "• Seuil des 96 points & Balance Vie/Fermeture : Au-dessus de 96 pts, le thème est Raml Kāmil (accompli) ; la somme des rangs Air+Eau (Vie) face à Feu+Terre (Fermeture) indique l'aisance ou la résistance.\n" +
                "• Les 5 Triangles (Muthallathāt al-Buyūt) : T1 (M1+M2→M9), T2 Dalīl (M3+M4→M10), T3 (M5+M6→M11), T4 (M7+M8→M12) et T5 ʿĀqiba (M13+M14→M15), comparant le sujet (M10) à l'issue (M15).\n" +
                "• Aspects sur M1–M12 & Sympathies : Tasdīs (Sextile 1/6), Tarbīʿ (Carré 1/4), Tathlīth (Trine 1/3) et Muqābala (Opposition 1/2), joints aux amitiés/inimitiés planétaires.\n" +
                "• Échelle d'Al-Ṭarābulusī & Arbitrage d'Al-Zanātī : Durées propres de 1 jour (M1) à 10 ans (M15), et contrôle duel croisant M1⊕M10 face à M7⊕M4.",
          )
        }
        }
      }

      item { Spacer(modifier = Modifier.height(12.dp)) }
    }

    SnackbarHost(
      hostState = snackbarHostState,
      modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp),
    )
  }
}

@Composable
private fun TimelineMilestoneRow(
  milestone: TimelineMilestone,
  nodeColor: Color,
  spineColor: Color,
  isFirst: Boolean,
  isLast: Boolean,
  isExpanded: Boolean,
  onToggle: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
    horizontalArrangement = Arrangement.spacedBy(10.dp),
  ) {
    Canvas(
      modifier = Modifier.width(28.dp).fillMaxHeight(),
    ) {
      val centerX = size.width / 2f
      val nodeY = 28.dp.toPx()
      if (!isFirst) {
        drawLine(
          color = spineColor,
          start = Offset(centerX, 0f),
          end = Offset(centerX, nodeY - 10.dp.toPx()),
          strokeWidth = 2.5.dp.toPx(),
        )
      }
      if (!isLast) {
        drawLine(
          color = spineColor,
          start = Offset(centerX, nodeY + 10.dp.toPx()),
          end = Offset(centerX, size.height),
          strokeWidth = 2.5.dp.toPx(),
        )
      }
      drawCircle(
        color = nodeColor.copy(alpha = 0.22f),
        radius = 12.dp.toPx(),
        center = Offset(centerX, nodeY),
      )
      drawCircle(
        color = nodeColor,
        radius = 6.dp.toPx(),
        center = Offset(centerX, nodeY),
      )
    }

    LiquidGlassCard(
      modifier =
        Modifier.weight(1f)
          .padding(bottom = 6.dp)
          .clickable(onClick = onToggle)
          .testTag("timeline_milestone_${milestone.id}"),
      shape = RoundedCornerShape(18.dp),
      elevated = isExpanded,
      accentBorderColor = if (isExpanded) nodeColor else null,
    ) {
      Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = nodeColor.copy(alpha = 0.14f),
          ) {
            Text(
              text = "${milestone.period} · ${milestone.region}",
              style = MaterialTheme.typography.labelSmall,
              color = nodeColor,
              fontWeight = FontWeight.SemiBold,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            )
          }
          Icon(
            imageVector = if (isExpanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
            contentDescription = if (isExpanded) "Réduire l'étape" else "Déplier l'étape",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        Text(
          text = milestone.title,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.primary,
        )
        Text(
          text = "${milestone.eraBadge} · ${milestone.arabicSubtitle}",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
          text = milestone.summary,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
        )

        AnimatedVisibility(visible = isExpanded) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f),
            modifier = Modifier.padding(top = 4.dp),
          ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
              Text(
                text = "Apport majeur à Ilm al-Raml",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
              )
              Text(
                text = milestone.keyContributions,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun GuideSectionCard(
  title: String,
  body: String,
) {
  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
  ) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
      )
      Text(
        text = body,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
    }
  }
}
