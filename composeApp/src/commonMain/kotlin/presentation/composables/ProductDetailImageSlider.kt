package presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AsyncImageLoader

@OptIn(ExperimentalFoundationApi::class, ExperimentalResourceApi::class)
@Composable
fun ProductDetailImageSlider(modifier: Modifier, images: List<String>, onDismiss: () -> Unit) {
    val pagerState = rememberPagerState { images.size }
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            state = pagerState,
        ) { position ->
            AsyncImageLoader(
                url = images[position],
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxSize()
            )
        }

        IconButton(modifier = Modifier
            .padding(25.dp)
            .size(40.dp)
            .align(Alignment.TopStart)
            .clip(CircleShape)
            .background(color = Color.Gray.copy(alpha = 0.5f)), onClick = {
            onDismiss.invoke()
        }) {
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_left),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        LazyRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 30.dp, end = 30.dp, bottom = 100.dp)
                .background(Color.Gray.copy(alpha = 0.5f), shape = MaterialTheme.shapes.small)
                .padding(vertical = 6.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            images.forEachIndexed { index, s ->
                item {
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (pagerState.settledPage == index) Color.Black else Color.White
                        ),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        shape = MaterialTheme.shapes.small,
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    ) {
                        AsyncImageLoader(
                            url = s,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}