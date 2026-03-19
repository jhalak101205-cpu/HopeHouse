package com.example.hopehouse.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hopehouse.R
import com.example.hopehouse.data.HopeHouseRepository
import com.example.hopehouse.data.Ngo
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class NgoDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NGO_ID = "extra_ngo_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ngo_detail)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarNgoDetail)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        val ngoId = intent.getStringExtra(EXTRA_NGO_ID) ?: return
        val ngo = HopeHouseRepository.getNgoById(ngoId) ?: return

        bindNgoHeader(ngo)

        populateList(findViewById(R.id.llNeeds), ngo.needs)
        populateList(findViewById(R.id.llCampaigns), ngo.campaigns)

        findViewById<MaterialButton>(R.id.btnVolunteer).setOnClickListener {
            val i = Intent(this, VolunteerActivity::class.java)
            i.putExtra(VolunteerActivity.EXTRA_NGO_ID, ngo.id)
            startActivity(i)
        }

        findViewById<MaterialButton>(R.id.btnDonate).setOnClickListener {
            val i = Intent(this, DonationActivity::class.java)
            i.putExtra(DonationActivity.EXTRA_NGO_ID, ngo.id)
            startActivity(i)
        }

        findViewById<MaterialButton>(R.id.btnViewEvents).setOnClickListener {
            val i = Intent(this, EventsActivity::class.java)
            i.putExtra(EventsActivity.EXTRA_NGO_ID, ngo.id)
            startActivity(i)
        }
    }

    private fun bindNgoHeader(ngo: Ngo) {
        val iv: ImageView = findViewById(R.id.ivNgoDetail)
        val tvName: TextView = findViewById(R.id.tvNgoNameDetail)
        val tvCategory: TextView = findViewById(R.id.tvNgoCategoryDetail)
        val tvLocation: TextView = findViewById(R.id.tvNgoLocationDetail)

        iv.setImageResource(ngo.imageResId)
        tvName.text = ngo.name
        tvCategory.text = "Category: ${ngo.category}"
        tvLocation.text = "Location: ${ngo.location}"
    }

    private fun populateList(container: LinearLayout, items: List<String>) {
        container.removeAllViews()

        for (item in items) {
            val tv = TextView(this)
            tv.text = "• $item"
            tv.setTextColor(getColor(R.color.black))

            tv.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            tv.setPadding(0, 6, 0, 6)
            container.addView(tv)
        }
    }
}