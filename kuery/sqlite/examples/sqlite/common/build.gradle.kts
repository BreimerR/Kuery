@file:Suppress("UnstableApiUsage")

import libetal.gradle.extensions.crossPlatformNative
import libetal.libraries.commonTargets
import libetal.libraries.invoke

val kotlin_version: String by extra

val composeVersion = "1.1.1"

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

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {

    commonTargets()

    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // implementation(compose.ui)
                // implementation(compose.runtime)
                // implementation(compose.material)
                api(project(":kuery:sqlite:core"))
                api(project(":annotations:common"))
                api(project(":annotations:sqlite"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }

}


android {

    compileSdk = androidCompileSdkVersion
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {

        minSdk = minSdkVersion
        targetSdk = targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources {
            // excludes += "**/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}