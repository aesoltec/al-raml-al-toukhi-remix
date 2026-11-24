package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
  entities = [SavedThemeEntity::class, CalibratedWeightsEntity::class],
  version = 4,
  exportSchema = false,
)
abstract class AlRamlDatabase : RoomDatabase() {
  abstract fun savedThemeDao(): SavedThemeDao

  abstract fun calibrationDao(): CalibrationDao

  companion object {
    @Volatile private var instance: AlRamlDatabase? = null

    val MIGRATION_1_2 =
      object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
          db.execSQL("ALTER TABLE saved_themes ADD COLUMN actualOutcomeName TEXT")
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN actualScoreText TEXT NOT NULL DEFAULT ''",
          )
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN leagueCategory TEXT NOT NULL DEFAULT ''",
          )
          db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS calibrated_weights (
              id INTEGER NOT NULL PRIMARY KEY,
              weightsCsv TEXT NOT NULL,
              isEnabled INTEGER NOT NULL,
              sampleCount INTEGER NOT NULL,
              baselineAccuracyPercent INTEGER NOT NULL,
              calibratedAccuracyPercent INTEGER NOT NULL,
              updatedAtMillis INTEGER NOT NULL
            )
            """.trimIndent(),
          )
        }
      }

    val MIGRATION_2_3 =
      object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN actualYellowCardsText TEXT NOT NULL DEFAULT ''",
          )
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN actualRedCardsText TEXT NOT NULL DEFAULT ''",
          )
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN includeExactScoreOption INTEGER NOT NULL DEFAULT 1",
          )
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN includeYellowCardsOption INTEGER NOT NULL DEFAULT 1",
          )
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN includeRedCardsOption INTEGER NOT NULL DEFAULT 1",
          )
        }
      }

    val MIGRATION_3_4 =
      object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
          db.execSQL(
            "ALTER TABLE saved_themes ADD COLUMN takhtHouseIdsCsv TEXT NOT NULL DEFAULT ''",
          )
        }
      }

    fun getInstance(context: Context): AlRamlDatabase =
      instance
        ?: synchronized(this) {
          instance
            ?: Room.databaseBuilder(
                context.applicationContext,
                AlRamlDatabase::class.java,
                "al_raml_toukhi.db",
              )
              .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
              .build()
              .also { instance = it }
        }
  }
}
