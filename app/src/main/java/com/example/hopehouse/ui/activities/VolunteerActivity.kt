package com.example.hopehouse.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.HopeHouseSession
import com.example.hopehouse.data.VolunteerOpportunity
import com.example.hopehouse.ui.adapters.VolunteerAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

/**
 * Volunteer screen:
 * - Form (skills, availability) (dummy preferences, no backend)
 * - RecyclerView list of opportunities with a Register button
 */
class VolunteerActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NGO_ID = "extra_ngo_id"
    }

    private lateinit var adapter: VolunteerAdapter
    private var opportunities: List<VolunteerOpportunity> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarVolunteer)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        val recycler: RecyclerView = findViewById(R.id.recyclerOpportunities)
        recycler.layoutManager = LinearLayoutManager(this)

        val ngoId = intent.getStringExtra(EXTRA_NGO_ID)
        opportunities = if (ngoId.isNullOrBlank()) {
            HopeHouseRepository.volunteerOpportunities
        } else {
            HopeHouseRepository.volunteerOpportunities.filter { it.ngoId == ngoId }
        }

        adapter = VolunteerAdapter { opportunity ->
            if (!HopeHouseRepository.isVolunteerRegistered(opportunity.id)) {
                HopeHouseRepository.registerVolunteer(opportunity.id)
                HopeHouseSession.recordVolunteer()
                Toast.makeText(this, "Registered for volunteering: ${opportunity.title}", Toast.LENGTH_SHORT).show()
                adapter.submitList(opportunities) // refresh button states
            } else {
                Toast.makeText(this, "Already registered!", Toast.LENGTH_SHORT).show()
            }
        }
        recycler.adapter = adapter
        adapter.submitList(opportunities)

        val etSkills: TextInputEditText = findViewById(R.id.etSkills)
        val etAvailability: TextInputEditText = findViewById(R.id.etAvailability)
        val btnSave: MaterialButton = findViewById(R.id.btnUpdateVolunteerProfile)
        btnSave.setOnClickListener {
            val skills = etSkills.text?.toString()?.trim().orEmpty()
            val availability = etAvailability.text?.toString()?.trim().orEmpty()
            if (skills.isBlank() || availability.isBlank()) {
                Toast.makeText(this, "Enter skills and availability.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Demo-only: keep input local. In a real app we would persist it.
            Toast.makeText(this, "Preferences saved (demo).", Toast.LENGTH_SHORT).show()
        }
    }
}

