package presentation.screens.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.Product
import domain.utils.NetworkUiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.composables.CartItemShimmer
import presentation.composables.DashedDivider
import presentation.composables.EmptyView
import presentation.screens.main.root.RootComponent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CartScreen(component: CartScreenComponent) {
    val userCartResponse: NetworkUiState<List<Product>> by component.networkUiState.collectAsState()
    val subTotal: Int by component.subTotal.collectAsState()
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(containerColor = MaterialTheme.colorScheme.background,
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetTonalElevation = 20.dp,
        sheetShadowElevation = 20.dp,
        sheetPeekHeight = 50.dp,
        sheetContent = {
            Column(
                modifier = Modifier.heightIn(min = 120.dp, max = 225.dp).fillMaxSize()
                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        text = "Subtotal"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.End,
                        text = "$ $subTotal"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        text = "Shipping"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.End,
                        text = "$ ${if (subTotal != 0) "9" else "0"}"
                    )
                }
                DashedDivider(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        text = "Total amount"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.End,
                        text = "$ ${if (subTotal != 0) subTotal + 9 else "0"}"
                    )
                }
                Button(
                    modifier = Modifier.fillMaxWidth().testTag("Checkout"), onClick = {
                        component.onRootNavigateTo(RootComponent.Configuration.CheckoutScreenConfig)
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ), enabled = subTotal != 0
                ) {
                    Text(text = "Checkout")
                }
            }
        }) {
        when (userCartResponse) {
            is NetworkUiState.Loading -> {
                CartItemShimmer()
            }

            is NetworkUiState.Error -> {
                (userCartResponse as NetworkUiState.Error).error.let {
                    if (it == "No data found!") {
                        EmptyView(
                            drawable = Res.drawable.ic_empty_cart, text = "Your cart is empty"
                        )
                    } else {
                        EmptyView(
                            drawable = Res.drawable.ic_network_error, text = it
                        )
                    }
                }
            }

            is NetworkUiState.Success -> {
                (userCartResponse as NetworkUiState.Success<List<Product>>).data.let { products ->
                    if (products.isEmpty()) {
                        EmptyView(
                            drawable = Res.drawable.ic_empty_cart, text = "Your cart is empty"
                        )
                    } else {
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxSize()
                                .padding(start = 10.dp, end = 10.dp, bottom = 50.dp)
                        ) {
                            itemsIndexed(items = products) { index, product ->
                                key(product.id) {
                                    CartItem(product, onCartQtyUpdate = { cartQty ->
                                        if (cartQty != 0) {
                                            component.updateCart(
                                                product.id,
                                                cartQty,
                                                products.toMutableStateList().apply {
                                                    this[index] = product.copy(cartQty = cartQty)
                                                })
                                        } else {
                                            component.removeCart(
                                                product.id,
                                                products.toMutableStateList().apply {
                                                    removeAt(index)
                                                })
                                        }
                                    })
                                }
                            }
                        }
                    }

                }
            }

            else -> {}
        }
    }

}