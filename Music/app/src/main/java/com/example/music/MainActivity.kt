package com.example.music

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.music.ui.theme.MusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    var Vbox by remember { mutableStateOf(false)}

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                if (Vbox) {Vbox = false}else {Vbox = true}
            },
            modifier = modifier.padding(24.dp)
        ) {
            Text(text = "Animetion")
        }
        var Vcoler by remember { mutableStateOf(false)}
        Button(
            onClick = {
                if (Vcoler) {Vcoler = false}else {Vcoler = true}
            },
            modifier = modifier.padding(24.dp)
        ) {
            Text(text = "change color")
        }
        val Size by animateDpAsState(
            targetValue = if (Vbox) 400.dp else 0.dp,
            label = "SizeAnimation"
        )
        val color by animateColorAsState(
            targetValue = if (Vcoler) Color.Gray else Color.Green,
            label = "colorAnimation"
        )


        Box(
            modifier = modifier.size(Size)
                .background(color)


        ) {

    }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MusicTheme {
        Greeting("Android")
    }
}