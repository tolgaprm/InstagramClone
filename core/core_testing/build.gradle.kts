plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.core_data"
}

dependencies {
    implementation(project(":core:core_domain"))
    implementation(libs.coroutines.test)
    implementation(libs.junit)
}