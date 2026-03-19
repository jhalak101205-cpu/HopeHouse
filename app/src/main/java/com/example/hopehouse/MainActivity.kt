package com.example.hopehouse

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopehouse.ui.activities.HomeActivity
import com.example.hopehouse.ui.activities.SignupActivity
import com.example.hopehouse.data.HopeHouseSession
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private fun isValidEmail(email: String): Boolean {
        // Requirement: must contain '@' and be in valid format.
        return email.contains('@') && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)

        // User section
        val userEmail = findViewById<TextInputEditText>(R.id.etUserEmail)
        val userPassword = findViewById<TextInputEditText>(R.id.etUserPassword)
        val tilUserEmail = findViewById<TextInputLayout>(R.id.tilUserEmail)
        val tilUserPassword = findViewById<TextInputLayout>(R.id.tilUserPassword)
        val userLoginBtn = findViewById<MaterialButton>(R.id.btnUserLogin)
        val userSignupBtn = findViewById<MaterialButton>(R.id.btnUserSignup)

        userLoginBtn.setOnClickListener {
            // Clear previous errors
            tilUserEmail.error = null
            tilUserPassword.error = null

            val email = userEmail.text?.toString()?.trim().orEmpty()
            val password = userPassword.text?.toString()?.trim().orEmpty()

            var ok = true
            if (email.isBlank() || !isValidEmail(email)) {
                tilUserEmail.error = "Enter a valid email"
                ok = false
            }
            if (password.isBlank()) {
                tilUserPassword.error = "Password is required"
                ok = false
            }
            if (!ok) {
                Toast.makeText(this, "Please fix the highlighted fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startHome("User")
        }

        userSignupBtn.setOnClickListener {
            // Open dedicated signup UI for proper signup form.
            val intent = Intent(this, SignupActivity::class.java)
            intent.putExtra(SignupActivity.EXTRA_ROLE, "User")
            startActivity(intent)
        }

        // NGO section
        val ngoEmail = findViewById<TextInputEditText>(R.id.etNgoEmail)
        val ngoPassword = findViewById<TextInputEditText>(R.id.etNgoPassword)
        val tilNgoEmail = findViewById<TextInputLayout>(R.id.tilNgoEmail)
        val tilNgoPassword = findViewById<TextInputLayout>(R.id.tilNgoPassword)
        val ngoLoginBtn = findViewById<MaterialButton>(R.id.btnNgoLogin)
        val ngoSignupBtn = findViewById<MaterialButton>(R.id.btnNgoSignup)

        ngoLoginBtn.setOnClickListener {
            // Clear previous errors
            tilNgoEmail.error = null
            tilNgoPassword.error = null

            val email = ngoEmail.text?.toString()?.trim().orEmpty()
            val password = ngoPassword.text?.toString()?.trim().orEmpty()

            var ok = true
            if (email.isBlank() || !isValidEmail(email)) {
                tilNgoEmail.error = "Enter a valid email"
                ok = false
            }
            if (password.isBlank()) {
                tilNgoPassword.error = "Password is required"
                ok = false
            }
            if (!ok) {
                Toast.makeText(this, "Please fix the highlighted fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startHome("NGO")
        }

        ngoSignupBtn.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.putExtra(SignupActivity.EXTRA_ROLE, "NGO")
            startActivity(intent)
        }
    }

    private fun startHome(role: String) {
        HopeHouseSession.userRole = role
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(HomeActivity.EXTRA_USER_ROLE, role)
        startActivity(intent)
        finish()
    }
}