package com.example.geomancy

/**
 * Les 16 Maisons (Buyut al-Raml) du Takht al-Raml selon les manuels de Cheikh al-Toukhi,
 * avec leurs gouverneurs naturels du Taskin et leurs règles de dérivation.
 */
object AlToukhiHouses {

  val all: List<HouseInfo> =
    listOf(
      HouseInfo(
        number = 1,
        arabicName = "بيت النفس والحياة",
        englishName = "Le Consultant & l'Âme",
        group = HouseGroup.MOTHERS,
        taskinRulerId = 1,
        element = Element.FIRE,
        direction = "Est",
        domainMeaning = "Le corps, l'esprit, le tempérament et la position présente du consultant.",
        derivationRule = "Première Mère — tirée directement par l'intention.",
      ),
      HouseInfo(
        number = 2,
        arabicName = "بيت المال والرزق",
        englishName = "Biens & Subsistance",
        group = HouseGroup.MOTHERS,
        taskinRulerId = 2,
        element = Element.AIR,
        direction = "Est-Nord-Est",
        domainMeaning = "Revenus, biens mobiliers, ressources immédiates et appuis matériels.",
        derivationRule = "Deuxième Mère — tirée directement par l'intention.",
      ),
      HouseInfo(
        number = 3,
        arabicName = "بيت الإخوة والتنقل",
        englishName = "Proches, Nouvelles & Petits Trajets",
        group = HouseGroup.MOTHERS,
        taskinRulerId = 3,
        element = Element.WATER,
        direction = "Nord-Nord-Est",
        domainMeaning = "Frères, voisins, messages, démarches courtes et déplacements proches.",
        derivationRule = "Troisième Mère — tirée directement par l'intention.",
      ),
      HouseInfo(
        number = 4,
        arabicName = "بيت الآباء والعقار",
        englishName = "Foyer, Racines & Fin des choses",
        group = HouseGroup.MOTHERS,
        taskinRulerId = 4,
        element = Element.EARTH,
        direction = "Nord",
        domainMeaning = "Ascendance, père, terre, demeure, patrimoine et bilan final d'une affaire.",
        derivationRule = "Quatrième Mère — tirée directement par l'intention.",
      ),
      HouseInfo(
        number = 5,
        arabicName = "بيت الأولاد والأفراح",
        englishName = "Enfants, Créativité & Joies",
        group = HouseGroup.DAUGHTERS,
        taskinRulerId = 5,
        element = Element.FIRE,
        direction = "Nord-Nord-Ouest",
        domainMeaning = "Descendance, bonnes nouvelles, élans créatifs, amour et présents.",
        derivationRule = "Extraite horizontalement des rangs de Feu (Tête) des Mères 1 à 4.",
      ),
      HouseInfo(
        number = 6,
        arabicName = "بيت المرض والخدم",
        englishName = "Santé, Travail & Épreuves",
        group = HouseGroup.DAUGHTERS,
        taskinRulerId = 6,
        element = Element.AIR,
        direction = "Ouest-Nord-Ouest",
        domainMeaning = "Déséquilibre humoral, fatigue, labeur quotidien, subordonnés et fautes.",
        derivationRule = "Extraite horizontalement des rangs d'Air (Poitrine) des Mères 1 à 4.",
      ),
      HouseInfo(
        number = 7,
        arabicName = "بيت الزواج والشركاء",
        englishName = "Mariage, Partenaires & Adversaires",
        group = HouseGroup.DAUGHTERS,
        taskinRulerId = 7,
        element = Element.WATER,
        direction = "Ouest",
        domainMeaning = "Conjoint, associé, contrats, opposant direct et objet recherché.",
        derivationRule = "Extraite horizontalement des rangs d'Eau (Ventre) des Mères 1 à 4.",
      ),
      HouseInfo(
        number = 8,
        arabicName = "بيت الخوف والميراث",
        englishName = "Craintes, Mutations & Pertes",
        group = HouseGroup.DAUGHTERS,
        taskinRulerId = 8,
        element = Element.EARTH,
        direction = "Ouest-Sud-Ouest",
        domainMeaning = "Inquiétudes, pertes, héritages, dettes cachées et transformations profondes.",
        derivationRule = "Extraite horizontalement des rangs de Terre (Pieds) des Mères 1 à 4.",
      ),
      HouseInfo(
        number = 9,
        arabicName = "بيت السفر والدين",
        englishName = "Grands Voyages & Sagesse",
        group = HouseGroup.NIECES,
        taskinRulerId = 9,
        element = Element.FIRE,
        direction = "Sud-Sud-Ouest",
        domainMeaning = "Voyages lointains, savoir sacré, songes, érudits et vérité supérieure.",
        derivationRule = "Addition par parité de Maison 1 + Maison 2.",
      ),
      HouseInfo(
        number = 10,
        arabicName = "بيت الملك والسلطان",
        englishName = "Autorité, Rang & Triomphe",
        group = HouseGroup.NIECES,
        taskinRulerId = 10,
        element = Element.AIR,
        direction = "Sud",
        domainMeaning = "Carrière, honneur, dirigeants, réputation publique et réussite.",
        derivationRule = "Addition par parité de Maison 3 + Maison 4.",
      ),
      HouseInfo(
        number = 11,
        arabicName = "بيت الرجاء والأصدقاء",
        englishName = "Espoirs, Alliés & Soutiens",
        group = HouseGroup.NIECES,
        taskinRulerId = 11,
        element = Element.WATER,
        direction = "Sud-Sud-Est",
        domainMeaning = "Amis fidèles, protecteurs, vœux espérés et appui du milieu.",
        derivationRule = "Addition par parité de Maison 5 + Maison 6.",
      ),
      HouseInfo(
        number = 12,
        arabicName = "بيت الأعداء والحسد",
        englishName = "Ennemis Cachés & Contraintes",
        group = HouseGroup.NIECES,
        taskinRulerId = 12,
        element = Element.EARTH,
        direction = "Est-Sud-Est",
        domainMeaning = "Opposition dissimulée, jalousie, blocage et épreuves intérieures.",
        derivationRule = "Addition par parité de Maison 7 + Maison 8.",
      ),
      HouseInfo(
        number = 13,
        arabicName = "الشاهد الأيمن",
        englishName = "Témoin Droit (Côté Consultant)",
        group = HouseGroup.WITNESSES,
        taskinRulerId = 13,
        element = Element.FIRE,
        direction = "Centre-Est",
        domainMeaning = "Témoigne de l'élan du consultant et des racines de la situation.",
        derivationRule = "Addition par parité de Maison 9 + Maison 10.",
      ),
      HouseInfo(
        number = 14,
        arabicName = "الشاهد الأيسر",
        englishName = "Témoin Gauche (Côté Adversaire / Objet)",
        group = HouseGroup.WITNESSES,
        taskinRulerId = 14,
        element = Element.AIR,
        direction = "Centre-Ouest",
        domainMeaning = "Témoigne des forces extérieures et du cours à venir des événements.",
        derivationRule = "Addition par parité de Maison 11 + Maison 12.",
      ),
      HouseInfo(
        number = 15,
        arabicName = "الميزان (القاضي)",
        englishName = "Le Juge (Al-Mīzān)",
        group = HouseGroup.JUDGE,
        taskinRulerId = 15,
        element = Element.WATER,
        direction = "Zénith / Centre",
        domainMeaning = "Synthèse des deux Témoins ; prononce la sentence centrale du thème.",
        derivationRule = "Addition par parité de Maison 13 + Maison 14.",
      ),
      HouseInfo(
        number = 16,
        arabicName = "عاقبة العاقبة",
        englishName = "L'Issue de l'Issue (Maison 16)",
        group = HouseGroup.OUTCOME,
        taskinRulerId = 16,
        element = Element.EARTH,
        direction = "Retour / Horizon",
        domainMeaning = "Résonance ultime reliant le Juge au Consultant en Maison 1.",
        derivationRule = "Addition par parité de Maison 15 (Juge) + Maison 1 (Consultant).",
      ),
    )

  fun findByNumber(number: Int): HouseInfo =
    all.getOrNull(number - 1) ?: error("Le numéro de maison doit être entre 1 et 16, reçu $number")

  fun taskinRulerFor(number: Int): GeomanticFigure =
    AlToukhiFigures.findById(findByNumber(number).taskinRulerId)

  /**
   * Maison d'Exaltation (al-Sharaf) selon « Al-Fulk al-Mashḥūn » (pp. 67–68).
   * Les Nœuds (Rāʾs/Dhanab) partagent la dignité lunaire (Sharaf M2, Hubūṭ M8).
   */
  fun sharafHouseFor(planet: Planet): Int =
    when (planet) {
      Planet.SUN -> 1
      Planet.MOON, Planet.MARS, Planet.NORTH_NODE, Planet.SOUTH_NODE -> 2
      Planet.JUPITER -> 4
      Planet.MERCURY -> 6
      Planet.SATURN -> 7
      Planet.VENUS -> 12
    }

  /** Maison de Chute (al-Hubūṭ) selon « Al-Fulk al-Mashḥūn » (pp. 67–68). */
  fun hubutHouseFor(planet: Planet): Int =
    when (planet) {
      Planet.SATURN -> 1
      Planet.VENUS -> 4
      Planet.SUN -> 7
      Planet.MOON, Planet.MARS, Planet.NORTH_NODE, Planet.SOUTH_NODE -> 8
      Planet.JUPITER, Planet.MERCURY -> 12
    }

  /** Évalue la dignité planétaire (Domicile Taskīn / Exaltation Sharaf / Chute Hubūṭ) d'une figure dans une maison. */
  fun dignityOf(figure: GeomanticFigure, houseNumber: Int): PlanetaryDignity {
    val isOwn = figure.id == findByNumber(houseNumber).taskinRulerId
    val isSharaf = houseNumber == sharafHouseFor(figure.planet)
    val isHubut = houseNumber == hubutHouseFor(figure.planet)
    return when {
      isOwn && isSharaf -> PlanetaryDignity.OWN_AND_EXALTATION
      isSharaf -> PlanetaryDignity.EXALTATION
      isOwn -> PlanetaryDignity.OWN_HOUSE
      isHubut -> PlanetaryDignity.FALL
      else -> PlanetaryDignity.NEUTRAL
    }
  }

  /** Rôle temporel de la maison : Awtād (Présent), Succédentes (Futur), Cadentes (Passé), Zawāʾid (Synthèse). */
  fun temporalRoleFor(houseNumber: Int): HouseTemporalRole =
    when (houseNumber) {
      1, 4, 7, 10 -> HouseTemporalRole.AWTAD_PRESENT
      2, 5, 8, 11 -> HouseTemporalRole.SUCCEDENT_FUTURE
      3, 6, 9, 12 -> HouseTemporalRole.CADENT_PAST
      else -> HouseTemporalRole.ZAWAYID_SYNTHESIS
    }

  private val houseStrengthOrder = listOf(1, 10, 7, 4, 5, 11, 2, 9, 13, 15, 3, 6, 8, 12, 14, 16)

  /** Rang de puissance hiérarchique de la maison (1 = M1 très forte .. 16 = M16). */
  fun houseStrengthRank(houseNumber: Int): Int {
    val idx = houseStrengthOrder.indexOf(houseNumber)
    return if (idx >= 0) idx + 1 else 16
  }

  /** Valeur triangulaire d'Ibn Ḥabīb / Ḥāwī al-Akhbār : T(k) = k(k+1)/2 (1, 3, 6, ..., 136). */
  fun hawiTriangularValue(index: Int): Int {
    val safe = index.coerceIn(1, 16)
    return (safe * (safe + 1)) / 2
  }

  /**
   * Aspect astrologique sur le cercle des 12 premières maisons (Al-Qawl al-Jāmiʿ, p. 28) :
   * Tasdīs (2, 10), Tarbīʿ (3, 9), Tathlīth (4, 8), Muqābala (6).
   */
  fun aspectBetweenHouses(houseA: Int, houseB: Int): AspectRelationType? {
    if (houseA !in 1..12 || houseB !in 1..12 || houseA == houseB) return null
    val diff = kotlin.math.abs(houseB - houseA)
    val arc = minOf(diff, 12 - diff)
    return when (arc) {
      2 -> AspectRelationType.SEXTILE
      3 -> AspectRelationType.SQUARE
      4 -> AspectRelationType.TRINE
      6 -> AspectRelationType.OPPOSITION
      else -> null
    }
  }

  /**
   * Amitiés et inimitiés planétaires (Maḥabba wa ʿAdāwat al-Kawākib — Al-Qawl pp. 19–20, MS 2380 p. 59).
   */
  fun planetaryCompatibility(p1: Planet, p2: Planet): String {
    if (p1 == p2) return "Identité planétaire (${p1.englishName} — Mushākala)"
    val pair = setOf(p1, p2)
    val enmities =
      setOf(
        setOf(Planet.JUPITER, Planet.MARS),
        setOf(Planet.SUN, Planet.SATURN),
        setOf(Planet.VENUS, Planet.MERCURY),
        setOf(Planet.MOON, Planet.SOUTH_NODE),
      )
    if (pair in enmities) {
      return "Inimitié planétaire (${p1.englishName} ⚔ ${p2.englishName})"
    }
    val friendships =
      setOf(
        setOf(Planet.JUPITER, Planet.VENUS),
        setOf(Planet.JUPITER, Planet.MOON),
        setOf(Planet.MERCURY, Planet.SUN),
        setOf(Planet.SATURN, Planet.MARS),
      )
    if (pair in friendships) {
      return "Amitié planétaire (${p1.englishName} ↔ ${p2.englishName})"
    }
    return "Relation planétaire tempérée (${p1.englishName} / ${p2.englishName})"
  }

  /** Compatibilité élémentaire (Feu↔Air, Eau↔Terre amis ; Feu⚔Eau, Air⚔Terre contraires). */
  fun elementalCompatibility(e1: Element, e2: Element): String {
    if (e1 == e2) return "Même élément (${e1.englishName}) — accord direct"
    val pair = setOf(e1, e2)
    return when {
      pair == setOf(Element.FIRE, Element.AIR) || pair == setOf(Element.WATER, Element.EARTH) ->
        "Éléments amis (${e1.englishName} ↔ ${e2.englishName})"
      pair == setOf(Element.FIRE, Element.WATER) || pair == setOf(Element.AIR, Element.EARTH) ->
        "Éléments contraires (${e1.englishName} ⚔ ${e2.englishName})"
      else ->
        "Demi-inimitié tempérée (${e1.englishName} / ${e2.englishName})"
    }
  }

  /** Échelle temporelle d'Al-Ṭarābulusī pour les 16 maisons (MS 2380 p. 29 / MS 2405 p. 24). */
  fun tarabulusiDurationForHouse(houseNumber: Int): String =
    when (houseNumber) {
      1 -> "1 jour"
      2 -> "2 jours"
      3 -> "5 jours"
      4 -> "10 jours"
      5 -> "1 semaine"
      6 -> "3 semaines"
      7 -> "1 mois"
      8 -> "2 mois"
      9 -> "3 mois"
      10 -> "6 mois"
      11 -> "10 mois"
      12 -> "1 an"
      13 -> "3 ans"
      14 -> "6 ans"
      15 -> "10 ans"
      else -> "horizon prolongé"
    }
}
