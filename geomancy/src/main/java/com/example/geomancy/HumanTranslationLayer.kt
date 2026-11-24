package com.example.geomancy

/**
 * Couche de traduction humaine pour les textes du moteur de géomancie.
 * Transforme les textes techniques et robotiques en textes naturels et pédagogiques.
 * Considère l'utilisateur comme un débutant (enfant de maternelle).
 */
object HumanTranslationLayer {

  /**
   * Transforme un texte technique en texte naturel et humain.
   * Ajoute des explications pour les débutants et unifie la terminologie.
   */
  fun humanize(text: String): String {
    var result = text
    // Unifier la terminologie : ajouter le nom ouest-africain à côté du nom arabe
    result = result.replace("Adama (Al-Laḥyān)", "Adama (اللحيان)")
    result = result.replace("Ousmana (Qabḍ al-Dākhil)", "Ousmana (القبض الداخلي)")
    result = result.replace("Inzan (Qabḍ al-Khārij)", "Inzan (القبض الخارجي)")
    result = result.replace("Moussa (Al-Jamāʿa)", "Moussa (الجماعة)")
    result = result.replace("Lawsana (Al-ʿAtaba al-Khārija)", "Lawsana (العتبة الخارجة)")
    result = result.replace("Solomana (Al-ʿUqla)", "Solomana (العقلة)")
    result = result.replace("Mangouchi (Al-Inkis)", "Mangouchi (الإنكيس)")
    result = result.replace("Lomara (Al-Ḥumra)", "Lomara (الحمرة)")
    result = result.replace("Bayaro (Al-Bayāḍ)", "Bayaro (البياض)")
    result = result.replace("Kalalaw (Al-Nuṣra al-Khārija)", "Kalalaw (النصرة الخارجة)")
    result = result.replace("Nouhoun (Al-Nuṣra al-Dākhila)", "Nouhoun (النصرة الداخلة)")
    result = result.replace("Malidjou (Al-ʿAtaba al-Dākhila)", "Malidjou (العتبة الداخلة)")
    result = result.replace("Tontigui Garya (Al-Naqī al-Khadd)", "Tontigui (النقي الخد)")
    result = result.replace("Djanfa Almami (Al-Jūdla)", "Djanfa Almamy (الجودلة)")
    result = result.replace("Badra Ali (Al-Ijtimāʿ)", "Badra Ali (الاجتماع)")
    result = result.replace("Taliki (Al-Ṭarīq)", "Taliki (الطريق)")
    return result
  }

  /**
   * Transforme un texte technique en texte naturel pour un débutant.
   */
  fun forBeginner(technicalText: String, figureName: String = ""): String {
    val humanized = humanize(technicalText)
    return if (figureName.isNotEmpty()) {
      "La figure $figureName ($humanized) t'indique que..."
    } else {
      humanized
    }
  }

  /**
   * Génère un message naturel pour une prédiction de duel.
   */
  fun contestPrediction(
    campAName: String,
    campBName: String,
    predictedWinner: String,
    isDraw: Boolean,
    scoreA: Double,
    scoreB: Double,
  ): String {
    return if (isDraw) {
      "Le sable montre un équilibre entre $campAName et $campBName. " +
        "Les deux camps sont à égalité. C'est un match serré où la patience et la constance feront la différence."
    } else if (predictedWinner == "A") {
      "Le sable favorise $campAName. " +
        "Les figures montrent une énergie entrante et favorable pour le camp de $campAName. " +
        "Cependant, la victoire n'est pas garantie — elle dépend de la constance et de la préparation."
    } else {
      "Le sable favorise $campBName. " +
        "Les figures montrent une énergie entrante et favorable pour le camp de $campBName. " +
        "Cependant, la victoire n'est pas garantie — elle dépend de la constance et de la préparation."
    }
  }

  /**
   * Génère un message naturel pour une prédiction générale.
   */
  fun generalPrediction(
    figureName: String,
    arabicName: String,
    houseNumber: Int,
    houseName: String,
    isFavorable: Boolean,
  ): String {
    val figure = "$figureName ($arabicName)"
    return if (isFavorable) {
      "La figure $figure en Maison $houseNumber ($houseName) est favorable. " +
        "Elle t'apporte de bonnes nouvelles et une énergie positive pour ce domaine de ta vie."
    } else {
      "La figure $figure en Maison $houseNumber ($houseName) est défavorable. " +
        "Elle t'avertit d'un obstacle ou d'une difficulté dans ce domaine. " +
        "Prends des précautions et consulte ton maître pour un remède."
    }
  }

  /**
   * Génère un message naturel pour le Damir (intention cachée).
   */
  fun damirPrediction(
    dominantElement: String,
    unspokenFocus: String,
  ): String {
    return "Le sable révèle ton intention cachée. " +
      "Ton esprit est occupé par : $unspokenFocus. " +
      "C'est normal de ressentir cela. Le sable t'aide à comprendre ce que ton cœur désire vraiment."
  }

  /**
   * Génère un message naturel pour la métrologie.
   */
  fun metrologyPrediction(
    type: String,
    value: Int,
    unit: String,
  ): String {
    return "Selon le sable, $type est de $value $unit. " +
      "Cette estimation est basée sur la figure qui gouverne cette maison. " +
      "C'est une indication, pas une certitude absolue."
  }

  /**
   * Génère un message naturel pour la météo.
   */
  fun weatherPrediction(
    dominantElement: String,
    delay: Int,
  ): String {
    return when (dominantElement) {
      "EAU" -> "Le sable annonce de la pluie dans environ $delay jours. Prépare-toi en conséquence."
      "AIR" -> "Le sable annonce du vent et des changements de temps dans environ $delay jours."
      "FEU" -> "Le sable annonce de la sécheresse et de la chaleur dans environ $delay jours."
      "TERRE" -> "Le sable annonce de la stabilité et du froid dans environ $delay jours."
      else -> "Le sable ne donne pas d'indication claire sur la météo."
    }
  }

  /**
   * Génère un message naturel pour les couleurs.
   */
  fun colorPrediction(element: String): String {
    return when (element) {
      "FEU" -> "La couleur associée est le rouge, l'orange ou l'or."
      "AIR" -> "La couleur associée est le jaune, le vert clair ou le blanc lumineux."
      "EAU" -> "La couleur associée est le bleu, le blanc lunaire, l'argent ou le vert d'eau."
      "TERRE" -> "La couleur associée est le brun, le noir, le gris ou l'ocre."
      else -> "La couleur n'est pas déterminée."
    }
  }

  /**
   * Génère un message naturel pour les odeurs.
   */
  fun odorPrediction(element: String): String {
    return when (element) {
      "FEU" -> "L'odeur associée est âcre, brûlée, épicée ou encens fort."
      "AIR" -> "L'odeur associée est florale, légère ou parfum discret."
      "EAU" -> "L'odeur associée est humide, marine, fade ou savon."
      "TERRE" -> "L'odeur associée est terreux, moisi, lourd ou racines."
      else -> "L'odeur n'est pas déterminée."
    }
  }
}
