package com.example.foodtinder.rep

import com.example.foodtinder.api.ApiService
import com.example.foodtinder.data.Dish


class DishRepository(private val apiService: ApiService) {

    suspend fun getDishes(): List<Dish> {
        return try {
            apiService.getDishes()
        } catch (e: Exception) {
            println("Ошибка при получении блюд: ${e.message}")
            emptyList()
        }
    }

    suspend fun addDish(dish: Dish): Dish? {
        return try {
            apiService.addDish(dish)
        } catch (e: Exception) {
            println("Ошибка при добавлении блюда: ${e.message}")
            null
        }
    }


    suspend fun removeDish(id: Int) {
        try {
            apiService.removeDish(id)
        } catch (e: Exception) {
            println("Ошибка при удалении блюда: ${e.message}")
        }
    }
}
