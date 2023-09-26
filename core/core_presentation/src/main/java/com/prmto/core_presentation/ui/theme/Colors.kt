package com.prmto.core_presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Color.Companion.InstaBlue: Color
    get() = Color(0xFF3897F0)

val Color.Companion.White40: Color
    get() = Color(0x66FFFFFF)

val Color.Companion.Black60: Color
    get() = Color(0x99000000)

val Nero = Color(0xFF262626)

val DriedLavenderFlowers = Color(0xFF767680)


@Composable
fun colorBlur(): Color {
    return if (isSystemInDarkTheme()) {
        Color.White40
    } else {
        Color.Black60
    }
}
