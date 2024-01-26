package presentation.screens.auth.forgot

import com.arkivanov.decompose.ComponentContext

class ForgotPasswordScreenComponent(
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext {
    fun goBack() {
        onGoBack.invoke()
    }

}