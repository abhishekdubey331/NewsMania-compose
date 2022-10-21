package com.clean.newsapp.data.local.datasource

import com.clean.newsapp.data.local.entities.NewsItemEntity

interface LocalDataSource {

    suspend fun getNewsFeed(): List<NewsItemEntity>

    suspend fun saveNewsFeedLocally(networkNewsItem: List<NewsItemEntity>)

    suspend fun getNewsItemById(newsItemId: Int): NewsItemEntity

    suspend fun isCachedFeedAvailable(): Boolean
}