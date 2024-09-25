package com.example.carebedsfe.model

import java.io.Serializable

data class Bed(
                   val id: Int = 0,
                   var name: String,
                   var hospital: Hospital? =null,
                   var patient: Patient? = null,
                   var availability: String? = "true"

): Serializable
