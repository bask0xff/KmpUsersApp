package com.example.shared.network

import com.example.kmpusersapp.Constants
import com.example.shared.model.User
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject

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
        val response: String = client.get("${Constants.BASE_URL}/users").body()
        println("Raw JSON Response: $response")

        val jsonArray = JSONArray(response)
        val users = mutableListOf<User>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val user = User(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                username = jsonObject.getString("username"),
                email = jsonObject.getString("email")
            )
            users.add(user)
        }

        return users
    }
}
