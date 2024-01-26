package domain.utils

sealed interface NetworkUiState<out T> {
    data object Loading : NetworkUiState<Nothing>
    data class Success<T>(val data: T) : NetworkUiState<T>
    data class Error(val code: Int = -1, val error: String) : NetworkUiState<Nothing>
    data object Empty : NetworkUiState<Nothing>
}