package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText

    private lateinit var btnCreateAccount: MaterialButton
    private lateinit var btnBackToLogin: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnCreateAccount = findViewById(R.id.btnCreateAccount)
        btnBackToLogin = findViewById(R.id.btnBackToLogin)

        btnBackToLogin.setOnClickListener {
            finish()
        }

        btnCreateAccount.setOnClickListener {
            val name = etName.text?.toString()?.trim() ?: ""
            val email = etEmail.text?.toString()?.trim() ?: ""
            val pass = etPassword.text?.toString()?.trim() ?: ""
            val confirm = etConfirmPassword.text?.toString()?.trim() ?: ""

            if (name.length < 3) {
                Toast.makeText(this, "Enter full name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6) {
                Toast.makeText(this, "Password must be 6+ characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            createAccount(email, pass)
        }
    }

    private fun createAccount(email: String, pass: String) {
        btnCreateAccount.isEnabled = false
        Toast.makeText(this, "Creating account...", Toast.LENGTH_SHORT).show()

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                btnCreateAccount.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(this, "Account created ✅", Toast.LENGTH_SHORT).show()

                    // After registration -> go to KYC status screen
                    startActivity(Intent(this, KycStatusActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Register failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}