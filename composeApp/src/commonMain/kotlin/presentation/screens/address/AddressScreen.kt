package presentation.screens.address

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.Address
import domain.utils.NetworkUiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composables.AddressDialog
import presentation.composables.AddressShimmer
import presentation.composables.CartWaveScaffold
import presentation.composables.EmptyView
import presentation.screens.checkout.AddAddressView

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddressScreen(component: AddressScreenComponent) {

    val addressDialog = remember {
        mutableStateOf(false)
    }
    val addressesResponse: NetworkUiState<List<Address>> by component.networkUiState.collectAsState()

    if (addressDialog.value) {
        AddressDialog(onDismissRequest = { addressDialog.value = false }, onAddAddress = {
            addressDialog.value = false
            component.addAddress(it)
        })
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
                text = "Address",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Choose your Location",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    Text(
                        text = "Let's find your unforgettable event. Choose a location below to get started.",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                item {
                    AddAddressView {
                        addressDialog.value = true
                    }
                }
                when (addressesResponse) {
                    is NetworkUiState.Loading -> {
                        item {
                            AddressShimmer()
                        }
                    }

                    is NetworkUiState.Error -> {
                        item {
                            (addressesResponse as NetworkUiState.Error).error.let {
                                if (it == "No data found!") {
                                    EmptyView(
                                        drawable = Res.drawable.ic_empty_cart,
                                        text = "You haven't added any address yet !"
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
                        (addressesResponse as NetworkUiState.Success<List<Address>>).data.let { addresses ->
                            items(items = addresses, key = {
                                it.id
                            }) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        width = 1.dp,
                                        color = if (it.isPrimary) Color.Green else Color.Gray.copy(
                                            alpha = 0.5f
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .clickable {
                                        component.updatePrimaryAddress(it.id, addresses)
//                                        viewModel.updatePrimaryAddress(it.id, addresses)
//                                        addressLoading.value = true
                                    }
                                    .padding(vertical = 12.dp, horizontal = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedCard(
                                        border = if (it.isPrimary) BorderStroke(
                                            width = 1.dp, color = Color.Green
                                        ) else BorderStroke(
                                            width = 1.dp, color = Color.Gray.copy(
                                                alpha = 0.5f
                                            )
                                        ),
                                        modifier = Modifier.size(50.dp),
                                        shape = CircleShape,
                                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    ) {
                                        Image(
                                            modifier = Modifier
                                                .width(100.dp)
                                                .height(75.dp)
                                                .clip(shape = MaterialTheme.shapes.medium),
                                            painter = painterResource(Res.drawable.ic_map),
                                            contentScale = ContentScale.FillBounds,
                                            contentDescription = null
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = it.address,
                                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                    }

                    else -> {}
                }
            }
        }

    }

}