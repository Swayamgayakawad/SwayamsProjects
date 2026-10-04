
package UI

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import com.example.compusgo.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    val context = LocalContext.current

    var text by remember { mutableStateOf("") }
    val appName = "compusGo"

    val mediaPlayer = remember {
        MediaPlayer.create(
            context,
            R.raw.type
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    LaunchedEffect(Unit) {
        for (i in appName.indices) {
            text = appName.substring(0, i + 1)

            mediaPlayer.seekTo(0)
            mediaPlayer.start()

            delay(180)
        }

        delay(800)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 42.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "|",
                fontSize = 42.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
