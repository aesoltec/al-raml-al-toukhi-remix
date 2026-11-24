package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.FigureEncyclopediaRepository
import com.example.data.GeomanticFigureEntity
import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.AlToukhiHouses
import com.example.geomancy.Element
import com.example.geomancy.FortunePolarity
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.HouseInfo
import com.example.geomancy.PointParity
import com.example.ui.GeomancyUiState
import com.example.ui.components.ElementTag
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.components.PolarityBadge
import com.example.ui.components.SectionHeaderCard
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard

@Composable
fun FiguresCatalogScreen(
  uiState: GeomancyUiState,
  onSearchChange: (String) -> Unit,
  onToggleElement: (Element?) -> Unit,
  onTogglePolarity: (FortunePolarity?) -> Unit,
  onSelectFigure: (GeomanticFigure) -> Unit,
  encyclopediaFigures: List<GeomanticFigureEntity>? = null,
  onToggleFavoritesOnly: () -> Unit = {},
  onToggleFigureFavorite: (Int, Boolean) -> Unit = { _, _ -> },
  modifier: Modifier = Modifier,
) {
  var encyclopediaSection by remember { mutableIntStateOf(0) } // 0 = 16 Figures, 1 = 16 Maisons

  val displayedFigureEntities =
    remember(
      encyclopediaFigures,
      uiState.figureSearchQuery,
      uiState.figureElementFilter,
      uiState.figurePolarityFilter,
      uiState.figureFavoritesOnlyFilter,
    ) {
      if (encyclopediaFigures != null) {
        encyclopediaFigures
      } else {
        val q = uiState.figureSearchQuery.trim().lowercase()
        FigureEncyclopediaRepository.buildSeedEntities().filter { entity ->
          val matchesQuery =
            q.isEmpty() ||
              entity.westAfricanName.lowercase().contains(q) ||
              entity.transliteration.lowercase().contains(q) ||
              entity.arabicName.contains(q) ||
              entity.traditionalTitle.lowercase().contains(q) ||
              entity.traditionalMeaning.lowercase().contains(q) ||
              entity.associationsSummary.lowercase().contains(q) ||
              entity.code.contains(q) ||
              entity.elementLabel.lowercase().contains(q) ||
              entity.elementQuality.lowercase().contains(q) ||
              entity.elementHumor.lowercase().contains(q) ||
              entity.elementDirection.lowercase().contains(q) ||
              entity.planetLabel.lowercase().contains(q) ||
              entity.zodiacSign.lowercase().contains(q) ||
              entity.motionLabel.lowercase().contains(q) ||
              entity.arabicLetter.contains(q)
          val matchesElement =
            uiState.figureElementFilter == null ||
              entity.dominantElementName == uiState.figureElementFilter.name
          val matchesPolarity =
            uiState.figurePolarityFilter == null ||
              entity.polarityName == uiState.figurePolarityFilter.name
          val matchesFavorite = !uiState.figureFavoritesOnlyFilter || entity.isFavorite
          matchesQuery && matchesElement && matchesPolarity && matchesFavorite
        }
      }
    }

  val filteredHouses =
    remember(uiState.figureSearchQuery, uiState.figureElementFilter) {
      val q = uiState.figureSearchQuery.trim().lowercase()
      AlToukhiHouses.all.filter { house ->
        val ruler = AlToukhiHouses.taskinRulerFor(house.number)
        val matchesQuery =
          q.isEmpty() ||
            "m${house.number}".contains(q) ||
            "maison ${house.number}".contains(q) ||
            house.englishName.lowercase().contains(q) ||
            house.arabicName.contains(q) ||
            house.domainMeaning.lowercase().contains(q) ||
            house.derivationRule.lowercase().contains(q) ||
            house.element.englishName.lowercase().contains(q) ||
            house.direction.lowercase().contains(q) ||
            ruler.westAfricanName.lowercase().contains(q) ||
            ruler.transliteration.lowercase().contains(q) ||
            ruler.dominantElement.englishName.lowercase().contains(q)
        val matchesElement =
          uiState.figureElementFilter == null || house.element == uiState.figureElementFilter
        matchesQuery && matchesElement
      }
    }

  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().widthIn(max = 760.dp).testTag("figures_encyclopedia_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      item {
        SectionHeaderCard(
          title = "Encyclopédie Géomantique (Room)",
          arabicSubtitle = "موسوعة الرمل والأشكال الستة عشر",
          body =
            "Base locale Room des 16 figures (noms ouest-africains & arabes, significations traditionnelles, associations planétaires, zodiacales, élémentaires et Taskīn) ainsi que les 16 Maisons.",
        )
      }

      item {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)),
          modifier = Modifier.fillMaxWidth().testTag("encyclopedia_room_status_banner"),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(
              imageVector = Icons.Rounded.Storage,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = "Encyclopédie Room locale · ${displayedFigureEntities.size} figure(s) affichée(s) · Significations & associations hors-ligne",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.primary,
            )
          }
        }
      }

      item {
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
          SegmentedButton(
            selected = encyclopediaSection == 0,
            onClick = { encyclopediaSection = 0 },
            shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
            modifier = Modifier.testTag("encyclopedia_tab_figures"),
          ) {
            Text("16 Figures (${displayedFigureEntities.size})", maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
          SegmentedButton(
            selected = encyclopediaSection == 1,
            onClick = { encyclopediaSection = 1 },
            shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
            modifier = Modifier.testTag("encyclopedia_tab_houses"),
          ) {
            Text("16 Maisons (${filteredHouses.size})", maxLines = 1, overflow = TextOverflow.Ellipsis)
          }
        }
      }

      item {
        OutlinedTextField(
          value = uiState.figureSearchQuery,
          onValueChange = onSearchChange,
          leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
          trailingIcon = {
            if (uiState.figureSearchQuery.isNotEmpty()) {
              IconButton(
                onClick = { onSearchChange("") },
                modifier = Modifier.testTag("encyclopedia_clear_search"),
              ) {
                Icon(Icons.Rounded.Clear, contentDescription = "Effacer la recherche")
              }
            }
          },
          placeholder = {
            Text(
              if (encyclopediaSection == 0) {
                "Nom, élément, humeur, planète, sens, code (ex. Feu, 1222, joie)…"
              } else {
                "Maison (ex. M7), élément, direction, gouverneur Taskīn, sens…"
              },
            )
          },
          singleLine = true,
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.fillMaxWidth().testTag("figures_search_input"),
        )
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          FilterChip(
            selected =
              uiState.figureElementFilter == null &&
                uiState.figurePolarityFilter == null &&
                !uiState.figureFavoritesOnlyFilter,
            onClick = {
              onToggleElement(null)
              onTogglePolarity(null)
              if (uiState.figureFavoritesOnlyFilter) onToggleFavoritesOnly()
            },
            label = { Text(stringResource(R.string.filter_all)) },
          )
          if (encyclopediaSection == 0) {
            FilterChip(
              selected = uiState.figureFavoritesOnlyFilter,
              onClick = onToggleFavoritesOnly,
              label = { Text("Favoris") },
              modifier = Modifier.testTag("encyclopedia_filter_favorites"),
            )
          }
          Element.entries.forEach { elem ->
            FilterChip(
              selected = uiState.figureElementFilter == elem,
              onClick = { onToggleElement(elem) },
              label = { Text(elem.englishName) },
              modifier = Modifier.testTag("encyclopedia_element_${elem.name.lowercase()}"),
            )
          }
          if (encyclopediaSection == 0) {
            FortunePolarity.entries.forEach { pol ->
              FilterChip(
                selected = uiState.figurePolarityFilter == pol,
                onClick = { onTogglePolarity(pol) },
                label = { Text(pol.arabicName) },
              )
            }
          }
        }
      }

      if (encyclopediaSection == 0) {
        if (displayedFigureEntities.isEmpty()) {
          item {
            EmptyEncyclopediaSearchCard(
              query = uiState.figureSearchQuery,
              onReset = {
                onSearchChange("")
                onToggleElement(null)
                onTogglePolarity(null)
                if (uiState.figureFavoritesOnlyFilter) onToggleFavoritesOnly()
              },
            )
          }
        } else {
          items(displayedFigureEntities, key = { it.id }) { entity ->
            val domainFig = remember(entity.id) { AlToukhiFigures.findById(entity.id) }
            FigureCatalogRow(
              entity = entity,
              figure = domainFig,
              onClick = { onSelectFigure(domainFig) },
              onToggleFavorite = { onToggleFigureFavorite(entity.id, !entity.isFavorite) },
            )
          }
        }
      } else {
        if (filteredHouses.isEmpty()) {
          item {
            EmptyEncyclopediaSearchCard(
              query = uiState.figureSearchQuery,
              onReset = {
                onSearchChange("")
                onToggleElement(null)
              },
            )
          }
        } else {
          items(filteredHouses, key = { it.number }) { house ->
            HouseCatalogRow(
              house = house,
              onOpenRulerFigure = { onSelectFigure(AlToukhiHouses.taskinRulerFor(house.number)) },
            )
          }
        }
      }

      item { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }
}

@Composable
private fun EmptyEncyclopediaSearchCard(
  query: String,
  onReset: () -> Unit,
) {
  LiquidGlassCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp)) {
    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(
        text = "Aucun résultat pour « ${query.ifBlank { "filtre actif" }} »",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
      )
      Text(
        text = "Essaie un autre mot-clé (ex. Feu, Eau, Djanfa, M10, voyage, Saturne) ou réinitialise les filtres.",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      FilterChip(
        selected = false,
        onClick = onReset,
        label = { Text("Réinitialiser la recherche") },
      )
    }
  }
}

@Composable
private fun FigureCatalogRow(
  entity: GeomanticFigureEntity,
  figure: GeomanticFigure,
  onClick: () -> Unit,
  onToggleFavorite: () -> Unit,
) {
  val extColors = AlRamlColors.extended
  val elem = figure.dominantElement
  val ranksSummary =
    figure.ranks.mapIndexed { idx, p ->
      val label = Element.entries[idx].englishName.first()
      "$label:${if (p == PointParity.ODD) "•" else "••"}"
    }.joinToString(" ")

  LiquidGlassCard(
    modifier =
      Modifier.fillMaxWidth()
        .clickable(onClick = onClick)
        .testTag("figure_card_${entity.id}"),
    shape = RoundedCornerShape(18.dp),
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        ) {
          Box(modifier = Modifier.padding(10.dp), contentAlignment = Alignment.Center) {
            GeomanticFigureGlyph(figure = figure, size = 38.dp, useElementColors = true)
          }
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
              text = "${entity.westAfricanOrder}. ${entity.westAfricanName}",
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.primary,
              modifier = Modifier.weight(1f),
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
            )
            Text(
              text = entity.arabicName,
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            IconButton(
              onClick = onToggleFavorite,
              modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp).testTag("figure_favorite_button_${entity.id}"),
            ) {
              Icon(
                imageVector =
                  if (entity.isFavorite) Icons.Rounded.Bookmark else Icons.Rounded.BookmarkBorder,
                contentDescription =
                  if (entity.isFavorite) "Retirer des favoris" else "Ajouter aux favoris",
                tint =
                  if (entity.isFavorite) AlRamlColors.extended.goldAccent
                  else MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }
          Text(
            text = "${entity.code} · ${entity.transliteration} · ${entity.traditionalTitle}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            PolarityBadge(polarity = figure.polarity)
            ElementTag(element = elem)
            Text(
              text = "${entity.planetLabel} · ${entity.arabicLetter}=${entity.abjadValue}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
            )
          }
        }
      }

      Surface(
        shape = RoundedCornerShape(10.dp),
        color = extColors.forElement(elem).copy(alpha = 0.08f),
        border = BorderStroke(1.dp, extColors.forElement(elem).copy(alpha = 0.25f)),
      ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
          Text(
            text = "${entity.elementLabel} (${entity.elementQuality}) · ${entity.elementHumor} · ${entity.elementDirection} · $ranksSummary",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = extColors.forElement(elem),
          )
          Text(
            text = entity.traditionalMeaning,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = "Associations : ${entity.zodiacSign} · ${entity.planetDay} · Taskīn M${entity.taskinHouseNumber} (${entity.taskinHouseName}) · ${entity.hawiStrengthLabel}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }
  }
}

@Composable
private fun HouseCatalogRow(
  house: HouseInfo,
  onOpenRulerFigure: () -> Unit,
) {
  val extColors = AlRamlColors.extended
  val ruler = remember(house.taskinRulerId) { AlToukhiHouses.taskinRulerFor(house.number) }

  LiquidGlassCard(
    modifier =
      Modifier.fillMaxWidth()
        .clickable(onClick = onOpenRulerFigure)
        .testTag("house_card_${house.number}"),
    shape = RoundedCornerShape(18.dp),
  ) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "Maison ${house.number} · ${house.englishName}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
          )
          Text(
            text = "${house.arabicName} · ${house.group.label}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
        ElementTag(element = house.element)
      }

      Text(
        text = house.domainMeaning,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
      )

      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth().padding(10.dp),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          GeomanticFigureGlyph(figure = ruler, size = 28.dp, useElementColors = true)
          Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
              text = "Gouverneur Taskīn : ${ruler.westAfricanName} (${ruler.dominantElement.englishName})",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = "Direction : ${house.direction} · Nature : ${house.element.quality} · ${house.derivationRule}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FigureDetailBottomSheet(
  figure: GeomanticFigure,
  onDismiss: () -> Unit,
  onSelectFigure: (GeomanticFigure) -> Unit = {},
) {
  val extColors = AlRamlColors.extended
  val taskinHouse = AlToukhiHouses.findByNumber(figure.id)
  val sharafHouseNum = AlToukhiHouses.sharafHouseFor(figure.planet)
  val hubutHouseNum = AlToukhiHouses.hubutHouseFor(figure.planet)
  val sharafHouse = AlToukhiHouses.findByNumber(sharafHouseNum)
  val hubutHouse = AlToukhiHouses.findByNumber(hubutHouseNum)
  val hawiTier = remember(figure) { AlToukhiFigures.hawiStrengthTier(figure) }
  val relations = remember(figure) { AlToukhiFigures.relationsFor(figure) }
  val rankElements = listOf(Element.FIRE, Element.AIR, Element.WATER, Element.EARTH)
  val rankTitles = listOf("Tête (Raʾs)", "Poitrine (Ṣadr)", "Ventre (Baṭn)", "Pieds (Qadam)")

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    containerColor = MaterialTheme.colorScheme.surface,
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).testTag("figure_detail_list"),
      contentPadding = PaddingValues(bottom = 28.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            border = BorderStroke(1.dp, extColors.cardBorder),
          ) {
            Box(modifier = Modifier.padding(14.dp)) {
              GeomanticFigureGlyph(figure = figure, size = 56.dp, useElementColors = true)
            }
          }
          Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "${figure.westAfricanName} · ${figure.arabicName}",
              style = MaterialTheme.typography.headlineSmall,
              color = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = "${figure.transliteration} — ${figure.englishTitle}",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              PolarityBadge(polarity = figure.polarity)
              ElementTag(element = figure.dominantElement)
            }
          }
        }
      }

      // 4 Rangs Élémentaires
      item {
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        ) {
          Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Quatre Rangs Élémentaires (de haut en bas)",
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.primary,
            )
            figure.ranks.forEachIndexed { idx, parity ->
              val elem = rankElements[idx]
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
              ) {
                Text(
                  text = "${rankTitles[idx]} · ${elem.englishName}",
                  style = MaterialTheme.typography.bodySmall,
                  color = extColors.forElement(elem),
                )
                Text(
                  text = if (parity == PointParity.ODD) "• Impair (Actif)" else "•• Pair (Posé)",
                  style = MaterialTheme.typography.labelMedium,
                  color = MaterialTheme.colorScheme.onSurface,
                )
              }
            }
          }
        }
      }

      // Correspondances Al-Toukhi
      item {
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
        ) {
          Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Correspondances Astronomiques & Abjad (Al-Toukhi)",
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.primary,
            )
            AttributeLine("Rang Ouest-Africain / Taskīn", "#${figure.westAfricanOrder} · Maison Taskīn ${figure.id} (${taskinHouse.englishName})")
            AttributeLine("Dignités Planétaires (Al-Fulk pp. 67–68)", "Exaltation (Sharaf) : M$sharafHouseNum (${sharafHouse.englishName}) · Chute (Hubūṭ) : M$hubutHouseNum (${hubutHouse.englishName})")
            AttributeLine("Rang Ḥāwī al-Akhbār (p. 155)", "${hawiTier.label} · ${hawiTier.arabicLabel}")
            AttributeLine("Planète Gouvernante", "${figure.planet.englishName} (${figure.planet.arabicName}) · ${figure.planet.dayEnglish}")
            AttributeLine("Zodiaque & Direction", "${figure.zodiacSign} · ${figure.dominantElement.direction}")
            AttributeLine("Mouvement & Vitesse", "${figure.motion.label} · ${figure.timingSpeed}")
            AttributeLine("Lettre Arabe & Abjad", "${figure.arabicLetter} = ${figure.abjadValue}")
            AttributeLine("Sens général", figure.generalMeaning)
          }
        }
      }

      // Mawāzīn Relationnels (Ḥāwī al-Akhbār, pp. 112–113, 144, 148)
      item {
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
          modifier = Modifier.fillMaxWidth().testTag("figure_relations_card"),
        ) {
          Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              text = "Mawāzīn Relationnels (Ḥāwī al-Akhbār)",
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.primary,
            )
            Text(
              text = "Toucher une figure compagne pour l’inspecter (Naẓar Feu, Nuṭq Air, Ittiṣāl Eau, Infiṣāl Terre, Ḍidd, Miroir).",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            val relationRows =
              listOf(
                Triple("figure_relation_nazar", "Naẓar (Regard · ⊕ Al-Laḥyān)", relations.nazar),
                Triple("figure_relation_nutq", "Nuṭq (Parole · ⊕ Al-Ḥumra)", relations.nutq),
                Triple("figure_relation_ittisal", "Ittiṣāl (Jonction · ⊕ Al-Bayāḍ)", relations.ittisal),
                Triple("figure_relation_infisal", "Infiṣāl (Séparation · ⊕ Al-Inkis)", relations.infisal),
                Triple("figure_relation_didd", "Al-Ḍidd (Opposé · ⊕ Al-Ṭarīq)", relations.opposite),
                Triple("figure_relation_mirror", "Aṣḥāb (Miroir vertical)", relations.verticalMirror),
              )
            relationRows.forEach { (tag, label, targetFig) ->
              Surface(
                modifier = Modifier
                  .fillMaxWidth()
                  .defaultMinSize(minHeight = 48.dp)
                  .clickable { onSelectFigure(targetFig) }
                  .testTag(tag),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
                border = BorderStroke(1.dp, extColors.cardBorder),
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                  verticalAlignment = Alignment.CenterVertically,
                ) {
                  GeomanticFigureGlyph(figure = targetFig, size = 24.dp, useElementColors = true)
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = label,
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                      text = "${targetFig.westAfricanName} (${targetFig.transliteration} · ${targetFig.code})",
                      style = MaterialTheme.typography.labelMedium,
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onSurface,
                    )
                  }
                  PolarityBadge(polarity = targetFig.polarity)
                }
              }
            }
          }
        }
      }

      // Attributs Spirituels (Al-Ruhaniyyat)
      item {
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
        ) {
          Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
              text = "Attributs Spirituels (Al-Rūḥāniyyāt)",
              style = MaterialTheme.typography.titleMedium,
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold,
            )
            AttributeLine("Correspondance", figure.spiritualProfile.ruhaniyyaCorrespondence)
            AttributeLine("Régent Planétaire", figure.planet.spiritualRegent)
            AttributeLine("Dhikr contemplatif", figure.spiritualProfile.contemplativeDhikr)
            AttributeLine("Tempérament spirituel", figure.spiritualProfile.spiritualTemperament)
            AttributeLine("Note opérative", figure.spiritualProfile.operativeNote)
            AttributeLine("Conseil", figure.spiritualProfile.counsel)
          }
        }
      }
    }
  }
}

@Composable
private fun AttributeLine(label: String, value: String) {
  Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}
