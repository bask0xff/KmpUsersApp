package com.example.shared.network

import com.example.shared.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonElement

class ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
            //level = LogLevel.BODY
            level = LogLevel.ALL
        }
    }

    suspend fun fetchUsers(): List<User> {
        try {
            val response: String = client.get("https://jsonplaceholder.typicode.com/users").toString()
            println("Raw JSON Response: $response")

            val users: List<User> = Json.decodeFromString(response)
            println("Parsed Users: $users")

            return users
        } catch (e: Exception) {
            println("Error while fetching users: ${e.message}")
            throw e
        }
    }
}
