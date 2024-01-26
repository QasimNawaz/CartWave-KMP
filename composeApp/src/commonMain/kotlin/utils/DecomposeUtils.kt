package utils

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import domain.model.BaseResponse
import domain.utils.NetworkCall
import domain.utils.NetworkUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext

fun LifecycleOwner.coroutineScope(
    context: CoroutineContext = Dispatchers.Main.immediate,
): CoroutineScope {
    val scope = CoroutineScope(context + SupervisorJob())
    lifecycle.doOnDestroy(scope::cancel)

    return scope
}

suspend fun <T> Flow<NetworkCall<BaseResponse<T>>>.asUiState(
    uiState: MutableStateFlow<NetworkUiState<T>>,
) {
    uiState.emit(NetworkUiState.Loading)
    collectLatest {
        when (it) {
            is NetworkCall.Error.HttpError -> {
                uiState.emit(
                    NetworkUiState.Error(
                        code = it.code, error = "${it.message}"
                    )
                )
            }

            is NetworkCall.Error.SerializationError -> {
                uiState.emit(NetworkUiState.Error(error = "${it.message}"))
            }

            is NetworkCall.Error.GenericError -> {
                uiState.emit(NetworkUiState.Error(error = "${it.message}"))
            }

            is NetworkCall.Success -> {
                if (it.data.success && it.data.data != null) {
                    uiState.emit(NetworkUiState.Success(it.data.data!!))
                } else {
                    uiState.emit(NetworkUiState.Error(error = it.data.message))
                }
            }
        }
    }
}