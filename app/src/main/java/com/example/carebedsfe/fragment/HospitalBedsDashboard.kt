package com.example.carebedsfe.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carebedsfe.R
import com.example.carebedsfe.adapters.BedAdapter
import com.example.carebedsfe.adapters.PatientAdapter
import com.example.carebedsfe.api.RetrofitInstance
import com.example.carebedsfe.model.Bed
import com.example.carebedsfe.model.Hospital
import kotlinx.coroutines.launch


class HospitalBedsDashboard : Fragment() {
    private lateinit var bedAdapter: BedAdapter
    private var hospitalId: Int = 0
    private lateinit var sharedPreferences: SharedPreferences
    private  var hospital:Hospital? = null
    private lateinit var beds:List<Bed>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hospital_beds_dashboard, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHospitalBeds)
        recyclerView.layoutManager= LinearLayoutManager(requireContext())
        bedAdapter = BedAdapter(getDummyEvents(),findNavController())
        recyclerView.adapter=bedAdapter
        sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        hospitalId = sharedPreferences.getLong("USER_ID", 0).toInt()

        getHospitalById(hospitalId)

        fetchBeds()


        return view
    }

    private fun getHospitalById(hospitalId: Int) {
        lifecycleScope.launch {
            try{
                val response =  RetrofitInstance.api.getHospitalById(hospitalId)

                if(response.isSuccessful){
                    hospital = response.body()!!
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getDummyEvents(): List<Bed> {


        return listOf(Bed(name = "Bed A11",
            availability = "Occupied",
            hospital = hospital))// Linked to hospital1))
    }

    private fun fetchBeds() {
        lifecycleScope.launch {
            try{
                val response = RetrofitInstance.api.getBedsByHospitalId(hospitalId)
                if(response.isSuccessful){
                    beds = response.body()!!

                }
                bedAdapter.updatePatients(beds.toSet())
            }
            catch (e: Exception) {
                // Handle the error (e.g., show a Toast or log it)
                e.printStackTrace() // Log the error for debugging
            }
        }
    }


}