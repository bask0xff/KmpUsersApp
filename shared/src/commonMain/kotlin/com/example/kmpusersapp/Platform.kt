package com.example.kmpusersapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform