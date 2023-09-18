package com.prmto.setting_presentation

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.profile_presentation.R
import com.prmto.setting_presentation.event.SettingScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val coreAuthRepository: FirebaseAuthCoreRepository,
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository
) : CommonViewModel<UiEvent>() {
    private val _uiState = MutableStateFlow(SettingScreenUiState())
    val uiState: StateFlow<SettingScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: SettingScreenEvent) {
        when (event) {
            is SettingScreenEvent.Logout -> {
                if (isHasCurrentUser()) {
                    logout()
                } else {
                    addConsumableViewEvent(
                        UiEvent.ShowMessage(
                            UiText.StringResource(R.string.you_are_not_logged_in)
                        )
                    )
                }
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            coreAuthRepository.signOut()
            handleResourceWithCallbacks(
                resourceSupplier = { coreUserPreferencesRepository.deleteUserDetail() },
                onSuccessCallback = {
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(
                        UiEvent.Navigate(NestedNavigation.Auth.route)
                    )
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(
                        UiEvent.ShowMessage(uiText)
                    )
                }
            )
        }
    }

    private fun isHasCurrentUser(): Boolean {
        return coreAuthRepository.currentUser() != null
    }
}

data class SettingScreenUiState(
    val isLoading: Boolean = false
)