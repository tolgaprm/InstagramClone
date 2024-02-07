package com.prmto.share_domain.usecase

import android.net.Uri
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.share_domain.R
import com.prmto.share_domain.repository.FakePostRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostShareUseCaseTest {

    private lateinit var postShareUseCase: PostShareUseCase
    private lateinit var postRepository: FakePostRepository
    private lateinit var firebaseAuthCoreRepository: FirebaseAuthCoreRepository

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        firebaseAuthCoreRepository = mockk(relaxed = true)
        postRepository = FakePostRepository()
        postShareUseCase = PostShareUseCase(
            postRepository = postRepository,
            firebaseAuthCoreRepository = firebaseAuthCoreRepository
        )
    }

    @Test
    fun `when selectedPostImageUris is empty, then return Resource Error`() = runTest {
        val result = postShareUseCase(
            selectedPostImageUris = emptyList(),
            caption = "caption"
        )

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).uiText).isEqualTo(UiText.StringResource(R.string.no_image_selected))
    }

    @Test
    fun `when user not logged in, then return Resource Error`() = runTest {
        every { Uri.parse(any()).lastPathSegment } returns "uri"
        every { firebaseAuthCoreRepository.currentUser() } returns null

        val result = postShareUseCase(
            selectedPostImageUris = listOf(
                "content://media/external/images/media/1"
            ),
            caption = "caption"
        )

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat((result as Resource.Error).uiText).isEqualTo(UiText.StringResource(R.string.user_not_logged))
    }
}