package com.prmto.convention.dependencyHandlerExt.module

import com.prmto.convention.dependencyHandlerExt.implementation.addModule
import org.gradle.kotlin.dsl.DependencyHandlerScope

// Core
fun DependencyHandlerScope.coreDataModule() {
    addModule(":core:core_data")
}

fun DependencyHandlerScope.coreDomainModule() {
    addModule(":core:core_domain")
}

fun DependencyHandlerScope.corePresentationModule() {
    addModule(":core:core_presentation")
}

fun DependencyHandlerScope.coreFeature() {
    coreDataModule()
    coreDomainModule()
    corePresentationModule()
    coreTestingModule()
    coreAndroidTestingModule()
}

fun DependencyHandlerScope.coreTestingModule() {
    addModule(":core:core_testing")
}

fun DependencyHandlerScope.coreAndroidTestingModule() {
    addModule(":core:core_android_testing")
}

// Home
fun DependencyHandlerScope.homeDataModule() {
    addModule(":home:home_data")
}

fun DependencyHandlerScope.homePresentationModule() {
    addModule(":home:home_presentation")
}

fun DependencyHandlerScope.homeFeature() {
    homeDataModule()
    homePresentationModule()
}

// Auth
fun DependencyHandlerScope.authDataModule() {
    addModule(":auth:auth_data")
}

fun DependencyHandlerScope.authDomainModule() {
    addModule(":auth:auth_domain")
}

fun DependencyHandlerScope.authPresentationModule() {
    addModule(":auth:auth_presentation")
}

fun DependencyHandlerScope.authFeature() {
    authDataModule()
    authDomainModule()
    authPresentationModule()
}

//Camera
fun DependencyHandlerScope.cameraFeature() {
    addModule(":camera")
}

//Permission
fun DependencyHandlerScope.permissionFeature() {
    addModule(":permission")
}

//Profile
fun DependencyHandlerScope.profileDataModule() {
    addModule(":profile:profile_data")
}

fun DependencyHandlerScope.profileDomainModule() {
    addModule(":profile:profile_domain")
}

fun DependencyHandlerScope.profilePresentationModule() {
    addModule(":profile:profile_presentation")
}

fun DependencyHandlerScope.profileFeature() {
    profileDataModule()
    profileDomainModule()
    profilePresentationModule()
}

//Reels
fun DependencyHandlerScope.reelsPresentationModule() {
    addModule(":reels:reels_presentation")
}

fun DependencyHandlerScope.reelsFeature() {
    reelsPresentationModule()
}

//Share
fun DependencyHandlerScope.shareDataModule() {
    addModule(":share:share_data")
}

fun DependencyHandlerScope.shareDomainModule() {
    addModule(":share:share_domain")
}

fun DependencyHandlerScope.sharePresentationModule() {
    addModule(":share:share_presentation")
}

fun DependencyHandlerScope.shareFeature() {
    shareDataModule()
    shareDomainModule()
    sharePresentationModule()
}

//Search
fun DependencyHandlerScope.searchPresentationModule() {
    addModule(":search:search_presentation")
}

fun DependencyHandlerScope.searchFeature() {
    searchPresentationModule()
}