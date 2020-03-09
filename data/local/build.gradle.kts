plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
    kotlin(Plugins.kapt)
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
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    // ROOM
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomRunTime)
    implementation(Libraries.roomKtx)
    implementation(AndroidLibraries.lifecycleExtensions)
    // TEST MODULE
    //androidTestImplementation(project(Modules.commonTest))
    // DATA MODULE
    implementation(project(Modules.model))
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // TEST
    androidTestImplementation(TestLibraries.androidTestRunner)
    androidTestImplementation(TestLibraries.junit)
    androidTestImplementation(TestLibraries.koin)
    androidTestImplementation(TestLibraries.archCoreTest)
}
