package com.example.cenit

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cenit.data.model.UserState
import com.example.cenit.data.network.SupabaseClient.client
import com.example.cenit.utils.UserPreferences
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SupabaseAuthViewModel : ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    // Funciones para validar email y contraseña
    private fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    // Mostrar mensaje de error si las validaciones fallan
    private fun showValidationError(message: String) {
        _userState.value = UserState.Error(message)
    }

    // Registro de usuario con validaciones
    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String
    ) {
        val userPreferences = UserPreferences(context)
        viewModelScope.launch {
            if (!validateEmail(userEmail)) {
                showValidationError("Correo inválido")
                return@launch
            }
            if (!validatePassword(userPassword)) {
                showValidationError("La contraseña debe tener al menos 6 caracteres")
                return@launch
            }

            _userState.value = UserState.Loading
            try {
                client.gotrue.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(userPreferences)
                _userState.value = UserState.Success("Usuario registrado exitosamente")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    // Guardar token usando DataStore
    private suspend fun saveToken(userPreferences: UserPreferences) {
        val accessToken = client.gotrue.currentAccessTokenOrNull() ?: ""
        userPreferences.saveAccessToken(accessToken)
    }

    // Login con validaciones
    fun login(
        context: Context,
        userEmail: String,
        userPassword: String
    ) {
        val userPreferences = UserPreferences(context)
        viewModelScope.launch {
            if (!validateEmail(userEmail)) {
                showValidationError("Correo inválido")
                return@launch
            }
            if (!validatePassword(userPassword)) {
                showValidationError("La contraseña debe tener al menos 6 caracteres")
                return@launch
            }

            _userState.value = UserState.Loading
            try {
                client.gotrue.loginWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(userPreferences)
                _userState.value = UserState.Success("Login exitoso")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    // Cerrar sesión y limpiar preferencias
    fun logout(context: Context) {
        val userPreferences = UserPreferences(context)
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.logout()
                userPreferences.clearPreferences()
                _userState.value = UserState.Success("Logout exitoso")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    // Comprobar si el usuario está autenticado
    fun isUserLoggedIn(context: Context) {
        val userPreferences = UserPreferences(context)
        viewModelScope.launch {
            try {
                val token = userPreferences.accessToken.first()
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("El usuario no está autenticado")
                } else {
                    client.gotrue.retrieveUser(token)
                    client.gotrue.refreshCurrentSession()
                    saveToken(userPreferences)
                    _userState.value = UserState.Success("El usuario está autenticado")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    fun resetUserState() {
        _userState.value = UserState.ClearError
    }
}
