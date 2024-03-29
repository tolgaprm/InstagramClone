package com.prmto.convention.dependencyHandlerExt.implementation

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

internal fun DependencyHandlerScope.addImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("implementation", dependency)
}

internal fun DependencyHandlerScope.addModule(path: String) {
    add("implementation", project(path))
}

internal fun DependencyHandlerScope.addTestImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", dependency)
}

internal fun DependencyHandlerScope.addAndroidTestImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", dependency)
}

internal fun DependencyHandlerScope.addKsp(dependency: Provider<MinimalExternalModuleDependency>) {
    add("ksp", dependency)
}

internal fun DependencyHandlerScope.addDebugImplementation(dependency: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", dependency)
}