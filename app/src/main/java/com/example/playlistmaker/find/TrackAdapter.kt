package com.example.playlistmaker.find

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R

class TrackAdapter(private val list: List<Track>):RecyclerView.Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_cart, parent,false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
