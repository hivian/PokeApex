import extensions.applyDefault


allprojects {
    plugins.apply(BuildPlugins.DETEKT)
    plugins.apply(BuildPlugins.UPDATE_DEPENDENCIES)

    repositories.applyDefault()
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

task("x", type = GradleBuild::class) {
    tasks = mutableListOf("detekt", "ktlint", "lintDebug", "testDebugUnitTest")
}
