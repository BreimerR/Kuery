import libetal.gradle.extensions.crossPlatformNative
import libetal.libraries.commonTargets

plugins {
    kotlin("multiplatform")
}

kotlin {

    crossPlatformNative().apply {
        binaries {
            executable {
                entryPoint = "libetal.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kuery:sqlite:core"))
                implementation(project(":annotations:sqlite"))
                implementation("libetal.kotlin:log:1.0.1")
            }
        }

    }


}

