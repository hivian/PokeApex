package commons

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
    // KOIN (Because each feature has to handle its dependencies)
    implementation(Libraries.koin)
    implementation(Libraries.koinViewModel)
    // GLIDE
    implementation(Libraries.glide)
    // TEST
    androidTestImplementation(TestLibraries.androidTestRunner)
    androidTestImplementation(TestLibraries.junit)
    androidTestImplementation(project(Modules.commonTest))
    androidTestImplementation(TestLibraries.mockkAndroid)
    androidTestImplementation(TestLibraries.fragmentNav)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.espressoContrib)
    androidTestImplementation(TestLibraries.koin)
    androidTestImplementation(TestLibraries.archCoreTest)
    testImplementation(TestLibraries.androidTestRunner)
    testImplementation(TestLibraries.junit)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.archCoreTest)
    kaptAndroidTest(TestLibraries.databinding)
    testImplementation(project(Modules.commonTest))
}