package presentation.screens.productDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.Product
import domain.utils.NetworkUiState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composables.CartWaveSurface
import presentation.composables.EmptyView
import presentation.composables.ProductDetailImageSlider
import presentation.composables.ProductDetailShimmer

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProductDetailScreen(component: ProductDetailScreenComponent) {

    var product by remember {
        mutableStateOf<Product?>(null)
    }

    val productDetailResponse: NetworkUiState<Product> by component.networkUiState.collectAsState()

    when (productDetailResponse) {
        is NetworkUiState.Loading -> {
            ProductDetailShimmer()
        }

        is NetworkUiState.Error -> {
            EmptyView(
                drawable = Res.drawable.ic_network_error,
                text = (productDetailResponse as NetworkUiState.Error).error
            )
        }

        is NetworkUiState.Success -> {
            (productDetailResponse as NetworkUiState.Success<Product>).data.let {
                product = it
            }
        }

        else -> {}
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        product?.let {
            ProductDetailImageSlider(
                Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f), it.images,
                onDismiss = {
                    component.goBack()
                }
            )
            CartWaveSurface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
                elevation = 20.dp,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        start = 20.dp, end = 20.dp, top = 15.dp, bottom = 10.dp
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Column(
                            modifier = Modifier.weight(8f)
                        ) {
                            Text(
                                text = it.title ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_yellow_star),
                                    modifier = Modifier.size(15.dp),
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "${it.averageRating}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Brand: ${it.brand}",
                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                        Icon(
                            modifier = Modifier
                                .padding(15.dp)
                                .size(36.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (it.isFavourite)
                                        component.removeFromFavourite(it.id, it)
                                    else
                                        component.addToFavourite(it.id, it)
                                }
                                .background(Color.DarkGray, shape = CircleShape)
                                .padding(6.dp),
                            painter = painterResource(if (it.isFavourite) Res.drawable.ic_heart_filled else Res.drawable.ic_heart),
                            contentDescription = null,
                            tint = Color.Red,
                        )
                    }
                    it.productDetails?.let {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            product?.productDetails?.let { map ->
                                items(map) { detail ->
                                    if (detail.keys.first() != "Style Code") {
                                        Row(
                                            modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .background(MaterialTheme.colorScheme.primary)
                                                    .padding(6.dp),
                                                text = "${detail.keys.first()} ",
                                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                                color = MaterialTheme.colorScheme.onPrimary
                                            )
                                            Text(
                                                modifier = Modifier
                                                    .background(
                                                        MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.5f
                                                        )
                                                    )
                                                    .padding(6.dp),
                                                text = " ${detail.values.first()}",
                                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        text = "${it.description}",
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Light
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append("$ ")
                                }
                                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append("${it.sellingPrice} ")
                                }
                                if (!it.actualPrice.isNullOrEmpty()) {
                                    withStyle(
                                        style = SpanStyle(
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            textDecoration = TextDecoration.LineThrough
                                        )
                                    ) {
                                        append(it.actualPrice ?: "")
                                    }
                                }
                            },
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        if (it.cartQty == 0) {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(color = MaterialTheme.colorScheme.primary)
                                    .clickable {
                                        component.updateCart(it.cartQty + 1, it)
                                    }, contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_cart),
                                        modifier = Modifier.size(25.dp),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Text(
                                        text = "Add to Cart",
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .height(50.dp)
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(color = MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    IconButton(modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colorScheme.secondary)
                                        .padding(2.dp), onClick = {
                                        if (it.cartQty > 1) {
                                            component.updateCart(it.cartQty - 1, it)
                                        } else {
                                            component.removeCart(it)
                                        }
                                    }) {
                                        Icon(
                                            painter = painterResource(Res.drawable.ic_minus),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSecondary
                                        )
                                    }
                                    Text(
                                        text = "${it.cartQty}",
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                        fontWeight = FontWeight.Bold
                                    )
                                    IconButton(modifier = Modifier
                                        .size(35.dp)
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colorScheme.primary)
                                        .padding(2.dp), onClick = {
                                        component.updateCart(
                                            it.cartQty + 1, it
                                        )
                                    }) {
                                        Icon(
                                            painter = painterResource(Res.drawable.ic_plus),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}