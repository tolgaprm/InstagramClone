package com.prmto.share_domain.repository

import android.net.Uri
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.share_domain.model.Post

interface PostRepository {
    suspend fun sharePost(post: Post): SimpleResource

    suspend fun uploadImage(imageUri: Uri): Resource<String>
}