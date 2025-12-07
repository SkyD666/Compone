@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.androidLibrary
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    androidLibrary {
        namespace = "com.skyd.compone"
        compileSdk = 36
        minSdk = 24
        androidResources.enable = true
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    macosX64()
    macosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.material3)
            implementation(libs.compose.material3.adaptive.navigation)
            implementation(libs.compose.materialIconsExtended)
            implementation(libs.compose.components.resources)
            implementation(libs.jetbrains.navigation.compose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {
            implementation(libs.compose.desktop)
        }

        all {
            with(languageSettings) {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
                optIn("androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi")
                optIn("androidx.compose.ui.ExperimentalComposeUiApi")
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
            }
        }
    }
}

compose.resources {
    publicResClass = true
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates("io.github.skyd666", "compone", "1.0-beta10")

    pom {
        name = "Compone"
        description = "A Compose Multiplatform UI Kit."
        inceptionYear = "2025"
        url = "https://github.com/SkyD666/Compone"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "http://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "SkyD666"
                name = "SkyD666"
                url = "https://github.com/SkyD666"
            }
        }
        scm {
            url = "https://github.com/SkyD666/Compone"
            connection = "scm:git:git://github.com/SkyD666/Compone.git"
            developerConnection = "scm:git:ssh://git@github.com/SkyD666/Compone.git"
        }
    }
}
