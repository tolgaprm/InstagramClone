package com.prmto.share_presentation.postPreview.navigation.args

import androidx.lifecycle.SavedStateHandle

class PostPreviewArgs(val postPreviewPhotoUris: Array<String>) {
    companion object {
        const val postPreviewArgsPhotoUris = "postPreviewArgsPhotoUris"
    }

    constructor(savedSavedStateHandle: SavedStateHandle) : this(
        postPreviewPhotoUris = savedSavedStateHandle[postPreviewArgsPhotoUris] ?: arrayOf()
    )
}