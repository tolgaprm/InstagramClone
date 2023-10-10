package com.prmto.core_data.remote.datasource.storage

import android.net.Uri
import com.prmto.core_domain.constants.Resource

interface StorageDataSource {

    suspend fun updatedProfileImage(photoUri: Uri): Resource<String>
}