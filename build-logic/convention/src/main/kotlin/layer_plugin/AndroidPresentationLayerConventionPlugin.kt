package layer_plugin

import com.android.build.api.dsl.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.commonPresentationLayerDependencies
import com.prmto.convention.dependencyHandler.addModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidPresentationLayerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("instagram.android.library")
                apply("instagram.android.hilt")
            }
            val extension = extensions.getByType<LibraryExtension>()
            extension.apply {
                commonPresentationLayerDependencies(this)
                commonDependenciesForEachModule(this)
                dependencies {
                    addModule(":core:core_domain")
                    "testImplementation"(project(":core:core_testing"))
                    "androidTestImplementation"(project(":core:core_testing"))
                    "androidTestImplementation"(project(":core:core_android_testing"))
                }
            }
        }
    }
}
