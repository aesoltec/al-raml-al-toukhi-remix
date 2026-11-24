package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalibrationDao {

  @Query("SELECT * FROM calibrated_weights WHERE id = 1")
  fun observeActiveWeights(): Flow<CalibratedWeightsEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun upsert(entity: CalibratedWeightsEntity)

  @Query("UPDATE calibrated_weights SET isEnabled = :enabled WHERE id = 1")
  suspend fun setEnabled(enabled: Boolean)

  @Query("DELETE FROM calibrated_weights")
  suspend fun clear()
}
