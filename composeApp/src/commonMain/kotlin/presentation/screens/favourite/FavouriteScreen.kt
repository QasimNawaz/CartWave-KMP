package presentation.screens.favourite

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.Product
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.composables.EmptyView
import presentation.composables.ProductRowItem
import presentation.composables.ProductShimmerItem
import presentation.screens.main.root.RootComponent

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FavouriteScreen(component: FavouriteScreenComponent) {
    val favourites: LazyPagingItems<Product> = component.networkUiState.collectAsLazyPagingItems()
    val refreshList: Boolean by component.refresh.collectAsState()
    if (refreshList) {
        favourites.refresh()
        component.updateRefresh()
    }
    LazyVerticalGrid(state = rememberLazyGridState(),
        contentPadding = PaddingValues(
            vertical = 10.dp,
            horizontal = 10.dp
        ),
        columns = GridCells.Fixed(2), content = {
            items(favourites.itemCount) { index ->
                favourites[index]?.let { item ->
                    ProductRowItem(
                        product = item, onNavigateToDetail = {
                            component.onRootNavigateTo(
                                RootComponent.Configuration.ProductDetailScreenConfig(
                                    it
                                )
                            )
                        }, onUpdateFavourite = {
                            if (!it) {
                                component.removeFromFavourite(item.id)
                            }

                        }
                    )
                }
            }
            favourites.loadState.apply {
                when {
                    refresh is LoadStateNotLoading && favourites.itemCount < 1 -> {
                        item {
                            EmptyView(Res.drawable.ic_empty_wish_list, "Your wishlist is empty")
                        }
                    }

                    refresh is LoadStateLoading || append is LoadStateLoading -> {
                        items(2) {
                            ProductShimmerItem(
                                modifier = Modifier
                                    .height(250.dp)
                                    .padding(horizontal = 6.dp, vertical = 10.dp)
                            )
                        }
                    }

                    refresh is LoadStateError -> {
                        item {
                            (refresh as LoadState.Error).error.message.let { error ->
                                EmptyView(
                                    Res.drawable.ic_network_error, error ?: "Something went wrong"
                                )
                            }

                        }
                    }
                }
            }
        })

}