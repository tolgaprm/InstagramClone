plugins {
    id("instagram.android.layer.domain")
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.core_android_testing"
}

dependencies {
    implementation(project(":core:core_domain"))
    implementation(project(":core:core_testing"))
    implementation(libs.datastore.preferences)
    implementation(libs.coroutines.test)
    implementation(libs.junit)
    implementation(libs.androidx.runner)
    implementation(libs.hilt.testing)
}