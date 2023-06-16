package com.rememberdev.greenify.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.databinding.ActivityVerifyOtpBinding
import com.rememberdev.greenify.ui.viewmodels.VerifyOTPViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class VerifyOTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpBinding
    private val verifyOTPViewModel: VerifyOTPViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var idUserPreference: IdUserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUserPreference = IdUserPreference(this)

        showLoading(false)
        verifyOTPViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.actionVerifyOtp.setOnClickListener {
            val userId = idUserPreference.getUserID()
            val verifyOTPCode = binding.edVerifyOtp.text.toString().trim()
            if (verifyOTPCode.isEmpty()) {
                Toast.makeText(this@VerifyOTPActivity, "Please input verification code", Toast.LENGTH_SHORT)
                    .show()
            } else {
                verifyOTPViewModel.verifyOTP(userId, verifyOTPCode)
                verifyOTPViewModel.verifySuccess.observe(this) {
                    if (it) {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        return@observe
                    }
                }
            }
        }

        binding.actionResendOtp.setOnClickListener{
            startActivity(Intent(this, ResendOTPActivity::class.java))
            finish()
        }
    }

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}