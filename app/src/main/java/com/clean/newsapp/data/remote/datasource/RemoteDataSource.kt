package com.clean.newsapp.data.remote.datasource

import com.clean.newsapp.data.remote.model.NetworkNewsItem


interface RemoteDataSource {
    suspend fun fetchNewsFeed(): List<NetworkNewsItem>
}