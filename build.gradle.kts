import extensions.applyDefault

plugins {
    id("com.github.ben-manes.versions")
}

allprojects {
    plugins.apply(BuildPlugins.DETEKT)
    //plugins.apply(BuildPlugins.GRADLE_VERSION_PLUGIN)

    repositories.applyDefault()
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

task("x", type = GradleBuild::class) {
    tasks = mutableListOf("detekt", "ktlint", "lintDebug", "testDebugUnitTest")
}

