package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.api.ApiService
import com.example.myapplication.net.Network
import com.example.myapplication.dis.InicioScreen
import com.example.myapplication.dis.LoginScreen
import com.example.myapplication.dis.LoginUiState
import com.example.myapplication.dis.RegistroScreen
import com.example.myapplication.dis.SplashScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class Screen { Splash, Login, Inicio, Registro }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val ctx = LocalContext.current
            val api = remember { Network.retrofit().create(ApiService::class.java) }

            var ui by remember { mutableStateOf(LoginUiState()) }
            var current by remember { mutableStateOf(Screen.Splash) }
            val scope = rememberCoroutineScope()

            when (current) {
                Screen.Splash -> SplashScreen(
                    onFinished = { current = Screen.Login } // el propio splash decide cuándo terminar
                )

                Screen.Login -> LoginScreen(
                    state = ui,
                    onEmailChange = { ui = ui.copy(email = it) },
                    onPasswordChange = { ui = ui.copy(password = it) },
                    onSubmit = {
                        scope.launch {
                            // ⛳️ MODO DEV: pasa directo a Inicio
                            current = Screen.Inicio
                            // si quieres mostrar algo en Inicio, ui.email quedará con lo que escribiste (ej: "123")
                        }
                    },
                    onRegister = { current = Screen.Registro }
                )

                Screen.Inicio -> InicioScreen(email = ui.email)

                Screen.Registro -> RegistroScreen(onBack = { current = Screen.Login })
            }
        }
    }
}
