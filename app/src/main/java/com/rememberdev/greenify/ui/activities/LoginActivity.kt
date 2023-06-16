package com.rememberdev.greenify.ui.activities

import android.content.Intent
import android.graphics.fonts.FontFamily
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rememberdev.greenify.R
import com.rememberdev.greenify.databinding.ActivityLoginBinding
import com.rememberdev.greenify.ui.viewmodels.LocationViewModel
import com.rememberdev.greenify.ui.viewmodels.LoginViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    var isShowPassword = false

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setupView()
        setupAction()
    }

    private fun setupAction() {

        binding.showPassword.setOnClickListener {
            actionViewHidePassword()
        }

        binding.actionToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.actionLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            when {
                email.isEmpty() -> {
                    Toast.makeText(this, "Please input Email", Toast.LENGTH_SHORT).show()
                }
                email.isNotEmpty() && !email.contains("@") -> {
                    Toast.makeText(
                        this,
                        "invalid Email! please try input Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Please input Password", Toast.LENGTH_SHORT).show()
                }
                password.length < 8 -> {
                    Toast.makeText(
                        this,
                        "Password must be longer than 8 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    loginViewModel.loginUser(email, password)
                    loginViewModel.loginSuccess.observe(this) {
                        if (it) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
//                            finish()
                        } else {
//                            return@observe
                        }
                    }
                }
            }
        }
    }

    private fun actionViewHidePassword() {
        if (isShowPassword) {
            binding.edLoginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.showPassword.setImageResource(R.drawable.ic_hidden_password)
            isShowPassword = false
        } else {
            binding.edLoginPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.showPassword.setImageResource(R.drawable.ic_show_password)
            isShowPassword = true
        }
        // Move the cursor to the end of the text after changing the input type
        binding.edLoginPassword.setSelection(binding.edLoginPassword.text!!.length)
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