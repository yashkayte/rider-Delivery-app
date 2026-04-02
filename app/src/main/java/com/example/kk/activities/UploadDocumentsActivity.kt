package com.example.kk.activities

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage

class UploadDocumentsActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnSubmit: MaterialButton

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private var selectedIndex: Int = -1

    private val documentList = mutableListOf(
        DocumentItem("Aadhaar Card", "aadhaar"),
        DocumentItem("PAN Card", "pan"),
        DocumentItem("Driving License", "driving_license")
    )

    private lateinit var adapter: DocumentUploadAdapter

    private val pickFileLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null && selectedIndex != -1) {
                uploadToFirebase(uri, selectedIndex)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_documents)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        
        // Explicitly initialize Storage with the correct bucket name from your google-services.json
        storage = FirebaseStorage.getInstance("gs://khana-rider.firebasestorage.app")

        val user = auth.currentUser
        if (user == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        recycler = findViewById(R.id.recyclerDocs)
        btnSubmit = findViewById(R.id.btnSubmitDocs)

        adapter = DocumentUploadAdapter(documentList) { _, position ->
            selectedIndex = position
            pickFileLauncher.launch("image/*")
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        loadExistingDocs()

        btnSubmit.setOnClickListener {
            if (documentList.all { it.isUploaded }) {
                submitKyc()
            } else {
                Toast.makeText(this, "Please upload all required documents", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun uploadToFirebase(uri: Uri, index: Int) {
        val user = auth.currentUser ?: return
        val docItem = documentList[index]

        adapter.setUploading(index, true)

        // Ensure the path is simple for initial testing
        val fileName = "${docItem.key}_${System.currentTimeMillis()}.jpg"
        val fileRef = storage.reference.child("riders/${user.uid}/$fileName")

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl
                    .addOnSuccessListener { downloadUrl ->
                        saveDocToFirestore(index, downloadUrl.toString())
                    }
                    .addOnFailureListener { e ->
                        adapter.setUploading(index, false)
                        Toast.makeText(this, "URL Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
            }
            .addOnFailureListener { e ->
                adapter.setUploading(index, false)
                // This will now show the exact error if the bucket isn't found
                Toast.makeText(this, "Upload Failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveDocToFirestore(index: Int, url: String) {
        val user = auth.currentUser ?: return
        val docItem = documentList[index]

        val data = hashMapOf(
            "title" to docItem.title,
            "key" to docItem.key,
            "fileUrl" to url,
            "status" to "uploaded",
            "uploadedAt" to Timestamp.now()
        )

        db.collection("riders")
            .document(user.uid)
            .collection("documents")
            .document(docItem.key)
            .set(data)
            .addOnSuccessListener {
                documentList[index].isUploaded = true
                documentList[index].fileUrl = url
                adapter.setUploading(index, false)
                adapter.notifyItemChanged(index)
                Toast.makeText(this, "${docItem.title} saved ✅", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                adapter.setUploading(index, false)
                Toast.makeText(this, "Firestore Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }

    private fun submitKyc() {
        val user = auth.currentUser ?: return
        btnSubmit.isEnabled = false

        val riderData = hashMapOf(
            "kycStatus" to "submitted",
            "submittedAt" to Timestamp.now()
        )

        db.collection("riders")
            .document(user.uid)
            .set(riderData, SetOptions.merge())
            .addOnSuccessListener {
                Toast.makeText(this, "KYC Submitted Successfully! ✅", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { e ->
                btnSubmit.isEnabled = true
                Toast.makeText(this, "Submit Failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
    }

    private fun loadExistingDocs() {
        val user = auth.currentUser ?: return

        db.collection("riders")
            .document(user.uid)
            .collection("documents")
            .get()
            .addOnSuccessListener { snap ->
                for (doc in snap.documents) {
                    val key = doc.getString("key") ?: continue
                    val url = doc.getString("fileUrl")
                    val idx = documentList.indexOfFirst { it.key == key }
                    if (idx != -1 && !url.isNullOrEmpty()) {
                        documentList[idx].isUploaded = true
                        documentList[idx].fileUrl = url
                    }
                }
                adapter.notifyDataSetChanged()
            }
    }
}
