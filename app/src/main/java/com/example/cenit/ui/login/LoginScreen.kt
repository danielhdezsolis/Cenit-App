package com.example.cenit.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cenit.R
import com.example.cenit.SupabaseAuthViewModel
import com.example.cenit.components.Loading
import com.example.cenit.data.model.UserState

@Composable
fun LoginScreen(viewModel: SupabaseAuthViewModel = viewModel()) {
    val context = LocalContext.current
    val userState by viewModel.userState
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var currentUserState by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.isUserLoggedIn(
            context
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Spacer para empujar la imagen hacia abajo
        Spacer(modifier = Modifier.height(150.dp)) // Ajusta el valor según sea necesario

        Image(
            painter = painterResource(id = R.drawable.cenit_dark_text),
            contentDescription = "Logo",
            modifier = Modifier
                .width(115.dp)    // Definir ancho
                .height(30.dp)   // Definir alto
                .align(Alignment.CenterHorizontally)
        )
        // Column para centrar los TextField
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // Utilizamos weight para que ocupe el espacio disponible
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar los campos
        ) {
            TextField(
                value = userEmail,
                placeholder = { Text("Ingresa email") },
                onValueChange = { userEmail = it },
                label = { Text("Correo electrónico") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                value = userPassword,
                placeholder = { Text("Ingresa password") },
                onValueChange = { userPassword = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        // Spacer para separar los campos del área de los botones
        Spacer(modifier = Modifier.height(16.dp))

        // Usamos un Spacer para empujar los botones al fondo
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Button(onClick = {
//                viewModel.signUp(
//                    context,
//                    userEmail,
//                    userPassword
//                )
//            }) {
//                Text(text = "Inscribirse")
//            }
//            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {
                viewModel.login(
                    context,
                    userEmail,
                    userPassword
                )
            }) {
                Text(text = "Iniciar sesión")
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {
                    viewModel.logout(context)
                }) {
                Text(text = "Cerrar sesión")
            }
        }

        when (userState) {
            is UserState.Loading -> {
                Loading()
            }

            is UserState.Success -> {
                val message = (userState as UserState.Success).message
                currentUserState = message
            }

            is UserState.Error -> {
                val message = (userState as UserState.Error).message
                currentUserState = message
            }
        }

        if (currentUserState.isNotEmpty()) {
            Text(text = currentUserState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}
