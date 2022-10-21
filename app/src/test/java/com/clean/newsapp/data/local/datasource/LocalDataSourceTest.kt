package com.clean.newsapp.data.local.datasource

import com.google.common.truth.Truth.assertThat
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.data.local.db.NewsDao
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
class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var newsDao: NewsDao

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        localDataSource = LocalDataSourceImpl(newsDao)
    }

    @Test
    fun `test fetch news feed from local success scenario`() = runTest {
        // Given
        val sampleResult = listOf(MockDataUtil.newsItemEntity)

        // When
        whenever(newsDao.getNewsFeed()).thenReturn(sampleResult)
        val testResult = localDataSource.getNewsFeed()

        // Then
        assertThat(testResult.size).isEqualTo(sampleResult.size)
        assertThat(testResult).isEqualTo(sampleResult)
        Mockito.verify(newsDao, Mockito.times(1)).getNewsFeed()
    }

    @Test
    fun `test fetch news item by id from local success scenario`() = runTest {
        // Given
        val sampleResult = MockDataUtil.newsItemEntity
        val newsItemId = 1

        // When
        whenever(newsDao.getNewsItemById(newsItemId)).thenReturn(sampleResult)
        // Invoke
        val testResult = localDataSource.getNewsItemById(newsItemId)

        // Then
        assertThat(testResult).isEqualTo(sampleResult)
        Mockito.verify(newsDao, Mockito.times(1)).getNewsItemById(newsItemId)
    }

    @Test
    fun `test save news item locally`() = runTest {
        // Given
        val newsFeed = listOf(MockDataUtil.newsItemEntity)

        // When
        whenever(newsDao.upsertNewsFeed(newsFeed)).thenReturn(Unit)
        // Invoke
        localDataSource.saveNewsFeedLocally(newsFeed)

        // Then
        Mockito.verify(newsDao, Mockito.times(1)).upsertNewsFeed(newsFeed)
    }

    @Test
    fun `test local data source returns true if news feed is cached`() = runTest {
        // Given
        val newsFeedCached = true

        // When
        whenever(newsDao.isCachedFeedAvailable()).thenReturn(newsFeedCached)
        // Invoke
        val result = localDataSource.isCachedFeedAvailable()

        // Then
        assertThat(result).isTrue()
        Mockito.verify(newsDao, Mockito.times(1)).isCachedFeedAvailable()
    }
}