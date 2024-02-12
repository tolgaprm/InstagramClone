package layer_plugin

import com.android.build.gradle.LibraryExtension
import com.prmto.convention.commonDependenciesForEachModule
import com.prmto.convention.dependencyHandlerExt.library.firebase
import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule
import com.prmto.convention.libs
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

            dependencies {
                firebase(libs)
            }
            extensions.configure<LibraryExtension> {
                commonDependenciesForEachModule(this)
                dependencies {
                    coreDomainModule()
                }
            }
        }
    }
}