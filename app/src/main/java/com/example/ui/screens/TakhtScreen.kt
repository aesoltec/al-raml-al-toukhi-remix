package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkAdded
import androidx.compose.material.icons.rounded.CompassCalibration
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.defaultMinSize
import com.example.R
import com.example.geomancy.AlFulkGeneralInsights
import com.example.geomancy.AlQawlAlJamiGeneralInsights
import com.example.geomancy.AlToukhiHouses
import com.example.geomancy.CardIntensityLevel
import com.example.geomancy.ChartHouse
import com.example.geomancy.Element
import com.example.geomancy.GeomanticFigure
import com.example.geomancy.MatchOutcome
import com.example.geomancy.MatchOutcomeParser
import com.example.geomancy.PlanetaryDignity
import com.example.geomancy.PredictiveCalibrationEngine
import com.example.geomancy.QuestionDomain
import com.example.geomancy.TakhtReading
import com.example.geomancy.ThemeSignatureRecurrence
import com.example.ui.components.ElementTag
import com.example.ui.components.GeomanticFigureGlyph
import com.example.ui.components.PolarityBadge
import com.example.ui.theme.AlRamlColors
import com.example.ui.theme.LiquidGlassCard
import java.text.DateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TakhtScreen(
  reading: TakhtReading?,
  inspectedHouseNumber: Int,
  isSaved: Boolean,
  savedTitle: String,
  savedContextNotes: String,
  savedActualOutcome: MatchOutcome? = null,
  savedActualScore: String = "",
  savedLeagueCategory: String = "",
  savedActualYellowCards: String = "",
  savedActualRedCards: String = "",
  autoSaveEnabled: Boolean = true,
  optionExactScore: Boolean = true,
  optionYellowCards: Boolean = true,
  optionRedCards: Boolean = true,
  themeRecurrence: ThemeSignatureRecurrence? = null,
  isCalibrationEnabled: Boolean = false,
  onSaveTheme: (String, String, MatchOutcome?, String, String) -> Unit = { _, _, _, _, _ -> },
  onSaveThemeWithCards: ((String, String, MatchOutcome?, String, String, String, String) -> Unit)? = null,
  onToggleOptionExactScore: () -> Unit = {},
  onToggleOptionYellowCards: () -> Unit = {},
  onToggleOptionRedCards: () -> Unit = {},
  onInspectHouse: (Int) -> Unit,
  onGoToCast: () -> Unit,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
  modifier: Modifier = Modifier,
) {
  if (reading == null) {
    EmptyTakhtState(onGoToCast = onGoToCast, modifier = modifier)
    return
  }

  var showSaveDialog by remember { mutableStateOf(false) }
  val inspectedHouse = reading.house(inspectedHouseNumber)
  val analysis = reading.analysis
  val extColors = AlRamlColors.extended

  Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    LazyColumn(
      modifier = Modifier.fillMaxSize().widthIn(max = 760.dp).testTag("takht_screen_list"),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
      // En-tête et Sentence du Juge
      item {
        LiquidGlassCard(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          elevated = true,
          accentBorderColor = extColors.forPolarity(analysis.overallPolarity),
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
            ) {
              Text(
                text = "${reading.domain.title} · ${reading.domain.arabicTitle}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
              )
              PolarityBadge(polarity = analysis.overallPolarity)
            }
            if (reading.questionText.isNotEmpty()) {
              Text(
                text = "« ${reading.questionText} »",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
              )
            }
            Text(
              text = analysis.verdictHeadline,
              style = MaterialTheme.typography.headlineSmall,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = analysis.verdictDetail,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            analysis.alFulkInsights?.reliability?.let { rel ->
              val relColor = if (rel.isSincere) extColors.fortunate else extColors.mixed
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = relColor.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, relColor.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth().testTag("sidq_al_raml_badge"),
              ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                  Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                  ) {
                    Icon(
                      imageVector = if (rel.isSincere) Icons.Rounded.Verified else Icons.Rounded.WarningAmber,
                      contentDescription = null,
                      tint = relColor,
                      modifier = Modifier.size(16.dp),
                    )
                    Text(
                      text = "Ṣidq al-Raml (Al-Fulk) : ${rel.summaryLabel} · Sceau ${rel.validationFigure.westAfricanName}",
                      style = MaterialTheme.typography.labelMedium,
                      fontWeight = FontWeight.SemiBold,
                      color = relColor,
                    )
                  }
                  rel.mothersUniformMotionWarning?.let { warning ->
                    Text(
                      text = warning,
                      style = MaterialTheme.typography.bodySmall,
                      color = extColors.mixed,
                    )
                  }
                }
              }
            }
            analysis.contestComparison?.let { duelSummary ->
              val bulugh = analysis.contestPrediction?.bulughInsights
              val alFulkDuel = analysis.contestPrediction?.alFulkInsights
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
              ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                  if (isCalibrationEnabled && reading.domain == QuestionDomain.CONTEST) {
                    Text(
                      text = "Modèle prédictif calibré actif · Bulūgh al-Amal & Al-Fulk",
                      style = MaterialTheme.typography.labelSmall,
                      fontWeight = FontWeight.Bold,
                      color = extColors.fortunate,
                    )
                  }
                  Text(
                    text = duelSummary,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                  )
                  if (bulugh != null) {
                    val thresholdText =
                      when {
                        bulugh.totalChartDots > 90 -> "${bulugh.totalChartDots}/128 pts (> 90)"
                        bulugh.totalChartDots == 90 -> "90/128 pts (seuil)"
                        else -> "${bulugh.totalChartDots}/128 pts (< 90)"
                      }
                    Text(
                      text =
                        "Indicateurs Bulūgh al-Amal : $thresholdText · Droite ${bulugh.rightSideOpenPoints} vs Gauche ${bulugh.leftSideOpenPoints} pts ouverts · Mawāzīn ${bulugh.mawazinFortunateCount}F/${bulugh.mawazinUnfortunateCount}D" +
                          if (bulugh.judgeParityValid) " · M15 pair ✓" else "",
                      style = MaterialTheme.typography.labelSmall,
                      color = MaterialTheme.colorScheme.onSurfaceVariant,
                      modifier = Modifier.testTag("bulugh_duel_insights"),
                    )
                    bulugh.activeRulesNotes.drop(1).take(2).forEach { note ->
                      Text(
                        text = "• $note",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                      )
                    }
                  }
                  if (alFulkDuel != null) {
                    val isDivergent = alFulkDuel.convergenceLabel.contains("divergence", ignoreCase = true) ||
                      alFulkDuel.convergenceLabel.contains("Prudence", ignoreCase = true)
                    val convColor = if (isDivergent) extColors.mixed else extColors.fortunate
                    Surface(
                      shape = RoundedCornerShape(10.dp),
                      color = convColor.copy(alpha = 0.12f),
                      border = BorderStroke(1.dp, convColor.copy(alpha = 0.35f)),
                      modifier = Modifier.fillMaxWidth().testTag("al_fulk_duel_crosscheck"),
                    ) {
                      Column(modifier = Modifier.padding(9.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                          text = "Contrôle Al-Fulk al-Mashḥūn : ${alFulkDuel.convergenceLabel}",
                          style = MaterialTheme.typography.labelMedium,
                          fontWeight = FontWeight.SemiBold,
                          color = convColor,
                        )
                        Text(
                          text =
                            "Mod-9 (${alFulkDuel.campAMod9Remainder}:${alFulkDuel.campBMod9Remainder} → ${alFulkDuel.mod9Verdict.shortLabel}) · " +
                              "Impaires/Paires (${alFulkDuel.oddHousesFortunateCount}:${alFulkDuel.evenHousesFortunateCount} → ${alFulkDuel.oddVsEvenVerdict.shortLabel}) · " +
                              "Awtād (${alFulkDuel.awtadSubJudge.westAfricanName}/${alFulkDuel.awtadSubOutcome.westAfricanName} → ${alFulkDuel.awtadVerdict.shortLabel}) · " +
                              "Mod-4 → ${alFulkDuel.mod4Verdict.shortLabel}",
                          style = MaterialTheme.typography.labelSmall,
                          color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        val crossA = alFulkDuel.crossedAwtadA
                        val crossB = alFulkDuel.crossedAwtadB
                        val crossVerdict = alFulkDuel.crossedAwtadVerdict
                        if (crossA != null && crossB != null && crossVerdict != null) {
                          Text(
                            text = "Awtād croisés (p. 316) : ${crossA.westAfricanName} vs ${crossB.westAfricanName} → ${crossVerdict.shortLabel} · ${alFulkDuel.dignityBalanceNote}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                          )
                        }
                      }
                    }
                  }
                  analysis.contestPrediction?.hawiInsights?.let { hawi ->
                    Surface(
                      shape = RoundedCornerShape(10.dp),
                      color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                      border = BorderStroke(1.dp, extColors.cardBorder),
                      modifier = Modifier.fillMaxWidth().testTag("hawi_duel_crosscheck"),
                    ) {
                      Column(modifier = Modifier.padding(9.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                          text = "Contrôle Ḥāwī al-Akhbār : ${hawi.summaryNote}",
                          style = MaterialTheme.typography.labelMedium,
                          fontWeight = FontWeight.SemiBold,
                          color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                          text =
                            "Paires : ${hawi.pairBalanceVerdict.shortLabel} · Avantage Sulṭānī : ${if (hawi.easternSultaniAdvantage >= 0) "+${hawi.easternSultaniAdvantage}" else hawi.easternSultaniAdvantage} · " +
                              "Dignités Sharaf/Hubūṭ : A(${hawi.dignityBalanceA}) vs B(${hawi.dignityBalanceB})",
                          style = MaterialTheme.typography.labelSmall,
                          color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                      }
                    }
                  }
                  analysis.contestPrediction?.matchPrecision?.let { mp ->
                    val redColor = if (mp.redCardLikely) extColors.unfortunate else extColors.fortunate
                    Surface(
                      shape = RoundedCornerShape(12.dp),
                      color = MaterialTheme.colorScheme.surface.copy(alpha = 0.78f),
                      border = BorderStroke(1.dp, extColors.goldAccent.copy(alpha = 0.45f)),
                      modifier = Modifier.fillMaxWidth().testTag("contest_match_precision_card"),
                    ) {
                      Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                          text = "Précisions Match : Score, Buts & Discipline",
                          style = MaterialTheme.typography.labelMedium,
                          fontWeight = FontWeight.Bold,
                          color = MaterialTheme.colorScheme.primary,
                        )
                        Row(
                          modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                          horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                          FilterChip(
                            selected = optionExactScore,
                            onClick = onToggleOptionExactScore,
                            label = { Text("Score exact", maxLines = 1) },
                            colors =
                              FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                                selectedLabelColor = MaterialTheme.colorScheme.primary,
                              ),
                            modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("takht_option_exact_score"),
                          )
                          FilterChip(
                            selected = optionYellowCards,
                            onClick = onToggleOptionYellowCards,
                            label = { Text("Cartons jaunes", maxLines = 1) },
                            colors =
                              FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                                selectedLabelColor = MaterialTheme.colorScheme.primary,
                              ),
                            modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("takht_option_yellow_cards"),
                          )
                          FilterChip(
                            selected = optionRedCards,
                            onClick = onToggleOptionRedCards,
                            label = { Text("Carton rouge", maxLines = 1) },
                            colors =
                              FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.16f),
                                selectedLabelColor = MaterialTheme.colorScheme.primary,
                              ),
                            modifier = Modifier.defaultMinSize(minHeight = 48.dp).testTag("takht_option_red_cards"),
                          )
                        }

                        if (!optionExactScore && !optionYellowCards && !optionRedCards) {
                          Text(
                            text = "Active au moins une option ci-dessus (Score exact, Cartons jaunes ou Carton rouge) pour afficher ses prévisions.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.testTag("precision_options_disabled_hint"),
                          )
                        }

                        if (optionExactScore) {
                          Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                          ) {
                            MetricPill(
                              label = "Score exact probable",
                              value = mp.exactScoreLabel,
                              modifier = Modifier.weight(1f).testTag("precision_exact_score"),
                            )
                            MetricPill(
                              label = "Les 2 équipes marquent",
                              value = "${if (mp.bothTeamsToScore) "Oui" else "Non"} (${mp.bttsConfidencePercent}%)",
                              modifier = Modifier.weight(1f).testTag("precision_btts"),
                            )
                          }
                        }
                        if (optionYellowCards || optionRedCards) {
                          Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                          ) {
                            if (optionYellowCards) {
                              MetricPill(
                                label = "Cartons jaunes (${mp.cardIntensity.label})",
                                value = mp.yellowCardsTotalRange,
                                modifier = Modifier.weight(1f).testTag("precision_yellow_cards"),
                              )
                            }
                            if (optionRedCards) {
                              MetricPill(
                                label = "Carton rouge",
                                value = "${if (mp.redCardLikely) "Oui" else "Non"} (${mp.redCardRiskPercent}%)",
                                modifier = Modifier.weight(1f).testTag("precision_red_card"),
                              )
                            }
                          }
                        }
                        if (optionExactScore) {
                          Text(
                            text = "Score : ${mp.exactScoreLabel} (alt. ${mp.alternateScoreLabel} · ${mp.totalGoalsRange}) — ${mp.scoreReasoning}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                          )
                          Text(
                            text = "BTTS (${if (mp.bothTeamsToScore) "Oui" else "Non"}) : ${mp.bttsReason}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                          )
                        }
                        if (optionYellowCards) {
                          Text(
                            text = "Cartons jaunes par camp : Camp A ${mp.yellowCardsCampA.first}–${mp.yellowCardsCampA.last} · Camp B ${mp.yellowCardsCampB.first}–${mp.yellowCardsCampB.last}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                          )
                        }
                        if (optionRedCards) {
                          Text(
                            text = "Expulsion : ${if (mp.redCardLikely) "Risque élevé" else "Peu probable"} (${mp.redCardRiskPercent}%) — ${mp.redCardReason}",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (mp.cardIntensity == CardIntensityLevel.CRITICAL || mp.redCardLikely) redColor else MaterialTheme.colorScheme.onSurfaceVariant,
                          )
                        }
                      }
                    }
                  }
                  analysis.contestPrediction?.alQawlInsights?.let { qawl ->
                    Surface(
                      shape = RoundedCornerShape(10.dp),
                      color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                      border = BorderStroke(1.dp, extColors.cardBorder),
                      modifier = Modifier.fillMaxWidth().testTag("al_qawl_duel_crosscheck"),
                    ) {
                      Column(modifier = Modifier.padding(9.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text(
                          text = "Contrôle Al-Qawl al-Jāmiʿ & La Mecque : ${qawl.summaryNote}",
                          style = MaterialTheme.typography.labelMedium,
                          fontWeight = FontWeight.SemiBold,
                          color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                          text =
                            "Mères+Droit (${qawl.mothersOpenPoints}) vs Filles+Gauche (${qawl.daughtersOpenPoints}) · " +
                              "Zanātī M1⊕M10 (${qawl.m1M10Composite.westAfricanName}) vs M7⊕M4 (${qawl.m7M4Composite.westAfricanName}) · " +
                              "Aspects → ${qawl.aspectAdvantage.shortLabel}",
                          style = MaterialTheme.typography.labelSmall,
                          color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                      }
                    }
                  }
                }
              }
            }
            if (savedContextNotes.isNotEmpty() || savedActualScore.isNotEmpty() || savedActualYellowCards.isNotEmpty() || savedActualRedCards.isNotEmpty()) {
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
              ) {
                Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                  if (savedActualScore.isNotEmpty() || savedActualYellowCards.isNotEmpty() || savedActualRedCards.isNotEmpty()) {
                    val obsSummary =
                      listOfNotNull(
                        savedActualScore.takeIf { it.isNotEmpty() }?.let { "Score : $it" },
                        savedActualYellowCards.takeIf { it.isNotEmpty() }?.let { "Cartons J : $it" },
                        savedActualRedCards.takeIf { it.isNotEmpty() }?.let { "Carton R : $it" },
                      ).joinToString(" · ")
                    Text(
                      text = obsSummary,
                      style = MaterialTheme.typography.labelMedium,
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                  }
                  if (savedContextNotes.isNotEmpty()) {
                    Text(
                      text = "Contexte / Résultat enregistré : $savedContextNotes",
                      style = MaterialTheme.typography.bodySmall,
                      color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                  }
                }
              }
            }
            val signatureCode = remember(reading) { PredictiveCalibrationEngine.canonicalSignature(reading) }
            val recurrenceCount = themeRecurrence?.occurrenceCount ?: 0
            Surface(
              shape = RoundedCornerShape(12.dp),
              color =
                if (recurrenceCount >= 2 || (!isSaved && recurrenceCount >= 1)) {
                  extColors.goldAccent.copy(alpha = 0.16f)
                } else {
                  MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f)
                },
              border =
                BorderStroke(
                  1.dp,
                  if (recurrenceCount >= 2 || (!isSaved && recurrenceCount >= 1)) {
                    extColors.goldAccent.copy(alpha = 0.55f)
                  } else {
                    extColors.cardBorder
                  },
                ),
              modifier = Modifier.fillMaxWidth().testTag("takht_recurrence_banner"),
            ) {
              Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                  Icon(
                    imageVector = Icons.Rounded.Repeat,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp),
                  )
                  val countHeadline =
                    when {
                      recurrenceCount == 0 -> "Thème inédit (0 apparition précédente)"
                      !isSaved -> "Thème déjà apparu $recurrenceCount fois dans les archives"
                      recurrenceCount == 1 -> "Archivé 1 fois (signature unique pour l’instant)"
                      else -> "Thème récurrent : apparu $recurrenceCount fois en archive"
                    }
                  Text(
                    text = countHeadline,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                  )
                }
                Text(
                  text = "Empreinte M1–M4 : $signatureCode",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                themeRecurrence?.occurrences?.take(3)?.forEach { occ ->
                  val dateText =
                    DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRENCH)
                      .format(Date(occ.createdAtMillis))
                  val outcomeSuffix = occ.actualOutcomeLabel?.let { " · $it" }.orEmpty()
                  Text(
                    text = "• $dateText — ${occ.title}$outcomeSuffix",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                  )
                }
              }
            }
            if (isSaved && autoSaveEnabled) {
              val catText = savedLeagueCategory.ifBlank { reading.domain.title }
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = extColors.fortunate.copy(alpha = 0.12f),
                border = BorderStroke(1.dp, extColors.fortunate.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth().testTag("takht_autosave_badge"),
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                  Icon(
                    imageVector = Icons.Rounded.BookmarkAdded,
                    contentDescription = null,
                    tint = extColors.fortunate,
                    modifier = Modifier.size(16.dp),
                  )
                  Text(
                    text = "Enregistré automatiquement · Catégorie « $catText »",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = extColors.fortunate,
                  )
                }
              }
            }
            OutlinedButton(
              onClick = { showSaveDialog = true },
              modifier = Modifier.fillMaxWidth().testTag("save_theme_button"),
              shape = RoundedCornerShape(12.dp),
            ) {
              Icon(
                imageVector = if (isSaved) Icons.Rounded.BookmarkAdded else Icons.Rounded.BookmarkAdd,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text =
                  if (isSaved) stringResource(R.string.btn_edit_context)
                  else stringResource(R.string.btn_save_theme),
              )
            }
          }
        }
      }

      // Tableau visuel des 16 maisons du Takht
      item {
        TakhtTableauCard(
          reading = reading,
          inspectedHouseNumber = inspectedHouseNumber,
          onInspectHouse = onInspectHouse,
        )
      }

      // Carte d'inspection de la maison sélectionnée
      item {
        InspectedHouseDetailCard(
          house = inspectedHouse,
          onOpenFigureDetail = { onOpenFigureDetail(inspectedHouse.figure) },
        )
      }

      // Istikhraj al-Damir (Pensée cachée)
      item {
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(Icons.Rounded.Psychology, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Text(
                text = "Istikhrāj al-Ḍamīr · استخراج الضمير",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
            }
            ElementTag(element = analysis.damirReport.dominantElement)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
              Element.entries.forEach { elem ->
                val count = analysis.damirReport.elementCounts[elem] ?: 0
                val elemColor = extColors.forElement(elem)
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                  Text(
                    text = elem.englishName,
                    style = MaterialTheme.typography.labelMedium,
                    color = elemColor,
                    modifier = Modifier.width(52.dp),
                  )
                  LinearProgressIndicator(
                    progress = { (count / 16f).coerceIn(0f, 1f) },
                    color = elemColor,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.weight(1f).height(7.dp),
                  )
                  Text(
                    text = "$count/16",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                  )
                }
              }
            }
            Text(
              text = analysis.damirReport.unspokenFocus,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = analysis.damirReport.taskinResonance,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }

      // Intiqal & Mujawara
      item {
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(Icons.Rounded.SwapHoriz, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Text(
                text = "Intiqāl & Mujāwara · انتقال الشكل",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
            }
            Text(
              text = analysis.querentMigration.interpretation,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = analysis.domainMigration.interpretation,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = "Voisinage autour de M${analysis.judgeFlanking.targetHouse} : ${analysis.judgeFlanking.status}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }

      // Abjad, Délais & Direction
      item {
        LiquidGlassCard(modifier = Modifier.fillMaxWidth()) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(Icons.Rounded.CompassCalibration, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Text(
                text = "Abjad, Délais & Direction · الحروف",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
              MetricPill(
                label = "Lettre extraite",
                value = "${analysis.abjadExtraction.extractedLetter} (${analysis.abjadExtraction.letterName})",
                modifier = Modifier.weight(1f),
              )
              MetricPill(
                label = "Somme Abjad",
                value = "${analysis.abjadExtraction.totalAbjadSum} → #${analysis.abjadExtraction.reducedMod28}",
                modifier = Modifier.weight(1f),
              )
            }
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
              MetricPill(
                label = "Horizon temporel",
                value = analysis.abjadExtraction.timingEstimate,
                modifier = Modifier.weight(1f),
              )
              MetricPill(
                label = "Direction",
                value = analysis.abjadExtraction.favorableDirection,
                modifier = Modifier.weight(1f),
              )
            }
          }
        }
      }

      // Al-Fulk al-Mashhun : 4 Figures Pures, Batin al-Takht & Diagnostic M12
      analysis.alFulkInsights?.let { alFulk ->
        item {
          AlFulkInsightsCard(
            insights = alFulk,
            onOpenFigureDetail = onOpenFigureDetail,
          )
        }
      }

      // Al-Qawl al-Jami & Manuscrits de La Mecque : Seuil 96, Vie/Fermeture, 5 Triangles & Aspects
      analysis.alQawlInsights?.let { alQawl ->
        item {
          AlQawlInsightsCard(
            insights = alQawl,
            onOpenFigureDetail = onOpenFigureDetail,
          )
        }
      }

      // Conseil spirituel et planétaire Al-Toukhi
      item {
        LiquidGlassCard(
          modifier = Modifier.fillMaxWidth(),
          accentBorderColor = extColors.goldAccent,
        ) {
          Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(Icons.Rounded.AutoAwesome, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Text(
                text = "Conseil Spirituel & Planétaire Al-Toukhi",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
              )
            }
            Text(
              text = analysis.planetaryAlignmentNote,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = analysis.humoralDiagnosis,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
            ) {
              Text(
                text = analysis.spiritualPrescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(12.dp),
              )
            }
          }
        }
      }

      item { Spacer(modifier = Modifier.height(12.dp)) }
    }
  }

  if (showSaveDialog) {
    val defaultTitle =
      savedTitle.ifEmpty { reading.questionText.ifEmpty { "Tirage · ${reading.domain.title}" } }
    SaveOrEditThemeDialog(
      dialogTitle = stringResource(R.string.save_dialog_title),
      initialTitle = defaultTitle,
      initialContext = savedContextNotes,
      isContestDomain = reading.domain == QuestionDomain.CONTEST,
      initialOutcome = savedActualOutcome,
      initialScore = savedActualScore,
      initialLeague =
        savedLeagueCategory.ifBlank {
          if (reading.domain == QuestionDomain.CONTEST) {
            MatchOutcomeParser.inferLeague("", reading.questionText, savedContextNotes)
          } else {
            reading.domain.title
          }
        },
      initialYellowCards = savedActualYellowCards,
      initialRedCards = savedActualRedCards,
      onDismiss = { showSaveDialog = false },
      onConfirm = { newTitle, newContext, outcome, score, league, yellowCards, redCards ->
        if (onSaveThemeWithCards != null) {
          onSaveThemeWithCards(newTitle, newContext, outcome, score, league, yellowCards, redCards)
        } else {
          onSaveTheme(newTitle, newContext, outcome, score, league)
        }
        showSaveDialog = false
      },
    )
  }
}

@Composable
private fun TakhtTableauCard(
  reading: TakhtReading,
  inspectedHouseNumber: Int,
  onInspectHouse: (Int) -> Unit,
) {
  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("takht_shield_layout"),
    shape = RoundedCornerShape(20.dp),
  ) {
    Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Text(
        text = "Takht al-Raml (de droite à gauche · M1 → M16)",
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 4.dp),
      )

      // Rang 1 : M8  M7  M6  M5  M4  M3  M2  M1 (de droite à gauche)
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        (8 downTo 1).forEach { num ->
          val ch = reading.house(num)
          Box(modifier = Modifier.weight(1f).padding(horizontal = 1.5.dp)) {
            HouseCell(
              house = ch,
              isSelected = num == inspectedHouseNumber,
              onClick = { onInspectHouse(num) },
              compact = true,
              modifier = Modifier.fillMaxWidth(),
            )
          }
        }
      }

      // Rang 2 : M12  M11  M10  M9 (centrées sous leurs paires parentes)
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        (12 downTo 9).forEach { num ->
          val ch = reading.house(num)
          Box(
            modifier = Modifier.weight(2f),
            contentAlignment = Alignment.Center,
          ) {
            HouseCell(
              house = ch,
              isSelected = num == inspectedHouseNumber,
              onClick = { onInspectHouse(num) },
              compact = false,
              modifier = Modifier.fillMaxWidth(0.76f),
            )
          }
        }
      }

      // Rang 3 : M14 (gauche, sous M12-M11)  et  M13 (droite, sous M10-M9)
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Box(
          modifier = Modifier.weight(4f),
          contentAlignment = Alignment.Center,
        ) {
          HouseCell(
            house = reading.house(14),
            isSelected = inspectedHouseNumber == 14,
            onClick = { onInspectHouse(14) },
            compact = false,
            modifier = Modifier.fillMaxWidth(0.38f),
          )
        }
        Box(
          modifier = Modifier.weight(4f),
          contentAlignment = Alignment.Center,
        ) {
          HouseCell(
            house = reading.house(13),
            isSelected = inspectedHouseNumber == 13,
            onClick = { onInspectHouse(13) },
            compact = false,
            modifier = Modifier.fillMaxWidth(0.38f),
          )
        }
      }

      // Rang 4 : M15 (au centre sous M14–M13)  et  M16 (à sa droite)
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Spacer(modifier = Modifier.weight(3f))
        Box(
          modifier = Modifier.weight(2f),
          contentAlignment = Alignment.Center,
        ) {
          HouseCell(
            house = reading.house(15),
            isSelected = inspectedHouseNumber == 15,
            onClick = { onInspectHouse(15) },
            compact = false,
            modifier = Modifier.fillMaxWidth(0.76f),
          )
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
          modifier = Modifier.weight(2f),
          contentAlignment = Alignment.Center,
        ) {
          HouseCell(
            house = reading.house(16),
            isSelected = inspectedHouseNumber == 16,
            onClick = { onInspectHouse(16) },
            compact = false,
            modifier = Modifier.fillMaxWidth(0.76f),
          )
        }
      }
    }
  }
}

@Composable
private fun HouseCell(
  house: ChartHouse,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  compact: Boolean = false,
) {
  val extColors = AlRamlColors.extended
  val polarityColor = extColors.forPolarity(house.figure.polarity)
  Surface(
    modifier =
      modifier
        .defaultMinSize(minHeight = 48.dp)
        .clickable(onClick = onClick)
        .testTag("takht_house_${house.info.number}"),
    shape = RoundedCornerShape(if (compact) 10.dp else 12.dp),
    color =
      if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.16f)
      else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
    border =
      BorderStroke(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) MaterialTheme.colorScheme.primary else extColors.cardBorder,
      ),
  ) {
    Column(
      modifier = Modifier.padding(vertical = if (compact) 6.dp else 8.dp, horizontal = if (compact) 2.dp else 4.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(if (compact) 3.dp else 4.dp),
    ) {
      Text(
        text = "M${house.info.number}",
        style = MaterialTheme.typography.labelSmall.copy(fontSize = if (compact) 9.5.sp else 11.sp),
        color = polarityColor,
        fontWeight = FontWeight.Bold,
      )
      GeomanticFigureGlyph(
        figure = house.figure,
        size = if (compact) 22.dp else 28.dp,
        dotColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
      )
      Text(
        text = house.figure.westAfricanName,
        style = MaterialTheme.typography.labelSmall.copy(fontSize = if (compact) 8.5.sp else 10.sp),
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
      )
    }
  }
}

@Composable
private fun InspectedHouseDetailCard(
  house: ChartHouse,
  onOpenFigureDetail: () -> Unit,
) {
  val extColors = AlRamlColors.extended
  val dignity = remember(house) { AlToukhiHouses.dignityOf(house.figure, house.info.number) }
  val temporalRole = remember(house.info.number) { AlToukhiHouses.temporalRoleFor(house.info.number) }
  val strengthRank = remember(house.info.number) { AlToukhiHouses.houseStrengthRank(house.info.number) }
  val triangularVal = remember(house.info.number) { AlToukhiHouses.hawiTriangularValue(house.info.number) }
  val dignityColor =
    when (dignity) {
      PlanetaryDignity.OWN_AND_EXALTATION, PlanetaryDignity.EXALTATION, PlanetaryDignity.OWN_HOUSE -> extColors.fortunate
      PlanetaryDignity.FALL -> extColors.unfortunate
      PlanetaryDignity.NEUTRAL -> MaterialTheme.colorScheme.onSurfaceVariant
    }

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().clickable(onClick = onOpenFigureDetail).testTag("inspected_house_detail_card"),
    shape = RoundedCornerShape(18.dp),
    elevated = true,
    accentBorderColor = MaterialTheme.colorScheme.primary,
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      horizontalArrangement = Arrangement.spacedBy(14.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      GeomanticFigureGlyph(figure = house.figure, size = 46.dp, useElementColors = true)
      Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "Maison ${house.info.number} : ${house.info.englishName}",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
          )
          PolarityBadge(polarity = house.figure.polarity)
        }
        Text(
          text = "${house.figure.westAfricanName} (${house.figure.transliteration} · ${house.figure.arabicName})",
          style = MaterialTheme.typography.titleSmall,
          color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
          text = "${house.info.domainMeaning} ${house.figure.generalMeaning}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
          text = "Dignité : ${dignity.label} · ${temporalRole.label} · Force #$strengthRank/16 (Δ=$triangularVal)",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.SemiBold,
          color = dignityColor,
        )
        Text(
          text = "Élément Maison : ${house.info.element.englishName} (${house.info.direction}) · Dérivation : ${house.info.derivationRule} · Toucher pour détails →",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.primary,
        )
      }
    }
  }
}

@Composable
private fun MetricPill(
  label: String,
  value: String,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
  ) {
    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = value,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}

@Composable
private fun EmptyTakhtState(
  onGoToCast: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = modifier.fillMaxSize().padding(24.dp),
    contentAlignment = Alignment.Center,
  ) {
    LiquidGlassCard(
      modifier = Modifier.widthIn(max = 420.dp),
      shape = RoundedCornerShape(22.dp),
      elevated = true,
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.AutoAwesome,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(42.dp),
        )
        Text(
          text = stringResource(R.string.takht_empty_title),
          style = MaterialTheme.typography.headlineSmall,
          color = MaterialTheme.colorScheme.primary,
          textAlign = TextAlign.Center,
        )
        Text(
          text = stringResource(R.string.takht_empty_desc),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
        )
        Button(
          onClick = onGoToCast,
          modifier = Modifier.testTag("empty_takht_cast_btn"),
          shape = RoundedCornerShape(12.dp),
        ) {
          Text(stringResource(R.string.takht_go_cast))
        }
      }
    }
  }
}

@Composable
private fun AlFulkInsightsCard(
  insights: AlFulkGeneralInsights,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
) {
  val extColors = AlRamlColors.extended
  val pureItems =
    listOf(
      Triple(Element.FIRE, "Feu-de-Feu", insights.pureElements.fireOfFire),
      Triple(Element.AIR, "Air-d’Air", insights.pureElements.airOfAir),
      Triple(Element.WATER, "Eau-d’Eau", insights.pureElements.waterOfWater),
      Triple(Element.EARTH, "Terre-de-Terre", insights.pureElements.earthOfEarth),
    )

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("al_fulk_insights_card"),
    shape = RoundedCornerShape(20.dp),
  ) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(Icons.Rounded.Layers, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(
          text = "Synthèse « Al-Fulk al-Mashḥūn » · الفلك المشحون",
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.primary,
        )
      }

      Text(
        text = insights.reliability.explanation,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
      )

      Text(
        text = "Quatre Figures Élémentaires Pures (pp. 77–78)",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        pureItems.forEach { (elem, label, fig) ->
          val elemColor = extColors.forElement(elem)
          Surface(
            modifier = Modifier.weight(1f).clickable { onOpenFigureDetail(fig) },
            shape = RoundedCornerShape(12.dp),
            color = elemColor.copy(alpha = 0.10f),
            border = BorderStroke(1.dp, elemColor.copy(alpha = 0.35f)),
          ) {
            Column(
              modifier = Modifier.padding(8.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = elemColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
              GeomanticFigureGlyph(figure = fig, size = 26.dp, dotColor = elemColor)
              Text(
                text = fig.westAfricanName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
              )
            }
          }
        }
      }
      Text(
        text = insights.pureElements.realizationSpeedNote,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      Text(
        text = "Thème Intérieur (Bāṭin al-Takht · Mᵢ ⊕ Taskīn, pp. 141–143)",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
      )
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        insights.batinHighlights.forEach { item ->
          Surface(
            modifier = Modifier.fillMaxWidth().clickable { onOpenFigureDetail(item.batinFigure) },
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
              horizontalArrangement = Arrangement.spacedBy(10.dp),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              GeomanticFigureGlyph(figure = item.batinFigure, size = 24.dp, useElementColors = true)
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "${item.houseLabel} → Bāṭin : ${item.batinFigure.westAfricanName}",
                  style = MaterialTheme.typography.labelMedium,
                  color = MaterialTheme.colorScheme.onSurface,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                )
                Text(
                  text = "${item.surfaceFigure.westAfricanName} ⊕ ${item.taskinRuler.westAfricanName} (Taskīn)",
                  style = MaterialTheme.typography.labelSmall,
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  maxLines = 1,
                  overflow = TextOverflow.Ellipsis,
                )
              }
              PolarityBadge(polarity = item.batinFigure.polarity)
            }
          }
        }
      }

      insights.temporalDynamics?.let { td ->
        Text(
          text = "Dynamique Temporelle (Awtād Présent · Succédentes Futur · Cadentes Passé)",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.SemiBold,
        )
        Row(
          modifier = Modifier.fillMaxWidth().testTag("temporal_dynamics_row"),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          MetricPill(
            label = "Passé (Cad.)",
            value = "${td.pastFortunateCount}F/${td.pastUnfortunateCount}D · ${td.pastDominantElement.englishName}",
            modifier = Modifier.weight(1f),
          )
          MetricPill(
            label = "Présent (Awtād)",
            value = "${td.presentFortunateCount}F/${td.presentUnfortunateCount}D · ${td.presentDominantElement.englishName}",
            modifier = Modifier.weight(1f),
          )
          MetricPill(
            label = "Futur (Succ.)",
            value = "${td.futureFortunateCount}F/${td.futureUnfortunateCount}D · ${td.futureDominantElement.englishName}",
            modifier = Modifier.weight(1f),
          )
        }
        Text(
          text = td.trajectorySummary,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      if (insights.relationalResonances.isNotEmpty()) {
        Text(
          text = "Mawāzīn Relationnels de M1 (Ḥāwī al-Akhbār · pp. 112–113)",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.SemiBold,
        )
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
          insights.relationalResonances.forEach { res ->
            Surface(
              modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .clickable { onOpenFigureDetail(res.targetFigure) },
              shape = RoundedCornerShape(10.dp),
              color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                GeomanticFigureGlyph(figure = res.targetFigure, size = 24.dp, useElementColors = true)
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "${res.relationType} → ${res.targetFigure.westAfricanName}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                  )
                  Text(
                    text = res.interpretation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                  )
                }
              }
            }
          }
        }
      }

      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
      ) {
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
          if (insights.dignitySummaryNote.isNotEmpty()) {
            Text(
              text = insights.dignitySummaryNote,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.primary,
            )
          }
          Text(
            text = "Obstacle M12 (p. 136) : ${insights.m12ObstacleDiagnostic}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
          )
          Text(
            text = "Cadence traditionnelle (pp. 250–265) : ${insights.traditionalDurationHint}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }
  }
}

@Composable
private fun AlQawlInsightsCard(
  insights: AlQawlAlJamiGeneralInsights,
  onOpenFigureDetail: (GeomanticFigure) -> Unit,
) {
  val extColors = AlRamlColors.extended
  val completeColor = if (insights.isComplete96) extColors.fortunate else extColors.mixed

  LiquidGlassCard(
    modifier = Modifier.fillMaxWidth().testTag("al_qawl_insights_card"),
    shape = RoundedCornerShape(20.dp),
  ) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(Icons.Rounded.CompassCalibration, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(
          text = "Synthèse « Al-Qawl al-Jāmiʿ » & MS 2380/2405",
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.primary,
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        MetricPill(
          label = "Seuil 96 pts",
          value = "${insights.totalChartDots}/128 · ${if (insights.isComplete96) "Raml Kāmil ✓" else "Raml Nāqiṣ"}",
          modifier = Modifier.weight(1f).testTag("al_qawl_96_pill"),
        )
        MetricPill(
          label = "Vie vs Fermeture",
          value = "Air+Eau ${insights.airWaterOpenPoints} / Feu+Terre ${insights.fireEarthOpenPoints}",
          modifier = Modifier.weight(1f).testTag("al_qawl_vitality_pill"),
        )
      }

      Text(
        text = insights.vitalityBalanceNote,
        style = MaterialTheme.typography.bodySmall,
        color = completeColor,
      )

      Text(
        text = "Les 5 Triangles du Takht (Muthallathāt al-Buyūt)",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
      )
      Column(
        modifier = Modifier.fillMaxWidth().testTag("al_qawl_triangles_section"),
        verticalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        listOf(insights.fiveTriangles.take(3), insights.fiveTriangles.drop(3)).forEach { rowTriangles ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            rowTriangles.forEach { tri ->
              val triColor = extColors.forPolarity(tri.apexFigure.polarity)
              Surface(
                modifier =
                  Modifier
                    .weight(1f)
                    .defaultMinSize(minHeight = 48.dp)
                    .clickable { onOpenFigureDetail(tri.apexFigure) }
                    .testTag("triangle_pill_${tri.index}"),
                shape = RoundedCornerShape(12.dp),
                color = triColor.copy(alpha = 0.10f),
                border = BorderStroke(1.dp, triColor.copy(alpha = 0.35f)),
              ) {
                Column(
                  modifier = Modifier.padding(8.dp),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                  Text(
                    text = "T${tri.index} (M${tri.apexHouseNumber})",
                    style = MaterialTheme.typography.labelSmall,
                    color = triColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                  )
                  GeomanticFigureGlyph(figure = tri.apexFigure, size = 24.dp, dotColor = triColor)
                  Text(
                    text = tri.apexFigure.westAfricanName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                  )
                }
              }
            }
          }
        }
      }
      Text(
        text = insights.trianglesVerdictNote,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      Text(
        text = "Aspects (M1–M12) & Amitiés Planétaires",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
      )
      Text(
        text = insights.planetarySympathyNote,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Column(
        modifier = Modifier.fillMaxWidth().testTag("al_qawl_aspects_section"),
        verticalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        if (insights.aspectLinks.isEmpty()) {
          Text(
            text = "Aucun aspect direct (Tasdīs, Tarbīʿ, Tathlīth, Muqābala) entre figures identiques sur M1–M12.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        } else {
          insights.aspectLinks.forEach { link ->
            val aspColor = if (link.aspectType.isHarmonious) extColors.fortunate else extColors.mixed
            Surface(
              modifier =
                Modifier
                  .fillMaxWidth()
                  .defaultMinSize(minHeight = 48.dp)
                  .clickable { onOpenFigureDetail(link.figure) },
              shape = RoundedCornerShape(10.dp),
              color = aspColor.copy(alpha = 0.10f),
              border = BorderStroke(1.dp, aspColor.copy(alpha = 0.30f)),
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                GeomanticFigureGlyph(figure = link.figure, size = 22.dp, useElementColors = true)
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "${link.aspectType.label} · M${link.firstHouse} ↔ M${link.secondHouse}",
                    style = MaterialTheme.typography.labelMedium,
                    color = aspColor,
                    fontWeight = FontWeight.SemiBold,
                  )
                  Text(
                    text = link.interpretation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                  )
                }
              }
            }
          }
        }
      }

      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
      ) {
        Text(
          text = insights.tarabulusiTimingHint,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.fillMaxWidth().padding(10.dp),
        )
      }
    }
  }
}
