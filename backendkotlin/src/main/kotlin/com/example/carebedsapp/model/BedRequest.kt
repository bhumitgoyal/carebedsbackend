package com.example.carebedsapp.model

data class BedRequest(
    val name: String,
    val availability: String?,
    val hospitalId: Int
)