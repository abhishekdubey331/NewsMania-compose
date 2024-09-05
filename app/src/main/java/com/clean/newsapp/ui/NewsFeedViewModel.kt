package com.clean.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clean.newsapp.common.ResultState
import com.clean.newsapp.domain.contract.GetNewsFeedUseCase
import com.clean.newsapp.ui.feed.state.NewsFeedScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val getNewsFeedUseCase: GetNewsFeedUseCase
) : ViewModel() {

    private val _newsFeedScreenMutableState = MutableStateFlow<NewsFeedScreenState>(NewsFeedScreenState.Loading)
    val newsFeedScreenState = _newsFeedScreenMutableState.asStateFlow()

    /***
     *   Fetches the News Feed from Remote/Local datasource
     *   Based on network status
     */
    fun fetchNewsFeed() {
        viewModelScope.launch {
            getNewsFeedUseCase().collect { result ->
                _newsFeedScreenMutableState.update {
                    when (result) {
                        is ResultState.Loading -> NewsFeedScreenState.Loading
                        is ResultState.Success -> NewsFeedScreenState.Loaded(result.data)
                        is ResultState.Failure -> NewsFeedScreenState.LoadFailed(result.errorMessage.orEmpty())
                    }
                }
            }
        }
    }
}
