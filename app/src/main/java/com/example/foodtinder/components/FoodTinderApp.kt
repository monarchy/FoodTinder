package com.example.foodtinder.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.foodtinder.ui_screen.DishScreen
import com.example.foodtinder.ui_screen.UserSelectionScreen

@Composable
fun FoodTinderApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "user_selection") {
        composable("user_selection") {
            UserSelectionScreen { userId ->
                navController.navigate("dish_screen/$userId")
            }
        }

        composable("dish_screen/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            DishScreen(userId = userId)
        }
    }
}