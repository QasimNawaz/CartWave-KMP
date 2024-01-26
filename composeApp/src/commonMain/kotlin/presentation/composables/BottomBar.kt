package presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.screens.main.MainScreenComponent

@Composable
fun BottomBar(component: MainScreenComponent) {
    val screens = listOf(
        BottomBarScreenInfo.Home,
        BottomBarScreenInfo.Wishlist,
        BottomBarScreenInfo.Cart,
        BottomBarScreenInfo.Profile,
    )

    val childStack by component.childStack.subscribeAsState()

    val shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp)
    CartWaveSurface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .padding(top = 1.dp),
        elevation = 4.dp,
        shape = shape
    ) {
        Row(
            modifier = Modifier
                .height(BottomNavHeight)
                .fillMaxWidth()
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    isSelected = screen.child == childStack.active.configuration,
                    onItemSelected = {
                        component.onTabSelect(screen.child)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddItem(
    screen: BottomBarScreenInfo,
    isSelected: Boolean,
    onItemSelected: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .height(40.dp)
            .clickable(
                interactionSource = interactionSource, indication = null
            ) {
                onItemSelected.invoke()
            }, contentAlignment = Alignment.CenterStart
    ) {
        Row {
            Spacer(modifier = Modifier.width(15.dp))
            AnimatedVisibility(
                visible = isSelected,
            ) {
                Text(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.5f) else Color.Transparent,
                        )
                        .padding(
                            start = 30.dp, end = 10.dp, top = 8.dp, bottom = 8.dp
                        ), text = screen.title, color = Color.White, fontSize = 11.sp
                )
            }
        }
        Icon(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape)
                .background(color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .padding(10.dp),
            painter = painterResource(screen.icon),
            contentDescription = "icon",
            tint = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
        )
    }
}

val BottomNavHeight = 52.dp

sealed class BottomBarScreenInfo(
    val child: MainScreenComponent.BottomBarConfiguration, val title: String, val icon: String
) {
    data object Home : BottomBarScreenInfo(
        child = MainScreenComponent.BottomBarConfiguration.HomeScreenConfig,
        title = "Home",
        icon = "ic_home.xml"
    )

    data object Wishlist : BottomBarScreenInfo(
        child = MainScreenComponent.BottomBarConfiguration.FavouriteScreenConfig,
        title = "Wishlist",
        icon = "ic_favourite.xml"
    )

    data object Cart : BottomBarScreenInfo(
        child = MainScreenComponent.BottomBarConfiguration.CartScreenConfig,
        title = "Cart",
        icon = "ic_cart.xml"
    )

    data object Profile : BottomBarScreenInfo(
        child = MainScreenComponent.BottomBarConfiguration.ProfileScreenConfig,
        title = "Profile",
        icon = "ic_profile.xml"
    )
}