package com.prmto.core_domain.repository.storage

import android.net.Uri
import com.prmto.core_domain.constants.Resource

interface StorageRepository {
    suspend fun updatedProfileImage(photoUri: Uri): Resource<String>
}