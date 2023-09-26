plugins {
    `kotlin-dsl`
}

group = "com.prmto.instagramclone.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.android.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "instagram.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "instagram.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "instagram.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidApplicationFirebase") {
            id = "instagram.android.application.firebase"
            implementationClass = "AndroidApplicationFirebaseConventionPlugin"
        }

        register("androidLibrary") {
            id = "instagram.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidHilt") {
            id = "instagram.android.hilt"
            implementationClass = "specific_library_plugin.AndroidHiltConventionPlugin"
        }

        register("androidRoom") {
            id = "instagram.android.room"
            implementationClass = "specific_library_plugin.AndroidRoomConventionPlugin"
        }

        register("androidDataLayer") {
            id = "instagram.android.layer.data"
            implementationClass = "layer_plugin.AndroidDataLayerConventionPlugin"
        }

        register("androidDomainLayer") {
            id = "instagram.android.layer.domain"
            implementationClass = "layer_plugin.AndroidDomainLayerConventionPlugin"
        }

        register("androidPresentationLayer") {
            id = "instagram.android.layer.presentation"
            implementationClass = "layer_plugin.AndroidPresentationLayerConventionPlugin"
        }
    }
}