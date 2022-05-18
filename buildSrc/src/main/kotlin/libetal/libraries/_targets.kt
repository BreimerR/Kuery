package libetal.libraries

import libetal.gradle.extensions.crossPlatformNative
import org.gradle.api.GradleException
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetWithHostTests

fun KotlinMultiplatformExtension.commonTargets() {

    jsTarget()

    desktopJvm("1.8")

    crossPlatformNative()

}

fun KotlinMultiplatformExtension.nativeTargets(
    shared: Boolean = true,
    configure: KotlinNativeTargetWithHostTests.() -> Unit = { }
): KotlinNativeTargetWithHostTests {
    val name = if (shared) "native" else null
    return when (val hostOs = System.getProperty("os.name")) {
        "Mac OS X" -> macosX64(name ?: "macosX64", configure)
        "Linux" -> linuxX64(name ?: "linuxX64", configure)
        else -> when (hostOs.startsWith("Windows")) {
            true -> mingwX64(name ?: "mingwX64", configure)
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }
    }
}

fun KotlinMultiplatformExtension.desktopJvm(jvmVersion: String = "1.8") = jvm("desktopJvm") {
    compilations.all {
        kotlinOptions.jvmTarget = jvmVersion
    }
}

fun KotlinMultiplatformExtension.jsTarget() = js(BOTH) {
    browser {
        commonWebpackConfig {
            cssSupport.enabled = true
        }
    }
}

fun KotlinMultiplatformExtension.mobileTargets() {
    isMac {
        iosArm32()
        iosArm64()
    }
    android()
}

fun <T> T.isMac(macAction: T.() -> Unit) {
    when (System.getProperty("os.name")) {
        "Mac OS X" -> macAction()
    }
}

fun isWindows(windowsAction: () -> Unit) {
    if (System.getProperty("os.name").startsWith("Windows")) {
        windowsAction()
    }
}
