package com.example.playlistmaker.find

data class Track(
    val trackId:Long,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String
){
    fun getId():Long{
        return trackId
    }
}
