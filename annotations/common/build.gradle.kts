@file:Suppress("UnstableApiUsage")

import libetal.libraries.commonTargets
import libetal.libraries.invoke
import libetal.libraries.mobileTargets

val kotlinVersion: String by extra

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

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}

group = "$kueryProjectGroup.annotations"
version = projectVersion

kotlin {

    commonTargets()

    mobileTargets()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val desktopJvmMain by getting
        val desktopJvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.6.1")
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
    namespace = "libetal.libraries.kuery.annotations.common"
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
dependencies {
    implementation("androidx.core:core-ktx:+")
}
repositories {
    mavenCentral()
}
