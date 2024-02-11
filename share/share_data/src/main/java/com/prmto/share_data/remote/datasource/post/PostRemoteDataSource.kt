package com.prmto.share_data.remote.datasource.post

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.share_data.remote.model.PostDto

interface PostRemoteDataSource {
    suspend fun sharePost(postDto: PostDto): SimpleResource

    suspend fun uploadImage(imageUri: String): Resource<String>
}