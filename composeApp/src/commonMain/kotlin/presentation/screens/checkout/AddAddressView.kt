package presentation.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddAddressView(
    onAddAddress: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().border(
                width = 1.dp, color = Color.Gray.copy(
                    alpha = 0.5f
                ), shape = RoundedCornerShape(10.dp)
            ).clip(shape = RoundedCornerShape(10.dp)).clickable { onAddAddress.invoke() }
            .padding(vertical = 12.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier.size(50.dp),
            painter = painterResource(Res.drawable.ic_plus_circle),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Add Address",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold
        )
    }
}