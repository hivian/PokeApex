
/**
 * Configuration of android build
 */
object BuildAndroidConfig {
    const val APPLICATION_ID = "com.hivian.pokedex"

    const val BUILD_TOOLS_VERSION = "29.0.2"
    const val COMPILE_SDK_VERSION = 29
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 29

    const val VERSION_CODE = 107
    const val VERSION_NAME = "1.0.7"

    const val SUPPORT_LIBRARY_VECTOR_DRAWABLES = true

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    val TEST_INSTRUMENTATION_RUNNER_ARGUMENTS = mapOf(
        "leakcanary.FailTestOnLeakRunListener" to "listener"
    )
}