@file:Suppress("UnstableApiUsage")

import libetal.libraries.commonTargets
import libetal.libraries.invoke

val kotlinVersion: String by extra

val projectVersion: String by project
val projectGroup: String by project

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "$projectGroup.annotations"
version = projectVersion

kotlin {

    commonTargets()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":annotations:common"))
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