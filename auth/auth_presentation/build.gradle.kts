import com.prmto.convention.dependencyHandlerExt.module.authDomainModule
import com.prmto.convention.dependencyHandlerExt.module.corePresentationModule

plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.auth_presentation"
}

dependencies {
    corePresentationModule()
    authDomainModule()
}