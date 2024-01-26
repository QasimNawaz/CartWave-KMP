package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingDialog(
    cornerRadius: Dp = 16.dp,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    progressIndicatorSize: Dp = 80.dp
) {
    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(start = 42.dp, end = 42.dp) // margin
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(cornerRadius)
                )
                .padding(top = 36.dp, bottom = 36.dp), // inner padding
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ProgressIndicatorLoading(
                progressIndicatorSize = progressIndicatorSize,
                progressIndicatorColor = progressIndicatorColor
            )

            // Gap between progress indicator and text
            Spacer(modifier = Modifier.height(32.dp))

            // Please wait text
            Text(
                text = "Please wait...",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}