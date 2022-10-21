package com.clean.newsapp.data.remote.datasource

import com.google.common.truth.Truth.assertThat
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.data.remote.api.NewsApi
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
class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var apiService: NewsApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        remoteDataSource = RemoteDataSourceImpl(apiService)
    }

    @Test
    fun `test fetch news feed from remote success scenario`() = runTest {
        // Given
        val sampleResult = listOf(MockDataUtil.newsItemRemote)

        // When
        whenever(apiService.fetchNewsFeed()).thenReturn(sampleResult)
        val testResult = remoteDataSource.fetchNewsFeed()

        // Then
        assertThat(testResult.size).isEqualTo(sampleResult.size)
        assertThat(testResult).isEqualTo(sampleResult)
        Mockito.verify(apiService, Mockito.times(1)).fetchNewsFeed()
    }
}