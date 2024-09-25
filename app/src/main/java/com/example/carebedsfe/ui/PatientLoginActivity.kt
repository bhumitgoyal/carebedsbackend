package com.example.carebedsfe.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.carebedsfe.R
import com.example.carebedsfe.api.ApiService
import com.example.carebedsfe.api.RetrofitInstance
import com.example.carebedsfe.fragment.PatientHospitalDashboard
import com.example.carebedsfe.model.LoginRequest
import kotlinx.coroutines.launch

class PatientLoginActivity : AppCompatActivity() {
    private lateinit var api: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_patient_login)

        api= RetrofitInstance.api
        val loginButton: Button = findViewById(R.id.buttonLogin)
        loginButton.setOnClickListener {
            val username = findViewById<EditText>(R.id.editTextEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun performLogin(username: String, password: String) {
        val loginRequest = LoginRequest(username, password)

        lifecycleScope.launch {
            try {
                val response = api.loginPatient(loginRequest)
                if (response.isSuccessful) {
                    // Extract data from the response
                    val loginResponse = response.body()
                    loginResponse?.let {
                        // Save the token, user ID, and role securely (e.g., SharedPreferences)
                        saveUserInfo(it.token, it.userId, it.role)

                        // Navigate to MainActivity
                        val intent = Intent(this@PatientLoginActivity, PatientDashboard::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } ?: run {
                        Toast.makeText(this@PatientLoginActivity, "Unexpected response from server", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Login failed"
                    Log.e("LoginActivity", "Login error: $errorMessage")
                    Toast.makeText(this@PatientLoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PatientLoginActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("LoginActivity", "Login error: ${e.message}")
            }
        }
    }
    private fun saveUserInfo(token: String, userId: Long, role: String) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("TOKEN", token)
            putLong("USER_ID", userId)
            putString("ROLE", role)
            apply()
        }
    }
}