plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.auth_data"
}

dependencies {
    implementation(project(":auth:auth_domain"))
    implementation(project(":core:core_data"))
}