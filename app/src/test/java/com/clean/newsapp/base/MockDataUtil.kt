package com.clean.newsapp.base

import com.clean.newsapp.data.local.entities.NewsItemEntity
import com.clean.newsapp.data.remote.model.NetworkNewsItem
import com.clean.newsapp.data.model.NewsItem

object MockDataUtil {

    val mockNewsFeed = listOf(
        NewsItem(
            id = 1,
            author = "Engadget",
            content = "Amazon is kicking off this year's holiday shopping season with its Prime Early Access Sale.",
            description = "Amazon is Back with a second Prime Day this October. Technically, it's called the Prime Early Access Sale, but let's be Prime Day 2.",
            title = "44 Absolute Best Prime Day Deals (2022): Prime Early Access Sale",
            moreInfoUrl = "https://www.wired.com/story/best-prime-day-deals-2022-8",
            newsMediaImageUrl = "https://images.unsplash.com/photo-1633174524827-db00a6b7bc74?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1792&q=80",
            publishedAt = "2022-10-11T11:00:11Z"
        ),
        NewsItem(
            id = 2,
            author = "Scott Gilbertson",
            content = "Amazon is kicking off this year's holiday shopping season with its Prime Early Access Sale.",
            description = "Amazon is Back with a second Prime Day this October. Technically, it's called the Prime Early Access Sale, but let's be Prime Day 2.",
            title = "44 Absolute Best Prime Day Deals (2022): Prime Early Access Sale",
            moreInfoUrl = "https://www.wired.com/story/best-prime-day-deals-2022-8",
            newsMediaImageUrl = "https://images.unsplash.com/photo-1633174524827-db00a6b7bc74?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1792&q=80",
            publishedAt = "2022-10-11T11:00:11Z"
        ),
        NewsItem(
            id = 3,
            author = "Sheena Vasani",
            content = "Amazon is kicking off this year's holiday shopping season with its Prime Early Access Sale.",
            description = "Amazon is Back with a second Prime Day this October. Technically, it's called the Prime Early Access Sale, but let's be Prime Day 2.",
            title = "44 Absolute Best Prime Day Deals (2022): Prime Early Access Sale",
            moreInfoUrl = "https://www.wired.com/story/best-prime-day-deals-2022-8",
            newsMediaImageUrl = "https://images.unsplash.com/photo-1633174524827-db00a6b7bc74?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1792&q=80",
            publishedAt = "2022-10-11T11:00:11Z"
        )
    )

    val newsItemEntity = NewsItemEntity(
        id = 1,
        author = "Engadget",
        content = "Amazon is kicking off this year's holiday shopping season with its Prime Early Access Sale.",
        description = "Amazon is Back with a second Prime Day this October. Technically, it's called the Prime Early Access Sale, but let's be Prime Day 2.",
        title = "44 Absolute Best Prime Day Deals (2022): Prime Early Access Sale",
        url = "https://www.wired.com/story/best-prime-day-deals-2022-8",
        urlToImage = "https://images.unsplash.com/photo-1633174524827-db00a6b7bc74?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1792&q=80",
        publishedAt = "2022-10-11T11:00:11Z"
    )

    val newsItemRemote = NetworkNewsItem(
        id = 1,
        author = "Engadget",
        content = "Amazon is kicking off this year's holiday shopping season with its Prime Early Access Sale.",
        description = "Amazon is Back with a second Prime Day this October. Technically, it's called the Prime Early Access Sale, but let's be Prime Day 2.",
        title = "44 Absolute Best Prime Day Deals (2022): Prime Early Access Sale",
        url = "https://www.wired.com/story/best-prime-day-deals-2022-8",
        urlToImage = "https://images.unsplash.com/photo-1633174524827-db00a6b7bc74?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1792&q=80",
        publishedAt = "2022-10-11T11:00:11Z"
    )
}
