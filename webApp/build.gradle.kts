
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(libs.compose.ui)
            }
        }

        // Общий исходный набор для браузерной версии.
        val webMain by creating {
            dependsOn(commonMain)
        }

        // webMain используется JavaScript- и Wasm-версиями.
        val jsMain by getting {
            dependsOn(webMain)

            dependencies {
                implementation("com.github.terrakok:navigation3-browser:1.1.0")
            }
        }

        val wasmJsMain by getting {
            dependsOn(webMain)

            dependencies {
                implementation("com.github.terrakok:navigation3-browser:1.1.0")
            }
        }
    }
}
