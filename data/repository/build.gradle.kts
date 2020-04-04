import extensions.addTestsDependencies

plugins {
    id(Plugins.androidLibraryCommon)
    kotlin(Plugins.kapt)
}

dependencies {
    // MODULES
    implementation(project(Modules.common))
    implementation(project(Modules.remote))
    implementation(project(Modules.local))
    api(project(Modules.model))
    // ANDROID
    implementation(KotlinLibraries.kotlinCoroutineCore)
    implementation(AndroidLibraries.kotlinCoroutineAndroid)
    implementation(AndroidLibraries.lifecycleExtensions)
    // RETROFIT
    implementation(Libraries.retrofit)
    implementation(Libraries.gson)
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // TESTS
    testImplementation(project(Modules.commonTest))
    addTestsDependencies()
}
