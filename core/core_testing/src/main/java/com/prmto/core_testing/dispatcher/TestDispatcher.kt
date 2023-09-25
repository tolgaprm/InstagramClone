package com.prmto.core_testing.dispatcher

import com.prmto.core_domain.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcher : DispatcherProvider {
    override val io: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val main: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val default: CoroutineDispatcher
        get() = StandardTestDispatcher()
    override val unconfined: CoroutineDispatcher
        get() = StandardTestDispatcher()
}