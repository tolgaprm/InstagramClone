import com.prmto.convention.dependencyHandlerExt.module.coreDomainModule

plugins {
    id("instagram.android.layer.domain")
}

android {
    namespace = "com.prmto.profile_domain"
}

dependencies {
    coreDomainModule()
}