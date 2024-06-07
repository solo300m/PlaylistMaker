package com.example.playlistmaker.player.domain.impl

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.player.data.dto.PlayerRepository
import com.example.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.models.Track

class MediaPlayerInteractorImpl(intent: Intent, playerRepository:PlayerRepository) : MediaPlayerInteractor {
    private val playerRep = playerRepository
    override fun getCurrentTrack(): Track {
       return playerRep.getCurrentTrack()
    }

    override fun init(expression: String) {
        playerRep.init(expression)
    }

    override fun preparePlayer() {
        playerRep.preparePlayer()
    }

    override fun playbackControl() {
       playerRep.playbackControl()
    }

    override fun startPlayer() {
        playerRep.startPlayer()
    }

    override fun pausePlayer() {
        playerRep.pausePlayer()
    }

    override fun getPlayer(): MediaPlayer {
        return playerRep.getPlayer()
    }

    override fun getStatus(): Int {
        return playerRep.getStatus()
    }

}
