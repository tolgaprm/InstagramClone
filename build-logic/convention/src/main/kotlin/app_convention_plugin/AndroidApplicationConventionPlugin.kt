package app_convention_plugin

import com.android.build.api.dsl.ApplicationExtension
import com.prmto.convention.addBuildConfigField
import com.prmto.convention.dependencyHandlerExt.module.authFeature
import com.prmto.convention.dependencyHandlerExt.module.cameraFeature
import com.prmto.convention.dependencyHandlerExt.module.coreFeature
import com.prmto.convention.dependencyHandlerExt.module.homeFeature
import com.prmto.convention.dependencyHandlerExt.module.permissionFeature
import com.prmto.convention.dependencyHandlerExt.module.profileFeature
import com.prmto.convention.dependencyHandlerExt.module.reelsFeature
import com.prmto.convention.dependencyHandlerExt.module.searchFeature
import com.prmto.convention.dependencyHandlerExt.module.shareFeature
import configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import java.util.Properties

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val properties = Properties().apply {
            load(target.rootProject.file("local.properties").inputStream())
        }

        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                compileSdk = 34
                defaultConfig {
                    targetSdk = 33
                    testInstrumentationRunner =
                        "com.prmto.core_android_testing.runner.HiltTestRunner"
                    addBuildConfigField(properties, "FIREBASE_PROJECT_ID")
                    addBuildConfigField(properties, "FIREBASE_APPLICATION_ID")
                    addBuildConfigField(properties, "FIREBASE_API_KEY")
                }
            }

            dependencies {
                coreFeature()
                homeFeature()
                authFeature()
                cameraFeature()
                permissionFeature()
                profileFeature()
                reelsFeature()
                shareFeature()
                searchFeature()
            }
        }
    }
}