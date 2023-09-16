plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.core_data"
}

dependencies {
    implementation(libs.datastore.preferences)
}