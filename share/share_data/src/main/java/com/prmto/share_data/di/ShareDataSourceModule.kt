package com.prmto.share_data.di

import com.prmto.share_data.remote.datasource.post.PostRemoteDataSource
import com.prmto.share_data.remote.datasource.post.PostRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ShareDataSourceModule {

    @Binds
    @ViewModelScoped
    fun providePostRemoteDataSource(
        postRemoteDataSourceImpl: PostRemoteDataSourceImpl
    ): PostRemoteDataSource
}