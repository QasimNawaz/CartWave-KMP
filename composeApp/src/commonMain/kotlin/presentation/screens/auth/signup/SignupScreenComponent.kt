package presentation.screens.auth.signup

import com.arkivanov.decompose.ComponentContext
import data.model.User
import data.repository.cache.KeyValueStorageRepo
import domain.dto.RegisterRequestDto
import domain.usecase.auth.RegisterUseCase
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
import utils.NameState
import utils.PasswordState
import utils.asUiState
import utils.coroutineScope

class SignupScreenComponent(
    private val componentContext: ComponentContext,
    private val onRootNavigate: (config: RootComponent.Configuration) -> Unit,
    private val onNavigate: (config: AuthScreenComponent.AuthConfiguration) -> Unit,
    private val onGoBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = coroutineScope()
    private val registerUseCase: RegisterUseCase by inject()
    private val keyValueStorageRepo: KeyValueStorageRepo by inject()

    private val _networkUiState = MutableStateFlow<NetworkUiState<User>>(NetworkUiState.Empty)
    val networkUiState: StateFlow<NetworkUiState<User>> = _networkUiState.asStateFlow()

    private val _firstNameState = NameState()
    val firstNameState = _firstNameState

    private val _lastNameState = NameState()
    val lastNameState = _lastNameState

    private val _emailState = EmailState()
    val emailState = _emailState

    private val _passwordState = PasswordState()
    val passwordState = _passwordState

    private val _redirectToHome = MutableStateFlow<Boolean>(false)
    val redirectToHome: StateFlow<Boolean> = _redirectToHome

    fun onFirstNameChange(name: String) {
        _firstNameState.text = name
    }

    fun onLastNameChange(name: String) {
        _lastNameState.text = name
    }

    fun onEmailChange(email: String) {
        _emailState.text = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.text = password
    }

    fun performRegister(registerRequestDto: RegisterRequestDto) {
        coroutineScope.launch {
            registerUseCase.execute(RegisterUseCase.Params(registerRequestDto))
                .asUiState(uiState = _networkUiState)
        }
    }

    fun ifAllDataValid(): Boolean {
        return firstNameState.error == null && firstNameState.text.isNotBlank() && lastNameState.error == null && lastNameState.text.isNotBlank() && emailState.error == null && emailState.text.isNotBlank() && passwordState.error == null && passwordState.text.isNotBlank()
    }

    fun onRootNavigateTo(config: RootComponent.Configuration) {
        onRootNavigate.invoke(config)
    }

    fun onNavigateTo(config: AuthScreenComponent.AuthConfiguration) {
        onNavigate.invoke(config)
    }

    fun saveUser(user: User, remember: Boolean) {
        coroutineScope.launch {
            keyValueStorageRepo.doRemember = remember
            keyValueStorageRepo.accessToken = user.authToken
            keyValueStorageRepo.user = user
            _redirectToHome.value = true
        }
    }

    fun emptyUiState() {
        coroutineScope.launch {
            _networkUiState.emit(NetworkUiState.Empty)
        }
    }

    fun goBack() {
        onGoBack.invoke()
    }
}