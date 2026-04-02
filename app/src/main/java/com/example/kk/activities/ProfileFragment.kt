package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.kk.R
import com.example.kk.database.DatabaseHelper
import com.example.kk.utils.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var txtName: TextView
    private lateinit var txtPhone: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize database helper
        databaseHelper = DatabaseHelper(requireContext())

        // Find views
        txtName = view.findViewById(R.id.txtName)
        txtPhone = view.findViewById(R.id.txtPhone)

        val editProfile = view.findViewById<View>(R.id.txtEditProfile)
        editProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        val bankUpi = view.findViewById<View>(R.id.txtBankUpi)
        bankUpi.setOnClickListener {
            val intent = Intent(requireContext(), BankUpiActivity::class.java)
            startActivity(intent)
        }

        val btnLogout = view.findViewById<MaterialButton>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            SessionManager(requireContext()).setLoggedIn(false)

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Load rider profile from database
        loadRiderProfile()

        return view
    }

    private fun loadRiderProfile() {
        lifecycleScope.launch {
            try {
                val rider = databaseHelper.getRider()
                if (rider != null) {
                    txtName.text = rider.name
                    txtPhone.text = rider.phone
                    Log.d("Profile", "Loaded rider profile: ${rider.name}")
                } else {
                    // Fallback to Firebase user data or default
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    txtName.text = firebaseUser?.displayName ?: "Rider"
                    txtPhone.text = firebaseUser?.phoneNumber ?: "+91xxxxxxxxxx"
                    Log.d("Profile", "No rider data in database, using Firebase data")
                }
            } catch (e: Exception) {
                Log.e("Profile", "Failed to load rider profile", e)
                // Fallback
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                txtName.text = firebaseUser?.displayName ?: "Rider"
                txtPhone.text = firebaseUser?.phoneNumber ?: "+91xxxxxxxxxx"
            }
        }
    }
}