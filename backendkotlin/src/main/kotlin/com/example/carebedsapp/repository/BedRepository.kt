package com.example.carebedsapp.repository

import com.example.carebedsapp.model.Bed
import org.springframework.data.jpa.repository.JpaRepository

interface BedRepository:JpaRepository<Bed,Int> {
}