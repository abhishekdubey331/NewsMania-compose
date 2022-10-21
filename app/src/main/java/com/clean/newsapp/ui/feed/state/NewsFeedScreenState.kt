package com.clean.newsapp.ui.feed.state

import com.clean.newsapp.data.model.NewsItem

data class NewsFeedScreenState(
    val errorMessage: String? = null,
    val newsFeed: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false
)
