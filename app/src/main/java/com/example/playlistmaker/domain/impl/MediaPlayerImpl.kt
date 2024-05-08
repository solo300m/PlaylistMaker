package com.example.playlistmaker.domain.impl

import android.media.MediaPlayer

import com.example.playlistmaker.domain.api.PlayerType


class MediaPlayerImpl : PlayerType {
    var previewUrl: String = ""
    override fun init(expression: String) {
        if (!expression.isNullOrEmpty())
            previewUrl = expression
        else
            previewUrl = ""
    }

    private val mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT

    override fun preparePlayer() {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
//            currentTime = 0
//            currTime.text = dateFormat.format(currentTime)
        }
        mediaPlayer.setOnCompletionListener {
            //playButton.setImageResource(R.drawable.playbutton)
            playerState = STATE_PREPARED
//            currentTime = 0
//            currTime.text = dateFormat.format(currentTime)
        }
    }

    override fun onPause() {
        pausePlayer()
    }

    override fun onDestroy() {
        //super.onDestroy()
        mediaPlayer.release()
    }

    override fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    override fun startPlayer() {
        mediaPlayer.start()
        //playButton.setImageResource(R.drawable.pause)
        playerState = STATE_PLAYING
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        //playButton.setImageResource(R.drawable.playbutton)
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
//        private const val DELAY = 1000L
    }
}
