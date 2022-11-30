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

val mysqlProjectGroup: String by extra

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = mysqlProjectGroup
version = projectVersion

kotlin {


    desktopJvm {
        testRuns["test"].executionTask.configure {
            useTestNG()
        }
    }

    nativeTargets {
        val main by compilations.getting
        val mariaDb by main.cinterops.creating {
            val nativeInterop = "$projectDir/src/nativeInterop/"
            defFile = File(nativeInterop + "kotlin/interop.def")
            headers(
                fileTree(nativeInterop + "mariadb") {
                    include("**/*.h")
                    include("**/*.hpp")
                }
            )
            packageName = "kuery.interop.mariadb"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kuery:core"))
                api("libetal.libraries.kotlin:log:1.1.0")
                api("libetal.libraries.kotlin:library:1.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        // val jsMain by getting
        // val jsTest by getting
        val nativeMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
            }
        }
        val nativeTest by getting
        val desktopJvmMain by getting {
            dependencies {
                implementation("org.mariadb.jdbc:mariadb-java-client:3.0.4")
            }
        }
        val desktopJvmTest by getting

    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        allprojects {
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
        }
        kotlin.sourceSets.all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}