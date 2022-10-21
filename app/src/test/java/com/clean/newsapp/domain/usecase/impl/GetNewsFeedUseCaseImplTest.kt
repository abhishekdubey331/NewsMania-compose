package com.clean.newsapp.domain.usecase.impl

import com.google.common.truth.Truth.assertThat
import com.clean.newsapp.base.MainCoroutinesRule
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.common.ResultState
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.domain.impl.GetNewsFeedUseCaseImpl
import com.clean.newsapp.util.NetworkStatusUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetNewsFeedUseCaseImplTest {

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Mock
    lateinit var newsFeedRepository: NewsFeedRepository

    @Mock
    lateinit var networkStatusUtil: NetworkStatusUtil

    private lateinit var getNewsFeedUseCase: GetNewsFeedUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getNewsFeedUseCase = GetNewsFeedUseCaseImpl(
            newsFeedRepository,
            networkStatusUtil,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun `test fetch news feed remote if internet is available`() = runTest {
        // Given
        val remoteNewsFeed = listOf(MockDataUtil.newsItemRemote)

        // When
        whenever(networkStatusUtil.isNetworkAvailable()).thenReturn(true)
        whenever(newsFeedRepository.newsFeedCacheAvailable()).thenReturn(true)
        whenever(newsFeedRepository.fetchNewsFeedRemote()).thenReturn(remoteNewsFeed)
        val testResult = getNewsFeedUseCase.invoke().toList()

        // Then
        assertThat(testResult.first()).isInstanceOf(ResultState.Loading::class.java)
        assertThat(testResult.last()).isInstanceOf(ResultState.Success::class.java)
        assertThat((testResult.last() as ResultState.Success).data.first())
            .isEqualTo(MockDataUtil.mockNewsFeed.first())
        verify(newsFeedRepository, times(1)).fetchNewsFeedRemote()
        verify(newsFeedRepository, times(0)).fetchNewsFeedLocal()
    }

    @Test
    fun `test fetch news feed locally if no internet and cached data is present`() = runTest {
        // Given
        val cachedNewsFeed = MockDataUtil.mockNewsFeed

        // When
        whenever(networkStatusUtil.isNetworkAvailable()).thenReturn(false)
        whenever(newsFeedRepository.newsFeedCacheAvailable()).thenReturn(true)
        whenever(newsFeedRepository.fetchNewsFeedLocal()).thenReturn(cachedNewsFeed)
        val testResult = getNewsFeedUseCase.invoke().toList()

        // Then
        assertThat(testResult.first()).isInstanceOf(ResultState.Loading::class.java)
        assertThat(testResult.last()).isInstanceOf(ResultState.Success::class.java)
        assertThat((testResult.last() as ResultState.Success).data).isEqualTo(cachedNewsFeed)
        verify(newsFeedRepository, times(1)).fetchNewsFeedLocal()
        verify(newsFeedRepository, times(0)).fetchNewsFeedRemote()
    }

    @Test
    fun `test fetch news feed returns error if internet is available but http error occurred`() =
        runTest {
            // Given
            val sampleErrorResponse = "Something went wrong!"
            val body = "Test Error Message".toResponseBody("text/html".toMediaTypeOrNull())
            val httpException = HttpException(Response.error<ResponseBody>(500, body))

            // When
            whenever(networkStatusUtil.isNetworkAvailable()).thenReturn(true)
            whenever(newsFeedRepository.newsFeedCacheAvailable()).thenReturn(true)
            whenever(newsFeedRepository.fetchNewsFeedRemote()).thenThrow(httpException)
            val testResult = getNewsFeedUseCase.invoke().toList()

            // Then
            // Then
            testResult.run {
                assertThat(first()).isInstanceOf(ResultState.Loading::class.java)
                assertThat(last()).isInstanceOf(ResultState.Failure::class.java)
                assertThat((last() as ResultState.Failure).errorMessage).isEqualTo(
                    sampleErrorResponse
                )
            }
            verify(newsFeedRepository, times(1)).fetchNewsFeedRemote()
        }
}
