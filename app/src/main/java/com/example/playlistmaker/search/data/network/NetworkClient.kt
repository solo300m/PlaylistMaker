package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.search.domain.model.ITunes
import com.example.playlistmaker.search.domain.model.Response

interface NetworkClient {
    fun doRequest(dto:String): Response
    fun getITunesClient(): ITunes
}
