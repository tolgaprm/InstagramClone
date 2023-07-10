package com.prmto.auth_presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.prmto.auth_presentation.R
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.asString

@Composable
fun AuthTextField(
    label: String,
    textFieldState: TextFieldState,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,
) {

    Column {
        TextField(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.colorTextFieldBorder),
                    shape = RoundedCornerShape(5.dp)
                )
                .clip(RoundedCornerShape(5.dp)),
            value = textFieldState.text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = MaterialTheme.typography.bodyMedium,
            enabled = enabled,
            visualTransformation = visualTransformation,
            placeholder = {
                Text(
                    text = label,
                    color = colorResource(id = R.color.colorTextFieldHint),
                )
            },
            keyboardOptions = keyboardOptions,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.colorTextField),
                unfocusedContainerColor = colorResource(id = R.color.colorTextField),
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.onBackground,
                    backgroundColor = Color.InstaBlue.copy(alpha = 0.2f)
                )
            )
        )

        AnimatedVisibility(visible = textFieldState.error != null) {
            textFieldState.error?.let {
                Text(
                    text = it.message?.asString() ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

        }
    }

}


@Preview
@Composable
fun AuthTextFieldPreview() {
    InstagramCloneTheme {
        AuthTextField(
            label = "Email",
            textFieldState = TextFieldState(
                ""
            ),
            onValueChange = {},
            enabled = true,
            maxLines = 1,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true,
            modifier = Modifier
        )
    }
}