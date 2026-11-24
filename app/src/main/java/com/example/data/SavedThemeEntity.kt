package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_themes")
data class SavedThemeEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0L,
  val title: String,
  val domainName: String,
  val questionText: String,
  val planetHourName: String,
  val motherDotCountsCsv: String,
  val judgeFigureId: Int,
  val outcomeFigureId: Int,
  val overallPolarityName: String,
  val contextOutcomeNotes: String = "",
  val createdAtMillis: Long = System.currentTimeMillis(),
  val actualOutcomeName: String? = null,
  val actualScoreText: String = "",
  val leagueCategory: String = "",
  val actualYellowCardsText: String = "",
  val actualRedCardsText: String = "",
  val includeExactScoreOption: Boolean = true,
  val includeYellowCardsOption: Boolean = true,
  val includeRedCardsOption: Boolean = true,
  val takhtHouseIdsCsv: String = "",
)
