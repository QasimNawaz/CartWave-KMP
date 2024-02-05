package presentation.screens.favourite

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import domain.model.Product
import domain.usecase.favourite.FavouritesPagingUseCase
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
import presentation.screens.main.root.RootComponent
import utils.Logger
import utils.asUiState
import utils.coroutineScope


class FavouriteScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private var job: Job? = null
    private val coroutineScope = coroutineScope()
    private val favouritesPagingUseCase: FavouritesPagingUseCase by inject()
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase by inject()
    private val _networkUiState = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val networkUiState: StateFlow<PagingData<Product>> = _networkUiState.asStateFlow()
    private val _refresh = MutableStateFlow<Boolean>(false)
    val refresh: StateFlow<Boolean> = _refresh.asStateFlow()

    init {
        lifecycle.doOnResume { getFavouriteProducts() }
    }

    private fun getFavouriteProducts() {
        job?.cancel()
        job = coroutineScope.launch {
            Logger.d("getFavouriteProducts", "FavouriteScreenComponent")
            favouritesPagingUseCase.execute(Unit).collectLatest {
                _networkUiState.emit(it)
            }
        }
    }

    fun removeFromFavourite(productId: Int) {
        coroutineScope.launch {
            removeFromFavouriteUseCase.execute(RemoveFromFavouriteUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            _refresh.value = true
                        }

                        else -> {}
                    }
                }
        }
    }

    fun updateRefresh(reLoad: Boolean = false) {
        _refresh.value = reLoad
    }

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }


}