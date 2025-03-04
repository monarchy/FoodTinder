package com.example.foodtinder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthScreen()
        }
    }
}

@Composable
fun AuthScreen() {
    val viewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поля ввода
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        // Кнопка
        Button(
            onClick = {
                if (isLoginMode) {
                    viewModel.login(email, password)
                } else {
                    viewModel.register(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(if (isLoginMode) "Войти" else "Зарегистрироваться")
        }

        // Центрированный текст для переключения между режимами
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextButton(
                onClick = { isLoginMode = !isLoginMode },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = if (isLoginMode) "Нет аккаунта? Зарегистрироваться" else "Уже есть аккаунт? Войти",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Отображение состояния аутентификации
        when (val state = viewModel.authState.collectAsState().value) {
            is AuthState.Success -> {
                Text(state.message, color = MaterialTheme.colorScheme.primary)
                if (!isLoginMode && state.message == "Регистрация успешна") {
                    isLoginMode = true // Переключаемся на форму входа
                }
            }

            is AuthState.Error -> Text(state.message, color = MaterialTheme.colorScheme.error)
            AuthState.Initial -> {}
        }
    }
}
