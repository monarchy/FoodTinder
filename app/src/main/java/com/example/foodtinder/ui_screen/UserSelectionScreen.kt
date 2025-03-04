package com.example.foodtinder.ui_screen

// UserSelectionScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.foodtinder.data.User
import com.example.foodtinder.viewmodel.UserListViewModel


@Composable
fun UserSelectionScreen() {
    val viewModel: UserListViewModel = viewModel()
    var selectedTab by remember { mutableStateOf("Контакты") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Вкладки
        TabRow(selectedTabIndex = when (selectedTab) {
            "Избранное" -> 0
            "Друзья" -> 1
            else -> 2
        }) {
            listOf("Избранное", "Друзья", "Контакты").forEach { tab ->
                Tab(
                    text = { Text(tab) },
                    selected = selectedTab == tab,
                    onClick = { selectedTab = tab }
                )
            }
        }

        // Список пользователей
        val filteredUsers = viewModel.getFilteredUsers(selectedTab)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredUsers) { user ->
                UserItem(
                    user = user,
                    onFavoriteClick = { viewModel.toggleFavorite(user) },
                    onAddFriendClick = { viewModel.addFriend(user) }
                )
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onFavoriteClick: () -> Unit,
    onAddFriendClick: () -> Unit
) {
    // Кешируем текущее состояние пользователя
    var isFavorite by remember { mutableStateOf(user.isFavorite) }
    var isFriend by remember { mutableStateOf(user.isFriend) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Аватар
        Image(
            painter = rememberImagePainter(data = user.profileImageUrl),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        // Имя пользователя
        Column(modifier = Modifier.weight(1f).padding(start = 16.dp)) {
            Text(text = user.name, style = MaterialTheme.typography.bodyLarge)
        }

        // Иконка "Избранное"
        IconButton(onClick = {
            onFavoriteClick() // Обновляем состояние в ViewModel
            isFavorite = !isFavorite // Обновляем локальное состояние
        }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Добавить в избранное",
                tint = if (isFavorite) Color.Red else Color.Gray
            )
        }

        // Иконка "Добавить в друзья"
        IconButton(onClick = {
            onAddFriendClick() // Обновляем состояние в ViewModel
            isFriend = true // Обновляем локальное состояние
        }) {
            Icon(
                imageVector = Icons.Default.PersonAdd,
                contentDescription = "Добавить в друзья",
                tint = if (isFriend) Color.Blue else Color.Gray
            )
        }
    }
}