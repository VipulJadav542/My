package com.rk.afterstart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.rk.afterstart.databinding.ActivityMainBinding
import com.rk.afterstart.databinding.ActivityWebViewBinding

class WebView1 : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.webview.loadUrl("https://neatroots.com/")
//        binding.webview.loadUrl("file:///android_asset/index.html")
        binding.webview.settings.javaScriptEnabled=true
        binding.webview.webViewClient= WebViewClient() //not open in browser
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webview.canGoBack()) {
            binding.webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}