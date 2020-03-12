plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExtensions)
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
    implementation(project(Modules.model))
    implementation(AndroidLibraries.lifecycleExtensions)
    implementation(TestLibraries.androidTestRunner)
    implementation(TestLibraries.espresso)
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    implementation(AndroidLibraries.recyclerView)
    implementation(TestLibraries.coroutine)
}
