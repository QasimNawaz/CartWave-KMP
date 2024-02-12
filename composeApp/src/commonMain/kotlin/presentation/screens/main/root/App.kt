package presentation.screens.main.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.NetworkFetcher
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.Logger
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import io.github.aakira.napier.Napier
import okio.FileSystem
import org.koin.compose.KoinContext
import presentation.screens.address.AddressScreen
import presentation.screens.auth.root.AuthScreen
import presentation.screens.checkout.CheckoutScreen
import presentation.screens.main.MainScreen
import presentation.screens.onboarding.OnboardingScreen
import presentation.screens.productDetail.ProductDetailScreen
import presentation.screens.splash.SplashScreen
import presentation.theme.AppTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun App(root: RootComponent) {
    val childStack by root.childStack.subscribeAsState()
    KoinContext {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .memoryCachePolicy(CachePolicy.DISABLED).memoryCache {
                    MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true)
                        .build()
                }.diskCachePolicy(CachePolicy.ENABLED).diskCache {
                    DiskCache.Builder()
                        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                        .maxSizeBytes(512L * 1024 * 1024) // 512MB
                        .build()
                }.crossfade(true)
                .components {
                    add(KtorNetworkFetcherFactory())
                }
                .build()
        }
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
                        is RootComponent.Child.CheckoutScreenChild -> CheckoutScreen(instance.component)
                        is RootComponent.Child.ProductDetailScreenChild -> ProductDetailScreen(
                            instance.component
                        )
                    }
                }
            }
        }
    }
}