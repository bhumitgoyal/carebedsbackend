package com.example.carebedsapp.model

data class PatientRequest(
    val name: String,
    val address: String,
    val role: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val location: String? = null,
    val gender: String? = null,
    val bloodType: String? = null,
    val medicalCondition: String? = null,
    val admissionType: String? = null,
    val medications: String? = null,
    val testResults: String? = null
)
