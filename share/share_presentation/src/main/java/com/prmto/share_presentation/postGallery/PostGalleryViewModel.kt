package com.prmto.share_presentation.postGallery

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.common.MediaAlbumProvider
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_domain.usecase.AlbumAndCoverImage
import com.prmto.core_domain.usecase.GetAlbumAndCoverImagesUseCase
import com.prmto.core_domain.usecase.GetImageUrisByFirstAlbumNameUseCase
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostGalleryViewModel @Inject constructor(
    private val getImageUrisByFirstAlbumNameUseCase: GetImageUrisByFirstAlbumNameUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val getAlbumAndCoverImagesUseCase: GetAlbumAndCoverImagesUseCase,
    private val mediaAlbumProvider: MediaAlbumProvider
) : CommonViewModel<UiEvent>() {
    private val _uiState = MutableStateFlow(PostGalleryUiState())
    val uiState: StateFlow<PostGalleryUiState> = _uiState.asStateFlow()

    init {
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
                            selectedImageUri = result.uriInFirstAlbum.first()
                        )
                    }
                },
                onErrorCallback = { error ->
                    _uiState.update { it.copy(errorMessage = error) }
                }
            )
        }
    }

    fun onEvent(event: PostGalleryEvent) {
        when (event) {
            is PostGalleryEvent.OnImageCropped -> handleCroppedImage(event.uri)

            PostGalleryEvent.OnClickMultipleSelectButton -> {
                _uiState.update {
                    it.copy(
                        isActiveMultipleSelection = !it.isActiveMultipleSelection
                    )
                }
            }

            is PostGalleryEvent.OnClickImageItem -> {
                _uiState.update {
                    it.copy(
                        selectedImageUri = event.selectedUri
                    )
                }
                if (uiState.value.isActiveMultipleSelection) {
                    toggleSelectedUri(event.selectedUri)
                }
            }

            PostGalleryEvent.OnOpenBottomSheet -> {
                viewModelScope.launch(dispatcherProvider.IO) {
                    val result = getAlbumAndCoverImagesUseCase(
                        albumNames = uiState.value.mediaAlbumNames
                    )
                    _uiState.update {
                        it.copy(
                            albumAndCoverImages = result
                        )
                    }
                }
            }

            is PostGalleryEvent.OnClickAlbumItem -> {
                getAllUrisForAlbum(event.albumName)
            }
        }
    }

    private fun handleCroppedImage(uri: Uri) {
        if (uiState.value.isActiveMultipleSelection) {
            _uiState.update {
                it.copy(
                    croppedImageUris = uiState.value.croppedImageUris.plus(uri)
                )
            }
            if (uiState.value.croppedImageUris.last() == uri) {
                // TODO add Navigate event to navigate to PostPreviewScreen
                /*  addConsumableViewEvent(
                      UiEvent.Navigate()
                  )*/
            }
        } else {
            _uiState.update { it.copy(croppedImageUri = uri) }
        }
    }

    private fun toggleSelectedUri(selectedUri: Uri) {
        val isExistsInSelectedUris =
            _uiState.value.selectedUrisInEnabledMultipleSelectMode.contains(selectedUri)
        if (isExistsInSelectedUris) {
            _uiState.update {
                it.copy(
                    selectedUrisInEnabledMultipleSelectMode = it.selectedUrisInEnabledMultipleSelectMode.minus(
                        selectedUri
                    )
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    selectedUrisInEnabledMultipleSelectMode = it.selectedUrisInEnabledMultipleSelectMode.plus(
                        selectedUri
                    )
                )
            }
        }
    }

    private fun getAllUrisForAlbum(albumName: String) {
        viewModelScope.launch(dispatcherProvider.IO) {
            val result = mediaAlbumProvider.getAllUrisForAlbum(albumName).toList()
            _uiState.update {
                it.copy(
                    urisInSelectedAlbum = result,
                    selectedAlbumName = albumName
                )
            }
            _uiState.update { it.copy(selectedImageUri = uiState.value.urisInSelectedAlbum.first()) }
        }
    }
}

data class PostGalleryUiState(
    val mediaAlbumNames: List<String> = emptyList(),
    val selectedAlbumName: String = "",
    val urisInSelectedAlbum: List<Uri> = emptyList(),
    val errorMessage: UiText? = null,
    val selectedImageUri: Uri? = null,
    val selectedUrisInEnabledMultipleSelectMode: List<Uri> = emptyList(),
    val croppedImageUri: Uri? = null,
    val croppedImageUris: List<Uri> = emptyList(),
    val isActiveMultipleSelection: Boolean = false,
    val albumAndCoverImages: List<AlbumAndCoverImage> = emptyList()
)