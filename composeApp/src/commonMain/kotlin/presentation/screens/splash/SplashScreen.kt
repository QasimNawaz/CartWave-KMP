package presentation.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(component: SplashScreenComponent) {

    val logoSize = 200.dp
    val nameSize = 300.dp
    val logoOffsetY = remember { Animatable(0f) }


    val shouldContinue = remember {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = shouldContinue.value, block = {
        if (shouldContinue.value) {
            component.onRootNavigateTo()
        }
    })

    val coroutineScope = rememberCoroutineScope()


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val targetValue = -(maxHeight.value / 3)
            LaunchedEffect(Unit) {
                logoOffsetY.animateTo(targetValue = targetValue,
                    animationSpec = tween(durationMillis = 1000),
                    block = {
                        if (this.value == targetValue) {
                            coroutineScope.launch {
                                delay(1000)
                                shouldContinue.value = true
                            }
                        }
                    })
            }
            Box(
                modifier = Modifier.size(logoSize)
                    .offset(y = with(LocalDensity.current) { logoOffsetY.value.dp })
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                )
            }

        }
    }
}
