package com.prmto.core_presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.R
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
fun ShowRationaleMessageForPermission(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    icon: ImageVector = Icons.Default.Security,
    launchPermissionAgain: () -> Unit,
) {
    var isDialogShow by rememberSaveable { mutableStateOf(true) }
    if (isDialogShow) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            iconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier,
            onDismissRequest = { isDialogShow = false },
            confirmButton = {
                TextButton(onClick = {
                    launchPermissionAgain()
                    isDialogShow = false
                }) {
                    Text(
                        text = stringResource(R.string.ok),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
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
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
}

@UiModePreview
@Composable
fun ShowRationaleMessageForPermissionPreview() {
    InstagramCloneTheme {
        Surface {
            ShowRationaleMessageForPermission(
                title = "We need the permission to use the camera.",
                message = "This app relies on camera to take photos.",
                icon = Icons.Default.CameraAlt,
                launchPermissionAgain = { }
            )
        }
    }
}