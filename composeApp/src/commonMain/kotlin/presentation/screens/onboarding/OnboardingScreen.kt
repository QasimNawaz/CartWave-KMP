package presentation.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun OnboardingScreen(component: OnboardingScreenComponent) {

    val pages = listOf(
        OnBoardingPage.First, OnBoardingPage.Second, OnBoardingPage.Third
    )
    val pagerState = rememberPagerState { 3 }

    Column(
        modifier = Modifier
            .fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(8f),
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) { position ->
            PagerScreen(
                onBoardingPage = pages[position],
            )
        }
        PageIndicator(
            numberOfPages = pages.size,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(0.2f),
            pagerState = pagerState
        )
        FinishButton(
            modifier = Modifier.weight(0.5f), pagerState = pagerState
        ) {
            component.onRootNavigateToAuth()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f)
        ) {
            Image(
                modifier = Modifier.fillMaxWidth().padding(bottom = 180.dp),
                painter = painterResource(onBoardingPage.topBg),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Pager Top Bg"
            )
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(onBoardingPage.image),
                    contentDescription = "Pager Image"
                )
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = onBoardingPage.title,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(top = 20.dp),
            text = onBoardingPage.description,
            fontSize = MaterialTheme.typography.labelLarge.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun FinishButton(
    modifier: Modifier, pagerState: PagerState, onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(), visible = pagerState.currentPage == 2
        ) {
            Button(
                onClick = onClick, colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    defaultColor: Color = Color.LightGray,
    defaultRadius: Dp = 10.dp,
    selectedLength: Dp = 50.dp,
    space: Dp = 15.dp,
    animationDurationInMillis: Int = 300,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier,
    ) {
        repeat(numberOfPages) { iteration ->
            PageIndicatorView(
                isSelected = pagerState.settledPage == iteration,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedLength = selectedLength,
                animationDurationInMillis = animationDurationInMillis,
            )
        }
    }
}

@Composable
fun PageIndicatorView(
    isSelected: Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedLength: Dp,
    animationDurationInMillis: Int,
    modifier: Modifier = Modifier,
) {

    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            defaultColor
        }, animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )
    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedLength
        } else {
            defaultRadius
        }, animationSpec = tween(
            durationMillis = animationDurationInMillis,
        )
    )

    Canvas(
        modifier = modifier.size(
            width = width,
            height = defaultRadius,
        ),
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx(),
            ),
        )
    }
}

sealed class OnBoardingPage(
    val topBg: String, val image: String, val title: String, val description: String
) {

    object First : OnBoardingPage(
        topBg = "ic_onboarding_bg_one.xml",
        image = "ic_onboarding_one.xml",
        title = "Purchase Online !!",
        description = "Seamless clicks, endless picks – CartWave: your gateway to hassle-free online shopping tricks."
    )

    object Second : OnBoardingPage(
        topBg = "ic_onboarding_bg_two.xml",
        image = "ic_onboarding_two.xml",
        title = "Track Order !!",
        description = "Order en route, worries put to rest, CartWave's tracking guides you through, ensuring your order's best."
    )

    object Third : OnBoardingPage(
        topBg = "ic_onboarding_bg_three.xml",
        image = "ic_onboarding_three.xml",
        title = "Get your order !!",
        description = "Unbox delight, your order takes flight – CartWave delivers, making everything just right."
    )
}