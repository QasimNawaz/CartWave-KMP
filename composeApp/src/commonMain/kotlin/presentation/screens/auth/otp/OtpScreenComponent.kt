package presentation.screens.auth.otp

import com.arkivanov.decompose.ComponentContext

class OtpScreenComponent(
    componentContext: ComponentContext,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext {

    fun goBack() {
        onGoBack.invoke()
    }

}