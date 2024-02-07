package com.prmto.share_domain.repository

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.share_domain.model.Post

interface PostRepository {
    suspend fun sharePost(post: Post): SimpleResource

    suspend fun uploadImage(imageUri: String): Resource<String>
}