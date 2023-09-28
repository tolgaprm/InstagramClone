package com.prmto.profile_presentation.previewDataProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.prmto.core_domain.model.dummyDataGenerator.userData
import com.prmto.profile_presentation.ProfileUiState

class ProfileUiStatePreviewProvider : PreviewParameterProvider<ProfileUiState> {
    override val values: Sequence<ProfileUiState>
        get() = sequenceOf(
            ProfileUiState(
                userData = userData()
            ),
            ProfileUiState(
                userData = userData(),
                isOwnProfile = true
            )
        )
}