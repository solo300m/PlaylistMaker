package com.example.playlistmaker.ui.find

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {
    @GET("/search?entity=song")
    fun getIdTrack(@Query("term") id: Long):Call<TrackResponse>
}
