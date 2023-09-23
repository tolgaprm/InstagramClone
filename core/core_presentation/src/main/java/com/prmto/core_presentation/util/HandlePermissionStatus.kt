package com.prmto.core_presentation.util

import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandlePermissionStatus(
    permissionStatus: PermissionStatus,
    onPermissionGranted: @Composable () -> Unit,
    onShowRationaleMessage: @Composable () -> Unit,
    onPermissionDeniedPermanently: @Composable () -> Unit,
) {
    when {
        permissionStatus == PermissionStatus.Granted -> {
            onPermissionGranted()
        }

        permissionStatus.shouldShowRationale -> {
            onShowRationaleMessage()
        }

        !permissionStatus.shouldShowRationale -> {
            onPermissionDeniedPermanently()
        }
    }
}