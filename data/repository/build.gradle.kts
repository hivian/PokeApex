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
}

dependencies {
    // MODULES
    implementation(project(Modules.remote))
    implementation(project(Modules.local))
    api(project(Modules.model))
    api(Libraries.timberKt)
    // ANDROID
    implementation(KotlinLibraries.kotlinCoroutineCore)
    implementation(AndroidLibraries.kotlinCoroutineAndroid)
    implementation(AndroidLibraries.lifecycleExtensions)
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // TESTS
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.archCoreTest)
    testImplementation(TestLibraries.coroutine)
    //testImplementation(project(Modules.commonTest))
}
