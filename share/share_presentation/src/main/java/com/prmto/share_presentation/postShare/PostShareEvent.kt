package com.prmto.share_presentation.postShare

sealed interface PostShareEvent {
    data class OnCaptionChanged(val caption: String) : PostShareEvent
    data object OnPostShareClicked : PostShareEvent
}