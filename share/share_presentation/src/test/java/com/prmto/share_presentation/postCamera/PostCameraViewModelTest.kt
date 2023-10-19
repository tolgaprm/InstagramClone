package com.prmto.share_presentation.postCamera

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.camera.usecase.GetNewFlashModeUseCase
import com.prmto.camera.util.CameraFlashMode
import com.prmto.core_testing.dispatcher.TestDispatcher
import com.prmto.core_testing.fake_repository.common.MediaAlbumProviderFake
import com.prmto.core_testing.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostCameraViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: PostCameraViewModel
    private lateinit var mediaAlbumProvider: MediaAlbumProviderFake
    private val lastUri = "file_name"

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        val dispatcherProvider = TestDispatcher()
        mediaAlbumProvider = MediaAlbumProviderFake()
        viewModel = PostCameraViewModel(
            changeFlashModeUseCase = GetNewFlashModeUseCase(),
            mediaAlbumProvider = mediaAlbumProvider,
            dispatcherProvider = dispatcherProvider
        )
        every { Uri.parse(any()) } returns mockk { every { lastPathSegment } returns lastUri }
        mediaAlbumProvider.lastUri = Uri.parse(lastUri)
    }

    @Test
    fun viewModelInit_LastUriOfTheImage_stateUpdated() = runTest {
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.lastPhotoInGallery).isEqualTo(Uri.parse(lastUri))
        }
    }

    @Test
    fun eventIsOnCroppedImage_croppedImage_stateUpdated() = runTest {
        val croppedImageUri = "cropped_image"
        viewModel.onEvent(PostCameraEvent.OnCroppedImage(Uri.parse(croppedImageUri)))
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.croppedImage).isEqualTo(Uri.parse(croppedImageUri))
        }
    }

    @Test
    fun eventIsOnClickCameraFlashMode_cameraFlashModeOff_stateUpdatedCameraFlashModeON() = runTest {
        viewModel.onEvent(PostCameraEvent.OnClickCameraFlashMode)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.cameraFlashMode).isEqualTo(CameraFlashMode.ON)
        }
    }
}