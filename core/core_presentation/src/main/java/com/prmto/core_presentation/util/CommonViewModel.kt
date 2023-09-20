package com.prmto.core_presentation.util

import androidx.lifecycle.ViewModel
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class CommonViewModel<Event> : ViewModel() {
    private val _consumableViewEvents = MutableStateFlow<List<Event>>(emptyList())
    val consumableViewEvents: StateFlow<List<Event>> = _consumableViewEvents.asStateFlow()

    /**
     * Handles a resource operation with optional success and error callbacks.
     *
     * This function is designed to simplify resource handling by providing callbacks for both
     * success and error cases. It executes the `resourceSupplier` lambda to obtain the resource,
     * and then it invokes the appropriate callback based on the result.
     *
     * @param resourceSupplier A lambda that supplies the resource to be processed.
     * @param onErrorCallback A callback to be executed in case of an error, with a `UiText` parameter
     *                        representing the error message or text to be displayed.
     * @param onSuccessCallback A callback to be executed when the resource operation succeeds.
     *
     * Usage:
     * ```kotlin
     * handleResourceWithCallbacks(
     *     resourceSupplier = { fetchDataFromServer() },
     *     onErrorCallback = { showErrorToast(it) },
     *     onSuccessCallback = { data ->
     *         displayData(data)
     *     }
     * )
     * ```
     *
     * @param T The type of the resource being handled.
     */
    protected inline fun <T> handleResourceWithCallbacks(
        resourceSupplier: () -> Resource<T>,
        crossinline onSuccessCallback: (T) -> Unit,
        crossinline onErrorCallback: (UiText) -> Unit
    ) {
        when (val response = resourceSupplier()) {
            is Resource.Success -> {
                onSuccessCallback(response.data)
            }

            is Resource.Error -> {
                onErrorCallback(response.uiText)
            }
        }
    }

    protected fun addConsumableViewEvent(uiEvent: Event) {
        _consumableViewEvents.update {
            it.plus(uiEvent)
        }
    }

    fun onEventConsumed() {
        val newConsumableViewEvents = consumableViewEvents.value.toMutableList()
        newConsumableViewEvents.removeFirstOrNull()
        _consumableViewEvents.update { newConsumableViewEvents }
    }
}