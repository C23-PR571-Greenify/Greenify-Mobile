package com.rememberdev.greenify.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rememberdev.greenify.databinding.ItemCategoryBinding
import com.rememberdev.greenify.model.DataCategories
import com.rememberdev.greenify.ui.activities.CategoryTourismActivity

class CategoriesAdapter(private val listCategory: List<DataCategories>) :
    RecyclerView.Adapter<CategoriesAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listCategory[position]
        holder.binding.tvItemCategoryName.text = data.name
//        holder.binding.tvRating.text = data.averageRating.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CategoryTourismActivity::class.java)
            intent.putExtra("EXTRA_ID_CATEGORY", data.id.toString())
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listCategory.size
}