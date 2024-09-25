package com.example.carebedsfe.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carebedsfe.R
import com.example.carebedsfe.adapters.PatientAdapter.PatientViewHolder
import com.example.carebedsfe.model.Bed
import com.example.carebedsfe.model.Patient

class BedAdapter(private var bedList: List<Bed>, private val navController: NavController):
    RecyclerView.Adapter<BedAdapter.BedViewHolder>() {

    fun updatePatients(newBeds: Set<Bed>) {
        bedList = newBeds.toList()
        notifyDataSetChanged()
    }

    inner class BedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val bedName: TextView = itemView.findViewById(R.id.ib_bed_name)
        private val bedAvailablility: TextView = itemView.findViewById(R.id.ib_bed_availability)
        private val bedUsedBy: TextView = itemView.findViewById(R.id.ib_bed_usedby)

        fun bind(bed:Bed){
            bedName.text = bed.name
            bedAvailablility.text = bed.availability
            if(bed.patient!=null){
                bedUsedBy.text = bed.patient!!.name.toString()

            }
            else{
                bedUsedBy.text="null"
            }


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bed, parent, false)
        return BedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BedViewHolder, position: Int) {
        holder.bind(bedList[position])
    }

    override fun getItemCount(): Int = bedList.size
}