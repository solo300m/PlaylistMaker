package com.example.playlistmaker.player.domain

import com.example.playlistmaker.player.domain.models.IntentData
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.models.Track

interface MediaPlayerInteractor {
    fun getCurrentTrack(locIntent: IntentData): Track?
    fun init(expression:String)
    fun preparePlayer()
    /*fun onPause()
    fun onDestroy()*/
    fun playbackControl()
    fun startPlayer()
    fun stopPlayer()
    fun pausePlayer()
    fun getPlayer(): PlayerData
    fun getStatus():Int?
}
