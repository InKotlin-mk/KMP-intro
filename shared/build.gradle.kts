import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
       browser()
        binaries.executable()
    }

    js(IR) {
        useCommonJs()
        browser()
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = libs.versions.app.build.kotlinJVMTarget.get()
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kermit)
            implementation(libs.kotlin.coroutines.core)
            val wasmVersion = "wasm2"
            implementation("io.ktor:ktor-client-core:3.0.0-$wasmVersion")
            implementation("io.ktor:ktor-client-logging:3.0.0-$wasmVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0-$wasmVersion")
            implementation("io.ktor:ktor-client-content-negotiation:3.0.0-$wasmVersion")
            /*implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.content.negotiation)*/
        }

        iosMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:atomicfu:0.23.2")
                implementation(libs.ktor.client.darwin)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.okhttp)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.ktor.client.android)
                implementation(libs.ktor.client.okhttp)
            }
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

android {
    namespace = "mk.inkotlin.kmp.intro.shared"
    compileSdk = libs.versions.app.build.compileSDKVersion.get().toInt()
    defaultConfig {
        minSdk = libs.versions.app.build.minimumSDK.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.app.build.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.app.build.javaVersion.get())
    }
    kotlin {
        jvmToolchain(libs.versions.app.build.kotlinJVMTarget.get().toInt())
    }
}
