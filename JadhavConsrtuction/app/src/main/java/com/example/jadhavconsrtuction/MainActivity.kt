package com.example.jadhavconsrtuction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.jadhavconsrtuction.ui.theme.JadhavConsrtuctionTheme
import com.example.jadhavconsrtuction.ui.theme.Screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JadhavConsrtuctionTheme {


            }
        }
    }
}


@Preview(
    showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    JadhavConsrtuctionTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}