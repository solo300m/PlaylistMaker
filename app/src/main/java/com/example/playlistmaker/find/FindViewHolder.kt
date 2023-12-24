package com.example.playlistmaker.find

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R


class FindViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
    private val trackName: TextView
    private val authorTrace: TextView
    private val timeTrack: TextView
    private val pictureTrack: ImageView
    private val card: ConstraintLayout
    private val view: View

    init {
        trackName = parentView.findViewById(R.id.trackName)
        authorTrace = parentView.findViewById(R.id.authorTrack)
        timeTrack = parentView.findViewById(R.id.timeTrack)
        pictureTrack = parentView.findViewById(R.id.picture)
        card = parentView.findViewById(R.id.cardTrack)
        view = parentView
    }

    fun bind(model: Track) {
        trackName.text = model.trackName
        authorTrace.text = model.artistName
        timeTrack.text = model.trackTime
        Glide
            .with(view)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.barsik)
            .transform(RoundedCorners(10))
            .into(pictureTrack)
    }
}
