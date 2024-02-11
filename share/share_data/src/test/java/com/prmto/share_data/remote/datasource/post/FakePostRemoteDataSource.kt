package com.prmto.share_data.remote.datasource.post

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.share_data.remote.model.PostDto

class FakePostRemoteDataSource : PostRemoteDataSource {

    var isReturnError = false

    private val imageUrl = "https://instaclone/postImages"

    override suspend fun sharePost(postDto: PostDto): SimpleResource {
        return if (isReturnError) {
            Resource.Error(UiText.DynamicString("Error sharing post"))
        } else {
            Resource.Success(Unit)
        }
    }

    override suspend fun uploadImage(imageUri: String): Resource<String> {
        return if (isReturnError) {
            Resource.Error(UiText.DynamicString("Error uploading image"))
        } else {
            Resource.Success(imageUrl + "/${imageUri.substringAfterLast("/")}")
        }
    }
}