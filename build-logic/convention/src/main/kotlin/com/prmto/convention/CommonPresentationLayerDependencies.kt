package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandlerExt.library.accompanistPermissions
import com.prmto.convention.dependencyHandlerExt.library.coil
import com.prmto.convention.dependencyHandlerExt.library.compose
import com.prmto.convention.dependencyHandlerExt.library.composeNavigation
import com.prmto.convention.dependencyHandlerExt.library.composeUiTest
import com.prmto.convention.dependencyHandlerExt.library.coreKtx
import com.prmto.convention.dependencyHandlerExt.library.lifecycleRuntimeKtx
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.commonPresentationLayerDependencies(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        dependencies {
            coreKtx(libs)
            lifecycleRuntimeKtx(libs)
            accompanistPermissions(libs)
            coil(libs)
            composeNavigation(libs)
            compose(libs)
            composeUiTest(libs)
        }
    }
}