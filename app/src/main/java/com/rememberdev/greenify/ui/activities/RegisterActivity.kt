package com.rememberdev.greenify.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.rememberdev.greenify.R
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.databinding.ActivityRegisterBinding
import com.rememberdev.greenify.ui.viewmodels.RegisterViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    var isShowPassword = false

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var idUserPreference: IdUserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUserPreference = IdUserPreference(this)

        showLoading(false)
        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setupView()
        setupAction()
    }

    private fun actionViewHidePassword(editText: EditText, imageView: ImageView) {
        if (isShowPassword) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.ic_hidden_password)
            isShowPassword = false
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.ic_show_password)
            isShowPassword = true
        }
        // Move the cursor to the end of the text after changing the input type
        editText.setSelection(editText.text!!.length)
    }

    private fun setupAction() {

        binding.showPassword.setOnClickListener {
            actionViewHidePassword(binding.edRegisterPassword, binding.showPassword)
        }

        binding.showConfirmPassword.setOnClickListener {
            actionViewHidePassword(binding.edRegisterConfirmPassword, binding.showConfirmPassword)
        }

        binding.actionToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.actionRegister.setOnClickListener {
            val fullName = binding.edRegisterFullname.text.toString()
            val username = binding.edRegisterUsername.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val phone = binding.edRegisterPhone.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val confirmPassword = binding.edRegisterConfirmPassword.text.toString()

            when {
                fullName.isEmpty() -> {
                    Toast.makeText(this, "Please input FullName", Toast.LENGTH_SHORT).show()
                }
                username.isEmpty() -> {
                    Toast.makeText(this, "Please input Username", Toast.LENGTH_SHORT).show()
                }
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
                phone.isEmpty() -> {
                    Toast.makeText(this, "Please input Phone Number", Toast.LENGTH_SHORT).show()
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
                confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Please input Confirm Password", Toast.LENGTH_SHORT).show()
                }
                confirmPassword.isNotEmpty() && confirmPassword != password ->{
                    binding.edRegisterConfirmPassword.error = "Password and confirm password must match"
                }
                else -> {
                    registerViewModel.userRegister(
                        fullName,
                        username,
                        email,
                        password,
                        phone,
                        confirmPassword
                    )
                    registerViewModel.registerSuccess.observe(this) {
                        if (it) {
                            AlertDialog.Builder(this).apply {
                                setTitle("Yeah!")
                                setMessage("Verification code has been sent please check your email")
                                setPositiveButton("Continue") { _, _ ->
                                    startActivity(Intent(this@RegisterActivity, VerifyOTPActivity::class.java))
                                    finish()
                                }
                                create()
                                show()
                            }
                        } else {
                            AlertDialog.Builder(this).apply {
                                setTitle("Oops!")
                                setMessage("Failed to create account")
                                setPositiveButton("Try again") { _, _ ->
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
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