package com.prmto.core_data.di

import android.content.Context
import com.prmto.core_data.repository.mediafile.MediaAlbumProviderImpl
import com.prmto.core_domain.dispatcher.DispatcherProvider
import com.prmto.core_domain.repository.mediafile.MediaAlbumProvider
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