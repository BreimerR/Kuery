import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kueryProjectGroup: String by project
val kotlinVersion: String by project
val kspVersion: String by project

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "$kueryProjectGroup.plugins"
// version = projectVersion

kotlin {

    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("com.google.devtools.ksp:symbol-processing-api:$kotlinVersion-$kspVersion")
                api(project(":annotations:common"))
            }
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions{
        kotlin.sourceSets.all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}