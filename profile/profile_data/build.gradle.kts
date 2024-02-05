plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.profile_data"
}

dependencies {
    implementation(project(":core:core_data"))
    implementation(project(":profile:profile_domain"))
}