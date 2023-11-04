package com.prmto.gallery

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_domain.repository.mediafile.MediaAlbumProvider
import com.prmto.core_domain.usecase.GetImageUrisByFirstAlbumNameUseCase
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectProfileImageGalleryViewModel @Inject constructor(
    private val getImageUrisByFirstAlbumNameUseCase: GetImageUrisByFirstAlbumNameUseCase,
    private val mediaAlbumProvider: MediaAlbumProvider,
    private val dispatcherProvider: DispatcherProvider
) : CommonViewModel<UiEvent>() {
    private val _uiState = MutableStateFlow(SelectProfileImageGalleryUiState())
    val uiState: StateFlow<SelectProfileImageGalleryUiState> = _uiState.asStateFlow()

    fun onEvent(event: SelectProfileImageGalleryEvent) {
        when (event) {
            is SelectProfileImageGalleryEvent.SelectAlbum -> handleSelectAlbumEvent(event.albumName)

            is SelectProfileImageGalleryEvent.SelectImage -> {
                _uiState.update { it.copy(selectedImageUri = event.uri) }
            }

            is SelectProfileImageGalleryEvent.AllPermissionsGranted -> handleAllPermissionGranted()
            is SelectProfileImageGalleryEvent.CropImage -> {
                _uiState.update { it.copy(croppedImageUri = event.croppedImage) }
            }
        }
    }

    private fun handleAllPermissionGranted() {
        viewModelScope.launch(dispatcherProvider.IO) {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    getImageUrisByFirstAlbumNameUseCase()
                },
                onSuccessCallback = { result ->
                    _uiState.update {
                        it.copy(
                            mediaAlbumNames = result.albumNames,
                            urisInSelectedAlbum = result.uriInFirstAlbum,
                            selectedAlbumName = result.albumNames.first(),
                            selectedImageUri = result.uriInFirstAlbum.firstOrNull()
                        )
                    }
                },
                onErrorCallback = { error ->
                    _uiState.update { it.copy(errorMessage = error) }
                }
            )
        }
    }

    private fun handleSelectAlbumEvent(albumName: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedAlbumName = albumName
                )
            }
            val urisInSelectedAlbum = getImageUriByAlbumName(albumName).await()
            _uiState.update {
                it.copy(
                    urisInSelectedAlbum = urisInSelectedAlbum
                )
            }
        }
    }

    private fun getImageUriByAlbumName(albumName: String) =
        viewModelScope.async(dispatcherProvider.IO) {
            mediaAlbumProvider.getAllUrisForAlbum(albumName).toList()
        }
}

data class SelectProfileImageGalleryUiState(
    val mediaAlbumNames: List<String> = emptyList(),
    val selectedAlbumName: String = "",
    val urisInSelectedAlbum: List<Uri> = emptyList(),
    val errorMessage: UiText? = null,
    val selectedImageUri: Uri? = null,
    val croppedImageUri: Uri? = null
)