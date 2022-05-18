@file:Suppress("UnstableApiUsage")

import libetal.libraries.invoke

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

val sqliteProjectGroup: String by extra

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}

group = sqliteProjectGroup


kotlin {

    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kuery:sqlite:core"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.5.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

