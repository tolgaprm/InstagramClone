plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.home_data"
}

dependencies {
    implementation(project(":core:core_data"))
}