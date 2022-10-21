package com.clean.newsapp.di

import android.content.Context
import com.clean.newsapp.util.DateTimeUtil
import com.clean.newsapp.util.NetworkStatusUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNetworkStatusUtil(
        @ApplicationContext context: Context
    ): NetworkStatusUtil = NetworkStatusUtil(context)

    @Provides
    @Singleton
    fun providesDateTimeUtil(): DateTimeUtil = DateTimeUtil()
}
