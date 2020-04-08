plugins {
    id(Plugins.androidLibraryCommon)
}

dependencies {
    implementation(Libraries.gson)

    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.paging)
    implementation(AndroidLibraries.navigation)
    implementation(AndroidLibraries.navigationFrag)
    implementation(AndroidLibraries.preferences)
}
