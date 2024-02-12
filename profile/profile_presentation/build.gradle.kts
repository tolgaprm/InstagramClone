import com.prmto.convention.dependencyHandlerExt.module.cameraFeature
import com.prmto.convention.dependencyHandlerExt.module.corePresentationModule
import com.prmto.convention.dependencyHandlerExt.module.permissionFeature
import com.prmto.convention.dependencyHandlerExt.module.profileDomainModule

plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.profile_presentation"
}

dependencies {
    corePresentationModule()
    profileDomainModule()
    cameraFeature()
    permissionFeature()
    implementation(libs.firebase.auth.ktx)
    implementation(libs.bundles.cameraX)
}