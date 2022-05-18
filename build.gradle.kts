import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }


    val kotlinVersion: String by project
    val androidBuildToolsVersion: String by project

    dependencies {
        classpath("com.android.tools.build:gradle:$androidBuildToolsVersion")
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }

}


allprojects {
    val projectGroup: String by project
    val projectVersion: String by project

    val mysqlProjectGroup by extra("$projectGroup.mysql")
    val sqliteProjectGroup by extra("$projectGroup.sqlite")

    version = projectVersion

    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }

}
