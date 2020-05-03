plugins {
    id(Plugins.androidLibraryCommon)
}

dependencies {
    // MODULES
    implementation(project(Modules.common))
    // KOTLIN
    implementation(KotlinLibraries.kotlin)
    // NETWORK
    implementation(Libraries.retrofitCoroutineAdapter)
    implementation(Libraries.gson)
    implementation(Libraries.retrofit)
    implementation(Libraries.retrofitGsonConverter)
    implementation(Libraries.httpLoggingInterceptor)
    // DATA MODULE
    implementation(project(Modules.model))
    // KOIN
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // TEST
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.koin)
    testImplementation(TestLibraries.mockWebServer)
}
