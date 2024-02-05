package presentation.screens.main.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceAll
import kotlinx.serialization.Serializable
import presentation.screens.address.AddressScreenComponent
import presentation.screens.auth.root.AuthScreenComponent
import presentation.screens.main.MainScreenComponent
import presentation.screens.onboarding.OnboardingScreenComponent
import presentation.screens.productDetail.ProductDetailScreenComponent
import presentation.screens.splash.SplashScreenComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    val childStack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.SplashScreenConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: Configuration, context: ComponentContext
    ): Child {
        return when (config) {
            is Configuration.SplashScreenConfig -> Child.SplashScreenChild(
                SplashScreenComponent(
                    componentContext = context,
                    onRootNavigateTo = {
                        navigation.replaceAll(it)
                    },
                )
            )

            is Configuration.OnboardingScreenConfig -> Child.OnboardingScreenChild(
                OnboardingScreenComponent(componentContext = context, onRootNavigate = {
                    navigation.replaceAll(Configuration.AuthScreenConfig)
                })
            )

            is Configuration.AuthScreenConfig -> Child.AuthScreenChild(
                AuthScreenComponent(componentContext = context,
                    onRootNavigate = {
                        navigation.replaceAll(Configuration.MainScreenConfig)
                    })
            )

            is Configuration.MainScreenConfig -> Child.MainScreenChild(
                MainScreenComponent(componentContext = context,
                    onRootNavigate = {
                        navigation.pushNew(it)
                    })
            )

            is Configuration.AddressScreenConfig -> Child.AddressScreenChild(
                AddressScreenComponent(componentContext = context)
            )

            is Configuration.ProductDetailScreenConfig -> Child.ProductDetailScreenChild(
                ProductDetailScreenComponent(
                    componentContext = context,
                    productId = config.productId, onGoBack = {
                        navigation.pop()
                    }
                )
            )
        }
    }

    sealed class Child {
        data class SplashScreenChild(val component: SplashScreenComponent) : Child()
        data class OnboardingScreenChild(val component: OnboardingScreenComponent) : Child()
        data class AuthScreenChild(val component: AuthScreenComponent) : Child()
        data class MainScreenChild(val component: MainScreenComponent) : Child()
        data class ProductDetailScreenChild(val component: ProductDetailScreenComponent) : Child()
        data class AddressScreenChild(val component: AddressScreenComponent) : Child()
    }

    @Serializable
    sealed class Configuration {
        @Serializable
        data object SplashScreenConfig : Configuration()

        @Serializable
        data object OnboardingScreenConfig : Configuration()

        @Serializable
        data object AuthScreenConfig : Configuration()

        @Serializable
        data object MainScreenConfig : Configuration()

        @Serializable
        data class ProductDetailScreenConfig(val productId: Int) : Configuration()

        @Serializable
        data object AddressScreenConfig : Configuration()
    }
}