@file:Suppress("UnstableApiUsage")

import libetal.libraries.invoke

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
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose") version "1.1.1"
}


android {
    compileSdk = androidCompileSdkVersion

    defaultConfig {
        applicationId = "$projectGroup.krom.android.example"
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
        versionCode = 1
        versionName = projectVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }
    packagingOptions {
        resources {
            // excludes += "**/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(compose.ui)
    implementation(compose.runtime)
    implementation(compose.material)
    implementation(compose.foundation)
    implementation(compose.materialIconsExtended)

    implementation(project(":examples:sqlite:common"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    implementation("androidx.navigation:navigation-compose:2.5.0-beta01")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

}


