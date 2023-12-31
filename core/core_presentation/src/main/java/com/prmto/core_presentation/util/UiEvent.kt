package com.prmto.core_presentation.util

import com.prmto.core_domain.constants.UiText

abstract class Event


sealed class UiEvent : Event() {
    data class Navigate(val route: String) : UiEvent()

    data class ShowMessage(val uiText: UiText) : UiEvent()

    data object PopBackStack : UiEvent()
}

fun List<UiEvent>.addNewUiEvent(uiEvent: UiEvent) = this + uiEvent