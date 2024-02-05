package utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import presentation.composables.shimmer

@Composable
fun AsyncImageLoader(
    url: String?, contentScale: ContentScale = ContentScale.Crop, modifier: Modifier
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalPlatformContext.current).data(url).crossfade(true)
            .build(),
        loading = {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = contentScale,
                modifier = modifier.shimmer(),
            )
        },
        contentScale = contentScale,
        contentDescription = null
    )
}