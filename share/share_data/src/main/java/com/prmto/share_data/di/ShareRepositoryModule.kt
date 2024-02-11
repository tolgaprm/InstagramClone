package com.prmto.share_data.di

import com.prmto.share_data.repository.PostRepositoryImpl
import com.prmto.share_domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface ShareRepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository
}