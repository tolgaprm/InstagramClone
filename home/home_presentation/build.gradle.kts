import com.prmto.convention.dependencyHandlerExt.module.corePresentationModule

plugins {
    id("instagram.android.library.compose")
    id("instagram.android.layer.presentation")
}

android {
    namespace = "com.prmto.home_presentation"
}

dependencies {
    corePresentationModule()
}