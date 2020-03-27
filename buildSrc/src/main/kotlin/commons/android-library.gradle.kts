package commons

import KotlinLibraries
import Libraries
import Releases
import Versions

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Versions.compileSdk)
    defaultConfig {
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Releases.versionCode
        versionName = Releases.versionName
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(KotlinLibraries.kotlin)
    implementation(Libraries.timberKt)
}