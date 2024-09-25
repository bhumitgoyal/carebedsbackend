package com.example.carebedsapp.repository

import com.example.carebedsapp.model.Hospital
import com.example.carebedsapp.model.Patient
import org.springframework.data.jpa.repository.JpaRepository

interface HospitalRepository :JpaRepository<Hospital,Int>{
    fun findByEmail(email:String): Hospital?
}