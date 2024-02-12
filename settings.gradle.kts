pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "InstagramClone"
include(":app")
// Core
include(":core:core_data")
include(":core:core_domain")
include(":core:core_presentation")
include(":core:core_testing")
include(":core:core_android_testing")
// Home
include(":home:home_data")
include(":home:home_presentation")
// Auth
include(":auth:auth_data")
include(":auth:auth_domain")
include(":auth:auth_presentation")
// Camera
include(":camera")
// Permission
include(":permission")
// Profile
include(":profile:profile_data")
include(":profile:profile_domain")
include(":profile:profile_presentation")
// Reels
include(":reels:reels_presentation")
// Share
include(":share:share_data")
include(":share:share_domain")
include(":share:share_presentation")
// Search
include(":search:search_presentation")