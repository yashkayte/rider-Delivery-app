package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class KycStatusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kyc_status)

        findViewById<MaterialButton>(R.id.btnUploadDocs).setOnClickListener {
            startActivity(Intent(this, UploadDocumentsActivity::class.java))
        }

        findViewById<MaterialButton>(R.id.btnSkipKyc).setOnClickListener {
            val i = Intent(this, RiderDashboardActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }
}