package com.rk.afterstart

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rk.afterstart.databinding.ActivityRegistrationBinding

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val registrationData = intent.getStringExtra("Registration")
        binding.textView9.text = registrationData.toString()

        binding.signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.button4.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
        binding.register123.setOnClickListener {
            if (binding.email123.text.isEmpty() && binding.password123.text.isEmpty()) {
                Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
            } else {
                val email1 = binding.email123.text.trim { it <= ' ' }.toString()
                val password1 = binding.password123.text.trim { it <= ' ' }.toString()
                auth.signInWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val sharedpref =
                                getSharedPreferences("my_pre", Context.MODE_PRIVATE)
                            val editor = sharedpref.edit()
                            editor.putString("email", email1)
                            editor.apply()
                            startActivity(Intent(this,MainActivity::class.java))
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
}