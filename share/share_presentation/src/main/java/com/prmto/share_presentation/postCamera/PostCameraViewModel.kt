package com.prmto.share_presentation.postCamera

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.prmto.camera.usecase.GetNewFlashModeUseCase
import com.prmto.camera.util.CameraFlashMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostCameraViewModel @Inject constructor(
    private val changeFlashModeUseCase: GetNewFlashModeUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostCameraUiState())
    val uiState: StateFlow<PostCameraUiState> = _uiState.asStateFlow()

    fun onEvent(event: PostCameraEvent) {
        when (event) {
            PostCameraEvent.OnClickCameraFlashMode -> {
                changeFlashModeUseCase(uiState.value.cameraFlashMode)
                    .also { newFlashMode ->
                        _uiState.update { it.copy(cameraFlashMode = newFlashMode) }
                    }
            }

            is PostCameraEvent.OnCroppedImage -> {
                _uiState.update { it.copy(croppedImage = event.uri) }
            }
        }
    }
}

data class PostCameraUiState(
    val cameraTab: CameraTab = CameraTab.POST,
    val cameraFlashMode: CameraFlashMode = CameraFlashMode.OFF,
    val lastPhotoInGallery: Uri? = null,
    val croppedImage: Uri? = null,
)