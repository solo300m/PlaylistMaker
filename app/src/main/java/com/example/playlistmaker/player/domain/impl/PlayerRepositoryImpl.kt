package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.player.domain.models.IntentData
import com.example.playlistmaker.player.domain.models.PlayerData
import com.example.playlistmaker.player.domain.api.PlayerRepository
import com.example.playlistmaker.player.domain.models.Track

class PlayerRepositoryImpl() : PlayerRepository {

    private var previewUrl: String = ""
    private val mediaPlayer = Creator.getMediaPlayer()

    override fun getCurrentTrack(locIntent: IntentData): Track? {
        if (locIntent.intentStatus) {
            val trackId = locIntent.intent?.getLongExtra("trackId", 0L)
            val trackName = locIntent.intent?.getStringExtra("trackName").toString()
            val pictureUrl = locIntent.intent?.getStringExtra("trackPicture").toString()
            val singerName = locIntent.intent?.getStringExtra("nameSinger").toString()
            val longTimeT = locIntent.intent?.getLongExtra("longTime", 0L)
            val albumT = locIntent.intent?.getStringExtra("album").toString()
            val countryName = locIntent.intent?.getStringExtra("country").toString()
            val realiseDate = locIntent.intent?.getStringExtra("realiseDate").toString()
            val genreName = locIntent.intent?.getStringExtra("genreName").toString()
            val previewUrl = locIntent.intent?.getStringExtra("url").toString()

            return Track(
                trackId!!,
                trackName,
                singerName,
                longTimeT!!,
                pictureUrl,
                albumT,
                realiseDate,
                genreName,
                countryName,
                previewUrl,
            )
        }else{
            return null
        }

    }

    override fun init(expression: String) {
        if (!expression.isNullOrEmpty())
            previewUrl = expression
        else
            previewUrl = ""
    }

    override fun preparePlayer() {
        mediaPlayer.mediaPlayer?.setDataSource(previewUrl)
        mediaPlayer.mediaPlayer?.prepareAsync()
        mediaPlayer.mediaPlayer?.setOnPreparedListener {
            mediaPlayer.playerState = STATE_PREPARED
        }
        mediaPlayer.mediaPlayer?.setOnCompletionListener {
            mediaPlayer.playerState = STATE_PREPARED
        }
    }

    override fun playbackControl() {
        when (mediaPlayer.playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }

            STATE_DEFAULT -> {
                stopPlayer()
            }
        }
    }

    override fun startPlayer() {
        mediaPlayer.mediaPlayer?.start()
        mediaPlayer.playerState = STATE_PLAYING
    }

    override fun stopPlayer() {
        mediaPlayer.mediaPlayer?.stop()
        mediaPlayer.playerState = STATE_DEFAULT
    }

    override fun pausePlayer() {
        mediaPlayer.mediaPlayer?.pause()
        mediaPlayer.playerState = STATE_PAUSED
    }

    override fun getPlayer(): PlayerData {
        return mediaPlayer
    }

    override fun getStatus(): Int? {
        return mediaPlayer.playerState
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 1000L
    }
}
