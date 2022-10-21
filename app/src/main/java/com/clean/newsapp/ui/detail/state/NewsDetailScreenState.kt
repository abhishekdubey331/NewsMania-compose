package com.clean.newsapp.ui.detail.state

import com.clean.newsapp.data.model.NewsItem

data class NewsDetailScreenState(
    val newsItem: NewsItem? = null,
    val isLoading: Boolean = false
)
