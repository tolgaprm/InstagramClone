package com.invio.convention

import com.android.build.api.dsl.CommonExtension
import com.invio.convention.dependencyHandler.addAndroidTestImplementation
import com.invio.convention.dependencyHandler.addImplementation
import com.invio.convention.dependencyHandler.addTestImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.commonDependenciesForEachModule(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            addImplementation(libs.findLibrary("core-ktx").get())
            addImplementation(libs.findLibrary("timber").get())

            addTestImplementation(libs.findLibrary("coroutines-test").get())
            addTestImplementation(libs.findLibrary("junit").get())
            addTestImplementation(libs.findLibrary("truth-library").get())
            addTestImplementation(libs.findLibrary("mockk").get())
            addTestImplementation(libs.findLibrary("turbine").get())

            addAndroidTestImplementation(libs.findLibrary("coroutines-test").get())
            addAndroidTestImplementation(libs.findLibrary("truth-library").get())
            addAndroidTestImplementation(libs.findLibrary("mockk").get())
            addAndroidTestImplementation(libs.findLibrary("turbine").get())
            addAndroidTestImplementation(libs.findLibrary("androidx-test-ext-junit").get())
        }
    }
}