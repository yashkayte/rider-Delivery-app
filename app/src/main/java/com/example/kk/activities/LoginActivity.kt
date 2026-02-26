package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnOtp = findViewById<MaterialButton>(R.id.btnOtp)
        val btnEmail = findViewById<MaterialButton>(R.id.btnEmailLogin)
        val btnSkip = findViewById<MaterialButton>(R.id.btnSkip)

        // Demo OTP login
        btnOtp.setOnClickListener {
            Toast.makeText(this, "OTP Sent (demo)", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, KycStatusActivity::class.java))
            finish()
        }

        // Demo Email login
        btnEmail.setOnClickListener {
            Toast.makeText(this, "Email login success (demo)", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, KycStatusActivity::class.java))
            finish()
        }

        // Skip directly to dashboard
        btnSkip.setOnClickListener {
            startActivity(Intent(this, RiderDashboardActivity::class.java))
            finish()
        }
    }
}