import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun ResendOrLogoutText(
    onResendClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Row {
        Text(
            text = "Haven't received the email? ",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Resend",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onResendClick() }
        )

        Text(
            text = " or ",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Text(
            text = "log out",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier.clickable { onLogoutClick() }
        )

        Text(
            text = ".",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
