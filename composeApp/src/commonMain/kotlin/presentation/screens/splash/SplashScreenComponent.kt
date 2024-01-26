package presentation.screens.splash

import com.arkivanov.decompose.ComponentContext
import data.repository.cache.KeyValueStorageRepo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screens.main.root.RootComponent


class SplashScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigateTo: (config: RootComponent.Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val keyValueStorageRepo: KeyValueStorageRepo by inject()

    fun onRootNavigateTo() {
        if (keyValueStorageRepo.isOnboarded) {
            if (keyValueStorageRepo.doRemember && keyValueStorageRepo.user != null) {
                onRootNavigateTo.invoke(RootComponent.Configuration.MainScreenConfig)
            } else {
                onRootNavigateTo.invoke(RootComponent.Configuration.AuthScreenConfig)
            }
        } else {
            onRootNavigateTo.invoke(RootComponent.Configuration.OnboardingScreenConfig)
        }
    }

}