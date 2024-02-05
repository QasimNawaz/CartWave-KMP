package presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CartWaveOutlinedTextField(
    modifier: Modifier,
    testTag: String = "",
    errorTestTag: String = "",
    value: String,
    isError: Boolean = false,
    errorMessage: String? = "",
    onValueChange: ((String) -> Unit),
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    iconDrawable: DrawableResource? = null
) {
    Column {
        OutlinedTextField(
            modifier = modifier.testTag(testTag),
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label) },
            isError = isError,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            trailingIcon = {
                iconDrawable?.let {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "null",
                    )
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,

                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),

                cursorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            )
        )
        if (isError) {
            ErrorText(
                modifier = Modifier
                    .padding(top = 4.dp, start = 4.dp)
                    .testTag(errorTestTag),
                errorMessage
            )
        } else {
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CartWaveOutlinedTextFieldPassword(
    modifier: Modifier,
    testTag: String = "",
    errorTestTag: String = "",
    value: String,
    isError: Boolean = false,
    errorMessage: String? = "",
    onValueChange: ((String) -> Unit),
    label: String,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var showPassword by remember { mutableStateOf(value = false) }
    Column {
        OutlinedTextField(
            modifier = modifier.testTag(testTag),
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(text = label) },
            isError = isError,
            visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = keyboardActions,
            trailingIcon = {
                if (showPassword) {
                    IconButton(onClick = { showPassword = false }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_eye),
                            contentDescription = "hide_password",
                        )
                    }
                } else {
                    IconButton(onClick = { showPassword = true }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_eye_slash),
                            contentDescription = "hide_password",
                        )
                    }
                }
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,

                unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),

                cursorColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            )
        )
        if (isError) {
            ErrorText(
                modifier = Modifier
                    .testTag(errorTestTag)
                    .padding(top = 4.dp, start = 4.dp),
                errorMessage
            )
        } else {
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun ErrorText(modifier: Modifier, errorMessage: String?) {
    Text(
        modifier = modifier,
        text = errorMessage ?: "",
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
    )
}