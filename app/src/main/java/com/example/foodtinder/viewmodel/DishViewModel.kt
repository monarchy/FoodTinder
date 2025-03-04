package com.example.foodtinder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodtinder.data.Dish


class DishViewModel : ViewModel() {
    private val _dishes = mutableListOf(
        Dish("1", "Пицца", "Классическая итальянская пицца", "https://example.com/pizza.jpg"),
        Dish("2", "Суши", "Японские суши с лососем", "https://example.com/sushi.jpg"),
        Dish("3", "Паста", "Паста с томатным соусом", "https://example.com/pasta.jpg")
    )

    // Функция для получения списка блюд
    val dishes: List<Dish>
        get() = _dishes

    fun likeDish(dish: Dish) {
        println("Лайкнули блюдо: ${dish.name}")
    }

    fun dislikeDish(dish: Dish) {
        println("Дизлайкнули блюдо: ${dish.name}")
    }
}