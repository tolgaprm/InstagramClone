plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.share_data"
}

dependencies {
    implementation(project(":core:core_data"))
    implementation(project(":share:share_domain"))
}