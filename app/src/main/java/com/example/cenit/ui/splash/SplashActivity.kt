package com.example.cenit.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cenit.R
import com.example.cenit.ui.home.MainActivity
import com.example.cenit.ui.theme.CenitTheme
import com.example.cenit.ui.theme.whiteColor
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CenitTheme {
                SplashScreen {
                    // Navegar a MainActivity después de la Splash
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val fontFamily =  FontFamily(Font(R.font.spacegrotesk_regular))
    LaunchedEffect(Unit) {
        delay(1500) // 2 segundos de espera
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.inicio_cenit),
                contentDescription = "Splash Image",
                modifier = Modifier.size(115.dp)
            )
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize(), // Margen inferior
        contentAlignment = Alignment.BottomCenter // Alinea el texto en la parte inferior
    ) {
        Text(
            text = "By Emerald",
            fontSize = 12.sp,
            fontFamily = fontFamily,
            color = whiteColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    CenitTheme {
        SplashScreen(onTimeout = {})
    }
}