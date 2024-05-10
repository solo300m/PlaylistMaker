package com.example.playlistmaker.domain.api

import android.media.MediaPlayer

interface MediaPlayerInterface {
    fun init(expression:String)
    fun preparePlayer()
    fun onPause()
    fun onDestroy()
    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun getPlayer():MediaPlayer
    fun getStatus():Int
}
