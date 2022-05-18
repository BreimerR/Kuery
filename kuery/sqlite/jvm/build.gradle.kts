@file:Suppress("UnstableApiUsage")

import libetal.libraries.commonTargets
import libetal.libraries.desktopJvm
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

val projectGroup: String by project
val sqliteProjectGroup: String by extra

repositories {
    mavenLocal()
    mavenCentral()
}

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = sqliteProjectGroup

kotlin {

    desktopJvm()

    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(project(":kuery:sqlite:core"))
                // implementation("libetal.kotlin:log:1.0.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

    }

}