package com.clean.newsapp.data.remote.model

import com.clean.newsapp.data.local.entities.NewsItemEntity

/**
 * Network representation of [NewsItem]
 */

data class NetworkNewsItem(
    val id: Int,
    val author: String,
    val content: String,
    val description: String,
    val title: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)

/**
 * Converts the network model to the local model for persisting
 * by the local data source
 */

fun NetworkNewsItem.asEntity() = NewsItemEntity(
    id = id,
    author = author,
    content = content,
    description = description,
    title = title,
    url = url,
    urlToImage = urlToImage,
    publishedAt = publishedAt
)


