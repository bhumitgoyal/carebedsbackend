package com.example.carebedsfe.model

import java.io.Serializable

data class Hospital(
                    val id: Int = 0,
                    val name: String,
                    val address: String,
                    val email: String,
                    val phoneNumber: String,
                    val location:String,
                    val password: String,
                    val role: String,
                    var admittedPatients: MutableSet<Patient> = mutableSetOf(),
                    var beds: MutableSet<Bed> = mutableSetOf()
): Serializable
