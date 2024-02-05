package com.prmto.profile_data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.prmto.core_data.remote.datasource.storage.uploadImage
import com.prmto.core_domain.constants.Resource
import com.prmto.profile_domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : ProfileRepository {
    override suspend fun updateProfileImage(
        photoUri: Uri
    ): Resource<String> {
        return storage.uploadImage(photoUri = photoUri, path = PROFILE_IMAGES_COLLECTION_NAME)
    }

    companion object {
        private const val PROFILE_IMAGES_COLLECTION_NAME = "ProfileImages"
    }
}