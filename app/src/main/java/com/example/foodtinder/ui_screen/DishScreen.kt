package com.example.foodtinder.ui_screen

import SwipeableCard
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodtinder.api.ApiClient
import com.example.foodtinder.rep.DishRepository
import com.example.foodtinder.viewmodel.DishViewModel

@Composable
fun DishScreen(userId: String) {
    val repository = remember { DishRepository(ApiClient.apiService) }
    val viewModel = remember { DishViewModel(repository) }


    val dishes by viewModel.dishes.collectAsState(emptyList())
    var currentDishIndex by remember { mutableStateOf(0) }


    if (currentDishIndex < dishes.size) {
        val currentDish = dishes[currentDishIndex]

        Column(modifier = Modifier.fillMaxSize()) {
            // Карточка блюда
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                SwipeableCard(
                    dish = currentDish,
                    onLike = {
                        viewModel.likeDish(currentDish)
                    },
                    onDislike = {
                        viewModel.dislikeDish(currentDish)
                    },
                    onAnimationComplete = {
                        currentDishIndex++
                    }
                )
            }

            // Кнопки под карточкой
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                        viewModel.likeDish(currentDish)
                        currentDishIndex++
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Like",
                        tint = Color.Green,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.dislikeDish(currentDish)
                        currentDishIndex++ // Переходим к следующей карточке
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dislike",
                        tint = Color.Red,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    } else {
        Text("Вы просмотрели все блюда!", modifier = Modifier.fillMaxSize())
    }
}