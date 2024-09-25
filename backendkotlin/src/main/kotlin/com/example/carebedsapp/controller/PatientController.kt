package com.example.carebedsapp.controller

import com.example.carebedsapp.model.*
import com.example.carebedsapp.service.HospitalService
import com.example.carebedsapp.service.PatientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/patients")
class PatientController(private val patientService: PatientService,private val hospitalService: HospitalService) {

    @PostMapping("/register")
    fun registerPatient(@RequestBody patientRequest: PatientRequest): ResponseEntity<Patient> {
       // val hospital = hospitalService.getHospitalById(patientRequest.registeredHospital.id)
         //   ?: return ResponseEntity.notFound().build()
        val newPatient = Patient(
            name = patientRequest.name,
            priority = "null",
            address = patientRequest.address,
            role = patientRequest.role,
            email = patientRequest.email,
            phoneNumber = patientRequest.phoneNumber,
            password = patientRequest.password,
            location = patientRequest.location,
            gender = patientRequest.gender,
            bloodType = patientRequest.bloodType,
            medicalCondition = patientRequest.medicalCondition,
            admissionType = patientRequest.admissionType,
            medications = patientRequest.medications,
            testResults = patientRequest.testResults,
        )

        val savedPatient = patientService.registerPatient(newPatient)
        return ResponseEntity.ok(savedPatient)
    }

    @GetMapping
    fun getAllPatients(): ResponseEntity<List<Patient>> {
        return ResponseEntity.ok(patientService.getAllPatients())
    }

    @DeleteMapping("/{id}")
    fun deletePatient(@PathVariable id: Int): ResponseEntity<Unit> {
        patientService.deletePatient(id)
        return ResponseEntity.noContent().build() // Return 204 No Content
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val (user, token) = patientService.login(loginRequest)
        val loginResponse = LoginResponse(token, user.id, user.role.toString())
        return ResponseEntity.ok(loginResponse)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<Patient> {
        val user = patientService.getPatientById(id)
        return ResponseEntity.ok(user) // Assuming ResourceNotFoundException is handled globally
    }

    @GetMapping("/{id}/bed")
    fun getUserRegisteredBed(@PathVariable id: Int): ResponseEntity<Bed?> {
        val bed = patientService.getPatientAllottedBed(id)
        return if (bed != null) ResponseEntity.ok(bed) else ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/hospital")
    fun getUserRegisteredHospital(@PathVariable id: Int): ResponseEntity<Hospital?> {
        val hospital = patientService.getPatientAllottedHospital(id)
        return if (hospital != null) ResponseEntity.ok(hospital) else ResponseEntity.notFound().build()
    }
}
