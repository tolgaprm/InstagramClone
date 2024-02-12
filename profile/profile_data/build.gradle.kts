import com.prmto.convention.dependencyHandlerExt.module.coreDataModule
import com.prmto.convention.dependencyHandlerExt.module.profileDomainModule

plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.profile_data"
}

dependencies {
    coreDataModule()
    profileDomainModule()
}