package com.example.carebedsapp.service

import com.example.carebedsapp.exception.ResourceNotFoundException
import com.example.carebedsapp.model.Bed
import com.example.carebedsapp.model.Hospital
import com.example.carebedsapp.model.LoginRequest
import com.example.carebedsapp.model.Patient
import com.example.carebedsapp.repository.BedRepository
import com.example.carebedsapp.repository.HospitalRepository
import com.example.carebedsapp.repository.PatientRepository
import org.springframework.stereotype.Service

@Service
class HospitalService(
    private val patientRepository: PatientRepository,
    private val hospitalRepository: HospitalRepository,
    private val bedRepository: BedRepository
) {
    fun login(loginRequest: LoginRequest): Pair<Hospital, String> {
        val hospital = hospitalRepository.findByEmail(loginRequest.email)
            ?: throw ResourceNotFoundException("Hospital not found")

        // Validate the password
        if (!validatePassword(loginRequest.password, hospital.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        // Generate a token
        val token = generateToken(hospital)

        return Pair(hospital, token)
    }

    private fun validatePassword(inputPassword: String, storedPassword: String): Boolean {
        // Implement your password validation logic (e.g., hash comparison)
        return inputPassword == storedPassword // Replace with proper hashing
    }

    private fun generateToken(hospital: Hospital): String {
        // Implement JWT or token generation logic here
        return "generatedToken"
    }

    fun registerHospital(hospital: Hospital): Hospital {
        return hospitalRepository.save(hospital)
    }

    fun deleteHospital(id: Int) {
        hospitalRepository.deleteById(id)
    }

    fun getHospitalById(id: Int): Hospital {
        return hospitalRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Hospital not found") }
    }

    fun admitPatient(patientId: Int, hospitalId: Int) {
        val hospital = getHospitalById(hospitalId)
        val patient = patientRepository.findById(patientId)
            .orElseThrow { ResourceNotFoundException("Patient not found") }

        if (hospital.admittedPatients.contains(patient)) {
            throw IllegalArgumentException("Patient already admitted in the hospital")
        }

        hospital.admittedPatients.add(patient)
        patient.registeredHospital = hospital
        hospitalRepository.save(hospital)
        patientRepository.save(patient)
    }
    fun getAllHospitals():List<Hospital>{
        return hospitalRepository.findAll()
    }


    fun getBeds(id: Int): MutableSet<Bed> {
        val hospital = getHospitalById(id)
        return hospital.beds
    }

    fun getPatients(id: Int): MutableSet<Patient> {
        val hospital = getHospitalById(id)
        return hospital.admittedPatients
    }

    fun addBed(bedId: Int, hospitalId: Int) {
        val bed = bedRepository.findById(bedId)
            .orElseThrow { ResourceNotFoundException("Bed not found!") }
        val hospital = getHospitalById(hospitalId)

        hospital.beds.add(bed)
        bed.hospital = hospital
        bedRepository.save(bed)
        hospitalRepository.save(hospital)
    }

    fun removePatient(hospitalId:Int, patientId:Int){
        val hospital = hospitalRepository.findById(hospitalId).orElseThrow{
            ResourceNotFoundException("Hospital not found! ")
        }
        val patient = patientRepository.findById(patientId).orElseThrow{
            ResourceNotFoundException("Patient not found! ")}
        if(hospital.admittedPatients.contains(patient)){
            hospital.admittedPatients.remove(patient)

            patient.registeredHospital = null

            hospitalRepository.save(hospital)
            patientRepository.save(patient)

        }
    }
}
