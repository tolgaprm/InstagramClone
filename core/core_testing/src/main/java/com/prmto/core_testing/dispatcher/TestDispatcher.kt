package com.prmto.core_testing.dispatcher

import com.prmto.core_domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcher : DispatcherProvider {
    override val IO: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val Main: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val Default: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val Unconfined: CoroutineDispatcher
        get() = StandardTestDispatcher()
}