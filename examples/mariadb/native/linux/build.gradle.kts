plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.1.1"
}

kotlin {
    jvm()
}

dependencies {
    implementation(project(":annotations:mariadb"))
    implementation(project(":kuery:mysql:mariadb"))
}