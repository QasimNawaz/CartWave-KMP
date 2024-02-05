package presentation.screens.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cartwave_kmp.composeapp.generated.resources.Res
import data.model.User
import domain.dto.RegisterRequestDto
import domain.utils.NetworkUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import platform.PermissionCallback
import platform.PermissionStatus
import platform.PermissionType
import platform.SharedImage
import platform.createPermissionsManager
import platform.rememberCameraManager
import platform.rememberGalleryManager
import presentation.composables.AlertMessageDialog
import presentation.composables.CartWaveLabelledCheckBox
import presentation.composables.CartWaveOutlinedTextField
import presentation.composables.CartWaveOutlinedTextFieldPassword
import presentation.composables.ImageSourceOptionDialog
import presentation.composables.LoadingDialog
import presentation.composables.SocialButton
import presentation.screens.main.root.RootComponent
import utils.Logger

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
@Composable
fun SignupScreen(component: SignupScreenComponent) {
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    var sharedImage by remember { mutableStateOf<SharedImage?>(null) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    var isChecked by remember { mutableStateOf(value = false) }
    val fNameFocusRequester = remember { FocusRequester() }
    val lNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passFocusRequester = remember { FocusRequester() }
    val signupState: NetworkUiState<User> by component.networkUiState.collectAsState()
    val redirectToHome: Boolean by component.redirectToHome.collectAsState()
    if (redirectToHome) {
        component.onRootNavigateTo(RootComponent.Configuration.MainScreenConfig)
    }
    when (signupState) {

        is NetworkUiState.Loading -> {
            LoadingDialog()
        }

        is NetworkUiState.Error -> {
            AlertMessageDialog(title = "Something went wrong !",
                message = (signupState as NetworkUiState.Error).error,
                positiveButtonText = "Retry",
                negativeButtonText = "Cancel",
                onPositiveClick = {
                    component.performRegister(
                        RegisterRequestDto(
                            firstName = component.firstNameState.text,
                            lastName = component.lastNameState.text,
                            avatar = null,
                            email = component.emailState.text,
                            password = component.passwordState.text
                        )
                    )
                },
                onNegativeClick = { component.emptyUiState() })
        }

        is NetworkUiState.Success -> {
            (signupState as NetworkUiState.Success<User>).data.let {
                component.saveUser(it, isChecked)
            }
        }

        else -> {}
    }

    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            Logger.d("${permissionType.name}, ${status.name}", tag = "onPermissionStatus")
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })

    val cameraManager = rememberCameraManager {
        sharedImage = it
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                sharedImage?.toImageBitmap()
            }
            imageBitmap = bitmap
        }
    }

    val galleryManager = rememberGalleryManager {
        sharedImage = it
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.IO) {
                sharedImage?.toImageBitmap()
            }
            imageBitmap = bitmap
        }
    }

    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }
    if (permissionRationalDialog) {
        AlertMessageDialog(title = "Permission Required",
            message = "To set your profile picture, please grant this permission. You can manage permissions in your device settings.",
            positiveButtonText = "Settings",
            negativeButtonText = "Cancel",
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true

            },
            onNegativeClick = {
                permissionRationalDialog = false
            })

    }
    Column(modifier = Modifier.fillMaxSize().padding(30.dp).verticalScroll(scrollState)) {
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Hello!",
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Signup to get started",
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier.fillMaxWidth().height(110.dp),
            contentAlignment = Alignment.Center
        ) {
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap!!,
                    contentDescription = "Profile",
                    modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
                        imageSourceOptionDialog = true
                    },
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
                        imageSourceOptionDialog = true
                    },
                    painter = painterResource(Res.drawable.ic_person_circle),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
        CartWaveOutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(fNameFocusRequester),
            value = component.firstNameState.text,
            isError = component.firstNameState.error != null,
            errorMessage = component.firstNameState.error,
            onValueChange = {
                component.onFirstNameChange(it)
                component.firstNameState.validate()
            },
            label = "First Name",
            iconDrawable = Res.drawable.ic_profile,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { lNameFocusRequester.requestFocus() })
        )
        Spacer(modifier = Modifier.size(10.dp))
        CartWaveOutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(lNameFocusRequester),
            value = component.lastNameState.text,
            isError = component.lastNameState.error != null,
            errorMessage = component.lastNameState.error,
            onValueChange = {
                component.onLastNameChange(it)
                component.lastNameState.validate()
            },
            label = "Last Name",
            iconDrawable = Res.drawable.ic_profile,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { emailFocusRequester.requestFocus() })
        )
        Spacer(modifier = Modifier.size(10.dp))
        CartWaveOutlinedTextField(
            modifier = Modifier.fillMaxWidth().focusRequester(emailFocusRequester),
            value = component.emailState.text,
            isError = component.emailState.error != null,
            errorMessage = component.emailState.error,
            onValueChange = {
                component.onEmailChange(it)
                component.emailState.validate()
            },
            label = "Email",
            keyboardType = KeyboardType.Email,
            iconDrawable = Res.drawable.ic_email,
            imeAction = ImeAction.Next,
            keyboardActions = KeyboardActions(onNext = { passFocusRequester.requestFocus() })
        )
        Spacer(modifier = Modifier.size(10.dp))
        CartWaveOutlinedTextFieldPassword(
            modifier = Modifier.fillMaxWidth().focusRequester(passFocusRequester),
            value = component.passwordState.text,
            isError = component.passwordState.error != null,
            errorMessage = component.passwordState.error,
            onValueChange = {
                component.onPasswordChange(it)
                component.passwordState.validate()
            },
            label = "Password",
            imeAction = ImeAction.Done,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.size(10.dp))
        CartWaveLabelledCheckBox(
            modifier = Modifier.fillMaxWidth(),
            isChecked = isChecked,
            onCheckedChange = { isChecked = it },
            label = "Remember me"
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(), onClick = {
                component.performRegister(
                    RegisterRequestDto(
                        firstName = component.firstNameState.text,
                        lastName = component.lastNameState.text,
                        avatar = null,
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
            Text(text = "Signup")
        }
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "or continue with",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SocialButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                resource = Res.drawable.ic_google,
                label = "Google"
            )
            Spacer(modifier = Modifier.size(10.dp))
            SocialButton(
                modifier = Modifier.fillMaxWidth().weight(1f),
                resource = Res.drawable.ic_facebook,
                label = "Facebook"
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account ? ",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
            Text(
                modifier = Modifier.clickable { component.goBack() },
                text = "Login",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            )
        }
    }
}
