package com.example.carebedsapp.model
data class LoginResponse(
    val token: String,
    val userId: Int,
    val role: String
)
