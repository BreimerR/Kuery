val kueryProjectGroup: String by project
val projectVersion: String by project
val kotlinVersion: String by project
val kspVersion: String by project

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp") version "1.6.21-1.0.6"
    `maven-publish`
}

group = "$kueryProjectGroup.sqlite.plugin"
version = projectVersion

kotlin {

    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.google.devtools.ksp:symbol-processing-api:$kotlinVersion-$kspVersion")
                implementation(project(":plugins:common"))
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        kotlin.sourceSets.all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}