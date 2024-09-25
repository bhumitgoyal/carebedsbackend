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
import androidx.lifecycle.lifecycleScope
import com.example.carebedsfe.R
import com.example.carebedsfe.api.RetrofitInstance
import com.example.carebedsfe.model.Patient
import kotlinx.coroutines.launch


class PatientDashboard : Fragment() {
    private lateinit var patientName: TextView
    private lateinit var patientPriority: TextView
    private lateinit var patientEmail: TextView
    private lateinit var patientPhone: TextView
    private lateinit var patientLocation: TextView
    private lateinit var patientGender: TextView
    private lateinit var patientBloodType: TextView
    private lateinit var patientCondition: TextView
    private lateinit var viewBedButton: Button
    private var patientId: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    private var patient: Patient? = null // Changed to nullable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_patient_dashboard, container, false)
        patientName = view.findViewById(R.id.patientName)
        patientPriority = view.findViewById(R.id.patientPriority)
        patientEmail = view.findViewById(R.id.patientEmail)
        patientPhone = view.findViewById(R.id.patientPhone)
        patientLocation = view.findViewById(R.id.patientLocation)
        patientGender = view.findViewById(R.id.patientGender)
        patientBloodType = view.findViewById(R.id.patientBloodType)
        patientCondition = view.findViewById(R.id.patientCondition)
        viewBedButton = view.findViewById(R.id.viewBedButton)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        patientId = sharedPreferences.getLong("USER_ID", 0).toInt()

        getPatient(patientId)

        // Handle button click
        viewBedButton.setOnClickListener {
            // Implement what happens when the button is clicked
            // For example, navigate to another fragment or activity
        }

        return view
    }

    private fun getPatient(patientId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getPatientById(patientId)
                if (response.isSuccessful) {
                    patient = response.body()
                    updateUI() // Update UI after data is loaded
                } else {
                    // Handle error response
                    showError("Error fetching patient data")
                }
            } catch (e: Exception) {
                // Handle network or other errors
                showError("Network error: ${e.message}")
            }
        }
    }

    private fun updateUI() {
        patient?.let {
            patientName.text = it.name ?: "N/A"
            patientPriority.text = it.priority ?: "N/A"
            patientEmail.text = it.email ?: "N/A"
            patientPhone.text = it.phoneNumber ?: "N/A"
            patientLocation.text = it.location ?: "N/A"
            patientGender.text = it.gender ?: "N/A"
            patientBloodType.text = it.bloodType ?: "N/A"
            patientCondition.text = it.medicalCondition ?: "N/A"
        }
    }

    private fun showError(message: String) {
        // Show error message to user, e.g., using Toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
