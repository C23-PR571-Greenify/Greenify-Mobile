package com.rememberdev.greenify.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.databinding.ActivityResendOtpBinding
import com.rememberdev.greenify.ui.viewmodels.ResendOTPViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class ResendOTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResendOtpBinding

    private val resendOTPViewModel: ResendOTPViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var idUserPreference: IdUserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResendOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUserPreference = IdUserPreference(this)

        showLoading(false)

        resendOTPViewModel.isLoading.observe(this){
            showLoading(it)
        }

        setupView()
        setupAction()
    }

    private fun setupAction() {
        binding.actionResendOtp.setOnClickListener {
            val userId = idUserPreference.getUserID()
            val email = binding.edResendOtpEmail.text.toString().trim()
            if (email.isEmpty()){
                Toast.makeText(this@ResendOTPActivity, "Please input your email!", Toast.LENGTH_SHORT).show()
            } else {
                resendOTPViewModel.resendOTP(userId, email)
                resendOTPViewModel.resendOTPSuccess.observe(this){
                    if (it){
                        startActivity(Intent(this, VerifyOTPActivity::class.java))
                        finish()
                    } else {
                        return@observe
                    }
                }
            }
        }
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

    private fun showLoading(it: Boolean) {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }
}