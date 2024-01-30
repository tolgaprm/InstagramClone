package com.prmto.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun rememberCameraControllerWithImageCapture(): CameraController {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var cameraControllerWithImageCapture: CameraControllerWithImageCapture? = null

    fun createCameraController(): CameraControllerWithImageCapture {
        return CameraControllerWithImageCapture(
            context = context,
            lifecycleOwner = lifecycleOwner
        )
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    cameraControllerWithImageCapture = createCameraController()
                }

                Lifecycle.Event.ON_DESTROY -> {
                    cameraControllerWithImageCapture = null
                }

                else -> Unit
            }
        }
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            cameraControllerWithImageCapture = null
        }
    }

    return remember {
        mutableStateOf(
            cameraControllerWithImageCapture ?: run {
                cameraControllerWithImageCapture = createCameraController()
                cameraControllerWithImageCapture!!
            }
        )
    }.value
}