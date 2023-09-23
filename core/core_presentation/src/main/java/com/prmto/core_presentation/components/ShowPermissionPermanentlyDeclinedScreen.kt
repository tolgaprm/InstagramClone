package com.prmto.core_presentation.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.prmto.core_presentation.R
import com.prmto.core_presentation.previews.UiModePreview
import com.prmto.core_presentation.ui.theme.InstaBlue
import com.prmto.core_presentation.ui.theme.InstagramCloneTheme

@Composable
fun ShowPermissionPermanentlyDeclinedScreen(
    modifier: Modifier = Modifier,
    permissionName: String,
    imageVector: ImageVector,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = imageVector,
                contentDescription = permissionName,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(
                    R.string.permission_permanently_declined_message,
                    permissionName
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    openAppSettings(context)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.InstaBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.go_to_settings))
            }
        }
    }
}

private fun openAppSettings(context: Context) {
    Intent(
        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    ).also(context::startActivity)
}


@UiModePreview
@Composable
fun ShowPermissionPermanentlyDeclinedScreenPreview() {
    InstagramCloneTheme {
        Surface {
            ShowPermissionPermanentlyDeclinedScreen(
                permissionName = "Camera",
                imageVector = Icons.Default.PhotoCamera
            )
        }
    }
}