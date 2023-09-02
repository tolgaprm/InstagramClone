package com.prmto.core_data.remote.util

import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText

suspend fun <T> safeCallWithTryCatch(
    call: suspend () -> Resource<T>
): Resource<T> {
    return try {
        call()
    } catch (e: Exception) {
        Resource.Error(UiText.DynamicString(e.localizedMessage ?: "Unknown error"))
    }
}