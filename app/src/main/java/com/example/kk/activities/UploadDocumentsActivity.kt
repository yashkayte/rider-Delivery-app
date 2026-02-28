package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class UploadDocumentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)

        val btnSubmit = findViewById<MaterialButton>(R.id.btnSubmitDocs)

        btnSubmit.setOnClickListener {
            Toast.makeText(this, "Documents submitted (Demo)", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RiderDashboardActivity::class.java))
            finish()
        }
    }
}