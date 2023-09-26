package com.prmto.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ProfileImageViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileImageUiState())
    val uiState: StateFlow<ProfileImageUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.collectLatest {
                Timber.d("ProfileImageViewModel: uiState: ${it.captureUri}")
            }
        }
    }

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

data class ProfileImageUiState(
    val captureUri: Uri? = null,
    val cameraFlashMode: CameraFlashMode = CameraFlashMode.OFF,
    val isVisibleCameraFlashMode: Boolean = true
) {
    fun getFlashMode(): Int = cameraFlashMode.mode
}