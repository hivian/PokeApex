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
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // COMMON
    implementation(project(Modules.common))

    implementation(KotlinLibraries.kotlin)
    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.constraintLayout)
    androidTestImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.espresso)
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // ANDROID
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.navigation)
    implementation(AndroidLibraries.navigationFrag)
}
