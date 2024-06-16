package com.example.playlistmaker.player.domain.impl

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.player.domain.api.PlayerRepository
import com.example.playlistmaker.player.domain.api.MediaPlayerInteractor
import com.example.playlistmaker.player.domain.models.Track

class MediaPlayerInteractorImpl(playerRepository: PlayerRepository) : MediaPlayerInteractor {
    private val playerRep = playerRepository
    override fun getCurrentTrack(intent: Intent): Track {
       return playerRep.getCurrentTrack(intent)
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

    override fun stopPlayer() {
        playerRep.stopPlayer()
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
