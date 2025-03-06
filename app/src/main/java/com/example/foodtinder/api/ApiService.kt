package com.example.foodtinder.api

import com.example.foodtinder.data.Dish
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    // Получение списка блюд
    @GET("dishes")
    suspend fun getDishes(): List<Dish>

    // Добавление нового блюда
    @POST("dishes")
    suspend fun addDish(@Body dish: Dish): Dish

    // Удаление блюда
    @DELETE("dishes/{id}")
    suspend fun removeDish(@Path("id") id: Int)
}