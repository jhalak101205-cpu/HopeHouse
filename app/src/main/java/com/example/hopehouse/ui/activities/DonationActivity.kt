package com.example.hopehouse.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopehouse.R
import com.example.hopehouse.data.Cause
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.HopeHouseSession
import com.example.hopehouse.ui.adapters.CauseAdapter
import com.google.android.material.appbar.MaterialToolbar

/**
 * Donation screen:
 * - Shows causes with progress bars
 * - Users can donate money or items (dummy updates in memory)
 */
class DonationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NGO_ID = "extra_ngo_id"
    }

    private lateinit var adapter: CauseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDonation)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        // If navigated from an NGO, we keep the extra for potential future filtering.
        intent.getStringExtra(EXTRA_NGO_ID)

        val recycler: RecyclerView = findViewById(R.id.recyclerCauses)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = CauseAdapter(
            onDonateMoney = { cause: Cause ->
                HopeHouseRepository.donateToCauseMoney(cause.id, amount = 250)
                HopeHouseSession.recordDonateMoney()
                Toast.makeText(this, "Thank you for donating money!", Toast.LENGTH_SHORT).show()
                refreshCauses()
            },
            onDonateItems = { cause: Cause ->
                HopeHouseRepository.donateToCauseItems(cause.id, progressDelta = 120)
                HopeHouseSession.recordDonateItems()
                Toast.makeText(this, "Thank you for donating items!", Toast.LENGTH_SHORT).show()
                refreshCauses()
            }
        )

        recycler.adapter = adapter
        refreshCauses()
    }

    private fun refreshCauses() {
        adapter.submitList(HopeHouseRepository.causes.toList())
    }
}

