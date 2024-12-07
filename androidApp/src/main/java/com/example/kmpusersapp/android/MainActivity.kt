package com.example.kmpusersapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shared.model.User
import com.example.shared.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserListScreen(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(viewModel: UserViewModel) {
    val users = viewModel.users.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Users") })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (isLoading.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = Color.LightGray,
                        strokeWidth = 5.dp
                    )
                }
            } else {
                UserList(users.value)
            }
        }
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(users) { user ->
            UserItem(user)
            Divider()
        }
    }
}

@Composable
fun UserItem(user: User) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = user.name,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = user.email,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}