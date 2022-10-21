package com.clean.newsapp.data.repository.impl

import com.clean.newsapp.data.local.datasource.LocalDataSource
import com.clean.newsapp.data.local.entities.NewsItemEntity
import com.clean.newsapp.data.local.entities.asExternal
import com.clean.newsapp.data.remote.datasource.RemoteDataSource
import com.clean.newsapp.data.remote.model.NetworkNewsItem
import com.clean.newsapp.data.remote.model.asEntity
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : NewsFeedRepository {

    /***
     *   Fetches News Feed from Remote
     */
    override suspend fun fetchNewsFeedRemote() = remoteDataSource
        .fetchNewsFeed()

    /***
     *   Fetches News Feed from Local Database
     */
    override suspend fun fetchNewsFeedLocal() = localDataSource
        .getNewsFeed()
        .map(NewsItemEntity::asExternal)

    /***
     *   Save news feed in database via Upsert
     */
    override suspend fun saveNewsFeedInCache(newsFeed: List<NetworkNewsItem>) = localDataSource
        .saveNewsFeedLocally(newsFeed.map(NetworkNewsItem::asEntity))

    /***
     *   Returns news item via id from database
     */
    override suspend fun getNewsItemById(newsItemId: Int) = localDataSource
        .getNewsItemById(newsItemId)

    /***
     *   Checks if cached feed is available locally
     */
    override suspend fun newsFeedCacheAvailable() = localDataSource
        .isCachedFeedAvailable()
}
