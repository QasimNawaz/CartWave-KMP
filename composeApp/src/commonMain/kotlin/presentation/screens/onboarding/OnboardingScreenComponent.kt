package presentation.screens.onboarding

import com.arkivanov.decompose.ComponentContext
import data.repository.cache.KeyValueStorageRepo
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screens.main.root.RootComponent

class OnboardingScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val keyValueStorageRepo: KeyValueStorageRepo by inject()
    fun onRootNavigateToAuth() {
        keyValueStorageRepo.isOnboarded = true
        onRootNavigate.invoke(RootComponent.Configuration.AuthScreenConfig)
    }

}