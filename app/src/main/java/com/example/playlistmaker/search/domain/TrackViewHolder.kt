package com.example.playlistmaker.search.domain

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.player.domain.models.Track
import com.example.playlistmaker.search.data.TrackPreferences
import java.text.SimpleDateFormat

import java.util.Locale
import kotlin.properties.Delegates


class TrackViewHolder(parentView: View, listener: Listener) : RecyclerView.ViewHolder(parentView) {
    private val trackName: TextView
    private val authorTrace: TextView
    private val timeTrack: TextView
    private val pictureTrack: ImageView
    private val buttonRight:ImageView
    private val card: ConstraintLayout
    private val view: View
    private var trackId by Delegates.notNull<Long>()
    private var objectSave = TrackPreferences() // объект класса TrackPreferences для обработки и сохранения клика на RecyclerView
    private val dateFormat by lazy {
        SimpleDateFormat("mm : ss", Locale.getDefault())
    }

    init {
        trackName = parentView.findViewById(R.id.trackName)
        authorTrace = parentView.findViewById(R.id.authorTrack)
        timeTrack = parentView.findViewById(R.id.timeTrack)
        pictureTrack = parentView.findViewById(R.id.picture)
        card = parentView.findViewById(R.id.cardTrack)
        buttonRight = parentView.findViewById(R.id.right)//кнопка стрелка вправо на карточке каждого трека
        view = parentView

        card.setOnClickListener { // обработка события Click на карточки трека RecyclerView
            val track = objectSave.getTrack(trackId.toLong())
            if (track != null) {
                listener.onClick(track)
            }
        }

    }
    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
    private val cornerToPx: Int = dpToPx(2f, itemView.context)

    fun bind(model: Track, listener: Listener) {
        trackId = model.trackId
        trackName.text = model.trackName
        authorTrace.text = model.artistName
        timeTrack.text = dateFormat.format(model.trackTimeMillis)
        Glide
            .with(view)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(cornerToPx))
            .into(pictureTrack)
    }

    interface Listener{
        fun onClick(track: Track)
    }
}
