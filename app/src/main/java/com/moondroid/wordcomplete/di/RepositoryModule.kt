package com.moondroid.wordcomplete.di

import com.moondroid.wordcomplete.data.repository.RepositoryImpl
import com.moondroid.wordcomplete.domain.respository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAppRepository(repository: RepositoryImpl): Repository
}