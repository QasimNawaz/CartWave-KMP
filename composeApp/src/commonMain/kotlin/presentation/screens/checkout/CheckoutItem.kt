package presentation.screens.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import domain.model.Product
import utils.AsyncImageLoader

@Composable
fun CheckoutItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
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
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    text = "Quantity: ${product.cartQty}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}