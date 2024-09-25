package com.example.carebedsapp.controller

import com.example.carebedsapp.model.*
import com.example.carebedsapp.service.HospitalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hospital")
class HospitalController(private val hospitalService: HospitalService) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        // Fetch the user and the token
        val (user, token) = hospitalService.login(loginRequest)

        // Create and return the login response
        val loginResponse = LoginResponse(token, user.id, user.role.toString())
        return ResponseEntity.ok(loginResponse)
    }

    @PostMapping("/register")
    fun registerHospital(@RequestBody hospital: Hospital): ResponseEntity<Hospital> {
        val createdHospital = hospitalService.registerHospital(hospital)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHospital)
    }

    @DeleteMapping("/{id}")
    fun deleteHospital(@PathVariable id: Int): ResponseEntity<String> {
        hospitalService.deleteHospital(id)
        return ResponseEntity.ok("Hospital deleted successfully")
    }

    @GetMapping("/{id}")
    fun getHospitalById(@PathVariable id: Int): ResponseEntity<Hospital> {
        val hospital = hospitalService.getHospitalById(id)
        return ResponseEntity.ok(hospital)
    }

    @GetMapping
    fun getAllHospitals(): ResponseEntity<List<Hospital>> {
        return ResponseEntity.ok(hospitalService.getAllHospitals())
    }

    @GetMapping("/{id}/beds")
    fun getHospitalBeds(@PathVariable id: Int): ResponseEntity<List<Bed>> {
        return ResponseEntity.ok(hospitalService.getBeds(id).toList())
    }

    @GetMapping("/{id}/patients")
    fun getHospitalPatients(@PathVariable id: Int): ResponseEntity<List<Patient>> {
        return ResponseEntity.ok(hospitalService.getPatients(id).toList())
    }

    @PostMapping("/{hospitalId}/addpatient/{patientId}")
    fun addPatient(@PathVariable hospitalId: Int, @PathVariable patientId: Int): ResponseEntity<String> {
        hospitalService.admitPatient(patientId, hospitalId)
        return ResponseEntity.ok("Patient admitted successfully")
    }

    @PostMapping("/{hospitalId}/addbed/{bedId}")
    fun addBed(@PathVariable hospitalId: Int, @PathVariable bedId: Int): ResponseEntity<String> {
        hospitalService.addBed(bedId, hospitalId)
        return ResponseEntity.ok("Bed added successfully")
    }
}
