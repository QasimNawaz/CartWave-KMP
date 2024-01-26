package presentation.screens.auth.forgot

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.composables.CartWaveOutlinedTextField

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ForgotPasswordScreen(component: ForgotPasswordScreenComponent) {
    val scrollState = rememberScrollState()
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(30.dp).verticalScroll(state = scrollState),
    ) {
        IconButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = { component.goBack() }) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource("ic_arrow_left.xml"),
                contentDescription = "null",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Text(
            text = "Forgot\nPassword?",
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "Don’t worry! it happens. Please enter the\n" + "address associated with your account.",
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.size(20.dp))
        CartWaveOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = userName,
            onValueChange = { userName = it },
            label = "Email ID / Mobile number",
            iconDrawable = null
        )
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth().weight(1f, false), onClick = {
                component.goBack()
            }, colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "Submit")
        }
    }
}