package com.example.playlistmaker.find

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunes {
    @GET("/search?entity=song")
    fun search(@Query("term") text: String): Call<TrackResponse>
}
