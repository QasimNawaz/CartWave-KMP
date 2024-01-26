package presentation.screens.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.User
import domain.dto.LoginRequestDto
import domain.utils.NetworkUiState
import presentation.composables.AlertMessageDialog
import presentation.composables.CartWaveLabelledCheckBox
import presentation.composables.CartWaveOutlinedTextField
import presentation.composables.CartWaveOutlinedTextFieldPassword
import presentation.composables.LoadingDialog
import presentation.composables.SocialButton
import presentation.screens.auth.root.AuthScreenComponent
import presentation.screens.main.root.RootComponent

@Composable
fun LoginScreen(component: LoginScreenComponent) {
    val scrollState = rememberScrollState()
    var isChecked by remember { mutableStateOf(value = false) }
    val loginState: NetworkUiState<User> by component.networkUiState.collectAsState()
    val redirectToHome: Boolean by component.redirectToHome.collectAsState()
    if (redirectToHome) {
        component.onRootNavigateTo(RootComponent.Configuration.MainScreenConfig)
    }
    when (loginState) {

        is NetworkUiState.Loading -> {
            LoadingDialog()
        }

        is NetworkUiState.Error -> {
            AlertMessageDialog(title = "Something went wrong !",
                message = (loginState as NetworkUiState.Error).error,
                positiveButtonText = "Retry",
                negativeButtonText = "Cancel",
                onPositiveClick = {
                    component.performLogin(
                        LoginRequestDto(
                            email = component.emailState.text,
                            password = component.passwordState.text
                        )
                    )
                },
                onNegativeClick = { component.emptyUiState() })
        }

        is NetworkUiState.Success -> {
            (loginState as NetworkUiState.Success<User>).data.let {
                component.saveUser(it, isChecked)
            }
        }

        else -> {}
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(30.dp).verticalScroll(state = scrollState),
    ) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Hello",
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Again!",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Welcome back you’ve\n" + "been missed",
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(20.dp))
        CartWaveOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            testTag = "UsernameField",
            errorTestTag = "UsernameFieldError",
            value = component.emailState.text,
            isError = component.emailState.error != null,
            errorMessage = component.emailState.error,
            onValueChange = {
                component.onEmailChange(it)
                component.emailState.validate()
            },
            label = "Email",
            keyboardType = KeyboardType.Email,
            iconDrawable = "ic_email.xml"
        )
        Spacer(modifier = Modifier.size(5.dp))
        CartWaveOutlinedTextFieldPassword(
            modifier = Modifier.fillMaxWidth(),
            testTag = "PasswordField",
            errorTestTag = "PasswordFieldError",
            value = component.passwordState.text,
            isError = component.passwordState.error != null,
            errorMessage = component.passwordState.error,
            onValueChange = {
                component.onPasswordChange(it)
                component.passwordState.validate()
            },
            label = "Password"
        )

        Spacer(modifier = Modifier.size(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            CartWaveLabelledCheckBox(
                modifier = Modifier.fillMaxWidth().weight(1f),
                isChecked = isChecked,
                onCheckedChange = { isChecked = it },
                label = "Remember me"
            )

            Text(
                modifier = Modifier.fillMaxWidth().weight(1f).clickable {
                    component.onNavigateTo(
                        AuthScreenComponent.AuthConfiguration.ForgotPasswordScreenConfig
                    )
                },
                text = "Forgot the password?",
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth().testTag("LoginButton"), onClick = {
                component.performLogin(
                    LoginRequestDto(
                        email = component.emailState.text,
                        password = component.passwordState.text
                    )
                )
            }, colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
            ), enabled = component.ifAllDataValid()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            text = "or continue with",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            SocialButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                resource = "ic_google.xml",
                label = "Google"
            )
            Spacer(modifier = Modifier.size(10.dp))
            SocialButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                resource = "ic_facebook.xml",
                label = "Facebook"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                .wrapContentWidth()
        ) {
            Text(
                text = "don't have an account ? ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier.clickable {
                    component.onNavigateTo(
                        AuthScreenComponent.AuthConfiguration.SignupScreenConfig
                    )
                }, text = "Sign Up", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp
            )
        }
    }
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "LoginScreen",
//            modifier = Modifier.clickable { component.onRootNavigateTo(RootComponent.Configuration.MainScreenConfig) })
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(text = "Signup", modifier = Modifier.clickable {
//            component.onNavigateTo(
//                AuthScreenComponent.AuthConfiguration.SignupScreenConfig
//            )
//        })
//        Spacer(modifier = Modifier.height(15.dp))
//        Text(text = "Forgot", modifier = Modifier.clickable {
//            component.onNavigateTo(
//                AuthScreenComponent.AuthConfiguration.ForgotPasswordScreenConfig
//            )
//        })
//    }
}