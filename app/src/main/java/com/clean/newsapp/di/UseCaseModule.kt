package com.clean.newsapp.di

import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.domain.impl.GetNewsFeedUseCaseImpl
import com.clean.newsapp.util.NetworkStatusUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun getNewsFeedUseCase(
        newsFeedRepository: NewsFeedRepository,
        networkStatusUtil: NetworkStatusUtil,
        @IoDispatcher coroutineDispatcher: CoroutineDispatcher
    ): GetNewsFeedUseCase = GetNewsFeedUseCaseImpl(
        newsFeedRepository, networkStatusUtil, coroutineDispatcher
    )
}
