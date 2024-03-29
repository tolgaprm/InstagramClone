package library_convention_plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")

                extensions.configure<LibraryExtension> {
                    defaultConfig {
                        testInstrumentationRunner =
                            "com.prmto.core_android_testing.runner.HiltTestRunner"
                        minSdk = 26
                    }

                    defaultConfig.targetSdk = 33
                    compileSdk = 34

                    compileOptions {
                        sourceCompatibility = JavaVersion.VERSION_17
                        targetCompatibility = JavaVersion.VERSION_17
                    }

                    kotlinExtension.jvmToolchain(17)
                }
            }
        }
    }
}