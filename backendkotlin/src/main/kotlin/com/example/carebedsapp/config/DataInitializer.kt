package com.example.carebedsapp.config

import com.example.carebedsapp.model.Bed
import com.example.carebedsapp.model.Hospital
import com.example.carebedsapp.model.Patient
import com.example.carebedsapp.repository.BedRepository
import com.example.carebedsapp.repository.HospitalRepository
import com.example.carebedsapp.repository.PatientRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val hospitalRepository: HospitalRepository,
    private val patientRepository: PatientRepository,
    private val bedRepository: BedRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Create hospitals
        val hospital1 = Hospital(
            name = "City Hospital",
            address = "123 Main St, Citytown",
            email = "contact@cityhospital.com",
            phoneNumber = "1234567890",
            password = "password1",
            role = "ROLE_HOSPITAL"
        )

//        val hospital2 = Hospital(
//            name = "County Hospital",
//            address = "456 County Rd, Countyville",
//            email = "contact@countyhospital.com",
//            phoneNumber = "0987654321",
//            password = "password2",
//            role = "ROLE_HOSPITAL"
//        )

        hospitalRepository.saveAll(listOf(hospital1))

        // Create beds
        val bed1 = Bed(name = "Bed 1", hospital = hospital1)
        //val bed2 = Bed(name = "Bed 2", hospital = hospital1)
//        val bed3 = Bed(name = "Bed 1", hospital = hospital2)
//        val bed4 = Bed(name = "Bed 2", hospital = hospital2)

        bedRepository.saveAll(listOf(bed1))

        // Create patients
        val patient1 = Patient(
            name = "John Doe",
            priority = "High",
            address = "789 Elm St, Citytown",

            registeredHospital = hospital1,
            registeredBed = bed1,
            role = "ROLE_PATIENT",
            email = "johndoe@example.com",
            phoneNumber = "1112223333",
            password = "password3"
        )

//        val patient2 = Patient(
//            name = "Jane Smith",
//            priority = "Low",
//            address = "321 Maple St, Citytown",
//            problem = "Routine checkup",
//            registeredHospital = hospital1,
//            registeredBed = bed2,
//            role = "ROLE_PATIENT",
//            email = "janesmith@example.com",
//            phoneNumber = "4445556666",
//            password = "password4"
//        )

//        val patient3 = Patient(
//            name = "Alice Johnson",
//            priority = "Medium",
//            address = "654 Pine St, Countyville",
//            problem = "Mild fever",
//            registeredHospital = hospital2,
//            registeredBed = bed3,
//            role = "ROLE_PATIENT",
//            email = "alicejohnson@example.com",
//            phoneNumber = "7778889999",
//            password = "password5"
//        )

        patientRepository.saveAll(listOf(patient1))

        // Linking patients to beds
        bed1.patient = patient1
        //bed2.patient = patient2
       // bed3.patient = patient3

        // Saving the updates
        bedRepository.saveAll(listOf(bed1))
    }
}