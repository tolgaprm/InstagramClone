package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.dependencyHandler.addModule
import com.prmto.convention.firebaseCommonDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidDataLayerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("instagram.android.library")
                apply("instagram.android.hilt")
            }

            extensions.configure<LibraryExtension> {
                commonDependenciesForEachModule(this)
                firebaseCommonDependencies(this)
                dependencies {
                    addModule(":core:core_domain")
                }
            }
        }
    }
}