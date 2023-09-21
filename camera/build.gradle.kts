plugins {
    id("instagram.android.library")
    id("instagram.android.library.compose")
}

android {
    namespace = "com.prmto.camera"
}

dependencies {
    implementation(libs.bundles.cameraX)
}