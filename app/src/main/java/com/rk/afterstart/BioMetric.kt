package com.rk.afterstart

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.core.app.ActivityCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BioMetric : AppCompatActivity() {
    private var cancellation: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                NotifyUser("Authentication Error $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                NotifyUser("Authentication Success!!!")
//                startActivity(Intent(this@BioMetric, MainActivity::class.java))
            }
        }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_page)


        checkBioMetricSupport()
        findViewById<Button>(R.id.Authenticate).setOnClickListener {
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Authentication")
                .setSubtitle("Authentication is required")
                .setDescription("this app uses fingerprint protection to keep your data secure")
                .setNegativeButton(
                    "Use Password",
                    this.mainExecutor,
                    DialogInterface.OnClickListener { dialog, which ->
                        showPasswordDialog()
                    })
                .build()
            biometricPrompt.authenticate(getCancelationSignal(),mainExecutor,authenticationCallback)
        }
    }

    private fun getCancelationSignal(): CancellationSignal {
        cancellation = CancellationSignal()
        cancellation?.setOnCancelListener {
            NotifyUser("Authentication was canceled by user")
        }
        return cancellation as CancellationSignal
    }

    private fun checkBioMetricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            NotifyUser("Fingerprint authentication has not been enabled in settings")
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            NotifyUser("Fingerprint authentication permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else {
            true
        }
    }

    private fun NotifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showPasswordDialog() {
        var inputEditText = EditText(this)
        inputEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        inputEditText.hint="Enter Password"
        val passwordPrompt = MaterialAlertDialogBuilder(this)
            .setTitle("Authentication")
            .setMessage("Enter your password")
            .setView(inputEditText)
            .setPositiveButton("OK") { dialog, which ->
                val enteredPassword = inputEditText.text.toString()
                    // Check if entered password is correct
                    if (enteredPassword == "123456") {
                        NotifyUser("Password authentication successful")
                        // Proceed to the next screen or perform desired action
                    } else {
                        NotifyUser("Invalid password")
                        // Handle invalid password scenario
                    }
            }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .create()

        passwordPrompt.show()
    }
}