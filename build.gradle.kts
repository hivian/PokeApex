// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.safeArgs}")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version("1.6.0")
    id("com.github.ben-manes.versions").version("0.20.0")
}

allprojects {
    apply(from = "$rootDir/detekt.gradle")

    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

task("x", type = GradleBuild::class) {
    tasks = mutableListOf("detekt", "ktlint", "lintDebug", "testDebugUnitTest")
}

