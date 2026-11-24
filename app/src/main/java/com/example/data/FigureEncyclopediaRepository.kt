package com.example.data

import com.example.geomancy.AlToukhiFigures
import com.example.geomancy.AlToukhiHouses
import com.example.geomancy.Element
import com.example.geomancy.FortunePolarity
import com.example.geomancy.GeomanticFigure
import kotlinx.coroutines.flow.Flow

class FigureEncyclopediaRepository(
  private val dao: GeomanticFigureDao,
) {

  val allFigures: Flow<List<GeomanticFigureEntity>> = dao.observeAll()

  fun observeFigures(
    query: String,
    element: Element? = null,
    polarity: FortunePolarity? = null,
    favoritesOnly: Boolean = false,
  ): Flow<List<GeomanticFigureEntity>> =
    dao.observeFiltered(
      query = query.trim(),
      elementName = element?.name,
      polarityName = polarity?.name,
      favoritesOnly = favoritesOnly,
    )

  suspend fun getById(id: Int): GeomanticFigureEntity? = dao.getById(id)

  suspend fun ensureSeeded() {
    val existingCount = dao.count()
    if (existingCount >= 16) return
    val favoriteIds = dao.getFavoriteIds().toSet()
    val seedEntities = buildSeedEntities(favoriteIds)
    dao.insertAll(seedEntities)
  }

  /**
   * Re-seed with updated encyclopedia data (including simple explanations and visual representations).
   * Call this when the app is updated with new encyclopedia data.
   */
  suspend fun reseedWithEncyclopedia() {
    val favoriteIds = dao.getFavoriteIds().toSet()
    val seedEntities = buildSeedEntities(favoriteIds)
    dao.clearAll()
    dao.insertAll(seedEntities)
  }

  suspend fun toggleFavorite(id: Int, isFavorite: Boolean) {
    dao.updateFavorite(id = id, isFavorite = isFavorite)
  }

  fun toDomainFigure(entity: GeomanticFigureEntity): GeomanticFigure =
    AlToukhiFigures.findById(entity.id)

  companion object {
    fun buildSeedEntities(favoriteIds: Set<Int> = emptySet()): List<GeomanticFigureEntity> =
      AlToukhiFigures.allInWestAfricanOrder.map { fig ->
        val encEntry = com.example.geomancy.GeomanticFigureEncyclopedia.findByCode(fig.code)
        val taskinHouse = AlToukhiHouses.findByNumber(fig.id)
        val sharafNum = AlToukhiHouses.sharafHouseFor(fig.planet)
        val hubutNum = AlToukhiHouses.hubutHouseFor(fig.planet)
        val sharafHouse = AlToukhiHouses.findByNumber(sharafNum)
        val hubutHouse = AlToukhiHouses.findByNumber(hubutNum)
        val hawiTier = AlToukhiFigures.hawiStrengthTier(fig)

        val associationsSummary =
          listOf(
            "Élément ${fig.dominantElement.englishName} (${fig.dominantElement.quality}, ${fig.dominantElement.humor}, ${fig.dominantElement.direction})",
            "Planète ${fig.planet.englishName} (${fig.planet.arabicName}, ${fig.planet.dayEnglish})",
            "Zodiaque : ${fig.zodiacSign}",
            "Lettre ${fig.arabicLetter} (Abjad ${fig.abjadValue})",
            "Maison Taskīn M${taskinHouse.number} (${taskinHouse.englishName})",
            "Sharaf M$sharafNum · Hubūṭ M$hubutNum",
            "Mouvement : ${fig.motion.label} (${fig.timingSpeed})",
            "Rang Ḥāwī : ${hawiTier.label}",
          ).joinToString(" · ")

        GeomanticFigureEntity(
          id = fig.id,
          westAfricanOrder = fig.westAfricanOrder,
          code = fig.code,
          westAfricanName = fig.westAfricanName,
          arabicName = fig.arabicName,
          transliteration = fig.transliteration,
          traditionalTitle = fig.englishTitle,
          traditionalMeaning = fig.generalMeaning,
          dominantElementName = fig.dominantElement.name,
          elementLabel = fig.dominantElement.englishName,
          elementQuality = fig.dominantElement.quality,
          elementHumor = fig.dominantElement.humor,
          elementDirection = fig.dominantElement.direction,
          motionLabel = fig.motion.label,
          polarityName = fig.polarity.name,
          polarityLabel = fig.polarity.arabicName,
          planetName = fig.planet.name,
          planetLabel = fig.planet.englishName,
          planetArabicName = fig.planet.arabicName,
          planetDay = fig.planet.dayEnglish,
          zodiacSign = fig.zodiacSign,
          arabicLetter = fig.arabicLetter,
          abjadValue = fig.abjadValue,
          timingSpeed = fig.timingSpeed,
          taskinHouseNumber = taskinHouse.number,
          taskinHouseName = taskinHouse.englishName,
          sharafHouseSummary = "M$sharafNum (${sharafHouse.englishName})",
          hubutHouseSummary = "M$hubutNum (${hubutHouse.englishName})",
          hawiStrengthLabel = hawiTier.label,
          associationsSummary = associationsSummary,
          ruhaniyyaCorrespondence = fig.spiritualProfile.ruhaniyyaCorrespondence,
          contemplativeDhikr = fig.spiritualProfile.contemplativeDhikr,
          spiritualTemperament = fig.spiritualProfile.spiritualTemperament,
          operativeNote = fig.spiritualProfile.operativeNote,
          counsel = fig.spiritualProfile.counsel,
          simpleExplanation = encEntry?.simpleExplanation ?: fig.generalMeaning,
          detailedDescription = encEntry?.detailedDescription ?: fig.generalMeaning,
          visualRepresentation = encEntry?.visualRepresentation ?: "",
          isFavorite = fig.id in favoriteIds,
        )
      }
  }
}
