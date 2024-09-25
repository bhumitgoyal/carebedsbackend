package com.example.carebedsapp.repository

import com.example.carebedsapp.model.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface PatientRepository:JpaRepository<Patient,Int> {
    fun findByEmail(email:String):Patient?
}