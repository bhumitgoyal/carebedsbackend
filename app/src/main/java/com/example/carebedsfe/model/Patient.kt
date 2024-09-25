package com.example.carebedsfe.model

import java.io.Serializable

data class Patient(
                    val id: Int = 0,
                    val name: String,
                    val priority: String,
                    val address: String,
                    var registeredHospital: Hospital? = null,
                    var registeredBed: Bed? = null,
                    var role: String,
                    val email: String,
                    val phoneNumber: String,
                    val password: String,
                    val location: String? = null,
                    val gender: String? = null,
                    val bloodType: String? = null,
                    val medicalCondition: String? = null,
                    val admissionType: String? = null,
                    val medications:String?= null,
                    val testResults: String? = null,
                    val age: Int,
                    val bedNum: Int?,
                    val hospitalNum: Int?

): Serializable
