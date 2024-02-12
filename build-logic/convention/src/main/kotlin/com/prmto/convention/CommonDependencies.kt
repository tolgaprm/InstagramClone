package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandlerExt.implementation.addAndroidTestImplementation
import com.prmto.convention.dependencyHandlerExt.library.coreKtx
import com.prmto.convention.dependencyHandlerExt.library.coroutines
import com.prmto.convention.dependencyHandlerExt.library.junit
import com.prmto.convention.dependencyHandlerExt.library.mockk
import com.prmto.convention.dependencyHandlerExt.library.timber
import com.prmto.convention.dependencyHandlerExt.library.truth
import com.prmto.convention.dependencyHandlerExt.library.turbine
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.commonDependenciesForEachModule(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            coreKtx(libs)
            timber(libs)
            coroutines(libs)
            truth(libs)
            mockk(libs)
            turbine(libs)
            junit(libs)
            addAndroidTestImplementation(libs.findLibrary("androidx-test-ext-junit").get())
        }
    }
}