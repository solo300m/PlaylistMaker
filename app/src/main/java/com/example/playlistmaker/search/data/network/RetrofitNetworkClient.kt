package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.search.domain.model.ITunes
import com.example.playlistmaker.search.domain.model.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {
    private val trackBaseURL = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(trackBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val trackService = retrofit.create(ITunes::class.java)

    override fun doRequest(dto: String): Response {
        return if(dto.isNotEmpty()){
            val resp = trackService.search(dto).execute()
            val body = resp.body() ?: Response()
            body.apply { resultCode = resp.code() }
        }else{
            Response().apply { resultCode = 400 }
        }
    }

    override fun getITunesClient(): ITunes {
        return trackService
    }
}
