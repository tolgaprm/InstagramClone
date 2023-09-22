package com.prmto.camera

import android.content.Context
import android.graphics.Color
import android.widget.LinearLayout
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun InstaCamera(
    modifier: Modifier = Modifier,
    onStartCamera: (PreviewView) -> Unit,
) {
    Scaffold(modifier = modifier) { paddingValues ->
        AndroidView(
            modifier = Modifier.padding(paddingValues),
            factory = { context ->
                val previewView = createPreviewView(context)
                onStartCamera(previewView)
                previewView
            }
        )
    }
}

private fun createPreviewView(context: Context): PreviewView {
    return PreviewView(context).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(Color.BLACK)
        scaleType = PreviewView.ScaleType.FILL_START
    }
}