package com.prmto.edit_profile_presentation

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserDetail
import com.prmto.core_domain.repository.preferences.CoreUserPreferencesRepository
import com.prmto.core_domain.repository.storage.StorageRepository
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_domain.usecase.CheckIfExistUserWithTheSameUsernameUseCase
import com.prmto.core_domain.usecase.GetCurrentUserUseCase
import com.prmto.core_presentation.util.CommonViewModel
import com.prmto.core_presentation.util.UiEvent
import com.prmto.edit_profile_presentation.event.EditProfileUiEvent
import com.prmto.profile_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.prmto.core_domain.R as CoreDomainR

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val coreUserPreferencesRepository: CoreUserPreferencesRepository,
    private val firebaseUserCoreRepository: FirebaseUserCoreRepository,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val firebaseStorageRepository: StorageRepository,
    private val checkIfExistUserWithTheSameUsernameUseCase: CheckIfExistUserWithTheSameUsernameUseCase
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

            is EditProfileUiEvent.SelectNewProfileImage -> {
                val selectedPhotoUri = Uri.parse(event.selectNewProfileUriString)
                _uiState.update {
                    it.copy(
                        selectedNewProfileImage = selectedPhotoUri,
                        isShowSaveButton = true
                    )
                }
            }

            EditProfileUiEvent.UpdateProfileInfo -> {
                trimUpdatedUserDetail()
                if (uiState.value.updatedUserDetail.name.isNotBlank() && uiState.value.updatedUserDetail.username.isNotBlank()) {
                    _uiState.update { it.copy(isLoading = true) }
                    handleUpdateProfileInfo()
                } else {
                    addConsumableViewEvent(
                        UiEvent.ShowMessage(UiText.StringResource(R.string.username_or_name_fields_are_not_empty))
                    )
                }
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
            handleResourceWithCallbacks(
                resourceSupplier = { checkIfExistUserWithTheSameUsernameUseCase(uiState.value.updatedUserDetail.username) },
                onSuccessCallback = { ifExistUserWithTheSameUsername ->
                    if (ifExistUserWithTheSameUsername && uiState.value.userDetail.username != uiState.value.updatedUserDetail.username) {
                        handleUsernameExists()
                    } else {
                        handleProfileUpdate()
                    }
                },
                onErrorCallback = {
                    addConsumableViewEvent(UiEvent.ShowMessage(it))
                }
            )
        }
    }

    private fun updateProfileImage(photoUri: Uri) =
        viewModelScope.async {
            handleResourceWithCallbacks(
                resourceSupplier = { firebaseStorageRepository.updatedProfileImage(photoUri) },
                onSuccessCallback = { photoUploadUrl ->
                    _uiState.update {
                        it.copy(
                            updatedUserDetail = it.updatedUserDetail.copy(
                                profilePictureUrl = photoUploadUrl
                            )
                        )
                    }
                },
                onErrorCallback = {
                    addConsumableViewEvent(UiEvent.ShowMessage(it))
                }
            )
        }

    private fun handleProfileUpdate() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase()
            if (currentUser != null) {
                if (uiState.value.selectedNewProfileImage != null) {
                    updateProfileImage(
                        uiState.value.selectedNewProfileImage ?: return@launch
                    ).await()
                }
                updateProfileInfoToFirebase(userUid = currentUser.uid)
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun handleUsernameExists() {
        _uiState.update { it.copy(isLoading = false) }
        addConsumableViewEvent(UiEvent.ShowMessage(UiText.StringResource(CoreDomainR.string.username_already_exists)))
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
                isShowSaveButton = name != it.userDetail.name
            )
        }
    }

    private fun handleEnteredUsername(username: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(username = username.trim()),
                isShowSaveButton = username != it.userDetail.username
            )
        }
    }

    private fun handleEnteredBio(bio: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(bio = bio),
                isShowSaveButton = bio != it.userDetail.bio
            )
        }
    }

    private fun handleEnteredWebsite(website: String) {
        _uiState.update {
            it.copy(
                updatedUserDetail = it.updatedUserDetail.copy(webSite = website),
                isShowSaveButton = website != it.userDetail.webSite
            )
        }
    }

    private fun trimUpdatedUserDetail() {
        _uiState.update {
            it.copy(
                updatedUserDetail = uiState.value.updatedUserDetail.copy(
                    name = uiState.value.updatedUserDetail.name.trim(),
                    bio = uiState.value.updatedUserDetail.bio.trim(),
                    webSite = uiState.value.updatedUserDetail.webSite.trim()
                )
            )
        }
    }
}

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val userDetail: UserDetail = UserDetail(),
    val updatedUserDetail: UserDetail = UserDetail(),
    val isShowSaveButton: Boolean = userDetail != updatedUserDetail,
    val selectedNewProfileImage: Uri? = null
)