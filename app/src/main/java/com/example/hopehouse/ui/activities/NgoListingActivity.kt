package com.example.hopehouse.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopehouse.R
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.Ngo
import com.example.hopehouse.ui.adapters.NgoAdapter
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.RecyclerView

/**
 * NGO Listing screen:
 * - RecyclerView with NGO image/name/category/location
 * - Filter buttons to narrow NGOs by category
 * - Navigates to NgoDetailActivity via intents
 */
class NgoListingActivity : AppCompatActivity() {

    private lateinit var adapter: NgoAdapter
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo_listing)

        val recycler: RecyclerView = findViewById(R.id.recyclerNgos)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = NgoAdapter { ngo ->
            val intent = Intent(this, NgoDetailActivity::class.java)
            intent.putExtra(NgoDetailActivity.EXTRA_NGO_ID, ngo.id)
            startActivity(intent)
        }
        recycler.adapter = adapter

        // Filter buttons
        setupFilters()
        applyFilter()
    }

    private fun setupFilters() {
        val btnAll: MaterialButton = findViewById(R.id.btnFilterAll)
        val btnEnv: MaterialButton = findViewById(R.id.btnFilterEnvironment)
        val btnFood: MaterialButton = findViewById(R.id.btnFilterFood)
        val btnEdu: MaterialButton = findViewById(R.id.btnFilterEducation)
        val btnHealth: MaterialButton = findViewById(R.id.btnFilterHealth)
        val btnShelter: MaterialButton = findViewById(R.id.btnFilterShelter)
        val btnDisaster: MaterialButton = findViewById(R.id.btnFilterDisaster)

        btnAll.setOnClickListener {
            selectedCategory = null
            applyFilter()
        }
        btnEnv.setOnClickListener {
            selectedCategory = "Environment"
            applyFilter()
        }
        btnFood.setOnClickListener {
            selectedCategory = "Food"
            applyFilter()
        }
        btnEdu.setOnClickListener {
            selectedCategory = "Education"
            applyFilter()
        }
        btnHealth.setOnClickListener {
            selectedCategory = "Health"
            applyFilter()
        }
        btnShelter.setOnClickListener {
            selectedCategory = "Shelter"
            applyFilter()
        }
        btnDisaster.setOnClickListener {
            selectedCategory = "Disaster Relief"
            applyFilter()
        }
    }

    private fun applyFilter() {
        val filtered: List<Ngo> = if (selectedCategory == null) {
            HopeHouseRepository.ngos
        } else {
            HopeHouseRepository.ngos.filter { it.category == selectedCategory }
        }
        adapter.submitList(filtered)
    }
}

