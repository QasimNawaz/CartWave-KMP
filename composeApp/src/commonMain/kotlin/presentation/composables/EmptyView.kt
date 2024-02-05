package presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun EmptyView(drawable: DrawableResource, text: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(drawable),
            contentDescription = null,
        )
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}