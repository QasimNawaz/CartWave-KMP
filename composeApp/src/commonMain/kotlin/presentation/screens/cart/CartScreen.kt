package presentation.screens.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.screens.main.root.RootComponent

@Composable
fun CartScreen(component: CartScreenComponent) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center).clickable {
            component.onRootNavigateTo(RootComponent.Configuration.AddressScreenConfig)
        }, text = "CartScreen")
    }
}