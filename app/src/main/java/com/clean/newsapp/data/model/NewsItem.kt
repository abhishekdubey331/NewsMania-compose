package com.clean.newsapp.data.model

/**
 *  External data layer representation
 *  Source - https://developer.android.com/topic/architecture/data-layer/offline-first#exposing-resources
 */
data class NewsItem(
    val id: Int,
    val author: String,
    val content: String,
    val description: String,
    val title: String,
    val moreInfoUrl: String,
    val newsMediaImageUrl: String,
    val publishedAt: String
)
