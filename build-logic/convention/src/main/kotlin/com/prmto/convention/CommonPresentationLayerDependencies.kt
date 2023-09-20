package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.commonPresentationLayerDependencies(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            addImplementation(libs.findLibrary("lifecycle-runtime-compose").get())
            addImplementation(libs.findLibrary("core-ktx").get())
            addImplementation(libs.findLibrary("lifecycle-runtime-ktx").get())
            addImplementation(libs.findLibrary("activity-compose").get())
            addImplementation(libs.findLibrary("ui").get())
            addImplementation(libs.findLibrary("ui-graphics").get())
            addImplementation(libs.findLibrary("ui-tooling-preview").get())
            addImplementation(libs.findLibrary("material3").get())
            addImplementation(libs.findLibrary("viewmodel-compose").get())
            addImplementation(libs.findLibrary("material").get())
            addImplementation(libs.findLibrary("accompanist-systemuicontroller").get())
            addImplementation(libs.findLibrary("icons-extended").get())

            addImplementation(libs.findLibrary("coil").get())
            addImplementation(libs.findLibrary("coil.svg").get())

            addImplementation(libs.findLibrary("navigation.compose").get())
            addImplementation(libs.findLibrary("hilt.navigation.compose").get())

            addAndroidTestImplementation(libs.findLibrary("ui-test-junit4").get())
            addAndroidTestImplementation(libs.findLibrary("espresso-core").get())
        }
    }
}