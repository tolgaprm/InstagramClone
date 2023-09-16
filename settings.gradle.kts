pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "InstagramClone"
include(":app")
include(":home:home_data")
include(":home:home_presentation")
include(":core")
include(":core:core_presentation")
include(":share")
include(":share:share_presentation")
include(":profile")
include(":profile:profile_presentation")
include(":search")
include(":search:search_presentation")
include(":reels")
include(":reels:reels_presentation")
include(":auth")
include(":auth:auth_presentation")
include(":auth:auth_data")
include(":auth:auth_domain")
include(":core:core_domain")
include(":core:core_data")
include(":core:core_testing")