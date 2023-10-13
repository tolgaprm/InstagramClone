package com.prmto.share_presentation.navigation

internal sealed class ShareNestedScreens(val route: String) {
    data object PostCameraScreen : ShareNestedScreens("post_camera_screen")

    data object PostGalleryScreen : ShareNestedScreens("post_gallery_screen")
}
