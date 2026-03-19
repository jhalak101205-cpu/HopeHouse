package com.example.hopehouse.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.VolunteerOpportunity
import com.google.android.material.button.MaterialButton

/**
 * RecyclerView adapter for volunteering opportunities.
 */
class VolunteerAdapter(
    private val onRegisterClick: (VolunteerOpportunity) -> Unit
) : RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder>() {

    private var items: List<VolunteerOpportunity> = emptyList()

    fun submitList(newItems: List<VolunteerOpportunity>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_volunteer_opportunity, parent, false)
        return VolunteerViewHolder(view, onRegisterClick)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VolunteerViewHolder(
        itemView: View,
        private val onRegisterClick: (VolunteerOpportunity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle: TextView = itemView.findViewById(R.id.tvVolunteerTitle)
        private val tvSkills: TextView = itemView.findViewById(R.id.tvVolunteerSkills)
        private val tvAvailability: TextView = itemView.findViewById(R.id.tvVolunteerAvailability)
        private val btnRegister: MaterialButton = itemView.findViewById(R.id.btnRegisterVolunteer)

        private var current: VolunteerOpportunity? = null

        init {
            btnRegister.setOnClickListener {
                current?.let(onRegisterClick)
            }
        }

        fun bind(op: VolunteerOpportunity) {
            current = op
            tvTitle.text = op.title
            tvSkills.text = "Skills: ${op.skills.joinToString(", ")}"
            tvAvailability.text = "Availability: ${op.availability}"

            val registered = HopeHouseRepository.isVolunteerRegistered(op.id)
            btnRegister.isEnabled = !registered
            btnRegister.text = if (registered) "Registered" else "Register"
        }
    }
}

