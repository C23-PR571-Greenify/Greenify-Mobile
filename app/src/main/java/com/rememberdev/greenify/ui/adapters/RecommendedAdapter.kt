package com.rememberdev.greenify.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rememberdev.greenify.databinding.ItemCategoryBinding
import com.rememberdev.greenify.databinding.ItemRecommendedBinding
import com.rememberdev.greenify.model.CleanedResultItem
import com.rememberdev.greenify.model.DataCategories
import com.rememberdev.greenify.ui.activities.DetailTourismActivity

class RecommendedAdapter(private val listRecommended: ArrayList<CleanedResultItem>) :
    RecyclerView.Adapter<RecommendedAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemRecommendedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecommendedAdapter.ListViewHolder {
        val binding = ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecommendedAdapter.ListViewHolder, position: Int) {
        val data = listRecommended[position]
        Glide.with(holder.itemView.context)
            .load(data.tourismImages[0].imageUrl)
            .into(holder.binding.imgTourism)
        holder.binding.tvTourismName.text = data.placeName
        holder.binding.tvAverageRating.text = data.rating.toString()
        holder.binding.tvLocation.text = data.city
        holder.binding.tvPrice.text = "Rp. ${data.price} /Visit"
//        holder.binding.tvRating.text = data.averageRating.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailTourismActivity::class.java)
            intent.putExtra("EXTRA_ID", data.id.toString())
            intent.putExtra("EXTRA_NAME", data.placeName)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listRecommended.size
}