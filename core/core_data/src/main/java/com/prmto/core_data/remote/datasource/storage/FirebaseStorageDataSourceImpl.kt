package com.prmto.core_data.remote.datasource.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.prmto.core_data.common.FirebaseCollectionNames.PROFILE_IMAGES
import com.prmto.core_data.common.safeCallWithTryCatch
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseStorageDataSourceImpl @Inject constructor(
    private val storage: FirebaseStorage
) : StorageDataSource {
    override suspend fun updatedProfileImage(
        photoUri: Uri,
    ): Resource<String> {
        return safeCallWithTryCatch {
            val storageRef = storage.reference
            val imagesRef = storageRef.child(PROFILE_IMAGES)
            val fileName = imagesRef.child("${UUID.randomUUID()}.jpg")
            val taskResult = fileName.putFile(photoUri).await()
            return@safeCallWithTryCatch if (taskResult.task.isSuccessful) {
                val photoUrl = taskResult.storage.downloadUrl.await().toString()
                Resource.Success(photoUrl)
            } else {
                Resource.Error(UiText.unknownError())
            }
        }
    }
}