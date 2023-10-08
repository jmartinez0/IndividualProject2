package com.example.individualproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.SharedPreferences


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonRegister.setOnClickListener {
            // Take in all the user inputs
            val firstName = findViewById<EditText>(R.id.editTextFirstName).text.toString().trim()
            val familyName = findViewById<EditText>(R.id.editTextFamilyName).text.toString().trim()
            val dob = findViewById<EditText>(R.id.editTextDOB).text.toString().trim()
            val email = findViewById<EditText>(R.id.editTextEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.editTextPassword).text.toString().trim()

            // Check if the data is valid for registration
            if (isDataValid(firstName, familyName, dob, email, password)) {
                // If registration is successful, we store a key-value pair of the email and password in shared preferences.
                // For this assignment, we only need email and password to enable log in.
                // Therefore, I will only be storing those two pieces of information.
                val sharedPreference: SharedPreferences = getSharedPreferences("SharedPrefKey", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString(email, email)
                editor.putString(password, password)
                editor.apply()
                // Go back to log in screen
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                // Display a message so the user knows registration was successful
                Toast.makeText(this, "Registration successful.", Toast.LENGTH_SHORT).show()
            } else {
                // Invalid data
                Toast.makeText(this, "Invalid input(s). See the descriptions above the fields.", Toast.LENGTH_SHORT).show()
            }
        }

        // Go back to the log in screen
        buttonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isDataValid(firstName: String, familyName: String, dob: String, email: String, password: String): Boolean {
        // If any of the fields are empty, the data is not valid
        if (firstName.isEmpty() || familyName.isEmpty() || dob.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return false
        }

        // First name, family name, and password all need to be 3-30 characters long
        if (firstName.length !in 3..30 || familyName.length !in 3..30 || password.length !in 3..30) {
            return false
        }

        // Check email address
        if (!checkEmail(email)) {
            return false
        }

        // Check date of birth
        if (!checkDOB(dob)) {
            return false;
        }

        return true
    }

    private fun checkEmail(email: String): Boolean {
        // Email must be in the format email@example.com
        // We check for alphanumeric characters before the @
        // Then the @
        // Then lowercase letters after the @ (gmail for example)
        // Then the .
        // Then lowercase letters after the . (.com for example)
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun checkDOB(dob: String): Boolean {
        // DOB must be in the format MM/DD/YY
        // We check for month by allowing a leading 0 for months 1-9 (september is 09 for example)
        // For october-december we allow 1 followed by a digit from 0-2
        // Then for days, we allow days from 01-31
        // Then for year, we take the last two digits to represent year (04 is 2004 for example)
        val dobPattern = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\\d{2}$"
        return dob.matches(dobPattern.toRegex())
    }
}