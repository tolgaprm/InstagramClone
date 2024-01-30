package com.prmto.profile_presentation.navigation.args

import androidx.lifecycle.SavedStateHandle

internal class ProfileArgs(val username: String? = null) {

    companion object {
        const val profileArgsUsername = "username"
    }

    constructor(savedStateHandle: SavedStateHandle) : this(
        username = savedStateHandle[profileArgsUsername]
    )
}