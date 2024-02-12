import com.prmto.convention.dependencyHandlerExt.module.authDomainModule
import com.prmto.convention.dependencyHandlerExt.module.coreDataModule

plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.auth_data"
}

dependencies {
    authDomainModule()
    coreDataModule()
}