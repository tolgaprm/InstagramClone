package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addDebugImplementation
import com.prmto.convention.dependencyHandler.addImplementation
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