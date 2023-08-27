@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.prmto.core_domain"
    compileSdk = 33
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)

    implementation(libs.core.ktx)

    implementation(libs.timber)

    //dagger - hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)


    testImplementation(libs.bundles.test)

    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth.library)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
}