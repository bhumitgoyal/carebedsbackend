package com.example.carebedsfe.model

data class LoginResponse(
    val token: String,
    val userId: Long,
    val role: String
)