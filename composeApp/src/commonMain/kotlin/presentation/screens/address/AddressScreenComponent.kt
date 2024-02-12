package presentation.screens.address

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnStart
import domain.model.Address
import domain.usecase.address.AddAddressUseCase
import domain.usecase.address.GetAddressesUseCase
import domain.usecase.address.UpdatePrimaryAddressUseCase
import domain.utils.NetworkCall
import domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.asUiState
import utils.coroutineScope

class AddressScreenComponent(
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = coroutineScope()
    private val getAddressesUseCase: GetAddressesUseCase by inject()
    private val addAddressUseCase: AddAddressUseCase by inject()
    private val updatePrimaryAddressUseCase: UpdatePrimaryAddressUseCase by inject()
    private val _networkUiState =
        MutableStateFlow<NetworkUiState<List<Address>>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<List<Address>>> = _networkUiState.asStateFlow()

    init {
        lifecycle.doOnStart {
            getAddresses(true)
        }
    }

    private fun getAddresses(showLoader: Boolean) {
        coroutineScope.launch {
            getAddressesUseCase.execute(Unit)
                .asUiState(_networkUiState, showLoader)
        }
    }

    fun addAddress(address: String) {
        coroutineScope.launch {
            addAddressUseCase.execute(AddAddressUseCase.Params(address)).collectLatest {
                when (it) {
                    is NetworkCall.Success -> {
                        if (it.data.success) {
                            getAddresses(false)
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    fun updatePrimaryAddress(addressId: Int, addresses: List<Address>) {
        coroutineScope.launch {
            updatePrimaryAddressUseCase.execute(UpdatePrimaryAddressUseCase.Params(addressId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            _networkUiState.emit(NetworkUiState.Success(
                                addresses.map { address ->
                                    address.copy(isPrimary = address.id == addressId)
                                }
                            ))
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