package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var verificationId: String? = null

    private lateinit var etPhone: TextInputEditText
    private lateinit var etOtpPhone: TextInputEditText
    private lateinit var btnSendOtpPhone: MaterialButton
    private lateinit var btnVerifyOtpPhone: MaterialButton

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnEmailLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // ✅ IDs must match activity_login.xml
        etPhone = findViewById(R.id.etPhone)
        etOtpPhone = findViewById(R.id.etOtpPhone)
        btnSendOtpPhone = findViewById(R.id.btnSendOtpPhone)
        btnVerifyOtpPhone = findViewById(R.id.btnVerifyOtpPhone)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnEmailLogin = findViewById(R.id.btnEmailLogin)
        btnRegister = findViewById(R.id.btnRegister)

        // ✅ Send OTP
        btnSendOtpPhone.setOnClickListener {
            val phone = etPhone.text?.toString()?.trim() ?: ""

            if (phone.isEmpty()) {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!phone.startsWith("+")) {
                Toast.makeText(this, "Use country code (ex: +91XXXXXXXXXX)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (phone.length < 10) {
                Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sendOtp(phone)
        }

        // ✅ Verify OTP
        btnVerifyOtpPhone.setOnClickListener {
            val otp = etOtpPhone.text?.toString()?.trim() ?: ""
            val verId = verificationId

            if (verId.isNullOrEmpty()) {
                Toast.makeText(this, "Please send OTP first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (otp.length != 6) {
                Toast.makeText(this, "Enter valid 6 digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val credential = PhoneAuthProvider.getCredential(verId, otp)
            signInWithCredential(credential)
        }

        // ✅ Email login (demo for now)
        btnEmailLogin.setOnClickListener {
            val email = etEmail.text?.toString()?.trim() ?: ""
            val pass = etPassword.text?.toString()?.trim() ?: ""

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass.length < 6) {
                Toast.makeText(this, "Password must be 6+ chars", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Email login will be connected next", Toast.LENGTH_SHORT).show()
        }

        // ✅ Register
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun sendOtp(phone: String) {
        btnSendOtpPhone.isEnabled = false
        Toast.makeText(this, "Sending OTP...", Toast.LENGTH_SHORT).show()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Sometimes auto detect
                    val code = credential.smsCode
                    if (!code.isNullOrEmpty()) {
                        etOtpPhone.setText(code)
                    }
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    btnSendOtpPhone.isEnabled = true
                    Toast.makeText(
                        this@LoginActivity,
                        "OTP Failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    e.printStackTrace()
                }

                override fun onCodeSent(
                    verId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = verId
                    btnSendOtpPhone.isEnabled = true
                    Toast.makeText(this@LoginActivity, "OTP Sent ✅", Toast.LENGTH_SHORT).show()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        btnVerifyOtpPhone.isEnabled = false

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                btnVerifyOtpPhone.isEnabled = true

                if (task.isSuccessful) {
                    Toast.makeText(this, "Login Success ✅", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, KycStatusActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Login Failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}