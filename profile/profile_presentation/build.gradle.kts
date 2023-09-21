plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.profile_presentation"
}

dependencies {
    implementation(project(":core:core_presentation"))
    implementation(project(":camera"))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.bundles.cameraX)
}