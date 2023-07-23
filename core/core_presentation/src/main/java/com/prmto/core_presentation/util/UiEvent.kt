package com.prmto.core_presentation.util

import androidx.annotation.StringRes
import com.prmto.core_domain.util.UiText

abstract class Event


sealed class UiEvent : Event() {
    data class Navigate(val route: String) : UiEvent()
    data class ShowMessage(val uiText: UiText) : UiEvent()
    object Idle : UiEvent()
}


fun UiEvent.showMessageWithResID(
    @StringRes resourceId: Int
) = UiEvent.ShowMessage(UiText.StringResource(resourceId))
