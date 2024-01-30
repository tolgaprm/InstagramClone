plugins {
    id("instagram.android.library")
    id("instagram.android.library.compose")
}

android {
    namespace = "com.prmto.camera"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.bundles.cameraX)
    implementation(libs.timber)
    implementation(libs.ucrop)

    testImplementation(libs.junit)
    testImplementation(libs.truth.library)
}