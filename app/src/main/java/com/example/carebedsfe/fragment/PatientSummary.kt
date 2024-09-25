package com.example.carebedsfe.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.carebedsfe.R
import com.example.carebedsfe.api.ApiService
import com.example.carebedsfe.api.RetrofitInstance
import com.example.carebedsfe.model.Bed
import com.example.carebedsfe.model.Hospital
import com.example.carebedsfe.model.Patient
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PatientSummary : Fragment() {
    private lateinit var patientName: TextView
    private lateinit var HospitalName: TextView
    private lateinit var bedAlloted: TextView
    private lateinit var problem: TextView
    private lateinit var priority: TextView
    private lateinit var qrCodeImageView: ImageView
    private lateinit var apiService: ApiService
    private var patientId: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    private var patient: Patient? = null // Changed to nullable
    private var hospital: Hospital? = null
    private var bed: Bed? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_patient_summary, container, false)
        patientName = view.findViewById(R.id.summary_patient_name)
        HospitalName = view.findViewById(R.id.summary_hospital_name)
        bedAlloted = view.findViewById(R.id.summary_bed_allotted)
        problem = view.findViewById(R.id.summary_problem)
        priority = view.findViewById(R.id.summary_priority)
        qrCodeImageView = view.findViewById(R.id.qr_code_image)

        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        patientId = sharedPreferences.getLong("USER_ID", 0).toInt()

        getPatient(patientId)

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
                }  else {
                    // Handle error response
                    // You can show a message to the user or log the error
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
    }

    private fun updateUI() {
        patient?.let {
            patientName.text = it.name
            HospitalName.text = it.registeredHospital?.name ?: "Not Registered"
            bedAlloted.text = it.registeredBed?.name ?: "Not Allotted"
            problem.text = it.medicalCondition ?: "Unknown Condition"
            priority.text = it.priority ?: "No Priority"

            // The URL of the QR code image
            val qrCodeImageUrl = "https://i.postimg.cc/mDRLjt63/user-6-event-6.png"

            // Using Glide to load the image
            Glide.with(requireContext())
                .load(qrCodeImageUrl)  // Add a placeholder image
                .error(R.drawable.logo)  // Add an error image if loading fails
                .priority(Priority.HIGH)  // Set high priority for loading
                .into(qrCodeImageView)
        }
    }

}
