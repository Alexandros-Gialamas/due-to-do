import org.jetbrains.kotlin.gradle.model.Kapt

plugins {
    alias(libs.plugins.androidApplication)
//    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.jetbrainsKotlinAndroid)

    // KSP
    id("com.google.devtools.ksp")

    // Kapt
//    kotlin("kapt")
//    id ("kotlin-kapt")

    // Room
    id ("androidx.room")

    // Dagger-Hilt
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.alexandros.p.gialamas.duetodo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexandros.p.gialamas.duetodo"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    // Room Specific
    room {
        schemaDirectory("$projectDir/schemas")
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    configurations{
//        implementation.get().exclude ( group = "org.jetbrains", module = "annotations")
//    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
//    implementation(libs.androidx.compose.material)  // Added for ContentAlpha
//    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.foundation)
    implementation(libs.ui)

    // WorkManager Kotlin + coroutines
    implementation(libs.androidx.work.runtime.ktx)
    // WorkManager - Multiprocess support
    implementation (libs.androidx.work.multiprocess)

    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx.v270)

    // ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose.v270)


    // Lifecycle utilities for Compose
    implementation(libs.androidx.lifecycle.runtime.compose.v270)

    // KSP
//    implementation(libs.symbol.processing.api)

    // Compose Navigation
    implementation(libs.androidx.navigation.compose)

    // Shared Element Transition
    implementation(libs.androidx.compose.animation)


    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.compiler)
    // Kapt
//    kapt(libs.androidx.room.compiler)
    // KSP
    ksp(libs.androidx.room.compiler)

    // Dagger - Hilt
//    implementation("com.google.dagger:hilt-android:2.48")
    implementation(libs.hilt.android)
    implementation (libs.androidx.hilt.navigation.compose)
    // Kapt
//    kapt(libs.dagger.compiler)
//    kapt(libs.hilt.android.compiler)
//    kapt(libs.hilt.compiler)
    // KSP
    ksp(libs.dagger.compiler)
    ksp(libs.hilt.android.compiler)
    ksp (libs.hilt.compiler)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // Permissions
    implementation(libs.accompanist.permissions)

    // Accompanist permission library
    implementation (libs.accompanist.permissions)

    // Compose Material Dialogs "Core"
    implementation (libs.core)

}