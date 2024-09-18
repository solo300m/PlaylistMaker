package com.example.playlistmaker.player.domain.models

import android.content.Intent
import android.media.MediaPlayer

data class PlayerData(
    val mediaPlayer: MediaPlayer?,
    var playerState: Int?,
)
