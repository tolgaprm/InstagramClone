package com.invio.convention

import com.android.build.api.dsl.CommonExtension
import com.invio.convention.dependencyHandler.addImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.firebaseCommonDependencies(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            addImplementation(platform(libs.findLibrary("firebase.bom").get()))
            addImplementation(libs.findLibrary("firebase.analytics.ktx").get())
            addImplementation(libs.findLibrary("firebase.auth.ktx").get())
            addImplementation(libs.findLibrary("play.services.auth").get())
            addImplementation(libs.findLibrary("firebase.firestore.ktx").get())
        }
    }
}