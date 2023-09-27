package com.prmto.permission.provider

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storage
import androidx.compose.ui.graphics.vector.ImageVector
import com.prmto.core_domain.constants.UiText
import com.prmto.permission.R

class WriteExternalStoragePermissionProvider : PermissionInfoProvider {
    override fun permissionName(): UiText {
        return UiText.StringResource(R.string.write_external_storage)
    }

    override fun titleForRationaleMessage(): UiText {
        return UiText.StringResource(R.string.write_external_storage_permission_rationale_title)
    }

    override fun messageForRationaleMessage(): UiText {
        return UiText.StringResource(R.string.write_external_storage_permission_rationale_message)
    }

    override fun getPermissionIcon(): ImageVector {
        return Icons.Default.Storage
    }
}