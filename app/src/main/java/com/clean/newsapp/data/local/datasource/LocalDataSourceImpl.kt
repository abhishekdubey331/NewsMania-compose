package com.clean.newsapp.data.local.datasource

import com.clean.newsapp.data.local.db.NewsDao
import com.clean.newsapp.data.local.entities.NewsItemEntity
import javax.inject.Inject


class LocalDataSourceImpl @Inject constructor(
    private val newsDao: NewsDao
) : LocalDataSource {

    override suspend fun getNewsFeed() = newsDao.getNewsFeed()

    override suspend fun saveNewsFeedLocally(
        networkNewsItem: List<NewsItemEntity>
    ) = newsDao.upsertNewsFeed(networkNewsItem)

    override suspend fun getNewsItemById(newsItemId: Int) = newsDao.getNewsItemById(newsItemId)

    override suspend fun isCachedFeedAvailable() = newsDao.isCachedFeedAvailable()
}
