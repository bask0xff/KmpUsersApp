package com.example.shared.network

import com.example.shared.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun fetchUsers(): List<User> {
        return client.get("https://jsonplaceholder.typicode.com/users").body()
    }
}
