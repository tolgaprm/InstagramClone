package specific_library_plugin

import com.prmto.convention.dependencyHandler.addImplementation
import com.prmto.convention.dependencyHandler.addKsp
import com.prmto.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            dependencies {
                addImplementation(libs.findLibrary("room.runtime").get())
                addImplementation(libs.findLibrary("room.ktx").get())
                add("annotationProcessor", libs.findLibrary("room.compiler").get())
                addKsp(libs.findLibrary("room.compiler").get())
            }
        }
    }
}