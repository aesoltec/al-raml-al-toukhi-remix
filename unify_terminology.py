# -*- coding: utf-8 -*-
"""
Script pour unifier la terminologie dans les fichiers de cours.
Remplace les noms arabes seuls par "NomOuestAfricain (NomArabe)".
"""
import re
import os

# Mapping: Arabic name -> Unified name (WestAfrican (Arabic))
# Only replace when the Arabic name appears alone (not already in unified format)
mapping = {
    "Al-Laḥyān": "Adama (Al-Laḥyān)",
    "Qabḍ al-Dākhil": "Ousmana (Qabḍ al-Dākhil)",
    "Qabḍ al-Khārij": "Inzan (Qabḍ al-Khārij)",
    "Al-Jamāʿa": "Moussa (Al-Jamāʿa)",
    "Al-ʿAtaba al-Khārija": "Lawsana (Al-ʿAtaba al-Khārija)",
    "Al-ʿUqla": "Solomana (Al-ʿUqla)",
    "Al-Inkis": "Mangouchi (Al-Inkis)",
    "Al-Ḥumra": "Lomara (Al-Ḥumra)",
    "Al-Bayāḍ": "Bayaro (Al-Bayāḍ)",
    "Al-Nuṣra al-Khārija": "Kalalaw (Al-Nuṣra al-Khārija)",
    "Al-Nuṣra al-Dākhila": "Nouhoun (Al-Nuṣra al-Dākhila)",
    "Al-ʿAtaba al-Dākhila": "Malidjou (Al-ʿAtaba al-Dākhila)",
    "Al-Naqī al-Khadd": "Tontigui Garya (Al-Naqī al-Khadd)",
    "Al-Jūdla": "Djanfa Almami (Al-Jūdla)",
    "Al-Ijtimāʿ": "Badra Ali (Al-Ijtimāʿ)",
    "Al-Ṭarīq": "Taliki (Al-Ṭarīq)",
}

course_files = [
    "ToukhiCourseData.kt",
    "ToukhiManbaCourseDataPart1.kt",
    "ToukhiManbaCourseDataPart2.kt",
    "BahriAndQawlCourseData.kt",
    "ManuscriptsCourseData.kt",
]

base_path = "geomancy/src/main/java/com/example/geomancy"

for filename in course_files:
    filepath = os.path.join(base_path, filename)
    if not os.path.exists(filepath):
        print(f"File not found: {filepath}")
        continue
    
    content = open(filepath, encoding='utf-8').read()
    original = content
    
    # Sort by length (longest first) to avoid partial replacements
    for arabic, unified in sorted(mapping.items(), key=lambda x: len(x[0]), reverse=True):
        # Simple approach: replace Arabic name only when not already followed by closing paren
        # and not preceded by a letter+space+open paren
        # We'll just do simple replacement and then fix double-unified names
        content = content.replace(arabic, unified)
    
    # Fix double-unified names (e.g., "Adama (Adama (اللحيان))")
    for arabic, unified in mapping.items():
        double = f"{unified.split(' (')[0]} ({unified})"
        content = content.replace(double, unified)
    
    if content != original:
        open(filepath, 'w', encoding='utf-8').write(content)
        print(f"Updated: {filename}")
    else:
        print(f"No changes: {filename}")

print("Done")
