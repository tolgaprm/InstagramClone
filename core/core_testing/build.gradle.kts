plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.core_testing"
}

dependencies {
    implementation(project(":core:core_domain"))
    implementation(libs.coroutines.test)
    implementation(libs.junit)
}