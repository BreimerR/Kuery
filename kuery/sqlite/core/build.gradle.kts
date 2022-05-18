@file:Suppress("UnstableApiUsage")

import libetal.libraries.*

val kotlinVersion: String by project

val projectVersion: String by project

val androidCompileSdkVersion by project {
    it.toString().toInt()
}

val minSdkVersion by project {
    it.toString().toInt()
}

val targetSdkVersion by project {
    it.toString().toInt()
}

val projectGroup: String by project

val sqliteProjectGroup: String by extra

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = sqliteProjectGroup
version = "$projectVersion-3" // this should represent the version of sqlite i.e 1.0.0-3

kotlin {
    jsTarget()
    desktopJvm()
    isMac {
        iosArm32()
        iosArm64()
    }

    nativeTargets {
        val main by compilations.getting
        val sqlite3Interop by main.cinterops.creating {
            val nativeInteropDir = "$projectDir/src/nativeInterop"
            defFile = File("$nativeInteropDir/kotlin/interop.def")
            compilerOpts.add("-I/$nativeInteropDir/sqlite3/src")
            headers(
                fileTree("$nativeInteropDir/sqlite3/src") {
                    include("**/*.h")
                    include("**/*.hpp")
                }
            )

            packageName = "libetal.interop.sqlite3"

        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kuery:core"))
                api(project(":kuery:library"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jsMain by getting
        val jsTest by getting

        val desktopJvmMain by getting
        val desktopJvmTest by getting

        val nativeMain by getting {
        }
        val nativeTest by getting
        /*val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.5.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }*/
    }
}
