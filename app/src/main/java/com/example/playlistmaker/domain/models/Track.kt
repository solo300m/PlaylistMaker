package com.example.playlistmaker.domain.models

import java.io.Serializable

data class Track(
    val trackId:Long, // изменение типа поля с Int на Long для использования в поисковых алгоритмах класса TrackPreferences
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Long,
    val artworkUrl100: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String
):Serializable{
    fun getId():Long{
        return trackId
    }
}
