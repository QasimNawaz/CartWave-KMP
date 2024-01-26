package presentation.screens.auth.login

import com.arkivanov.decompose.ComponentContext
import data.model.User
import data.repository.cache.KeyValueStorageRepo
import domain.dto.LoginRequestDto
import domain.usecase.auth.LoginUseCase
import domain.utils.NetworkUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screens.auth.root.AuthScreenComponent
import presentation.screens.main.root.RootComponent
import utils.EmailState
import utils.PasswordState
import utils.asUiState
import utils.coroutineScope

class LoginScreenComponent(
    componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
    private val onNavigate: (config: AuthScreenComponent.AuthConfiguration) -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = coroutineScope()
    private val loginUseCase: LoginUseCase by inject()
    private val keyValueStorageRepo: KeyValueStorageRepo by inject()

    private val _emailState = EmailState()
    val emailState = _emailState

    private val _passwordState = PasswordState()
    val passwordState = _passwordState

    private val _networkUiState = MutableStateFlow<NetworkUiState<User>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<User>> = _networkUiState.asStateFlow()

    private val _redirectToHome = MutableStateFlow<Boolean>(false)
    val redirectToHome: StateFlow<Boolean> = _redirectToHome

    fun onEmailChange(username: String) {
        _emailState.text = username
    }


    fun onPasswordChange(password: String) {
        _passwordState.text = password
    }

    fun ifAllDataValid(): Boolean {
        return emailState.error == null && emailState.text.isNotBlank() && passwordState.error == null && passwordState.text.isNotBlank()
    }

    fun performLogin(loginRequestDto: LoginRequestDto) {
        coroutineScope.launch {
            loginUseCase.execute(LoginUseCase.Params(loginRequestDto))
                .asUiState(uiState = _networkUiState)
        }
    }


    fun saveUser(user: User, remember: Boolean) {
        coroutineScope.launch {
            keyValueStorageRepo.doRemember = remember
            keyValueStorageRepo.user = user
            _redirectToHome.value = true
        }
    }

    fun emptyUiState() {
        coroutineScope.launch {
            _networkUiState.emit(NetworkUiState.Empty)
        }
    }

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }

    fun onNavigateTo(config: AuthScreenComponent.AuthConfiguration) {
        onNavigate.invoke(config)
    }

}