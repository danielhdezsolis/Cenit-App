package com.example.cenit.ui.runningproofs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun RunningProofsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Running Proofs Screen")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRunningProofsScreen() {
    RunningProofsScreen()
}