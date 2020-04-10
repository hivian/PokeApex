import extensions.addTestsDependencies

plugins {
    id(Plugins.androidLibraryCommon)
}

dependencies {
    implementation(Libraries.gson)

    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.constraintLayout)
    implementation(AndroidLibraries.paging)
    implementation(AndroidLibraries.navigation)
    implementation(AndroidLibraries.navigationFrag)
    implementation(AndroidLibraries.preferences)

    addTestsDependencies()
}
