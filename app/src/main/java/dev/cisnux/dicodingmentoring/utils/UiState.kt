package dev.cisnux.dicodingmentoring.utils

sealed class UiState<out T: Any?> {

    object Loading : UiState<Nothing>()

    data class Success<out T: Any>(val data: T? = null) : UiState<T>()

    data class Error(val error: Exception?) : UiState<Nothing>()
}