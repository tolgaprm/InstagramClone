package com.prmto.camera

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileImageViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileCameraUiState())
    val uiState: StateFlow<ProfileCameraUiState> = _uiState.asStateFlow()

    val visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun onEvent(event: ProfileCameraScreenEvent) {
        when (event) {
            is ProfileCameraScreenEvent.PhotoTaken -> {
                _uiState.value = _uiState.value.copy(captureUri = event.photoUri)
            }

            is ProfileCameraScreenEvent.ClickedFlashMode -> setNewFlashMode()
            is ProfileCameraScreenEvent.ChangeCameraSelector -> {
                _uiState.value = _uiState.value.copy(
                    isVisibleCameraFlashMode = !event.isFrontCamera,
                    cameraFlashMode = CameraFlashMode.OFF
                )
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
    val cameraFlashMode: CameraFlashMode = CameraFlashMode.OFF,
    val isVisibleCameraFlashMode: Boolean = true
) {
    fun getFlashMode(): Int = cameraFlashMode.mode
}