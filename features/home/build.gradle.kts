plugins {
    id(Plugins.featureLibraryCommon)
    id("androidx.navigation.safeargs.kotlin")
}

android {
    defaultConfig {
        testInstrumentationRunner = "com.hivian.common_test.FakeRunner"
    }
    testOptions {
        animationsDisabled = true
    }
}

dependencies {

}
