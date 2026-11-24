package com.example.geomancy

data class ThemeExample(
  val id: String,
  val title: String,
  val arabicTitle: String,
  val question: String,
  val mothers: List<String>,
  val daughters: List<String>,
  val nieces: List<String>,
  val witnesses: List<String>,
  val judge: String,
  val sentence: String,
  val interpretation: String,
  val remedy: String,
)

object ThemeExamplesData {
  val examples: List<ThemeExample> = listOf(
    ThemeExample(
      id = "theme_1_sante",
      title = "Consultation sur la sante",
      arabicTitle = "استشارة صحية",
      question = "Un consultant vient pour un probleme de sante.",
      mothers = listOf("1121", "2122", "1211", "2111"),
      daughters = listOf("1211", "1121", "2211", "1211"),
      nieces = listOf("1211", "2122", "2212", "1122"),
      witnesses = listOf("2211", "1212"),
      judge = "1121",
      sentence = "2222",
      interpretation = "M1 = Djanfa Almamy (1121, ENTRANTE) -> le consultant est pret a recevoir. M6 (maladie) = Djanfa Almamy (1121, ENTRANTE) -> guerison possible. M15 = Djanfa Almamy (1121) -> OUI ABSOLU - Victoire totale. Verdict : la sante va s'ameliorer.",
      remedy = "Sadaqa : coq blanc ou agneau blanc aux necessiteux, dimanche lever soleil, heure du Soleil, direction Est.",
    ),
    ThemeExample(
      id = "theme_2_argent",
      title = "Consultation sur l'argent",
      arabicTitle = "استشارة مالية",
      question = "Un consultant vient pour un probleme d'argent.",
      mothers = listOf("2211", "1221", "2122", "2212"),
      daughters = listOf("2112", "2222", "1222", "1122"),
      nieces = listOf("1112", "1212", "1212", "2222"),
      witnesses = listOf("2222", "1112"),
      judge = "1212",
      sentence = "2121",
      interpretation = "M2 (argent) = Solomana (1221, ENTRANTE) -> gain possible. Mais M15 = Inzan (1212, SORTANTE) -> reponse defavorable. Le consultant va avoir des difficultes financieres. Damir : M1=Nouhoun, maisonZanati=Nouhoun est en M12 -> domaine = Ennemis caches.",
      remedy = "Sadaqa : sadaqa noire, charbon, graines noires, vendredi, heure de Venus, direction Sud.",
    ),
    ThemeExample(
      id = "theme_3_mariage",
      title = "Consultation sur le mariage",
      arabicTitle = "استشارة زواج",
      question = "Un consultant vient pour un probleme de mariage.",
      mothers = listOf("1211", "1122", "2211", "1112"),
      daughters = listOf("1121", "2211", "1212", "1212"),
      nieces = listOf("2211", "1221", "2212", "2222"),
      witnesses = listOf("1112", "1112"),
      judge = "2222",
      sentence = "1111",
      interpretation = "M7 (mariage) = Inzan (1212, SORTANTE) -> blocage dans le mariage. M1 = Tontigui (1211, ENTRANTE) -> le consultant est ouvert. M15 = Moussa (2222, FIXE) -> stagnation. Verdict : situation bloquee, pas de mouvement pour le moment.",
      remedy = "Patience, Sadaqa.",
    ),
    ThemeExample(
      id = "theme_4_duel",
      title = "Consultation sur un duel sportif",
      arabicTitle = "استشارة duel",
      question = "Deux equipes s'affrontent.",
      mothers = listOf("1121", "2211", "1212", "2122"),
      daughters = listOf("1121", "2211", "1212", "1121"),
      nieces = listOf("2211", "2121", "2212", "2211"),
      witnesses = listOf("1112", "1112"),
      judge = "2222",
      sentence = "1121",
      interpretation = "M1 = Djanfa Almamy (1121, ENTRANTE) -> Camp A est plein d'energie. M7 = Inzan (1212, SORTANTE) -> Camp B est bloque. M15 = Moussa (2222, FIXE) -> match nul. Verdict : match nul, les deux camps sont a egalite.",
      remedy = "Sadaqa pour debloquer la situation.",
    ),
  )

  fun getExample(id: String): ThemeExample? = examples.find { it.id == id }
  fun getAllExamples(): List<ThemeExample> = examples
}
