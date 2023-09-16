plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.auth_domain"
}

dependencies {
    implementation(project(":core:core_domain"))
}