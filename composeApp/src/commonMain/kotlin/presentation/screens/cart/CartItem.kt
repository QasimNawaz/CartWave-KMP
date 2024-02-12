package presentation.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.Product
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AsyncImageLoader

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CartItem(product: Product, onCartQtyUpdate: (cartQty: Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
            AsyncImageLoader(
                product.images.firstOrNull(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier.height(100.dp).weight(1f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().align(Alignment.TopStart),
                    text = "${product.title}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = "$ ${product.sellingPrice}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .align(Alignment.BottomEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        IconButton(modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.secondary)
                            .padding(2.dp),
                            onClick = {
                                onCartQtyUpdate.invoke(product.cartQty - 1)
                            }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_minus),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                        Text(
                            text = "${product.cartQty}",
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.primary)
                            .padding(2.dp),
                            onClick = {
                                onCartQtyUpdate.invoke(product.cartQty + 1)
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