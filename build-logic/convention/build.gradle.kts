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
            implementationClass = "app_convention_plugin.AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "instagram.android.application.compose"
            implementationClass = "app_convention_plugin.AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "instagram.android.library.compose"
            implementationClass = "library_convention_plugin.AndroidLibraryComposeConventionPlugin"
        }

        register("androidApplicationFirebase") {
            id = "instagram.android.application.firebase"
            implementationClass = "app_convention_plugin.AndroidApplicationFirebaseConventionPlugin"
        }

        register("androidLibrary") {
            id = "instagram.android.library"
            implementationClass = "library_convention_plugin.AndroidLibraryConventionPlugin"
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