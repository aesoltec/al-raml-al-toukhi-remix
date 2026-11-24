package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedThemeDao {

  @Query("SELECT * FROM saved_themes ORDER BY createdAtMillis DESC")
  fun observeAll(): Flow<List<SavedThemeEntity>>

  @Query("SELECT * FROM saved_themes WHERE id = :id LIMIT 1")
  suspend fun getById(id: Long): SavedThemeEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(entity: SavedThemeEntity): Long

  @Query("UPDATE saved_themes SET title = :title, contextOutcomeNotes = :notes WHERE id = :id")
  suspend fun updateContext(id: Long, title: String, notes: String)

  @Query(
    "UPDATE saved_themes SET title = :title, contextOutcomeNotes = :notes, " +
      "actualOutcomeName = :actualOutcomeName, actualScoreText = :actualScoreText, " +
      "leagueCategory = :leagueCategory WHERE id = :id",
  )
  suspend fun updateContextAndOutcome(
    id: Long,
    title: String,
    notes: String,
    actualOutcomeName: String?,
    actualScoreText: String,
    leagueCategory: String,
  )

  @Query(
    "UPDATE saved_themes SET title = :title, contextOutcomeNotes = :notes, " +
      "actualOutcomeName = :actualOutcomeName, actualScoreText = :actualScoreText, " +
      "leagueCategory = :leagueCategory, actualYellowCardsText = :actualYellowCardsText, " +
      "actualRedCardsText = :actualRedCardsText, " +
      "includeExactScoreOption = :includeExactScoreOption, " +
      "includeYellowCardsOption = :includeYellowCardsOption, " +
      "includeRedCardsOption = :includeRedCardsOption WHERE id = :id",
  )
  suspend fun updateContextAndMatchDetails(
    id: Long,
    title: String,
    notes: String,
    actualOutcomeName: String?,
    actualScoreText: String,
    leagueCategory: String,
    actualYellowCardsText: String,
    actualRedCardsText: String,
    includeExactScoreOption: Boolean,
    includeYellowCardsOption: Boolean,
    includeRedCardsOption: Boolean,
  )

  @Query(
    "UPDATE saved_themes SET includeExactScoreOption = :includeExactScore, " +
      "includeYellowCardsOption = :includeYellowCards, " +
      "includeRedCardsOption = :includeRedCards WHERE id = :id",
  )
  suspend fun updatePrecisionOptions(
    id: Long,
    includeExactScore: Boolean,
    includeYellowCards: Boolean,
    includeRedCards: Boolean,
  )

  @Query("DELETE FROM saved_themes WHERE id = :id")
  suspend fun deleteById(id: Long)
}
