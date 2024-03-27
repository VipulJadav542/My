    package com.rk.afterstart

    import android.content.ContentValues.TAG
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import android.widget.Toast
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.FirebaseDatabase
    import com.rk.afterstart.DataModel.RealTime1
    import com.rk.afterstart.databinding.ActivityRealTimeDatabaseBinding
    import java.text.SimpleDateFormat
    import java.util.Calendar
    import java.util.Date
    import java.util.Locale

    class RealTimeDatabase : AppCompatActivity() {
        private lateinit var binding: ActivityRealTimeDatabaseBinding
        private lateinit var databaseReference: DatabaseReference
        private lateinit var auth: FirebaseAuth
    //    private lateinit var noteItem: RealTime

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityRealTimeDatabaseBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.show.setOnClickListener {
                startActivity(Intent(this,RetriveFromRealTime::class.java))
            }
            auth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().reference
            binding.save.setOnClickListener {
                val email=auth.currentUser?.email
                val title = binding.notetitle.text.toString()
                val description = binding.notedescription.text.toString()

                val key = FirebaseDatabase.getInstance().getReference("Notes").key
                val noteItem = RealTime1(key.toString(),title, description, email.toString(),getCurrentDateTime())
                databaseReference.child("Notes").push().setValue(noteItem)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this,RetriveFromRealTime::class.java))
                            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                            Log.e(TAG, "Failed to write data to Firebase.", task.exception)
                        }
                    }
            }
        }
        private fun getCurrentDateTime(): String {
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
            return dateFormat.format(currentDate)
        }
    }