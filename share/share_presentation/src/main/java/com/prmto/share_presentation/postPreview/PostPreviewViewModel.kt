package com.prmto.share_presentation.postPreview

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.prmto.share_presentation.postPreview.navigation.args.PostPreviewArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostPreviewUiState())
    val uiState: StateFlow<PostPreviewUiState> = _uiState.asStateFlow()

    init {
        val selectedUrisForPost = PostPreviewArgs(savedStateHandle).postPreviewPhotoUris.first()
            .split(",")
            .map { Uri.parse(it) }
        _uiState.update { it.copy(postPreviewPhotoUris = selectedUrisForPost) }
    }

}

data class PostPreviewUiState(
    val postPreviewPhotoUris: List<Uri> = emptyList()
)