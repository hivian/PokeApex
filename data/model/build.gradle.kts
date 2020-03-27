plugins {
    id(Plugins.kotlinLibraryCommon)
}

dependencies {
    implementation(Libraries.koin)
    implementation(KotlinLibraries.kotlin)
    implementation(Libraries.gson)
    implementation(Libraries.roomRunTime)
}
