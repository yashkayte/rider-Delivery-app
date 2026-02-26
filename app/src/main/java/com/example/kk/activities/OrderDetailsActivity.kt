package com.example.kk.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class OrderDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val btnCall = findViewById<MaterialButton>(R.id.btnCall)
        val btnNavigate = findViewById<MaterialButton>(R.id.btnNavigate)
        val btnComplete = findViewById<MaterialButton>(R.id.btnComplete)

        btnCall.setOnClickListener {
            Toast.makeText(this, "Calling customer...", Toast.LENGTH_SHORT).show()
        }

        btnNavigate.setOnClickListener {
            Toast.makeText(this, "Opening Maps...", Toast.LENGTH_SHORT).show()
        }

        btnComplete.setOnClickListener {
            Toast.makeText(this, "Delivery Completed!", Toast.LENGTH_SHORT).show()
        }
    }
}