package commons

import extensions.addTestsDependencies

plugins {
    id("commons.android-library")
}

dependencies {
    implementation(project(Modules.common))

    implementation(project(Modules.repository))
    // KOTLIN
    implementation(KotlinLibraries.kotlin)
    implementation(KotlinLibraries.kotlinCoroutineCore)
    implementation(AndroidLibraries.kotlinCoroutineAndroid)
    implementation(KotlinLibraries.anko)
    // ANDROID
    implementation(AndroidLibraries.appCompat)
    implementation(AndroidLibraries.coreKtx)
    implementation(AndroidLibraries.swipeRefreshLayout)
    implementation(AndroidLibraries.paging)
    implementation(AndroidLibraries.constraintLayout)
    implementation(AndroidLibraries.lifecycleViewModel)
    implementation(AndroidLibraries.lifecycleExtensions)
    implementation(AndroidLibraries.recyclerView)
    implementation(AndroidLibraries.navigation)
    implementation(AndroidLibraries.navigationFrag)
    implementation(AndroidLibraries.preferences)
    // KOIN (Because each feature has to handle its dependencies)
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // GLIDE
    implementation(Libraries.glide)
    // TEST
    testImplementation(project(Modules.commonTest))
    androidTestImplementation(project(Modules.commonTest))
    addTestsDependencies()
}