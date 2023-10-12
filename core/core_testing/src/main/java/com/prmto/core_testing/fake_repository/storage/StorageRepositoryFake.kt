package com.prmto.core_testing.fake_repository.storage

import android.net.Uri
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.storage.StorageRepository

class StorageRepositoryFake : StorageRepository {
    var isReturnError = false
    override suspend fun updatedProfileImage(photoUri: Uri): Resource<String> {
        if (isReturnError) {
            return Resource.Error(UiText.unknownError())
        }
        return Resource.Success("https://www.imageurl.com")
    }
}