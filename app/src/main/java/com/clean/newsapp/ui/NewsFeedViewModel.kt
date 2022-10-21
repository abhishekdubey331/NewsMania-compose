package com.clean.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.newsapp.common.ResultState
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.domain.contract.GetNewsItemByIdUseCase
import com.clean.newsapp.ui.detail.state.NewsDetailScreenState
import com.clean.newsapp.ui.feed.state.NewsFeedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val getNewsFeedUseCase: GetNewsFeedUseCase,
    private val getNewsItemByIdUseCase: GetNewsItemByIdUseCase
) : ViewModel() {

    private val _newsFeedScreenMutableState = MutableStateFlow(NewsFeedScreenState())
    val newsFeedScreenState = _newsFeedScreenMutableState.asStateFlow()

    private val _newsDetailScreenMutableState = MutableStateFlow(
        NewsDetailScreenState(isLoading = true)
    )
    val newsDetailScreenState = _newsDetailScreenMutableState.asStateFlow()

    init {
        fetchNewsFeed()
    }

    /***
     *   Fetches the News Feed from Remote/Local datasource
     *   Based on network status
     */
    fun fetchNewsFeed() {
        viewModelScope.launch {
            getNewsFeedUseCase().collect { result ->
                _newsFeedScreenMutableState.update {
                    when (result) {
                        is ResultState.Loading -> it.copy(
                            isLoading = true, errorMessage = null
                        )

                        is ResultState.Success -> it.copy(
                            newsFeed = result.data, isLoading = false
                        )

                        is ResultState.Failure -> it.copy(
                            errorMessage = result.errorMessage, isLoading = false
                        )
                    }
                }
            }
        }
    }

    /***
     *   Fetches the News Item by Id from Local datasource
     */
    fun getNewsItemById(newsItemId: Int) {
        viewModelScope.launch {
            getNewsItemByIdUseCase(newsItemId).collect { result ->
                _newsDetailScreenMutableState.update {
                    when (result) {
                        is ResultState.Loading -> it.copy(isLoading = true)

                        is ResultState.Success -> it.copy(newsItem = result.data, isLoading = false)

                        is ResultState.Failure -> it.copy(newsItem = null, isLoading = false)
                    }
                }
            }
        }
    }
}
