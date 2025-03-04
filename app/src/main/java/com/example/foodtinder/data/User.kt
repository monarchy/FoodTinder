package com.example.foodtinder.data

data class User(
    val id: String,
    val name: String,
    val profileImageUrl: String,
    var isFavorite: Boolean = false,
    var isFriend: Boolean = false
)
