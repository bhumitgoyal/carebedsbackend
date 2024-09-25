package com.example.carebedsapp.service

import com.example.carebedsapp.exception.ResourceNotFoundException
import com.example.carebedsapp.model.Bed
import com.example.carebedsapp.model.Patient
import com.example.carebedsapp.repository.BedRepository
import com.example.carebedsapp.repository.HospitalRepository
import com.example.carebedsapp.repository.PatientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BedService(
    private val bedRepository: BedRepository,
    private val patientRepository: PatientRepository,
    private val hospitalRepository: HospitalRepository,
    private val hospitalService: HospitalService
) {

    fun addBed(bed: Bed): Bed {
        return bedRepository.save(bed)
    }

    fun deleteBed(id: Int) {
        bedRepository.deleteById(id)
    }

    @Transactional
    fun admitPatient(patientId: Int, bedId: Int) {

        val bed = bedRepository.findById(bedId).orElseThrow {
            ResourceNotFoundException("Bed not found")
        }
        val patient = patientRepository.findById(patientId).orElseThrow {
            ResourceNotFoundException("Patient not found")
        }
        val hospital = patient.registeredHospital?.let {
            hospitalRepository.findById(it.id).orElseThrow {
                ResourceNotFoundException("Hospital not found")
            }
        }
        if(hospital!=null){
            hospitalService.admitPatient(patientId,hospital.id)
        }

        bed.availability="false"
        bed.patient = patient
        patient.registeredBed = bed

        bedRepository.save(bed)
        patientRepository.save(patient)
    }

    @Transactional
    fun freePatient(bedId: Int) {

        val bed = bedRepository.findById(bedId).orElseThrow{ResourceNotFoundException("Bed not fiund")}
        val patient = bed.patient



        val hospital = patient?.registeredHospital
        if(hospital!=null){
            if (patient != null) {
                hospitalService.removePatient(hospital.id,patient.id)
            }
        }

        if (bed != null) {
            bed.patient = null
            bed.availability="true"
            bedRepository.save(bed) // Save bed after detaching patient
        }
        if (patient != null) {
            patient.registeredHospital = null
        }

        if (patient != null) {
            patient.registeredBed = null
        }
        if (patient != null) {
            patientRepository.save(patient)
        }
    }

    fun getAllBeds(): List<Bed> {
        return bedRepository.findAll()
    }

    fun getBedById(bedId: Int): Bed {
        return bedRepository.findById(bedId).orElseThrow {
            ResourceNotFoundException("Bed not found")
        }
    }

    fun getPatient(bedId: Int): Patient? {
        val bed = getBedById(bedId)
        return bed.patient
    }
}
