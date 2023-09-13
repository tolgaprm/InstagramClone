package com.prmto.profile_presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.common.NavArguments
import com.prmto.core_domain.constants.onSuccess
import com.prmto.core_domain.model.UserData
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        val username = savedStateHandle.get<String>(NavArguments.USERNAME)
        if (username == null) {
            _uiState.update { it.copy(isOwnProfile = true) }
            getUserDataFromPreferences()
        } else {
            getUserDataFromFirebase(username)
        }
    }

    fun getUserDataFromPreferences() {
        viewModelScope.launch {
            coreUserPreferencesRepository.getUserDetail().onSuccess { userDetail ->
                _uiState.update {
                    it.copy(
                        userData = UserData(
                            userDetail = userDetail
                        )
                    )
                }
            }
        }
    }

    private fun getUserDataFromFirebase(username: String) {
        viewModelScope.launch {
            userCoreRepository.getUserBySearchingUsername(username)
                .onSuccess { userData -> _uiState.update { it.copy(userData = userData) } }
        }
    }
}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val userData: UserData = UserData(),
    val isOwnProfile: Boolean = false
)