package com.prmto.core_domain.constants

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    class Error<T>(val uiText: UiText) : Resource<T>
}

typealias SimpleResource = Resource<Unit>

fun <T> Resource<T>.onSuccess(action: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        action(data)
    }
    return this
}

fun <T> Resource<T>.onError(action: (UiText) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        action(uiText)
    }
    return this
}