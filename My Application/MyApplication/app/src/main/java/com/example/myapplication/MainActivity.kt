package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.api.ApiService
import com.example.myapplication.net.Network
import com.example.myapplication.dis.*

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class Screen { Splash, Login, Inicio, Registro }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = Network.retrofit().create(ApiService::class.java)

        setContent {
            val ctx = LocalContext.current
            var ui by remember { mutableStateOf(LoginUiState()) }
            var current by remember { mutableStateOf(Screen.Splash) }
            val scope = rememberCoroutineScope()

            // Splash temporizado (si no lo quieres, quítalo y pon Screen.Login)
            LaunchedEffect(Unit) {
                kotlinx.coroutines.delay(1500)
                current = Screen.Login
            }

            when (current) {
                Screen.Splash -> SplashScreen()

                Screen.Login -> LoginScreen(
                    state = ui,
                    onEmailChange = { ui = ui.copy(email = it) },
                    onPasswordChange = { ui = ui.copy(password = it) },
                    onSubmit = {
                        scope.launch {
                            ui = ui.copy(loading = true, error = null)
                            try {
                                withContext(Dispatchers.IO) {
                                    api.login(mapOf("email" to ui.email, "password" to ui.password))
                                }
                                current = Screen.Inicio   // ← ir a inicio
                            } catch (e: Exception) {
                                Toast.makeText(ctx, e.message ?: "Error", Toast.LENGTH_SHORT).show()
                                ui = ui.copy(error = e.message)
                            } finally {
                                ui = ui.copy(loading = false)
                            }
                        }
                    },
                    onRegister = {
                        current = Screen.Registro    // ← ir a registro
                    }
                )

                Screen.Inicio -> InicioScreen(email = ui.email)

                Screen.Registro -> RegistroScreen(
                    onBack = { current = Screen.Login }
                )
            }
        }
    }
}
