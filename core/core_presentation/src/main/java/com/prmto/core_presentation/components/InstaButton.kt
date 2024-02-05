package com.prmto.core_presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
fun InstaButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier.height(48.dp),
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.InstaBlue.copy(alpha = 0.5f)),
        colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
            contentColor = if (enabled) Color.White else Color.InstaBlue,
            containerColor = if (enabled) Color.InstaBlue else Color.White,
        )
    ) {
        Text(
            text = buttonText,
            color = if (enabled) Color.White else Color.InstaBlue,
        )
    }
}

@Preview
@Composable
fun AuthButtonPreview() {
    InstagramCloneTheme {
        InstaButton(buttonText = "SignIn") {

        }
    }
}