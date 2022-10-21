package com.clean.newsapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clean.newsapp.data.model.NewsItem

@Entity(tableName = "newsItem")
data class NewsItemEntity(
    @PrimaryKey
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
 * Converts the local model to the external model for use
 * by layers external to the data layer
 */

fun NewsItemEntity.asExternal() = NewsItem(
    id = id,
    author = author,
    content = content,
    description = description,
    title = title,
    moreInfoUrl = url,
    newsMediaImageUrl = urlToImage,
    publishedAt = publishedAt
)
