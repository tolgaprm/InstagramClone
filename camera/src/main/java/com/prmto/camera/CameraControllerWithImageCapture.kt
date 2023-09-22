package com.prmto.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

class CameraControllerWithImageCapture(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner
) : CameraController {
    override var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override var imageCaptureUseCase: ImageCapture? = null

    override var previewView: PreviewView? = null

    override var capturedPhotoUri: Uri? = null

    override fun startCamera(previewView: PreviewView) {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener(
            {
                val cameraProvider: ProcessCameraProvider =
                    cameraProviderFuture.get()

                val previewUseCase = createPreviewUseCase(previewView = previewView)
                imageCaptureUseCase = ImageCapture.Builder().build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        imageCaptureUseCase,
                        previewUseCase
                    )
                } catch (exc: Exception) {
                    Timber.e(exc, "Use case binding failed")
                }
            },
            ContextCompat.getMainExecutor(context)
        )
    }

    override fun takePhoto(
        onPhotoCaptured: (Uri) -> Unit,
    ) {
        imageCaptureUseCase = imageCaptureUseCase ?: return

        val outputOptions = createOutputOptionsForImage()

        imageCaptureUseCase?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    onPhotoCaptured(outputFileResults.savedUri ?: return)
                    Timber.d("Photo capture succeeded $capturedPhotoUri")
                }

                override fun onError(exception: ImageCaptureException) {
                    Timber.e(exception, "Photo capture failed: " + exception.message)
                }
            }
        )
    }

    override fun changeCamera() {
        cameraSelector = when (cameraSelector) {
            CameraSelector.DEFAULT_FRONT_CAMERA -> CameraSelector.DEFAULT_BACK_CAMERA
            CameraSelector.DEFAULT_BACK_CAMERA -> CameraSelector.DEFAULT_FRONT_CAMERA
            else -> CameraSelector.DEFAULT_BACK_CAMERA
        }
        previewView?.let {
            startCamera(previewView = previewView ?: return)
        }
    }

    private fun createPreviewUseCase(previewView: PreviewView): Preview {
        this.previewView = previewView
        return Preview.Builder().build()
            .also { it.setSurfaceProvider(previewView.surfaceProvider) }
    }

    private fun createContentValues(name: String, nowTimestamp: Long): ContentValues {
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name.plus(".jpg"))
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.DATE_TAKEN, nowTimestamp)
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_DCIM + "/$DirectoryName"
                )
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                put(MediaStore.Images.Media.DATE_TAKEN, nowTimestamp)
                put(MediaStore.Images.Media.DATE_ADDED, nowTimestamp)
                put(MediaStore.Images.Media.DATE_MODIFIED, nowTimestamp)
                put(MediaStore.Images.Media.AUTHOR, context.packageName)
            }
        }
    }

    private fun createOutputOptionsForImage(): ImageCapture.OutputFileOptions {
        val nowTimestamp = System.currentTimeMillis()
        val name = SimpleDateFormat(
            FILENAME_FORMAT,
            Locale.getDefault()
        ).format(nowTimestamp)

        val contentValues = createContentValues(name = name, nowTimestamp = nowTimestamp)

        val imageCollection: Uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> MediaStore.Images.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            )

            else -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        return ImageCapture.OutputFileOptions.Builder(
            context.contentResolver,
            imageCollection,
            contentValues
        ).build()
    }

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val DirectoryName = "InstagramClone-Images"
    }
}