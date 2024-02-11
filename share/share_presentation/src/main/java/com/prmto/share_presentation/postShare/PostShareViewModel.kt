package com.prmto.share_presentation.postShare

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.constants.UiText
import com.prmto.core_presentation.navigation.Screen
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.share_domain.usecase.PostShareUseCase
import com.prmto.share_presentation.R
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
    private val savedStateHandle: SavedStateHandle,
    private val postShareUseCase: PostShareUseCase
) : CommonViewModel<UiEvent>() {
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
        _uiState.update { it.copy(selectedPostImageUris = selectedUrisForPost) }
    }

    fun onEvent(event: PostShareEvent) {
        when (event) {
            is PostShareEvent.OnCaptionChanged -> {
                _uiState.update { it.copy(caption = event.caption) }
            }

            PostShareEvent.OnPostShareClicked -> sharePost()
        }
    }

    private fun sharePost() {
        viewModelScope.launch {
            _uiState.update { it.copy(isPostUploading = true) }

            handleResourceWithCallbacks(
                resourceSupplier = {
                    postShareUseCase(
                        selectedPostImageUris = uiState.value.selectedPostImageUris,
                        caption = uiState.value.caption
                    )
                },
                onSuccessCallback = {
                    _uiState.update { it.copy(isPostUploading = false) }
                    addConsumableViewEvent(
                        UiEvent.ShowMessage(
                            UiText.StringResource(R.string.post_shared_successfully)
                        )
                    )
                    addConsumableViewEvent(UiEvent.Navigate(Screen.Home.route))
                },
                onErrorCallback = {
                    _uiState.update { it.copy(isPostUploading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(it))
                }
            )
        }
    }
}

data class PostShareUiState(
    val selectedPostImageUris: List<String> = emptyList(),
    val caption: String = "",
    val isPostUploading: Boolean = false,
)