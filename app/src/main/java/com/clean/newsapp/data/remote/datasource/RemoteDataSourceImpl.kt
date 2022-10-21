package com.clean.newsapp.data.remote.datasource

import com.clean.newsapp.data.remote.api.NewsApi
import com.clean.newsapp.data.remote.model.NetworkNewsItem
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: NewsApi
) : RemoteDataSource {

    override suspend fun fetchNewsFeed(): List<NetworkNewsItem> {
        return apiService.fetchNewsFeed()
    }
}