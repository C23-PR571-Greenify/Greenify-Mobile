package com.rememberdev.greenify.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rememberdev.greenify.databinding.ItemAuthorBinding
import com.rememberdev.greenify.model.DataAuthor

class AuthorAdapter(private val listAuthor: List<DataAuthor>) : RecyclerView.Adapter<AuthorAdapter.ListViewHolder>() {
    class ListViewHolder(var binding: ItemAuthorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AuthorAdapter.ListViewHolder {
        val binding = ItemAuthorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AuthorAdapter.ListViewHolder, position: Int) {
        val data = listAuthor[position]
        Glide.with(holder.itemView.context)
            .load(data.profileUrl)
            .into(holder.binding.imgAuthor)
        holder.binding.tvAuthorName.text = data.fullname
        holder.binding.tvId.text = data.bangkitId
        holder.binding.tvPath.text = data.path
        holder.binding.tvLinkedin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.linkedIn))
            it.context.startActivity(intent)
        }
        holder.binding.tvGithub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.github))
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = listAuthor.size
}