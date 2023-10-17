package com.prmto.share_presentation.postGallery

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.common.MediaAlbumProvider
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.dispatcher.DefaultDispatcherProvider
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
    private val mediaAlbumProvider: MediaAlbumProvider,
    private val getImageUrisByFirstAlbumNameUseCase: GetImageUrisByFirstAlbumNameUseCase,
    private val dispatcherProvider: DefaultDispatcherProvider
) : CommonViewModel<UiEvent>() {
    private val _uiState = MutableStateFlow(PostGalleryUiState())
    val uiState: StateFlow<PostGalleryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcherProvider.IO) {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    getImageUrisByFirstAlbumNameUseCase(viewModelScope)
                },
                onSuccessCallback = { result ->
                    _uiState.update {
                        it.copy(
                            mediaAlbumNames = result.albumNames,
                            urisInSelectedAlbum = result.uriInFirstAlbum,
                            selectedAlbumName = result.albumNames.first()
                        )
                    }
                },
                onErrorCallback = { error ->
                    _uiState.update { it.copy(errorMessage = error) }
                }
            )
        }
    }
}

data class PostGalleryUiState(
    val mediaAlbumNames: List<String> = emptyList(),
    val selectedAlbumName: String = "",
    val urisInSelectedAlbum: List<Uri> = emptyList(),
    val errorMessage: UiText? = null,
    val selectedImageUri: Uri? = null,
    val croppedImageUri: Uri? = null
)