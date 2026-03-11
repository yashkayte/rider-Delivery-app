package com.example.kk.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        findViewById<MaterialButton>(R.id.btnSaveProfile).setOnClickListener {
            Toast.makeText(this, "Profile Saved (Demo)", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}