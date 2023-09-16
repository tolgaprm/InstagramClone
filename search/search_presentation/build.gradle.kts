plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.search_presentation"
}

dependencies {
    implementation(project(":core:core_presentation"))
}