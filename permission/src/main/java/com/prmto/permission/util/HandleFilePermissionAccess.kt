package com.prmto.permission.util

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun handleFilePermissionAccess(
    onPermissionResult: (Boolean) -> Unit = {}
): PermissionState {
    val permissionToRequest = permissionToRequestForFile()
    val permissionState = rememberPermissionState(
        permission = permissionToRequest,
        onPermissionResult = onPermissionResult
    )

    // Request permission when the composable is first launched and if the permission is not granted
    LaunchedEffect(key1 = Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    return permissionState
}

fun permissionToRequestForFile(): String {
    return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Manifest.permission.READ_EXTERNAL_STORAGE
    } else {
        Manifest.permission.READ_MEDIA_IMAGES
    }
}