package presentation.screens.auth.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import presentation.screens.auth.forgot.ForgotPasswordScreen
import presentation.screens.auth.login.LoginScreen
import presentation.screens.auth.otp.OtpScreen
import presentation.screens.auth.signup.SignupScreen

@Composable
fun AuthScreen(component: AuthScreenComponent) {
    Box(modifier = Modifier.fillMaxSize()) {
        Children(
            stack = component.childStack,
            animation = stackAnimation(slide())
        ) { child ->
            when (val instance = child.instance) {
                is AuthScreenComponent.AuthChild.ForgotPasswordScreenChild -> ForgotPasswordScreen(
                    instance.component
                )

                is AuthScreenComponent.AuthChild.LoginScreenChild -> LoginScreen(instance.component)
                is AuthScreenComponent.AuthChild.OtpScreenChild -> OtpScreen(instance.component)
                is AuthScreenComponent.AuthChild.SignupScreenChild -> SignupScreen(instance.component)
            }
        }
    }
}