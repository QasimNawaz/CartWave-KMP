package presentation.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import data.model.User
import domain.dto.LoginRequestDto
import domain.dto.OrderProduct
import domain.dto.PlaceOrderRequestDto
import domain.model.Address
import domain.model.Product
import domain.utils.NetworkUiState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composables.AddressShimmer
import presentation.composables.AlertMessageDialog
import presentation.composables.CartWaveScaffold
import presentation.composables.CheckoutItemShimmer
import presentation.composables.EmptyView
import presentation.composables.LoadingDialog
import presentation.screens.main.root.RootComponent

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(component: CheckoutScreenComponent) {

    val scope = rememberCoroutineScope()
    val userCartResponse: NetworkUiState<List<Product>> by component.networkUiState.collectAsState()
    val subTotal: Int by component.subTotal.collectAsState()
    val primaryAddressResponse: NetworkUiState<Address> by component.primaryAddressUiState.collectAsState()
    val placeOrder: NetworkUiState<String> by component.placeOrderNetworkUiState.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { false })

    val primaryAddress = remember {
        mutableStateOf<Address?>(null)
    }
    val products = remember {
        mutableStateListOf<Product>()
    }

    CartWaveScaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Box(
            modifier = Modifier.fillMaxWidth().height(60.dp).padding(horizontal = 20.dp),
        ) {
            Image(
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier.size(20.dp).align(Alignment.CenterStart).clickable {
                    component.goBack()
                },
                painter = painterResource(Res.drawable.ic_arrow_left),
                contentDescription = null,
            )

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Payment",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }) { innerPadding ->
        when (placeOrder) {
            is NetworkUiState.Loading -> {
                LoadingDialog()
            }

            is NetworkUiState.Error -> {
                AlertMessageDialog(title = "Something went wrong !",
                    message = (placeOrder as NetworkUiState.Error).error,
                    positiveButtonText = "OK",
                    onPositiveClick = {

                    })
            }

            is NetworkUiState.Success -> {
                ModalBottomSheet(modifier = Modifier.padding(innerPadding), onDismissRequest = {
                    component.goBack()
                }, sheetState = sheetState, dragHandle = { BottomSheetDefaults.DragHandle() }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(500.dp)
                            .padding(horizontal = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Order Placed",
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                textAlign = TextAlign.Center
                            )
                            Image(
                                modifier = Modifier.size(200.dp),
                                painter = painterResource(Res.drawable.ic_success),
                                contentDescription = null,
                            )
                            Text(
                                text = "Your order has been confirmed, we will send you confirmation email shortly.",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                        Button(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
//                                if (!sheetState.isVisible) {
//                                    navController.popBackStack()
//                                }
                                    component.goBack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary,
                                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text("Continue Shopping")
                        }
                    }
                }
            }

            else -> {}
        }
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
            ) {

                LazyColumn(
                    modifier = Modifier.fillMaxSize().weight(1f),
                ) {
                    item {
                        when (primaryAddressResponse) {
                            is NetworkUiState.Error -> {
                                AddAddressView {
                                    component.onRootNavigateTo(RootComponent.Configuration.AddressScreenConfig)
                                }
                            }

                            is NetworkUiState.Loading -> {
                                AddressShimmer()
                            }

                            is NetworkUiState.Success -> {
                                (primaryAddressResponse as NetworkUiState.Success<Address>).data.let { address ->
                                    primaryAddress.value = address
                                    CheckoutAddress(address) {
                                        component.onRootNavigateTo(RootComponent.Configuration.AddressScreenConfig)
                                    }
                                }
                            }

                            else -> {}
                        }
                    }
                    when (userCartResponse) {
                        is NetworkUiState.Loading -> {
                            item {
                                Spacer(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                )
                            }
                            items(2) {
                                CheckoutItemShimmer()
                            }
                        }

                        is NetworkUiState.Error -> {
                            item {
                                (userCartResponse as NetworkUiState.Error).error.let {
                                    if (it == "No data found!") {
                                        EmptyView(
                                            drawable = Res.drawable.ic_empty_cart,
                                            text = "Your cart is empty"
                                        )
                                    } else {
                                        EmptyView(
                                            drawable = Res.drawable.ic_network_error, text = it
                                        )
                                    }
                                }
                            }
                        }

                        is NetworkUiState.Success -> {
                            (userCartResponse as NetworkUiState.Success<List<Product>>).data.let { list ->
                                if (list.isEmpty()) {
                                    item {
                                        EmptyView(
                                            drawable = Res.drawable.ic_empty_cart,
                                            text = "Your cart is empty"
                                        )
                                    }
                                } else {
                                    products.clear()
                                    products.addAll(list)
                                    item {
                                        Text(
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(vertical = 8.dp),
                                            text = "Products(${list.size})",
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    items(items = list) { product ->
                                        key(product.id) {
                                            CheckoutItem(product)
                                        }
                                    }
                                }
                            }
                        }

                        else -> {}
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Total amount"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.End,
                        text = "$ ${subTotal + 9}"
                    )
                }

                Button(
                    modifier = Modifier.fillMaxWidth().testTag("Place Order"),
                    onClick = {
                        component.placeOrder(
                            PlaceOrderRequestDto(
                                orderDate = "",
                                shippingAddress = primaryAddress?.value?.address ?: "",
                                orderStatus = "Placed",
                                promoCode = "",
                                totalAmount = subTotal + 9,
                                paymentMethod = "",
                                products = products.map {
                                    OrderProduct(
                                        productId = it.id,
                                        title = it.title ?: "",
                                        price = it.sellingPrice?.replace(",", "")?.toIntOrNull()
                                            ?: 0,
                                        quantity = it.cartQty
                                    )
                                })
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                ) {
                    Text(text = "Place Order")
                }
            }
        }
    }

}