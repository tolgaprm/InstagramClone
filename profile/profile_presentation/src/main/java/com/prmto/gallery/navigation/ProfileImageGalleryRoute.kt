package com.prmto.gallery.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.prmto.gallery.ProfileImageGalleryScreen
import com.prmto.gallery.SelectProfileImageGalleryEvent
import com.prmto.gallery.SelectProfileImageGalleryViewModel
import com.prmto.permission.provider.getPermissionInfoProvider
import com.prmto.permission.util.HandlePermissionStatus
import com.prmto.permission.util.handleFilePermissionAccess
import com.prmto.permission.util.permissionToRequestForFile

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun ProfileImageGalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: SelectProfileImageGalleryViewModel = hiltViewModel(),
    onPopBacStack: () -> Unit,
    onPopBackStackWithSelectedUri: (selectedPhotoUri: Uri) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionState = handleFilePermissionAccess(
        onPermissionGranted = {
            viewModel.onEvent(SelectProfileImageGalleryEvent.AllPermissionsGranted)
        }
    )
    val permissionProvider = getPermissionInfoProvider(permissionToRequestForFile())
    ProfileImageGalleryScreen(
        modifier = modifier,
        uiState = uiState,
        permissionIsGranted = permissionState.status.isGranted,
        onPopBackStack = onPopBacStack,
        onEvent = viewModel::onEvent,
        handlePermission = {
            HandlePermissionStatus(
                permissionState = permissionState,
                permissionProvider = permissionProvider
            )
        }
    )

    LaunchedEffect(key1 = uiState.croppedImageUri) {
        uiState.croppedImageUri?.let {
            onPopBackStackWithSelectedUri(it)
        }
    }
}