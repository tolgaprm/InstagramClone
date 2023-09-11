package com.prmto.edit_profile_presentation.event

sealed interface EditProfileUiEvent {
    data class EnteredName(val name: String) : EditProfileUiEvent

    data class EnteredUsername(val username: String) : EditProfileUiEvent

    data class EnteredBio(val bio: String) : EditProfileUiEvent

    data class EnteredWebsite(val website: String) : EditProfileUiEvent

    data object UpdateProfileInfo : EditProfileUiEvent
}