package com.rememberdev.greenify.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rememberdev.greenify.model.DataTourismByCategory
import com.rememberdev.greenify.ui.adapters.TourismByCategoryAdapter
import com.rememberdev.greenify.ui.viewmodels.AllTourismByCategoryViewModel
import com.rememberdev.greenify.utils.ViewModelFactory
import com.rememberdev.greenify.databinding.ActivityCategoryTourismBinding

class CategoryTourismActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryTourismBinding

    private val allTourismByCategoryModel: AllTourismByCategoryViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var rvTourism: RecyclerView
    private val listTourismByCategory = ArrayList<DataTourismByCategory>()

    private var extraValueIdCategory: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryTourismBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extraValueIdCategory = intent.getStringExtra("EXTRA_ID_CATEGORY").toString()

        allTourismByCategoryModel.getAllTourismByCategory(extraValueIdCategory.toInt())

        rvTourism = binding.rvTourismByCategory
        rvTourism.setHasFixedSize(true)
        showListTourismByCategory(listTourismByCategory)

        allTourismByCategoryModel.listTourismByCategory.observe(this){
            showListTourismByCategory(it)
        }
    }

    private fun showListTourismByCategory(listTourism: ArrayList<DataTourismByCategory>) {
        rvTourism.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = TourismByCategoryAdapter(listTourism)
        rvTourism.adapter = adapter
    }
}