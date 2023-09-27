package com.prmto.permission.provider

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.ui.graphics.vector.ImageVector
import com.prmto.core_domain.constants.UiText

interface PermissionInfoProvider {
    fun permissionName(): UiText
    fun titleForRationaleMessage(): UiText
    fun messageForRationaleMessage(): UiText
    fun getPermissionIcon(): ImageVector = Icons.Default.Security
}

fun getPermissionInfoProvider(permission: String): PermissionInfoProvider {
    return when (permission) {
        android.Manifest.permission.CAMERA -> CameraPermissionInfoProvider()
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE -> WriteExternalStoragePermissionProvider()
        android.Manifest.permission.READ_EXTERNAL_STORAGE -> ReadExternalMessagePermissionProvider()
        android.Manifest.permission.READ_MEDIA_IMAGES -> ReadMediaImagesPermissionProvider()
        else -> throw IllegalArgumentException("Unknown permission $permission")
    }
}