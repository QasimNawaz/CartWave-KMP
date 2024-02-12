package utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import domain.model.BaseResponse
import domain.model.Product
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
    showLoader: Boolean = true
) {
    if (showLoader) {
        uiState.emit(NetworkUiState.Loading)
    }
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
                    uiState.emit(NetworkUiState.Success(it.data.data))
                } else {
                    uiState.emit(NetworkUiState.Error(error = it.data.message))
                }
            }
        }
    }
}

suspend fun Flow<NetworkCall<BaseResponse<List<Product>>>>.asCartUiState(
    uiState: MutableStateFlow<NetworkUiState<List<Product>>>,
    subTotal: MutableStateFlow<Int>
) {
    uiState.emit(NetworkUiState.Loading)
    collectLatest {
        when (it) {
            is NetworkCall.Error.HttpError -> {
                subTotal.emit(0)
                uiState.emit(
                    NetworkUiState.Error(
                        code = it.code, error = "${it.message}"
                    )
                )
            }

            is NetworkCall.Error.SerializationError -> {
                subTotal.emit(0)
                uiState.emit(NetworkUiState.Error(error = "${it.message}"))
            }

            is NetworkCall.Error.GenericError -> {
                subTotal.emit(0)
                uiState.emit(NetworkUiState.Error(error = "${it.message}"))
            }

            is NetworkCall.Success -> {
                if (it.data.success && it.data.data != null) {
                    it.data.data.let { carts ->
                        subTotal.emit(carts.sumOf { product ->
                            (product.sellingPrice?.replace(",", "")?.toIntOrNull()
                                ?: 0) * product.cartQty
                        })
                        uiState.emit(NetworkUiState.Success(carts))
                    }
                    uiState.emit(NetworkUiState.Success(it.data.data))
                } else {
                    subTotal.emit(0)
                    uiState.emit(NetworkUiState.Error(error = it.data.message))
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@ExperimentalDecomposeApi
fun homeDefaultHorizontalPager(): Pager =
    { modifier, state, key, pageContent ->
        HorizontalPager(
            modifier = modifier,
            state = state,
            userScrollEnabled = false,
            key = key,
            beyondBoundsPageCount = 1,
            pageContent = pageContent,
        )
    }

@OptIn(ExperimentalFoundationApi::class)
internal typealias Pager =
        @Composable (
            Modifier,
            PagerState,
            key: (index: Int) -> Any,
            pageContent: @Composable PagerScope.(index: Int) -> Unit,
        ) -> Unit