package com.prmto.gallery.navigation

import android.Manifest
import android.os.Build
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
import com.google.accompanist.permissions.rememberPermissionState
import com.prmto.gallery.ProfileImageGalleryScreen
import com.prmto.gallery.SelectProfileImageGalleryEvent
import com.prmto.gallery.SelectProfileImageGalleryViewModel
import com.prmto.permission.provider.getPermissionInfoProvider
import com.prmto.permission.util.HandlePermissionStatus

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun ProfileImageGalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: SelectProfileImageGalleryViewModel = hiltViewModel(),
    onPopBacStack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val permissionsToRequest = getPermissionToRequest()
    val permissionState = rememberPermissionState(permission = permissionsToRequest) {
        if (it) {
            viewModel.onEvent(SelectProfileImageGalleryEvent.AllPermissionsGranted)
        }
    }
    val permissionProvider = getPermissionInfoProvider(permissionsToRequest)
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

    // Request permission when the composable is first launched and if the permission is not granted
    LaunchedEffect(key1 = Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    ObservePermissionStatusAndTriggerEvent(
        permissionIsGranted = permissionState.status.isGranted,
        onEvent = viewModel::onEvent
    )
}

private fun getPermissionToRequest(): String {
    return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        Manifest.permission.READ_EXTERNAL_STORAGE
    } else {
        Manifest.permission.READ_MEDIA_IMAGES
    }
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