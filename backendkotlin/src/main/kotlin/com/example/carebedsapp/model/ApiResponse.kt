package com.example.carebedsapp.model

data class ApiResponse<T>(
    val status: String,
    val data: T? = null,
    val error: String? = null
)
