package presentation.screens.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.navigate
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import presentation.screens.home.pager.PageComponent
import presentation.screens.main.root.RootComponent
import utils.Logger

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
) : ComponentContext by componentContext {

    @OptIn(ExperimentalDecomposeApi::class)
    private val navigation = PagesNavigation<PagesConfiguration>()


    @OptIn(ExperimentalDecomposeApi::class)
    val pages: Value<ChildPages<*, PageComponent>> =
        childPages(
            source = navigation,
            serializer = PagesConfiguration.serializer(),
            initialPages = {
                Pages(
                    items = listOf(
                        PagesConfiguration(text = "All"),
                        PagesConfiguration(text = "Clothing and Accessories"),
                        PagesConfiguration(text = "Footwear"),
                        PagesConfiguration(text = "Bags, Wallets & Belts")
                    ),
                    selectedIndex = 0,
                )
            },
            key = "HomeScreenComponent",
            handleBackButton = false,
            childFactory = { config, childComponentContext ->
                PageComponent(
                    componentContext = childComponentContext,
                    text = config.text,
                )
            }
        )

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }

    @OptIn(ExperimentalDecomposeApi::class)
    fun onNavigateToPageIndex(index: Int) {
        Logger.d("onNavigateToPageIndex: $index")
        navigation.select(index)
    }

    @Serializable
    private data class PagesConfiguration(val text: String)


}