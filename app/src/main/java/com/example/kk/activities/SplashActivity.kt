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

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashContent = findViewById<View>(R.id.splashContent)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        splashContent.startAnimation(fadeIn)

        val auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser != null) {
                val intent = Intent(this, KycStatusActivity::class.java)
                startActivity(intent)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2500) // Increased delay to 2.5s to let animation finish
    }
}