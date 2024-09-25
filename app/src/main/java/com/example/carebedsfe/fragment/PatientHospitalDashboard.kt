package com.example.carebedsfe.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.carebedsfe.R
import com.example.carebedsfe.api.RetrofitInstance
import com.example.carebedsfe.model.Bed
import com.example.carebedsfe.model.Hospital
import com.example.carebedsfe.model.Patient
import kotlinx.coroutines.launch

class PatientHospitalDashboard : Fragment() {
    private var patientId: Int = 0
    private lateinit var CV:CardView
    private lateinit var sharedPreferences: SharedPreferences
    private var patient: Patient? = null // Changed to nullable
    private lateinit var hospitalName: TextView
    private lateinit var hospitalLocation: TextView
    private lateinit var hospitalEmail: TextView
    private lateinit var hospitalPhone: TextView
    private lateinit var allottedBedName: TextView
    private lateinit var callHospitalButton: Button
    private lateinit var directionsButton: Button
    private var hospital: Hospital? = null
    private var bed: Bed? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_patient_hospital_dashboard, container, false)
        hospitalName = view.findViewById(R.id.hospitalName)
        CV = view.findViewById(R.id.cv1)
        hospitalLocation = view.findViewById(R.id.hospitalLocation)
        hospitalEmail = view.findViewById(R.id.hospitalEmail)
        hospitalPhone = view.findViewById(R.id.hospitalPhone)
        callHospitalButton = view.findViewById(R.id.callHospitalButton)
        allottedBedName = view.findViewById(R.id.allottedBedName)
        directionsButton = view.findViewById(R.id.directionsButton)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        patientId = sharedPreferences.getLong("USER_ID", 0).toInt()

        getPatient(patientId)

        // Button click handling
        callHospitalButton.setOnClickListener {
            // Implement call functionality
            // For example, initiate a phone call using an Intent
        }

        directionsButton.setOnClickListener {
            // Implement directions functionality
            // For example, open a map with directions to the hospital
        }

        return view
    }

    private fun getPatient(patientId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getPatientById(patientId)

                if (response.isSuccessful) {
                    patient = response.body()
                    val response2 = patient?.hospitalNum?.let {
                        RetrofitInstance.api.getHospitalById(
                            it
                        )
                    }
                    val response3 = patient?.bedNum?.let { RetrofitInstance.api.getBedById(it) }

                    if(response3!=null){
                        if(response3.isSuccessful){
                            bed = response3.body()
                        }
                    }
                    patient?.registeredBed =bed


                    if (response2 != null) {
                        if(response2.isSuccessful){
                            hospital = response2.body()
                        }
                        patient?.registeredHospital = hospital

                            updateUI()
                    } // Update UI after data is loaded
                } else {
                    // Handle error response
                    showError("Error fetching patient data: ${response.message()}")
                }
            } catch (e: Exception) {
                // Handle network or other errors
                showError("Network error: ${e.localizedMessage}")
            }
        }
    }

    private fun updateUI() {
        patient?.let { patient ->
            CV.visibility =View.GONE

            patient.registeredHospital?.let { hospital ->
                hospitalName.text = hospital.name ?: "N/A"
                hospitalLocation.text = hospital.location ?: "N/A"
                hospitalEmail.text = hospital.email ?: "N/A"
                hospitalPhone.text = hospital.phoneNumber ?: "N/A"
            } ?: run {
                CV.visibility =View.VISIBLE
                showError("Hospital information not available")
            }
            allottedBedName.text = patient.registeredBed?.name ?: "N/A"
        }
    }

    private fun showError(message: String) {
        // Show error message to user, e.g., using Toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
