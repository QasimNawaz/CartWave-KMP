package presentation.screens.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.serialization.Serializable
import presentation.screens.cart.CartScreenComponent
import presentation.screens.favourite.FavouriteScreenComponent
import presentation.screens.home.HomeScreenComponent
import presentation.screens.main.root.RootComponent
import presentation.screens.profile.ProfileScreenComponent

class MainScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<BottomBarConfiguration>()

    val childStack = childStack(
        source = navigation,
        serializer = BottomBarConfiguration.serializer(),
        initialConfiguration = BottomBarConfiguration.HomeScreenConfig,
        handleBackButton = false,
        childFactory = ::createChild
    )

    private fun createChild(
        config: BottomBarConfiguration, context: ComponentContext
    ): BottomBarChild {
        return when (config) {
            is BottomBarConfiguration.HomeScreenConfig -> BottomBarChild.HomeScreenChild(
                HomeScreenComponent(
                    componentContext = context, onRootNavigate
                )
            )

            is BottomBarConfiguration.FavouriteScreenConfig -> BottomBarChild.FavouriteScreenChild(
                FavouriteScreenComponent(
                    componentContext = context
                )
            )

            is BottomBarConfiguration.CartScreenConfig -> BottomBarChild.CartScreenChild(
                CartScreenComponent(
                    componentContext = context, onRootNavigate
                )
            )

            is BottomBarConfiguration.ProfileScreenConfig -> BottomBarChild.ProfileScreenChild(
                ProfileScreenComponent(
                    componentContext = context
                )
            )
        }
    }

    fun onTabSelect(child: BottomBarConfiguration) {
        when (child) {
            BottomBarConfiguration.HomeScreenConfig -> navigation.bringToFront(
                BottomBarConfiguration.HomeScreenConfig
            )

            BottomBarConfiguration.FavouriteScreenConfig -> navigation.bringToFront(
                BottomBarConfiguration.FavouriteScreenConfig
            )

            BottomBarConfiguration.CartScreenConfig -> navigation.bringToFront(
                BottomBarConfiguration.CartScreenConfig
            )

            BottomBarConfiguration.ProfileScreenConfig -> navigation.bringToFront(
                BottomBarConfiguration.ProfileScreenConfig
            )
        }
    }

    sealed class BottomBarChild {
        data class HomeScreenChild(val component: HomeScreenComponent) : BottomBarChild()
        data class FavouriteScreenChild(val component: FavouriteScreenComponent) : BottomBarChild()
        data class CartScreenChild(val component: CartScreenComponent) : BottomBarChild()
        data class ProfileScreenChild(val component: ProfileScreenComponent) : BottomBarChild()
    }

    @Serializable
    sealed class BottomBarConfiguration {
        @Serializable
        data object HomeScreenConfig : BottomBarConfiguration()

        @Serializable
        data object FavouriteScreenConfig : BottomBarConfiguration()

        @Serializable
        data object CartScreenConfig : BottomBarConfiguration()

        @Serializable
        data object ProfileScreenConfig : BottomBarConfiguration()
    }

}
