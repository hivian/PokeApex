plugins {
    id(Plugins.kotlinLibraryCommon)
}

dependencies {
    implementation(KotlinLibraries.kotlin)
    implementation(Libraries.gson)
    implementation(Libraries.roomRunTime)
}
