plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.profile_domain"
}

dependencies {
    implementation(project(":core:core_domain"))
}