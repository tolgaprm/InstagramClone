package com.prmto.edit_profile_presentation.previewDataProvider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.prmto.core_domain.model.dummyDataGenerator.userDetail
import com.prmto.edit_profile_presentation.EditProfileUiState

class EditProfileUiStatePreviewProvider : PreviewParameterProvider<EditProfileUiState> {
    override val values: Sequence<EditProfileUiState>
        get() = sequenceOf(
            EditProfileUiState(
                isLoading = false,
                userDetail = userDetail(),
                updatedUserDetail = userDetail(),
                isShowSaveButton = false
            )
        )
}