package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.data.dto.ITunes
import com.example.playlistmaker.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto:String):Response
    fun getITunesClient():ITunes
}
