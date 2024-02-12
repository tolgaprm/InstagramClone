import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule

plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.core_testing"
}

dependencies {
    coreDomainModule()
    implementation(libs.coroutines.test)
    implementation(libs.junit)
    testImplementation(libs.truth.library)
}