package presentation.screens.cart

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import domain.model.Product
import domain.usecase.cart.GetUserCartUseCase
import domain.usecase.cart.RemoveCartUseCase
import domain.usecase.cart.UpdateCartUseCase
import domain.utils.NetworkCall
import domain.utils.NetworkUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screens.main.root.RootComponent
import utils.asCartUiState
import utils.coroutineScope

class CartScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private var job: Job? = null
    private val coroutineScope = coroutineScope()
    private val getUserCartUseCase: GetUserCartUseCase by inject()
    private val updateCartUseCase: UpdateCartUseCase by inject()
    private val removeCartUseCase: RemoveCartUseCase by inject()
    private val _subTotal = MutableStateFlow<Int>(0)
    val subTotal: StateFlow<Int> = _subTotal.asStateFlow()
    private val _networkUiState =
        MutableStateFlow<NetworkUiState<List<Product>>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<List<Product>>> = _networkUiState.asStateFlow()

    init {
        lifecycle.doOnResume { getUserCart() }
    }

    private fun getUserCart() {
        job?.cancel()
        job = coroutineScope.launch {
            getUserCartUseCase.execute(Unit)
                .asCartUiState(uiState = _networkUiState, subTotal = _subTotal)
        }
    }

    fun updateCart(productId: Int, cartQty: Int, updatedList: SnapshotStateList<Product>) {
        coroutineScope.launch {
            updateCartUseCase.execute(UpdateCartUseCase.Params(productId, cartQty))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            updatedList.let { carts ->
                                _subTotal.emit(carts.sumOf { product ->
                                    (product.sellingPrice?.replace(",", "")?.toIntOrNull()
                                        ?: 0) * product.cartQty
                                })
                            }
                            _networkUiState.emit(NetworkUiState.Success(updatedList))
                        }

                        else -> {}
                    }
                }
        }
    }

    fun removeCart(productId: Int, updatedList: SnapshotStateList<Product>) {
        coroutineScope.launch {
            removeCartUseCase.execute(RemoveCartUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            updatedList.let { carts ->
                                _subTotal.emit(carts.sumOf { product ->
                                    (product.sellingPrice?.replace(",", "")?.toIntOrNull()
                                        ?: 0) * product.cartQty
                                })
                            }
                            _networkUiState.emit(NetworkUiState.Success(updatedList))
                        }

                        else -> {}
                    }
                }
        }
    }

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }
}

