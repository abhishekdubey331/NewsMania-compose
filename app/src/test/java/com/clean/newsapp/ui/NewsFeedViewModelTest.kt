package com.clean.newsapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.clean.newsapp.base.MainCoroutinesRule
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.common.ResultState
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.domain.contract.GetNewsItemByIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsFeedViewModelTest {

    private lateinit var newsFeedViewModel: NewsFeedViewModel

    @Mock
    lateinit var getNewsFeedUseCase: GetNewsFeedUseCase

    @Mock
    lateinit var getNewsItemByIdUseCase: GetNewsItemByIdUseCase

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainCoroutinesRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test fetch news feed success`() = runTest {
        // Given
        val newsFeedFlow = flow {
            emit(ResultState.Success(MockDataUtil.mockNewsFeed))
        }

        // When
        whenever(getNewsFeedUseCase()).thenReturn(newsFeedFlow)

        // Invoke
        newsFeedViewModel = NewsFeedViewModel(getNewsFeedUseCase, getNewsItemByIdUseCase)

        // Then
        newsFeedViewModel.run {
            assertThat(newsFeedScreenState.value.newsFeed.size).isEqualTo(MockDataUtil.mockNewsFeed.size)
            assertThat(newsFeedScreenState.value.newsFeed.first()).isEqualTo(MockDataUtil.mockNewsFeed.first())
            assertThat(newsFeedScreenState.value.newsFeed.last()).isEqualTo(MockDataUtil.mockNewsFeed.last())
        }
    }

    @Test
    fun `test fetch news feed failure`() = runTest {
        // Given
        val sampleErrorResponse = "Fetch News Feed Failure!"
        val newsFeedFlow = flow {
            emit(ResultState.Failure(sampleErrorResponse))
        }

        // When
        whenever(getNewsFeedUseCase()).thenReturn(newsFeedFlow)

        // Invoke
        newsFeedViewModel = NewsFeedViewModel(getNewsFeedUseCase, getNewsItemByIdUseCase)

        // Then
        newsFeedViewModel.run {
            assertThat(newsFeedScreenState.value.newsFeed.size).isEqualTo(0)
            assertThat(newsFeedScreenState.value.errorMessage).isEqualTo(sampleErrorResponse)
        }
    }

    @Test
    fun `test get news item by Id success`() = runTest {
        // Given
        val newsItemId = 1
        val newsFeedFlow = flow {
            emit(ResultState.Success(MockDataUtil.mockNewsFeed.first()))
        }

        // When
        whenever(getNewsItemByIdUseCase(newsItemId)).thenReturn(newsFeedFlow)

        // Invoke
        newsFeedViewModel = NewsFeedViewModel(getNewsFeedUseCase, getNewsItemByIdUseCase)
        newsFeedViewModel.getNewsItemById(newsItemId)

        // Then
        newsFeedViewModel.run {
            assertThat(newsDetailScreenState.value.newsItem).isEqualTo(MockDataUtil.mockNewsFeed.first())
            assertThat(newsDetailScreenState.value.newsItem).isNotNull()
            assertThat(newsDetailScreenState.value.newsItem?.id).isEqualTo(MockDataUtil.mockNewsFeed.first().id)
        }
    }

    @Test
    fun `test get news item by Id failure`() = runTest {
        // Given
        val newsItemId = -1
        val newsFeedFlow = flow {
            emit(ResultState.Failure("Something went wrong!"))
        }

        // When
        whenever(getNewsItemByIdUseCase(newsItemId)).thenReturn(newsFeedFlow)

        // Invoke
        newsFeedViewModel = NewsFeedViewModel(getNewsFeedUseCase, getNewsItemByIdUseCase)
        newsFeedViewModel.getNewsItemById(newsItemId)

        // Then
        newsFeedViewModel.run {
            assertThat(newsDetailScreenState.value.newsItem).isNull()
        }
    }
}
