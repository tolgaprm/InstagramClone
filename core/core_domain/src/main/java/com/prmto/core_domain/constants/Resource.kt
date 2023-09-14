package com.prmto.core_domain.constants

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    class Error<T>(val uiText: UiText) : Resource<T>
}

typealias SimpleResource = Resource<Unit>

inline fun <T> Resource<T>.onSuccess(crossinline action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        action(data)
    }
    return this
}

inline fun <T> Resource<T>.onError(crossinline action: (UiText) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        action(uiText)
    }
    return this
}