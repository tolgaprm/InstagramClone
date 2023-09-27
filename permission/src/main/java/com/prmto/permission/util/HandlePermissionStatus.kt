package com.prmto.permission.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.prmto.core_presentation.util.asString
import com.prmto.permission.R
import com.prmto.permission.components.ShowAlertDialogForPermission
import com.prmto.permission.provider.PermissionInfoProvider

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandlePermissionStatus(
    permissionState: PermissionState,
    permissionProvider: PermissionInfoProvider
) {
    val permissionStatus = permissionState.status
    when {
        permissionStatus.shouldShowRationale -> {
            ShowAlertDialogForPermission(
                title = permissionProvider.titleForRationaleMessage().asString(),
                message = permissionProvider.messageForRationaleMessage().asString(),
                onOkClick = {
                    permissionState.launchPermissionRequest()
                }
            )
        }

        !permissionStatus.shouldShowRationale -> {
            ShowAlertDialogForPermission(
                message = stringResource(
                    R.string.permission_permanently_declined_message,
                    permissionProvider.permissionName().asString()
                ),
                icon = permissionProvider.getPermissionIcon(),
                isPermanentlyDeclined = true
            )
        }
    }
}

@Composable
fun HandleMultiplePermissionStatus(
    permissionProvider: PermissionInfoProvider,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    shouldShowRationale: Boolean
) {
    if (!shouldShowRationale) {
        ShowAlertDialogForPermission(
            message = stringResource(
                R.string.permission_permanently_declined_message,
                permissionProvider.permissionName().asString()
            ),
            icon = permissionProvider.getPermissionIcon(),
            onDismiss = onDismiss,
            isPermanentlyDeclined = true
        )
    } else {
        ShowAlertDialogForPermission(
            title = permissionProvider.titleForRationaleMessage().asString(),
            message = permissionProvider.messageForRationaleMessage().asString(),
            icon = permissionProvider.getPermissionIcon(),
            onOkClick = onOkClick,
            onDismiss = onDismiss
        )
    }
}