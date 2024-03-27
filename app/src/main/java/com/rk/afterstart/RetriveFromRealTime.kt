package com.rk.afterstart

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rk.afterstart.Adapters.RealTimeDataFetchAdapter
import com.rk.afterstart.DataModel.RealTime
import com.rk.afterstart.DataModel.RealTime1
import com.rk.afterstart.databinding.ActivityRetriveFromRealTimeBinding
import com.rk.afterstart.databinding.UpdateNoteBinding


class RetriveFromRealTime : AppCompatActivity(), RealTimeDataFetchAdapter.OnItemClickListener {
    private lateinit var binding: ActivityRetriveFromRealTimeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var ryclerView: androidx.recyclerview.widget.RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetriveFromRealTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        ryclerView = binding.recyclerView3
        ryclerView.layoutManager = LinearLayoutManager(this)
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes")
        val key = FirebaseDatabase.getInstance().getReference("Notes").key
        val dataList = mutableListOf<RealTime1>()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (noteSnapshot in snapshot.children) {
                    for (childSnapshot in noteSnapshot.children) {
                        for (innerSnapshot in childSnapshot.children) {
//                            val title =
//                                innerSnapshot.child("title").getValue(String::class.java)
//                            val dataLt=innerSnapshot.getValue<RealTime>() //for single line data
//                            val description =
//                                innerSnapshot.child("description")
//                                    .getValue(String::class.java)
//                            if (title != null && description != null) {
//                                val data = RealTime(title, description)
//                                dataList.add(data)
//                            }
                        }
                    }
                    val title =
                        noteSnapshot.child("title").getValue(String::class.java)
                    val description =
                        noteSnapshot.child("description")
                            .getValue(String::class.java)
                    val email = noteSnapshot.child("email").getValue(String::class.java)
                    val Date_time = noteSnapshot.child("date_time").getValue(String::class.java)
                    val noteId = noteSnapshot.key
                    if (email == auth.currentUser?.email.toString()) {
                        if (title != null && description != null&& Date_time != null) {
                            val data = RealTime1(noteId.toString(), title, description,email,Date_time)
                            dataList.add(data)
                        }
                    }
                }
                val adapter = RealTimeDataFetchAdapter(
                    dataList,
                    this@RetriveFromRealTime,
                    this@RetriveFromRealTime
                )
                ryclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read data from Firebase.", error.toException())
            }
        })
    }

    override fun OnDeleteClick(Id: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(Id)
        databaseReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record deleted successfully")
            } else {
                Log.e(TAG, "Failed to delete record", task.exception)
            }
        }
    }

    override fun OnUpdateClick(Id: String, currenttitle: String, currentdescription: String,email: String,date: String) {
        val dialogBinding = UpdateNoteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            .setTitle("Update Note")
            .setPositiveButton("Update") { dialog, _ ->
                val newtitle = dialogBinding.updateTitle.text.toString()
                val newdescription = dialogBinding.updateDescription.text.toString()
                UpdateNote(Id, newtitle, newdescription,email,date)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateTitle.setText(currenttitle)
        dialogBinding.updateDescription.setText(currentdescription)
        dialog.show()
    }

    private fun UpdateNote(id: String, newtitle: String, newdescription: String,email: String,date: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val updateNote = RealTime1(id, newtitle, newdescription, email, date)
        databaseReference.setValue(updateNote).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record Updated successfully")
            } else {
                Log.e(TAG, "Failed to Updated record", task.exception)
            }
        }
    }
}