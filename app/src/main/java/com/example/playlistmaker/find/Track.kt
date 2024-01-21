package com.example.playlistmaker.find

data class Track(
    val id:Int,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String
)
