package com.prmto.gallery.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
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
    val permissionState = handleFilePermissionAccess()
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

    ObservePermissionStatusAndTriggerEvent(
        permissionIsGranted = permissionState.status.isGranted,
        onEvent = viewModel::onEvent
    )
}

/***
 * Observe lifecycle events, when lifecycle is created and permission is granted
 * trigger the [SelectProfileImageGalleryEvent.AllPermissionsGranted] event
 * to get the list of albums and images in the first album
 */
@Composable
fun ObservePermissionStatusAndTriggerEvent(
    permissionIsGranted: Boolean,
    onEvent: (SelectProfileImageGalleryEvent) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner
    ) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    if (permissionIsGranted) {
                        onEvent(SelectProfileImageGalleryEvent.AllPermissionsGranted)
                    }
                }

                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}