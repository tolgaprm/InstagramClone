import com.prmto.convention.dependencyHandlerExt.module.coreDataModule

plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.home_data"
}

dependencies {
    coreDataModule()
}