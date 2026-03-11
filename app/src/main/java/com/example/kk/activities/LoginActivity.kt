package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
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
    private var isDemoOtpMode = false

    private lateinit var etPhone: TextInputEditText
    private lateinit var etOtpPhone: TextInputEditText
    private lateinit var btnSendOtpPhone: MaterialButton
    private lateinit var btnVerifyOtpPhone: MaterialButton

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnEmailLogin: MaterialButton
    private lateinit var btnRegister: MaterialButton

    private lateinit var otpBox: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        etPhone = findViewById(R.id.etPhone)
        etOtpPhone = findViewById(R.id.etOtpPhone)
        btnSendOtpPhone = findViewById(R.id.btnSendOtpPhone)
        btnVerifyOtpPhone = findViewById(R.id.btnVerifyOtpPhone)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnEmailLogin = findViewById(R.id.btnEmailLogin)
        btnRegister = findViewById(R.id.btnRegister)

        otpBox = findViewById(R.id.otpBox)
        otpBox.visibility = View.GONE

        btnSendOtpPhone.setOnClickListener {
            val phone = etPhone.text?.toString()?.trim().orEmpty()

            if (phone.isEmpty()) {
                toast("Enter phone number")
                return@setOnClickListener
            }

            if (!phone.startsWith("+")) {
                toast("Use country code, example: +919999999999")
                return@setOnClickListener
            }

            if (phone.length < 10) {
                toast("Enter valid phone number")
                return@setOnClickListener
            }

            otpBox.visibility = View.VISIBLE
            sendOtp(phone)
        }

        btnVerifyOtpPhone.setOnClickListener {
            val otp = etOtpPhone.text?.toString()?.trim().orEmpty()

            if (otp.length != 6) {
                toast("Enter valid 6 digit OTP")
                return@setOnClickListener
            }

            if (isDemoOtpMode) {
                if (otp == "123456") {
                    auth.signInAnonymously()
                        .addOnSuccessListener { loginSuccess() }
                        .addOnFailureListener { e ->
                            toast("Demo login failed: ${e.message}")
                        }
                } else {
                    toast("Wrong OTP. Demo OTP is 123456")
                }
                return@setOnClickListener
            }

            val verId = verificationId
            if (verId.isNullOrEmpty()) {
                toast("Please send OTP first")
                return@setOnClickListener
            }

            val credential = PhoneAuthProvider.getCredential(verId, otp)
            signInWithCredential(credential)
        }

        btnEmailLogin.setOnClickListener {
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass = etPassword.text?.toString()?.trim().orEmpty()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                toast("Enter valid email")
                return@setOnClickListener
            }

            if (pass.length < 6) {
                toast("Password must be at least 6 characters")
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener { loginSuccess() }
                .addOnFailureListener { e ->
                    toast("Email login failed: ${e.message}")
                }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun sendOtp(phone: String) {
        btnSendOtpPhone.isEnabled = false
        toast("Sending OTP...")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    val code = credential.smsCode
                    if (!code.isNullOrEmpty()) {
                        etOtpPhone.setText(code)
                    }
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    btnSendOtpPhone.isEnabled = true
                    isDemoOtpMode = true
                    verificationId = "DEMO"
                    toast("OTP failed, demo mode enabled. Use OTP: 123456")
                }

                override fun onCodeSent(
                    verId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = verId
                    isDemoOtpMode = false
                    btnSendOtpPhone.isEnabled = true
                    toast("OTP Sent")
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
                    loginSuccess()
                } else {
                    toast("Login failed: ${task.exception?.message}")
                }
            }
    }

    private fun loginSuccess() {
        toast("Login Success")
        val i = Intent(this, KycStatusActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
        finish()
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}