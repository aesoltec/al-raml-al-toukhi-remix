package com.example.geomancy

object HumanTranslationLayer {
  fun humanize(text: String): String { return text }
  fun forBeginner(technicalText: String, figureName: String = ""): String {
    return if (figureName.isNotEmpty()) {
      "La figure " + figureName + " te parle comme un ami : " + technicalText
    } else { technicalText }
  }
  fun contestPrediction(campAName: String, campBName: String, predictedWinner: String, isDraw: Boolean, scoreA: Double, scoreB: Double): String {
    return if (isDraw) {
      "Le sable revele un equilibre fascinant entre " + campAName + " et " + campBName + ". Ce match sera serre et passionnant."
    } else if (predictedWinner == "A") {
      "Le sable penche vers " + campAName + ". Une energie puissante les accompagne."
    } else {
      "Le sable penche vers " + campBName + ". Une energie puissante les accompagne."
    }
  }
  fun generalPrediction(figureName: String, arabicName: String, houseNumber: Int, houseName: String, isFavorable: Boolean): String {
    val figure = figureName + " (" + arabicName + ")"
    return if (isFavorable) {
      "Bonne nouvelle ! " + figure + " en Maison " + houseNumber + " (" + houseName + ") t apporte une energie lumineuse."
    } else {
      "Attention. " + figure + " en Maison " + houseNumber + " (" + houseName + ") t avertit d un obstacle."
    }
  }
  fun damirPrediction(dominantElement: String, unspokenFocus: String): String {
    return "Le sable revele ton intention cachee. Ton esprit est occupe par : " + unspokenFocus + ". Le sable t aide a comprendre ton coeur."
  }
  fun metrologyPrediction(type: String, value: Int, unit: String): String {
    return "Le sable indique : " + type + " = " + value + " " + unit + ". C est une indication, pas une certitude."
  }
  fun weatherPrediction(dominantElement: String, delay: Int): String {
    return when (dominantElement) {
      "EAU" -> "Le sable murmure que la pluie viendra dans " + delay + " jours."
      "AIR" -> "Le sable annonce du vent dans " + delay + " jours."
      "FEU" -> "Le sable annonce de la secheresse dans " + delay + " jours."
      "TERRE" -> "Le sable annonce de la stabilite dans " + delay + " jours."
      else -> "Le sable ne donne pas d indication claire."
    }
  }
  fun colorPrediction(element: String): String {
    return when (element) {
      "FEU" -> "Les couleurs : rouge flamboyant, orange chaud, or eclatant."
      "AIR" -> "Les couleurs : jaune lumineux, vert clair, blanc pur."
      "EAU" -> "Les couleurs : bleu profond, blanc lunaire, argent, vert d eau."
      "TERRE" -> "Les couleurs : brun chaud, noir profond, gris, ocre."
      else -> "La couleur n est pas determinee."
    }
  }
  fun odorPrediction(element: String): String {
    return when (element) {
      "FEU" -> "L odeur : acre, brulee, epicee, encens fort."
      "AIR" -> "L odeur : florale, legere, parfum discret."
      "EAU" -> "L odeur : humide, marine, fade, savon."
      "TERRE" -> "L odeur : terreux, moisi, lourd, racines."
      else -> "L odeur n est pas determinee."
    }
  }
  fun verdictMessage(judgeFigure: String, isFavorable: Boolean, isDraw: Boolean = false): String {
    return if (isDraw) {
      "Le Juge Supreme, " + judgeFigure + ", rend un verdict d equilibre. Patience et constance."
    } else if (isFavorable) {
      "Le Juge Supreme, " + judgeFigure + ", rend un verdict favorable. Le sable te benit."
    } else {
      "Le Juge Supreme, " + judgeFigure + ", rend un verdict defavorable. Prends des precautions."
    }
  }
  fun damirDeepMessage(domain: String, figureName: String, emotionalState: String): String {
    return "Le sable revele ton intention cachee. Domaine : " + domain + ". Figure : " + figureName + ". Etat : " + emotionalState + "."
  }
  fun themeSynthesis(judgeFigure: String, sentenceFigure: String, isFavorable: Boolean): String {
    return if (isFavorable) {
      "Synthese favorable. Juge (" + judgeFigure + ") et Sentence (" + sentenceFigure + ") en harmonie."
    } else {
      "Synthese defavorable. Obstacles a surmonter. Consulte ton maitre."
    }
  }
}
