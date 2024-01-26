package presentation.screens.auth.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import presentation.screens.auth.forgot.ForgotPasswordScreenComponent
import presentation.screens.auth.login.LoginScreenComponent
import presentation.screens.auth.otp.OtpScreenComponent
import presentation.screens.auth.signup.SignupScreenComponent
import presentation.screens.main.root.RootComponent

class AuthScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<AuthConfiguration>()

    val childStack = childStack(
        source = navigation,
        serializer = AuthConfiguration.serializer(),
        initialConfiguration = AuthConfiguration.LoginScreenConfig,
        handleBackButton = true,
        childFactory = ::createChild
    )

    @OptIn(ExperimentalDecomposeApi::class)
    private fun createChild(
        config: AuthConfiguration, context: ComponentContext
    ): AuthChild {
        return when (config) {
            AuthConfiguration.ForgotPasswordScreenConfig -> AuthChild.ForgotPasswordScreenChild(
                ForgotPasswordScreenComponent(
                    componentContext = context, onGoBack = {
                        navigation.pop()
                    }
                )
            )

            AuthConfiguration.LoginScreenConfig -> AuthChild.LoginScreenChild(
                LoginScreenComponent(
                    componentContext = context, onRootNavigate = onRootNavigate, onNavigate = {
                        navigation.pushNew(it)
                    }
                )
            )

            AuthConfiguration.OtpScreenConfig -> AuthChild.OtpScreenChild(
                OtpScreenComponent(
                    componentContext = context, onGoBack = {
                        navigation.pop()
                    }
                )
            )

            AuthConfiguration.SignupScreenConfig -> AuthChild.SignupScreenChild(
                SignupScreenComponent(
                    componentContext = context, onRootNavigate = onRootNavigate, onGoBack = {
                        navigation.pop()
                    }, onNavigate = {
                        navigation.pushNew(it)
                    }
                )
            )
        }
    }

    fun onPop() {
        navigation.pop()
    }

    sealed class AuthChild {
        data class LoginScreenChild(val component: LoginScreenComponent) : AuthChild()
        data class SignupScreenChild(val component: SignupScreenComponent) : AuthChild()
        data class ForgotPasswordScreenChild(val component: ForgotPasswordScreenComponent) :
            AuthChild()

        data class OtpScreenChild(val component: OtpScreenComponent) : AuthChild()
    }

    @Serializable
    sealed class AuthConfiguration {
        @Serializable
        data object LoginScreenConfig : AuthConfiguration()

        @Serializable
        data object SignupScreenConfig : AuthConfiguration()

        @Serializable
        data object ForgotPasswordScreenConfig : AuthConfiguration()

        @Serializable
        data object OtpScreenConfig : AuthConfiguration()
    }

}