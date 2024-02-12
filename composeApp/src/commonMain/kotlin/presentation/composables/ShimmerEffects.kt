package presentation.composables

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import utils.AsyncImageLoader
import utils.header

@Composable
fun HomeScreenShimmer() {
    LazyVerticalGrid(state = rememberLazyGridState(),
        contentPadding = PaddingValues(
            vertical = 10.dp,
            horizontal = 10.dp
        ),
        columns = GridCells.Fixed(2),
        content = {
            repeat(2) {
                header {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 6.dp, vertical = 6.dp)
                            .clip(shape = MaterialTheme.shapes.small)
                            .shimmer(),
                    )
                }
                items(2) {
                    ProductShimmerItem(
                        modifier = Modifier
                            .height(250.dp)
                            .padding(horizontal = 6.dp, vertical = 10.dp)
                    )
                }
            }

        })
}

@Composable
fun ProductShimmerItem(modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
                    .shimmer()
                    .clip(shape = MaterialTheme.shapes.medium),
            )
            Spacer(modifier = Modifier.height(1.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .shimmer(),
            )
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(7f)
                        .shimmer(),
                )
                Spacer(modifier = Modifier.width(1.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(3f)
                        .shimmer(),
                )
            }
        }
    }
}

@Composable
fun ProductDetailShimmer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .shimmer()
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
                        Spacer(
                            modifier = Modifier.fillMaxWidth().height(20.dp).shimmer(),
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Spacer(
                            modifier = Modifier.fillMaxWidth().height(12.dp).shimmer(),
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(15.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .shimmer()
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(3) {
                        Spacer(
                            modifier = Modifier
                                .width(150.dp)
                                .height(50.dp)
                                .padding(6.dp)
                                .clip(shape = MaterialTheme.shapes.small)
                                .shimmer()
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .padding(horizontal = 20.dp)
                            .weight(1f)
                            .shimmer()
                    )
                    Spacer(
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(CircleShape)
                            .shimmer()
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemShimmer() {
    Card(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
            Spacer(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .shimmer()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier.height(100.dp).weight(1f)
            ) {
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(16.dp).padding(end = 20.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .align(Alignment.TopStart)
                        .shimmer(),
                )

                Spacer(
                    modifier = Modifier.width(50.dp).height(16.dp).align(Alignment.BottomStart)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .shimmer(),
                )

                Spacer(
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .align(Alignment.BottomEnd).shimmer(),
                )
            }
        }
    }
}

@Composable
fun CheckoutItemShimmer() {
    Card(
        modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()
            .height(110.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(6.dp)) {
            Spacer(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .shimmer()
            )
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                modifier = Modifier.height(100.dp).weight(1f)
            ) {
                Spacer(
                    modifier = Modifier.fillMaxWidth().height(16.dp).padding(end = 20.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .align(Alignment.TopStart)
                        .shimmer(),
                )

                Spacer(
                    modifier = Modifier.width(50.dp).height(16.dp).align(Alignment.BottomStart)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .shimmer(),
                )

                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .width(100.dp)
                        .clip(shape = MaterialTheme.shapes.medium)
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .align(Alignment.BottomEnd).shimmer(),
                )
            }
        }
    }
}

@Composable
fun AddressShimmer() {
    Row(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray.copy(
                    alpha = 0.5f
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(vertical = 12.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .width(100.dp)
                .height(75.dp)
                .clip(shape = MaterialTheme.shapes.medium)
                .shimmer(),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Spacer(
            modifier = Modifier
                .weight(1f)
                .height(20.dp)
                .shimmer(),
        )
    }
}


@Composable
fun Modifier.shimmer(): Modifier =
    background(shimmerBrush())

@Composable
fun shimmerBrush(): Brush {
    val gradient = listOf(
        Color.LightGray.copy(alpha = 0.9f), //darker grey (90% opacity)
        Color.LightGray.copy(alpha = 0.3f), //lighter grey (30% opacity)
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "") // animate infinite times

    val translateAnimation = transition.animateFloat( //animate the transition
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, // duration for the animation
                easing = FastOutLinearInEasing
            )
        ), label = ""
    )
    return Brush.linearGradient(
        colors = gradient,
        start = Offset(200f, 200f),
        end = Offset(
            x = translateAnimation.value,
            y = translateAnimation.value
        )
    )
}

