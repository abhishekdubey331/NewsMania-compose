package com.clean.newsapp.domain.impl

import com.clean.newsapp.common.ResultState
import com.clean.newsapp.data.local.entities.asExternal
import com.clean.newsapp.data.remote.model.NetworkNewsItem
import com.clean.newsapp.data.remote.model.asEntity
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import com.clean.newsapp.di.IoDispatcher
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.util.NetworkStatusUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNewsFeedUseCaseImpl @Inject constructor(
    private val newsFeedRepository: NewsFeedRepository,
    private val networkStatusUtil: NetworkStatusUtil,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : GetNewsFeedUseCase {

    /***
     *  Internet --> Fetches news feed from Network if internet is available
     *               Caches the results in database
     *               returns the news feed
     *  No Internet --> Return News Feed from Cache
     */
    override fun invoke() = flow {
        emit(ResultState.Loading)
        if (fetchFromCache()) {
            val newsFeedCached = newsFeedRepository.fetchNewsFeedLocal()
            emit(ResultState.Success(newsFeedCached))
        } else {
            try {
                val newsFeedRemote = newsFeedRepository.fetchNewsFeedRemote()
                updateLocalNewsFeed(newsFeedRemote)
                emit(ResultState.Success(newsFeedRemote.map { it.asEntity().asExternal() }))
            } catch (e: HttpException) {
                emit(ResultState.Failure("Something went wrong!"))
            } catch (e: IOException) {
                emit(ResultState.Failure("No internet connection!"))
            }
        }
    }.flowOn(ioDispatcher)

    private suspend fun updateLocalNewsFeed(newsFeedRemote: List<NetworkNewsItem>) {
        newsFeedRepository.saveNewsFeedInCache(newsFeedRemote)
    }

    /***
     *   Checks if internet is available and cached news feed is cached
     */
    private suspend fun fetchFromCache() =
        networkStatusUtil.isNetworkAvailable().not() && newsFeedRepository.newsFeedCacheAvailable()
}
