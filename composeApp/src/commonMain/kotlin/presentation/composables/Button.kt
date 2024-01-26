package presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    resource: String,
    label: String,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(resource),
                    modifier = Modifier
                        .size(18.dp),
                    contentDescription = "drawable_icons",
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = label,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
            )
        }
    }
}

@Suppress("FunctionName")
@Composable
fun CircularButton(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.error)
            .run {
                if (enabled) {
                    clickable { onClick() }
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Suppress("FunctionName")
@Composable
fun CircularButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    CircularButton(
        modifier = modifier,
        content = {
            Icon(imageVector, null, Modifier.size(34.dp), Color.White)
        },
        enabled = enabled,
        onClick = onClick,
    )
}

@Suppress("FunctionName")
@Composable
internal fun InstagramCameraButton(
    modifier: Modifier = Modifier,
    size: Dp = 70.dp,
    borderSize: Dp = 5.dp,
    onClick: () -> Unit,
) {
    // Outer size including the border
    val outerSize = size + borderSize * 2
    // Inner size excluding the border
    val innerSize = size - borderSize

    Box(
        modifier =
        modifier
            .size(outerSize)
            .clip(CircleShape)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center,
    ) {
        // Surface for the border effect
        Surface(
            modifier = Modifier.size(outerSize),
            shape = CircleShape,
            color = Color.Transparent,
            border = BorderStroke(borderSize, Color.White),
        ) {}

        // Inner clickable circle
        Box(
            modifier =
            Modifier
                .size(innerSize)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {}
    }
}
