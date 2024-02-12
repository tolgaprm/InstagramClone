package com.prmto.convention.dependencyHandlerExt.library

import com.prmto.convention.dependencyHandlerExt.implementation.addAndroidTestImplementation
import com.prmto.convention.dependencyHandlerExt.implementation.addDebugImplementation
import com.prmto.convention.dependencyHandlerExt.implementation.addImplementation
import com.prmto.convention.dependencyHandlerExt.implementation.addKsp
import com.prmto.convention.dependencyHandlerExt.implementation.addTestImplementation
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope


internal fun DependencyHandlerScope.hilt(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("dagger.hilt").get())
    addKsp(libs.findLibrary("dagger.hilt.compiler").get())
    addKsp(libs.findLibrary("hilt.compiler").get())
    addImplementation(libs.findLibrary("hilt.navigation.compose").get())
    addAndroidTestImplementation(libs.findLibrary("hilt.testing").get())
}

internal fun DependencyHandlerScope.room(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("room.runtime").get())
    addImplementation(libs.findLibrary("room.ktx").get())
    add("annotationProcessor", libs.findLibrary("room.compiler").get())
    addKsp(libs.findLibrary("room.compiler").get())
}

internal fun DependencyHandlerScope.composeBom(libs: VersionCatalog) {
    val bom = libs.findLibrary("compose-bom").get()
    addImplementation(platform(bom))
    addAndroidTestImplementation(platform(bom))
}

internal fun DependencyHandlerScope.compose(libs: VersionCatalog) {
    composeBom(libs)
    addImplementation(libs.findLibrary("lifecycle-runtime-compose").get())
    addImplementation(libs.findLibrary("activity-compose").get())
    addImplementation(libs.findLibrary("viewmodel-compose").get())
    composeMaterial3(libs)
    addDebugImplementation(libs.findLibrary("ui-tooling").get())
    addDebugImplementation(libs.findLibrary("ui-test-manifest").get())
    addImplementation(libs.findLibrary("ui").get())
    addImplementation(libs.findLibrary("ui-graphics").get())
    addImplementation(libs.findLibrary("ui-tooling-preview").get())
}

internal fun DependencyHandlerScope.composeUiTest(libs: VersionCatalog) {
    addAndroidTestImplementation(libs.findLibrary("ui-test-junit4").get())
    addAndroidTestImplementation(libs.findLibrary("espresso-core").get())
    addAndroidTestImplementation(libs.findLibrary("androidx-rules").get())
    addAndroidTestImplementation(libs.findLibrary("ui-automator").get())
}

private fun DependencyHandlerScope.composeMaterial3(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("material3").get())
    addImplementation(libs.findLibrary("icons-extended").get())
}

internal fun DependencyHandlerScope.composeNavigation(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("navigation.compose").get())
    addImplementation(libs.findLibrary("hilt.navigation.compose").get())
    addAndroidTestImplementation(libs.findLibrary("navigation-testing").get())
}

internal fun DependencyHandlerScope.coil(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("coil").get())
    addImplementation(libs.findLibrary("coil.svg").get())
}

internal fun DependencyHandlerScope.accompanistPermissions(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("accompanist-permissions").get())
}

internal fun DependencyHandlerScope.coreKtx(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("core-ktx").get())
}

internal fun DependencyHandlerScope.lifecycleRuntimeKtx(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("lifecycle-runtime-ktx").get())
}

internal fun DependencyHandlerScope.timber(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("timber").get())
}

internal fun DependencyHandlerScope.coroutines(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("coroutines-test").get())
    addAndroidTestImplementation(libs.findLibrary("coroutines-test").get())
}

internal fun DependencyHandlerScope.truth(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("truth-library").get())
    addAndroidTestImplementation(libs.findLibrary("truth-library").get())
}

internal fun DependencyHandlerScope.mockk(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("mockk").get())
    addAndroidTestImplementation(libs.findLibrary("mockk").get())
}

internal fun DependencyHandlerScope.turbine(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("turbine").get())
    // addAndroidTestImplementation(libs.findLibrary("turbine").get())
}

internal fun DependencyHandlerScope.junit(libs: VersionCatalog) {
    addTestImplementation(libs.findLibrary("junit").get())
}

internal fun DependencyHandlerScope.firebase(libs: VersionCatalog) {
    firebaseBom(libs)
    firebaseAuth(libs)
    addImplementation(libs.findLibrary("firebase.analytics.ktx").get())
    addImplementation(libs.findLibrary("play.services.auth").get())
    addImplementation(libs.findLibrary("firebase.firestore.ktx").get())
    addImplementation(libs.findLibrary("firebase-storage-ktx").get())
}

internal fun DependencyHandlerScope.firebaseBom(libs: VersionCatalog) {
    addImplementation(platform(libs.findLibrary("firebase.bom").get()))
}

internal fun DependencyHandlerScope.firebaseAuth(libs: VersionCatalog) {
    addImplementation(libs.findLibrary("firebase.auth.ktx").get())
}