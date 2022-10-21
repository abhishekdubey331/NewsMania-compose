package com.clean.newsapp.di

import com.clean.newsapp.data.local.datasource.LocalDataSource
import com.clean.newsapp.data.remote.datasource.RemoteDataSource
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import com.clean.newsapp.data.repository.impl.NewsFeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideNewsFeedRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): NewsFeedRepository = NewsFeedRepositoryImpl(remoteDataSource, localDataSource)
}
