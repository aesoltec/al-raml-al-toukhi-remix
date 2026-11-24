import re
path = "geomancy/src/main/java/com/example/geomancy/AlToukhiGeomancyEngine.kt"
content = open(path, encoding='utf-8').read()

replacements = [
    ('interpretation = querentMigrationText,', 'interpretation = HumanTranslationLayer.humanize(querentMigrationText),'),
    ('interpretation = domainMigrationText,', 'interpretation = HumanTranslationLayer.humanize(domainMigrationText),'),
    ('status = flankingStatus,', 'status = HumanTranslationLayer.humanize(flankingStatus),'),
    ('planetaryAlignmentNote = planetaryAlignmentNote,', 'planetaryAlignmentNote = HumanTranslationLayer.humanize(planetaryAlignmentNote),'),
    ('humoralDiagnosis = humoralDiagnosis,', 'humoralDiagnosis = HumanTranslationLayer.humanize(humoralDiagnosis),'),
    ('spiritualPrescription = spiritualPrescription,', 'spiritualPrescription = HumanTranslationLayer.humanize(spiritualPrescription),'),
]

for old, new in replacements:
    if old in content:
        content = content.replace(old, new, 1)
        print(f"OK: {old}")
    else:
        print(f"NOT FOUND: {old}")

# Also wrap contest summaryText
old_ct = 'summaryText = "Al-Ghālib wa-l-Maghlūb$calibratedTag : $edgeText",'
new_ct = 'summaryText = HumanTranslationLayer.humanize("Al-Ghālib wa-l-Maghlūb$calibratedTag : $edgeText"),'
if old_ct in content:
    content = content.replace(old_ct, new_ct, 1)
    print("OK: summaryText")
else:
    print("NOT FOUND: summaryText")

open(path, 'w', encoding='utf-8').write(content)
print("Done")
