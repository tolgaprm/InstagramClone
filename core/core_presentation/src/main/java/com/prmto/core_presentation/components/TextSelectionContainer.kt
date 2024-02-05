package com.prmto.core_presentation.components

import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.prmto.core_presentation.ui.theme.InstaBlue

@Composable
fun TextSelectionContainer(
    content: @Composable () -> Unit
) {
    val customTextSelectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.onBackground,
        backgroundColor = Color.InstaBlue.copy(alpha = 0.4f),
    )

    CompositionLocalProvider(
        LocalTextSelectionColors provides customTextSelectionColors,
    ) {
        content()
    }
}