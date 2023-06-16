package com.rememberdev.greenify.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememberdev.greenify.databinding.ActivityAuthorBinding
import com.rememberdev.greenify.model.DataAuthor
import com.rememberdev.greenify.ui.adapters.AuthorAdapter
import com.rememberdev.greenify.ui.viewmodels.AuthorViewModel
import com.rememberdev.greenify.utils.ViewModelFactory

class AuthorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorBinding

    private val authorViewModel: AuthorViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var rvAuthor: RecyclerView
    private val listAuthor = ArrayList<DataAuthor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authorViewModel.getAuthors()

        rvAuthor = binding.rvAuthors
        rvAuthor.setHasFixedSize(true)
        showListAuthors(listAuthor)

        authorViewModel.authorList.observe(this){
            showListAuthors(it)
        }
    }

    private fun showListAuthors(listAuthor: List<DataAuthor>) {
        rvAuthor.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = AuthorAdapter(listAuthor)
        rvAuthor.adapter = adapter
    }
}