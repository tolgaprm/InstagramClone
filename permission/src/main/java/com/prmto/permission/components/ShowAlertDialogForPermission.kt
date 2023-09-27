package com.prmto.permission.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme
import com.prmto.permission.R

@Composable
fun ShowAlertDialogForPermission(
    modifier: Modifier = Modifier,
    title: String = "",
    message: String,
    icon: ImageVector = Icons.Default.Security,
    onOkClick: () -> Unit = {},
    onDismiss: () -> Unit = {},
    isPermanentlyDeclined: Boolean = false,
) {
    val context = LocalContext.current
    var isVisible by rememberSaveable {
        mutableStateOf(true)
    }
    if (isVisible) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            iconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier,
            onDismissRequest = {
                onDismiss()
                isVisible = false
            },
            confirmButton = {
                if (isPermanentlyDeclined) {
                    PermanentlyDeclinedButton(
                        context = context,
                        onOkClick = {
                            openAppSettings(context = context)
                            isVisible = false
                        }
                    )
                } else {
                    RationaleButton(onOkClick = {
                        onOkClick()
                        isVisible = false
                    })
                }
            },
            icon = {
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = icon,
                    contentDescription = null
                )
            },
            title = {
                if (!isPermanentlyDeclined) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@Composable
private fun PermanentlyDeclinedButton(
    context: Context,
    onOkClick: () -> Unit
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onOkClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.InstaBlue,
            contentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(R.string.go_to_settings)
        )
    }
}

@Composable
private fun RationaleButton(
    onOkClick: () -> Unit
) {
    TextButton(
        onClick = {
            onOkClick()
        }
    ) {
        Text(
            text = stringResource(R.string.ok),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@UiModePreview
@Composable
fun AlertDialogRationalePreview() {
    InstagramCloneTheme {
        Surface {
            ShowAlertDialogForPermission(
                title = "We need the permission to use the camera.",
                message = "This app relies on camera to take photos.",
                icon = Icons.Default.CameraAlt,
                onOkClick = { },
                onDismiss = { },
                isPermanentlyDeclined = false
            )
        }
    }
}

@UiModePreview
@Composable
fun AlertDialogPermanentlyDeclinedPreview() {
    InstagramCloneTheme {
        Surface {
            ShowAlertDialogForPermission(
                message = "Camera permission is permanently declined, please go to settings and enable it",
                icon = Icons.Default.CameraAlt,
                onDismiss = { },
                isPermanentlyDeclined = true
            )
        }
    }
}

private fun openAppSettings(context: Context) {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    ).also(context::startActivity)
}