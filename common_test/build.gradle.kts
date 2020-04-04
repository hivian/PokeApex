plugins {
    id(Plugins.androidLibraryCommon)
}

dependencies {
    implementation(project(Modules.model))
    implementation(AndroidLibraries.lifecycleExtensions)
    implementation(TestLibraries.androidTestRunner)
    implementation(TestLibraries.espresso)
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    implementation(AndroidLibraries.recyclerView)
    implementation(TestLibraries.coroutines)
}
