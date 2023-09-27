plugins {
    id("instagram.android.library.compose")
    id("instagram.android.library")
}

android {
    namespace = "com.prmto.permission"
}

dependencies {
    implementation(project(":core:core_presentation"))
    implementation(project(":core:core_domain"))
}