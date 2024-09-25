package com.example.carebedsfe.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebedsfe.R
import com.example.carebedsfe.adapters.PatientAdapter
import com.example.carebedsfe.api.RetrofitInstance
import com.example.carebedsfe.model.Hospital
import com.example.carebedsfe.model.Patient
import kotlinx.coroutines.launch


class HospitalPatientDashboard : Fragment() {

    private lateinit var patientAdapter: PatientAdapter
    private var hospitalId: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var Patients:List<Patient>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hospital_patient_dashboard, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewPatients)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        patientAdapter = PatientAdapter(getDummyEvents(),findNavController())
        recyclerView.adapter=patientAdapter
        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        hospitalId = sharedPreferences.getLong("USER_ID", 0).toInt()



        fetchPatients()
        // Inflate the layout for this fragment
        return view
    }



    private fun fetchPatients() {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getPatientsByHospitalId(hospitalId)
                if (response.isSuccessful) {
                    Patients = response.body() ?: emptyList() // Handle null response gracefully
                    patientAdapter.updatePatients(Patients)
                } else {
                    showError("Failed to load patients: ${response.message()}")
                    Log.e("LAZYY","${response.message()}")
                }
            } catch (e: Exception) {
                showError("Error fetching patients: ${e.localizedMessage}")
                Log.e("FetchPatients", "Error fetching patients: ${e.message}", e) // Log error for debugging
            }
        }
    }

    private fun getDummyEvents(): List<Patient> {
        return listOf(Patient(
            id=1,
            name = "Bhumit Doe",
            priority = "High",
            address = "789 Elm St, Citytown",
            registeredHospital = null,  // Reference to hospital1
            registeredBed = null,            // Reference to bed1
            role = "ROLE_PATIENT",
            email = "johndoe@example.com",
            phoneNumber = "1112223333",
            password = "password3",
            age = 31,
            location = "789 Elm St, Citytown",
            gender = "Male",
            bloodType = "O+",
            medicalCondition = "Hypertension",
            admissionType = "Emergency",
            medications = "Amlodipine",
            testResults = "Blood Pressure: 150/90",
            bedNum = null,
            hospitalNum = null
        ))


    }
    private fun showError(message: String) {
        // Show error message to user, e.g., using Toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }



}