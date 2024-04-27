// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
//    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    // Room
    id ("androidx.room") version "2.6.1" apply false
    // Dagger-Hilt
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    // Kapt
    kotlin("kapt") version "1.9.0" apply false
    // KSP
//    id("com.google.devtools.ksp") version "1.9.23-1.0.20" apply false


}