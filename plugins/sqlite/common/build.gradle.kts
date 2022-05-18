val projectVersion: String by project
val projectGroup: String by project

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "$projectGroup.sqlite.plugin"
version = projectVersion

kotlin {

    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                api("com.google.devtools.ksp:symbol-processing-api:1.6.20-1.0.5")
                implementation(project(":plugins:common"))
            }
        }
    }
}