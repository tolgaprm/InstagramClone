package com.prmto.core_domain.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

interface DispatcherProvider {
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
    val Default: CoroutineDispatcher
    val Unconfined: CoroutineDispatcher
}

class DefaultDispatcherProvider @Inject constructor() : DispatcherProvider {
    override val IO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val Main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val Default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val Unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}