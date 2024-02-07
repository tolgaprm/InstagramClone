package com.prmto.share_data.remote.datasource.post

import android.net.Uri
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.share_data.remote.model.PostDto

interface PostRemoteDataSource {
    suspend fun sharePost(postDto: PostDto): SimpleResource

    suspend fun uploadImage(imageUri: Uri): Resource<String>
}