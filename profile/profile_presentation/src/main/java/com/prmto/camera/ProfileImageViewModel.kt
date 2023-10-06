package com.prmto.camera

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileImageViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileCameraUiState())
    val uiState: StateFlow<ProfileCameraUiState> = _uiState.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun onEvent(event: ProfileCameraScreenEvent) {
        when (event) {
            is ProfileCameraScreenEvent.PhotoTaken -> {
                _uiState.update { it.copy(captureUri = event.photoUri) }
            }

            is ProfileCameraScreenEvent.PhotoCropped -> {
                _uiState.update { it.copy(croppedUri = event.croppedUri) }
            }

            is ProfileCameraScreenEvent.ClickedFlashMode -> setNewFlashMode()
            is ProfileCameraScreenEvent.ChangeCameraSelector -> {
                _uiState.update {
                    it.copy(
                        isVisibleCameraFlashMode = !event.isFrontCamera,
                        cameraFlashMode = CameraFlashMode.OFF
                    )
                }
            }

            ProfileCameraScreenEvent.DismissDialog -> {
                visiblePermissionDialogQueue.removeFirstOrNull()
            }

            is ProfileCameraScreenEvent.PermissionResult -> {
                if (!event.isGranted && !visiblePermissionDialogQueue.contains(event.permission)) {
                    visiblePermissionDialogQueue.add(event.permission)
                }
            }
        }
    }

    private fun setNewFlashMode() {
        val newFlashMode = when (uiState.value.cameraFlashMode) {
            CameraFlashMode.OFF -> CameraFlashMode.ON
            CameraFlashMode.ON -> CameraFlashMode.AUTO
            CameraFlashMode.AUTO -> CameraFlashMode.OFF
        }
        _uiState.value = _uiState.value.copy(cameraFlashMode = newFlashMode)
    }
}

data class ProfileCameraUiState(
    val captureUri: Uri? = null,
    val croppedUri: Uri? = null,
    val cameraFlashMode: CameraFlashMode = CameraFlashMode.OFF,
    val isVisibleCameraFlashMode: Boolean = true // The camera flash mode is not visible
    // camera Mode is Front.
) {
    fun getFlashMode(): Int = cameraFlashMode.mode
}