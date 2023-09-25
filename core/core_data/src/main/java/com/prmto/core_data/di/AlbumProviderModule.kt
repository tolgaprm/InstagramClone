package com.prmto.core_data.di

import android.content.Context
import com.prmto.core_data.common.MediaAlbumProviderImpl
import com.prmto.core_domain.common.MediaAlbumProvider
import com.prmto.core_domain.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AlbumProviderModule {

    @Provides
    fun bindAlbumProvider(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): MediaAlbumProvider {
        return MediaAlbumProviderImpl(
            context.contentResolver,
            dispatcherProvider
        )
    }
}