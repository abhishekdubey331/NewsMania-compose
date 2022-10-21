package com.clean.newsapp.data.repository.contract

import com.clean.newsapp.data.local.entities.NewsItemEntity
import com.clean.newsapp.data.model.NewsItem
import com.clean.newsapp.data.remote.model.NetworkNewsItem

interface NewsFeedRepository {

    suspend fun fetchNewsFeedRemote(): List<NetworkNewsItem>

    suspend fun fetchNewsFeedLocal(): List<NewsItem>

    suspend fun saveNewsFeedInCache(newsFeed: List<NetworkNewsItem>)

    suspend fun getNewsItemById(newsItemId: Int): NewsItemEntity?

    suspend fun newsFeedCacheAvailable(): Boolean
}
