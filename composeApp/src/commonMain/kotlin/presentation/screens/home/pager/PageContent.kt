package presentation.screens.home.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.ProductsByCategoryItem
import domain.utils.NetworkUiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.composables.EmptyView
import presentation.composables.HomeScreenShimmer
import presentation.composables.ProductRowItem
import utils.header

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PageContent(component: PageComponent, onNavigateToDetail: (id: Int) -> Unit) {
//    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()

    val productsResponse: NetworkUiState<List<ProductsByCategoryItem>> by component.networkUiState.collectAsState()

    when (productsResponse) {
        is NetworkUiState.Loading -> {
            HomeScreenShimmer()
        }

        is NetworkUiState.Error -> {
            EmptyView(
                drawable = Res.drawable.ic_network_error,
                text = (productsResponse as NetworkUiState.Error).error
            )

        }

        is NetworkUiState.Success -> {
            (productsResponse as NetworkUiState.Success<List<ProductsByCategoryItem>>).data.let { list ->
                LazyVerticalGrid(state = lazyGridState,
                    contentPadding = PaddingValues(
                        vertical = 10.dp,
                        horizontal = 10.dp
                    ),
                    columns = GridCells.Fixed(2),
                    content = {
                        list.forEachIndexed { categoryIndex, category ->
                            header {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .padding(horizontal = 6.dp, vertical = 6.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = MaterialTheme.shapes.small
                                        )
                                        .padding(horizontal = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = category.category ?: "N/A",
                                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.weight(0.8f)
                                    )

                                    Text(
                                        text = "See All",
                                        textAlign = TextAlign.End,
                                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.weight(0.2f)
                                    )
                                }
                            }
                            category.products?.let { products ->
                                itemsIndexed(products) { index, item ->
                                    key(item.id) {
                                        ProductRowItem(product = item,
                                            onNavigateToDetail = onNavigateToDetail,
                                            onUpdateFavourite = { add ->
                                                val categoryList = list.toMutableList()
                                                val productList = products.toMutableList()
                                                if (add) {
                                                    productList[index] =
                                                        item.copy(isFavourite = true)
                                                    categoryList[categoryIndex] = category.copy(
                                                        products = productList
                                                    )
                                                    component.addToFavourite(item.id, categoryList)
                                                } else {
                                                    productList[index] =
                                                        item.copy(isFavourite = false)
                                                    categoryList[categoryIndex] = category.copy(
                                                        products = productList
                                                    )
                                                    component.removeFromFavourite(
                                                        item.id,
                                                        categoryList
                                                    )
                                                }
                                            })
                                    }
                                }
                            }
                        }

                    })
            }
        }

        else -> {}
    }
}