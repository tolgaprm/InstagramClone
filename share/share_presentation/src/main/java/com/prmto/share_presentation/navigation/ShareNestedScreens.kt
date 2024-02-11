package com.prmto.share_presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.prmto.share_presentation.postPreview.navigation.args.PostPreviewArgs
import com.prmto.share_presentation.postShare.navigation.args.PostShareArgs

internal sealed class ShareNestedScreens(val route: String) {
    data object PostCameraScreen : ShareNestedScreens("post_camera_screen")

    data object PostGalleryScreen : ShareNestedScreens("post_gallery_screen")

    data object PostShareScreen :
        ShareNestedScreens("post_share_screen?${PostShareArgs.postShareArgsPhotoUris}={${PostShareArgs.postShareArgsPhotoUris}}") {

        fun passArguments(photoUris: Array<String>) =
            "post_share_screen?${PostShareArgs.postShareArgsPhotoUris}=${photoUris.joinToString(",")}"

        val arguments = listOf(
            navArgument(
                PostShareArgs.postShareArgsPhotoUris,
            ) {
                type = NavType.StringArrayType
            }
        )
    }

    data object PostPreviewScreen :
        ShareNestedScreens(("post_preview_screen?${PostPreviewArgs.postPreviewArgsPhotoUris}={${PostPreviewArgs.postPreviewArgsPhotoUris}}")) {

        fun passArguments(photoUris: Array<String>) =
            "post_preview_screen?${PostPreviewArgs.postPreviewArgsPhotoUris}=${
                photoUris.joinToString(
                    ","
                )
            }"

        val arguments = listOf(
            navArgument(
                PostPreviewArgs.postPreviewArgsPhotoUris,
            ) {
                type = NavType.StringArrayType
            }
        )
    }
}
