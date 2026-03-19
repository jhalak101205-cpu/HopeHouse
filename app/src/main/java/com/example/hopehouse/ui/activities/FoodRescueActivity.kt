package com.example.hopehouse.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopehouse.R
import com.example.hopehouse.data.FoodPost
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.HopeHouseSession
import com.example.hopehouse.ui.adapters.FoodPostAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import androidx.recyclerview.widget.RecyclerView

/**
 * Food Rescue screen:
 * - Form to add surplus food (location, quantity, time)
 * - RecyclerView list of posts
 * - Each post supports "Claim Food" (dummy state + session tracking)
 */
class FoodRescueActivity : AppCompatActivity() {

    private lateinit var adapter: FoodPostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_rescue)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFoodRescue)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        val recycler: RecyclerView = findViewById(R.id.recyclerFoodPosts)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = FoodPostAdapter { post ->
            // Claim behavior updates in-memory state.
            val claimed = HopeHouseRepository.markFoodClaimed(post.id)
            if (claimed) {
                HopeHouseSession.recordFoodClaim()
                Toast.makeText(this, "Food claimed successfully!", Toast.LENGTH_SHORT).show()
                adapter.submitList(HopeHouseRepository.foodPosts.toList())
            } else {
                Toast.makeText(this, "This food post is already claimed.", Toast.LENGTH_SHORT).show()
            }
        }
        recycler.adapter = adapter
        adapter.submitList(HopeHouseRepository.foodPosts.toList())

        val etLocation: TextInputEditText = findViewById(R.id.etFoodLocation)
        val etQuantity: TextInputEditText = findViewById(R.id.etFoodQuantity)
        val etTime: TextInputEditText = findViewById(R.id.etFoodTime)
        val btnPost: MaterialButton = findViewById(R.id.btnPostFood)

        btnPost.setOnClickListener {
            val location = etLocation.text?.toString()?.trim().orEmpty()
            val quantityText = etQuantity.text?.toString()?.trim().orEmpty()
            val timeLabel = etTime.text?.toString()?.trim().orEmpty()

            if (location.isBlank() || timeLabel.isBlank() || quantityText.isBlank()) {
                Toast.makeText(this, "Please fill location, quantity, and time.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantityKg = quantityText.toIntOrNull() ?: 0
            if (quantityKg <= 0) {
                Toast.makeText(this, "Quantity must be greater than 0.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add new food post to the in-memory repository.
            val newPost = FoodPost(
                id = "food_${System.currentTimeMillis()}",
                location = location,
                quantityKg = quantityKg,
                timeLabel = timeLabel,
                claimed = false
            )
            HopeHouseRepository.addFoodPost(newPost)
            HopeHouseSession.recordFoodPost()

            Toast.makeText(this, "Food posted for rescue!", Toast.LENGTH_SHORT).show()

            etLocation.setText("")
            etQuantity.setText("")
            etTime.setText("")

            adapter.submitList(HopeHouseRepository.foodPosts.toList())
        }
    }
}

