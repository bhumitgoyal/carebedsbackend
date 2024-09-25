package com.example.carebedsapp.service

import com.example.carebedsapp.exception.ResourceNotFoundException
import com.example.carebedsapp.model.Bed
import com.example.carebedsapp.model.Hospital
import com.example.carebedsapp.model.LoginRequest
import com.example.carebedsapp.model.Patient
import com.example.carebedsapp.repository.PatientRepository
import org.springframework.stereotype.Service

@Service // Annotate this class as a service for Spring's component scanning
class PatientService(private val patientRepository: PatientRepository) {

    fun login(loginRequest: LoginRequest): Pair<Patient, String> {
        val user = patientRepository.findByEmail(loginRequest.email)
            ?: throw ResourceNotFoundException("User not found")

        // Validate the password
        if (!validatePassword(loginRequest.password, user.password)) {
            throw IllegalArgumentException("Invalid password") // Use a more specific exception
        }

        // Generate a token
        val token = generateToken(user)

        return Pair(user, token)
    }

    fun registerPatient(patient: Patient): Patient {
        return patientRepository.save(patient)
    }

    fun deletePatient(id: Int) {
        patientRepository.deleteById(id)
    }

    fun getPatientById(id: Int): Patient {
        return patientRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("User not found") }
    }

    fun getAllPatients(): List<Patient> {
        return patientRepository.findAll()
    }

    fun getPatientAllottedBed(id: Int): Bed? {
        val user = getPatientById(id)
        return user.registeredBed
    }

    fun getPatientAllottedHospital(id: Int): Hospital? {
        val user = getPatientById(id)
        return user.registeredHospital
    }

    private fun generateToken(user: Patient): String {
        // Implement JWT or token generation logic here
        return "generatedToken"
    }

    private fun validatePassword(inputPassword: String, storedPassword: String): Boolean {
        // Implement your password validation logic (e.g., hash comparison)
        return inputPassword == storedPassword // Replace this with proper hashing
    }
}
