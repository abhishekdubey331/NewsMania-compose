package com.clean.newsapp.ui.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.clean.newsapp.data.model.NewsItem
import com.clean.newsapp.ui.NewsFeedViewModel
import com.clean.newsapp.ui.feed.state.NewsFeedScreenState
import com.clean.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsFeedScreen(
    modifier: Modifier = Modifier, navigateToDetailScreen: (newsItemId: Int) -> Unit
) {
    val newsFeedViewModel = hiltViewModel<NewsFeedViewModel>()
    val uiState by newsFeedViewModel.newsFeedScreenState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        newsFeedViewModel.fetchNewsFeed()
    }

    NewsFeedContent(
        modifier = modifier.padding(top = 1.dp),
        uiState = uiState,
        onRetryClick = { newsFeedViewModel.fetchNewsFeed() },
        onNewsItemClick = navigateToDetailScreen
    )
}

@Composable
fun NewsFeedContent(
    modifier: Modifier = Modifier,
    uiState: NewsFeedScreenState,
    onRetryClick: () -> Unit,
    onNewsItemClick: (Int) -> Unit
) {
    when (uiState) {
        is NewsFeedScreenState.Loading -> Loading()
        is NewsFeedScreenState.Loaded -> LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(NewsAppTheme.Spacing.sm),
            verticalArrangement = Arrangement.spacedBy(NewsAppTheme.Spacing.sm)
        ) {
            items(uiState.newsFeed) { newsItem ->
                NewsItemRow(
                    newsItem = newsItem, onNewsItemClick = onNewsItemClick
                )
            }
        }

        is NewsFeedScreenState.LoadFailed -> RetryView(onRetryClick)
    }
}

@Composable
fun RetryView(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(NewsAppTheme.Spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Failed to load news. Please try again.")
        Spacer(modifier = Modifier.height(NewsAppTheme.Spacing.md))
        Button(onClick = onRetryClick) {
            Text("Retry")
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NewsItemRow(
    newsItem: NewsItem, onNewsItemClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = NewsAppTheme.Spacing.sm)
            .clickable { onNewsItemClick(newsItem.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = newsItem.newsMediaImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(newAppCardHeight),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(NewsAppTheme.Spacing.sm)
            ) {
                Text(
                    text = newsItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(NewsAppTheme.Spacing.xs))
                Text(
                    text = newsItem.author,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(NewsAppTheme.Spacing.sm))
                Text(
                    text = newsItem.publishedAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private val newAppCardHeight = 200.dp
