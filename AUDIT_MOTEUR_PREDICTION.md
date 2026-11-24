# AUDIT DU MOTEUR DE PREDICTION
## AlToukhiGeomancyEngine.kt (~80 000 lignes)

### Date : 2026-11-24

---

## 1. COMPOSANTS AUTHENTIQUES [A] (basés sur les PDF)

### Génération des 16 maisons
- Mères → Filles → Nièces → Témoins → Juge → Sentence
- M16 = M15 ⊕ M1 (loi absolue)
- M15 doit être pair (validation)
- ✅ CONFORME aux PDF (Manbaʿ, Bulūgh, Wikipedia)

### Damir (Intention cachée)
- 4 étapes : Miroir Zanatî, Répétitions, Al-Sahm, État émotionnel
- ✅ CONFORME aux PDF (Bulūgh Bāb X)

### Taskin (Grille de repos)
- M1=1121, M2=1222, ..., M16=2222
- ✅ CONFORME aux PDF (Manbaʿ p.2, Bulūgh Bāb IV, Kassibo p.548)

### Rencontre des deux armées
- Cases impaires = demandeur, paires = demandé
- Décompte des saoud, égalité = paix, nahis sortants = défaite
- ✅ CONFORME aux PDF (Manbaʿ p.77)

### Litige
- M1 demandeur, M7 défendeur, M10 juge, M5 objet, M15 issue
- ✅ CONFORME aux PDF (Manbaʿ p.75)

### Quartiers (Awtād, Succédentes, Cadentes)
- ✅ CONFORME aux PDF (Bulūgh Bāb IV)

### Code éthique des initiés
- 6 leçons, pédagogie, déontologie, secret (dalilu/kirisiw)
- ✅ CONFORME aux PDF (Jansen 2011)

### Remèdes bambara (saraka)
- Prescriptions sacrificielles, règles de temps et d'espace
- ✅ CONFORME aux PDF (Bertaux 1984)

---

## 2. COMPOSANTS NON VÉRIFIÉS [B] (sources faibles)

### Correspondances figure ↔ planète
- Source : forums de praticiens (ramalastrology.com)
- ⚠️ NON VÉRIFIÉ — pas dans le corpus imprimé

### Correspondances figure ↔ élément
- Sources se contredisent, non collationnées page à page
- ⚠️ NON VÉRIFIÉ

### Heures planétaires (sāʿāt kawkabiyya)
- Objet classique du genre, mais pas de table figure↔heure dans le corpus
- ⚠️ NON VÉRIFIÉ

### Grilles de tasākīn (abḍaḥ, al-ḥurūf, al-kawākib, bazdaḥ, al-ḥasān)
- Source : forums de praticiens (altanjem, omeabir)
- ⚠️ NON VÉRIFIÉ — source unique

---

## 3. COMPOSANTS INVENTÉS [D] (non sourcés)

### Prédiction de score sportif
- Buts, cartons jaunes/rouges, BTTS, total de buts
- ❌ INVENTÉ — les PDF ne contiennent pas de méthode pour prédire des scores
- Le fichier canonique dit : "ni le Bulūgh, ni le Manbaʿ ne convertissent un thème en nombre de buts"

### Calcul des poids des figures (weightTable)
- Coefficients arbitraires assignés aux figures
- ❌ INVENTÉ — aucune source ne donne de poids numériques

### Prédiction de météo
- Pluie, vent, sécheresse, famine
- ❌ INVENTÉ — "aucune preuve dans le Manbaʿ" (canon §5.1)

### Calculs métrologiques
- Profondeur ×54cm, hauteur ×22cm, distance ×6km, durée
- ❌ INVENTÉ — "aucune occurrence dans le corpus" (canon §9)

### Couleurs et odeurs
- ❌ INVENTÉ — non documenté

### Extraction Abjad
- Lettre extraite, nom de lettre
- ❌ INVENTÉ — [D] pour l'application aux figures

### Points ouverts (rightSideOpenPoints vs leftSideOpenPoints)
- ❌ INVENTÉ — pure invention

### Prédiction de durée (jours/semaines/mois/années)
- ❌ INVENTÉ — non documenté

---

## 4. ACTIONS À RÉALISER

### Priorité 1 (Critique)
1. Marquer les composants [D] dans le moteur
2. Refuser d'afficher les prédictions [D] ou les marquer clairement
3. Ne garder que les règles [A] pour les prédictions principales

### Priorité 2 (Important)
4. Créer une couche de traduction humaine pour les textes
5. Unifier la terminologie (NomOuestAfricain (NomArabe))
6. Ajouter les représentations visuelles (points)

### Priorité 3 (Amélioration)
7. Rendre les textes naturels et humains
8. Ajouter des explications pour les débutants
9. Améliorer la pédagogie

---

## 5. FICHIERS À MODIFIER

### Moteur de prédiction
- `AlToukhiGeomancyEngine.kt` — marquer les composants [D]

### Cours
- `ToukhiCourseData.kt` — unifier la terminologie, ajouter les représentations visuelles
- `ToukhiManbaCourseDataPart1.kt` — idem
- `ToukhiManbaCourseDataPart2.kt` — idem
- `BahriAndQawlCourseData.kt` — idem
- `ManuscriptsCourseData.kt` — idem

### Encyclopédie
- `GeomanticFigureEncyclopedia.kt` — déjà créé, à intégrer

---

## 6. CONCLUSION

Le moteur est un mélange de 3 choses :
1. Règles authentiques [A] (~30%) : structure, génération, Damir, Taskin, rencontre, litige, quartiers
2. Données de praticiens [B] (~20%) : grilles, planètes, éléments, heures
3. Inventions [D] (~50%) : scores sportifs, météo, métrologie, cartons, BTTS, poids, durées, couleurs, odeurs

**Recommandation principale** : Marquer chaque composant avec son niveau de fiabilité et créer une couche de traduction humaine pour les textes.
