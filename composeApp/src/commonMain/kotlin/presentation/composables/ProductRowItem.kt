package presentation.composables

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cartwave_kmp.composeapp.generated.resources.Res
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import domain.model.Product
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AsyncImageLoader

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProductRowItem(
    product: Product,
    onNavigateToDetail: (id: Int) -> Unit,
    onUpdateFavourite: (add: Boolean) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(250.dp)
            .padding(horizontal = 6.dp, vertical = 10.dp).clickable {
                onNavigateToDetail.invoke(product.id)
            },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.7f).height(0.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
            ) {
                AsyncImageLoader(
                    url = product.images.firstOrNull(), modifier = Modifier.fillMaxSize()
                )
                Icon(
                    modifier = Modifier.align(Alignment.TopEnd).padding(10.dp).size(36.dp)
                        .clip(CircleShape)
                        .clickable {
                            if (product.isFavourite) onUpdateFavourite.invoke(false)
                            else onUpdateFavourite.invoke(true)
                        }.background(Color.DarkGray, shape = CircleShape)
                        .padding(6.dp),
                    painter = if (product.isFavourite) painterResource(Res.drawable.ic_heart_filled)
                    else painterResource(Res.drawable.ic_heart),
                    contentDescription = null,
                    tint = Color.Red,
                )

                if (!product.discount.isNullOrEmpty()) {
                    Text(
                        modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)
                            .background(Color.DarkGray, shape = CircleShape)
                            .padding(horizontal = 12.dp, vertical = 3.dp),
                        text = product.discount ?: "",
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 3.dp),
                text = product.title ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                lineHeight = 14.sp,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 5.dp)) {
                Text(
                    buildAnnotatedString {
                        append("$ ")
                        if (!product.actualPrice.isNullOrEmpty()) {
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                                append(product.actualPrice ?: "")
                            }
                        }
                        append(" ${product.sellingPrice}")
                    },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                )
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_yellow_star),
                        modifier = Modifier.size(15.dp),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${product.averageRating}",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                }
            }
        }
    }
}