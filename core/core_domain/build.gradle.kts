plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    namespace = "com.prmto.core_domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)


    testImplementation(libs.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.truth.library)
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.truth.library)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.turbine)
    androidTestImplementation(libs.hilt.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
}
