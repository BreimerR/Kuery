import libetal.libraries.commonTargets
import libetal.libraries.jsTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.1.1"
}

kotlin {

    jsTarget()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":annotations:mariadb"))
                implementation(project(":kuery:mysql:mariadb"))
            }
        }
    }
}

