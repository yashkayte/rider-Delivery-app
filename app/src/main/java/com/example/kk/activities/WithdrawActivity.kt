package com.example.kk.activities

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class WithdrawActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdraw)

        val etAmount = findViewById<TextInputEditText>(R.id.etWithdrawAmount)
        val etUpi = findViewById<TextInputEditText>(R.id.etUpiId)
        val etBank = findViewById<TextInputEditText>(R.id.etBankDetails)

        val rbUpi = findViewById<RadioButton>(R.id.rbUpi)
        val rbBank = findViewById<RadioButton>(R.id.rbBank)

        val btnRequest = findViewById<MaterialButton>(R.id.btnRequestWithdraw)
        val btnBack = findViewById<MaterialButton>(R.id.btnBackWithdraw)

        fun updateFields() {
            if (rbUpi.isChecked) {
                etUpi.isEnabled = true
                etUpi.alpha = 1f
                etBank.isEnabled = false
                etBank.alpha = 0.4f
            } else {
                etUpi.isEnabled = false
                etUpi.alpha = 0.4f
                etBank.isEnabled = true
                etBank.alpha = 1f
            }
        }

        rbUpi.setOnCheckedChangeListener { _, _ -> updateFields() }
        rbBank.setOnCheckedChangeListener { _, _ -> updateFields() }
        updateFields()

        btnRequest.setOnClickListener {
            val amountStr = etAmount.text?.toString()?.trim() ?: ""
            val amount = amountStr.toIntOrNull()

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Enter valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rbUpi.isChecked) {
                val upi = etUpi.text?.toString()?.trim() ?: ""
                if (upi.isEmpty() || !upi.contains("@")) {
                    Toast.makeText(this, "Enter valid UPI ID", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                val bank = etBank.text?.toString()?.trim() ?: ""
                if (bank.length < 10) {
                    Toast.makeText(this, "Enter bank details (Acc + IFSC)", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            Toast.makeText(this, "✅ Withdrawal Requested: ₹$amount", Toast.LENGTH_LONG).show()
            finish()
        }

        btnBack.setOnClickListener { finish() }
    }
}