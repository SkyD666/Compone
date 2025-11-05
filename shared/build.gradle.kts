import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.vanniktech.mavenPublish)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
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
                optIn("kotlin.contracts.ExperimentalContracts")
            }
        }
    }
}

android {
    namespace = "com.skyd.compone"
    compileSdk = 36
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
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates("io.github.skyd666", "compone", "1.0-beta07")

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
