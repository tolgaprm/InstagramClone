package com.prmto.share_domain.repository

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.share_domain.model.Post

class FakePostRepository : PostRepository {

    var isReturnError = false
    override suspend fun sharePost(post: Post): SimpleResource {
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
            Resource.Success("postImages/${imageUri.substringAfterLast("/")}.jpg")
        }
    }
}