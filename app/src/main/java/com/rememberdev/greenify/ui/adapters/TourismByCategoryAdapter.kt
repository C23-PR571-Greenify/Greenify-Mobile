package com.rememberdev.greenify.ui.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
import com.rememberdev.greenify.databinding.ItemTourismBinding
import com.rememberdev.greenify.model.AllTourismImagesItem
import com.rememberdev.greenify.model.DataTourismByCategory
import com.rememberdev.greenify.ui.activities.DetailTourismActivity

class TourismByCategoryAdapter(private val listAllTourism: ArrayList<DataTourismByCategory>) :
    RecyclerView.Adapter<TourismByCategoryAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: ItemTourismBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TourismByCategoryAdapter.ListViewHolder {
        val binding = ItemTourismBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TourismByCategoryAdapter.ListViewHolder, position: Int) {
        val data = listAllTourism[position]
        if (data.tourismImages.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(data.tourismImages[0].imageUrl)
                .into(holder.binding.imgTourism)
        } else {
            Glide.with(holder.itemView.context)
                .load("https://www.salonlfc.com/wp-content/uploads/2018/01/image-not-found-scaled.png")
                .into(holder.binding.imgTourism)
        }
        holder.binding.tvTourismName.text = data.placeName
        holder.binding.tvLocation.text = data.city
        holder.binding.tvPrice.text = data.price
        holder.binding.tvAverageRating.text = data.rating.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetailTourismActivity::class.java)
            intent.putExtra("EXTRA_ID", data.id.toString())
            intent.putExtra("EXTRA_NAME", data.placeName)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listAllTourism.size

}