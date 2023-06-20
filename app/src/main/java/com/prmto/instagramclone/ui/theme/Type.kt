package com.prmto.instagramclone.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.prmto.instagramclone.R

val FontFamily.Companion.SF_PRO: FontFamily
    get() = FontFamily(
        Font(R.font.sf_pro_regular, FontWeight.Normal),
        Font(R.font.sf_pro_medium, FontWeight.Medium),
    )


val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.SF_PRO,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
)