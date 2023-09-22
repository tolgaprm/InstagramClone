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

    fun setCaptureUri(uri: Uri) {
        _uiState.value = _uiState.value.copy(captureUri = uri)
    }
}

data class ProfileImageUiState(
    val captureUri: Uri? = null,
)