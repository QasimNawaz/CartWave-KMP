package presentation.screens.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cartwave_kmp.composeapp.generated.resources.Res
import domain.model.Address
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CheckoutAddress(address: Address, onEditAddress: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Address",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.clickable {
                    onEditAddress.invoke()
                },
                text = "Edit",
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth().border(
                width = 1.dp, color = Color.Gray.copy(
                    alpha = 0.5f
                ), shape = RoundedCornerShape(10.dp)
            ).clip(shape = RoundedCornerShape(10.dp))
                .padding(vertical = 12.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.width(100.dp).height(75.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                painter = painterResource(Res.drawable.ic_map),
                contentScale = ContentScale.FillBounds,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = address.address,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}