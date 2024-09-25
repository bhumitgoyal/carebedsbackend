package com.example.carebedsapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.springframework.boot.availability.ApplicationAvailability
import java.util.*
@Entity
data class Bed(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    var name: String,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    var hospital: Hospital? =null,

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "patient_id")
    var patient: Patient? = null,

    var availability: String? = "true"




) {
    val patientNum: Int? get() = patient?.id
    val hospitalNum: Int? get() = hospital?.id
    // You might want to avoid deriving values directly from the associated objects.


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Bed) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
