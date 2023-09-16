plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.prmto.core_data"
    compileSdk = 33
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":core:core_domain"))

    implementation(libs.coroutines.core)
    implementation(libs.bundles.test)
}