package com.example.playlistmaker.player.data.impl

import android.content.Intent
import android.media.MediaPlayer
import com.example.playlistmaker.player.data.dto.PlayerRepository
import com.example.playlistmaker.player.domain.models.Track

class PlayerRepositoryImpl(intent:Intent) : PlayerRepository {
    private val intent:Intent = intent
    private var previewUrl: String = ""
    private val mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT

    override fun getCurrentTrack(): Track {
        val trackId = intent.getLongExtra("trackId", 0L)
        val trackName = intent.getStringExtra("trackName").toString()
        val pictureUrl = intent.getStringExtra("trackPicture").toString()
        val singerName = intent.getStringExtra("nameSinger").toString()
        val longTimeT = intent.getLongExtra("longTime", 0L)
        val albumT = intent.getStringExtra("album").toString()
        val countryName = intent.getStringExtra("country").toString()
        val realiseDate = intent.getStringExtra("realiseDate").toString()
        val genreName = intent.getStringExtra("genreName").toString()
        val previewUrl = intent.getStringExtra("url").toString()

        return Track(
            trackId,
            trackName,
            singerName,
            longTimeT,
            pictureUrl,
            albumT,
            realiseDate,
            genreName,
            countryName,
            previewUrl,
        )

    }

    override fun init(expression: String) {
        if (!expression.isNullOrEmpty())
            previewUrl = expression
        else
            previewUrl = ""
    }

    override fun preparePlayer() {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }

    override fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
            STATE_DEFAULT ->{
                stopPlayer()
            }
        }
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = STATE_PLAYING
    }

    override fun stopPlayer() {
        mediaPlayer.stop()
        playerState = STATE_DEFAULT
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
    }

    override fun getPlayer(): MediaPlayer {
        return mediaPlayer
    }

    override fun getStatus(): Int {
        return playerState
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val DELAY = 1000L
    }
}
