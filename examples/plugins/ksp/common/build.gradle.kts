val kotlinVersion: String by project
val composeVersion: String by project
val androidMinSdkVersion: String by project
val androidTargetSdkVersion: String by project
val androidCompileSdkVersion: String by project

plugins {
    kotlin("multiplatform")
    id("com.google.devtools.ksp") version "1.6.21-1.0.6"
    `maven-publish`
}



kotlin {

    jvm()
    linuxX64() // TODO: Add Support for other native clients

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":annotations:common"))
                api("libetal.libraries.kuery.sqlite:core:0.0.1-SNAPSHOT")
            }

            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
        }

        val commonTest by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonTest/kotlin")
        }
        val linuxX64Main by getting
    }

}

ksp {
    arg("packageName", "libetal.applications.kuery.entities")
    arg("dbExpected", "true")
    arg("databaseObjectClass", "libetal.applications.kuery.plugin.examples.data.Database")
}

dependencies {
    // add("kspCommonMainMetadata", project(":plugins:common"))
    add("kspCommonMainMetadata", project(":plugins:sqlite:common"))
    // add("kspMetadata", project(":plugins:sqlite:common"))
    // add("kspLinuxX64", project(":plugins:mysql:mariadb"))
    /*
    * add("kspCommonMainMetadata", project(":test-processor"))
    add("kspJvm", project(":test-processor"))
    add("kspJvmTest", project(":test-processor"))
    add("kspJs", project(":test-processor"))
    add("kspJsTest", project(":test-processor"))
    add("kspAndroidNativeX64", project(":test-processor"))
    add("kspAndroidNativeX64Test", project(":test-processor"))
    add("kspAndroidNativeArm64", project(":test-processor"))
    add("kspAndroidNativeArm64Test", project(":test-processor"))
    add("kspLinuxX64", project(":test-processor"))
    add("kspLinuxX64Test", project(":test-processor"))
    add("kspMingwX64", project(":test-processor"))
    add("kspMingwX64Test", project(":test-processor"))
    * */
}

/*
dependencies {

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
    implementation("androidx.activity:activity-compose:1.5.0")

    // TESTING
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
}*/
