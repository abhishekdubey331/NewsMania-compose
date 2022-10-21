package com.clean.newsapp.di

import com.clean.newsapp.data.local.datasource.LocalDataSource
import com.clean.newsapp.data.local.datasource.LocalDataSourceImpl
import com.clean.newsapp.data.remote.datasource.RemoteDataSource
import com.clean.newsapp.data.remote.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface DataSourceModule {

    @Binds
    fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource

    @Binds
    fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource
}