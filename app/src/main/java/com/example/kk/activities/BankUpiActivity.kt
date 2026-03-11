package com.example.kk.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class BankUpiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_upi)

        findViewById<MaterialButton>(R.id.btnSaveBank).setOnClickListener {
            Toast.makeText(this, "Bank/UPI Saved (Demo)", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}