plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.core_domain"
}

dependencies {
    implementation(libs.firebase.storage.ktx)
}