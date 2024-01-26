package presentation.screens.main.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import org.koin.compose.KoinContext
import presentation.screens.address.AddressScreen
import presentation.screens.auth.root.AuthScreen
import presentation.screens.main.MainScreen
import presentation.screens.onboarding.OnboardingScreen
import presentation.screens.productDetail.ProductDetailScreen
import presentation.screens.splash.SplashScreen
import presentation.theme.AppTheme

@Composable
fun App(root: RootComponent) {
    val childStack by root.childStack.subscribeAsState()
    KoinContext {
        AppTheme {
            Scaffold(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ) {
                Children(
                    stack = childStack,
                    animation = stackAnimation(slide())
                ) { child ->
                    when (val instance = child.instance) {
                        is RootComponent.Child.SplashScreenChild -> SplashScreen(instance.component)
                        is RootComponent.Child.OnboardingScreenChild -> OnboardingScreen(instance.component)
                        is RootComponent.Child.AuthScreenChild -> AuthScreen(instance.component)
                        is RootComponent.Child.MainScreenChild -> MainScreen(instance.component)
                        is RootComponent.Child.AddressScreenChild -> AddressScreen(instance.component)
                        is RootComponent.Child.ProductDetailScreenChild -> ProductDetailScreen(
                            instance.component
                        )
                    }
                }
            }
        }
    }
}