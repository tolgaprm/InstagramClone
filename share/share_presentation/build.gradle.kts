import com.prmto.convention.dependencyHandlerExt.module.cameraFeature
import com.prmto.convention.dependencyHandlerExt.module.corePresentationModule
import com.prmto.convention.dependencyHandlerExt.module.permissionFeature
import com.prmto.convention.dependencyHandlerExt.module.shareDomainModule

plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.share_presentation"
}

dependencies {
    corePresentationModule()
    cameraFeature()
    permissionFeature()
    shareDomainModule()
    implementation(libs.bundles.cameraX)
}