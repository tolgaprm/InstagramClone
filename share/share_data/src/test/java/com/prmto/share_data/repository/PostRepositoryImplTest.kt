package com.prmto.share_data.repository

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.Resource
import com.prmto.share_data.remote.datasource.post.FakePostRemoteDataSource
import com.prmto.share_domain.model.Post
import com.prmto.share_domain.repository.PostRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class PostRepositoryImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var postRemoteDataSource: FakePostRemoteDataSource

    @Before
    fun setUp() {
        postRemoteDataSource = FakePostRemoteDataSource()
        postRepository = PostRepositoryImpl(postRemoteDataSource = postRemoteDataSource)
    }

    @Test
    fun `sharePost should return success`() = runTest {
        postRemoteDataSource.isReturnError = false
        val result = postRepository.sharePost(
            post = Post(
                caption = "",
                imageUrls = emptyList(),
                date = LocalDateTime.now(),
                userId = ""
            )
        )
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isEqualTo(Unit)
    }

    @Test
    fun `sharePost should return error`() = runTest {
        postRemoteDataSource.isReturnError = true
        val result = postRepository.sharePost(
            post = Post(
                caption = "",
                imageUrls = emptyList(),
                date = LocalDateTime.now(),
                userId = ""
            )
        )
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `uploadImage should return success`() = runTest {
        postRemoteDataSource.isReturnError = false
        val result =
            postRepository.uploadImage(imageUri = "content://media/external/images/media/image.jpg")
        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isEqualTo("https://instaclone/postImages/image.jpg")
    }

    @Test
    fun `uploadImage should return error`() = runTest {
        postRemoteDataSource.isReturnError = true
        val result =
            postRepository.uploadImage(imageUri = "content://media/external/images/media/12")
        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }
}