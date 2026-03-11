package com.example.kk.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kk.R
import com.google.android.material.button.MaterialButton

class DocumentUploadAdapter(
    private val list: MutableList<DocumentItem>,
    private val onUploadClick: (DocumentItem, Int) -> Unit
) : RecyclerView.Adapter<DocumentUploadAdapter.DocVH>() {

    private val uploading = HashSet<Int>()

    fun setUploading(position: Int, isUploading: Boolean) {
        if (isUploading) uploading.add(position) else uploading.remove(position)
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_document_upload, parent, false)
        return DocVH(view)
    }

    override fun onBindViewHolder(holder: DocVH, position: Int) {
        val item = list[position]

        holder.txtDocName.text = item.title

        when {
            uploading.contains(position) -> {
                holder.btnUpload.text = "Uploading..."
                holder.btnUpload.isEnabled = false
            }
            item.isUploaded -> {
                holder.btnUpload.text = "Uploaded ✅"
                holder.btnUpload.isEnabled = false
            }
            else -> {
                holder.btnUpload.text = "Upload"
                holder.btnUpload.isEnabled = true
            }
        }

        holder.btnUpload.setOnClickListener {
            onUploadClick(item, position)
        }
    }

    override fun getItemCount(): Int = list.size

    class DocVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDocName: TextView = itemView.findViewById(R.id.txtDocName)
        val btnUpload: MaterialButton = itemView.findViewById(R.id.btnUploadDoc)
    }
}