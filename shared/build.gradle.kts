import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }

    jvm()

//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "shared"
//            isStatic = true
//        }
//    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(libs.jetbrains.navigation.compose)
            implementation(libs.jetbrains.compose.adaptive.navigation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
        }

        all {
            with(languageSettings) {
                optIn("androidx.compose.material3.ExperimentalMaterial3Api")
                optIn("androidx.compose.material3.ExperimentalMaterial3ExpressiveApi")
                optIn("androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi")
                optIn("kotlin.contracts.ExperimentalContracts")
            }
        }
    }
}

android {
    namespace = "com.skyd.compone"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.resources {
    publicResClass = true
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    signAllPublications()

    coordinates("io.github.skyd666", "compone", "1.0-beta06")

    pom {
        name.set("Compone")
        description.set("A Compose Multiplatform UI Kit.")
        inceptionYear.set("2025")
        url.set("https://github.com/SkyD666/Compone")
        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                id.set("SkyD666")
                name.set("SkyD666")
                url.set("https://github.com/SkyD666")
            }
        }
        scm {
            url.set("https://github.com/SkyD666/Compone")
            connection.set("scm:git:git://github.com/SkyD666/Compone.git")
            developerConnection.set("scm:git:ssh://git@github.com/SkyD666/Compone.git")
        }
    }
}
