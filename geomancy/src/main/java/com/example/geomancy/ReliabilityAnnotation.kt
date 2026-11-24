package com.example.geomancy

/**
 * Annotation marquant le niveau de fiabilité d'un composant du moteur de géomancie.
 * Basé sur le fichier canonique skillsGeomancie.md (v2.13).
 *
 * - A : Attesté (source académique)
 * - B : Source unique (forum de praticiens)
 * - C : Calculé/vérifié (mathématiquement)
 * - D : NON SOURCÉ (invention, interdit de l'utiliser comme règle)
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class Reliability(val level: ReliabilityLevel)