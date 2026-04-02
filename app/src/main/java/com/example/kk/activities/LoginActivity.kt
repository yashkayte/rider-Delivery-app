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
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnEmailLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnEmailLogin = findViewById(R.id.btnEmailLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnEmailLogin.setOnClickListener {
            loginUser()
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        if (auth.currentUser != null) {
            checkUserStatusAndNavigate(auth.currentUser?.uid!!)
        }
    }

    private fun loginUser() {
        val email = etEmail.text?.toString()?.trim() ?: ""
        val password = etPassword.text?.toString()?.trim() ?: ""

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Invalid email"
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Enter password"
            return
        }

        btnEmailLogin.isEnabled = false
        Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    checkUserStatusAndNavigate(auth.currentUser?.uid!!)
                } else {
                    btnEmailLogin.isEnabled = true
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkUserStatusAndNavigate(uid: String) {
        db.collection("riders").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val kycStatus = document.getString("kycStatus") ?: "pending"
                    if (kycStatus == "approved") {
                        startActivity(Intent(this, RiderDashboardActivity::class.java))
                    } else {
                        startActivity(Intent(this, KycStatusActivity::class.java))
                    }
                    finish()
                } else {
                    // If no firestore record exists, go to register or dashboard
                    startActivity(Intent(this, RiderDashboardActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener {
                startActivity(Intent(this, RiderDashboardActivity::class.java))
                finish()
            }
    }
}
