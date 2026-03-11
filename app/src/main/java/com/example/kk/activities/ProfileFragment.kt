package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kk.R
import com.example.kk.utils.SessionManager
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

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

        return view
    }
}