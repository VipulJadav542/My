package com.rk.afterstart.PhoneAuth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.rk.afterstart.MainActivity
import com.rk.afterstart.databinding.ActivityNumbersBinding
import java.util.concurrent.TimeUnit


class Numbers : AppCompatActivity() {
    private lateinit var binding: ActivityNumbersBinding
    private lateinit var phonrNumber:EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var sendOTP: Button
    private lateinit var number:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        phonrNumber=binding.number
        sendOTP=binding.send
        auth=FirebaseAuth.getInstance()
        val button=binding.send

        button.setOnClickListener {
            Toast.makeText(this,phonrNumber.text.toString(), Toast.LENGTH_SHORT).show()
            if(phonrNumber.text.isNotEmpty()) {
                if(phonrNumber.text.length == 10)
                {
                    number="+91${phonrNumber.text}"
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(callbacks)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                }
                else
                {
                    Toast.makeText(this,"enter 10 digit mobile number", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(this,"please enter mobile number", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {

            } else if (e is FirebaseTooManyRequestsException) {

            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {

            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {

            Log.d(TAG, "onCodeSent:$verificationId")
            val intent = Intent(this@Numbers,OTPActivity::class.java)
            intent.putExtra("OTP", verificationId)
            intent.putExtra("resendToken", token)
            intent.putExtra("PhoneNumber", number)
            startActivity(intent)

        }
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")

                    startActivity(Intent(this,MainActivity::class.java))

                    val user = task.result?.user
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    }
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser != null)
        {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}