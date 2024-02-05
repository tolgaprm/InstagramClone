package com.prmto.share_presentation.postCamera

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.prmto.camera.usecase.GetNewFlashModeUseCase
import com.prmto.camera.util.CameraFlashMode
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_domain.repository.mediafile.MediaAlbumProvider
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.share_presentation.navigation.ShareNestedScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCameraViewModel @Inject constructor(
    private val changeFlashModeUseCase: GetNewFlashModeUseCase,
    private val mediaAlbumProvider: MediaAlbumProvider,
    private val dispatcherProvider: DispatcherProvider
) : CommonViewModel<UiEvent>() {
    private val _uiState = MutableStateFlow(PostCameraUiState())
    val uiState: StateFlow<PostCameraUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcherProvider.IO) {
            val uri = mediaAlbumProvider.getLastUriOfTheImage()
            _uiState.update { it.copy(lastPhotoInGallery = uri) }
        }
    }

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
                addConsumableViewEvent(
                    UiEvent.Navigate(
                        ShareNestedScreens.PostShareScreen.passArguments(
                            arrayOf(event.uri.toString())
                        )
                    )
                )
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