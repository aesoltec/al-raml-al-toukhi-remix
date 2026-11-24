# -*- coding: utf-8 -*-
"""
Fix double-unified terms created by the unify_terminology.py script.
"""
import os
import re

course_files = [
    "ToukhiCourseData.kt",
    "ToukhiManbaCourseDataPart1.kt",
    "ToukhiManbaCourseDataPart2.kt",
    "BahriAndQawlCourseData.kt",
    "ManuscriptsCourseData.kt",
]

base_path = "geomancy/src/main/java/com/example/geomancy"

# Fix double-unified terms
fixes = [
    ("Bayada (Bayaro (Al-Bayāḍ))", "Bayaro (Al-Bayāḍ)"),
    ("Bayada (Al-Bayāḍ)", "Bayaro (Al-Bayāḍ)"),
    ("Bayaro (Bayaro (Al-Bayāḍ))", "Bayaro (Al-Bayāḍ)"),
    ("Al-Ḥumra 2122", "Lomara (Al-Ḥumra) 2122"),
    ("Al-Bayāḍ 2212", "Bayaro (Al-Bayāḍ) 2212"),
    ("Al-ʿAtaba al-Dākhila 2111", "Malidjou (Al-ʿAtaba al-Dākhila) 2111"),
    ("Al-Ṭarīq 1111", "Taliki (Al-Ṭarīq) 1111"),
    ("Al-Ḥumra 2122", "Lomara (Al-Ḥumra) 2122"),
]

for filename in course_files:
    filepath = os.path.join(base_path, filename)
    if not os.path.exists(filepath):
        print(f"File not found: {filepath}")
        continue
    
    content = open(filepath, encoding='utf-8').read()
    original = content
    
    for old, new in fixes:
        content = content.replace(old, new)
    
    if content != original:
        open(filepath, 'w', encoding='utf-8').write(content)
        print(f"Fixed: {filename}")
    else:
        print(f"No changes: {filename}")

print("Done")
