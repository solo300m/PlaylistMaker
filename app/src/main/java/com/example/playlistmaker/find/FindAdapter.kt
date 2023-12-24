package com.example.playlistmaker.find

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R

class FindAdapter(private val list: List<Track>):RecyclerView.Adapter<FindViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_cart, parent,false)
        return FindViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FindViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
