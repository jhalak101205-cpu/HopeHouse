package com.example.hopehouse

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopehouse.ui.activities.HomeActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        // User section
        val userEmail = findViewById<TextInputEditText>(R.id.etUserEmail)
        val userPassword = findViewById<TextInputEditText>(R.id.etUserPassword)
        val userLoginBtn = findViewById<MaterialButton>(R.id.btnUserLogin)
        val userSignupBtn = findViewById<MaterialButton>(R.id.btnUserSignup)

        userLoginBtn.setOnClickListener {
            if (userEmail.text.isNullOrBlank() || userPassword.text.isNullOrBlank()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startHome("User")
        }

        userSignupBtn.setOnClickListener {
            if (userEmail.text.isNullOrBlank() || userPassword.text.isNullOrBlank()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startHome("User")
        }

        // NGO section
        val ngoEmail = findViewById<TextInputEditText>(R.id.etNgoEmail)
        val ngoPassword = findViewById<TextInputEditText>(R.id.etNgoPassword)
        val ngoLoginBtn = findViewById<MaterialButton>(R.id.btnNgoLogin)
        val ngoSignupBtn = findViewById<MaterialButton>(R.id.btnNgoSignup)

        ngoLoginBtn.setOnClickListener {
            if (ngoEmail.text.isNullOrBlank() || ngoPassword.text.isNullOrBlank()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startHome("NGO")
        }

        ngoSignupBtn.setOnClickListener {
            if (ngoEmail.text.isNullOrBlank() || ngoPassword.text.isNullOrBlank()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startHome("NGO")
        }
    }

    private fun startHome(role: String) {
        com.example.hopehouse.data.HopeHouseSession.userRole = role
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(HomeActivity.EXTRA_USER_ROLE, role)
        startActivity(intent)
        finish()
    }
}