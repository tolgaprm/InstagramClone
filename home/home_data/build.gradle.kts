@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.library")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    //   id("com.google.gms.google-services")
    alias(libs.plugins.com.google.devtools.ksp)
}

android {
    namespace = "com.prmto.home_data"
    compileSdk = 33
}

dependencies {
    implementation(libs.timber)

    //dagger - hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.hilt.compiler)


    testImplementation(libs.bundles.test)

    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth.library)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
}
