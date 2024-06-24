package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.player.domain.models.IntentData
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.models.Track

interface PlayerRepository {
    fun getCurrentTrack(locIntent: IntentData):Track?
    fun init(expression:String)
    fun preparePlayer()
    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun getPlayer(): PlayerData
    fun getStatus():Int?
}
