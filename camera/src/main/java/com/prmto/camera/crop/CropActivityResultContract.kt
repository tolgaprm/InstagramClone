package com.prmto.camera.crop

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.result.contract.ActivityResultContract
import com.yalantis.ucrop.UCrop
import java.io.File

class CropActivityResultContract(
    private val aspectRatioX: Float = 1f,
    private val aspectRatioY: Float = 1f,
    private val isCircle: Boolean = false
) : ActivityResultContract<Uri, Uri?>() {
    override fun createIntent(context: Context, input: Uri): Intent {
        return UCrop.of(
            input,
            Uri.fromFile(
                File(
                    context.cacheDir,
                    context.contentResolver.getFileName(input)
                )
            )
        ).withAspectRatio(aspectRatioX, aspectRatioY)
            .withOptions(
                UCrop.Options().apply {
                    setHideBottomControls(true)
                    setCircleDimmedLayer(isCircle)
                }
            )
            .getIntent(context)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return UCrop.getOutput(intent ?: return null)
    }
}

private fun ContentResolver.getFileName(uri: Uri): String {
    val returnCursor = query(uri, null, null, null, null) ?: return ""
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}