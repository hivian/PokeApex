plugins {
    id(Plugins.androidLibraryCommon)
}

dependencies {
    implementation(Libraries.gson)

    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.navigation)
    implementation(AndroidLibraries.navigationFrag)
}
