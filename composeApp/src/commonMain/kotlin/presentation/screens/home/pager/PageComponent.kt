package presentation.screens.home.pager

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnResume
import domain.model.ProductsByCategoryItem
import domain.usecase.favourite.AddToFavouriteUseCase
import domain.usecase.favourite.RemoveFromFavouriteUseCase
import domain.usecase.product.ProductsGroupBySubCategoryUseCase
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

class PageComponent(
    componentContext: ComponentContext,
    private val text: String,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = coroutineScope()
    private var job: Job? = null
    private val productsUseCase: ProductsGroupBySubCategoryUseCase by inject()
    private val addToFavouriteUseCase: AddToFavouriteUseCase by inject()
    private val removeFromFavouriteUseCase: RemoveFromFavouriteUseCase by inject()
    private val _networkUiState =
        MutableStateFlow<NetworkUiState<List<ProductsByCategoryItem>>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<List<ProductsByCategoryItem>>> =
        _networkUiState.asStateFlow()


    init {
        lifecycle.doOnResume { getProductsGroupBySubCategory() }
    }

    private fun getProductsGroupBySubCategory() {
        job?.cancel()
        job = coroutineScope.launch {
            productsUseCase.execute(ProductsGroupBySubCategoryUseCase.Params(text))
                .asUiState(uiState = _networkUiState)
        }
    }

    fun addToFavourite(productId: Int, data: MutableList<ProductsByCategoryItem>) {
        coroutineScope.launch {
            addToFavouriteUseCase.execute(AddToFavouriteUseCase.Params(productId)).collectLatest {
                when (it) {
                    is NetworkCall.Success -> {
                        _networkUiState.emit(NetworkUiState.Success(data))
                    }

                    else -> {}
                }
            }
        }
    }

    fun removeFromFavourite(productId: Int, data: MutableList<ProductsByCategoryItem>) {
        coroutineScope.launch {
            removeFromFavouriteUseCase.execute(RemoveFromFavouriteUseCase.Params(productId))
                .collectLatest {
                    when (it) {
                        is NetworkCall.Success -> {
                            _networkUiState.emit(NetworkUiState.Success(data))
                        }

                        else -> {}
                    }
                }
        }
    }

}