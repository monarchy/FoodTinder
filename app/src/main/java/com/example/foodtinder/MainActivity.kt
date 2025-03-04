package com.example.foodtinder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.foodtinder.ui_screen.AuthScreen
import com.example.foodtinder.ui_screen.UserSelectionScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Проверяем, выполнен ли вход
            val auth = FirebaseAuth.getInstance()
            if (auth.currentUser != null) {
                // Если пользователь авторизован, показываем экран выбора человека
                UserSelectionScreen()
            } else {
                // Иначе показываем экран авторизации
                AuthScreen()
            }
        }
    }
}


