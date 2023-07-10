plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.prmto.instagramclone"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.prmto.instagramclone"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":home:home_data"))
    implementation(project(":home:home_presentation"))
    implementation(project(":core:core_presentation"))
    implementation(project(":share:share_presentation"))
    implementation(project(":profile:profile_presentation"))
    implementation(project(":search:search_presentation"))
    implementation(project(":reels:reels_presentation"))
    implementation(project(":auth:auth_presentation"))
    implementation(project(":auth:auth_data"))
    implementation(project(":auth:auth_domain"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.viewmodel.compose)
    implementation(libs.accompanist.systemuicontroller)


    implementation(libs.timber)

    // Navigation
    implementation(libs.navigation.compose)

    //dagger - hilt
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // coroutines for getting off the UI thread
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)

    // Work Manager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)

    // DataStore
    implementation(libs.datastore.preferences)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.svg)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.firestore.ktx)


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
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}