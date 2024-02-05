package com.prmto.profile_domain.repository

import android.net.Uri
import com.prmto.core_domain.constants.Resource

interface ProfileRepository {
    suspend fun updateProfileImage(photoUri: Uri): Resource<String>
}