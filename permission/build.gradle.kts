import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule
import com.prmto.convention.dependencyHandlerExt.module.corePresentationModule

plugins {
    id("instagram.android.library.compose")
    id("instagram.android.library")
}

android {
    namespace = "com.prmto.permission"
}

dependencies {
    corePresentationModule()
    coreDomainModule()
}