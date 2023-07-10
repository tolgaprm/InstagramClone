package com.prmto.core_presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.prmto.core_presentation.R

val FontFamily.Companion.SF_PRO: FontFamily
    get() = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_medium, FontWeight.Medium),
    )


val Typography = Typography(
    titleMedium = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 17.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
)