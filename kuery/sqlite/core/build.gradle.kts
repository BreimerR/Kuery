@file:Suppress("UnstableApiUsage")

import libetal.libraries.*

val kotlinVersion: String by project

val projectVersion: String by project

val jdbcVersion: String by project

val androidCompileSdkVersion by project {
    it.toString().toInt()
}

val androidMinSdkVersion by project {
    it.toString().toInt()
}

val androidTargetSdkVersion by project {
    it.toString().toInt()
}

val projectGroup: String by project

val sqliteProjectGroup: String by extra

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}

group = sqliteProjectGroup
version = projectVersion // this should represent the version of sqlite i.e 1.0.0-3

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    jsTarget()
    desktopJvm {
        /** Makes tests not work right
        testRuns["test"].executionTask.configure {

        }**/
        testRuns["test"].executionTask.configure{
            useTestNG()
        }
    }
    isMac {
        iosArm32()
        iosArm64()
    }

    nativeTargets {
        val srcDir = File(projectDir, "src")
        val nativeInteropDir = File(srcDir, "nativeInterop")
        val nativeInteropKotlinDir = File(nativeInteropDir, "kotlin")
        val nativeInteropDefFile = File(nativeInteropKotlinDir, "interop.def")
        val headerFiles = listOf(
            "sqlite3"
        )

        val headers = headerFiles.joinToString(" ") { "$it.h" }

        nativeInteropDefFile.outputStream().use { stream ->
            stream.writer().use { writer ->
                writer.write(
                    """|# DO NOT EDIT FILE
                       |package=libetal.interop.sqlite3
                       |headers = $headers
                       |compilerOpts = -I${nativeInteropDir.path}/interop/include/
                       |headerFilter = sqlite3*.h
                       |
                       |linkerOpts.linux_x64 = -lpthread -ldl
                       |linkerOpts.macos_x64 = -lpthread -ldl
                    """.trimMargin()
                )
            }
        }

        val main by compilations.getting
        val sqlite3Interop by main.cinterops.creating {
            defFile = nativeInteropDefFile
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kuery:core"))
                // api("libetal.libraries.kotlin:ksqlite:1.0.0")
                implementation("libetal.libraries.kotlin:io:1.0.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            }
        }

        val commonTest by getting {

        }

        val androidMain by getting {
            dependencies {
                implementation("androidx.annotation:annotation:1.4.0")
            }
        }

        val jsMain by getting
        val jsTest by getting

        val desktopJvmMain by getting {
            dependencies {
                implementation("org.xerial:sqlite-jdbc:3.36.0.3")
            }
        }

        val desktopJvmTest by getting {
            dependencies {
               // implementation(kotlin("test"))
            }
        }


        val nativeMain by getting
        val nativeTest by getting

    }
}


android {
    compileSdk = androidCompileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
/*
dependencies {
    testImplementation("org.testng:testng:7.1.0")
}
tasks.withType<Test> {
    useJUnitPlatform()
}
*/

