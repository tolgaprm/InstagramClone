package com.prmto.share_presentation.postShare

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.share_presentation.postShare.navigation.args.PostShareArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostShareViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostShareUiState())
    val uiState: StateFlow<PostShareUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getSelectedPostImageUris()
        }
    }

    private fun getSelectedPostImageUris() {
        val selectedUrisForPost = PostShareArgs(savedStateHandle).postSharePhotoUris.first()
            .split(",")
            .map { Uri.parse(it) }
        _uiState.update { it.copy(selectedPostImageUris = selectedUrisForPost) }
    }

    fun onEvent(event: PostShareEvent) {
        when (event) {
            is PostShareEvent.OnCaptionChanged -> {
                _uiState.update { it.copy(caption = event.caption) }
            }

            PostShareEvent.OnPostShareClicked -> TODO()
        }
    }
}

data class PostShareUiState(
    val selectedPostImageUris: List<Uri> = emptyList(),
    val caption: String = ""
)