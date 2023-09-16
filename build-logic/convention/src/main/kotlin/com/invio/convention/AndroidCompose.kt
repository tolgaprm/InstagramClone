package com.invio.convention

import com.android.build.api.dsl.CommonExtension
import com.invio.convention.dependencyHandler.addAndroidTestImplementation
import com.invio.convention.dependencyHandler.addDebugImplementation
import com.invio.convention.dependencyHandler.addImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion =
                libs.findVersion("androidxComposeCompiler").get().toString()
        }

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            addImplementation(platform(bom))
            addDebugImplementation(libs.findLibrary("ui-tooling").get())
            addDebugImplementation(libs.findLibrary("ui-test-manifest").get())
            addAndroidTestImplementation(platform(bom))
        }
    }
}