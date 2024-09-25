package com.example.carebedsfe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carebedsfe.R
import com.example.carebedsfe.fragment.PatientDetailsDialogFragment
import com.example.carebedsfe.model.Patient

class PatientAdapter(private var patientList: List<Patient>,private val navController: NavController):
    RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    fun updatePatients(newPatients: List<Patient>) {
        patientList = newPatients
        notifyDataSetChanged()
    }

    inner class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patient_id: TextView = itemView.findViewById(R.id.ip_patient_id)

        val patient_name: TextView = itemView.findViewById(R.id.ip_patient_name)

        val patient_room: TextView = itemView.findViewById(R.id.ip_patient_room)
//
//        val patientName: TextView = itemView.findViewById(R.id.patientName)
//        val patientPriority  :TextView =  itemView.findViewById(R.id.patientPriority)
//        val patientAddress :TextView =  itemView.findViewById(R.id.patientAddress)
//        val patientEmail :TextView =  itemView.findViewById(R.id.patientEmail)
//        val patientPhone :TextView =  itemView.findViewById(R.id.patientPhone)
//        val patientLocation :TextView =  itemView.findViewById(R.id.patientLocation)
//        val patientGender :TextView =  itemView.findViewById(R.id.patientGender)
//        val patientBloodType :TextView =  itemView.findViewById(R.id.patientBloodType)
//        val patientCondition :TextView =  itemView.findViewById(R.id.patientCondition)
//        val patientAdmissionType :TextView =  itemView.findViewById(R.id.patientAdmissionType)
//        val patientMedications :TextView =  itemView.findViewById(R.id.patientMedications)
//        val patientTestResults :TextView =  itemView.findViewById(R.id.patientTestResults)
//        val patientAge :TextView =  itemView.findViewById(R.id.patientAge)

        init {
            itemView.setOnClickListener {
                // Get the patient at the clicked position
                val patient = patientList[adapterPosition]
                // Create a new instance of the dialog fragment
                val dialog = PatientDetailsDialogFragment.newInstance(patient)
                // Show the dialog
                dialog.show((itemView.context as AppCompatActivity).supportFragmentManager, "PatientDetailsDialog")
            }
        }
        fun bind(patient: Patient) {
            patient_id.text = patient.id.toString()
            patient_name.text= patient.name

            patient_room.text = ""
//            patientAge.text = patient.age.toString()
//            patientName.text = patient.name
//            patientAddress.text = patient.address
//            patientEmail.text = patient.email
//            patientPhone.text = patient.phoneNumber
//            patientLocation.text = patient.location
//            patientGender.text = patient.gender
//            patientBloodType.text = patient.bloodType
//            patientCondition.text = patient.medicalCondition
//            patientAdmissionType.text = patient.admissionType
//            patientMedications.text = patient.medication
//            patientTestResults.text = patient.testResults
//            patientPriority.text = patient.priority


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        holder.bind(patientList[position])
    }

    override fun getItemCount(): Int = patientList.size

}