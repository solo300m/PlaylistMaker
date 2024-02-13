package com.example.playlistmaker.find

data class Track(
    val trackId:Long, // изменение типа поля с Int на Long для использования в поисковых алгоритмах класса TrackPreferences
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String
){
    fun getId():Long{
        return trackId
    }
}
