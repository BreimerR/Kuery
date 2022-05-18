import java.io.FileInputStream
import java.util.*

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
}

val gradleProperties = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "../gradle.properties")))
}
val kotlinVersion: String by gradleProperties
val androidBuildToolsVersion: String by gradleProperties

dependencies {
    implementation(kotlin("gradle-plugin", kotlinVersion))
    implementation("libetal.plugins.gradle:BuildSrc:1.6.10")
    implementation("com.android.tools.build:gradle:$androidBuildToolsVersion")
}