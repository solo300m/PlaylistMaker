package com.example.playlistmaker.player.data.dto

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.player.domain.models.Track

interface PlayerRepository {
    fun getCurrentTrack():Track
    fun init(expression:String)
    fun preparePlayer()
    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun getPlayer(): MediaPlayer
    fun getStatus():Int
}
