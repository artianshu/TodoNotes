package com.example.todonotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.todonotes.R
import com.example.todonotes.model.Data

class BlogsAdapter(val list : List<Data>) : RecyclerView.Adapter<BlogsAdapter.ViewHolder>(){

    inner class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val imageViewBlog: ImageView = itemView.findViewById(R.id.imageViewBlog)
        val textViewTitleBlog: TextView = itemView.findViewById(R.id.textViewTitleBlog)
        val textViewDescriptionBlog: TextView = itemView.findViewById(R.id.textViewDescriptionBlog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_blog,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = list[position]
        holder.textViewTitleBlog.text = blog.title.trim()
        holder.textViewDescriptionBlog.text = blog.description.trim()
        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageViewBlog)
    }
}