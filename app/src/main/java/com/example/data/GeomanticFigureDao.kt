package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GeomanticFigureDao {

  @Query("SELECT * FROM geomantic_figures ORDER BY westAfricanOrder ASC")
  fun observeAll(): Flow<List<GeomanticFigureEntity>>

  @Query(
    """
    SELECT * FROM geomantic_figures
    WHERE (:elementName IS NULL OR dominantElementName = :elementName)
      AND (:polarityName IS NULL OR polarityName = :polarityName)
      AND (:favoritesOnly = 0 OR isFavorite = 1)
      AND (
        :query = ''
        OR LOWER(westAfricanName) LIKE '%' || LOWER(:query) || '%'
        OR arabicName LIKE '%' || :query || '%'
        OR LOWER(transliteration) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(traditionalTitle) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(traditionalMeaning) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(associationsSummary) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(elementLabel) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(elementQuality) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(elementHumor) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(elementDirection) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(planetLabel) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(zodiacSign) LIKE '%' || LOWER(:query) || '%'
        OR LOWER(motionLabel) LIKE '%' || LOWER(:query) || '%'
        OR code LIKE '%' || :query || '%'
        OR arabicLetter LIKE '%' || :query || '%'
      )
    ORDER BY westAfricanOrder ASC
    """,
  )
  fun observeFiltered(
    query: String,
    elementName: String?,
    polarityName: String?,
    favoritesOnly: Boolean,
  ): Flow<List<GeomanticFigureEntity>>

  @Query("SELECT * FROM geomantic_figures WHERE id = :id LIMIT 1")
  suspend fun getById(id: Int): GeomanticFigureEntity?

  @Query("SELECT id FROM geomantic_figures WHERE isFavorite = 1")
  suspend fun getFavoriteIds(): List<Int>

  @Query("SELECT COUNT(*) FROM geomantic_figures")
  suspend fun count(): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(figures: List<GeomanticFigureEntity>)

  @Query("UPDATE geomantic_figures SET isFavorite = :isFavorite WHERE id = :id")
  suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}
