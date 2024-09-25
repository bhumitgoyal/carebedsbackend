package com.example.carebedsapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*
@Entity
data class Hospital(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    val name: String,
    val address: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val role: String,

    @JsonIgnore
    @OneToMany(mappedBy = "registeredHospital", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var admittedPatients: MutableSet<Patient> = mutableSetOf(),

    @OneToMany(mappedBy = "hospital", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var beds: MutableSet<Bed> = mutableSetOf()
) {
    val capacity: Int? get() = beds.size

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Hospital) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
