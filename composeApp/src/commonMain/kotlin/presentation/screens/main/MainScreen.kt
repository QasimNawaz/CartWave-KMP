package presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import presentation.composables.BottomBar
import presentation.composables.CartWaveScaffold
import presentation.screens.cart.CartScreen
import presentation.screens.favourite.FavouriteScreen
import presentation.screens.home.HomeScreen
import presentation.screens.profile.ProfileScreen

@Composable
fun MainScreen(component: MainScreenComponent) {
    val childStack by component.childStack.subscribeAsState()

    CartWaveScaffold(bottomBar = { BottomBar(component) }, topBar = {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(user?.avatar?.base64toBitmap()).placeholder(R.drawable.ic_person_circle)
//                    .error(R.drawable.ic_person_circle).diskCachePolicy(CachePolicy.ENABLED)
//                    .memoryCachePolicy(CachePolicy.ENABLED).build(),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(35.dp)
//                    .clip(CircleShape),
//                contentScale = ContentScale.FillBounds
//            )
//            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(color = MaterialTheme.colorScheme.onBackground, text = "Hi, Qasim")
                Text(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    text = "Let's go shopping"
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                modifier = Modifier.size(25.dp),
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }) { innerPadding ->
        Children(
            stack = component.childStack,
            modifier = Modifier.fillMaxSize().padding(innerPadding),

            animation = stackAnimation(fade()),
//            animation = tabAnimation(),
        ) {
            when (val instance = it.instance) {
                is MainScreenComponent.BottomBarChild.HomeScreenChild -> HomeScreen(instance.component)

                is MainScreenComponent.BottomBarChild.FavouriteScreenChild -> FavouriteScreen(
                    instance.component
                )

                is MainScreenComponent.BottomBarChild.CartScreenChild -> CartScreen(instance.component)

                is MainScreenComponent.BottomBarChild.ProfileScreenChild -> ProfileScreen(instance.component)
            }
        }
    }
}