package com.example.kk.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEdit = view.findViewById<MaterialButton>(R.id.btnEditProfile)
        val btnDocs = view.findViewById<MaterialButton>(R.id.btnDocuments)
        val btnSupport = view.findViewById<MaterialButton>(R.id.btnSupport)
        val btnLogout = view.findViewById<MaterialButton>(R.id.btnLogout)

        btnEdit.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile (coming soon)", Toast.LENGTH_SHORT).show()
        }

        btnDocs.setOnClickListener {
            Toast.makeText(requireContext(), "Documents (coming soon)", Toast.LENGTH_SHORT).show()
        }

        btnSupport.setOnClickListener {
            Toast.makeText(requireContext(), "Support (coming soon)", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            // Back to Login
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}