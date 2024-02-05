package com.prmto.share_presentation.postShare.navigation.args

import androidx.lifecycle.SavedStateHandle

internal class PostShareArgs(val postSharePhotoUris: Array<String>) {
    companion object {
        const val postShareArgsPhotoUris = "selectedPhotoUris"
    }

    constructor(savedStateHandle: SavedStateHandle) : this(
        postSharePhotoUris = savedStateHandle[postShareArgsPhotoUris] ?: arrayOf()
    )
}