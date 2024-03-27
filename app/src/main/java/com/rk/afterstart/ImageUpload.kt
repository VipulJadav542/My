package com.rk.afterstart

import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.rk.afterstart.Adapters.ImageFetchAdapter
import com.rk.afterstart.DataModel.Images
import com.rk.afterstart.databinding.ActivityImageUploadBinding
import com.rk.afterstart.databinding.DeletePhotoLayoutBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ImageUpload : AppCompatActivity(), ImageFetchAdapter.OnItemClickListener {
    private lateinit var binding: ActivityImageUploadBinding
    private var imageUri: Uri? = null
    private lateinit var email: String
    private var permission = 0
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        {
            permission = if (it) {
                1
            } else {
                0
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.videoView.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            resultLauncher.launch("image/*")
        }
        get()

        FirebaseAuth.getInstance().currentUser.toString()
        val sharedpref = getSharedPreferences("my_pre", Context.MODE_PRIVATE)
        email = sharedpref.getString("email", "").toString()
        binding.select.setOnClickListener {
            binding.cardview.alpha = 0.2f
            binding.progressBar.visibility = View.VISIBLE
            if (imageUri != null) {
                val ref = Firebase.storage
                    .reference.child("Photos/")
                    .child(System.currentTimeMillis().toString())
                ref.putFile(imageUri!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
//                        val key = FirebaseDatabase.getInstance().getReference("Images").key
                        val noteItem = Images(it.toString(), email)
                        FirebaseDatabase.getInstance().reference.child("Images").push()
                            .setValue(noteItem)
                        Toast.makeText(this, "Upload Successfully", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()

                        binding.progressBar.visibility = View.INVISIBLE
                        binding.cardview.alpha = 1f
                        binding.videoView.setImageResource(R.drawable.ic_launcher_background)
                    }
                }
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.cardview.alpha = 1f
                Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getImage() {
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val dataList = mutableListOf<Images>()
        FirebaseDatabase.getInstance().reference.child("Images")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataList.clear()
                    for (noteSnapshot in snapshot.children) {
                        val ImgUrl =
                            noteSnapshot.child("imgId").getValue(String::class.java).toString()
                        val EmailID =
                            noteSnapshot.child("email").getValue(String::class.java).toString()
                        if (EmailID == email) {
                            val data = Images(ImgUrl, EmailID)
                            dataList.add(data)
                        }

                    }
                    val adapter = ImageFetchAdapter(dataList, this@ImageUpload, this@ImageUpload)
                    binding.recyclerview.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            imageUri = it
        }
        binding.videoView.setImageURI(it)
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun get() {
        CoroutineScope(Dispatchers.IO).async {
            getImage()
        }
    }

    override fun OnDeleteClick(Id: String) {
        val dialogBinding = DeletePhotoLayoutBinding.inflate(layoutInflater)
        Picasso.get().load(Id).into(dialogBinding.imageView2)
        val dialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Delete Photo")
            .setPositiveButton("Delete") { dialog, _ ->
                deletePhoto(Id)
                dialog.dismiss()
            }
            .setNeutralButton("Download") { dialog, _ ->
                requestPermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (permission == 1) {
                    OnDownload(Id)
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .create()
        dialog.show()
    }

    override fun OnDownload(Id: String) {
//        val request = DownloadManager.Request(Uri.parse(Id))
//
//        // Set the destination path for the downloaded file
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "image.jpg")
//
//        // Get DownloadManager service
//        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//
//        // Enqueue the download request
//        downloadManager.enqueue(request)
//        Toast.makeText(this,"Download Successfully", Toast.LENGTH_SHORT).show()
        try {
            val timestamp = System.currentTimeMillis()
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val imagelink = Uri.parse(Id)
            val request = DownloadManager.Request(imagelink)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
                .setMimeType("image/jpg/jpeg/png")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION or DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            // Specify the complete path where you want to save the file
            val fileName = "$timestamp.jpg"
            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(directory, "AfterStart/$fileName")
            request.setDestinationUri(Uri.fromFile(file))

            // Create the subfolder if it doesn't exist
            val folder = File(directory, "AfterStart")
            if (!folder.exists()) {
                folder.mkdirs()
            }

            downloadManager.enqueue(request)
            Toast.makeText(this, "Download Successfully", Toast.LENGTH_SHORT).show()
            Toast.makeText(this,"saved at : $file", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Download Failed due to $e", Toast.LENGTH_SHORT).show()
            Log.e("TAG", e.toString())
        }
    }

        fun encodeTimestamp(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(timestamp)
        val formatted = dateFormat.format(date)
//        return formatted.replace("\\s".toRegex(), "")
        return formatted
    }

    private fun deletePhoto(id: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Images")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (noteSnapshot in snapshot.children) {
                    val title =
                        noteSnapshot.child("imgId").getValue(String::class.java)
                    if (title == id) {
                        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(id)
                        storageReference.delete()
                            .addOnSuccessListener {
                                Log.d(TAG, "Image deleted successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.d(TAG, e.toString())
                            }
                        FirebaseDatabase.getInstance().getReference("Images")
                            .child(noteSnapshot.key.toString())
                            .removeValue().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "Image successfully")
                                } else {
                                    Log.e(TAG, "Failed to delete Image", task.exception)
                                }
                            }

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


}
