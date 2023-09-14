package com.prmto.edit_profile_presentation

import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.usecase.GetCurrentUserUseCase
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.edit_profile_presentation.event.EditProfileUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository,
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : CommonViewModel<UiEvent>() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    init {
        getUserDetail()
    }

    fun onEvent(event: EditProfileUiEvent) {
        when (event) {
            is EditProfileUiEvent.EnteredName -> handleEnteredName(event.name)

            is EditProfileUiEvent.EnteredUsername -> handleEnteredUsername(event.username)

            is EditProfileUiEvent.EnteredBio -> handleEnteredBio(event.bio)

            is EditProfileUiEvent.EnteredWebsite -> handleEnteredWebsite(event.website)

            EditProfileUiEvent.UpdateProfileInfo -> {
                _uiState.update { it.copy(isLoading = true) }
                handleUpdateProfileInfo()
            }
        }
    }

    private fun getUserDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            handleResourceWithCallbacks(
                resourceSupplier = { coreUserPreferencesRepository.getUserDetail() },
                onSuccessCallback = { userDetail ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            userDetail = userDetail,
                            updatedUserDetail = userDetail
                        )
                    }
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun handleUpdateProfileInfo() {
        viewModelScope.launch {
            getCurrentUserUseCase()?.let { currentUser ->
                updateProfileInfoToFirebase(userUid = currentUser.uid)
            } ?: _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun updateProfileInfoToPreferences() {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    coreUserPreferencesRepository.saveUserDetail(uiState.value.updatedUserDetail)
                },
                onSuccessCallback = {
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.PopBackStack)
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun updateProfileInfoToFirebase(userUid: String) {
        viewModelScope.launch {
            handleResourceWithCallbacks(
                resourceSupplier = {
                    firebaseUserCoreRepository.updateUserDetail(
                        userDetail = uiState.value.updatedUserDetail,
                        userUid = userUid
                    )
                },
                onSuccessCallback = {
                    updateProfileInfoToPreferences()
                },
                onErrorCallback = { uiText ->
                    _uiState.update { it.copy(isLoading = false) }
                    addConsumableViewEvent(UiEvent.ShowMessage(uiText = uiText))
                }
            )
        }
    }

    private fun handleEnteredName(name: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(name = name),
                isShowSaveButton = it.updatedUserDetail.copy(name = name.trim()) != it.userDetail
            )
        }
    }

    private fun handleEnteredUsername(username: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(username = username),
                isShowSaveButton = it.updatedUserDetail.copy(
                    username = username.trim()
                ) != it.userDetail
            )
        }
    }

    private fun handleEnteredBio(bio: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(bio = bio),
                isShowSaveButton = it.updatedUserDetail.copy(
                    bio = bio.trim()
                ) != it.userDetail
            )
        }
    }

    private fun handleEnteredWebsite(website: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(webSite = website),
                isShowSaveButton = it.updatedUserDetail.copy(
                    webSite = website.trim()
                ) != it.userDetail
            )
        }
    }
}

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val userDetail: UserDetail = UserDetail(),
    val updatedUserDetail: UserDetail = UserDetail(),
    val isShowSaveButton: Boolean = userDetail != updatedUserDetail
)