package com.prmto.core_presentation.util

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Int.toDp(): Dp {
    return (this / Resources.getSystem().displayMetrics.density).dp
}