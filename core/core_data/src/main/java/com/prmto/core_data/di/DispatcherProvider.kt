package com.prmto.core_data.di

import com.prmto.core_domain.dispatcher.DefaultDispatcherProvider
import com.prmto.core_domain.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DispatcherProviderModule {
    @Binds
    fun bindsDispatcherProvider(
        defaultDispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider
}