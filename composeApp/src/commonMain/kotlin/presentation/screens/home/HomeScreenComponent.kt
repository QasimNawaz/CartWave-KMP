package presentation.screens.home

import com.arkivanov.decompose.ComponentContext
import presentation.screens.main.root.RootComponent

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
) : ComponentContext by componentContext {

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }


}