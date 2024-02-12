import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule
import com.prmto.convention.dependencyHandlerExt.module.coreTestingModule

plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.share_domain"
}

dependencies {
    coreDomainModule()
    coreTestingModule()
}