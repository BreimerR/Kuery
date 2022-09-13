@file:Suppress("UnstableApiUsage")
@file:SuppressLint("GradleDependency")

import libetal.libraries.*
import android.annotation.SuppressLint
import libetal.libraries.invoke
import libetal.libraries.isMac

val kotlinVersion: String by project

val projectVersion: String by project

val androidCompileSdkVersion by project {
    it.toString().toInt()
}

val androidMinSdkVersion by project {
    it.toString().toInt()
}

val androidTargetSdkVersion by project {
    it.toString().toInt()
}

val kueryProjectGroup: String by project

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

group = kueryProjectGroup
version = projectVersion

kotlin {

    jsTarget()
    desktopJvm()

    isMac {
        iosArm32()
        iosArm64()
    }

    android {
        publishLibraryVariants("release", "debug")
    }

    // TODO: Affects build system for kmm android()
    // TODO: androidNativeArm64("androidArm64")
    // TODO: androidNativeArm32("androidArm32")

    nativeTargets { }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api("libetal.libraries.kotlin:log:1.0.2")
                api("libetal.libraries.kotlin:library:1.0.2")
                api("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                // api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
                // TODO implementation ("org.jetbrains.kotlinx:atomicfu:0.17.1")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val desktopJvmMain by getting
        val desktopJvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting {
            dependencies {

            }
        }
        val nativeTest by getting

        isMac {
            val iosArm32Main by getting
            val iosArm64Main by getting
        }

        isWindows {
            val mingwX64Main by getting
            val mingwX64Test by getting
        }

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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        allprojects {
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
            // freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
        }
        kotlin.sourceSets.all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}