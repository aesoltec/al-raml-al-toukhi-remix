package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité Room stockant l'encyclopédie complète des 16 figures géomantiques
 * (noms traditionnels ouest-africains et arabes, significations et associations).
 */
@Entity(tableName = "geomantic_figures")
data class GeomanticFigureEntity(
  @PrimaryKey val id: Int,
  val westAfricanOrder: Int,
  val code: String,
  val westAfricanName: String,
  val arabicName: String,
  val transliteration: String,
  val traditionalTitle: String,
  val traditionalMeaning: String,
  val dominantElementName: String,
  val elementLabel: String,
  val elementQuality: String,
  val elementHumor: String,
  val elementDirection: String,
  val motionLabel: String,
  val polarityName: String,
  val polarityLabel: String,
  val planetName: String,
  val planetLabel: String,
  val planetArabicName: String,
  val planetDay: String,
  val zodiacSign: String,
  val arabicLetter: String,
  val abjadValue: Int,
  val timingSpeed: String,
  val taskinHouseNumber: Int,
  val taskinHouseName: String,
  val sharafHouseSummary: String,
  val hubutHouseSummary: String,
  val hawiStrengthLabel: String,
  val associationsSummary: String,
  val ruhaniyyaCorrespondence: String,
  val contemplativeDhikr: String,
  val spiritualTemperament: String,
  val operativeNote: String,
  val counsel: String,
  val simpleExplanation: String = "",
  val detailedDescription: String = "",
  val visualRepresentation: String = "",
  val isFavorite: Boolean = false,
)