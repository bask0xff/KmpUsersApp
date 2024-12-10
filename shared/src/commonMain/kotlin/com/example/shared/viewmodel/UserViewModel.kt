package com.example.shared.viewmodel

import com.example.shared.model.User
import com.example.shared.model.UserPost
import com.example.shared.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class UserViewModel {
    private val apiClient = ApiClient()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    private val _posts = MutableStateFlow<List<UserPost>>(emptyList())
    val userPosts: StateFlow<List<UserPost>> = _posts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        CoroutineScope(Dispatchers.Main).launch {
            _isLoading.value = true
            try {
                _users.value = apiClient.fetchUsers()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getPosts(id: Int): List<UserPost> {
        CoroutineScope(Dispatchers.Main).launch {
            _isLoading.value = true
            try {
                _posts.value = apiClient.getPosts(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
