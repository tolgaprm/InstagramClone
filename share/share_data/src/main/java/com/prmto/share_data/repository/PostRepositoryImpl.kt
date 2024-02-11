package com.prmto.share_data.repository

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.share_data.remote.datasource.post.PostRemoteDataSource
import com.prmto.share_data.remote.mapper.toPostDto
import com.prmto.share_domain.model.Post
import com.prmto.share_domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostRepository {
    override suspend fun sharePost(post: Post): SimpleResource {
        return postRemoteDataSource.sharePost(postDto = post.toPostDto())
    }

    override suspend fun uploadImage(imageUri: String): Resource<String> {
        return postRemoteDataSource.uploadImage(imageUri = imageUri)
    }
}