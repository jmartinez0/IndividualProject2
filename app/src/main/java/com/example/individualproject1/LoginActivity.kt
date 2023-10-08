package com.example.individualproject1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonLogin.setOnClickListener {
            // Get user inputs
            val emailInput = findViewById<EditText>(R.id.editTextEmail).text.toString().trim()
            val passwordInput = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()

            if (checkLoginCredentials(emailInput, passwordInput)) {
                // If login is successful, display success message
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            } else {
                // If login fails, display error message
                Toast.makeText(this, "Email and/or password is invalid", Toast.LENGTH_SHORT).show()
            }

        }

        // Go to register screen
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkLoginCredentials(email: String, password: String): Boolean {
        // Empty fields are invalid
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        val sharedPreference = getSharedPreferences("SharedPrefKey", Context.MODE_PRIVATE)
        val emailInSharedPref = sharedPreference.getString(email, "")
        val passInSharedPref = sharedPreference.getString(password, "")
        // Invalid if the user-inputted email and password are NOT equal to the key-value pair in shared preferences
        if (!(email == emailInSharedPref && password == passInSharedPref)) {
            return false
        }

        return true
    }
}