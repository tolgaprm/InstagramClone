plugins {
    id("instagram.android.application")
    id("instagram.android.application.compose")
    id("instagram.android.hilt")
    id("instagram.android.room")
    id("instagram.android.application.firebase")
}

android {
    namespace = "com.prmto.instagramclone"
    defaultConfig {
        applicationId = "com.prmto.instagramclone"
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

dependencies {
    implementation(libs.bundles.ui.related)
    implementation(libs.splash)
    implementation(libs.timber)

    implementation(libs.bundles.cameraX)

    implementation(libs.ucrop)

    // Navigation
    implementation(libs.navigation.compose)

    // Work Manager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)

    // DataStore
    implementation(libs.datastore.preferences)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.svg)

    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.truth.library)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(project(":core:core_testing"))
    androidTestImplementation(project(":core:core_android_testing"))
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.ui.automator)
    kspAndroidTest(libs.hilt.compiler)
}