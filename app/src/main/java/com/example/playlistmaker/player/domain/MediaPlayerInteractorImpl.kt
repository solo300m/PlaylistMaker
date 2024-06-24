package com.example.playlistmaker.player.domain

import com.example.playlistmaker.player.domain.models.IntentData
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.api.PlayerRepository
import com.example.playlistmaker.player.domain.models.Track

class MediaPlayerInteractorImpl(playerRepository: PlayerRepository) : MediaPlayerInteractor {
    private val playerRep = playerRepository
    override fun getCurrentTrack(locIntent: IntentData): Track? {
       return locIntent?.let { playerRep.getCurrentTrack(it) }
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

    override fun getPlayer(): PlayerData {
        return playerRep.getPlayer()
    }

    override fun getStatus(): Int? {
        return playerRep.getStatus()
    }

}
