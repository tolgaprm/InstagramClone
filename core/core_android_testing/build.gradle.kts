import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule
import com.prmto.convention.dependencyHandlerExt.module.coreTestingModule

plugins {
    id("instagram.android.layer.domain")
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.core_android_testing"
}

dependencies {
    coreDomainModule()
    coreTestingModule()
    implementation(libs.datastore.preferences)
    implementation(libs.coroutines.test)
    implementation(libs.junit)
    implementation(libs.androidx.runner)
    implementation(libs.hilt.testing)
}