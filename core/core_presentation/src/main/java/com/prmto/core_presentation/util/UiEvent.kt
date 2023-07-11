package com.prmto.core_presentation.util

abstract class Event


sealed class UiEvent : Event() {
    data class Navigate(val route: String) : UiEvent()
    data class ShowMessage(val uiText: com.prmto.core_domain.util.UiText) : UiEvent()
}
