package com.prmto.auth_presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.prmto.auth_presentation.R
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState
import com.prmto.core_presentation.util.asString
import com.prmto.core_presentation.util.isErrorNull

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    label: String,
    textFieldState: TextFieldState? = null,
    passwordTextFieldState: PasswordTextFieldState? = null,
    enabled: Boolean = true,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    onTogglePasswordVisibility: () -> Unit = {},
) {
    var visualTransformation by remember {
        mutableStateOf(
            VisualTransformation.None
        )
    }

    passwordTextFieldState?.let {
        LaunchedEffect(key1 = it.isPasswordVisible) {
            if (it.isPasswordVisible) {
                visualTransformation = VisualTransformation.None
            } else {
                visualTransformation = PasswordVisualTransformation()
            }
        }
    }

    Column {
        TextField(
            modifier = modifier
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.colorTextFieldBorder),
                    shape = RoundedCornerShape(5.dp)
                )
                .clip(RoundedCornerShape(5.dp)),
            value = textFieldState?.text ?: passwordTextFieldState?.text ?: "",
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
            keyboardActions = keyboardActions,
            trailingIcon = {
                passwordTextFieldState?.let {
                    Icon(
                        modifier = Modifier.clickable { onTogglePasswordVisibility() },
                        imageVector = if (it.isPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (it.isPasswordVisible) stringResource(R.string.hide_password) else stringResource(
                            R.string.show_password
                        )
                    )
                }
            },
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

        textFieldState?.let {
            ShowErrorMessage(
                isVisible = !it.isErrorNull(),
                message = it.error?.message?.asString()
            )
        }

        passwordTextFieldState?.let {
            ShowErrorMessage(
                isVisible = !it.isErrorNull(),
                message = it.error?.message?.asString()
            )
        }
    }
}

@Composable
fun ShowErrorMessage(
    isVisible: Boolean = false,
    message: String? = null,
) {
    AnimatedVisibility(visible = isVisible) {
        Text(
            text = message ?: "",
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@UiModePreview
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
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true,
            modifier = Modifier
        )
    }
}

@UiModePreview
@Composable
fun AuthTextPasswordFieldPreview() {
    InstagramCloneTheme {
        AuthTextField(
            label = "Password",
            passwordTextFieldState = PasswordTextFieldState(
                text = ""
            ),
            onValueChange = {},
            enabled = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default,
            singleLine = true,
            modifier = Modifier
        )
    }
}