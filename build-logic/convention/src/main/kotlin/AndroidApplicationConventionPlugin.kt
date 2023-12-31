import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.DefaultConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val properties = Properties().apply {
            load(target.rootProject.file("local.properties").inputStream())
        }

        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-parcelize")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                compileSdk = 34
                defaultConfig {
                    targetSdk = 33
                    testInstrumentationRunner =
                        "com.prmto.core_android_testing.runner.HiltTestRunner"
                    addFirebaseProjectIdToBuildConfig(properties)
                    addFirebaseApplicationIdToBuildConfig(properties)
                    addFirebaseApiKeyToBuildConfig(properties)
                }
            }
        }
    }

    private fun DefaultConfig.addFirebaseProjectIdToBuildConfig(
        properties: Properties
    ) {
        buildConfigField(
            "String",
            "FIREBASE_PROJECT_ID",
            "\"${properties.getProperty("FIREBASE_PROJECT_ID")}\""
        )
    }

    private fun DefaultConfig.addFirebaseApplicationIdToBuildConfig(
        properties: Properties
    ) {
        buildConfigField(
            "String",
            "FIREBASE_APPLICATION_ID",
            "\"${properties.getProperty("FIREBASE_APPLICATION_ID")}\""
        )
    }

    private fun DefaultConfig.addFirebaseApiKeyToBuildConfig(
        properties: Properties
    ) {
        buildConfigField(
            "String",
            "FIREBASE_API_KEY",
            "\"${properties.getProperty("FIREBASE_API_KEY")}\""
        )
    }
}