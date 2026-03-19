package com.example.hopehouse.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopehouse.R
import com.example.hopehouse.data.HopeHouseSession
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.RadioGroup

/**
 * Signup screen:
 * - Name
 * - Email
 * - Password
 * - Role (User / NGO)
 *
 * Dummy-only: validates inputs then navigates to HomeActivity.
 */
class SignupActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ROLE = "extra_role"
    }

    private fun isValidEmail(email: String): Boolean {
        // Requirement: must contain '@' and be in valid format.
        return email.contains('@') && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarSignup)
        toolbar.setNavigationIcon(android.R.drawable.ic_media_previous)
        toolbar.setNavigationOnClickListener { finish() }

        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)

        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)

        val rgRole = findViewById<RadioGroup>(R.id.rgRole)
        val rbUser = findViewById<MaterialRadioButton>(R.id.rbRoleUser)
        val rbNgo = findViewById<MaterialRadioButton>(R.id.rbRoleNgo)

        // Preselect role if user came from MainActivity signup buttons.
        when (intent.getStringExtra(EXTRA_ROLE)) {
            "NGO" -> rgRole.check(rbNgo.id)
            else -> rgRole.check(rbUser.id)
        }

        val btnSignup = findViewById<MaterialButton>(R.id.btnSignup)
        btnSignup.setOnClickListener {
            tilName.error = null
            tilEmail.error = null
            tilPassword.error = null

            val name = etName.text?.toString()?.trim().orEmpty()
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val password = etPassword.text?.toString()?.trim().orEmpty()

            val selectedRole = when (rgRole.checkedRadioButtonId) {
                rbUser.id -> "User"
                rbNgo.id -> "NGO"
                else -> null
            }

            var ok = true
            if (name.isBlank()) {
                tilName.error = "Name is required"
                ok = false
            }
            if (email.isBlank() || !isValidEmail(email)) {
                tilEmail.error = "Enter a valid email"
                ok = false
            }
            // Password rules not specified beyond being required; enforce minimum length for demo UX.
            if (password.isBlank()) {
                tilPassword.error = "Password is required"
                ok = false
            } else if (password.length < 6) {
                tilPassword.error = "Password must be at least 6 characters"
                ok = false
            }

            if (selectedRole == null) {
                Toast.makeText(this, "Please choose a role.", Toast.LENGTH_SHORT).show()
                ok = false
            }

            if (!ok) {
                Toast.makeText(this, "Please fix the highlighted fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            HopeHouseSession.userRole = selectedRole

            val homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.putExtra(HomeActivity.EXTRA_USER_ROLE, selectedRole)
            startActivity(homeIntent)
            finish()
        }
    }
}

