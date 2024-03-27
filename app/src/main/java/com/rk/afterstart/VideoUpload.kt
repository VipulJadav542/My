package com.rk.afterstart

import android.app.ProgressDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.rk.afterstart.Adapters.VideoFetchAdapter
import com.rk.afterstart.DataModel.Video
import com.rk.afterstart.databinding.ActivityVideoBinding

class VideoUpload : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding
    private lateinit var progressDialog: ProgressDialog
    private var videoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getVideo()

        binding.videoView.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            resultLauncher.launch("video/*")
        }

        progressDialog = ProgressDialog(this)
        binding.select.setOnClickListener {
            binding.cardview.alpha = 0.2f
            binding.progressBar.visibility = View.VISIBLE
            if (videoUri != null) {
                progressDialog.setTitle("Uploading...")
                progressDialog.show()
                val ref = Firebase.storage
                    .reference.child("Videos/")
                    .child(System.currentTimeMillis().toString())
                ref.putFile(videoUri!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        progressDialog.show()
                        val noteItem = Video(it.toString())
                        FirebaseDatabase.getInstance().reference.child("Videos").push()
                            .setValue(noteItem)
                        Toast.makeText(this, "Upload Successfully", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()

                        binding.progressBar.visibility = View.INVISIBLE
                        binding.cardview.alpha = 1f
                        val mediaController = MediaController(this)
                        mediaController.setAnchorView(binding.videoView)
                        binding.videoView.setVideoURI(it)
                        binding.videoView.setMediaController(mediaController)
                        binding.videoView.start()
                    }
                }
                    .addOnProgressListener {
                        val progress = (it.bytesTransferred / it.totalByteCount) * 100
                        progressDialog.setTitle("Uploaded $progress%")

                    }
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                binding.cardview.alpha = 1f
                Toast.makeText(this, "please select video", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getVideo() {
        val storage = FirebaseStorage.getInstance()


        val recyclerView = binding.recyclerview2
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val dataList = mutableListOf<Video>()
        FirebaseDatabase.getInstance().reference.child("Videos")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dataList.clear()
                    for (noteSnapshot in snapshot.children) {
                        val videoUrl = noteSnapshot.child("videoId").getValue(String::class.java)
                        val storageReference = storage.getReferenceFromUrl(videoUrl.toString())
                        // Delete the file
                        storageReference.delete()
                            .addOnSuccessListener {
                                // File deleted successfully
                                // Handle success, e.g., update UI or show a message
                                Log.d(ContentValues.TAG, "Image deleted successfully")
                            }
                            .addOnFailureListener { exception ->
                                // Handle any errors that occurred while deleting the file
                                Log.e(ContentValues.TAG, "Error deleting image: $exception")
                            }


//                        Log.d(TAG, videoUrl.toString())
//                        if (videoUrl != null) {
//                            val data = Video(videoUrl)
//                            dataList.add(data)
//                        }
                    }
                    val adapter = VideoFetchAdapter(dataList, this@VideoUpload)
                    Toast.makeText(this@VideoUpload,dataList.toString(), Toast.LENGTH_SHORT).show()
                    binding.recyclerview2.adapter = adapter
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (it != null) {
            videoUri = it
        }

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)
        binding.videoView.setVideoURI(it)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.start()
        binding.progressBar.visibility = View.INVISIBLE
    }
}
