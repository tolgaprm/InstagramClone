package com.prmto.profile_domain.usecase

import javax.inject.Inject

class ValidateWebSiteUrlUseCase @Inject constructor() {
    operator fun invoke(websiteUrl: String): Boolean {
        return Regex("[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)").matches(
            websiteUrl
        )
    }
}