package com.prmto.gallery

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_testing.dispatcher.TestDispatcher
import com.prmto.core_testing.fake_repository.common.MediaAlbumProviderFake
import com.prmto.core_testing.util.MainDispatcherRule
import com.prmto.profile_presentation.R
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
class SelectProfileImageGalleryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SelectProfileImageGalleryViewModel
    private lateinit var mediaAlbumProvider: MediaAlbumProviderFake
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        mediaAlbumProvider = MediaAlbumProviderFake()
        dispatcherProvider = TestDispatcher()
        viewModel = SelectProfileImageGalleryViewModel(mediaAlbumProvider, dispatcherProvider)
        every { Uri.parse(any()) } returns mockk { every { lastPathSegment } returns "file_name" }
    }

    @Test
    fun eventIsAllPermissionGranted_getAllAlbumNames_getUrisAssociatedFirstAlbumName() = runTest {
        mediaAlbumProvider.albumNames = listOf("Album1", "Album2", "Album3", "Album3", "Album2")
        mediaAlbumProvider.albumsAndUris = mapOf(
            "Album1" to listOf(Uri.parse("file_name")),
            "Album2" to emptyList(),
            "Album3" to emptyList()
        )
        viewModel.onEvent(SelectProfileImageGalleryEvent.AllPermissionsGranted)

        viewModel.uiState.test {
            awaitItem()
            advanceUntilIdle()
            val uiState = awaitItem()
            assertThat(uiState.mediaAlbumNames).isEqualTo(listOf("Album1", "Album2", "Album3"))
            assertThat(uiState.urisInSelectedAlbum).isEqualTo(listOf(Uri.parse("file_name")))
            assertThat(uiState.selectedAlbumName).isEqualTo("Album1")
        }
    }

    @Test
    fun eventIsAllPermissionGranted_getAllAlbumNames_albumNameEmpty_updateErrorMessage() = runTest {
        mediaAlbumProvider.albumNames = emptyList()
        viewModel.onEvent(SelectProfileImageGalleryEvent.AllPermissionsGranted)

        viewModel.uiState.test {
            awaitItem()
            advanceUntilIdle()
            val uiState = awaitItem()
            assertThat(uiState.mediaAlbumNames).isEmpty()
            assertThat(uiState.errorMessage).isEqualTo(UiText.StringResource(R.string.no_albums_found))
        }
    }

    @Test
    fun eventIsSelectAlbum_getImageUriByAlbumName_updateUrisInSelectedAlbum() = runTest {
        mediaAlbumProvider.albumNames = listOf("Album1", "Album2")
        mediaAlbumProvider.albumsAndUris = mapOf(
            "Album1" to emptyList(), "Album2" to listOf(Uri.parse("file_name"))
        )
        viewModel.onEvent(SelectProfileImageGalleryEvent.SelectAlbum(albumName = "Album2"))
        viewModel.uiState.test {
            assertThat(awaitItem().selectedAlbumName).isEqualTo("Album2")
            advanceUntilIdle()
            assertThat(awaitItem().urisInSelectedAlbum).isEqualTo(listOf(Uri.parse("file_name")))
        }
    }

    @Test
    fun eventIsCropImage_stateUpdated() = runTest {
        viewModel.onEvent(SelectProfileImageGalleryEvent.CropImage(croppedImage = Uri.parse("file_name")))
        viewModel.uiState.test {
            assertThat(awaitItem().croppedImageUri).isEqualTo(Uri.parse("file_name"))
        }
    }

    @Test
    fun eventIsSelectImage_updateSelectedImageUri() = runTest {
        viewModel.onEvent(SelectProfileImageGalleryEvent.SelectImage(uri = Uri.parse("file_name")))
        viewModel.uiState.test {
            assertThat(awaitItem().selectedImageUri).isEqualTo(Uri.parse("file_name"))
        }
    }
}