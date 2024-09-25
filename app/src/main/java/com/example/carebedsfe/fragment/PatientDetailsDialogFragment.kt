package com.example.carebedsfe.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.carebedsfe.R
import com.example.carebedsfe.model.Patient

class PatientDetailsDialogFragment : DialogFragment() {

    private lateinit var patient: Patient // Your Patient data class

    // Factory method to create a new instance with patient data
    companion object {
        fun newInstance(patient: Patient): PatientDetailsDialogFragment {
            val fragment = PatientDetailsDialogFragment()
            val args = Bundle()
            args.putSerializable("patient", patient) // Pass the patient object
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the patient object from the arguments
        patient = arguments?.getSerializable("patient") as Patient
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate your dialog layout here
        val view = inflater.inflate(R.layout.dialog_patient_details, container, false)

        // Set patient details to TextViews
        view.findViewById<TextView>(R.id.patientName).text = patient.name
        view.findViewById<TextView>(R.id.patientPriority).text = patient.priority
        view.findViewById<TextView>(R.id.patientAddress).text = patient.address
        view.findViewById<TextView>(R.id.patientEmail).text = patient.email
        view.findViewById<TextView>(R.id.patientPhone).text = patient.phoneNumber
        view.findViewById<TextView>(R.id.patientLocation).text = patient.location
        view.findViewById<TextView>(R.id.patientGender).text = patient.gender
        view.findViewById<TextView>(R.id.patientBloodType).text = patient.bloodType
        view.findViewById<TextView>(R.id.patientCondition).text = patient.medicalCondition
        view.findViewById<TextView>(R.id.patientAdmissionType).text = patient.admissionType
        view.findViewById<TextView>(R.id.patientMedications).text = patient.medicalCondition
        view.findViewById<TextView>(R.id.patientTestResults).text = patient.testResults
        view.findViewById<TextView>(R.id.patientAge).text = patient.age.toString()




        // Call button action
        view.findViewById<Button>(R.id.callPatientButton).setOnClickListener {
            // Handle call action
            dismiss() // Close the dialog
        }

        return view
    }
}
