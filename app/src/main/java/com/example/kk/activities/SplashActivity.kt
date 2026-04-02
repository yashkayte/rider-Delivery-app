package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val splashContent = findViewById<View>(R.id.splashContent)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        splashContent.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = auth.currentUser
            if (currentUser != null) {
                checkUserStatusAndNavigate(currentUser.uid)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 2500)
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
                } else {
                    // If no firestore record exists, assume registration was incomplete
                    startActivity(Intent(this, KycStatusActivity::class.java))
                }
                finish()
            }
            .addOnFailureListener {
                // On failure, default to login or dashboard
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
    }
}
