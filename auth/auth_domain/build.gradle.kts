import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule

plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.auth_domain"
}

dependencies {
    coreDomainModule()
}