package com.prmto.share_domain.usecase

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.share_domain.R
import com.prmto.share_domain.model.Post
import com.prmto.share_domain.repository.PostRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class PostShareUseCase @Inject constructor(
    private val postRepository: PostRepository,
    private val firebaseAuthCoreRepository: FirebaseAuthCoreRepository,
) {

    suspend operator fun invoke(
        selectedPostImageUris: List<String>,
        caption: String
    ): SimpleResource {
        if (selectedPostImageUris.isEmpty()) {
            return Resource.Error(
                UiText.StringResource(
                    R.string.no_image_selected
                )
            )
        }

        val currentUserId = firebaseAuthCoreRepository.currentUser()?.uid ?: return Resource.Error(
            UiText.StringResource(
                R.string.user_not_logged
            )
        )

        if (selectedPostImageUris.size == 1) {
            return sharePostWithOneImage(
                postImageUri = selectedPostImageUris.first(),
                caption = caption,
                currentUserId = currentUserId
            )
        }

        return sharePostWithMultipleImages(
            selectedPostImageUris = selectedPostImageUris,
            caption = caption,
            currentUserId = currentUserId
        )
    }


    private suspend fun sharePostWithOneImage(
        postImageUri: String,
        caption: String,
        currentUserId: String
    ): SimpleResource {
        val imageUploadResult = postRepository.uploadImage(imageUri = postImageUri)

        return when (imageUploadResult) {
            is Resource.Error -> Resource.Error(imageUploadResult.uiText)
            is Resource.Success -> {
                val uploadedImageUrl = imageUploadResult.data
                val post = Post(
                    caption = caption,
                    imageUrls = listOf(uploadedImageUrl),
                    userId = currentUserId
                )

                postRepository.sharePost(post)
            }
        }
    }

    private suspend fun sharePostWithMultipleImages(
        selectedPostImageUris: List<String>,
        caption: String,
        currentUserId: String
    ): SimpleResource {
        return coroutineScope {
            val deferredUploadedImageUris = selectedPostImageUris.map { imageUri ->
                async {
                    when (val result = postRepository.uploadImage(imageUri = imageUri)) {
                        is Resource.Error -> ""
                        is Resource.Success -> result.data
                    }
                }
            }

            val uploadedImageUris = deferredUploadedImageUris.map { it.await() }

            val post = Post(
                caption = caption,
                imageUrls = uploadedImageUris,
                userId = currentUserId
            )

            postRepository.sharePost(post)
        }
    }
}