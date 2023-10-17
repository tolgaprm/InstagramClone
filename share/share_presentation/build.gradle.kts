plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.share_presentation"
}

dependencies {
    implementation(project(":core:core_presentation"))
    implementation(project(":camera"))
    implementation(project(":permission"))
    implementation(libs.bundles.cameraX)
}