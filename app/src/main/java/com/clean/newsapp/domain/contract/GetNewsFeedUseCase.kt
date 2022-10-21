package com.clean.newsapp.domain.contract


import com.clean.newsapp.common.ResultState
import com.clean.newsapp.data.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface GetNewsFeedUseCase {
    operator fun invoke(): Flow<ResultState<List<NewsItem>>>
}
