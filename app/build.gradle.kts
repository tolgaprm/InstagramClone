import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    alias(libs.plugins.com.google.devtools.ksp)
}
var properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
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
        buildConfigField(
            "String",
            "FIREBASE_PROJECT_ID",
            "\"${properties.getProperty("FIREBASE_PROJECT_ID")}\""
        )
        buildConfigField(
            "String",
            "FIREBASE_APPLICATION_ID",
            "\"${properties.getProperty("FIREBASE_APPLICATION_ID")}\""
        )
        buildConfigField(
            "String",
            "FIREBASE_API_KEY",
            "\"${properties.getProperty("FIREBASE_API_KEY")}\""
        )
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
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
    implementation(project(":core:core_domain"))
    implementation(project(":core:core_data"))
    implementation(project(":share:share_presentation"))
    implementation(project(":profile:profile_presentation"))
    implementation(project(":search:search_presentation"))
    implementation(project(":reels:reels_presentation"))
    implementation(project(":auth:auth_presentation"))
    implementation(project(":auth:auth_data"))
    implementation(project(":auth:auth_domain"))

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.ui.related)
    implementation(libs.splash)
    implementation(libs.timber)

    // Navigation
    implementation(libs.navigation.compose)

    //dagger - hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // coroutines for getting off the UI thread
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)
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

    testImplementation(libs.bundles.test)

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