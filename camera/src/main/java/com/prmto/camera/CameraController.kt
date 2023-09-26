package com.prmto.camera

import android.net.Uri
import android.util.Rational
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView

interface CameraController {

    var cameraSelector: CameraSelector

    var imageCaptureUseCase: ImageCapture?

    var previewView: PreviewView?

    var capturedPhotoUri: Uri?

    fun startCamera(previewView: PreviewView)

    fun takePhoto(onPhotoCaptured: (Uri) -> Unit)

    fun changeCamera()

    fun setAspectRatio(rational: Rational)

    fun setFlashMode(flashMode: Int)

}