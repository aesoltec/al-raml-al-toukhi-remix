package com.example.geomancy

/**
 * Cours fondamental : Fondements de la geomancie (Ilm al-Raml).
 * Structure pedagogique selon l'ordre demande par l'utilisateur.
 */
val fondementsCourseLessons: List<CourseLessonSection> = listOf(
  CourseLessonSection(
    lessonRef = "Fondement 1",
    heading = "Definition de la geomancie (Ilm al-Raml)",
    arabicTerms = "علم الرمل، رمل، فن التنجيم بالرمل",
    body = "La geomancie (Ilm al-Raml) est l'art de diviner par le sable. Le mot arabe raml signifie sable. C'est la science qui consiste a tracer des points dans le sable, a les reduire par paires, et a interpreter les figures qui en resultent. Le geomancien (Rammal) est celui qui pratique cet art. Il doit etre en etat de purete rituelle, silencieux, concentre, et oriente vers la Qibla. La geomancie est une science ancienne transmise de generation en generation, de maitre a disciple, selon un code ethique strict. Elle permet de repondre a toute question concernant la vie du consultant : sante, argent, mariage, voyage, vol, maladie, duel, etc.",
    keyFormulaOrTable = "Ilm al-Raml = science du sable. Rammal = geomancien. Takht = tableau de 16 maisons. Figure = combinaison de 4 lignes (Feu, Air, Eau, Terre).",
    visualAids = listOf(
      "• = point simple (impair, Khalwah)",
      "•• = point double (pair, Zawjiyyah)",
      "16 figures possibles = 2^4 = 16 combinaisons",
    ),
  ),
  CourseLessonSection(
    lessonRef = "Fondement 2",
    heading = "Definition du Taskin (Taskina / Tasakin al-Falak al-Dair)",
    arabicTerms = "تسكين الفلك الدائر، taskin، maison de repos",
    body = "Le Taskin (تسكين) est la grille de repos des 16 figures. Chaque figure a une maison fixe dans Al-Falak al-Dair (le Ciel Tournant). C'est la position naturelle et immuable de chaque figure. Quand une figure tombe dans sa maison de repos, elle est a sa place naturelle et le theme est equilibre. Le Taskin est la cle de toute interpretation : il permet de savoir si une figure est a sa place (repos) ou deplacee (migration). La grille Taskin est composee de 16 maisons (M1 a M16), chacune gouvernee par une figure specifique. Cette grille ne change jamais : c'est la carte celeste de la geomancie.",
    keyFormulaOrTable = "Taskin = grille de repos des 16 figures. M1=1121, M2=1222, M3=2111, M4=2212, M5=1111, M6=1212, M7=2122, M8=2221, M9=1122, M10=1221, M11=2112, M12=2211, M13=1112, M14=1211, M15=2121, M16=2222.",
    visualAids = listOf(
      "M8  M7  M6  M5  M4  M3  M2  M1",
      "  M12    M11      M10     M9",
      "      M14             M13",
      "            M15             M16",
      "  M12    M11      M10     M9",
      "      M14             M13",
      "            M15             M16",
    ),
  ),
  CourseLessonSection(
    lessonRef = "Fondement 3",
    heading = "Les 16 maisons au repos selon le Taskin : noms, definitions et caracteristiques",
    arabicTerms = "بيوت Taskin، maisons de repos، domains des 16 maisons",
    body = "Chaque maison du Taskin a un nom, un domaine et des caracteristiques specifiques. M1 (Ascendant/Consultant) : Ame, sante, vitalite. M2 (Foyer des Biens) : Argent, biens, subsistance (Rizq). M3 (Entourage Proche) : Freres/soeurs, voisins, deplacements courts. M4 (Fonds du Ciel) : Pere, foyer, patrimoine, racines. M5 (Maison des Enfants) : Enfants, grossesse, creations. M6 (Maison du Service) : Maladie, obstacles quotidiens. M7 (Descendant/Conjoint) : Conjoint, partenaire, adversaire. M8 (Maison de la Transformation) : Peur, perte, mort, heritage. M9 (Maison des Grands Voyages) : Longs voyages, religion, science. M10 (Milieu du Ciel/Autorite) : Travail, carriere, honneur. M11 (Maison des Espoirs) : Amis, appuis, espoirs, gains futurs. M12 (Maison des Ennemis Caches) : Ennemis caches, jalousie, blocages. M13 (Temoin Droit/Passé) : Origine de l'affaire. M14 (Temoin Gauche/Futur) : Evolution prochaine. M15 (Juge Supreme) : Verdict global definitif. M16 (Sentence Finale) : Conseil divin, consequence ultime.",
    keyFormulaOrTable = "4 Awtad (Piliers) : M1, M4, M7, M10 = Present & Force. 4 Succedentes : M2, M5, M8, M11 = Futur. 4 Cadentes : M3, M6, M9, M12 = Passe. 4 Mawazin : M12, M14, M15, M16 = Balance finale.",
  ),
  CourseLessonSection(
    lessonRef = "Fondement 4",
    heading = "Les 16 figures au repos selon le Taskin : noms, definitions et caracteristiques",
    arabicTerms = "أشكال Taskin، figures de repos، caracteristiques des 16 figures",
    body = "Chaque figure a une position de repos dans le Taskin. M1 = Djanfa Almamy (الجودلة) 1121 - Feu, Jupiter, Mobile - Acquisition, gain. M2 = Adama (اللحيان) 1222 - Air, Lune, Fixe - Peuple, groupe. M3 = Malidjou (العتبة الداخلة) 2111 - Eau, Mercure, Entrante - Jonction, union. M4 = Bayaro (البياض) 2212 - Terre, Venus, Sortante - Petite fortune. M5 = Taliki (الطريق) 1111 - Feu, Mercure, Mobile - Chemin, route. M6 = Inzan (القبض الخارج) 1212 - Air, Venus, Sortante - Perte, disparition. M7 = Lomara (الحمرة) 2122 - Eau, Mars, Sortante - Rouge, passion. M8 = Mangouchi (الإنكيس) 2221 - Terre, Noeud Nord, Fixe - Tete du dragon. M9 = Kalalaw (النصرة الخارجة) 1122 - Feu, Jupiter, Sortante - Joie, reussite. M10 = Solomana (العقلة) 1221 - Air, Mercure, Fixe - Blanc, purete. M11 = Ali (الاجتماع) 2112 - Eau, Saturne, Entrante - Prison, blocage. M12 = Nouhoun (النصرة الداخلة) 2211 - Terre, Jupiter, Entrante - Grande fortune. M13 = Lawsana (العتبة الخارجة) 1112 - Feu, Lune, Sortante - Seuil, entree. M14 = Tontigui (النقي الخد) 1211 - Air, Venus, Fixe - Jeune fille, beaute. M15 = Ousmana (القبض الداخلي) 2121 - Eau, Saturne, Entrante - Tristesse, deuil. M16 = Moussa (الجماعة) 2222 - Terre, Noeud Sud, Fixe - Queue du dragon, fin.",
    keyFormulaOrTable = "Chaque figure a une maison de repos fixe. Si une figure tombe dans sa maison de repos, elle est a sa place naturelle. Si elle est ailleurs, elle a migre (migration).",
    visualAids = listOf(
      "Taliki (الطريق) = • • • •",
      "Lawsana (العتبة الخارجة) = • • • ••",
      "Djanfa Almamy (الجودلة) = • • •• •",
      "Kalalaw (النصرة الخارجة) = • • •• ••",
      "Tontigui (النقي الخد) = • •• • •",
      "Inzan (القبض الخارج) = • •• • ••",
      "Solomana (العقلة) = • •• •• •",
      "Adama (اللحيان) = • •• •• ••",
      "Malidjou (العتبة الداخلة) = •• • • •",
      "Ali (الاجتماع) = •• • • ••",
      "Ousmana (القبض الداخلي) = •• • •• •",
      "Lomara (الحمرة) = •• • •• ••",
      "Nouhoun (النصرة الداخلة) = •• •• • •",
      "Bayaro (البياض) = •• •• • ••",
      "Mangouchi (الإنكيس) = •• •• •• •",
      "Moussa (الجماعة) = •• •• •• ••",
    ),
  ),
  CourseLessonSection(
    lessonRef = "Fondement 5",
    heading = "Definition d'un theme geomantique",
    arabicTerms = "theme geomantique، takht، tableau de 16 maisons",
    body = "Un theme geomantique (Takht) est un tableau de 16 maisons (M1 a M16) contenant chacune une figure. Le theme est genere a partir de 4 figures aleatoires (les Meres) tracees dans le sable. Les 12 autres figures sont derivees par copulation (addition binaire). Le theme represente l'etat du consultant et de sa question au moment du tirage. Le Takht est le tableau de bord de la consultation : il contient toutes les informations necessaires pour repondre a la question. Le Juge (M15) est le verdict final, et la Sentence (M16) est le conseil divin.",
    keyFormulaOrTable = "Theme = 16 maisons (M1-M16) x 1 figure. Meres (M1-M4) -> Filles (M5-M8) -> Niecess (M9-M12) -> Temoins (M13-M14) -> Juge (M15) -> Sentence (M16). M15 = M13 XOR M14 (doit etre pair). M16 = M15 XOR M1 (loi absolue).",
  ),
  CourseLessonSection(
    lessonRef = "Fondement 6",
    heading = "Interpretation globale d'un theme geomantique",
    arabicTerms = "interpretation globale، lecture du theme، methode d'interpretation",
    body = "L'interpretation globale d'un theme se fait en plusieurs etapes. 1. Lire M1 (le consultant) : qui est le consultant, son etat, son intention. 2. Lire M15 (le Juge) : le verdict final, la reponse a la question. 3. Lire M16 (la Sentence) : le conseil divin, la consequence ultime. 4. Lire M13 (le Temoin Droit/Passé) : l'origine de l'affaire. 5. Lire M14 (le Temoin Gauche/Futur) : l'evolution prochaine. 6. Lire les Awtad (M1, M4, M7, M10) : l'etat present et les fondations. 7. Lire les Succedentes (M2, M5, M8, M11) : le devenir proche. 8. Lire les Cadentes (M3, M6, M9, M12) : le passe accompli. 9. Lire les Mawazin (M12, M14, M15, M16) : la balance finale. 10. Calculer le Damir (l'intention cachee du consultant). 11. Verifier la confirmation ou l'annulation du tirage. 12. Donner le verdict et le remede.",
    keyFormulaOrTable = "Ordre de lecture : M1 -> M15 -> M16 -> M13 -> M14 -> Awtad -> Succedentes -> Cadentes -> Mawazin -> Damir -> Confirmation -> Verdict -> Remede.",
  ),
  CourseLessonSection(
    lessonRef = "Fondement 7",
    heading = "Comment faire une interpretation quelconque : methode et procedures",
    arabicTerms = "methode d'interpretation، procedure، etapes de lecture",
    body = "La methode d'interpretation depend de l'objet de la consultation. Pour chaque type de question, il y a des maisons specifiques a observer. 1. Sante : observer M1 (patient), M6 (maladie), M8 (issue critique), M15 (verdict). 2. Argent : observer M2 (argent), M10 (travail), M15 (verdict). 3. Mariage : observer M1 (consultant), M7 (conjoint), M5 (enfants/joie), M15 (verdict). 4. Voyage : observer M1 (consultant), M9 (voyage), M3 (deplacements courts), M15 (verdict). 5. Vol : observer M2 (bien), M7 (voleur), M15 (verdict). 6. Maladie : observer M1 (patient), M6 (nature du mal), M8 (issue), M15 (verdict). 7. Duel : observer Camp A (M1, M2, M10, M13) vs Camp B (M7, M8, M4, M14), M15 (arbitre). 8. Prisonnier : observer M12 (prison), M15 (verdict), M16 (conseil). La procedure generale est : identifier l'objet -> observer les maisons correspondantes -> lire les figures -> interpreter selon leur direction (Entrante/Sortante/Fixe/Mobile) -> donner le verdict.",
    keyFormulaOrTable = "Procedure : 1. Identifier l'objet -> 2. Observer les maisons -> 3. Lire les figures -> 4. Interpreter la direction -> 5. Donner le verdict -> 6. Proposer le remede.",
  ),
  CourseLessonSection(
    lessonRef = "Fondement 8",
    heading = "Interpretation des figures selon l'objet, le deplacement et la position",
    arabicTerms = "interpretation des figures، deplacement، position، maisons",
    body = "L'interpretation d'une figure depend de trois facteurs : 1. L'objet de la consultation (sante, argent, mariage, etc.). 2. Le deplacement de la figure (migration) : si la figure a migre de sa maison de repos, elle indique un changement. 3. La position de la figure : dans quelle maison elle se trouve determine son sens. Une figure seule dans une maison a un sens particulier. Deux figures identiques dans deux maisons differentes indiquent une passation (transmission). Plusieurs figures identiques dans plusieurs maisons indiquent une confirmation forte. Les figures peuvent etre : - Seules (une seule occurrence) -> sens individuel. - En double (deux occurrences) -> passation, relation. - En multiple (trois ou plus) -> confirmation forte, Mustawli. L'interpretation varie aussi selon que la figure est Entrante (favorable), Sortante (defavorable), Fixe (stagnation) ou Mobile (incertitude).",
    keyFormulaOrTable = "Figure seule -> sens individuel. Figure en double -> passation, relation. Figure en multiple (>=3) -> confirmation forte, Mustawli. Direction : Entrante = favorable, Sortante = defavorable, Fixe = stagnation, Mobile = incertitude.",
  ),
  CourseLessonSection(
    lessonRef = "Fondement 9",
    heading = "Recherche de figure en cachette (Damir) selon l'objet de la consultation",
    arabicTerms = "recherche de figure en cachette، Damir، intention cachee",
    body = "La recherche de figure en cachette (Istikhrāj al-Ḍamīr) permet de decouvrir la pensee secrète du consultant. Elle se fait selon l'objet de la consultation. 1. Pour la sante : chercher la figure de M1 dans la grille Taskin -> si elle est en M6 (maladie) ou M8 (transformation), le consultant est preoccupe par sa sante. 2. Pour l'argent : chercher la figure de M1 dans M2 (argent) ou M10 (travail) -> le consultant est preoccupe par ses finances. 3. Pour le mariage : chercher la figure de M1 dans M7 (conjoint) -> le consultant est preoccupe par son couple. 4. Pour le vol : chercher la figure de M1 dans M2 (bien) ou M7 (voleur) -> le consultant est preoccupe par un vol. 5. Pour le voyage : chercher la figure de M1 dans M9 (voyage) -> le consultant est preoccupe par un voyage. Le Damir revele ce que le consultant ne dit pas. C'est la cle de toute consultation : on lit le Damir AVANT que le consultant ait parle. Le maître doit toujours commencer par le Damir pour etablir sa credibilite et montrer qu'il lit dans les pensees.",
    keyFormulaOrTable = "Damir = Istikhrāj al-Ḍamīr. Methode : 1. Identifier M1 -> 2. Trouver sa maison de repos -> 3. Interpreter le domaine -> 4. Compter les repetitions -> 5. Calculer la Part de Fortune -> 6. Determiner l'etat emotionnel.",
  ),
)
