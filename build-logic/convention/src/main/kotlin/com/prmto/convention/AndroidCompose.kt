package com.prmto.convention

import com.android.build.api.dsl.CommonExtension
import com.prmto.convention.dependencyHandlerExt.library.accompanistPermissions
import com.prmto.convention.dependencyHandlerExt.library.coil
import com.prmto.convention.dependencyHandlerExt.library.compose
import com.prmto.convention.dependencyHandlerExt.library.composeUiTest
import com.prmto.convention.dependencyHandlerExt.library.coreKtx
import com.prmto.convention.dependencyHandlerExt.library.lifecycleRuntimeKtx
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
            coreKtx(libs)
            lifecycleRuntimeKtx(libs)
            compose(libs)
            accompanistPermissions(libs)
            coil(libs)
            composeUiTest(libs)
        }
    }
}