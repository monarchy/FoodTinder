package com.example.foodtinder.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState


    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success("Регистрация успешна")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Ошибка регистрации")
                }
            }
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Success("Вход выполнен")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Ошибка входа")
                }
            }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Initial
    }
}

sealed class AuthState {
    object Initial : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}
