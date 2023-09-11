package com.prmto.setting_presentation

import androidx.lifecycle.ViewModel
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import com.prmto.core_presentation.navigation.NestedNavigation
import com.prmto.core_presentation.util.UiEvent
import com.prmto.core_presentation.util.addNewUiEvent
import com.prmto.setting_presentation.event.SettingScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val coreAuthRepository: FirebaseAuthCoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingScreenUiState())
    val uiState: StateFlow<SettingScreenUiState> = _uiState.asStateFlow()

    fun onEvent(event: SettingScreenEvent) {
        when (event) {
            is SettingScreenEvent.Logout -> {
                logout()
            }
        }
    }

    private fun logout() {
        coreAuthRepository.signOut()
        _uiState.update {
            it.copy(
                consumableViewEvents = it.consumableViewEvents.addNewUiEvent(
                    UiEvent.Navigate(NestedNavigation.Auth.route)
                )
            )
        }
    }

    fun onEventConsumed() {
        _uiState.update {
            it.copy(
                consumableViewEvents = uiState.value.consumableViewEvents.drop(1)
            )
        }
    }
}

data class SettingScreenUiState(
    val isLoading: Boolean = false,
    val consumableViewEvents: List<UiEvent> = listOf()
)