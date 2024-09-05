package com.clean.newsapp.ui.feed.state

import com.clean.newsapp.data.model.NewsItem

/**
 * A sealed hierarchy describing the state of the news feed screen.
 */
sealed interface NewsFeedScreenState {

    /**
     * The news feed state is loading.
     */
    data object Loading : NewsFeedScreenState

    /**
     * The news feed state was unable to load.
     */
    data class LoadFailed(val errorMessage: String) : NewsFeedScreenState

    /**
     * The news feed has been successfully loaded with the list of news items.
     */
    data class Loaded(val newsFeed: List<NewsItem>) : NewsFeedScreenState
}

