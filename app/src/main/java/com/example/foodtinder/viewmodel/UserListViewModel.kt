package com.example.foodtinder.viewmodel

import androidx.lifecycle.ViewModel
import com.example.foodtinder.data.User

class UserListViewModel : ViewModel() {
    private val _users = mutableListOf(
        User("1", "John Doe", "https://example.com/avatar1.jpg"),
        User("2", "Jane Smith", "https://example.com/avatar2.jpg"),
        User("3", "Alice Johnson", "https://example.com/avatar3.jpg")
    )

    val users: List<User>
        get() = _users

    fun toggleFavorite(user: User) {
        val index = _users.indexOf(user)
        if (index != -1) {
            _users[index] = user.copy(isFavorite = !user.isFavorite)
        }
    }

    fun addFriend(user: User) {
        val index = _users.indexOf(user)
        if (index != -1) {
            _users[index] = user.copy(isFriend = true)
        }
    }

    fun getFilteredUsers(filter: String): List<User> {
        return when (filter) {
            "Избранное" -> _users.filter { it.isFavorite }
            "Друзья" -> _users.filter { it.isFriend }
            else -> _users
        }
    }
}