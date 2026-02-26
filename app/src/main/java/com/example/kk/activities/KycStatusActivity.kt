package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class KycStatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc_status)

        val btnUpload = findViewById<MaterialButton>(R.id.btnUploadDocs)
        val btnSkip = findViewById<MaterialButton>(R.id.btnSkipKyc)

        btnUpload.setOnClickListener {
            Toast.makeText(this, "Upload screen (next step)", Toast.LENGTH_SHORT).show()
            // Next step: open UploadDocumentsActivity (we will create)
        }

        btnSkip.setOnClickListener {
            startActivity(Intent(this, RiderDashboardActivity::class.java))
            finish()
        }
    }
}