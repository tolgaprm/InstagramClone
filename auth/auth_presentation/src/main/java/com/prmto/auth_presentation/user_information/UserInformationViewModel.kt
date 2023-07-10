package com.prmto.auth_presentation.user_information

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.prmto.auth_presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInformationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(UserInfoData())
    val state: State<UserInfoData> = _state

    init {
        savedStateHandle.get<String>(Constants.UserInfoEmailArgumentName)?.let { email ->
            _state.value = state.value.copy(
                email = email
            )
        }

        savedStateHandle.get<String>(Constants.UserInfoPhoneArgumentName)?.let { phoneNumber ->
            _state.value = state.value.copy(
                phoneNumber = phoneNumber
            )
        }
    }
}