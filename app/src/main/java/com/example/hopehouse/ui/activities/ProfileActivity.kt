package com.example.hopehouse.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hopehouse.R
import com.example.hopehouse.data.HopeHouseSession
import com.google.android.material.appbar.MaterialToolbar
import android.widget.Button
import android.widget.TextView

/**
 * Profile dashboard:
 * - Shows user activity counters (donations, volunteering, food posts)
 * - Data is tracked in-memory via [HopeHouseSession] (demo)
 */
class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarProfile)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        bindCounters()

        findViewById<Button>(R.id.btnBackToHome).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun bindCounters() {
        val role = HopeHouseSession.userRole ?: "User"
        findViewById<TextView>(R.id.tvProfileRole).text = "Role: $role"

        findViewById<TextView>(R.id.tvDonationMoney).text =
            "Donations (Money): ${HopeHouseSession.donationMoneyCount}"
        findViewById<TextView>(R.id.tvDonationItems).text =
            "Donations (Items): ${HopeHouseSession.donationItemsCount}"
        findViewById<TextView>(R.id.tvVolunteerCount).text =
            "Volunteering: ${HopeHouseSession.volunteeringCount}"
        findViewById<TextView>(R.id.tvFoodPostsCount).text =
            "Food posts: ${HopeHouseSession.foodPostsCount}"
        findViewById<TextView>(R.id.tvFoodClaimsCount).text =
            "Food claimed: ${HopeHouseSession.foodClaimsCount}"
        findViewById<TextView>(R.id.tvEventRegistrations).text =
            "Event registrations: ${HopeHouseSession.eventRegistrationsCount}"
    }
}

