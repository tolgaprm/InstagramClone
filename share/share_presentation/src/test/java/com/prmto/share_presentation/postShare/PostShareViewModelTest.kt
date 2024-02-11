package com.prmto.share_presentation.postShare

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.share_domain.repository.FakePostRepository
import com.prmto.share_domain.usecase.PostShareUseCase
import com.prmto.share_presentation.R
import com.prmto.share_presentation.postShare.navigation.args.PostShareArgs
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostShareViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PostShareViewModel
    private lateinit var postShareUseCase: PostShareUseCase
    private lateinit var repository: FakePostRepository
    private lateinit var firebaseAuthCoreRepository: FirebaseAuthCoreRepository

    @Before
    fun setUp() {
        firebaseAuthCoreRepository = mockk(relaxed = true)
        repository = FakePostRepository()
        postShareUseCase = PostShareUseCase(
            postRepository = repository,
            firebaseAuthCoreRepository = firebaseAuthCoreRepository
        )
        val savedStateHandle = SavedStateHandle(
            mapOf(
                PostShareArgs.postShareArgsPhotoUris to arrayOf("content://uri1,content://uri2")
            )
        )
        viewModel = PostShareViewModel(
            savedStateHandle = savedStateHandle,
            postShareUseCase = postShareUseCase
        )
    }

    @Test
    fun `viewModelInit selectedPostImageUris stateUpdated`() = runTest {
        val expected = listOf("content://uri1", "content://uri2")
        assertThat(viewModel.uiState.value.selectedPostImageUris).isEqualTo(expected)
    }

    @Test
    fun `when event is OnCaptionChanged then caption stateUpdated`() = runTest {
        val caption = "The caption for the post."
        viewModel.onEvent(PostShareEvent.OnCaptionChanged(caption))
        assertThat(viewModel.uiState.value.caption).isEqualTo(caption)
    }

    @Test
    fun `when event is OnPostShareClicked and return success from postShareUseCases, update state properly`() =
        runTest {
            viewModel.onEvent(PostShareEvent.OnPostShareClicked)
            viewModel.uiState.test {
                awaitItem() // initial state
                assertThat(awaitItem().isPostUploading).isTrue()
                assertThat(awaitItem().isPostUploading).isFalse()
            }

            viewModel.consumableViewEvents.test {
                assertThat(awaitItem()).isEqualTo(
                    listOf(
                        UiEvent.ShowMessage(
                            UiText.StringResource(R.string.post_shared_successfully)
                        ),
                        UiEvent.Navigate(Screen.Home.route)
                    )
                )
            }
        }

    @Test
    fun `when event is OnPostShareClicked and return error from postShareUseCases, update state properly`() =
        runTest {
            repository.isReturnError = true
            viewModel.onEvent(PostShareEvent.OnPostShareClicked)
            viewModel.uiState.test {
                awaitItem() // initial state
                assertThat(awaitItem().isPostUploading).isTrue()
                assertThat(awaitItem().isPostUploading).isFalse()
            }

            viewModel.consumableViewEvents.test {
                assertThat(awaitItem()).isEqualTo(
                    listOf(
                        UiEvent.ShowMessage(UiText.DynamicString("Error sharing post"))
                    )
                )
            }
        }
}