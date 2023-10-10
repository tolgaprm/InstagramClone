package com.prmto.core_data.repository.storage

import android.net.Uri
import com.prmto.core_data.remote.datasource.storage.StorageDataSource
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.repository.storage.StorageRepository
import javax.inject.Inject

class FirebaseStorageRepositoryImpl @Inject constructor(
    private val storageDataSource: StorageDataSource
) : StorageRepository {
    override suspend fun updatedProfileImage(photoUri: Uri): Resource<String> {
        return storageDataSource.updatedProfileImage(photoUri = photoUri)
    }
}