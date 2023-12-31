package specific_library_plugin

import com.prmto.convention.dependencyHandler.addAndroidTestImplementation
import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKsp
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("dagger.hilt.android.plugin")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                addImplementation(libs.findLibrary("dagger.hilt").get())
                addKsp(libs.findLibrary("dagger.hilt.compiler").get())
                addKsp(libs.findLibrary("hilt.compiler").get())
                addImplementation(libs.findLibrary("hilt.navigation.compose").get())
                addAndroidTestImplementation(libs.findLibrary("hilt.testing").get())
            }
        }
    }
}