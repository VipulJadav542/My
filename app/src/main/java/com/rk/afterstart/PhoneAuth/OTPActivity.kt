package com.rk.afterstart.PhoneAuth

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.rk.afterstart.MainActivity
import com.rk.afterstart.R
import com.rk.afterstart.databinding.ActivityOtpactivityBinding
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var num1: EditText
    private lateinit var num2: EditText
    private lateinit var num3: EditText
    private lateinit var num4: EditText
    private lateinit var num5: EditText
    private lateinit var num6: EditText
    private lateinit var varify: Button
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var OTP: String
    private lateinit var Auth: FirebaseAuth
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
        addTextChangedListener()
        OTP = intent.getStringExtra("OTP").toString()
        resendToken = intent.getParcelableExtra("resendToken")!!
        phoneNumber = intent.getStringExtra("PhoneNumber")!!

        binding.textView14.setOnClickListener {
            resendVarificationCode()
            resendOTPTextVisibility()
        }
        varify.setOnClickListener {
            val typedOTP =
                (num1.text.toString() + num2.text.toString() + num3.text.toString() + num4.text.toString() + num5.text.toString() + num6.text.toString())

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                        OTP, typedOTP
                    )
                    signInWithPhoneAuthCredential(credential)
                } else {
                    Toast.makeText(this, "Enter all OTP", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resendOTPTextVisibility() {
        num1.setText("")
        num2.setText("")
        num3.setText("")
        num4.setText("")
        num5.setText("")
        num6.setText("")

        binding.textView14.visibility = View.INVISIBLE
        binding.textView14.isEnabled = false

        Handler(Looper.myLooper()!!).postDelayed(
            {
                binding.textView14.visibility = View.VISIBLE
                binding.textView14.isEnabled = true

            }, 6000
        )
    }

    private fun resendVarificationCode() {
        val options = PhoneAuthOptions.newBuilder(Auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .setForceResendingToken(resendToken)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(ContentValues.TAG, "onVerificationCompleted:$credential")
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(ContentValues.TAG, "onVerificationFailed", e)

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
            } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                // reCAPTCHA verification attempted with null Activity
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            OTP = verificationId
            resendToken = token

        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")

                    startActivity(Intent(this, MainActivity::class.java))

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun init() {
        num1 = binding.editTextText1
        num2 = binding.editTextText2
        num3 = binding.editTextText3
        num4 = binding.editTextText4
        num5 = binding.editTextText5
        num6 = binding.editTextText6

        Auth = FirebaseAuth.getInstance()
        varify = binding.button10
    }

    private fun addTextChangedListener() {
        num1.addTextChangedListener(EditTextWatcher(num1))
        num2.addTextChangedListener(EditTextWatcher(num2))
        num3.addTextChangedListener(EditTextWatcher(num3))
        num4.addTextChangedListener(EditTextWatcher(num4))
        num5.addTextChangedListener(EditTextWatcher(num5))
        num6.addTextChangedListener(EditTextWatcher(num6))
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()
            when (view.id) {
                R.id.editTextText1 -> if (text.length == 1) num2.requestFocus()
                R.id.editTextText2 -> if (text.length == 1) num3.requestFocus() else if (text.isEmpty()) num1.requestFocus()
                R.id.editTextText3 -> if (text.length == 1) num4.requestFocus() else if (text.isEmpty()) num2.requestFocus()
                R.id.editTextText4 -> if (text.length == 1) num5.requestFocus() else if (text.isEmpty()) num3.requestFocus()
                R.id.editTextText5 -> if (text.length == 1) num6.requestFocus() else if (text.isEmpty()) num4.requestFocus()
                R.id.editTextText6 -> if (text.isEmpty()) num5.requestFocus()
            }
        }

    }
}