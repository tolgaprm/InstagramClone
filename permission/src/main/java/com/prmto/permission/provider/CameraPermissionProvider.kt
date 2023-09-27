package com.prmto.permission.provider

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.ui.graphics.vector.ImageVector
import com.prmto.core_domain.constants.UiText
import com.prmto.permission.R

class CameraPermissionInfoProvider : PermissionInfoProvider {
    override fun permissionName(): UiText {
        return UiText.StringResource(R.string.camera)
    }

    override fun titleForRationaleMessage(): UiText {
        return UiText.StringResource(R.string.camera_permission_rationale_title)
    }

    override fun messageForRationaleMessage(): UiText {
        return UiText.StringResource(R.string.camera_permission_rationale_message)
    }

    override fun getPermissionIcon(): ImageVector {
        return Icons.Default.PhotoCamera
    }
}