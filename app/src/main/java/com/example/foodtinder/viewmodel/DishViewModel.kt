package com.example.foodtinder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodtinder.data.Dish
import com.example.foodtinder.rep.DishRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class DishViewModel(private val repository: DishRepository) : ViewModel() {

    private val _dishes = MutableStateFlow<List<Dish>>(emptyList())
    val dishes: StateFlow<List<Dish>> = _dishes

    init {
        loadDishes()
    }

    // Загрузка блюд из API
    private fun loadDishes() {
        viewModelScope.launch {
            _dishes.value = repository.getDishes()
        }
    }

    // Добавление нового блюда
    fun addDish(dish: Dish) {
        viewModelScope.launch {
            repository.addDish(dish)?.let { newDish ->
                val updatedDishes = _dishes.value.toMutableList()
                updatedDishes.add(newDish)
                _dishes.value = updatedDishes
            }
        }
    }

    // Удаление блюда
    fun removeDish(id: Int) {
        viewModelScope.launch {
            repository.removeDish(id)
            val updatedDishes = _dishes.value.filter { it.id != id.toString() }
            _dishes.value = updatedDishes
        }
    }

    // Обработка лайка
    fun likeDish(dish: Dish) {
        println("Лайкнули блюдо: ${dish.name}")
    }

    // Обработка дизлайка
    fun dislikeDish(dish: Dish) {
        println("Дизлайкнули блюдо: ${dish.name}")
    }
}