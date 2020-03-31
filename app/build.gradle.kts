plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
    kotlin(Plugins.kapt)
}

//apply(rootProject.file("common-precompiled.gradle.kts"))

android {
    compileSdkVersion(Versions.compileSdk)
    buildToolsVersion = "29.0.2"
    defaultConfig {
        applicationId = ApplicationId.id
        minSdkVersion(Versions.minSdk)
        targetSdkVersion(Versions.targetSdk)
        versionCode = Releases.versionCode
        versionName = Releases.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    dataBinding {
        isEnabled = true
    }
    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            java.srcDir("src/androidTest/kotlin")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // COMMON
    implementation(project(Modules.common))
    // DATA modules
    implementation(project(Modules.model))
    implementation(project(Modules.local))
    implementation(project(Modules.remote))
    implementation(project(Modules.repository))
    // FEATURES modules
    implementation(project(Modules.featureHome))
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // ANDROID
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.navigation)
    implementation(AndroidLibraries.navigationFrag)
}
