package com.example.kk.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class SupportActivity : AppCompatActivity() {

    private lateinit var issueTypeDropdown: AutoCompleteTextView
    private lateinit var etIssueMessage: TextInputEditText
    private lateinit var btnCallSupport: MaterialButton
    private lateinit var btnEmailSupport: MaterialButton
    private lateinit var btnWhatsappSupport: MaterialButton
    private lateinit var btnSubmitIssue: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        issueTypeDropdown = findViewById(R.id.issueTypeDropdown)
        etIssueMessage = findViewById(R.id.etIssueMessage)
        btnCallSupport = findViewById(R.id.btnCallSupport)
        btnEmailSupport = findViewById(R.id.btnEmailSupport)
        btnWhatsappSupport = findViewById(R.id.btnWhatsappSupport)
        btnSubmitIssue = findViewById(R.id.btnSubmitIssue)

        val issueTypes = listOf(
            "Order Issue",
            "Payment Issue",
            "KYC Issue",
            "App Problem",
            "Bank / UPI Issue",
            "Other"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, issueTypes)
        issueTypeDropdown.setAdapter(adapter)

        btnCallSupport.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:+919999999999")
            startActivity(dialIntent)
        }

        btnEmailSupport.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:support@khanarider.com")
                putExtra(Intent.EXTRA_SUBJECT, "Khana Rider Support")
            }
            startActivity(emailIntent)
        }

        btnWhatsappSupport.setOnClickListener {
            val number = "919999999999"
            val message = "Hello Support, I need help regarding Khana Rider app."
            val uri = Uri.parse("https://wa.me/$number?text=${Uri.encode(message)}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
            }
        }

        btnSubmitIssue.setOnClickListener {
            val issueType = issueTypeDropdown.text.toString().trim()
            val message = etIssueMessage.text.toString().trim()

            if (issueType.isEmpty()) {
                Toast.makeText(this, "Please select issue type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (message.isEmpty()) {
                Toast.makeText(this, "Please enter issue details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(
                this,
                "Issue submitted successfully ✅\nType: $issueType",
                Toast.LENGTH_LONG
            ).show()

            issueTypeDropdown.setText("")
            etIssueMessage.setText("")
        }
    }
}