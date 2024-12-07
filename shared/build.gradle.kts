import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    //kotlin("multiplatform")
    //kotlin("plugin.serialization")
    //alias(libs.plugins.kotlinSerialization)
    //id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:2.3.2")
                implementation("io.ktor:ktor-client-json:2.3.2")
                implementation("io.ktor:ktor-client-logging:2.3.2")
                implementation("io.ktor:ktor-client-serialization:2.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
                implementation("com.russhwolf:multiplatform-settings:1.0.0")
                implementation("org.json:json:20230618")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.2")
                implementation("org.json:json:20230618")
            }
        }
        /*val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.2")
            }
        }*/

        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.kmpusersapp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
