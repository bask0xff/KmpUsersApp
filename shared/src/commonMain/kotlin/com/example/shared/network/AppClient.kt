package com.example.shared.network

import com.example.kmpusersapp.Constants
import com.example.shared.model.User
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

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
            val response: String = client.get(Constants.BASE_URL).toString()
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
