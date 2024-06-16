package com.example.playlistmaker.player.domain.api

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.player.domain.models.Track

interface MediaPlayerInteractor {
    fun getCurrentTrack(intent:Intent): Track
    fun init(expression:String)
    fun preparePlayer()
    /*fun onPause()
    fun onDestroy()*/
    fun playbackControl()
    fun startPlayer()
    fun stopPlayer()
    fun pausePlayer()
    fun getPlayer():MediaPlayer
    fun getStatus():Int
}
