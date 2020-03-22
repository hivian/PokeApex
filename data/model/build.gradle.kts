plugins {
    id("commons.kotlin-library")
}

dependencies {
    implementation(KotlinLibraries.kotlin)
    implementation(Libraries.gson)
    implementation(Libraries.roomRunTime)
}
