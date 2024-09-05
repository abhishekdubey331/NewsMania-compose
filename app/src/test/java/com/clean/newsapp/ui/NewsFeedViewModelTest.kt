package com.clean.newsapp.ui

import com.clean.newsapp.base.MainCoroutinesRule
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.common.ResultState
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.ui.feed.state.NewsFeedScreenState
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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

    @get:Rule
    val dispatcherRule = MainCoroutinesRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        newsFeedViewModel = NewsFeedViewModel(getNewsFeedUseCase)
    }

    @Test
    fun `test fetch news feed success`() = runTest {
        // Given
        val mockNewsFeed = MockDataUtil.mockNewsFeed
        val newsFeedFlow = flow {
            emit(ResultState.Success(mockNewsFeed))
        }

        // When
        println(getNewsFeedUseCase.hashCode())
        whenever(getNewsFeedUseCase()).thenReturn(newsFeedFlow)

        // Invoke
        newsFeedViewModel.fetchNewsFeed()

        // Then
        assertThat(newsFeedViewModel.newsFeedScreenState.value).isEqualTo(
            NewsFeedScreenState.Loaded(mockNewsFeed)
        )
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
        newsFeedViewModel.fetchNewsFeed()

        // Then
        assertThat(newsFeedViewModel.newsFeedScreenState.value).isEqualTo(
            NewsFeedScreenState.LoadFailed(sampleErrorResponse)
        )
    }
}
