package com.prmto.share_presentation.postGallery

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.usecase.GetAlbumAndCoverImagesUseCase
import com.prmto.core_domain.usecase.GetImageUrisByFirstAlbumNameUseCase
import com.prmto.core_testing.dispatcher.TestDispatcher
import com.prmto.core_testing.fake_repository.common.MediaAlbumProviderFake
import com.prmto.core_testing.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostGalleryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PostGalleryViewModel
    private lateinit var mediaAlbumProvider: MediaAlbumProviderFake

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        mediaAlbumProvider = MediaAlbumProviderFake()
        val dispatchProvider = TestDispatcher()
        val getImageUrisByFirstAlbumNameUseCase =
            GetImageUrisByFirstAlbumNameUseCase(mediaAlbumProvider)
        val getAlbumAndCoverImagesUseCase = GetAlbumAndCoverImagesUseCase(mediaAlbumProvider)
        viewModel = PostGalleryViewModel(
            getImageUrisByFirstAlbumNameUseCase = getImageUrisByFirstAlbumNameUseCase,
            dispatcherProvider = dispatchProvider,
            getAlbumAndCoverImagesUseCase = getAlbumAndCoverImagesUseCase,
            mediaAlbumProvider = mediaAlbumProvider
        )

        every { Uri.parse(any()) } returns mockk {
            every { lastPathSegment } returns "uri"
        }
    }

    @Test
    fun whenViewModelEventAllPermissionGranted_getImageUrisByFirstAlbumNameUseCase_stateUpdated() =
        runTest {
            setMockUri()
            mediaAlbumProvider.albumNames = listOf("album1", "album2")
            mediaAlbumProvider.albumsAndUris = mapOf(
                "album1" to listOf(Uri.parse("uri1"), Uri.parse("uri2")),
                "album2" to listOf((Uri.parse("uri3")), Uri.parse("uri4"))
            )
            viewModel.onEvent(PostGalleryEvent.AllPermissionGranted)
            advanceUntilIdle()
            viewModel.uiState.test {
                val uiState = awaitItem()
                assertThat(uiState.mediaAlbumNames).isEqualTo(listOf("album1", "album2"))
                assertThat(uiState.urisInSelectedAlbum).isEqualTo(
                    listOf(
                        Uri.parse("uri1"),
                        Uri.parse("uri2")
                    )
                )
                assertThat(uiState.selectedAlbumName).isEqualTo("album1")
                assertThat(uiState.selectedImageUri).isEqualTo(Uri.parse("uri1"))
            }
        }

    @Test
    fun whenEventOnClickedMultipleSelection_stateUpdated() = runTest {
        viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)

        viewModel.uiState.test {
            assertThat(awaitItem().isActiveMultipleSelection).isTrue()
            viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
            assertThat(awaitItem().isActiveMultipleSelection).isFalse()
        }
    }

    @Test
    fun whenEventOnClickedMultipleSelection_MultipleSelectionModeEnabled_ThenSelectedUrisInEnabledMultipleSelectMode_updateEmptyList() =
        runTest {
            viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
            viewModel.onEvent(PostGalleryEvent.OnClickImageItem(Uri.parse("uri")))
            viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton) // enable multiple selection mode
            viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
            //This event is to disable multiple selection mode, but before this is updated in the viewModel
            // we make the SelectedUrisInEnabledMultipleSelectMode variable empty

            viewModel.uiState.test {
                assertThat(awaitItem().selectedUrisInEnabledMultipleSelectMode).isEmpty()
            }
        }

    @Test
    fun whenEventOnImageCropped_ThenActiveMultipleSelection_stateUpdated() = runTest {
        viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
        viewModel.onEvent(PostGalleryEvent.OnImageCropped(Uri.parse("uri")))

        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.croppedImageUris).isNotEmpty()
            assertThat(uiState.croppedImageUri).isNull()
        }
    }

    @Test
    fun whenEventOnImageCropped_ThenDisableMultipleSelection_stateUpdated() = runTest {
        viewModel.onEvent(PostGalleryEvent.OnImageCropped(Uri.parse("uri")))

        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.croppedImageUris).isEmpty()
            assertThat(uiState.croppedImageUri).isNotNull()
        }
    }

    @Test
    fun whenEventOnClickImageItem_ThenDisableMultipleSelection_stateUpdated() = runTest {
        viewModel.onEvent(PostGalleryEvent.OnClickImageItem(Uri.parse("uri")))
        viewModel.uiState.test {
            assertThat(awaitItem().selectedImageUri).isEqualTo(Uri.parse("uri"))
        }
    }

    @Test
    fun whenEventOnClickImageItem_ThenActiveMultipleSelection_stateUpdated() = runTest {
        viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
        viewModel.onEvent(PostGalleryEvent.OnClickImageItem(Uri.parse("uri")))
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.selectedImageUri).isEqualTo(Uri.parse("uri"))
            assertThat(uiState.selectedUrisInEnabledMultipleSelectMode).contains(Uri.parse("uri"))
        }
    }

    @Test
    fun whenEventOnClickImageItem_ThenActiveMultipleSelectionAndExistsInSelectedUris_stateUpdated() =
        runTest {
            viewModel.onEvent(PostGalleryEvent.OnClickMultipleSelectButton)
            viewModel.onEvent(PostGalleryEvent.OnClickImageItem(Uri.parse("uri")))
            viewModel.onEvent(PostGalleryEvent.OnClickImageItem(Uri.parse("uri")))
            viewModel.uiState.test {
                val uiState = awaitItem()
                assertThat(uiState.selectedImageUri).isEqualTo(Uri.parse("uri"))
                assertThat(uiState.selectedUrisInEnabledMultipleSelectMode).doesNotContain(
                    Uri.parse("uri")
                )
                assertThat(uiState.selectedUrisInEnabledMultipleSelectMode).isEmpty()
            }
        }

    private fun setMockUri() {
        every { Uri.parse("uri1") } returns mockk {
            every { lastPathSegment } returns "uri1"
        }
        every { Uri.parse("uri2") } returns mockk {
            every { lastPathSegment } returns "uri2"
        }

        every { Uri.parse("uri3") } returns mockk {
            every { lastPathSegment } returns "uri3"
        }

        every { Uri.parse("uri4") } returns mockk {
            every { lastPathSegment } returns "uri4"
        }
    }
}