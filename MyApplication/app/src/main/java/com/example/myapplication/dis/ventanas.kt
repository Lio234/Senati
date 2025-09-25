package com.example.myapplication.dis

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.widget.doAfterTextChanged
import com.example.myapplication.databinding.SettingsActivityBinding   // layout: res/layout/settings_activity.xml
import com.example.myapplication.databinding.InicioBinding          // layout: res/layout/inicio.xml
import com.example.myapplication.databinding.RegistroBinding        // layout: res/layout/registro.xml
import com.example.myapplication.databinding.SplashBinding

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    var started by remember { mutableStateOf(false) }

    AndroidViewBinding(SplashBinding::inflate) {
        if (!started) {
            started = true
            // Desvanece todo el contenedor del splash
            root.alpha = 1f
            root.animate()
                .setStartDelay(800)   // visible 0.8s (ajústalo)
                .alpha(0f)            // fade-out
                .setDuration(600)     // duración del fade
                .withEndAction { onFinished() }
                .start()


        }
    }
}
@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onRegister: () -> Unit,
) {
    AndroidViewBinding(SettingsActivityBinding::inflate) {

        if (email.text.toString() != state.email) {
            email.setText(state.email); email.setSelection(state.email.length)
        }
        if (contra.text.toString() != state.password) {
            contra.setText(state.password); contra.setSelection(state.password.length)
        }

        if (email.tag != "watch") { email.doAfterTextChanged { onEmailChange(it?.toString().orEmpty()) }; email.tag = "watch" }
        if (contra.tag != "watch") { contra.doAfterTextChanged { onPasswordChange(it?.toString().orEmpty()) }; contra.tag = "watch" }

        login.text = "LOGIN"
        login.isEnabled = !state.loading && state.email.isNotBlank() && state.password.isNotBlank()
        login.setOnClickListener { onSubmit() }

        registro.text = "REGÍSTRATE"
        registro.setOnClickListener { onRegister() }
    }
}

@Composable
fun InicioScreen(email: String) {
    AndroidViewBinding(InicioBinding::inflate) {
        titulo.text = "Bienvenido 👋"
        emailText.text = email
    }
}

@Composable
fun RegistroScreen(onBack: () -> Unit) {
    AndroidViewBinding(RegistroBinding::inflate) {
        // asegúrate de tener un botón con id btnBack
        btnBack.setOnClickListener { onBack() }
    }
}
