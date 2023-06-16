package com.rememberdev.greenify.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.rememberdev.greenify.R

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {
    private val splashTimeOut: Long = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        Handler().postDelayed({
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}