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


    jsTarget()
    desktopJvm{
        testRuns["test"].executionTask.configure {
            useTestNG()
        }
    }
    nativeTargets { }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kuery:core"))
                // implementation("libetal.kotlin:log:1.0.0")
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
        val nativeMain by getting
        val nativeTest by getting

    }
}
