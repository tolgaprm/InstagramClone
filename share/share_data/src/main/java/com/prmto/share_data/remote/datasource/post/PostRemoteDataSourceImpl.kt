package com.prmto.share_data.remote.datasource.post

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.prmto.core_data.common.safeCallWithTryCatch
import com.prmto.core_data.remote.datasource.storage.uploadImage
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.share_data.common.CollectionNames
import com.prmto.share_data.remote.model.PostDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : PostRemoteDataSource {
    override suspend fun sharePost(postDto: PostDto): SimpleResource {
        return safeCallWithTryCatch {
            firestore.collection(CollectionNames.POST_COLLECTION_NAME)
                .add(postDto)
                .await()
            Resource.Success(Unit)
        }
    }

    override suspend fun uploadImage(imageUri: String): Resource<String> {
        return storage.uploadImage(Uri.parse(imageUri), CollectionNames.POST_IMAGES_COLLECTION)
    }
}