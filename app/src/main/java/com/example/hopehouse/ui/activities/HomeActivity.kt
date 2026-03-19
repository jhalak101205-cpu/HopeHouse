package com.example.hopehouse.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hopehouse.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Home screen:
 * - Card-based entry points for each main section
 * - Bottom navigation bar that navigates via intents
 */
class HomeActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER_ROLE = "extra_user_role"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val role = intent.getStringExtra(EXTRA_USER_ROLE) ?: "User"
        findViewById<android.widget.TextView>(R.id.tvWelcome).text =
            "Welcome, $role"

        // Card-based navigation
        findViewById<android.view.View>(R.id.cardNgosNearby).setOnClickListener {
            startActivity(Intent(this, NgoListingActivity::class.java))
        }
        findViewById<android.view.View>(R.id.cardFoodRescue).setOnClickListener {
            startActivity(Intent(this, FoodRescueActivity::class.java))
        }
        findViewById<android.view.View>(R.id.cardVolunteer).setOnClickListener {
            startActivity(Intent(this, VolunteerActivity::class.java))
        }
        findViewById<android.view.View>(R.id.cardEvents).setOnClickListener {
            startActivity(Intent(this, EventsActivity::class.java))
        }
        findViewById<android.view.View>(R.id.cardDonate).setOnClickListener {
            startActivity(Intent(this, DonationActivity::class.java))
        }
        findViewById<android.view.View>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Bottom navigation bar
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navNgos -> startActivity(Intent(this, NgoListingActivity::class.java))
                R.id.navFoodRescue -> startActivity(Intent(this, FoodRescueActivity::class.java))
                R.id.navVolunteer -> startActivity(Intent(this, VolunteerActivity::class.java))
                R.id.navEvents -> startActivity(Intent(this, EventsActivity::class.java))
                R.id.navDonate -> startActivity(Intent(this, DonationActivity::class.java))
                else -> return@setOnItemSelectedListener false
            }
            true
        }
    }
}

