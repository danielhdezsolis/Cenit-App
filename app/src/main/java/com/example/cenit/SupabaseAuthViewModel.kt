package com.example.cenit

import android.content.Context
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cenit.data.model.UserState
import com.example.cenit.data.network.SupabaseClient.client
import com.example.cenit.utils.SharePreferenceHelper
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf

class SupabaseAuthViewModel : ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun signUp(
        context: Context,
        userEmail: String,
        userPassword: String
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Usuario registrado exitosamente")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = client.gotrue.currentAccessTokenOrNull() ?: ""
            val sharedPref = SharePreferenceHelper(context)
            sharedPref.saveString("accessToken", accessToken)
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharePreferenceHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    fun login(
        context: Context,
        userEmail: String,
        userPassword: String
    ) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.loginWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Login exitoso")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    fun logout(context: Context) {
        val sharedPref = SharePreferenceHelper(context)
        viewModelScope.launch {
            _userState.value = UserState.Loading
            try {
                client.gotrue.logout()
                sharedPref.cleaPreferences()
                _userState.value = UserState.Success("Logout exitoso")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }

    fun isUserLoggedIn(
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("El usuario no está autenticado")
                } else {
                    client.gotrue.retrieveUser(token)
                    client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("El usuario está autenticado")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error: ${e.message}" ?: "Error desconocido")
            }
        }
    }
}