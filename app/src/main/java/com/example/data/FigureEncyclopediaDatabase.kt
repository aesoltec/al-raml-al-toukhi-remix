package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [GeomanticFigureEntity::class],
  version = 2,
  exportSchema = false,
)
abstract class FigureEncyclopediaDatabase : RoomDatabase() {
  abstract fun geomanticFigureDao(): GeomanticFigureDao

  companion object {
    @Volatile private var instance: FigureEncyclopediaDatabase? = null

    fun getInstance(context: Context): FigureEncyclopediaDatabase =
      instance
        ?: synchronized(this) {
          instance
            ?: Room.databaseBuilder(
                context.applicationContext,
                FigureEncyclopediaDatabase::class.java,
                "al_raml_figures_encyclopedia.db",
              )
              .fallbackToDestructiveMigration(dropAllTables = true)
              .build()
              .also { instance = it }
        }
  }
}
