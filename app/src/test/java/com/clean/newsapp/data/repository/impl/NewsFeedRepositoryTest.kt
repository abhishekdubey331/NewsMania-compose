package com.clean.newsapp.data.repository.impl

import com.google.common.truth.Truth.assertThat
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.data.local.datasource.LocalDataSource
import com.clean.newsapp.data.local.entities.NewsItemEntity
import com.clean.newsapp.data.local.entities.asExternal
import com.clean.newsapp.data.remote.datasource.RemoteDataSource
import com.clean.newsapp.data.remote.model.NetworkNewsItem
import com.clean.newsapp.data.remote.model.asEntity
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class NewsFeedRepositoryTest {

    private lateinit var newsFeedRepository: NewsFeedRepository

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        newsFeedRepository = NewsFeedRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `test fetch news feed remote returns success`() = runTest {
        // Given
        val sampleResult = listOf(MockDataUtil.newsItemRemote)

        // When
        whenever(remoteDataSource.fetchNewsFeed()).thenReturn(sampleResult)
        val testResult = newsFeedRepository.fetchNewsFeedRemote()

        // Then
        assertThat(testResult.size).isEqualTo(sampleResult.size)
        assertThat(testResult).isEqualTo(sampleResult)
        Mockito.verify(remoteDataSource, Mockito.times(1)).fetchNewsFeed()
    }

    @Test
    fun `test fetch news feed local returns news success`() = runTest {
        // Given
        val sampleResult = listOf(MockDataUtil.newsItemRemote.asEntity())

        // When
        whenever(localDataSource.getNewsFeed()).thenReturn(sampleResult)
        val testResult = newsFeedRepository.fetchNewsFeedLocal()

        // Then
        assertThat(testResult.size).isEqualTo(sampleResult.size)
        assertThat(testResult).isEqualTo(sampleResult.map(NewsItemEntity::asExternal))
        Mockito.verify(localDataSource, Mockito.times(1)).getNewsFeed()
    }

    @Test
    fun `test news feed local cache available returns true`() = runTest {
        // Given
        val cachedFeedAvailable = true

        // When
        whenever(localDataSource.isCachedFeedAvailable()).thenReturn(cachedFeedAvailable)
        val testResult = newsFeedRepository.newsFeedCacheAvailable()

        // Then
        assertThat(testResult).isEqualTo(cachedFeedAvailable)
        Mockito.verify(localDataSource, Mockito.times(1)).isCachedFeedAvailable()
    }

    @Test
    fun `test save news feed in local cache`() = runTest {
        // Given
        val remoteFeed = listOf(MockDataUtil.newsItemRemote)

        // Invoke
        newsFeedRepository.saveNewsFeedInCache(remoteFeed)

        // Then
        Mockito.verify(localDataSource, Mockito.times(1))
            .saveNewsFeedLocally(remoteFeed.map(NetworkNewsItem::asEntity))
    }
}
