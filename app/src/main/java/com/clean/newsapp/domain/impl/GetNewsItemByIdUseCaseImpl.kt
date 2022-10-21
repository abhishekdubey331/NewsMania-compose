package com.clean.newsapp.domain.impl

import com.clean.newsapp.common.ResultState
import com.clean.newsapp.data.local.entities.asExternal
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import com.clean.newsapp.di.IoDispatcher
import com.clean.newsapp.domain.contract.GetNewsItemByIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNewsItemByIdUseCaseImpl @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetNewsItemByIdUseCase {

    override fun invoke(newsItemId: Int) = flow {
        emit(ResultState.Loading)
        val newsItem = newsFeedRepository.getNewsItemById(newsItemId)
        if (newsItem != null) {
            emit(ResultState.Success(newsItem.asExternal()))
        } else {
            emit(ResultState.Failure("Something went wrong!"))
        }
    }.flowOn(ioDispatcher)
}
