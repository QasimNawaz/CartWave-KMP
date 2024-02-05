package presentation.screens.productDetail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import domain.model.Product
import domain.usecase.cart.UpdateCartUseCase
import domain.usecase.cart.RemoveCartUseCase
import domain.usecase.favourite.AddToFavouriteUseCase
import domain.usecase.favourite.RemoveFromFavouriteUseCase
import domain.usecase.product.ProductDetailUseCase
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
import utils.Logger
import utils.asUiState
import utils.coroutineScope

class ProductDetailScreenComponent(
    componentContext: ComponentContext, private val productId: Int, private val onGoBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private var job: Job? = null
    private val coroutineScope = coroutineScope()
    private val productDetailUseCase: ProductDetailUseCase by inject()
    private val addToFavouriteUseCase: AddToFavouriteUseCase by inject()
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase by inject()
    private val updateCartUseCase: UpdateCartUseCase by inject()
    private val removeCartUseCase: RemoveCartUseCase by inject()
    private val _networkUiState = MutableStateFlow<NetworkUiState<Product>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<Product>> = _networkUiState.asStateFlow()

    init {
        lifecycle.doOnStart { getProductDetail() }
    }

    private fun getProductDetail() {
        job?.cancel()
        job = coroutineScope.launch {
            Logger.d("getProductDetail: $productId", "ProductDetailScreenComponent")
            productDetailUseCase.execute(ProductDetailUseCase.Params(productId))
                .asUiState(uiState = _networkUiState)
        }
    }

    fun addToFavourite(productId: Int, product: Product) {
        coroutineScope.launch {
            addToFavouriteUseCase.execute(AddToFavouriteUseCase.Params(productId)).collectLatest {
                when (it) {
                    is NetworkCall.Success -> {
                        _networkUiState.emit(NetworkUiState.Success(product.copy(isFavourite = true)))
                    }

                    else -> {}
                }
            }
        }
    }

    fun removeFromFavourite(productId: Int, product: Product) {
        coroutineScope.launch {
            removeFromFavouriteUseCase.execute(RemoveFromFavouriteUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            _networkUiState.emit(NetworkUiState.Success(product.copy(isFavourite = false)))
                        }

                        else -> {}
                    }
                }
        }
    }

    fun updateCart(cartQty: Int, product: Product) {
        coroutineScope.launch {
            updateCartUseCase.execute(UpdateCartUseCase.Params(product.id, cartQty))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            _networkUiState.emit(NetworkUiState.Success(product.copy(cartQty = cartQty)))
                        }

                        else -> {}
                    }
                }
        }
    }

    fun removeCart(product: Product) {
        coroutineScope.launch {
            removeCartUseCase.execute(RemoveCartUseCase.Params(product.id))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            _networkUiState.emit(NetworkUiState.Success(product.copy(cartQty = 0)))
                        }

                        else -> {}
                    }
                }
        }
    }

    fun goBack() {
        onGoBack.invoke()
    }

}