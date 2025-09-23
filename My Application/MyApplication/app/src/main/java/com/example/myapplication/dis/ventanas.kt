package com.example.myapplication.dis

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.core.widget.doAfterTextChanged
import com.example.myapplication.databinding.SettingsActivityBinding
import com.example.myapplication.databinding.InicioBinding
import com.example.myapplication.databinding.RegistroBinding

// Estado del login
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)

@Composable
fun SplashScreen() {
    // añadir despues)
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

        // Sincroniza UI <- estado
        if (email.text.toString() != state.email) {
            email.setText(state.email); email.setSelection(state.email.length)
        }
        if (contra.text.toString() != state.password) {
            contra.setText(state.password); contra.setSelection(state.password.length)
        }

        // Listeners una sola vez
        if (email.tag != "watch") { email.doAfterTextChanged { onEmailChange(it?.toString().orEmpty()) }; email.tag = "watch" }
        if (contra.tag != "watch") { contra.doAfterTextChanged { onPasswordChange(it?.toString().orEmpty()) }; contra.tag = "watch" }

        // Botones
        login.text = "LOGIN"
        login.isEnabled = !state.loading && state.email.isNotBlank() && state.password.isNotBlank()
        login.setOnClickListener { onSubmit() }

        registro.text = "REGÍSTRATE"
        registro.setOnClickListener { onRegister() }     // <-- navegar a registro
    }
}

@Composable
fun InicioScreen(email: String) {
    AndroidViewBinding(InicioBinding::inflate) {
        titulo.text = "Bienvenido 👋"
        emailText.text = email.ifBlank { "Sin email" }
    }
}

@Composable
fun RegistroScreen() {
    AndroidViewBinding(RegistroBinding::inflate) {
        // de momento solo muestra el título "Registro"
        //
    }
}

@Composable
fun RegistroScreen(onBack: () -> Unit) {
    AndroidViewBinding(RegistroBinding::inflate) {
        btnBack.setOnClickListener { onBack() }
    }
}


