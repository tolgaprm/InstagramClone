package com.prmto.profile_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.profile_presentation.navigation.args.ProfileArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userCoreRepository: FirebaseUserCoreRepository,
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository,
    savedStateHandle: SavedStateHandle
) : CommonViewModel<UiEvent>() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    init {
        val username = ProfileArgs(savedStateHandle).username
        if (username == null) {
            _uiState.update { it.copy(isOwnProfile = true) }
            getUserDataFromPreferences()
        } else {
            getUserDataFromFirebase(username)
        }
    }

    fun getUserDataFromPreferences() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            handleResourceWithCallbacks(
                resourceSupplier = { coreUserPreferencesRepository.getUserDetail() },
                onSuccessCallback = { userDetail ->
                    _uiState.update {
                        it.copy(
                            userData = UserData(userDetail = userDetail),
                            isLoading = false
                        )
                    }
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText))
                }
            )
        }
    }

    private fun getUserDataFromFirebase(username: String) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = { userCoreRepository.getUserBySearchingUsername(username) },
                onSuccessCallback = { userData ->
                    _uiState.update {
                        it.copy(userData = userData, isLoading = false)
                    }
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userData: UserData = UserData(),
    val isOwnProfile: Boolean = false
)