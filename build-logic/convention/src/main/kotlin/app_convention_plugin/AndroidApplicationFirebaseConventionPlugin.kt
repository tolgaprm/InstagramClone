package app_convention_plugin

import com.prmto.convention.dependencyHandlerExt.library.firebase
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationFirebaseConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.gms.google-services")

            dependencies {
                firebase(libs)
            }
        }
    }
}