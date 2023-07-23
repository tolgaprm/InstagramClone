package com.prmto.core_presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.prmto.core_domain.util.UiText

@Composable
fun UiText.asString(): String {
    return when (this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> stringResource(id = this.id)
    }
}