@file:Suppress("UnstableApiUsage")

import libetal.libraries.*
import org.jetbrains.kotlin.konan.library.konanPlatformLibraryPath

val kotlinVersion: String by project

val projectVersion: String by project

val jdbcVersion: String by project

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
    id("com.android.library")
    `maven-publish`
}

group = sqliteProjectGroup
version = "$projectVersion-3" // this should represent the version of sqlite i.e 1.0.0-3

kotlin {
    android {

    }
    jsTarget()
    desktopJvm()
    isMac {
        iosArm32()
        iosArm64()
    }

    nativeTargets {
        val srcDir = File(projectDir, "src")
        val nativeInteropDir = File(srcDir, "nativeInterop")
        val nativeInteropKotlinDir = File(nativeInteropDir, "kotlin")
        val nativeInteropDefFile = File(nativeInteropKotlinDir, "interop.def")
        nativeInteropDefFile.outputStream().use { stream ->
            stream.writer().use { writer ->
                writer.write(
                    """
                    |# DO NOT EDIT FILE 
                    |headers = sqlite3.h library.h
                    |compilerOpts.linux= -I/usr/include \
                    |                    -I/usr/local/include \
                    |                    -I/usr/include/x86_64-linux-gnu \
                    |                    -I$nativeInteropDir/sqlite3/src
                    |staticLibraries = libsqlite3_interop.a libsqlite3.a
                    |libraryPaths = /usr/local/lib /usr/lib $nativeInteropDir/sqlite3/cmake-build-debug
                """.trimMargin()
                )
            }
        }

        val main by compilations.getting
        val sqlite3Interop by main.cinterops.creating {
            defFile = nativeInteropDefFile
            // headers(
            //     fileTree("$nativeInteropDir/sqlite3/src") {
            //         include("**/*.h")
            //         include("**/*.hpp")
            //     }
            // )

            packageName = "libetal.interop.sqlite3"

        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kotlin:io"))
                api(project(":kuery:core"))
                api(project(":kotlin:library"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                api("androidx.annotation:annotation:1.4.0")
            }
        }

        val jsMain by getting
        val jsTest by getting

        val desktopJvmMain by getting {
            dependencies {
                implementation("org.xerial:sqlite-jdbc:3.36.0.3")
                // implementation("com.oracle.database.jdbc:ojdbc11:$jdbcVersion")
            }
        }
        val desktopJvmTest by getting {

        }

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


android {
    compileSdk = androidCompileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
