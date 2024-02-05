package com.repository

import android.net.Uri
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import com.prmto.profile_domain.repository.ProfileRepository

class FakeProfileRepository : ProfileRepository {
    var isReturnError = false
    override suspend fun updateProfileImage(photoUri: Uri): Resource<String> {
        if (isReturnError) {
            return Resource.Error(UiText.unknownError())
        }
        return Resource.Success("https://www.imageurl.com")
    }
}