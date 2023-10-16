package com.prmto.share_presentation.postCamera

import androidx.annotation.StringRes
import com.prmto.share_presentation.R

enum class CameraTab(@StringRes val titleResId: Int) {
    POST(R.string.post),
    STORY(R.string.story),
    REELS(R.string.reels)
}