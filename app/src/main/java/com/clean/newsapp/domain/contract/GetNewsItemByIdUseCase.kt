package com.clean.newsapp.domain.contract


import com.clean.newsapp.common.ResultState
import com.clean.newsapp.data.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface GetNewsItemByIdUseCase {
    operator fun invoke(newsItemId: Int): Flow<ResultState<NewsItem>>
}
