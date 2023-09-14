package com.prmto.core_data.common

import com.google.firebase.FirebaseException
import com.prmto.core_data.R
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import timber.log.Timber
import java.io.IOException

suspend fun <T> safeCallWithTryCatch(
    errorUiText: UiText? = null,
    call: suspend () -> Resource<T>
): Resource<T> {
    return try {
        call()
    } catch (e: FirebaseException) {
        Timber.e(e.localizedMessage)
        if (errorUiText != null) {
            Resource.Error(errorUiText)
        } else {
            Resource.Error(UiText.DynamicString(e.localizedMessage ?: "Unknown error"))
        }
    } catch (e: IOException) {
        Resource.Error(UiText.StringResource(R.string.no_internet_connection))
    }
}