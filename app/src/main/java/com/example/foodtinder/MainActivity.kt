package com.example.foodtinder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.foodtinder.components.FoodTinderApp
import com.example.foodtinder.ui_screen.AuthScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val auth = FirebaseAuth.getInstance()

            if (auth.currentUser != null) {
                FoodTinderApp(navController)
            } else {
                AuthScreen()
            }
        }
    }
}


