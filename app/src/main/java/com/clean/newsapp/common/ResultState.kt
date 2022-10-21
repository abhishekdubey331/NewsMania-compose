package com.clean.newsapp.common

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Failure(val errorMessage: String?) : ResultState<Nothing>()
}
