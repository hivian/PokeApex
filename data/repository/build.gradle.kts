plugins {
    id(Plugins.androidLibraryCommon)
    kotlin(Plugins.kapt)
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
    // RETROFIT
    implementation(Libraries.retrofit)
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // TESTS
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.archCoreTest)
    testImplementation(TestLibraries.coroutine)
    testImplementation(project(Modules.commonTest))
}
