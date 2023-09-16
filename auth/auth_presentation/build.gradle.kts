plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.auth_presentation"
}

dependencies {
    implementation(project(":core:core_presentation"))
    implementation(project(":auth:auth_domain"))
}