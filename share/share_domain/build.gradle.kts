plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.share_domain"
}

dependencies {
    implementation(project(":core:core_domain"))
    implementation(project(":core:core_testing"))
}