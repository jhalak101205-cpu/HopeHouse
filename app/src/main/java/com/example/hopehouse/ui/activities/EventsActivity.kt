package com.example.hopehouse.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.Event
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.HopeHouseSession
import com.example.hopehouse.ui.adapters.EventAdapter
import com.google.android.material.appbar.MaterialToolbar

/**
 * Events screen:
 * - Shows event cards with date/title/NGO + register button
 * - Optional filter by NGO when navigated from an NGO detail
 */
class EventsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NGO_ID = "extra_ngo_id"
    }

    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarEvents)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        val recycler: RecyclerView = findViewById(R.id.recyclerEvents)
        recycler.layoutManager = LinearLayoutManager(this)

        val ngoId = intent.getStringExtra(EXTRA_NGO_ID)
        val events: List<Event> = if (ngoId.isNullOrBlank()) {
            HopeHouseRepository.events
        } else {
            HopeHouseRepository.events.filter { it.ngoId == ngoId }
        }

        adapter = EventAdapter { event ->
            if (!HopeHouseRepository.isEventRegistered(event.id)) {
                HopeHouseRepository.registerEvent(event.id)
                HopeHouseSession.recordEventRegistration()
                Toast.makeText(this, "Registered for: ${event.title}", Toast.LENGTH_SHORT).show()
                adapter.submitList(events) // refresh states
            } else {
                Toast.makeText(this, "Already registered!", Toast.LENGTH_SHORT).show()
            }
        }
        recycler.adapter = adapter
        adapter.submitList(events)
    }
}

