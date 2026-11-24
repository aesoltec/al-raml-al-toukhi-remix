package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calibrated_weights")
data class CalibratedWeightsEntity(
  @PrimaryKey val id: Int = 1,
  val weightsCsv: String,
  val isEnabled: Boolean = true,
  val sampleCount: Int = 0,
  val baselineAccuracyPercent: Int = 0,
  val calibratedAccuracyPercent: Int = 0,
  val updatedAtMillis: Long = System.currentTimeMillis(),
)
