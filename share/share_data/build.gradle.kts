import com.prmto.convention.dependencyHandlerExt.module.coreDataModule
import com.prmto.convention.dependencyHandlerExt.module.shareDomainModule

plugins {
    id("instagram.android.layer.data")
}

android {
    namespace = "com.prmto.share_data"
}

dependencies {
    coreDataModule()
    shareDomainModule()
}