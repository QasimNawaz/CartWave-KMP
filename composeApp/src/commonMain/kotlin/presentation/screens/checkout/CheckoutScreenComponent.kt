package presentation.screens.checkout

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.arkivanov.essenty.lifecycle.doOnStart
import domain.dto.PlaceOrderRequestDto
import domain.model.Address
import domain.model.Product
import domain.usecase.address.GetPrimaryAddressUseCase
import domain.usecase.cart.GetUserCartUseCase
import domain.usecase.order.PlaceOrderUseCase
import domain.utils.NetworkUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screens.main.root.RootComponent
import utils.asCartUiState
import utils.asUiState
import utils.coroutineScope

class CheckoutScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private var cartJob: Job? = null
    private var addressJob: Job? = null
    private val coroutineScope = coroutineScope()
    private val getUserCartUseCase: GetUserCartUseCase by inject()
    private val primaryAddressUseCase: GetPrimaryAddressUseCase by inject()
    private val placeOrderUseCase: PlaceOrderUseCase by inject()
    private val _primaryAddressUiState =
        MutableStateFlow<NetworkUiState<Address>>(NetworkUiState.Empty)
    val primaryAddressUiState: StateFlow<NetworkUiState<Address>> =
        _primaryAddressUiState.asStateFlow()
    private val _subTotal = MutableStateFlow<Int>(0)
    val subTotal: StateFlow<Int> = _subTotal.asStateFlow()
    private val _networkUiState =
        MutableStateFlow<NetworkUiState<List<Product>>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<List<Product>>> = _networkUiState.asStateFlow()
    private val _placeOrderNetworkUiState =
        MutableStateFlow<NetworkUiState<String>>(NetworkUiState.Empty)
    val placeOrderNetworkUiState: StateFlow<NetworkUiState<String>> =
        _placeOrderNetworkUiState.asStateFlow()

    init {
        lifecycle.doOnStart {
            getUserCart()
        }
        lifecycle.doOnResume {
            getPrimaryAddress()
        }
    }

    private fun getUserCart() {
        cartJob?.cancel()
        cartJob = coroutineScope.launch {
            getUserCartUseCase.execute(Unit)
                .asCartUiState(uiState = _networkUiState, subTotal = _subTotal)
        }
    }

    private fun getPrimaryAddress() {
        addressJob?.cancel()
        addressJob = coroutineScope.launch {
            primaryAddressUseCase.execute(Unit).asUiState(_primaryAddressUiState)
        }
    }

    fun placeOrder(placeOrderRequestDto: PlaceOrderRequestDto) {
        coroutineScope.launch {
            placeOrderUseCase.execute(PlaceOrderUseCase.Params(placeOrderRequestDto))
                .asUiState(_placeOrderNetworkUiState)
        }
    }

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }

    fun goBack() {
        onGoBack.invoke()
    }
}