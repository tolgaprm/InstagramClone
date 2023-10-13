package com.prmto.camera

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.camera.usecase.GetNewFlashModeUseCase
import com.prmto.camera.util.CameraFlashMode
import com.prmto.core_testing.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileImageViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ProfileImageViewModel

    @Before
    fun setUp() {
        mockkStatic(Uri::class)
        every { Uri.parse(SAMPLE_IMAGE_URI).lastPathSegment } returns SAMPLE_IMAGE_URI
        viewModel = ProfileImageViewModel(getNewFlashModeUseCase = GetNewFlashModeUseCase())
    }

    @Test
    fun initViewModel_defaultState() = runTest {
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.captureUri).isNull()
            assertThat(uiState.cameraFlashMode).isEqualTo(CameraFlashMode.OFF)
            assertThat(uiState.isVisibleCameraFlashMode).isEqualTo(true)
        }
    }

    @Test
    fun eventIsPhotoTaken_stateUpdated() = runTest {
        val sampleCapturedPhotoUri = Uri.parse(SAMPLE_IMAGE_URI)
        val event = ProfileCameraScreenEvent.PhotoTaken(photoUri = sampleCapturedPhotoUri)
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.captureUri).isEqualTo(sampleCapturedPhotoUri)
        }
    }

    @Test
    fun eventIsPhotoCropped_stateUpdated() = runTest {
        val sampleCroppedPhotoUri = Uri.parse(SAMPLE_IMAGE_URI)
        val event = ProfileCameraScreenEvent.PhotoCropped(croppedUri = sampleCroppedPhotoUri)
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.croppedUri).isEqualTo(sampleCroppedPhotoUri)
        }
    }

    @Test
    fun eventIsClickedFlashMode_stateUpdated() = runTest {
        val event = ProfileCameraScreenEvent.ClickedFlashMode
        // CameraFlashMode currently is [CameraFlashMode.OFF], must updated to [CameraFlashMode.ON]
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.cameraFlashMode).isEqualTo(CameraFlashMode.ON)
        }

        // CameraFlashMode currently is [CameraFlashMode.ON], must updated to [CameraFlashMode.AUTO]
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.cameraFlashMode).isEqualTo(CameraFlashMode.AUTO)
        }

        // CameraFlashMode currently is [CameraFlashMode.AUTO], must updated to [CameraFlashMode.OFF]
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.cameraFlashMode).isEqualTo(CameraFlashMode.OFF)
        }
    }

    @Test
    fun eventIsChangeCameraSelector_stateUpdated() = runTest {
        val event = ProfileCameraScreenEvent.ChangeCameraSelector(isFrontCamera = true)
        viewModel.onEvent(event)
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertThat(uiState.isVisibleCameraFlashMode).isEqualTo(false)
            assertThat(uiState.cameraFlashMode).isEqualTo(CameraFlashMode.OFF)
        }
    }

    @Test
    fun eventIsPermissionResult_stateUpdated() = runTest {
        val permission = "android.permission.CAMERA"
        val event = ProfileCameraScreenEvent.PermissionResult(
            permission = permission,
            isGranted = false
        )
        viewModel.onEvent(event)
        assertThat(viewModel.visiblePermissionDialogQueue).contains(permission)
    }

    @Test
    fun eventIsDismissDialog_stateUpdated() = runTest {
        val cameraPermission = "android.permission.CAMERA"
        val storagePermission = "android.permission.WRITE_EXTERNAL_STORAGE"
        addPermissionToQueue(cameraPermission)
        addPermissionToQueue(storagePermission)

        val event = ProfileCameraScreenEvent.DismissDialog
        viewModel.onEvent(event)
        assertThat(viewModel.visiblePermissionDialogQueue).doesNotContain(cameraPermission)
        assertThat(viewModel.visiblePermissionDialogQueue).contains(storagePermission)
        viewModel.onEvent(event)
        assertThat(viewModel.visiblePermissionDialogQueue).doesNotContain(storagePermission)
    }

    companion object {
        const val SAMPLE_IMAGE_URI =
            "content://com.android.providers.media.documents/document/image%3A1"
    }

    private fun addPermissionToQueue(permission: String) {
        viewModel.visiblePermissionDialogQueue.add(permission)
    }
}