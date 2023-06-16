package com.rememberdev.greenify.ui.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.app.AuthenticationRequiredException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.rememberdev.greenify.data.preferences.IdUserPreference
import com.rememberdev.greenify.databinding.FragmentProfileBinding
import com.rememberdev.greenify.model.UserModel
import com.rememberdev.greenify.ui.activities.AuthorActivity
import com.rememberdev.greenify.ui.activities.EditProfileActivity
import com.rememberdev.greenify.ui.activities.ForgotPasswordActivity
import com.rememberdev.greenify.ui.activities.LoginActivity
import com.rememberdev.greenify.ui.viewmodels.UserViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mIdUserPreference: IdUserPreference

    private val userViewModel: UserViewModel by viewModels {
        ViewModelFactory.getInstance(Application())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIdUserPreference = IdUserPreference(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAction()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewModel() {
        userViewModel.getUserById(mIdUserPreference.getUserID().toInt())
        userViewModel.dataUser.observe(viewLifecycleOwner) {
            binding.tvProfileUsername.text = "Hello, ${it.fullname}"
            binding.tvProfileEmail.text = it.email
        }
    }

    private fun setupAction() {

        binding.tvEditProfile.setOnClickListener {
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(requireContext(), ForgotPasswordActivity::class.java))
        }

        binding.tvLogout.setOnClickListener {
            mIdUserPreference.setUserID("")
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.tvAuthor.setOnClickListener {
            startActivity(Intent(requireContext(), AuthorActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}