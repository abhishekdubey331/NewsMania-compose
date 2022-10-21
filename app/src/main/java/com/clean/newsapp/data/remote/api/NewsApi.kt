package com.clean.newsapp.data.remote.api

import com.clean.newsapp.common.Constants
import com.clean.newsapp.data.remote.model.NetworkNewsItem
import retrofit2.http.GET

interface NewsApi {

    @GET(Constants.NEWS_FEED_URL)
    suspend fun fetchNewsFeed(): List<NetworkNewsItem>
}
