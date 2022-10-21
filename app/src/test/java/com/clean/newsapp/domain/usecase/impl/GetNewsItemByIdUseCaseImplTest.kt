package com.clean.newsapp.domain.usecase.impl

import com.google.common.truth.Truth.assertThat
import com.clean.newsapp.base.MainCoroutinesRule
import com.clean.newsapp.base.MockDataUtil
import com.clean.newsapp.common.ResultState
import com.clean.newsapp.data.local.entities.asExternal
import com.clean.newsapp.data.repository.contract.NewsFeedRepository
import com.clean.newsapp.domain.contract.GetNewsItemByIdUseCase
import com.clean.newsapp.domain.impl.GetNewsItemByIdUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

@OptIn(ExperimentalCoroutinesApi::class)
class GetNewsItemByIdUseCaseImplTest {

    @get:Rule
    var coroutineRule = MainCoroutinesRule()

    @Mock
    lateinit var newsFeedRepository: NewsFeedRepository

    private lateinit var getNewsItemByIdUseCase: GetNewsItemByIdUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getNewsItemByIdUseCase = GetNewsItemByIdUseCaseImpl(
            newsFeedRepository,
            coroutineRule.testDispatcher
        )
    }

    @Test
    fun `test get news item by id success`() = runTest {
        // Given
        val newsItemById = 1
        val newsItemEntity = MockDataUtil.newsItemEntity

        // When
        whenever(newsFeedRepository.getNewsItemById(newsItemById)).thenReturn(newsItemEntity)
        val testResult = getNewsItemByIdUseCase.invoke(newsItemById).toList()

        // Then
        assertThat(testResult.first()).isInstanceOf(ResultState.Loading::class.java)
        assertThat(testResult.last()).isInstanceOf(ResultState.Success::class.java)
        assertThat((testResult.last() as ResultState.Success).data).isEqualTo(newsItemEntity.asExternal())
        assertThat((testResult.last() as ResultState.Success).data.id).isEqualTo(newsItemEntity.id)
        verify(newsFeedRepository, times(1)).getNewsItemById(newsItemById)
    }

    @Test
    fun `test get news item by id failure`() = runTest {
        // Given
        val newsItemById = 1
        val errorMessage = "Something went wrong!"

        // When
        whenever(newsFeedRepository.getNewsItemById(newsItemById)).thenReturn(null)
        val testResult = getNewsItemByIdUseCase.invoke(newsItemById).toList()

        // Then
        testResult.run {
            assertThat(first()).isInstanceOf(ResultState.Loading::class.java)
            assertThat(last()).isInstanceOf(ResultState.Failure::class.java)
            assertThat((last() as ResultState.Failure).errorMessage).isEqualTo(errorMessage)
        }
        verify(newsFeedRepository, times(1)).getNewsItemById(newsItemById)
    }
}
