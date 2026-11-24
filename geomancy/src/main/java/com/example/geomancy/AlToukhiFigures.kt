package com.example.geomancy

/**
 * Les 16 Figures Géomantiques (al-Ashkal al-Sittat Ashar) selon Cheikh al-Toukhi (Taskin al-Zanati)
 * associées à leurs appellations ouest-africaines traditionnelles (Djanfa Almami, Adama, Malidjou,
 * Bayada / Bayaro / Idrissa, Taliki, Inzan, Lomara, Mangouchi, Kalalaw, Solomana, Badra Ali,
 * Nouhoun, Lawsana, Tontigui Garya, Ousmana, Moussa), ainsi que leurs attributs spirituels.
 */
object AlToukhiFigures {

  val all: List<GeomanticFigure> =
    listOf(
      GeomanticFigure(
        id = 1,
        westAfricanOrder = 2,
        code = "1222",
        fire = PointParity.ODD,
        air = PointParity.EVEN,
        water = PointParity.EVEN,
        earth = PointParity.EVEN,
        westAfricanName = "Adama",
        arabicName = "اللحيان",
        transliteration = "Al-Laḥyān",
        englishTitle = "Laetitia — Joie, Élévation & Bénédiction",
        dominantElement = Element.FIRE,
        motion = FigureMotion.FIXED,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.JUPITER,
        zodiacSign = "Taureau / Sagittaire",
        arabicLetter = "ا",
        abjadValue = 1,
        timingSpeed = "Modérée et stable",
        generalMeaning =
          "Signifie la joie spirituelle, la noblesse, l'élévation, les bonnes nouvelles, les contrats favorables et la droiture.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Mushtarī (Jupiter) — Lumière de Miséricorde & d'Ascension",
            contemplativeDhikr = "Yā Fattāḥ, Yā Karīm",
            spiritualTemperament = "Expansion bienveillante & Sérénité intérieure",
            operativeNote =
              "Propice le jeudi à l'heure de Jupiter ; associée à la lumière safran/blanche et à l'encens pur.",
            counsel =
              "Avance avec gratitude et générosité ; la clarté vient par la rectitude plutôt que par la précipitation.",
          ),
      ),
      GeomanticFigure(
        id = 2,
        westAfricanOrder = 15,
        code = "2121",
        fire = PointParity.EVEN,
        air = PointParity.ODD,
        water = PointParity.EVEN,
        earth = PointParity.ODD,
        westAfricanName = "Ousmana",
        arabicName = "القبض الداخل",
        transliteration = "Qabḍ al-Dākhil",
        englishTitle = "Acquisitio — Le Gain qui entre & Réception",
        dominantElement = Element.AIR,
        motion = FigureMotion.ENTERING,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.JUPITER,
        zodiacSign = "Bélier / Verseau",
        arabicLetter = "ب",
        abjadValue = 2,
        timingSpeed = "Accumulation régulière",
        generalMeaning =
          "Annonce l'acquisition, la conservation des biens, la réception de ce qui est espéré et une croissance durable.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Qabḍ al-Mubārak — Rassemblement & Préservation",
            contemplativeDhikr = "Yā Razzāq, Yā Mughnī",
            spiritualTemperament = "Abondance réceptive & Sagesse de gestion",
            operativeNote =
              "Très favorable pour les accords, l'épargne et la recherche de subsistance à l'heure de Jupiter ou du Soleil.",
            counsel =
              "Accueille ce qui arrive sans avidité et protège tes acquis par une discipline constante.",
          ),
      ),
      GeomanticFigure(
        id = 3,
        westAfricanOrder = 6,
        code = "1212",
        fire = PointParity.ODD,
        air = PointParity.EVEN,
        water = PointParity.ODD,
        earth = PointParity.EVEN,
        westAfricanName = "Inzan",
        arabicName = "القبض الخارج",
        transliteration = "Qabḍ al-Khārij",
        englishTitle = "Amissio — La Perte, Dépense & Libération",
        dominantElement = Element.WATER,
        motion = FigureMotion.EXITING,
        polarity = FortunePolarity.UNFORTUNATE,
        planet = Planet.VENUS,
        zodiacSign = "Balance / Taureau",
        arabicLetter = "ج",
        abjadValue = 3,
        timingSpeed = "Dispersion rapide",
        generalMeaning =
          "Indique une dépense, une perte matérielle, un éloignement ou une occasion qui s'échappe — mais libère d'un fardeau.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Zuhara — Détachement & Purification",
            contemplativeDhikr = "Yā Ghanī, Yā Ḥafīẓ",
            spiritualTemperament = "Lâcher-prise & Dépouillement volontaire",
            operativeNote =
              "Évite les engagements financiers risqués sous cette figure ; al-Toukhi conseille l'aumône (ṣadaqa) pour transmuter la perte.",
            counsel =
              "Laisse partir ce qui n'est plus utile ; le don volontaire transforme une sortie subie en protection.",
          ),
      ),
      GeomanticFigure(
        id = 4,
        westAfricanOrder = 16,
        code = "2222",
        fire = PointParity.EVEN,
        air = PointParity.EVEN,
        water = PointParity.EVEN,
        earth = PointParity.EVEN,
        westAfricanName = "Moussa",
        arabicName = "الجماعة",
        transliteration = "Al-Jamāʿa",
        englishTitle = "Populus — L'Assemblée & la Foule",
        dominantElement = Element.WATER,
        motion = FigureMotion.FIXED,
        polarity = FortunePolarity.MIXED,
        planet = Planet.MOON,
        zodiacSign = "Cancer",
        arabicLetter = "د",
        abjadValue = 4,
        timingSpeed = "Réflexive / selon l'entourage",
        generalMeaning =
          "Représente les assemblées, l'opinion publique, les réunions familiales et reflète les figures qui l'entourent.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Qamar — Miroir collectif & Consensus",
            contemplativeDhikr = "Yā Jāmiʿ, Yā Wadūd",
            spiritualTemperament = "Résonance communautaire & Miroir neutre",
            operativeNote =
              "Prend la couleur de ses voisines (Mujāwara) ; plus forte le lundi durant la lune croissante.",
            counsel =
              "Observe ton entourage et tes alliés : le milieu environnant amplifie actuellement ton intention.",
          ),
      ),
      GeomanticFigure(
        id = 5,
        westAfricanOrder = 13,
        code = "1112",
        fire = PointParity.ODD,
        air = PointParity.ODD,
        water = PointParity.ODD,
        earth = PointParity.EVEN,
        westAfricanName = "Lawsana",
        arabicName = "العتبة الخارجة (راية فرحة)",
        transliteration = "Al-ʿAtaba al-Khārija",
        englishTitle = "Cauda Draconis — Le Seuil Sortant",
        dominantElement = Element.FIRE,
        motion = FigureMotion.EXITING,
        polarity = FortunePolarity.UNFORTUNATE,
        planet = Planet.SOUTH_NODE,
        zodiacSign = "Sagittaire (Nœud descendant)",
        arabicLetter = "هـ",
        abjadValue = 5,
        timingSpeed = "Départ brusque",
        generalMeaning =
          "Signale une fin, une sortie d'un lieu ou d'une situation, une rupture nette ou un faux départ pour un projet neuf.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Dhanab — Coupure & Passage du seuil",
            contemplativeDhikr = "Yā Bāsiṭ, Yā Hādī",
            spiritualTemperament = "Clôture & Désengagement",
            operativeNote =
              "Al-Toukhi la juge favorable uniquement pour sortir d'une dette, d'une maladie ou d'un blocage ; défavorable pour démarrer une union.",
            counsel =
              "Ne force pas une porte qui se referme ; quitte proprement ce qui a achevé son cycle.",
          ),
      ),
      GeomanticFigure(
        id = 6,
        westAfricanOrder = 10,
        code = "1221",
        fire = PointParity.ODD,
        air = PointParity.EVEN,
        water = PointParity.EVEN,
        earth = PointParity.ODD,
        westAfricanName = "Solomana",
        arabicName = "العقلة",
        transliteration = "Al-ʿUqla",
        englishTitle = "Carcer — Le Lien, Retenue & Sanctuaire",
        dominantElement = Element.EARTH,
        motion = FigureMotion.FIXED,
        polarity = FortunePolarity.UNFORTUNATE,
        planet = Planet.SATURN,
        zodiacSign = "Capricorne / Poissons",
        arabicLetter = "و",
        abjadValue = 6,
        timingSpeed = "Lente / Retardée",
        generalMeaning =
          "Indique une contrainte, un retard, une attente, la solitude ou le secret — mais protège ce qui est gardé.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat Zuḥal — Retraite contemplative & Contenance",
            contemplativeDhikr = "Yā Ṣabūr, Yā Wakīl",
            spiritualTemperament = "Protection hermétique & Endurance",
            operativeNote =
              "Liée au samedi ; peu propice aux voyages rapides, mais protectrice pour garder un secret ou une retraite (khalwa).",
            counsel =
              "Accepte le délai comme une enceinte protectrice ; la patience l'emporte sur l'action hâtive.",
          ),
      ),
      GeomanticFigure(
        id = 7,
        westAfricanOrder = 8,
        code = "2221",
        fire = PointParity.EVEN,
        air = PointParity.EVEN,
        water = PointParity.EVEN,
        earth = PointParity.ODD,
        westAfricanName = "Mangouchi",
        arabicName = "الإنكيس",
        transliteration = "Al-Inkis",
        englishTitle = "Tristitia — Le Renversement & Profondeur",
        dominantElement = Element.EARTH,
        motion = FigureMotion.FIXED,
        polarity = FortunePolarity.UNFORTUNATE,
        planet = Planet.SATURN,
        zodiacSign = "Verseau / Scorpion",
        arabicLetter = "ز",
        abjadValue = 7,
        timingSpeed = "Lourde et prolongée",
        generalMeaning =
          "Signifie la pesanteur, la contrariété, un repli ou des choses enfouies ; propice pour découvrir des secrets cachés.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Inkis — Humilité & Découverte du caché",
            contemplativeDhikr = "Yā Laṭīf, Yā Mufarrij al-Kurūb",
            spiritualTemperament = "Introspection sobre & Pénitence",
            operativeNote =
              "Al-Toukhi précise que si les entreprises mondaines ralentissent sous Mangouchi, la recherche profonde et le repentir réussissent.",
            counsel =
              "Reste humble, regarde sous l'apparence immédiate et transforme la lourdeur en discernement.",
          ),
      ),
      GeomanticFigure(
        id = 8,
        westAfricanOrder = 7,
        code = "2122",
        fire = PointParity.EVEN,
        air = PointParity.ODD,
        water = PointParity.EVEN,
        earth = PointParity.EVEN,
        westAfricanName = "Lomara",
        arabicName = "الحمرة",
        transliteration = "Al-Ḥumra",
        englishTitle = "Rubeus — La Flamme Rouge & Ardeur",
        dominantElement = Element.FIRE,
        motion = FigureMotion.MOBILE,
        polarity = FortunePolarity.UNFORTUNATE,
        planet = Planet.MARS,
        zodiacSign = "Scorpion / Bélier",
        arabicLetter = "ح",
        abjadValue = 8,
        timingSpeed = "Brusque et brûlante",
        generalMeaning =
          "Évoque la passion, le feu, le conflit, la colère, l'adversité vive ou la rupture brutale ; met en garde contre l'impulsivité.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Mirrīkh — Zèle ardent & Transmutation",
            contemplativeDhikr = "Yā Ḥalīm, Yā Salām",
            spiritualTemperament = "Impulsion volcanique à maîtriser",
            operativeNote =
              "Al-Toukhi recommande le calme et la maîtrise le mardi, afin d'éviter disputes et paroles irréparables.",
            counsel =
              "Refroidis la colère avant d'agir ; canalise l'ardeur en courage mesuré et jamais en emportement.",
          ),
      ),
      GeomanticFigure(
        id = 9,
        westAfricanOrder = 4,
        code = "2212",
        fire = PointParity.EVEN,
        air = PointParity.EVEN,
        water = PointParity.ODD,
        earth = PointParity.EVEN,
        westAfricanName = "Bayada / Bayaro / Idrissa",
        arabicName = "البياض",
        transliteration = "Al-Bayāḍ",
        englishTitle = "Albus — La Blancheur Pure & Clarté",
        dominantElement = Element.WATER,
        motion = FigureMotion.FIXED,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.MERCURY,
        zodiacSign = "Gémeaux / Cancer",
        arabicLetter = "ط",
        abjadValue = 9,
        timingSpeed = "Calme et mesurée",
        generalMeaning =
          "Symbolise la pureté, la sagesse, la paix, la conscience claire, la vérité et les bons conseils.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat ʿUṭārid al-Ṣāfī — Intellect lumineux & Pureté",
            contemplativeDhikr = "Yā Nūr, Yā ʿAlīm",
            spiritualTemperament = "Lucidité, Paix & Clarté dévotionnelle",
            operativeNote =
              "Propice le mercredi pour l'étude, la rédaction, la réconciliation et la recherche de vérité spirituelle.",
            counsel =
              "Garde tes intentions limpides et écoute les avis sages ; la vérité paisible résout la complexité.",
          ),
      ),
      GeomanticFigure(
        id = 10,
        westAfricanOrder = 9,
        code = "1122",
        fire = PointParity.ODD,
        air = PointParity.ODD,
        water = PointParity.EVEN,
        earth = PointParity.EVEN,
        westAfricanName = "Kalalaw",
        arabicName = "النصرة الخارجة",
        transliteration = "Al-Nuṣra al-Khārija",
        englishTitle = "Fortuna Minor — La Victoire Extérieure",
        dominantElement = Element.FIRE,
        motion = FigureMotion.EXITING,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.SUN,
        zodiacSign = "Lion",
        arabicLetter = "ي",
        abjadValue = 10,
        timingSpeed = "Rapide mais passagère",
        generalMeaning =
          "Promet un secours rapide venant de l'extérieur et une percée soudaine, mais exige de la constance pour durer.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Shams — Secours rayonnant & Percée rapide",
            contemplativeDhikr = "Yā Nāṣir, Yā ʿAzīz",
            spiritualTemperament = "Éclair solaire & Appui extérieur",
            operativeNote =
              "Agis le dimanche à l'heure du Soleil ; signale une aide obtenue par un appui ou une ouverture soudaine.",
            counsel =
              "Saisis l'ouverture tant qu'elle se présente et consolide un succès rapide par l'humilité.",
          ),
      ),
      GeomanticFigure(
        id = 11,
        westAfricanOrder = 12,
        code = "2211",
        fire = PointParity.EVEN,
        air = PointParity.EVEN,
        water = PointParity.ODD,
        earth = PointParity.ODD,
        westAfricanName = "Nouhoun",
        arabicName = "النصرة الداخلة",
        transliteration = "Al-Nuṣra al-Dākhila",
        englishTitle = "Fortuna Major — La Grande Victoire Intérieure",
        dominantElement = Element.EARTH,
        motion = FigureMotion.ENTERING,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.SUN,
        zodiacSign = "Lion / Verseau",
        arabicLetter = "ك",
        abjadValue = 20,
        timingSpeed = "Durable et souveraine",
        generalMeaning =
          "L'une des plus grandes fortunes du système d'al-Toukhi : triomphe durable, force intérieure, dignité et réussite solide.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Naṣr al-Akbar — Grâce souveraine & Stabilité",
            contemplativeDhikr = "Yā Qawī, Yā Mālik al-Mulk",
            spiritualTemperament = "Noblesse enracinée & Victoire assurée",
            operativeNote =
              "Puissamment faste en Maisons 1, 10, 15 ou 16 ; stabilise même un entourage agité.",
            counsel =
              "Tiens ferme dans la dignité et la vision longue ; la victoire durable naît de la constance.",
          ),
      ),
      GeomanticFigure(
        id = 12,
        westAfricanOrder = 3,
        code = "2111",
        fire = PointParity.EVEN,
        air = PointParity.ODD,
        water = PointParity.ODD,
        earth = PointParity.ODD,
        westAfricanName = "Malidjou",
        arabicName = "العتبة الداخلة",
        transliteration = "Al-ʿAtaba al-Dākhila",
        englishTitle = "Caput Draconis — Le Seuil Entrant",
        dominantElement = Element.EARTH,
        motion = FigureMotion.ENTERING,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.NORTH_NODE,
        zodiacSign = "Vierge (Nœud ascendant)",
        arabicLetter = "ل",
        abjadValue = 30,
        timingSpeed = "Ouverture progressive",
        generalMeaning =
          "Marque les commencements bénis, l'entrée dans un nouveau lieu ou rang, l'élévation et les portes qui s'ouvrent.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Raʾs — Porte sacrée & Initiation",
            contemplativeDhikr = "Yā Fattāḥ, Yā Muqaddim",
            spiritualTemperament = "Seuil ascendant & Grâce inaugurale",
            operativeNote =
              "Idéale pour entamer une étude, signer un accord ou inaugurer une discipline spirituelle.",
            counsel =
              "Franchis le seuil avec une intention claire ; les graines plantées maintenant portent un élan durable.",
          ),
      ),
      GeomanticFigure(
        id = 13,
        westAfricanOrder = 14,
        code = "1211",
        fire = PointParity.ODD,
        air = PointParity.EVEN,
        water = PointParity.ODD,
        earth = PointParity.ODD,
        westAfricanName = "Tontigui Garya",
        arabicName = "النقي الخد",
        transliteration = "Al-Naqī al-Khadd",
        englishTitle = "Puella — Grâce, Douceur & Harmonie",
        dominantElement = Element.AIR,
        motion = FigureMotion.MOBILE,
        polarity = FortunePolarity.FORTUNATE,
        planet = Planet.VENUS,
        zodiacSign = "Balance",
        arabicLetter = "م",
        abjadValue = 40,
        timingSpeed = "Douce et harmonieuse",
        generalMeaning =
          "Signifie la grâce, la beauté, la réconciliation, l'affection sincère et le dénouement paisible sans contrainte.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Zuhara al-Laṭīfa — Miséricorde, Beauté & Concorde",
            contemplativeDhikr = "Yā Laṭīf, Yā Jamīl",
            spiritualTemperament = "Affection raffinée & Esprit de paix",
            operativeNote =
              "Propice le vendredi pour la réconciliation, le mariage, les accords et les arts.",
            counsel =
              "Privilégie la douceur et la diplomatie ; la courtoisie accomplit ce que la force ne peut obtenir.",
          ),
      ),
      GeomanticFigure(
        id = 14,
        westAfricanOrder = 1,
        code = "1121",
        fire = PointParity.ODD,
        air = PointParity.ODD,
        water = PointParity.EVEN,
        earth = PointParity.ODD,
        westAfricanName = "Djanfa Almami",
        arabicName = "الجودلة (الكوسج)",
        transliteration = "Al-Jūdla (Al-Kawsaj)",
        englishTitle = "Puer — Élan, Audace & Ruse",
        dominantElement = Element.AIR,
        motion = FigureMotion.MOBILE,
        polarity = FortunePolarity.MIXED,
        planet = Planet.MARS,
        zodiacSign = "Bélier",
        arabicLetter = "ن",
        abjadValue = 50,
        timingSpeed = "Très rapide et directe",
        generalMeaning =
          "Dénote l'énergie vive, l'initiative audacieuse, la compétition ou la ruse ; favorable pour agir, risquée pour la diplomatie délicate.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-ʿAzm — Résolution active & Tranchant",
            contemplativeDhikr = "Yā Qawiyy, Yā Matīn",
            spiritualTemperament = "Potentiel fougueux & Élan vers l'avant",
            operativeNote =
              "Puissante pour l'action décisive et la protection ; demande de tempérer l'impulsivité par la prévoyance.",
            counsel =
              "Dirige ton énergie vers un seul objectif digne au lieu de la disperser en querelles.",
          ),
      ),
      GeomanticFigure(
        id = 15,
        westAfricanOrder = 11,
        code = "2112",
        fire = PointParity.EVEN,
        air = PointParity.ODD,
        water = PointParity.ODD,
        earth = PointParity.EVEN,
        westAfricanName = "Badra Ali",
        arabicName = "الاجتماع",
        transliteration = "Al-Ijtimāʿ",
        englishTitle = "Conjunctio — L'Union, Retrouvailles & Espoirs",
        dominantElement = Element.AIR,
        motion = FigureMotion.ENTERING,
        polarity = FortunePolarity.MIXED,
        planet = Planet.MERCURY,
        zodiacSign = "Vierge",
        arabicLetter = "س",
        abjadValue = 60,
        timingSpeed = "Convergence rapide",
        generalMeaning =
          "Signifie la réunion, le recouvrement de ce qui était égaré, l'accord, les nouvelles et la synthèse des fils épars.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat ʿUṭārid — Synthèse, Lettres & Réunion",
            contemplativeDhikr = "Yā Jāmiʿ, Yā Ḥakīm",
            spiritualTemperament = "Création de liens & Alignement intellectuel",
            operativeNote =
              "Gouverne naturellement la Maison 15 (Al-Mīzān / Le Juge) dans le Taskīn al-Zanātī ; excelle à réconcilier les contraires.",
            counsel =
              "Rassemble ce qui était séparé par un dialogue clair ; ce qui semblait perdu peut être retrouvé.",
          ),
      ),
      GeomanticFigure(
        id = 16,
        westAfricanOrder = 5,
        code = "1111",
        fire = PointParity.ODD,
        air = PointParity.ODD,
        water = PointParity.ODD,
        earth = PointParity.ODD,
        westAfricanName = "Taliki",
        arabicName = "الطريق",
        transliteration = "Al-Ṭarīq",
        englishTitle = "Via — La Voie Ouverte & Cheminement",
        dominantElement = Element.WATER,
        motion = FigureMotion.MOBILE,
        polarity = FortunePolarity.MIXED,
        planet = Planet.MOON,
        zodiacSign = "Cancer / Voie mobile",
        arabicLetter = "ع",
        abjadValue = 70,
        timingSpeed = "Mouvement immédiat",
        generalMeaning =
          "Représente la route, les voyages, les transitions, l'eau vive et un chemin dégagé dont la qualité reflète les maisons voisines.",
        spiritualProfile =
          SpiritualProfile(
            ruhaniyyaCorrespondence = "Rūḥāniyyat al-Ṭarīq — La Voie spirituelle (Sulūk)",
            contemplativeDhikr = "Yā Hādī, Yā Fattāḥ",
            spiritualTemperament = "Flux ininterrompu & Pèlerinage",
            operativeNote =
              "Gouverne naturellement la Maison 16 (ʿĀqibat al-ʿĀqiba) ; signale mouvement, passage et cheminement continu.",
            counsel =
              "Avance avec foi et souplesse ; l'immobilité présente n'est qu'une préparation à la route.",
          ),
      ),
    )

  /** Liste triée selon la séquence ouest-africaine traditionnelle (1..16). */
  val allInWestAfricanOrder: List<GeomanticFigure> = all.sortedBy { it.westAfricanOrder }

  private val byCode: Map<String, GeomanticFigure> = all.associateBy { it.code }
  private val byId: Map<Int, GeomanticFigure> = all.associateBy { it.id }

  fun findById(id: Int): GeomanticFigure =
    byId[id] ?: error("Identifiant de figure Taskin inconnu : $id")

  fun findByRanks(
    fire: PointParity,
    air: PointParity,
    water: PointParity,
    earth: PointParity,
  ): GeomanticFigure {
    val code = "${fire.dots}${air.dots}${water.dots}${earth.dots}"
    return byCode[code] ?: error("Combinaison géomantique invalide : $code")
  }

  fun combine(first: GeomanticFigure, second: GeomanticFigure): GeomanticFigure =
    findByRanks(
      fire = first.fire + second.fire,
      air = first.air + second.air,
      water = first.water + second.water,
      earth = first.earth + second.earth,
    )

  /**
   * Calcule les 4 balances relationnelles du traité « Ḥāwī al-Akhbār » (pp. 112–113) ainsi que
   * l'opposé complet (Al-Ḍidd, p. 144) et la symétrie miroir (Aṣḥāb, p. 148) :
   * - Naẓar (Regard / Intention) = figure ⊕ Al-Laḥyān (1222, id 1) -> inverse le rang de Feu
   * - Nuṭq (Parole / Signal) = figure ⊕ Al-Ḥumra (2122, id 8) -> inverse le rang d'Air
   * - Ittiṣāl (Jonction / Accord) = figure ⊕ Al-Bayāḍ (2212, id 9) -> inverse le rang d'Eau
   * - Infiṣāl (Séparation / Blocage) = figure ⊕ Al-Inkis (2221, id 7) -> inverse le rang de Terre
   * - Ḍidd (Opposé total) = figure ⊕ Al-Ṭarīq (1111, id 16) -> inverse tous les 4 rangs
   * - Miroir vertical (Aṣḥāb) = renverse l'ordre des rangs (Terre, Eau, Air, Feu).
   */
  fun relationsFor(figure: GeomanticFigure): FigureRelations =
    FigureRelations(
      source = figure,
      nazar = combine(figure, findById(1)),
      nutq = combine(figure, findById(8)),
      ittisal = combine(figure, findById(9)),
      infisal = combine(figure, findById(7)),
      opposite = combine(figure, findById(16)),
      verticalMirror =
        findByRanks(
          fire = figure.earth,
          air = figure.water,
          water = figure.air,
          earth = figure.fire,
        ),
    )

  /**
   * Hiérarchie quaternaire des 16 figures selon « Ḥāwī al-Akhbār » (p. 155) :
   * - Fortes fastes : 1 (Adama), 2 (Ousmana), 11 (Nouhoun), 12 (Malidjou)
   * - Mixtes fastes : 9 (Bayada), 10 (Kalalaw), 13 (Tontigui), 14 (Djanfa)
   * - Mixtes défavorables : 4 (Moussa), 6 (Solomana), 15 (Badra Ali), 16 (Taliki)
   * - Fortes défavorables : 3 (Inzan), 5 (Lawsana), 7 (Mangouchi), 8 (Lomara).
   */
  fun hawiStrengthTier(figure: GeomanticFigure): HawiStrengthTier =
    when (figure.id) {
      1, 2, 11, 12 -> HawiStrengthTier.STRONG_FORTUNE
      9, 10, 13, 14 -> HawiStrengthTier.MIXED_FORTUNE
      4, 6, 15, 16 -> HawiStrengthTier.MIXED_MISFORTUNE
      else -> HawiStrengthTier.STRONG_MISFORTUNE
    }
}
