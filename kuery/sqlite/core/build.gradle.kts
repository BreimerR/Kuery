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
        val headerFiles = listOf(
            "library"
        )
        val headers = headerFiles.joinToString(" ") { "$it.h" }

        nativeInteropDefFile.outputStream().use { stream ->
            stream.writer().use { writer ->
                writer.write(
                    """|# DO NOT EDIT FILE 
                       |headers = $headers
                       |compilerOpts = -I${nativeInteropDir.path}/sqlite3/include
                    """.trimMargin()
                )
            }
        }

        val main by compilations.getting
        val sqlite3Interop by main.cinterops.creating {
            defFile = nativeInteropDefFile
            packageName = "libetal.interop.sqlite3"

        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":kuery:core"))
                api("libetal.libraries.kotlin:log:1.0.2")
                api("libetal.libraries.kotlin:io:1.0.2")
                api("libetal.libraries.kotlin:library:1.0.2")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
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
        minSdk = androidMinSdkVersion
        targetSdk = androidTargetSdkVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}